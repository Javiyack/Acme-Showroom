
package controllers.Actor;

import controllers.AbstractController;
import domain.Actor;
import domain.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.SubscriptionService;

import java.util.Collection;

@Controller
@RequestMapping("/subscription/actor")
public class SubscriptionActorController extends AbstractController {

	// Supporting services -----------------------------------------------------
	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private ActorService actorService;


	// Constructors -----------------------------------------------------------

	public SubscriptionActorController() {
		super();
	}

	// List Subscribers -------------------------------------------------------
	@RequestMapping(value = "/subscribers/list", method = RequestMethod.GET)
	public ModelAndView followeds(final Integer pageSize) {
		ModelAndView result;
		final Collection<Actor> followers = this.actorService.findSubscribers();

		result = new ModelAndView("actor/list");
		result.addObject("followers", followers);
		result.addObject("requestUri", "subscription/subscriptors/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}
	// List Subcription --------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView followers(final Integer pageSize) {
		ModelAndView result;
		final Collection<Actor> subscribedActors = this.subscriptionService.findSubscribedActors();
		final Collection<Subscription> topicSubscriptions = this.subscriptionService.findTopicSubscriptions();
		result = new ModelAndView("subscription/actor/list");
		result.addObject("subscribedActors", subscribedActors);
		result.addObject("topicSubscriptions", topicSubscriptions);
		result.addObject("requestUri", "subscription/actor/list.do");
		result.addObject("pageSize", (pageSize != null) ? pageSize : 5);
		return result;
	}

	// Subsribe  -----------------------------------------------------------
	@RequestMapping(value = "/subcribe", method = RequestMethod.GET)
	public ModelAndView activation(@RequestParam final int actorId) {
		ModelAndView result;
		try{
			this.subscriptionService.follow(actorId);
		} catch (Throwable oops) {
			if (oops.getMessage().startsWith("msg.")) {
				return createMessageModelAndView(oops.getLocalizedMessage(), "/actor/actor/list.do");
			} else {
				return this.createMessageModelAndView("msg.commit.error", "/");
			}
		}
		result = new ModelAndView("redirect:/actor/actor/list.do");
		return result;
	}



}
