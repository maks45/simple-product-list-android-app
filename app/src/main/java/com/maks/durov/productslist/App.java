package com.maks.durov.productslist;

import android.app.Application;
import com.maks.durov.productslist.di.component.AppComponent;
import com.maks.durov.productslist.di.component.DaggerAppComponent;
import com.maks.durov.productslist.di.module.AppModule;
import com.maks.durov.productslist.di.module.MvpModule;
import com.maks.durov.productslist.di.module.RoomModule;

public class App extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .roomModule(new RoomModule(this))
                .mvpModule(new MvpModule())
                .build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
