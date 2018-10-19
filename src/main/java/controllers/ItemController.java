
package controllers;

import domain.Constant;
import domain.Item;
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
import services.ActorService;
import services.ItemService;
import services.ShowroomService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ActorService actorService;
    // Constructors -----------------------------------------------------------

    public ItemController() {
        super();
    }
    // Display  -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int itemId) {
        ModelAndView result;
        try {
            final Item item = this.itemService.findOne(itemId);
            Assert.notNull(item, "msg.not.found.resource");
            result = this.createEditModelAndView(item);
            result.addObject("display", true);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        return result;
    }



    // Auxiliary methods -----------------------------------------------------


    protected ModelAndView createEditModelAndView(final Item model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Item model, final String message) {
        final ModelAndView result;
        Constant.difficultyLevels[] difficulties = Constant.difficultyLevels.values();
        List<String> difficultyLevels = new ArrayList<>();
        for (Constant.difficultyLevels level:difficulties) {
            difficultyLevels.add(level.toString());
        }
        result = new ModelAndView("item/edit");
        result.addObject("item", model);
        result.addObject("difficultyLevels", difficultyLevels);
        result.addObject("message", message);

        return result;

    }

}
