package com.maks.durov.productslist.room;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.maks.durov.productslist.entity.Product;
import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM Product")
    List<Product> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<Product> stores);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Product product);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}
