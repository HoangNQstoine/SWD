package SWD.NET1704.repositories;

import SWD.NET1704.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    @Query(value = "SELECT t from TransactionEntity t where t.productEntity.productId=:id")
    List<TransactionEntity> getTransactionByProductEntity(int id);
}
