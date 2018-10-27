
package controllers.Actor;

import controllers.AbstractController;
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
import services.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/actor/actor")
public class ActorActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ActorService actorService;
    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private UserService userService;


    // Constructors -----------------------------------------------------------

    public ActorActorController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;

        final Collection<Actor> users = this.actorService.findAll();
        final Collection<Actor> actorSubscriptions = this.userService.findActorSubscriptions();
        Map<Actor,Boolean> userIsFollowedMap = new HashMap<>();
        for (Actor user:users) {
            userIsFollowedMap.put(user, actorSubscriptions.contains(user));
        }
        result = new ModelAndView("actor/list");
        result.addObject("legend", "label.actors");
        result.addObject("actors", users);
        result.addObject("userIsFollowedMap", userIsFollowedMap);
        result.addObject("requestUri", "user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }



}
