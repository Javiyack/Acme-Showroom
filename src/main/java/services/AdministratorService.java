
package services;

import java.util.Collection;
import java.util.Map;

import domain.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Administrator;
import domain.TabooWord;
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

            isSpam = !tabooWords.isEmpty();

		}

		return isSpam;
	}

	public Administrator reconstruct(final AdminForm adminForm, final BindingResult binding) {
		Administrator admin = null;
		Actor loggedActor = actorService.findByPrincipal();
		Assert.notNull(loggedActor, "msg.not.logged.block");
		Assert.isTrue(loggedActor instanceof Administrator, "msg.not.owned.block");

		this.validator.validate(adminForm, binding);
		admin = (Administrator) actorService.reconstruct(adminForm, binding);
		
		return admin;
	}

	public Double findAverageShowroomsPerUser() {
		return administratorRepository.findAverageShowroomsPerUser();
	}

	public Integer findMinimunShowroomsPerUser() {
		return administratorRepository.findMinimunShowroomsPerUser();
	}

	public Integer findMaximunShowroomsPerUser() {
		return administratorRepository.findMaximunShowroomsPerUser();
	}

	public Double findStdevShowroomsPerUser() {
		return administratorRepository.findStdevShowroomsPerUser();
	}

	public Double findAverageItemsPerUser() {
		return administratorRepository.findAverageItemsPerUser();
	}

	public Integer findMinimunItemsPerUser() {
		return administratorRepository.findMinimunItemsPerUser();
	}

	public Integer findMaximunItemsPerUser() {
		return administratorRepository.findMaximunItemsPerUser();
	}

	public Double findStdevItemsPerUser() {
		return administratorRepository.findStdevItemsPerUser();
	}

	public Double findAverageRequestsPerUser() {
		return administratorRepository.findAverageRequestsPerUser();
	}

	public Integer findMinimunRequestsPerUser() {
		return administratorRepository.findMinimunRequestsPerUser();
	}

	public Integer findMaximunRequestsPerUser() {
		return administratorRepository.findMaximunRequestsPerUser();
	}

	public Double findStdevRequestsPerUser() {
		return administratorRepository.findStdevRequestsPerUser();
	}

	public Double findAverageRejectedRequestsPerUser() {
		return administratorRepository.findAverageRejectedRequestsPerUser();
	}

	public Integer findMinimunRejectedRequestsPerUser() {
		return administratorRepository.findMinimunRejectedRequestsPerUser();
	}

	public Integer findMaximunRejectedRequestsPerUser() {
		return administratorRepository.findMaximunRejectedRequestsPerUser();
	}

	public Double findStdevRejectedRequestsPerUser() {
		return administratorRepository.findStdevRejectedRequestsPerUser();
	}

	public Double findAverageChirpsPerUser() {
		return administratorRepository.findAverageChirpsPerUser();
	}

	public Integer findMinimunChirpsPerUser() {
		return administratorRepository.findMinimunChirpsPerUser();
	}

	public Integer findMaximunChirpsPerUser() {
		return administratorRepository.findMaximunChirpsPerUser();
	}

	public Double findStdevChirpsPerUser() {
		return administratorRepository.findStdevChirpsPerUser();
	}


	public Double findAverageFollowersPerUser() {
		return administratorRepository.findAverageFollowersPerUser();
	}

	public Integer findMinimunFollowersPerUser() {
		return administratorRepository.findMinimunFollowersPerUser();
	}

	public Integer findMaximunFollowersPerUser() {
		return administratorRepository.findMaximunFollowersPerUser();
	}

	public Double findStdevFollowersPerUser() {
		return administratorRepository.findStdevFollowersPerUser();
	}
	
	public Double findAverageFollowedsPerUser() {
		return administratorRepository.findAverageFollowedsPerUser();
	}

	public Integer findMinimunFollowedsPerUser() {
		return administratorRepository.findMinimunFollowedsPerUser();
	}

	public Integer findMaximunFollowedsPerUser() {
		return administratorRepository.findMaximunFollowedsPerUser();
	}

	public Double findStdevFollowedsPerUser() {
		return administratorRepository.findStdevFollowedsPerUser();
	}

	public Collection<Object> findChirpsNumberPerTopic() {
		return administratorRepository.findChirpsNumberPerTopic();
	}


	public Double findAverageCommentsPerShowroom() {
		return administratorRepository.findAverageCommentsPerShowroom();
	}

	public Integer findMinimunCommentsPerShowroom() {
		return administratorRepository.findMinimunCommentsPerShowroom();
	}

	public Integer findMaximunCommentsPerShowroom() {
		return administratorRepository.findMaximunCommentsPerShowroom();
	}

	public Double findStdevCommentsPerShowroom() {
		return administratorRepository.findStdevCommentsPerShowroom();
	}


	public Double findAverageCommentsPerItem() {
		return administratorRepository.findAverageCommentsPerItem();
	}

	public Integer findMinimunCommentsPerItem() {
		return administratorRepository.findMinimunCommentsPerItem();
	}

	public Integer findMaximunCommentsPerItem() {
		return administratorRepository.findMaximunCommentsPerItem();
	}

	public Double findStdevCommentsPerItem() {
		return administratorRepository.findStdevCommentsPerItem();
	}


	public Double findAverageCommentsPerUser() {
		return administratorRepository.findAverageCommentsPerUser();
	}

	public Integer findMinimunCommentsPerUser() {
		return administratorRepository.findMinimunCommentsPerUser();
	}

	public Integer findMaximunCommentsPerUser() {
		return administratorRepository.findMaximunCommentsPerUser();
	}

	public Double findStdevCommentsPerUser() {
		return administratorRepository.findStdevCommentsPerUser();
	}


}
