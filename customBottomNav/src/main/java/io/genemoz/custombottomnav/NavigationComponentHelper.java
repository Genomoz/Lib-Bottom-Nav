package io.genemoz.custombottomnav;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.ui.NavigationUI;

import java.lang.ref.WeakReference;


public class NavigationComponentHelper {

    public static void setupWithNavController(Menu menu, SmoothBottomBar smoothBottomBar, NavController navController) {
        smoothBottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int pos) {
                return NavigationUI.onNavDestinationSelected(menu.getItem(pos), navController);
            }
        });

        WeakReference<SmoothBottomBar> weakReference = new WeakReference<>(smoothBottomBar);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
                SmoothBottomBar view = weakReference.get();

                if (view == null) {
                    navController.removeOnDestinationChangedListener(this);
                    return;
                }

                for (int h = 0; h < menu.size(); h++) {
                    MenuItem menuItem = menu.getItem(h);
                    if (matchDestination(destination, menuItem.getItemId())) {
                        menuItem.setChecked(true);
                        smoothBottomBar.setItemActiveIndex(h);
                    }
                }
            }
        });
    }

    /**
     * Determines whether the given `destId` matches the NavDestination. This handles
     * both the default case (the destination's id matches the given id) and the nested case where
     * the given id is a parent/grandparent/etc of the destination.
     */
    public static boolean matchDestination(NavDestination destination, @IdRes int destId) {
        NavDestination currentDestination = destination;
        while (currentDestination.getId() != destId && currentDestination.getParent() != null) {
            currentDestination = currentDestination.getParent();
        }
        return currentDestination.getId() == destId;
    }
}
