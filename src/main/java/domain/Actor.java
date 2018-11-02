
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	//Attributes
	private String 		name;
	private String 		surname;
	private String 		email;
	private String 		address;
	private String 		phone;
	//Relationships
	private UserAccount userAccount;


	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@SafeHtml
	@Pattern(regexp = "^([+][1-9]\\d{0,2}[ ]?[(][1-9]\\d{0,2}[)][ ]?\\d{4,32})$|^([+][0-9]{1,3}[ ]\\d{4,32})$|^(\\d{4,32})$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}


	@Valid
	@OneToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}


	@SafeHtml
	@NotBlank
	@Size(max = 32)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
