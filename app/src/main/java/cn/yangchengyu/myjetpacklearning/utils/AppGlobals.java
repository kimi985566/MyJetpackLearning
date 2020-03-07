package cn.yangchengyu.myjetpacklearning.utils;

import android.app.Application;

import java.lang.reflect.InvocationTargetException;

public class AppGlobals {

    private static Application application;

    public static Application getApplication() {
        if (application == null) {
            try {
                application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return application;
    }
}
