
package controllers.administrator;

import controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.AdministratorService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {


	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// ---------------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result= new ModelAndView("dashboard/display");

		/*  DASHBOARD C /*

		/*	1. The average, the minimum, the maximum, and the standard deviation of the
			number of showrooms per user. */
		Map<String, Object> showroomsPerUser = new HashMap<>();

		showroomsPerUser.put("avg", administratorService.findAverageShowroomsPerUser());
		showroomsPerUser.put("min", administratorService.findMinimunShowroomsPerUser());
		showroomsPerUser.put("max", administratorService.findMaximunShowroomsPerUser());
		showroomsPerUser.put("stdev", administratorService.findStdevShowroomsPerUser());
		result.addObject("showroomsPerUser", showroomsPerUser);

		/*	2. The average, the minimum, the maximum, and the standard deviation of the
			number of items per showroom.		*/
	/*	result.addObject("findAverageItemsPerUser", administratorService.findAverageItemsPerUser());
		result.addObject("findMininmunItemsPerUser", administratorService.findMininmunItemsPerUser());
		result.addObject("findMaximunItemsPerUser", administratorService.findMaxinmunItemsPerUser());
		result.addObject("findStdevItemsPerUserPerUser", administratorService.findStdevItemsPerUserPerUser());

		/*	3. The average, the minimum, the maximum, and the standard deviation of the
			number of requests per user.		*/
	/*	result.addObject("findAverageResquestsPerUser", administratorService.findAverageResquestsPerUser());
		result.addObject("findMininmunRequestsPerUser", administratorService.findMininmunRequestsPerUser());
		result.addObject("findMaximunRequestsPerUser", administratorService.findMaximunRequestsPerUser());
		result.addObject("findStdevRequestsPerUser", administratorService.findStdevRequestsPerUser());

		/*	4. A chart with the average,  the minimum,  the maximum,  and the standard
		 	deviation of the number of rejected requests per user.		*/
	/*	result.addObject("findAverageRejectedResquestsPerUser", administratorService.findAverageRejectedResquestsPerUser());
		result.addObject("findMininmunRejectedRequestsPerUser", administratorService.findMininmunRejectedRequestsPerUser());
		result.addObject("findMaximunRejectedRequestsPerUser", administratorService.findMaximunRejectedRequestsPerUser());
		result.addObject("findStdevRejectedRequestsPerUser", administratorService.findStdevRejectedRequestsPerUser());


		/* 	DASHBOARD B */
		/*	1.	The minimum, the maximum, the average, and the standard deviation of the
			number of chirps per user.*/
	/*	result.addObject("findAverageChirpsPerUser", administratorService.findAverageChirpsPerUser());
		result.addObject("findMininmunChirpsPerUser", administratorService.findMininmunChirpsPerUser());
		result.addObject("findMaxinmunChirpsPerUser", administratorService.findMaxinmunChirpsPerUser());
		result.addObject("findStdevChirpsPerUser", administratorService.findStdevChirpsPerUser());

		/*	2. 	The minimum, the maximum, the average, and the standard deviation of the
			number of users to whom users subscribe.*/
	/*	result.addObject("findAverageSubscriptionsToUserPerUser", administratorService.findAverageSubscriptionsToUserPerUser());
		result.addObject("findMininmunSubscriptionsToUserPerUser", administratorService.findMininmunSubscriptionsToUserPerUser());
		result.addObject("findMaxinmunSubscriptionsToUserPerUser", administratorService.findMaxinmunSubscriptionsToUserPerUser());
		result.addObject("findStdevSubscriptionsToUserPerUser", administratorService.findStdevSubscriptionsToUserPerUser());

		/*	3. 	The minimum, the maximum, the average, and the standard deviation of the
			number of subscriptors per user.*/
	/*	result.addObject("findAverageFollowersPerUser", administratorService.findAverageFollowersPerUser());
		result.addObject("findMininmunFollowersPerUser", administratorService.findMininmunFollowersPerUser());
		result.addObject("findMaxinmunFollowersPerUser", administratorService.findMaxinmunFollowersPerUser());
		result.addObject("findStdevFollowersPerUser", administratorService.findStdevFollowersPerUser());

		/*	4.	A chart with the number of chirps published grouped by topic.  */
	/*	result.addObject("findChirpsNumberPerTopic", administratorService.findChirpsNumberPerTopic());


		/* DASHBOARD A */ 
		
		/*	1.	The average, the minimum, the maximum, and the standard deviation of the
			number of comments per showroom.*/

		/*	2. 	The average, the minimum, the maximum, and the standard deviation of the
		number of comments per item.*/

		/*	3. 	The average, the minimum, the maximum, and the standard deviation of the
		number of comments per user */


		return result;
	}

}
