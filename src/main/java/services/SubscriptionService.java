package services;

import domain.Actor;
import domain.Subscription;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.SubscriptionRepository;
import repositories.UserRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class SubscriptionService {
    //Repositories
    @Autowired
    private UserRepository userRepository;
    //Services
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ActorService actorService;

    //Constructor
    public SubscriptionService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Subscription create() {
        final Subscription result = new Subscription();
        result.setSince(new Date());
        return result;
    }

    public Subscription save(Subscription foolow) {
        return subscriptionRepository.save(foolow);

    }

    public Collection<Subscription> findAll() {
        return subscriptionRepository.findAll();
    }



    public Collection<Actor> findSubscriptorActors() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return subscriptionRepository.findSubscriberActorsBySubscribedId((actor!=null)?actor.getId():-1);
    }

    public Collection<Actor> findSubscriptorUsers() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return subscriptionRepository.findSubscriberActorsBySubscribedId((actor!=null)?actor.getId():-1);
    }

    public Collection<Subscription> findFollowers() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return subscriptionRepository.findBySubscribed((actor!=null)?actor.getId():-1);
    }
    public Collection<Actor> findSubscribedActors() {

        final Actor actor = this.actorService.findByPrincipal();
        return subscriptionRepository.findSubscribedActorsBySubscriberId((actor!=null)?actor.getId():-1);
    }

    public Collection<Subscription> findByLogedActor() {
        final Actor actor = this.actorService.findByPrincipal();
        return subscriptionRepository.findBySubscriber((actor!=null)?actor.getId():-1);
    }

    public Collection<Subscription> findTopicSubscriptions() {
        final Actor actor = this.actorService.findByPrincipal();
        return subscriptionRepository.findTopicSubscriptions((actor!=null)?actor.getId():-1);
    }

    public void follow(int userId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        int followerId = actor.getId();
        Actor followed = actorService.findOne(userId);
        Subscription follow = subscriptionRepository.find(followerId, followed.getId());
        Assert.isTrue(!followed.equals(actor), "msg.not.self.follow.block");
        if(follow!=null)
            subscriptionRepository.delete(follow);
        else{
            follow = this.create();
            follow.setSubscriber(actor);
            follow.setSubscribedActor(followed);
            subscriptionRepository.save(follow);
        }
    }

    public Subscription findOne(int followId) {
        return subscriptionRepository.findOne(followId);
    }

}
