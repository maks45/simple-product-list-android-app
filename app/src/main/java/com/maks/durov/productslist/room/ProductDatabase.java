package com.maks.durov.productslist.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.maks.durov.productslist.entity.Product;

@Database(entities = {Product.class}, version = 1,exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "shopping_list";

    public abstract ProductDao getProductDao();
}
