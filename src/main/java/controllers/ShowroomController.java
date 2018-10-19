
package controllers;

import domain.Item;
import domain.Showroom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ItemService;
import services.ShowroomService;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/showroom")
public class ShowroomController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ShowroomService showroomService;

    @Autowired
    private ItemService itemService;
    // Constructors -----------------------------------------------------------

    public ShowroomController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(final Integer pageSize) {
        ModelAndView result;
        final Collection<Showroom> showrooms;
        showrooms = this.showroomService.findAll();
        result = new ModelAndView("showroom/list");
        result.addObject("showrooms", showrooms);
        result.addObject("requestUri", "showroom/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }

    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int showroomId) {
        ModelAndView result;
        try {
            final Showroom showroom = this.showroomService.findOne(showroomId);
            Assert.notNull(showroom, "msg.not.found.resource");
            result = this.createDisplaytModelAndView(showroom);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/");
            }
        }
        return result;
    }


    protected ModelAndView createDisplaytModelAndView(final Showroom model) {
        final ModelAndView result;
        result = this.createDisplaytModelAndView(model, null);
        return result;
    }

    protected ModelAndView createDisplaytModelAndView(final Showroom model, final String message) {
        final ModelAndView result;
        Collection<Item> items =  itemService.findByShowroom(model);
        result = new ModelAndView("showroom/create");
        result.addObject("showroom", model);
        result.addObject("items", items);
        result.addObject("requestUri", "showroom/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);
        result.addObject("display", true);
        return result;

    }


}
