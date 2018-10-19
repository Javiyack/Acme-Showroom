
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "title, description")})
public class Chirp extends DomainEntity {

    private String title;
    private String description;
    private Date moment;
    // Relationships
    private User user;

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
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String body) {
        this.description = body;
    }

    @NotBlank
    @SafeHtml
    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @ManyToOne(optional = true)
    @Valid
    public User getUser() {
        return user;
    }

    public void setUser(User showroom) {
        this.user = showroom;
    }


}
