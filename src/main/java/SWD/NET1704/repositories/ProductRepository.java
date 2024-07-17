package SWD.NET1704.repositories;

import java.util.List;

import SWD.NET1704.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT p FROM ProductEntity p where p.status = true order by  p.productId desc")
    List<ProductEntity> getAllProductWithStatusIsTrue();
}
