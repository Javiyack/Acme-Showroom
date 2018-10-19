
package repositories;

import domain.Chirp;
import domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Integer> {
    @Query("select c from Chirp c where c.user.id=?1")
    Collection<Chirp> findByUser(int user);
}
