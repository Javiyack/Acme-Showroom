
package forms;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import domain.Agent;

public class AgentForm extends ActorForm{

	private String 		company;		
	

	// Constructors -------------------------

	public AgentForm() {
		super();
	}

	public AgentForm(final Agent agent) {
		super(agent);
		this.setCompany(agent.getCompany());

	}


	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
