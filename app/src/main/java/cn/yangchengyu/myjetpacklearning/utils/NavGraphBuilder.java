package cn.yangchengyu.myjetpacklearning.utils;

import android.content.ComponentName;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

import java.util.HashMap;

import cn.yangchengyu.libcommon.utils.AppGlobals;
import cn.yangchengyu.myjetpacklearning.model.Destination;

public class NavGraphBuilder {

    public static void build(NavController navController) {
        NavigatorProvider provider = navController.getNavigatorProvider();

        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);

        HashMap<String, Destination> destConfig = AppConfig.getDestConfig();
        for (Destination destination : destConfig.values()) {
            if (destination.isFragment()) {
                FragmentNavigator.Destination fragmentNavigatorDestination = fragmentNavigator.createDestination();
                fragmentNavigatorDestination.setClassName(destination.getClassName());
                fragmentNavigatorDestination.setId(destination.getId());
                fragmentNavigatorDestination.addDeepLink(destination.getPageUrl());

                navGraph.addDestination(fragmentNavigatorDestination);
            } else {
                ActivityNavigator.Destination activityNavigatorDestination = activityNavigator.createDestination();
                activityNavigatorDestination.setComponentName(new ComponentName(AppGlobals.getApplication().getPackageName(), destination.getClassName()));
                activityNavigatorDestination.setId(destination.getId());
                activityNavigatorDestination.addDeepLink(destination.getPageUrl());

                navGraph.addDestination(activityNavigatorDestination);
            }

            if (destination.isAsStarter()) {
                navGraph.setStartDestination(destination.getId());
            }
        }

        navController.setGraph(navGraph);
    }
}
