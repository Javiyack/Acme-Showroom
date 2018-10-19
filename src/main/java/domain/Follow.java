
package domain;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "followed_id"}))
public class Follow extends DomainEntity {

	//Relationships
	private User follower;
	private User followed;

	private Date since;

	//Constructor
	public Follow() {
		super();
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getFollower() {
		return this.follower;
	}

	public void setFollower(final User follower) {
		this.follower = follower;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getFollowed() {
		return this.followed;
	}

	public void setFollowed(final User followed) {
		this.followed = followed;
	}

	public Date getSince() {
		return since;
	}

	public void setSince(Date since) {
		this.since = since;
	}
}
