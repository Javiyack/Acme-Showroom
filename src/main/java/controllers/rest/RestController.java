package controllers.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import services.ConfigurationService;
import services.TopicService;

import java.util.Collection;

@Controller
@RequestMapping("/asynchronous")
public class RestController {
	@Autowired
	private ConfigurationService configurationService;
	@Autowired
	private TopicService topicService;


	@RequestMapping(value="/topic/list")
	public @ResponseBody Collection<Topic> list(HttpServletRequest req, HttpServletResponse res) {
		Collection<Topic> topics = topicService.findAll();
		return topics;
	}
	@RequestMapping(value="/topic/create")
	public @ResponseBody String create(HttpServletRequest req, HttpServletResponse res) {
		String topicName = req.getParameter("topicName");
		Topic topic = topicService.findByName(topicName);
		if(topic != null) {
			return topic.getName();
		}
		else{
			topic = topicService.create(req.getParameter("topicName"));
			return topic.getName();
		}
	}
	@RequestMapping(value="/hostCheck")
	public @ResponseBody String ajaxQuery(HttpServletRequest req, HttpServletResponse res) {
		String result = "";
		result = "Resultado de la consulta";
		return result;
	}
}