
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "name, description")})
public class Showroom extends DomainEntity {


    private String name;
    private String description;

    private Double length;      // En Km. tres decimales

    private Collection<String> pictures;

    // Relationships
    private User user;
    // Relationships


    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @NotBlank
    @SafeHtml
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @SafeHtml(whitelistType = WhiteListType.NONE)
    @NotBlank
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @NotNull
    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @NotNull
    @ElementCollection
    public Collection<String> getPictures() {
        return pictures;
    }

    public void setPictures(Collection<String> pictures) {
        this.pictures = pictures;
    }


}
