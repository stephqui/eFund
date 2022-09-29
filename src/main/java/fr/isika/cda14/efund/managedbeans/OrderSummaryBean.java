package fr.isika.cda14.efund.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import fr.isika.cda14.efund.entity.shop.BasketOrder;
import fr.isika.cda14.efund.services.ShopService;

@ManagedBean
@ViewScoped
public class OrderSummaryBean {

	@Inject
	ShopService shopService;

	BasketOrder order;

	public void onLoad(String id) {
		Long orderId = Long.parseLong(id);
		this.order = shopService.getFullBasketOrder(orderId);
	}

	public BasketOrder getOrder() {
		return order;
	}
}
