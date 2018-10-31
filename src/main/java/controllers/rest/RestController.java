package controllers.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ConfigurationService;

@Controller
@RequestMapping("/asynchronous")
public class RestController {
	@Autowired
	private ConfigurationService configurationService;


	@RequestMapping(value="/configuration/welcome")
	public @ResponseBody String ajaxQuery(HttpServletRequest req, HttpServletResponse res) {
		String result = configurationService.findWelcomeMessage("es");
		return result;
	}
}