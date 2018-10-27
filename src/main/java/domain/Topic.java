
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Topic extends DomainEntity {

    private String name;


    @NotBlank
    @SafeHtml
    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }


}
