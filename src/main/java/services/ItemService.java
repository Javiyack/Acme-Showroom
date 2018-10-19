
package services;

import domain.Actor;
import domain.Item;
import domain.Showroom;
import domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import repositories.ItemRepository;

import java.util.Collection;

@Service
@Transactional
public class ItemService {

    // Managed repository -----------------------------------------------------


    // Supporting services
    @Autowired
    private ActorService actorService;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ShowroomService showroomService;


    // CRUD Methods

    // Create
    public Item create(Showroom showroom) {
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Item result= new Item();
        result.setShowroom(showroom);
        return result;
    }

    // Save
    public Item save( Item item) {
        Assert.notNull(item, "msg.not.found.resource");
        Actor actor = actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        Assert.isTrue(actor instanceof User, "msg.not.owned.block");
        Assert.isTrue(item.getShowroom().getUser().equals(actor), "msg.not.owned.block");
        Item result = itemRepository.saveAndFlush(item);
        return result;
    }

    public Item findOne(Integer id) {
        return itemRepository.findOne(id);
    }

    public Collection<Item> findAll() {
        Collection<Item> items =  itemRepository.findAll();
        return items;
    }

    public Collection<Item> findByLogedActor() {
        Actor actor;
        actor = this.actorService.findByPrincipal();
        Assert.notNull(actor, "msg.not.logged.block");
        return itemRepository.findByUserId((actor.getId()));
    }

    public Collection<Item> findByUserId(int id) {
        return itemRepository.findByUserId(id);
    }

    public Collection<Item> findByKeyWord(String keyWord) {
        return itemRepository.findByIndexedKeyWord(keyWord);
    }


    public void flush() {
        this.itemRepository.flush();

    }

    public void delete(Item item) {
        itemRepository.delete(item);
    }

    public void delete(int itemId) {
        itemRepository.delete(itemId);
    }
    public void deleteInBatch(Collection<Item> items) {
        itemRepository.deleteInBatch(items);
    }

    public Collection<Item> findByShowroom(Showroom showroom) {
        return this.findByShowroomId(showroom.getId());
    }
    public Collection<Item> findByShowroomId(Integer showroomId) {

        return itemRepository.findByShowroomId(showroomId);
    }
}
