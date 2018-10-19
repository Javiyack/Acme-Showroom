package repositories;

import domain.Folder;
import domain.Item;
import domain.Showroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query("select h from Item h where h.showroom.id=?1")
	Collection<Item> findByShowroomId(Integer showroomId);

	@Query("select h from Item h where h.name like %?1% " +
			"or h.description like %?1% or h.origin like %?1% or h.destination like %?1% " +
			"or h.difficulty like %?1%")
	Collection<Item> findByKeyWord(String keyWord);

	@Query("select h from Item h where h.difficulty like %?1%")
	Collection<Item> findByDifficultyKeyWord(String keyWord);

	@Query("select h from Item h where h.name like %?1% " +
			"or h.description like %?1% or h.origin like %?1% or h.destination like %?1% ")
	Collection<Item> findByIndexedKeyWord(String keyWord);

	@Query("select h from Item h" +
			" where h.showroom.user.id=?1")
	Collection<Item> findByUserId(int id);
}
