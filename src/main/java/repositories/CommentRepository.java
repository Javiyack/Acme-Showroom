
package repositories;

import domain.Comment;
import domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("select c from Comment c where c.showroom.id=?1 or c.item=?1")
    Collection<Comment> findByPlace(int place);
}
