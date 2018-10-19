
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {

	@Query("select f from File f where f.message.id = ?1")
	Collection<File> findByMessage(int ownerId);
}
