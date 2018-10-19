
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

import domain.Actor;
import domain.Agent;
import domain.User;

public class ActorForm {

	private String name;
	private String surname;
	private String email;
	private String phone; // Optional
	private String address; // Optional
	private String username;
	private String password;
	private String newPassword;
	private String confirmPassword;
	private String authority;
	private String photo; // User
	private Date birthdate; // User
	private String genere; // User
	private String 		company;	
	
	private boolean agree;
	private AccountForm account;
	private int id;
	private int version;

	// Constructors -------------------------

	public ActorForm() {
		super();
		this.id = 0;
		this.version = 0;
		this.account = new AccountForm();
	}

	public ActorForm(final Actor actor) {
		super();
		this.id = actor.getId();
		this.setName(actor.getName());
		this.setSurname(actor.getSurname());
		this.setEmail(actor.getEmail());
		this.setPhone(actor.getPhone());
		this.setAddress(actor.getAddress());
		
		this.setAccount(new AccountForm(actor));
		this.setAuthority(this.getAccount().getAuthority());
		this.setUsername(actor.getUserAccount().getUsername());
		
		this.setPhoto(((User) actor).getPhoto());
		this.setBirthdate(((User) actor).getBirthdate());
		this.setGenere(((User) actor).getGenere());
		
		this.setCompany(((Agent) actor).getCompany());
		
		
		

	}
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date bithdate) {
		this.birthdate = bithdate;
	}

	@Pattern(regexp = "MALE|FEMALE|UNDEFINED")
	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
		this.account.setAuthority(authority);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.account.setUsername(username);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.account.setPassword(password);
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
		this.account.setConfirmPassword(confirmPassword);
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		this.account.setNewPassword(newPassword);
	}

	public AccountForm getAccount() {
		return account;
	}

	public void setAccount(AccountForm account) {
		this.account = account;
	}

	@NotNull
	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	@SafeHtml(whitelistType = WhiteListType.NONE)
	@NotBlank
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@Pattern(regexp = "()|^[6,7]\\d{8}$")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
