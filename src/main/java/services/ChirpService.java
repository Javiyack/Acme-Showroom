package services;

import domain.Actor;
import domain.Chirp;
import domain.Comment;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ChirpRepository;

import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class ChirpService {
    //Repositories
    @Autowired
    private ChirpRepository chirpRepository;
    //Services
    @Autowired
    private ActorService actorService;
    //Constructor
    public ChirpService() {
        super();
    }

    // Simple CRUD methods ----------------------------------------------------

    //Create
    public Chirp create() {
        final Chirp result = new Chirp();
        result.setMoment(new Date());
        return result;
    }

    public Chirp save(Chirp chirp) {
        Assert.notNull(chirp, "msg.commit.error");
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        chirp.setUser((User) actor);
        if(chirp.getId()==0)
            chirp = chirpRepository.save(chirp);
        return chirp;
    }

    public Collection<Chirp> findAll() {
        return chirpRepository.findAll();
    }

    public Chirp findOne(int chirpId) {
        return chirpRepository.findOne(chirpId);
    }

    public Collection<Chirp> findByLoggedUser() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.user.block");
        return chirpRepository.findByUser(actor.getId());
    }
    public Collection<Chirp> findByUserId(Integer UserId) {
        return chirpRepository.findByUser(UserId);
    }
}
