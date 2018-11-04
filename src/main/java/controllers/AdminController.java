
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


    // Edit ---------------------------------------------------------------

    @RequestMapping("/edit")
    public ModelAndView edit() {
        ModelAndView result = new ModelAndView("redirect:/admin/administrator/edit.do");
        return result;
    }
}
