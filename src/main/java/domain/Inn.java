
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Inn extends DomainEntity {
	
	private String name;
	private String badge; // Insignia. Url
	private String phone;
	private Address address;
	private CreditCard creditCard;
	private String email; /// Optional
	private String webSite; /// Optional

	// Relationships
	private Agent agent;

	@NotBlank
	@SafeHtml
	public String getBadge() {
		return this.badge;
	}

	public void setBadge(final String subject) {
		this.badge = subject;
	}

	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Valid
	@ManyToOne(optional = false)
	public Agent getAgent() {
		return this.agent;
	}

	public void setAgent(final Agent parentFolder) {
		this.agent = parentFolder;
	}


	@SafeHtml
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@SafeHtml
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}


	@Valid
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
