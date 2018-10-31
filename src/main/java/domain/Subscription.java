
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"subscriber_id", "subscribedActor_id"}),
							@UniqueConstraint(columnNames = {"subscriber_id", "topic"})})
public class Subscription extends DomainEntity {

	//Relationships
	private Actor subscriber;
	private Actor subscribedActor;
	private String topic;

	//Constructor
	public Subscription() {
		super();
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSubscriber() {
		return this.subscriber;
	}

	public void setSubscriber(final Actor follower) {
		this.subscriber = follower;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getSubscribedActor() {
		return this.subscribedActor;
	}

	public void setSubscribedActor(final Actor followed) {
		this.subscribedActor = followed;
	}

	@SafeHtml
	public String getTopic() {
		return this.topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
}
