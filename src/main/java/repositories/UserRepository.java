
package repositories;

import java.util.Collection;

import domain.PostBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.userAccount.active=true")
    Collection<User> findAllActive();
}
