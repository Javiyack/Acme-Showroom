
package domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "name, description, origin, destination")})
public class Item extends DomainEntity {

    private String name;
    private String description;
    private String origin;
    private String destination;
    private String difficulty;//pattern

    private Double length;

    private Collection<String> pictures;

    // Relationships
    private Showroom showroom;
    // Relationships


    @NotBlank
    @SafeHtml
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotBlank
    @SafeHtml
    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    @NotBlank
    @SafeHtml
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    @SafeHtml
    @Pattern(regexp = "^(EASY|MEDIUM|DIFFICULT)$")
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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

    @Valid
    @NotNull
    @ManyToOne(optional = false)
    public Showroom getShowroom() {
        return showroom;
    }

    public void setShowroom(Showroom showroom) {
        this.showroom = showroom;
    }


    @NotBlank
    @SafeHtml
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
