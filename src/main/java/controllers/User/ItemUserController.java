
package controllers.User;

import controllers.AbstractController;
import domain.Constant;
import domain.Item;
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
@RequestMapping("/item/user")
public class ItemUserController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private ItemService itemService;
    @Autowired
    private ShowroomService showroomService;
    @Autowired
    private ActorService actorService;
    // Constructors -----------------------------------------------------------

    public ItemUserController() {
        super();
    }

    // List ------------------------------------------------------------------
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list( Integer showroomId,  Integer pageSize) {
        ModelAndView result;
         Collection<Item> showrooms;
        result = new ModelAndView("item/list");
        try {
            showrooms = this.itemService.findByShowroomId(showroomId);
        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + showroomId);
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        result.addObject("showrooms", showrooms);
        result.addObject("requestUri", "showroom/user/list.do");
        result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
        return result;
    }


    // Create ---------------------------------------------------------------

    @RequestMapping("/create")
    public ModelAndView create(int showroomId) {
        ModelAndView result;
         Item item = itemService.create(showroomService.findOne(showroomId));
        result = this.createEditModelAndView(item);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam  int itemId) {
        ModelAndView result;

        try {
             Item item = this.itemService.findOne(itemId);
            Assert.notNull(item, "msg.not.found.resource");
            Assert.notNull(item.getShowroom().getUser().equals(actorService.findByPrincipal()), "msg.not.owned.block");
            result = this.createEditModelAndView(item);
            result.addObject("display", false);

        } catch (Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/list.do");
            } else {
                return this.createMessageModelAndView("panic.message.text", "/showroom/list.do");
            }
        }
        return result;
    }


    // Save mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@Valid Item item, BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(item);
        else
            try {
                item = this.itemService.save(item);
                result = new ModelAndView("redirect:/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
            } catch ( Throwable oops) {
                if (oops.getMessage().startsWith("msg.")) {
                    return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
                } else {
                    return this.createMessageModelAndView("msg.commit.error", "/showroom/user/edit.do?showroomId=" + item.getShowroom().getId());
                }
            }
        return result;
    }
// Delete mediante Post ---------------------------------------------------

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "delete")
    public ModelAndView delete(@Valid  Item item) {
        ModelAndView result = erase(item.getId(), item.getShowroom().getId());
        return result;
    }

    //  Delete mediante GET  -----------------------------------------------------------
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView remove(@RequestParam  int itemId,  int showroomId) {
        ModelAndView result = erase(itemId, showroomId);
        return result;
    }

    // Auxiliary methods -----------------------------------------------------

    protected ModelAndView erase( Integer itemId,  Integer showroomId) {
        ModelAndView result;
        try {
            this.itemService.delete(itemId);
            result = new ModelAndView("redirect:/showroom/user/edit.do?showroomId=" + showroomId);
        } catch ( Throwable oops) {
            if (oops.getMessage().startsWith("msg.")) {
                return createMessageModelAndView(oops.getLocalizedMessage(), "/showroom/user/edit.do?showroomId=" + showroomId);
            } else {
                return this.createMessageModelAndView("msg.commit.error", "/showroom/user/edit.do?showroomId=" + showroomId);
            }
        }
        return result;
    }


    protected ModelAndView createEditModelAndView( Item model) {
         ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView( Item model,  String message) {
         ModelAndView result;
        Constant.difficultyLevels[] difficulties = Constant.difficultyLevels.values();
        List<String> difficultyLevels = new ArrayList<>();

        for (Constant.difficultyLevels level:difficulties) {
            difficultyLevels.add(level.toString());
        }
        result = new ModelAndView("item/create");
        result.addObject("item", model);
        result.addObject("difficultyLevels", difficultyLevels);
        result.addObject("requestUri", "item/user/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
