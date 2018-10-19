
package controllers.User;

import controllers.AbstractController;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.FollowService;
import services.UserService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/follow/user")
public class FollowUserController extends AbstractController {

	// Supporting services -----------------------------------------------------

	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;


	// Constructors -----------------------------------------------------------

	public FollowUserController() {
		super();
	}

	// List Followeds -------------------------------------------------------
	@RequestMapping(value = "/followers", method = RequestMethod.GET)
	public ModelAndView followeds(final Integer pageSize) {
		ModelAndView result;
		final Collection<User> followers = this.userService.findFollowerUsers();

		result = new ModelAndView("actor/list");
		result.addObject("followers", followers);
		result.addObject("requestUri", "follow/user/followers.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}
	// List followers --------------------------------------------------------
	@RequestMapping(value = "/followeds", method = RequestMethod.GET)
	public ModelAndView followers(final Integer pageSize) {
		ModelAndView result;
		final Collection<User> users = this.userService.findFollowedUsers();
		Map<User,Boolean> userIsFollowedMap = new HashMap<>();
		for (User user:users) {
			userIsFollowedMap.put(user,true);
		}
		result = new ModelAndView("actor/list");
		result.addObject("userIsFollowedMap", userIsFollowedMap);
		result.addObject("requestUri", "follow/user/followeds.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Foolow  -----------------------------------------------------------
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public ModelAndView activation(@RequestParam final int userId) {
		ModelAndView result;
		try{
			this.followService.follow(userId);
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), "/user/list.do");
			} else {
				return this.createMessageModelAndView("panic.message.text", "/");
			}
		}
		result = new ModelAndView("redirect:/user/list.do");
		return result;
	}



}
