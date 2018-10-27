
package controllers.Actor;

import controllers.AbstractController;
import domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CommentService;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {

    // Supporting services -----------------------------------------------------

    @Autowired
    private CommentService commentService
            ;

    // Constructors -----------------------------------------------------------

    public CommentActorController() {
        super();
    }



    // Display user -----------------------------------------------------------
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ModelAndView display(@RequestParam final int commentId) {
        ModelAndView result;

        try {
            final Comment comment = this.commentService.findOne(commentId);
            Assert.notNull(comment, "msg.not.found.resource");
            result = new ModelAndView("comment/display");
            result.addObject("comment", comment);
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
        final Comment comment = commentService.create();
        result = this.createEditModelAndView(comment);
        return result;
    }

    // Edit  -----------------------------------------------------------
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(@RequestParam final int showroomId) {
        ModelAndView result;

        try {
            final Comment comment = this.commentService.findOne(showroomId);
            Assert.notNull(comment, "msg.not.found.resource");
            result = new ModelAndView("comment/edit");
            result.addObject("comment", comment);
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
    public ModelAndView save(@Valid final Comment comment, final BindingResult binding) {
        ModelAndView result;
        if (binding.hasErrors())
            result = this.createEditModelAndView(comment);
        else
            try {
                this.commentService.save(comment);
                result = new ModelAndView("redirect:/showroom/list.do");
            } catch (final Throwable oops) {
                if (oops.getCause().getCause() != null
                        && oops.getCause().getCause().getMessage().startsWith("Duplicate"))
                    result = this.createEditModelAndView(comment, "msg.duplicate.username");
                else
                    result = this.createEditModelAndView(comment, "msg.commit.error");
            }
        return result;
    }

    // Auxiliary methods -----------------------------------------------------
    protected ModelAndView createEditModelAndView(final Comment model) {
        final ModelAndView result;
        result = this.createEditModelAndView(model, null);
        return result;
    }

    protected ModelAndView createEditModelAndView(final Comment model, final String message) {
        final ModelAndView result;
        result = new ModelAndView("comment/create");
        result.addObject("comment", model);
        result.addObject("requestUri", "comment/create.do");
        result.addObject("edition", true);
        result.addObject("creation", model.getId() == 0);
        result.addObject("message", message);

        return result;

    }

}
