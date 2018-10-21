
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Administrator;
import forms.ActorForm;
import forms.AdminForm;
import security.Authority;
import services.ActorService;
import services.AdministratorService;

@Controller
@RequestMapping("/admin")
public class AdminController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private ActorService actorService;
	@Autowired
	private AdministratorService adminService;

	// Constructors -----------------------------------------------------------

	public AdminController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final Integer pageSize) {
		ModelAndView result;

		final Collection<Administrator> administators = this.adminService.findAll();
		result = new ModelAndView("actor/list");
		result.addObject("administators", administators);
		result.addObject("requestUri", "admin/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Display user -----------------------------------------------------------
	@RequestMapping(value = "/display")
	public ModelAndView display(@RequestParam final int actorId, final Integer pageSize, final Integer word) {
		ModelAndView result;

		final Actor actor;
		actor = this.actorService.findOneIfActive(actorId);
		result = new ModelAndView("administators/display");
		result.addObject("adminForm", actor);
		result.addObject("display", true);
		result.addObject("modelName", "adminForm");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Create ---------------------------------------------------------------

	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		final AdminForm registerForm = new AdminForm();
		result = this.createEditModelAndView(registerForm, null);
		return result;
	}

	// Edit ---------------------------------------------------------------

	@RequestMapping("/edit")
	public ModelAndView edit() {
		ModelAndView result;
		ActorForm model;

		try {
			final Actor actor = this.actorService.findByPrincipal();
			model = new AdminForm(actor);
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
	public ModelAndView save(final AdminForm adminForm, final BindingResult binding) {
		ModelAndView result;
		Administrator actor;

		try {
			actor =  this.adminService.reconstruct(adminForm, binding);
			if (binding.hasErrors())
				result = this.createEditModelAndView(adminForm);
			else
				try {
					this.actorService.save(actor);
					result = new ModelAndView("redirect:/");
				} catch (final Throwable oops) {
					if (oops.getCause().getCause() != null
							&& oops.getCause().getCause().getMessage().startsWith("Duplicate"))
						result = this.createEditModelAndView(adminForm, "msg.duplicate.username");
					else
						result = this.createEditModelAndView(adminForm, "msg.commit.error");
				}

		} catch (final Throwable oops) {
			if (oops.getLocalizedMessage().startsWith("msg."))
				result = this.createEditModelAndView(adminForm, oops.getLocalizedMessage());
			else if (oops.getCause().getCause() != null
					&& oops.getCause().getCause().getMessage().startsWith("Duplicate"))
				result = this.createEditModelAndView(adminForm, "msg.duplicate.username");
			else
				result = this.createEditModelAndView(adminForm, "actor.reconstruct.error");
		}

		return result;
	}

	// Auxiliary methods -----------------------------------------------------
	protected ModelAndView createEditModelAndView(final ActorForm model) {
		final ModelAndView result;
		result = this.createEditModelAndView(model, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final ActorForm model, final String message) {
		final ModelAndView result;
		final Collection<Authority> permisos = Authority.listAuthorities();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMINISTRATOR);
		permisos.remove(authority);
		result = new ModelAndView("actor/create");
		result.addObject("adminForm", model);
		result.addObject("modelName", "adminForm");
		result.addObject("requestUri", "admin/create.do");
		result.addObject("permisos", permisos);
		result.addObject("edition", true);
		result.addObject("creation", model.getId() == 0);
		result.addObject("message", message);

		return result;

	}

}
