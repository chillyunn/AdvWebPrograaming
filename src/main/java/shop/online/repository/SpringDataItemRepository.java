package shop.online.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.online.domain.item.Item;
import shop.online.dto.BasicItemDTO;

import java.util.List;

public interface SpringDataItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByPriceGreaterThanEqual(int price);
    List<Item> findByNameLike(String name);
    List<Item> findByNameLikeAndPriceLessThanEqualOrderByPriceAsc(String name, int price);
    Page<Item> findPageBy(Pageable pageable);

    @Query("SELECT i FROM Item i WHERE i.price <=:price")
    List<Item> findByPriceLessThan(int price);

    @Query(value = "SELECT i.name, i.price FROM Item i WHERE i.price <=:price", nativeQuery = true)
    List<Object[]> findByPriceLessThan2(int price);

    @Query("SELECT new shop.online.dto.BasicItemDTO(i.name,i.price) FROM Item i WHERE i.price <=:price")
    List<BasicItemDTO> findByPriceLessThan3(int price);
}
