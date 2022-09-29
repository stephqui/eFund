package fr.isika.cda14.efund.entity.account;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admin_account")
public class AdminAccount extends Account {

	@OneToOne
	@JoinColumn(name = "admin_info_id")
	private AdminInfo adminInfo;
}
