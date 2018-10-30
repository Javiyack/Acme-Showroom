
package repositories;

import domain.TabooWord;
import domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

	@Query("select t.name from Topic t")
	Collection<String> findNameList();

	@Query("select t from Topic t where t.name = ?1")
	Topic fidByName(String topicName);
}
