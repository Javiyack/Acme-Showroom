
package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.User;
import domain.User;
import forms.UserForm;
import forms.UserForm;
import security.Authority;
import services.ActorService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private UserService userService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<User> users = this.userService.findAllActive();
		final Collection<User> followeds = this.userService.findFollowedUsers();
		Map<User,Boolean> userIsFollowedMap = new HashMap<>();
		for (User user:users) {
			userIsFollowedMap.put(user,followeds.contains(user));
		}
		result = new ModelAndView("actor/list");
		result.addObject("users", users);
		result.addObject("userIsFollowedMap", userIsFollowedMap);
		result.addObject("requestUri", "user/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Display user -----------------------------------------------------------
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int userId) {
			ModelAndView result;

			final User user = this.userService.findOne(userId);
			result = new ModelAndView("actor/display");
			result.addObject("userForm", user);
			result.addObject("display", true);
			return result;
		}
		
		 // Create ---------------------------------------------------------------

	    @RequestMapping("/create")
	    public ModelAndView create() {
	        ModelAndView result;
	        final UserForm registerForm = new UserForm();
	        result = this.createEditModelAndView(registerForm, null);
	        return result;
	    }

	    // Edit ---------------------------------------------------------------

	    @RequestMapping("/edit")
	    public ModelAndView edit() {
	        ModelAndView result;
	        UserForm model;

	        try {
	            final User user = (User) this.actorService.findByPrincipal();
	            model = new UserForm(user);
	            result = this.createEditModelAndView(model, null);
	        } catch (Throwable oops) {
	            if (oops.getMessage().startsWith("msg.")) {
	                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
	            } else {
	                return this.createMessageModelAndView("panic.message.text", "/");
	            }
	        }

	        return result;
	    }

	    // Save mediante Post ---------------------------------------------------

	    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	    public ModelAndView save(final UserForm userForm, final BindingResult binding) {
	        ModelAndView result;
	        User user;

	        try {
	            user = this.userService.reconstruct(userForm, binding);
	            if (binding.hasErrors())
	                result = this.createEditModelAndView(userForm);
	            else
	                try {
	                    this.userService.save(user);
	                    result = new ModelAndView("redirect:/");
	                } catch (final Throwable oops) {
	                    if (oops.getCause().getCause() != null
	                            && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
	                        result = this.createEditModelAndView(userForm, "msg.duplicate.username");
	                    else
	                        result = this.createEditModelAndView(userForm, "msg.commit.error");
	                }

	        } catch (final Throwable oops) {
	            if (oops.getLocalizedMessage().startsWith("msg."))
	                result = this.createEditModelAndView(userForm, oops.getLocalizedMessage());
	            else if (oops.getCause().getCause() != null
	                    && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
	                result = this.createEditModelAndView(userForm, "msg.duplicate.username");
	            else
	                result = this.createEditModelAndView(userForm, "user.reconstruct.error");
	        }

	        return result;
	    }
	    // Auxiliary methods -----------------------------------------------------
	    protected ModelAndView createEditModelAndView(final UserForm model) {
	        final ModelAndView result;
	        result = this.createEditModelAndView(model, null);
	        return result;
	    }

	    protected ModelAndView createEditModelAndView(final UserForm model, final String message) {
	        final ModelAndView result;
	        final Collection<Authority> permisos = Authority.listAuthorities();
	        final Authority authority = new Authority();
	        authority.setAuthority(Authority.ADMINISTRATOR);
	        permisos.remove(authority);
	        result = new ModelAndView("actor/create");
	        result.addObject("userForm", model);
	        result.addObject("requestUri", "actor/create.do");
	        result.addObject("permisos", permisos);
	        result.addObject("edition", true);
	        result.addObject("creation", model.getId() == 0);
	        result.addObject("message", message);

	        return result;

	    }


}
