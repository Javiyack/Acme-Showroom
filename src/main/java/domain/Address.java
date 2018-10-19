package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
@Access(AccessType.PROPERTY)
public class Address {

	private String street;

	private Integer number;

	private String city;

	@NotBlank
	@SafeHtml
	public String getStreet() {
		return street;
	}

	public void setStreet(String holderName) {
		this.street = holderName;
	}

	@NotBlank
	@SafeHtml
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@NotBlank
	@SafeHtml
	public String getCity() {
		return city;
	}

	public void setCity(String expirationMonth) {
		this.city = expirationMonth;
	}

}
