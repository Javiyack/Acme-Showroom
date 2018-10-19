
package controllers.administrator;

import controllers.AbstractController;
import domain.Constant.currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;
import services.ConfigurationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {


	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {

		ModelAndView result= new ModelAndView("dashboard/display");
		return result;
	}

}
