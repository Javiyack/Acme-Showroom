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

	@Query("select h from Item h where h.title like %?1% or h.description like %?1% or h.SKU like %?1%")
	Collection<Item> findByKeyWord(String keyWord);

	@Query("select h from Item h where (h.title like %?1% or h.description like %?1% or h.SKU like %?1%) and h.showroom.id=?2")
	Collection<Item> findByKeyWordAndShowroom(String word, Integer showroomId);

	@Query("select h from Item h where h.SKU like %?1%")
	Collection<Item> findBySKU(String keyWord);

	@Query("select h from Item h where h.title like %?1% or h.description like %?1% or h.SKU like %?1%")
	Collection<Item> findByIndexedKeyWord(String keyWord);

	@Query("select h from Item h" +
			" where h.showroom.user.id=?1")
	Collection<Item> findByUserId(int id);

}
