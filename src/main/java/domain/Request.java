
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "item_id"})})
public class Request extends DomainEntity {
    /*  For every request, the system must store the
        moment when the request is made; if the corresponding item is free, then no credit card
        must be included in the request; otherwise a valid credit card that must not expire in less
        than 30 days must be included.
    */
    //Relationships
    private User user;
    private Item item;
    private Date moment;
    private String status;
    private CreditCard creditCard;

    //Constructor
    public Request() {
        super();
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    public Date getMoment() {
        return this.moment;
    }

    public void setMoment(final Date moment) {
        this.moment = moment;
    }

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @NotNull
    @Valid
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Valid
    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    @NotBlank
    @SafeHtml
    @Pattern(regexp = "^(PENDING|ACCEPTED|REJECTED)$")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}