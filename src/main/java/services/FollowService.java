package services;

import domain.Actor;
import domain.Follow;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.FollowRepository;
import repositories.UserRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class FollowService {
    //Repositories
    @Autowired
    private UserRepository userRepository;
    //Services
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private ActorService actorService;

    //Constructor
    public FollowService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Follow create() {
        final Follow result = new Follow();
        result.setSince(new Date());
        return result;
    }

    public Follow save(Follow foolow) {
        return followRepository.save(foolow);

    }

    public Collection<Follow> findAll() {
        return followRepository.findAll();
    }



    public Collection<User> findFollowerUsers() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return followRepository.findFollowerUsers((actor!=null)?actor.getId():-1);
    }

    public Collection<Follow> findFollowers() {

        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return followRepository.findByFollowed((actor!=null)?actor.getId():-1);
    }
    public Collection<User> findFollowedUsers() {

        final Actor actor = this.actorService.findByPrincipal();
        return followRepository.findFollowedUsers((actor!=null)?actor.getId():-1);
    }

    public Collection<Follow> findFolloweds() {
        final Actor actor = this.actorService.findByPrincipal();
        return followRepository.findByFollower((actor!=null)?actor.getId():-1);
    }

    public void follow(int userId) {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        int followerId = actor.getId();
        User followed = userRepository.findOne(userId);
        Follow follow = followRepository.find(followerId, followed.getId());
        Assert.isTrue(!followed.equals(actor), "msg.not.self.follow.block");
        if(follow!=null)
            followRepository.delete(follow);
        else{
            follow = this.create();
            follow.setFollower((User)actor);
            follow.setFollowed(followed);
            followRepository.save(follow);
        }
    }

    public Follow findOne(int followId) {
        return followRepository.findOne(followId);
    }

}
