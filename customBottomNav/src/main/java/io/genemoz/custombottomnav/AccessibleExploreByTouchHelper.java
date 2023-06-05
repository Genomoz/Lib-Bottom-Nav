package io.genemoz.custombottomnav;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import java.util.List;
import java.util.function.Consumer;

public class AccessibleExploreByTouchHelper {


      //  extends ExploreByTouchHelper {
//    private SmoothBottomBar host;
//    private List<BottomBarItem> bottomBarItems;
//    private Consumer<Integer> onClickAction;
//
//    public AccessibleExploreByTouchHelper(SmoothBottomBar host, List<BottomBarItem> bottomBarItems, Consumer<Integer> onClickAction) {
//        super(host);
//        this.host = host;
//        this.bottomBarItems = bottomBarItems;
//        this.onClickAction = onClickAction;
//    }
//
//    @Override
//    protected int getVirtualViewAt(float x, float y) {
//        int itemWidth = host.getWidth() / bottomBarItems.size();
//        return (int)(x / itemWidth);
//    }
//
//    @Override
//    protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
//        for (int i = 0; i < bottomBarItems.size(); i++) {
//            virtualViewIds.add(i);
//        }
//    }
//
//    @Override
//    protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
//        node.setClassName(BottomBarItem.class.getSimpleName());
//        node.setContentDescription(bottomBarItems.get(virtualViewId).getContentDescription());
//        node.setClickable(true);
//        node.setFocusable(true);
//        node.setScreenReaderFocusable(true);
//
//        node.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
//
//        node.setSelected(host.getItemActiveIndex() == virtualViewId);
//
//        Rect bottomItemBoundRect = updateBoundsForBottomItem(virtualViewId);
//        node.setBoundsInParent(bottomItemBoundRect);
//    }
//
//    @Override
//    protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
//        if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
//            onClickAction.accept(virtualViewId);
//            return true;
//        }
//        return false;
//    }
//
//    private Rect updateBoundsForBottomItem(int index) {
//        Rect itemBounds = new Rect();
//        int itemWidth = host.getWidth() / bottomBarItems.size();
//        int left = index * itemWidth;
//        itemBounds.left = left;
//        itemBounds.top = 0;
//        itemBounds.right = left + itemWidth;
//        itemBounds.bottom = host.getHeight();
//        return itemBounds;
//    }
}
