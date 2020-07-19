package com.maks.durov.productslist.di.module;

import android.app.Application;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import javax.inject.Inject;
import javax.inject.Singleton;
import com.maks.durov.productslist.room.ProductDao;
import com.maks.durov.productslist.room.ProductDatabase;

@Module
public class RoomModule {
    private final ProductDatabase productDatabase;

    @Inject
    public RoomModule(Application application) {
        productDatabase = Room.databaseBuilder(
                application,
                ProductDatabase.class, ProductDatabase.DATABASE_NAME)
                .build();
    }

    @Provides
    @Singleton
    public ProductDao provideProductDao() {
        return productDatabase.getProductDao();
    }
}
