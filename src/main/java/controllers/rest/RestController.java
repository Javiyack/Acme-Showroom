package controllers.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ConfigurationService;

import java.util.Locale;

@Controller
@RequestMapping("/asynchronous")
public class RestController {
	@Autowired
	private ConfigurationService configurationService;


	@RequestMapping(value="/configuration/welcome")
	public @ResponseBody String ajaxQuery(HttpServletRequest req, HttpServletResponse res) {
		final Locale locale = LocaleContextHolder.getLocale();
		String result = configurationService.findWelcomeMessage(locale.getLanguage());
		return result;
	}
}