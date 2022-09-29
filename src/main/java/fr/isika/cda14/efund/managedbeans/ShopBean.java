package fr.isika.cda14.efund.managedbeans;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;

import fr.isika.cda14.efund.entity.account.OrganizationAccount;
import fr.isika.cda14.efund.entity.account.paiement.Payment;
import fr.isika.cda14.efund.entity.shop.BasketOrder;
import fr.isika.cda14.efund.entity.shop.Item;
import fr.isika.cda14.efund.entity.shop.OrderLine;
import fr.isika.cda14.efund.services.ShopService;
import fr.isika.cda14.efund.tool.SessionTool;
import fr.isika.cda14.efund.viewmodel.PaymentShopVM;

@ManagedBean
@SessionScoped
public class ShopBean {

	private BigDecimal sumOfCartFromBean = new BigDecimal(0);

	private String orderId;
	private PaymentShopVM paymentVM = new PaymentShopVM();
	private OrganizationAccount orgAccount;

	@Inject
	private ShopService shopService;

	private List<OrderLine> cart = new ArrayList<OrderLine>();

	private OrderLine selectedOrderLine;

	public void onLoad(String itemId) throws IOException {

		if (itemId == null || itemId.isEmpty()) {
		} else {
			this.addOrderLineToCart(this.createOrderLine(Long.parseLong(itemId)));
		}
	}

	public OrderLine createOrderLine(Item item) {
		shopService.createOrderLine(item);

		return shopService.createOrderLine(item);
	}

	public OrderLine createOrderLine(Long id) {
		Item item = shopService.findItem(id);
		shopService.createOrderLine(item);

		return shopService.createOrderLine(item);
	}

	public void addOrderLineToCart(OrderLine orderLine) throws IOException {

		boolean orderlineExists = false;

		if (cart.isEmpty()) {
			orderLine.setQuantity(1);
			orderLine.setDate(Calendar.getInstance().getTime());
			cart.add(orderLine);
		} else {

			for (int i = 0; i < cart.size(); i++) {

				if (cart.get(i).getItem().getId().compareTo(orderLine.getItem().getId()) == 0) {
					cart.get(i).setQuantity(cart.get(i).getQuantity() + 1);
					orderlineExists = true;
				}
			}

			if (!orderlineExists) {
				orderLine.setQuantity(1);
				orderLine.setDate(Calendar.getInstance().getTime());
				cart.add(orderLine);
			}
		}
	}

	/* Methode pour persister mon cart et redireger vers la page de payment */
	public String payMyCart() {

		BasketOrder createdBasketOrder = shopService.createBasketOrder(this.cart);
		BasketOrder persistedBasketOrder = shopService.persistBasketOrder(createdBasketOrder);

		this.saveMyPayment(persistedBasketOrder);

		resetCart();

		return "userProfil?faces-redirect=true&amp;id=" + SessionTool.getUserId();
	}

	private void resetCart() {
		this.cart.clear();
		this.sumOfCartFromBean = BigDecimal.ZERO;
	}

	public BigDecimal sumOfMyCart() {
		sumOfCartFromBean = shopService.sumOfmyCart(this.cart);
		return sumOfCartFromBean;
	}

	public Payment saveMyPayment(BasketOrder basketOrder) {
		Payment payment = new Payment();

		payment.setBasketOrder(basketOrder);
		payment.setCreditCardNumber(paymentVM.getCardNumber());
		payment.setCryptogram(paymentVM.getSecurityCode());
		payment.setFullName(paymentVM.getName());
		payment.setAmount(basketOrder.getTotalPrice());
		payment.setExpirationDate(paymentVM.getExpirationDate());
		payment.setPaymentDate(basketOrder.getDate());

		shopService.saveMyPayment(payment);

		return payment;
	}

	public void onUserCellEdit(CellEditEvent<Integer> event) {
		Integer oldValue = (Integer) event.getOldValue();
		Integer newValue = (Integer) event.getNewValue();

		OrderLine orderLine = (OrderLine) event.getComponent().getAttributes().get("orderLineAttr");
		orderLine.setQuantity(newValue);

		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getItem().getId().compareTo(orderLine.getItem().getId()) == 0) {
				cart.get(i).setQuantity(newValue);
			}
		}

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cellule modifiée",
					"Avant : " + oldValue + ", Après : " + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		try {
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			externalContext.redirect("cartPage.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteOrderLine() {
		this.cart.remove(this.selectedOrderLine);
		this.selectedOrderLine = null;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Compte utilisateur supprimé"));
		PrimeFaces.current().ajax().update("@form:dt-cart");
	}

	public String getUserIdFromSession() {

		return Long.toString(SessionTool.getUserId());

	}

	public List<OrderLine> getCart() {
		return cart;
	}

	public void setCart(List<OrderLine> cart) {
		this.cart = cart;
	}

	public String getOrderId() {
		return orderId;
	}

	public BigDecimal getSumOfCartFromBean() {
		return sumOfCartFromBean;
	}

	public void setSumOfCartFromBean(BigDecimal sumOfCartFromBean) {
		this.sumOfCartFromBean = sumOfCartFromBean;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public OrderLine getSelectedOrderLine() {
		return selectedOrderLine;
	}

	public void setSelectedOrderLine(OrderLine selectedOrderLine) {
		this.selectedOrderLine = selectedOrderLine;
	}

	public OrganizationAccount getOrgAccount() {
		return orgAccount;
	}

	public void setOrgAccount(OrganizationAccount orgAccount) {
		this.orgAccount = orgAccount;
	}

	public PaymentShopVM getPaymentShopForm() {
		return paymentVM;
	}

	public void setPaymentShopForm(PaymentShopVM paymentShopForm) {
		this.paymentVM = paymentShopForm;
	}
}
