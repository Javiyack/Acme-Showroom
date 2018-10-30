package services;

import domain.Actor;
import domain.Chirp;
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
    @Autowired
    private TopicService topicService;
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
        chirp.setActor(actor);

        if(chirp.getId()==0) {
            if (chirp.getTopic() != null)
                if (!topicService.findAll().contains(chirp.getTopic()))
                    topicService.save(chirp.getTopic());
            chirp = chirpRepository.save(chirp);
        }
        return chirp;
    }

    public Collection<Chirp> findAll() {
        return chirpRepository.findAll();
    }

    public Chirp findOne(int chirpId) {
        return chirpRepository.findOne(chirpId);
    }

    public Collection<Chirp> findByLoggedActor() {
        final Actor actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return chirpRepository.findByActor(actor.getId());
    }
    public Collection<Chirp> findByUserId(Integer UserId) {
        return chirpRepository.findByActor(UserId);
    }
}
