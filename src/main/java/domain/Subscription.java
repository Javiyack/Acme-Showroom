
package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"subscriber_id", "subscribedActor_id"}))
public class Subscription extends DomainEntity {

	//Relationships
	private Actor subscriber;
	private Actor subscribedActor;
	private Actor topic;

	private Date since;

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

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}

	@Valid
	@ManyToOne(optional = true)
	public Actor getTopic() {
		return this.topic;
	}

	public void setTopic(Actor topic) {
		this.topic = topic;
	}
}
