package controllers.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ConfigurationService;

@Controller
@RequestMapping("/jquery")
public class RestController {
	@Autowired
	private ConfigurationService configurationService;

	
	@RequestMapping(value="/hostCheck")
	public @ResponseBody String ajaxQuery(HttpServletRequest req, HttpServletResponse res) {
		String result = "";
		result = "Resultado de la consulta";
		return result;
	}
}