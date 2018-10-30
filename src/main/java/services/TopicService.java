
package services;

import domain.Actor;
import domain.Administrator;
import domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.TopicRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class TopicService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private TopicRepository		topicRepository;

	//Services
	@Autowired
	private ActorService	actorService;


	// Constructor ----------------------------------------------------------
	public TopicService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------

	public Topic create() {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Topic topic = new Topic();

		return topic;
	}
	public Collection<String> findNameList(){
		return topicRepository.findNameList();
	}
	public Topic create(String name) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Topic topic = new Topic();
		topic.setName(name);
		return topic;
	}

	public Topic findOne(final int topicId) {
		Topic result;
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		result = this.topicRepository.findOne(topicId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Topic> findAll() {

		Collection<Topic> result;

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		result = this.topicRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Topic save(final Topic topic) {

		Assert.notNull(topic);

		final Actor admin = this.actorService.findByPrincipal();
		Assert.notNull(admin);

		final Topic saved = this.topicRepository.save(topic);

		return saved;
	}

	public void delete(final Topic topic) {
		Assert.notNull(topic);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		this.topicRepository.delete(topic);
	}

	public void deleteById(final int topicId) {
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		this.topicRepository.delete(topicId);
	}

	public void flush() {
		this.topicRepository.flush();

	}

	public Topic findByName(String topicName) {
		return topicRepository.fidByName(topicName);
	}
}
