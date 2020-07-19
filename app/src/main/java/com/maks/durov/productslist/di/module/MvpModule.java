package com.maks.durov.productslist.di.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import com.maks.durov.productslist.mvp.ProductModel;
import com.maks.durov.productslist.mvp.ProductPresenter;
import com.maks.durov.productslist.mvp.impl.ProductModelImpl;
import com.maks.durov.productslist.mvp.impl.ProductPresenterImpl;
import com.maks.durov.productslist.room.ProductDao;

@Module
public class MvpModule {

    @Provides
    @Singleton
    public ProductModel provideModel(ProductDao productDao) {
        return new ProductModelImpl(productDao);
    }

    @Provides
    @Singleton
    public ProductPresenter providePresenter(ProductModel productModel) {
        return new ProductPresenterImpl(productModel);
    }
}
