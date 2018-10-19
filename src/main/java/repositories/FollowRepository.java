
package repositories;

import domain.Follow;
import domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    @Query("select f from Follow f where f.follower.id=?1 and f.followed.id=?2")
    Follow find(int follower, int followed);

    @Query("select f from Follow f where f.followed.id=?1 and f.followed.userAccount.active=true and  f.follower.userAccount.active=true ")
    Collection<Follow> findByFollowed(int id);
    @Query("select f from Follow f where f.follower.id=?1 and f.followed.userAccount.active=true and  f.follower.userAccount.active=true ")
    Collection<Follow> findByFollower(int id);
    @Query("select f.follower from Follow f where f.followed.id=?1 and f.followed.userAccount.active=true and  f.follower.userAccount.active=true ")
    Collection<User> findFollowerUsers(int id);
    @Query("select f.followed from Follow f where f.follower.id=?1 and f.followed.userAccount.active=true and  f.follower.userAccount.active=true ")
    Collection<User> findFollowedUsers(int id);
}
