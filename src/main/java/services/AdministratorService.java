
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Administrator;
import domain.Agent;
import domain.TabooWord;
import forms.ActorForm;
import forms.AdminForm;
import repositories.AdministratorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class AdministratorService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private TabooWordService tabooWordService;
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private Validator validator;
	

	// Constructor ----------------------------------------------------------
	public AdministratorService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Administrator> findAll() {

		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findByPrincipal() {
		Administrator result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = (Administrator) this.actorService.findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}

	public Administrator create() {
		return new Administrator();

	}

	public void flush() {
		this.administratorRepository.flush();

	}

	public boolean checkIsSpam(final String subject, final String body) {

		Boolean isSpam;

		if (subject.isEmpty() && body.isEmpty())
			isSpam = false;
		else {

			Collection<TabooWord> tabooWords;
			tabooWords = this.tabooWordService.getTabooWordFromMyMessageSubjectAndBody(subject, body);

			if (tabooWords.isEmpty())
				isSpam = false;
			else
				isSpam = true;

		}

		return isSpam;
	}

	public Administrator reconstruct(final AdminForm adminForm, final BindingResult binding) {
		Administrator admin = null;

		this.validator.validate(adminForm, binding);
		admin = (Administrator) actorService.reconstruct((ActorForm) adminForm, binding);
		
		return admin;
	}

}
