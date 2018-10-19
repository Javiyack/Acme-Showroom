package services;

import domain.Actor;
import domain.Administrator;
import domain.Agent;
import domain.Follow;
import domain.User;
import forms.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.FollowRepository;
import repositories.UserRepository;
import security.Authority;
import security.UserAccount;
import security.UserAccountService;

import java.util.Collection;

@Service
@Transactional
public class UserService {
    //Repositories
    @Autowired
    private UserRepository userRepository;
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
    public UserService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public User create() {

        final User result = new User();


        return result;
    }

    public User save(User user) {
        return userRepository.save(user);

    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public Collection<User> findAllActive() {
        return userRepository.findAllActive();
    }



    public User findOne(int userId) {
        return userRepository.findOne(userId);
    }

    public Collection<User> findFollowedUsers() {
        return followService.findFollowedUsers();
    }

    public Collection<User> findFolloweds() {
        return followService.findFollowerUsers();
    }


    public Collection<User> findFollowerUsers() {
        return followService.findFollowerUsers();
    }

    public Collection<User> findFollowers() {
        return followService.findFollowerUsers();
    }

	public User reconstruct(UserForm userForm, BindingResult binding) {
		User logedActor = null;

		UserAccount useraccount = null;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (userForm.getId() == 0) {
			userForm.getAccount().setNewPassword(userForm.getAccount().getPassword());
			useraccount = new UserAccount();
			logedActor = new User();
			useraccount = this.userAccountService.create(userForm.getAccount().getAuthority());
			logedActor.setUserAccount(useraccount);

			logedActor.getUserAccount().setUsername(userForm.getAccount().getUsername());
			logedActor.getUserAccount().setPassword(userForm.getAccount().getPassword());
			logedActor.setName(userForm.getName());
			logedActor.setSurname(userForm.getSurname());
			logedActor.setEmail(userForm.getEmail());
			logedActor.setAddress(userForm.getAddress());
			logedActor.setPhone(userForm.getPhone());
			logedActor.setPhoto(userForm.getPhoto());
			logedActor.setBirthdate(userForm.getBirthdate());
			logedActor.setGenere(userForm.getGenere());
			
			this.validator.validate(userForm, binding);
			this.validator.validate(userForm.getAccount(), binding);
			
			if (!binding.hasErrors()) {
				Assert.isTrue(userForm.getAccount().getPassword().equals(userForm.getAccount().getConfirmPassword()),
						"msg.userAccount.repeatPassword.mismatch");
				logedActor.getUserAccount()
						.setPassword(encoder.encodePassword(userForm.getAccount().getPassword(), null));
				Assert.isTrue(userForm.isAgree(), "msg.not.terms.agree.block");
			}
		} else {
			this.validator.validate(userForm, binding);
			final String formPass = encoder.encodePassword(userForm.getAccount().getPassword(), null);
			logedActor = (User) actorService.findByPrincipal();
			Assert.notNull(logedActor, "msg.not.logged.block");
			if (!binding.hasErrors()) {
				logedActor.setName(userForm.getName());
				logedActor.setSurname(userForm.getSurname());
				logedActor.setEmail(userForm.getEmail());
				logedActor.setAddress(userForm.getAddress());
				logedActor.setPhone(userForm.getPhone());
				logedActor.setPhoto(userForm.getPhoto());
				logedActor.setBirthdate(userForm.getBirthdate());
				logedActor.setGenere(userForm.getGenere());
				
			} // Si ha cambiado algún parámetro del Authority (Usuario, password)
			if (!userForm.getAccount().getUsername().equals(logedActor.getUserAccount().getUsername())) {

				if (!userForm.getAccount().getNewPassword().isEmpty()) {
					// Valida el la cuenta de usuario
					this.validator.validate(userForm.getAccount(), binding);
					Assert.isTrue(
							userForm.getAccount().getNewPassword().equals(userForm.getAccount().getConfirmPassword()),
							"msg.userAccount.repeatPassword.mismatch");
					// Cambia la contraseña
					// Comprueba la contraseña y la cambia si todo ha ido bien
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					Assert.isTrue(checkLength(userForm.getAccount().getNewPassword()), "msg.password.length");
					logedActor.getUserAccount()
							.setPassword(encoder.encodePassword(userForm.getAccount().getNewPassword(), null));
				} else {
					userForm.setNewPassword(null);
					userForm.getAccount().setConfirmPassword(null);
					// Valida el la cuenta de usuario
					this.validator.validate(userForm.getAccount(), binding);
					// Comprueba la contraseña
					Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");

				}

				// Cambia el nombre de usuario
				logedActor.getUserAccount().setUsername(userForm.getAccount().getUsername());

			} else {
				if (!userForm.getAccount().getPassword().isEmpty()) {
					if (!userForm.getAccount().getNewPassword().isEmpty()) {
						// Valida el la cuenta de usuario
						this.validator.validate(userForm, binding);
						Assert.isTrue(
								userForm.getAccount().getNewPassword()
										.equals(userForm.getAccount().getConfirmPassword()),
								"msg.userAccount.repeatPassword.mismatch");
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
						Assert.isTrue(checkLength(userForm.getAccount().getNewPassword()), "msg.password.length");
						logedActor.getUserAccount()
								.setPassword(encoder.encodePassword(userForm.getAccount().getNewPassword(), null));
					} else {
						userForm.getAccount().setNewPassword("XXXXX");
						userForm.getAccount().setConfirmPassword("XXXXX");
						// Valida el la cuenta de usuario
						this.validator.validate(userForm, binding);
						// Comprueba la contraseña
						Assert.isTrue(formPass.equals(logedActor.getUserAccount().getPassword()), "msg.wrong.password");
					}

				} else {
					// Como no ha cambiado ni usuario ni escrito contraseña seteamos temporalmente
					// el username y passwords para pasar la validacion de userAccount
					// Valida El formulario
					userForm.getAccount().setPassword("XXXXX");
					userForm.getAccount().setNewPassword("XXXXX");
					userForm.getAccount().setConfirmPassword("XXXXX");
					this.validator.validate(userForm.getAccount(), binding);
				}
			}
		}
		return logedActor;
	}
	
	private boolean checkLength(String newPassword) {
		return newPassword.length() > 4 && newPassword.length() < 33;
	}
}
