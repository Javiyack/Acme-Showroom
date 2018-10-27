
package controllers.Actor;

import controllers.AbstractController;
import domain.Chirp;
import domain.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ChirpService;
import services.SubscriptionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/chirp/actor")
public class ChirpActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ChirpService chirpService;

    @Autowired
    private SubscriptionService subscriptionService;
    // Constructors -----------------------------------------------------------

    public ChirpActorController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;
        final Collection<Chirp> chirps;
        chirps = this.chirpService.findByLoggedActor();
        result = new ModelAndView("chirp/actor/list");
        result.addObject("chirps", chirps);
        result.addObject("requestUri", "chirp/actor/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    public ModelAndView stream(final Integer pageSize) {
        ModelAndView result;
        final Collection<Chirp> chirps = new ArrayList<Chirp>();
        final Collection<Subscription> follows = subscriptionService.findByLogedActor();
        for (Subscription followed: follows) {
            chirps.addAll(chirpService.findByUserId(followed.getSubscribedActor().getId()));
        }
        result = new ModelAndView("chirp/actor/stream");
        result.addObject("chirps", chirps);
        result.addObject("requestUri", "chirp/actor/stream.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int chirpId) {
        ModelAndView result;
        try {
            final Chirp chirp = this.chirpService.findOne(chirpId);
            Assert.notNull(chirp, "msg.not.found.resource");
            result = new ModelAndView("chirp/actor/create");
            result.addObject("chirp", chirp);
            result.addObject("display", true);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }

    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView result;
        final Chirp chirp = chirpService.create();
        result = this.createEditModelAndView(chirp);
        return result;
    }

    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(chirp);
        else
            try {
                this.chirpService.save(chirp);
                result = new ModelAndView("redirect:/chirp/actor/list.do");
            } catch (final Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/");
                } else {
                    return this.createMessageModelAndView("panic.message.text", "/");
                }
            }
        return result;
    }

    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final Chirp model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Chirp model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("chirp/actor/create");
        result.addObject("chirp", model);
        result.addObject("requestUri", "chirp/actor/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);
        return result;
    }

}
