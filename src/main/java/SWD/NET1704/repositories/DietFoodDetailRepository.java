package SWD.NET1704.repositories;

import SWD.NET1704.entities.DietFoodDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DietFoodDetailRepository extends JpaRepository<DietFoodDetailEntity, Integer> {
    @Query(value = "SELECT t from DietFoodDetailEntity t where t.foodEntity.foodId=:id")
    List<DietFoodDetailEntity> getDietByFoodEntity(int id);
}
