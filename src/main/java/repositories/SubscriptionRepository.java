
package repositories;

import domain.Actor;
import domain.Subscription;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Collection;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    @Query("select s from Subscription s where s.subscriber.id=?1 and s.subscribedActor.id=?2")
    Subscription find(int follower, int followed);

    @Query("select s from Subscription s where s.topic=?1 and s.subscribedActor.userAccount.active=true and  s.subscriber.userAccount.active=true ")
    Collection<Subscription> findByTopic(String topic);
    @Query("select s from Subscription s where s.topic=?1 and s.subscriber.id=?2 ")
    Subscription findByTopic(String topic, int actorId);
    @Query("select s from Subscription s where s.subscriber.id=?1 and s.subscribedActor=null")
    Collection<Subscription> findTopicSubscriptions(int ActorId);
    @Query("select s from Subscription s where s.subscribedActor.id=?1 and s.subscribedActor.userAccount.active=true and  s.subscriber.userAccount.active=true ")
    Collection<Subscription> findBySubscribed(int id);
    @Query("select s from Subscription s where s.subscriber.id=?1")
    Collection<Subscription> findBySubscriber(int id);
    @Query("select s.subscriber from Subscription s where s.subscribedActor.id=?1 and s.subscribedActor.userAccount.active=true and  s.subscriber.userAccount.active=true ")
    Collection<Actor> findSubscriberActorsBySubscribedId(int id);
    @Query("select s.subscribedActor from Subscription s where s.subscriber.id=?1 and s.subscribedActor.userAccount.active=true and  s.subscriber.userAccount.active=true ")
    Collection<Actor> findSubscribedActorsBySubscriberId(int id);

    @Query("select s.topic from Subscription s where s.subscriber.id=?1 and s.subscribedActor=null")
    Collection<String> findTopicSubscriptionsNames(int actorId);
}
