package com.swpuiot.stp.injector.component;


import android.app.Activity;
import android.content.Context;

import com.swpuiot.stp.injector.module.ActivityModule;
import com.swpuiot.stp.injector.scrope.ActivityScope;
import com.swpuiot.stp.injector.scrope.ContextLifeCycle;
import com.swpuiot.stp.views.MainActivity;

import dagger.Component;


/**
 * Created by wuhaojie on 2016/7/7 10:57.
 */
@ActivityScope
@Component(modules = ActivityModule.class, dependencies = AppComponent.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    Activity activity();

    @ContextLifeCycle("Activity")
    Context activityContext();

    @ContextLifeCycle("App")
    Context appContext();


}
