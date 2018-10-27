
package controllers;

import domain.Administrator;
import forms.ActorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import security.Authority;
import services.ActorService;
import services.AdministratorService;

import java.util.Collection;

@Controller
@RequestMapping("/administrator")
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

        final Collection <Administrator> administators = this.adminService.findAll();
        result = new ModelAndView("actor/list");
        result.addObject("legend", "label.administrators");
        result.addObject("actors", administators);
        result.addObject("requestUri", "administators/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Edit ---------------------------------------------------------------

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("redirect:/admin/administrator/edit.do");
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
        result = new ModelAndView("actor/create");
        result.addObject("actorForm", model);
        result.addObject("actorAuthority", Authority.ADMINISTRATOR);
        result.addObject("requestUri", "actor/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
