
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.User;
import forms.ActorForm;
import forms.AdminForm;
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
	private AdministratorService adminService;

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

	public Actor reconstruct(ActorForm actorForm, BindingResult binding) {
		Actor actor = null;

		UserAccount useraccount = null;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (actorForm.getId() == 0) {
			this.validator.validate(actorForm.getAccount(), binding);

			actorForm.getAccount().setNewPassword(actorForm.getAccount().getPassword());
			useraccount = new UserAccount();
			if (actorForm instanceof UserForm) {
				actor = new User();
				
			} else if (actorForm instanceof AgentForm) {
				actor = new Agent();				
			}
			else {
				actor = new Administrator();
			}
			useraccount = this.userAccountService.create(actorForm.getAccount().getAuthority());
			actor.setUserAccount(useraccount);

			actor.getUserAccount().setUsername(actorForm.getAccount().getUsername());
			actor.getUserAccount().setPassword(actorForm.getAccount().getPassword());
			actor.setName(actorForm.getName());
			actor.setSurname(actorForm.getSurname());
			actor.setEmail(actorForm.getEmail());
			actor.setPhone(actorForm.getPhone());

			Assert.isTrue(actorForm.getAccount().getPassword().equals(actorForm.getAccount().getConfirmPassword()),
					"msg.userAccount.repeatPassword.mismatch");
			actor.getUserAccount().setPassword(encoder.encodePassword(actorForm.getAccount().getPassword(), null));
			Assert.isTrue(actorForm.isAgree(), "msg.not.terms.agree.block");

		} else {
			final String formPass = encoder.encodePassword(actorForm.getAccount().getPassword(), null);
			actor = (User) this.findByPrincipal();
			Assert.notNull(actor, "msg.not.logged.block");
			if (!binding.hasErrors()) {
				actor.setName(actorForm.getName());
				actor.setSurname(actorForm.getSurname());
				actor.setEmail(actorForm.getEmail());
				actor.setPhone(actorForm.getPhone());

			}
			// Si ha cambiado algún parámetro del Authority (Usuario, password)
			// Si ha cambiado el nombre de usuario
			if (!actorForm.getAccount().getUsername().equals(actor.getUserAccount().getUsername())) {
				if (!actorForm.getAccount().getNewPassword().isEmpty()) {
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm.getAccount(), binding);
					Assert.isTrue(
							actorForm.getAccount().getNewPassword().equals(actorForm.getAccount().getConfirmPassword()),
							"msg.userAccount.repeatPassword.mismatch");
					// Cambia la contraseña
					// Comprueba la contraseña y la cambia si todo ha ido bien
					Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
					Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
					actor.getUserAccount()
							.setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
				} else {
					actorForm.setNewPassword(null);
					actorForm.getAccount().setConfirmPassword(null);
					// Valida el la cuenta de usuario
					this.validator.validate(actorForm.getAccount(), binding);
					// Comprueba la contraseña
					Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");

				}

				// Cambia el nombre de usuario
				actor.getUserAccount().setUsername(actorForm.getAccount().getUsername());

			} else { // Si NO ha cambiado el nombre se usuario
				if (!actorForm.getAccount().getPassword().isEmpty()) {
					if (!actorForm.getAccount().getNewPassword().isEmpty()) {
						Assert.isTrue(
								actorForm.getAccount().getNewPassword()
										.equals(actorForm.getAccount().getConfirmPassword()),
								"msg.userAccount.repeatPassword.mismatch");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
						Assert.isTrue(checkLength(actorForm.getAccount().getNewPassword()), "msg.password.length");
						actor.getUserAccount()
								.setPassword(encoder.encodePassword(actorForm.getAccount().getNewPassword(), null));
					} else {
						actorForm.getAccount().setNewPassword("XXXXX");
						actorForm.getAccount().setConfirmPassword("XXXXX");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(actor.getUserAccount().getPassword()), "msg.wrong.password");
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
		return actor;
	}

	private boolean checkLength(String newPassword) {
		return newPassword.length() > 4 && newPassword.length() < 33;
	}

	public String getType(final UserAccount userAccount) {

		final List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	public void flush() {
		this.actorRepository.flush();

	}

	public Collection<Administrator> findAllAdministrators() {
		return adminService.findAll();
	}
}
