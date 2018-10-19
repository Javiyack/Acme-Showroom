
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
import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.validator.constraints.URL;

import domain.Actor;
import domain.User;

public class AdminForm extends ActorForm{


	// Constructors -------------------------

	public AdminForm() {
		super();
	}

	public AdminForm(final Actor actor) {
		super(actor);

	}

}
