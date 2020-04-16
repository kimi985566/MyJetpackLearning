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
import cn.yangchengyu.myjetpacklearning.navigator.FixFragmentNavigator;

public class NavGraphBuilder {

    public static void build(FragmentActivity activity, FragmentManager childFragmentManager, NavController controller, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();

        //NavGraphNavigator也是页面路由导航器的一种，只不过他比较特殊。
        //它只为默认的展示页提供导航服务,但真正的跳转还是交给对应的navigator来完成的
        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        //FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        //fragment的导航此处使用我们定制的FixFragmentNavigator，底部Tab切换时 使用hide()/show(),而不是replace()
        FixFragmentNavigator fragmentNavigator = new FixFragmentNavigator(activity, childFragmentManager, containerId);
        provider.addNavigator(fragmentNavigator);
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

        controller.setGraph(navGraph);
    }
}
