
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "name, body")},
		uniqueConstraints = @UniqueConstraint(columnNames = { "name",
		"parent_id" }))
public class Classoplon extends DomainEntity {

	/* Clase maestra para usar como plantilla */


	
	private String name;
	private String subject;
	private String body;
	private String priority;

	private Date moment;

	private boolean broadcast;
	private boolean spam;
	private boolean systemItem;

	private Double NumeroReal;

	private Integer numeroEntero;
	private Integer numberOfDays;

	private Collection<String> comments;

	// Relationships
	private Classoplon parent;
	private Actor actor;
	private Actor recipient;
	private Actor sender;
	private Message mensaje;
	private Folder folder;


	// Collection of Relationships
	private Collection<File> files;





	public boolean isSpam() {
		return spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@SafeHtml
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	@SafeHtml
	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	@NotBlank
	@SafeHtml
	@Pattern(regexp = "HIGH|NEUTRAL|LOW")
	public String getPriority() {
		return this.priority;
	}

	public void setPriority(final String priority) {
		this.priority = priority;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return this.recipient;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public boolean getSystemItem() {
		return this.systemItem;
	}

	public void setSystemItem(final boolean system) {
		this.systemItem = system;
	}

	@ManyToOne(optional = true)
	@Valid
	public Classoplon getParent() {
		return this.parent;
	}

	public void setParent(final Classoplon parentFolder) {
		this.parent = parentFolder;
	}

	@ManyToOne(optional = true)
	@Valid
	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public boolean getBroadcast() {
		return this.broadcast;
	}

	public void setBroadcast(final boolean broadcast) {
		this.broadcast = broadcast;
	}
	

	public Integer getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(Integer numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	@ElementCollection
	@NotNull
	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Message getMensaje() {
		return mensaje;
	}

	public void setMensaje(Message mensaje) {
		this.mensaje = mensaje;
	}

	@NotNull
	@ManyToOne(optional = false)
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	public Double getNumeroReal() {
		return NumeroReal;
	}

	public void setNumeroReal(Double numeroReal) {
		NumeroReal = numeroReal;
	}

	public Integer getNumeroEntero() {
		return numeroEntero;
	}

	public void setNumeroEntero(Integer numeroEntero) {
		this.numeroEntero = numeroEntero;
	}


}
