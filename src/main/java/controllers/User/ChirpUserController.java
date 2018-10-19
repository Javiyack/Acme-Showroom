
package controllers.User;

import controllers.AbstractController;
import domain.Chirp;
import domain.Follow;
import domain.Showroom;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ChirpService;
import services.FollowService;
import services.ShowroomService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/chirp/user")
public class ChirpUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ChirpService chirpService;

    @Autowired
    private FollowService followService;
    // Constructors -----------------------------------------------------------

    public ChirpUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;
        final Collection<Chirp> chirps;
        chirps = this.chirpService.findByLoggedUser();
        result = new ModelAndView("chirp/user/list");
        result.addObject("chirps", chirps);
        result.addObject("requestUri", "chirp/user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/stream", method = RequestMethod.GET)
    public ModelAndView stream(final Integer pageSize) {
        ModelAndView result;
        final Collection<Chirp> chirps = new ArrayList<Chirp>();
        final Collection<Follow> follows = followService.findFolloweds();
        for (Follow followed: follows) {
            chirps.addAll(chirpService.findByUserId(followed.getFollowed().getId()));
        }
        result = new ModelAndView("chirp/user/stream");
        result.addObject("chirps", chirps);
        result.addObject("requestUri", "chirp/user/stream.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int showroomId) {
        ModelAndView result;
        try {
            final Chirp chirp = this.chirpService.findOne(showroomId);
            Assert.notNull(chirp, "msg.not.found.resource");
            result = new ModelAndView("chirp/user/display");
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

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int chirpId) {
        ModelAndView result;

        try {
            final Chirp chirp = this.chirpService.findOne(chirpId);
            Assert.notNull(chirp, "msg.not.found.resource");
            result = new ModelAndView("chirp/user/edit");
            result.addObject("chirp", chirp);
            result.addObject("display", false);
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
    public ModelAndView save(@Valid final Chirp chirp, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(chirp);
        else
            try {
                this.chirpService.save(chirp);
                result = new ModelAndView("redirect:/chirp/user/list.do");
            } catch (final Throwable oops) {
                if (oops.getCause().getCause() != null
                        && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                    result = this.createEditModelAndView(chirp, "msg.duplicate.username");
                else
                    result = this.createEditModelAndView(chirp, "msg.commit.error");
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
        result = new ModelAndView("chirp/user/create");
        result.addObject("chirp", model);
        result.addObject("requestUri", "chirp/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);
        return result;
    }

}
