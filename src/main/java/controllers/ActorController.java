
package controllers;

import domain.Actor;
import domain.Showroom;
import forms.ActorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import security.Authority;
import services.ActorService;
import services.ShowroomService;

import java.util.Collection;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ActorService actorService;
    @Autowired
    private ShowroomService showroomService;


    // Constructors -----------------------------------------------------------

    public ActorController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;

        final Collection<Actor> actors = this.actorService.findAll();

        result = new ModelAndView("actor/list");
        result.addObject("legend", "label.all.accounts");
        result.addObject("actors", actors);
        result.addObject("requestUri", "actor/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display")
    public ModelAndView display(@RequestParam final int actorId, final Integer pageSize, final Integer word) {
        ModelAndView result;

        final Actor actor;
        actor = this.actorService.findOneIfActive(actorId);
        Collection<Showroom> showrooms = showroomService.findByUserId(actorId);
        result = new ModelAndView("actor/display");
        result.addObject("actorForm", actor);
        result.addObject("modelName", "actorForm");
        result.addObject("display", true);
        result.addObject("showrooms", showrooms);
        result.addObject("display", true);
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }


    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        final ActorForm registerForm = new ActorForm();
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
            model = new ActorForm(actor);
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
    public ModelAndView save(final ActorForm actorForm, final BindingResult binding) {
        ModelAndView result;
        Actor actor;

        try {
            actor = this.actorService.reconstruct(actorForm, binding);
            if (binding.hasErrors())
                result = this.createEditModelAndView(actorForm);
            else
                try {
                    this.actorService.save(actor);
                    result = new ModelAndView("redirect:/");
                } catch (final Throwable oops) {
                    if (oops.getCause().getCause() != null
                            && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                        result = this.createEditModelAndView(actorForm, "msg.duplicate.username");
                    else
                        result = this.createEditModelAndView(actorForm, "msg.commit.error");
                }

        } catch (final Throwable oops) {
            if (oops.getLocalizedMessage().startsWith("msg."))
                result = this.createEditModelAndView(actorForm, oops.getLocalizedMessage());
            else if (oops.getCause().getCause() != null
                    && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                result = this.createEditModelAndView(actorForm, "msg.duplicate.username");
            else
                result = this.createEditModelAndView(actorForm, "actor.reconstruct.error");
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
        result.addObject("actorForm", model);
        result.addObject("requestUri", "actor/create.do");
        result.addObject("permisos", permisos);
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
