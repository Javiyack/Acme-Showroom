
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import domain.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.User;
import forms.ActorForm;
import forms.AgentForm;
import forms.UserForm;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;

@Service
@Transactional
public class ActorService {

	// Managed repository -----------------------------------------------------
	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ----------------------------------------------------
	@Autowired
	private UserAccountService userAccountService;
	@Autowired
	private UserService userService;
	@Autowired
	private AgentService agentService;

	@Autowired
	private Validator validator;

	@Autowired
	private FolderService folderService;
	@Autowired
	private ConfigurationService configurationService;

	// Constructors -----------------------------------------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor findOneIfActive(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOneIfActive(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		Actor result;

		if (actor.getId() == 0) {

		} else {
			Assert.isTrue(actor.equals(this.findByPrincipal()), "not.allowed.action");
		}
		result = this.actorRepository.save(actor);
		this.flush();
		if (actor.getId() == 0) {
			this.folderService.createSystemFolders(result);
		}

		return result;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);
		Assert.isTrue(actor.getId() != 0);
		Assert.isTrue(this.actorRepository.exists(actor.getId()));
		Assert.isTrue(actor.equals(this.findByPrincipal()));

		this.actorRepository.delete(actor);
	}

	// Other business methods -------------------------------------------------

	public UserAccount findUserAccount(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = actor.getUserAccount();

		return result;
	}

	public Actor findByPrincipal() {
		Actor result = null;
		UserAccount userAccount;

		try {
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount, "msg.not.logged.block");
			result = this.findByUserAccount(userAccount);
			Assert.notNull(result, "msg.not.logged.block");
		} catch (final Throwable oops) {
		}

		return result;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public String getType(final UserAccount userAccount) {

		final List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	public Actor reconstruct(final ActorForm actorForm, final BindingResult binding) {
		Actor logedActor = null;
		switch (actorForm.getAccount().getAuthority()) {
		case Authority.ADMINISTRATOR:
			logedActor = this.reconstructAdmin(actorForm, binding);
			break;
		case Authority.USER:
			logedActor = userService.reconstruct((UserForm) actorForm, binding);
			break;
		case Authority.AGENT:
			logedActor = agentService.reconstruct((AgentForm) actorForm, binding);
			break;
		default:
			break;
		}
		return logedActor;
	}
	public Actor reconstructAdmin(final ActorForm actorForm, final BindingResult binding) {
		Actor logedActor = null;

		UserAccount useraccount = null;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (actorForm.getId() == 0) {
			actorForm.getAccount().setNewPassword(actorForm.getAccount().getPassword());
			useraccount = new UserAccount();
			logedActor = new Administrator();
			
			useraccount = this.userAccountService.create(actorForm.getAccount().getAuthority());
			logedActor.setUserAccount(useraccount);

			logedActor.getUserAccount().setUsername(actorForm.getAccount().getUsername());
			logedActor.getUserAccount().setPassword(actorForm.getAccount().getPassword());
			logedActor.setName(actorForm.getName());
			logedActor.setSurname(actorForm.getSurname());
			logedActor.setEmail(actorForm.getEmail());
			logedActor.setAddress(actorForm.getAddress());
			logedActor.setPhone(actorForm.getPhone());
			this.validator.validate(actorForm, binding);
			this.validator.validate(actorForm.getAccount(), binding);
			if (!binding.hasErrors()) {
				Assert.isTrue(actorForm.getAccount().getPassword().equals(actorForm.getAccount().getConfirmPassword()),
						"msg.userAccount.repeatPassword.mismatch");
				logedActor.getUserAccount()
						.setPassword(encoder.encodePassword(actorForm.getAccount().getPassword(), null));
				Assert.isTrue(actorForm.isAgree(), "msg.not.terms.agree.block");
			}
		} else {
			this.validator.validate(actorForm, binding);
			final String formPass = encoder.encodePassword(actorForm.getAccount().getPassword(), null);
			logedActor = this.findByPrincipal();
			Assert.notNull(logedActor, "msg.not.logged.block");
			if (!binding.hasErrors()) {
				logedActor.setName(actorForm.getName());
				logedActor.setSurname(actorForm.getSurname());
				logedActor.setEmail(actorForm.getEmail());
				logedActor.setAddress(actorForm.getAddress());
				logedActor.setPhone(actorForm.getPhone());
			} // Si ha cambiado algún parámetro del Authority (Usuario, password)
			if (!actorForm.getAccount().getUsername().equals(logedActor.getUserAccount().getUsername())) {

				if (!actorForm.getAccount().getNewPassword().isEmpty()) {
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm.getAccount(), binding);
					Assert.isTrue(
							actorForm.getAccount().getNewPassword().equals(actorForm.getAccount().getConfirmPassword()),
							"msg.userAccount.repeatPassword.mismatch");
					// Cambia la contraseña
					// Comprueba la contraseña y la cambia si todo ha ido bien
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
					logedActor.getUserAccount()
							.setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
				} else {
					actorForm.setNewPassword(null);
					actorForm.getAccount().setConfirmPassword(null);
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm.getAccount(), binding);
					// Comprueba la contraseña
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");

				}

				// Cambia el nombre de usuario
				logedActor.getUserAccount().setUsername(actorForm.getAccount().getUsername());

			} else {
				if (!actorForm.getAccount().getPassword().isEmpty()) {
					if (!actorForm.getAccount().getNewPassword().isEmpty()) {
						// Valida el la cuenta de usuario
						this.validator.validate(actorForm, binding);
						Assert.isTrue(
								actorForm.getAccount().getNewPassword()
										.equals(actorForm.getAccount().getConfirmPassword()),
								"msg.userAccount.repeatPassword.mismatch");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
						Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
						logedActor.getUserAccount()
								.setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
					} else {
						actorForm.getAccount().setNewPassword("XXXXX");
						actorForm.getAccount().setConfirmPassword("XXXXX");
						// Valida el la cuenta de usuario
						this.validator.validate(actorForm, binding);
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					}

				} else {
					// Como no ha cambiado ni usuario ni escrito contraseña seteamos temporalmente
					// el username y passwords para pasar la validacion de userAccount
					// Valida El formulario
					actorForm.getAccount().setPassword("XXXXX");
					actorForm.getAccount().setNewPassword("XXXXX");
					actorForm.getAccount().setConfirmPassword("XXXXX");
					this.validator.validate(actorForm.getAccount(), binding);
				}
			}
		}
		return logedActor;
	}

	private boolean checkLength(String newPassword) {
		return newPassword.length() > 4 && newPassword.length() < 33;
	}

	public void flush() {
		this.actorRepository.flush();

	}
}
