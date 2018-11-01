
package repositories;

import domain.Actor;
import domain.Request;
import domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    
    @Query("select r from Request r where r.user.id=?1")
    Collection<Request> findByUser(int id);

    @Query("select r from Request r where r.item.showroom.user.id=?1")
    Collection<Request> findRecivedRequests(int id);
}

