package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.User;
import forms.ActorForm;
import forms.UserForm;
import repositories.UserRepository;
import security.UserAccountService;

@Service
@Transactional
public class UserService {
	// Repositories
	@Autowired
	private UserRepository userRepository;
	// Services
	@Autowired
	private FollowService followService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private Validator validator;

	// Constructor
	public UserService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	// Create
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
		User user = null;
		this.validator.validate(userForm, binding);
		user = (User) actorService.reconstruct((ActorForm) userForm, binding);
		if (!binding.hasErrors()) {
			
			user.setPhoto(userForm.getPhoto());
			user.setBirthdate(userForm.getBirthdate());
			user.setGenere(userForm.getGenere());

		}
		return user;
	}

}
