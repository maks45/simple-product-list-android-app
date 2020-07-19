package com.maks.durov.productslist.di.component;

import android.app.Application;
import dagger.Component;
import javax.inject.Singleton;
import com.maks.durov.productslist.di.module.AppModule;
import com.maks.durov.productslist.di.module.MvpModule;
import com.maks.durov.productslist.di.module.RoomModule;
import com.maks.durov.productslist.ui.CreateProductActivity;
import com.maks.durov.productslist.ui.ListProductFragment;
import com.maks.durov.productslist.ui.MainActivity;

@Component(modules = {AppModule.class, RoomModule.class, MvpModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity mainActivity);

    void inject(ListProductFragment listProductFragment);

    void inject(CreateProductActivity createItemActivity);

    Application getApplication();
}
