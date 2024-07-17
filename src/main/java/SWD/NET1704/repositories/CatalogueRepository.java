package SWD.NET1704.repositories;

import SWD.NET1704.entities.CatalogueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogueRepository  extends JpaRepository<CatalogueEntity, Integer> {
    @Query(value = "SELECT * FROM swp_zoo_management.catalogue c where c.status = true",nativeQuery = true)
    List<CatalogueEntity> getAllByStatusTrue();
}
