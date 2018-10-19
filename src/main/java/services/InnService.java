package services;

import domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ItemRepository;
import repositories.InnRepository;

import java.util.Collection;

@Service
@Transactional
public class InnService {

    // Managed repository -----------------------------------------------------


    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private InnRepository innRepository;


    // CRUD Methods

    // Create
    public Inn create() {
        Actor actor =actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof Agent, "msg.not.owned.block");
        Inn result= new Inn();
        result.setAgent((Agent) actor);
        return result;
    }

    // Save
    public Inn save( Inn inn) {
        Assert.notNull(inn, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof Agent, "msg.not.owned.block");
        Assert.isTrue(inn.getAgent().equals(actor), "msg.not.owned.block");
        Inn result = innRepository.saveAndFlush(inn);
        return result;
    }

    public Inn findOne(Integer id) {
        return innRepository.findOne(id);
    }

    public Collection<Inn> findAll() {
        Collection<Inn> inns =  innRepository.findAll();
        return inns;
    }




    public void flush() {
        this.itemRepository.flush();

    }

    public void delete(Inn inn) {
        innRepository.delete(inn);
    }

    public void delete(int innId) {
        innRepository.delete(innId);
    }
    public void deleteInBatch(Collection<Inn> items) {
        innRepository.deleteInBatch(items);
    }

}
