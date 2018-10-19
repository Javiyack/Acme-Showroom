package services;

import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.Follow;
import domain.Agent;
import forms.AgentForm;
import forms.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowRepository;
import repositories.AgentRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;

import java.util.Collection;

@Service
@Transactional
public class AgentService {
    //Repositories
    @Autowired
    private AgentRepository agentRepository;
    //Services
    @Autowired
    private FollowService followService;
    @Autowired
    private ActorService actorService;
    @Autowired
	private UserAccountService userAccountService;

	@Autowired
	private Validator validator;
	
	//Constructor
    public AgentService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Agent create() {

        final Agent result = new Agent();


        return result;
    }

    public Agent save(Agent agent) {
        return agentRepository.save(agent);

    }

    public Collection<Agent> findAll() {
        return agentRepository.findAll();
    }

    public Collection<Agent> findAllActive() {
        return agentRepository.findAllActive();
    }



	public Agent reconstruct(AgentForm agentForm, BindingResult binding) {
		Agent logedActor = null;

		UserAccount useraccount = null;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (agentForm.getId() == 0) { // Si es nuevo usuario
			agentForm.getAccount().setNewPassword(agentForm.getAccount().getPassword());
			useraccount = new UserAccount();
			logedActor = new Agent();
			useraccount = this.userAccountService.create(agentForm.getAccount().getAuthority());
			logedActor.setUserAccount(useraccount);

			logedActor.getUserAccount().setUsername(agentForm.getAccount().getUsername());
			logedActor.getUserAccount().setPassword(agentForm.getAccount().getPassword());
			logedActor.setName(agentForm.getName());
			logedActor.setSurname(agentForm.getSurname());
			logedActor.setEmail(agentForm.getEmail());
			logedActor.setAddress(agentForm.getAddress());
			logedActor.setPhone(agentForm.getPhone());
			logedActor.setCompany(agentForm.getCompany());
			
			this.validator.validate(agentForm, binding);
			this.validator.validate(agentForm.getAccount(), binding);
			
			if (!binding.hasErrors()) {
				Assert.isTrue(agentForm.getAccount().getPassword().equals(agentForm.getAccount().getConfirmPassword()),
						"msg.userAccount.repeatPassword.mismatch");
				logedActor.getUserAccount()
						.setPassword(encoder.encodePassword(agentForm.getAccount().getPassword(), null));
				Assert.isTrue(agentForm.isAgree(), "msg.not.terms.agree.block");
			}
		} else {//Si no es nuevo usuario
			this.validator.validate(agentForm, binding);
			final String formPass = encoder.encodePassword(agentForm.getAccount().getPassword(), null);
			logedActor = (Agent) actorService.findByPrincipal();
			Assert.notNull(logedActor, "msg.not.logged.block");
			if (!binding.hasErrors()) {
				logedActor.setName(agentForm.getName());
				logedActor.setSurname(agentForm.getSurname());
				logedActor.setEmail(agentForm.getEmail());
				logedActor.setAddress(agentForm.getAddress());
				logedActor.setPhone(agentForm.getPhone());
				logedActor.setCompany(agentForm.getCompany());
				
			} 
			
			// Si ha cambiado algún parámetro del Authority (Usuario, password)
			if (!agentForm.getAccount().getUsername().equals(logedActor.getUserAccount().getUsername())) {// Si ha cambiado el nombre de Usuario.

				if (!agentForm.getAccount().getNewPassword().isEmpty()) {// Si hay nueva contraseña
					// Valida el la cuenta de usuario
					this.validator.validate(agentForm.getAccount(), binding);// Valida los datos de la cuenta
					Assert.isTrue(
							agentForm.getAccount().getNewPassword().equals(agentForm.getAccount().getConfirmPassword()),
							"msg.userAccount.repeatPassword.mismatch");// Comprueba que coinciden las passw
					
					// Comprueba la contraseña y la cambia si todo ha ido bien
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					Assert.isTrue(checkLength(agentForm.getAccount().getNewPassword()), "msg.password.length");
					// Cambia la contraseña
					logedActor.getUserAccount()
							.setPassword(encoder.encodePassword(agentForm.getAccount().getNewPassword(), null));
				} else {
					agentForm.setNewPassword(null);
					agentForm.getAccount().setConfirmPassword(null);
					// Valida el la cuenta de usuario
					this.validator.validate(agentForm.getAccount(), binding);
					// Comprueba la contraseña
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");

				}

				// Cambia el nombre de usuario
				logedActor.getUserAccount().setUsername(agentForm.getAccount().getUsername());

			} else {// Si NO ha cambiado el nombre de Usuario.
				if (!agentForm.getAccount().getPassword().isEmpty()) {// Si hay nueva contraseña
					if (!agentForm.getAccount().getNewPassword().isEmpty()) {
						// Valida el la cuenta de usuario
						this.validator.validate(agentForm, binding);
						Assert.isTrue(
								agentForm.getAccount().getNewPassword()
										.equals(agentForm.getAccount().getConfirmPassword()),
								"msg.userAccount.repeatPassword.mismatch");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
						Assert.isTrue(checkLength(agentForm.getAccount().getNewPassword()), "msg.password.length");
						logedActor.getUserAccount()
								.setPassword(encoder.encodePassword(agentForm.getAccount().getNewPassword(), null));
					} else {
						agentForm.getAccount().setNewPassword("XXXXX");
						agentForm.getAccount().setConfirmPassword("XXXXX");
						// Valida el la cuenta de usuario
						this.validator.validate(agentForm, binding);
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					}

				} else {// Si NO hay nueva contraseña
					// Como no ha cambiado ni usuario ni escrito contraseña seteamos temporalmente
					// el username y passwords para pasar la validacion de userAccount
					// Valida El formulario
					agentForm.getAccount().setPassword("XXXXX");
					agentForm.getAccount().setNewPassword("XXXXX");
					agentForm.getAccount().setConfirmPassword("XXXXX");
					this.validator.validate(agentForm.getAccount(), binding);
				}
			}
		}
		return logedActor;
	}
	
	private boolean checkLength(String newPassword) {
		return newPassword.length() > 4 && newPassword.length() < 33;
	}
}
