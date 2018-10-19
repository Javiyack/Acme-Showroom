
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import controllers.AbstractController;
import services.ActorService;

@Controller
@RequestMapping("/actor/administrator")
public class AgentAdministratorController extends AbstractController {

    // Supporting services -----------------------------------------------------
    @Autowired
    protected ActorService actorService;

    // Constructors -----------------------------------------------------------

    public AgentAdministratorController() {
        super();
    }

}
