package io.genemoz.custombottomnav;

import static io.genemoz.custombottomnav.NavUtils.d2p;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.PopupMenu;
import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.FontRes;
import androidx.annotation.Nullable;
import androidx.annotation.XmlRes;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import kotlin.math.MathKt;

public class SmoothBottomBar extends View {

    private static final int INVALID_RES = -1;
    private static final String DEFAULT_INDICATOR_COLOR = "#2DFFFFFF";
    private static final String DEFAULT_TINT = "#C8FFFFFF";

    private static final int NO_CORNERS = 0;
    private static final int TOP_LEFT_CORNER = 1;
    private static final int TOP_RIGHT_CORNER = 2;
    private static final int BOTTOM_RIGHT_CORNER = 4;
    private static final int BOTTOM_LEFT_CORNER = 8;
    private static final int ALL_CORNERS = 15;

    private static final float DEFAULT_SIDE_MARGIN = 10f;
    private static final float DEFAULT_ITEM_PADDING = 10f;
    private static final long DEFAULT_ANIM_DURATION = 200L;
    private static final float DEFAULT_ICON_SIZE = 18F;
    private static final float DEFAULT_ICON_MARGIN = 4F;
    private static final float DEFAULT_TEXT_SIZE = 11F;
    private static final float DEFAULT_CORNER_RADIUS = 20F;
    private static final float DEFAULT_BAR_CORNER_RADIUS = 0F;
    private static final int DEFAULT_BAR_CORNERS = TOP_LEFT_CORNER | TOP_RIGHT_CORNER;

    private static final int OPAQUE = 255;
    private static final int TRANSPARENT = 0;



    private float itemWidth = 0F;
    private int currentIconTint = getItemIconTintActive(); // itemIconTintActive;
    private float indicatorLocation = getBarSideMargins();  // barSideMargins;
    private final RectF rect = new RectF();
    private List<BottomBarItem> items = new ArrayList<>();

    // Attribute Defaults
    @ColorInt
    private int _barBackgroundColor = Color.WHITE;
    @ColorInt
    private int _barIndicatorColor = Color.parseColor(DEFAULT_INDICATOR_COLOR);
    @Dimension
    private float _barIndicatorRadius = d2p(getContext(),DEFAULT_CORNER_RADIUS);
    @Dimension
    private float _barSideMargins = d2p(getContext(),DEFAULT_SIDE_MARGIN);
    @Dimension
    private float _barCornerRadius = d2p(getContext(),DEFAULT_BAR_CORNER_RADIUS);
    private int _barCorners = DEFAULT_BAR_CORNERS;
    @Dimension
    private float _itemPadding = d2p(getContext(),DEFAULT_ITEM_PADDING);
    private long _itemAnimDuration = DEFAULT_ANIM_DURATION;
    @Dimension
    private float _itemIconSize = d2p(getContext(),DEFAULT_ICON_SIZE);
    @Dimension
    private float _itemIconMargin = d2p(getContext(),DEFAULT_ICON_MARGIN);
    @ColorInt
    private int _itemIconTint = Color.parseColor(DEFAULT_TINT);
    @ColorInt
    private int _itemIconTintActive = Color.WHITE;
    @ColorInt
    private int _itemTextColor = Color.WHITE;
    @ColorInt
    private int _itemBadgeColor = Color.RED;
    @Dimension
    private float _itemTextSize = d2p(getContext(),DEFAULT_TEXT_SIZE);
    @FontRes
    private int _itemFontFamily = INVALID_RES;
    @XmlRes
    private int _itemMenuRes = INVALID_RES;
    private int _itemActiveIndex = 0;
    private Menu menu;
    private final HashSet<Integer> badge_arr = new HashSet<>();

    // Core Attributes
    public int getBarBackgroundColor() {
        return _barBackgroundColor;
    }

    public void setBarBackgroundColor(@ColorInt int value) {
        _barBackgroundColor = value;
        paintBackground.setColor(value);
        invalidate();
    }

    public int getBarIndicatorColor() {
        return _barIndicatorColor;
    }

    public void setBarIndicatorColor(@ColorInt int value) {
        _barIndicatorColor = value;
        paintIndicator.setColor(value);
        invalidate();
    }

    public float getBarIndicatorRadius() {
        return _barIndicatorRadius;
    }

    public void setBarIndicatorRadius(@Dimension float value) {
        _barIndicatorRadius = value;
        invalidate();
    }

    public float getBarSideMargins() {
        return _barSideMargins;
    }

    public void setBarSideMargins(@Dimension float value) {
        _barSideMargins = value;
        invalidate();
    }

    public float getBarCornerRadius() {
        return _barCornerRadius;
    }

    public void setBarCornerRadius(@Dimension float value) {
        _barCornerRadius = value;
        invalidate();
    }

    public int getBarCorners() {
        return _barCorners;
    }

    public void setBarCorners(int value) {
        _barCorners = value;
        invalidate();
    }

    public float getItemTextSize() {
        return _itemTextSize;
    }

    public void setItemTextSize(@Dimension float value) {
        _itemTextSize = value;
        paintText.setTextSize(value);
        invalidate();
    }

    public int getItemTextColor() {
        return _itemTextColor;
    }

    public void setItemTextColor(@ColorInt int value) {
        _itemTextColor = value;
        paintText.setColor(value);
        invalidate();
    }

    public int getItemBadgeColor() {
        return _itemBadgeColor;
    }

    public void setItemBadgeColor(@ColorInt int value) {
        _itemBadgeColor = value;
        badgePaint.setColor(value);
        invalidate();
    }

    public float getItemPadding() {
        return _itemPadding;
    }

    public void setItemPadding(@Dimension float value) {
        _itemPadding = value;
        invalidate();
    }

    public long getItemAnimDuration() {
        return _itemAnimDuration;
    }

    public void setItemAnimDuration(long value) {
        _itemAnimDuration = value;
    }

    public float getItemIconSize() {
        return _itemIconSize;
    }

    public void setItemIconSize(@Dimension float value) {
        _itemIconSize = value;
        invalidate();
    }

    public float getItemIconMargin() {
        return _itemIconMargin;
    }

    public void setItemIconMargin(@Dimension float value) {
        _itemIconMargin = value;
        invalidate();
    }

    public int getItemIconTint() {
        return _itemIconTint;
    }

    public void setItemIconTint(@ColorInt int value) {
        _itemIconTint = value;
        invalidate();
    }

    public int getItemIconTintActive() {
        return _itemIconTintActive;
    }

    public void setItemIconTintActive(@ColorInt int value) {
        _itemIconTintActive = value;
        invalidate();
    }

    public int getItemFontFamily() {
        return _itemFontFamily;
    }

    public void setItemFontFamily(@FontRes int value) {
        _itemFontFamily = value;
        if (value != INVALID_RES) {
            paintText.setTypeface(ResourcesCompat.getFont(getContext(), value));
            invalidate();
        }
    }

    public int getItemMenuRes() {
        return _itemMenuRes;
    }

    public void setItemMenuRes(@XmlRes int value) {
        _itemMenuRes = value;
        PopupMenu popupMenu = new PopupMenu(getContext(), null);
        popupMenu.inflate(value);
        this.menu = popupMenu.getMenu();
        if (value != INVALID_RES) {
            items = BottomBarParser.parse(getContext(), value);
            invalidate();
        }
    }

    public int getItemActiveIndex() {
        return _itemActiveIndex;
    }

    public void setItemActiveIndex(int value) {
        _itemActiveIndex = value;
        applyItemActiveIndex();
    }

    // Listeners
    private OnItemSelectedListener onItemSelectedListener;
    private OnItemReselectedListener onItemReselectedListener;
    private OnItemSelectedListenerLambda onItemSelectedListenerLambda;
    private OnItemReselectedListenerLambda onItemReselectedListenerLambda;

    public OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    public OnItemReselectedListener getOnItemReselectedListener() {
        return onItemReselectedListener;
    }

    public void setOnItemSelectedListener(OnItemSelectedListenerLambda listener) {
        onItemSelectedListenerLambda = listener;
        onItemSelectedListener = null;
    }


    // Paints
    private final Paint paintBackground = new Paint();
    private final Paint paintIndicator = new Paint();
    private final Paint badgePaint = new Paint();
    private final Paint paintText = new Paint();
    private AccessibleExploreByTouchHelper exploreByTouchHelper;

    public SmoothBottomBar(Context context) {
        this(context, null);
    }

    public SmoothBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.SmoothBottomBarStyle);
    }

    public SmoothBottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainStyledAttributes(attrs, defStyleAttr);
        exploreByTouchHelper = new AccessibleExploreByTouchHelper(this, items, this::onClickAction);
        ViewCompat.setAccessibilityDelegate(this, exploreByTouchHelper);
    }

    private void obtainStyledAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SmoothBottomBar,
                defStyleAttr,
                0
        );

        try {
            setBarBackgroundColor(typedArray.getColor(
                    R.styleable.SmoothBottomBar_backgroundColor,
                    getBarBackgroundColor()
            ));
            setBarIndicatorColor(typedArray.getColor(
                    R.styleable.SmoothBottomBar_indicatorColor,
                    getBarIndicatorColor()
            ));
            setBarIndicatorRadius(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_indicatorRadius,
                    getBarIndicatorRadius()
            ));
            setBarSideMargins(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_sideMargins,
                    getBarSideMargins()
            ));
            setBarCornerRadius(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_cornerRadius,
                    getBarCornerRadius()
            ));
            setBarCorners(typedArray.getInteger(
                    R.styleable.SmoothBottomBar_corners,
                    getBarCorners()
            ));
            setItemPadding(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_itemPadding,
                    getItemPadding()
            ));
            setItemTextColor(typedArray.getColor(
                    R.styleable.SmoothBottomBar_textColor,
                    getItemTextColor()
            ));
            setItemTextSize(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_textSize,
                    getItemTextSize()
            ));
            setItemIconSize(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_iconSize,
                    getItemIconSize()
            ));
            setItemIconMargin(typedArray.getDimension(
                    R.styleable.SmoothBottomBar_iconMargin,
                    getItemIconMargin()
            ));
            setItemIconTint(typedArray.getColor(
                    R.styleable.SmoothBottomBar_iconTint,
                    getItemIconTint()
            ));
            setItemBadgeColor(typedArray.getColor(
                    R.styleable.SmoothBottomBar_badgeColor,
                    getItemBadgeColor()
            ));
            setItemIconTintActive(typedArray.getColor(
                    R.styleable.SmoothBottomBar_iconTintActive,
                    getItemIconTintActive()
            ));
            setItemActiveIndex(typedArray.getInt(
                    R.styleable.SmoothBottomBar_activeItem,
                    getItemActiveIndex()
            ));
            setItemFontFamily(typedArray.getResourceId(
                    R.styleable.SmoothBottomBar_itemFontFamily,
                    getItemFontFamily()
            ));
            setItemAnimDuration(typedArray.getInt(
                    R.styleable.SmoothBottomBar_duration,
                    (int) getItemAnimDuration()
            ));
            setItemMenuRes(typedArray.getResourceId(
                    R.styleable.SmoothBottomBar_menu,
                    getItemMenuRes()
            ));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float lastX = getBarSideMargins(); // barSideMargins;
        itemWidth = (getWidth() - (getBarSideMargins() * 2)) / items.size();

        List<BottomBarItem> itemsToLayout;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            itemsToLayout = new ArrayList<>(items);
            Collections.reverse(itemsToLayout);
        } else {
            itemsToLayout = items;
        }

        for (BottomBarItem item : itemsToLayout) {
            boolean shorted = false;
            while (paintText.measureText(item.title) > itemWidth - getItemIconSize() - getItemIconMargin() - (getItemPadding() * 2)) {
                item.title = item.title.substring(0, item.title.length() - 1);
                shorted = true;
            }

            if (shorted) {
                item.title = item.title.substring(0, item.title.length() - 1);
                item.title += getResources().getString(R.string.ellipsis);
            }

            item.rect = new RectF(lastX, 0f, itemWidth + lastX, (float) getHeight());
            lastX += itemWidth;
        }

        applyItemActiveIndex();
    }

    public void setBadge(int pos) {
        badge_arr.add(pos);
        invalidate();
    }

    public void removeBadge(int pos) {
        badge_arr.remove(pos);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw background
        if (getBarCornerRadius() > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(
                    0f, 0f,
                    (float) getWidth(),
                    (float) getHeight(),
                    Math.min(getBarCornerRadius(), (float) getHeight() / 2),
                    Math.min(getBarCornerRadius(), (float) getHeight() / 2),
                    paintBackground
            );

            if (getBarCorners() != ALL_CORNERS) {
                if ((getBarCorners() & TOP_LEFT_CORNER) != TOP_LEFT_CORNER) {
                    canvas.drawRect(0f, 0f, (float) getWidth() / 2,
                            (float) getHeight() / 2, paintBackground);
                }

                if ((getBarCorners() & TOP_RIGHT_CORNER) != TOP_RIGHT_CORNER) {
                    canvas.drawRect((float) getWidth() / 2, 0f, (float) getWidth(),
                            (float) getHeight() / 2, paintBackground);
                }

                if ((getBarCorners() & BOTTOM_LEFT_CORNER) != BOTTOM_LEFT_CORNER) {
                    canvas.drawRect(0f, (float) getHeight() / 2, (float) getWidth() / 2,
                            (float) getHeight(), paintBackground);
                }

                if ((getBarCorners() & BOTTOM_RIGHT_CORNER) != BOTTOM_RIGHT_CORNER) {
                    canvas.drawRect((float) getWidth() / 2, (float) getHeight() / 2, (float) getWidth(),
                            (float) getHeight(), paintBackground);
                }
            }
        } else {
            canvas.drawRect(
                    0f, 0f,
                    (float) getWidth(),
                    (float) getHeight(),
                    paintBackground
            );
        }

        // Draw indicator
        rect.left = indicatorLocation;
        rect.top = items.get(getItemActiveIndex()).rect.centerY() - getItemIconSize() / 2 - getItemPadding();
        rect.right = indicatorLocation + itemWidth;
        rect.bottom = items.get(getItemActiveIndex()).rect.centerY() + getItemIconSize() / 2 + getItemPadding();

        canvas.drawRoundRect(
                rect,
                getBarIndicatorRadius(),
                getBarIndicatorRadius(),
                paintIndicator
        );

        float textHeight = (paintText.descent() + paintText.ascent()) / 2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            for (int i = 0; i < items.size(); i++) {
                BottomBarItem item = items.get(i);
                float textLength = paintText.measureText(item.title);
                item.icon.mutate();
                item.icon.setBounds(
                        (int) (item.rect.centerX()
                                - getItemIconSize() / 2
                                + ((textLength / 2) * (1 - (OPAQUE - item.alpha) / (float) OPAQUE))),
                        getHeight() / 2 - (int) (getItemIconSize() / 2),
                        (int) (item.rect.centerX()
                                + getItemIconSize() / 2
                                + ((textLength / 2) * (1 - (OPAQUE - item.alpha) / (float) OPAQUE))),
                        getHeight() / 2 + (int) (getItemIconSize() / 2)
                );

                tintAndDrawIcon(item, i, canvas);

                paintText.setAlpha(item.alpha);
                canvas.drawText(
                        item.title,
                        item.rect.centerX() - (getItemIconSize() / 2 + getItemIconMargin()),
                        item.rect.centerY() - textHeight, paintText
                );
            }
        } else {
            for (int i = 0; i < items.size(); i++) {
                BottomBarItem item = items.get(i);
                float textLength = paintText.measureText(item.title);

                item.icon.mutate();
                item.icon.setBounds(
                        (int) (item.rect.centerX()
                                - getItemIconSize() / 2
                                - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / (float) OPAQUE))),
                        getHeight() / 2 - (int) (getItemIconSize() / 2),
                        (int) (item.rect.centerX()
                                + getItemIconSize() / 2
                                - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / (float) OPAQUE))),
                        getHeight() / 2 + (int) (getItemIconSize() / 2)
                );

                if (badge_arr.contains(i)) {
                    canvas.drawCircle(
                            item.rect.centerX()
                                    - getItemIconSize() / 2f
                                    - ((textLength / 2) * (1 - (OPAQUE - item.alpha) / (float) OPAQUE)),
                            getHeight() / 2f - getItemIconSize() / 2f,
                            10f,
                            badgePaint
                    );
                }

                tintAndDrawIcon(item, i, canvas);

                paintText.setAlpha(item.alpha);
                canvas.drawText(
                        item.title,
                        item.rect.centerX() + getItemIconSize() / 2 + getItemIconMargin(),
                        item.rect.centerY() - textHeight, paintText
                );
            }
        }
    }

    private void tintAndDrawIcon(BottomBarItem item, int index, Canvas canvas) {
        DrawableCompat.setTint(
                item.icon,
                index == itemActiveIndex ? currentIconTint : itemIconTint
        );

        item.icon.draw(canvas);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i < items.size(); i++) {
                    BottomBarItem item = items.get(i);
                    if (item.rect.contains(event.getX(), event.getY())) {
                        onClickAction(i);
                        break;
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchHoverEvent(MotionEvent event) {
        return exploreByTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    private void onClickAction(int viewId) {
        exploreByTouchHelper.invalidateVirtualView(viewId);
        if (viewId != itemActiveIndex) {
            itemActiveIndex = viewId;
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelect(viewId);
            } else if (onItemSelectedListenerLambda != null) {
                onItemSelectedListenerLambda.onItemSelect(viewId);
            }
        } else {
            if (onItemReselectedListener != null) {
                onItemReselectedListener.onItemReselect(viewId);
            } else if (onItemReselectedListenerLambda != null) {
                onItemReselectedListenerLambda.onItemReselect(viewId);
            }
        }
        exploreByTouchHelper.sendEventForVirtualView(
                viewId,
                AccessibilityEvent.TYPE_VIEW_CLICKED
        );
    }

    private void applyItemActiveIndex() {
        if (!items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                BottomBarItem item = items.get(i);
                if (i == itemActiveIndex) {
                    animateAlpha(item, OPAQUE);
                } else {
                    animateAlpha(item, TRANSPARENT);
                }
            }

            ValueAnimator.ofFloat(
                    indicatorLocation,
                    items.get(itemActiveIndex).rect.left
            ).apply {
                setDuration(itemAnimDuration);
                setInterpolator(new DecelerateInterpolator());
                addUpdateListener(animation -> {
                    indicatorLocation = (float) animation.getAnimatedValue();
                    invalidate();
                });
                start();
            }

            ValueAnimator.ofObject(new ArgbEvaluator(), itemIconTint, itemIconTintActive).apply {
                setDuration(itemAnimDuration);
                addUpdateListener(animation -> {
                    currentIconTint = (int) animation.getAnimatedValue();
                    invalidate();
                });
                start();
            }
        }
    }

    private void animateAlpha(BottomBarItem item, int to) {
        ValueAnimator.ofInt(item.alpha, to).apply {
            setDuration(itemAnimDuration);
            addUpdateListener(animation -> {
                int value = (int) animation.getAnimatedValue();
                item.alpha = value;
                invalidate();
            });
            start();
        }
    }

    public void setupWithNavController(Menu menu, NavController navController) {
        NavigationUI.setupWithNavController(menu, this, navController);
    }

    public void setupWithNavController(NavController navController) {
        NavigationUI.setupWithNavController(menu, this, navController);
        Navigation.setViewNavController(this, navController);
    }

    public void setSelectedItem(int pos) {
        try {
            itemActiveIndex = pos;
            NavigationUI.onNavDestinationSelected(menu.getItem(pos), findNavController());
        } catch (Exception e) {
            throw new Exception("set menu using PopupMenu");
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        onItemSelectedListener = listener;
        onItemSelectedListenerLambda = null;
    }

    public void setOnItemReselectedListener(OnItemReselectedListener listener) {
        onItemReselectedListener = listener;
        onItemReselectedListenerLambda = null;
    }



    public void setOnItemReselectedListener(OnItemReselectedListenerLambda listener) {
        onItemReselectedListenerLambda = listener;
        onItemReselectedListener = null;
    }

    public interface OnItemSelectedListener {
        boolean onItemSelect(int pos);
    }

    public interface OnItemReselectedListener {
        void onItemReselect(int pos);
    }

    public interface OnItemSelectedListenerLambda {
        boolean onItemSelect(int pos);
    }

    public interface OnItemReselectedListenerLambda {
        void onItemReselect(int pos);
    }

    private static class BottomBarItem {
        public String title;
        public Drawable icon;
        public RectF rect;
        public int alpha;

        public BottomBarItem(String title, Drawable icon) {
            this.title = title;
            this.icon = icon;
            this.alpha = TRANSPARENT;
        }
    }

    private static class BottomBarParser {
        public static List<BottomBarItem> parse(Context context, @XmlRes int menuRes) {
            MenuParser menuParser = new MenuParser(context);
            try {
                menuParser.parse(menuRes);
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            Menu menu = menuParser.getMenu();
            List<BottomBarItem> items = new ArrayList<>();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem menuItem = menu.getItem(i);
                items.add(new BottomBarItem(
                        menuItem.getTitle().toString(),
                        menuItem.getIcon()
                ));
            }
            return items;
        }
    }

    private static class MenuParser {
        private static final String XML_MENU_ITEM = "item";
        private static final String XML_MENU_GROUP = "group";

        private final Context context;
        private Menu menu;

        public MenuParser(Context context) {
            this.context = context;
        }

        public void parse(@XmlRes int menuRes) throws XmlPullParserException, IOException {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(context.getResources().openRawResource(menuRes), null);
            menu = createMenu(parser, null);
        }

        public Menu getMenu() {
            return menu;
        }

        private Menu createMenu(XmlPullParser parser, @Nullable Menu parentMenu)
                throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            Menu menu = parentMenu;
            String tagName;
            String menuId = null;
            String menuGroup = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equals(XML_MENU_ITEM)) {
                            if (menu == null) {
                                throw new RuntimeException("Menu XML file contains a menu item outside of a menu group");
                            }
                            readItem(parser, menu);
                        } else if (tagName.equals(XML_MENU_GROUP)) {
                            menuGroup = parser.getAttributeValue(null, "group");
                            menu = parentMenu;
                        } else {
                            if (tagName.equals("menu")) {
                                menuId = parser.getAttributeValue(null, "id");
                            }
                            menu = parentMenu;
                            parentMenu = createNewMenu(menuGroup, menu);
                            menu = parentMenu;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equals("menu") && parentMenu != null) {
                            menu = parentMenu;
                            parentMenu = parentMenu.getParentMenu();
                        }
                        break;
                }
                eventType = parser.next();
            }
            return menu;
        }

        private void readItem(XmlPullParser parser, Menu menu)
                throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            String tagName;
            String itemId = null;
            String itemName = null;
            String itemIcon = null;
            String itemEnabled = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        if (tagName.equals(XML_MENU_ITEM)) {
                            itemId = parser.getAttributeValue(null, "id");
                            itemName = parser.getAttributeValue(null, "title");
                            itemIcon = parser.getAttributeValue(null, "icon");
                            itemEnabled = parser.getAttributeValue(null, "enabled");
                            MenuItem item = menu.add(
                                    0,
                                    Integer.parseInt(itemId),
                                    0,
                                    itemName
                            );
                            if (itemIcon != null) {
                                int iconResId = context.getResources().getIdentifier(
                                        itemIcon,
                                        "drawable",
                                        context.getPackageName()
                                );
                                item.setIcon(iconResId);
                            }
                            if (itemEnabled != null) {
                                item.setEnabled(Boolean.parseBoolean(itemEnabled));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = parser.getName();
                        if (tagName.equals(XML_MENU_ITEM)) {
                            return;
                        }
                        break;
                }
                eventType = parser.next();
            }
        }

        private Menu createNewMenu(String groupId, Menu parentMenu) {
            if (parentMenu != null) {
                MenuBuilder subMenu = new MenuBuilder(context);
                subMenu.setQwertyMode(parentMenu.isQwertyMode());
                parentMenu.addSubMenu(groupId, subMenu);
                return subMenu;
            } else {
                throw new RuntimeException("Menu XML file contains a menu item outside of a menu group");
            }
        }
    }

    private static class AccessibleExploreByTouchHelper extends ExploreByTouchHelper {
        private final List<BottomBarItem> items;
        private final Consumer<Integer> clickConsumer;

        public AccessibleExploreByTouchHelper(View host, List<BottomBarItem> items, Consumer<Integer> clickConsumer) {
            super(host);
            this.items = items;
            this.clickConsumer = clickConsumer;
        }

        @Override
        protected int getVirtualViewAt(float x, float y) {
            for (int i = 0; i < items.size(); i++) {
                BottomBarItem item = items.get(i);
                if (item.rect.contains(x, y)) {
                    return i;
                }
            }
            return ExploreByTouchHelper.INVALID_ID;
        }

        @Override
        protected void getVisibleVirtualViews(List<Integer> virtualViewIds) {
            for (int i = 0; i < items.size(); i++) {
                virtualViewIds.add(i);
            }
        }

        @Override
        protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            event.setContentDescription(items.get(virtualViewId).title);
            event.setClassName(SmoothBottomBar.class.getName());
        }

        @Override
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfoCompat node) {
            BottomBarItem item = items.get(virtualViewId);
            node.setContentDescription(item.title);
            node.setClassName(SmoothBottomBar.class.getName());
            node.setBoundsInParent(new Rect(
                    (int) item.rect.left,
                    (int) item.rect.top,
                    (int) item.rect.right,
                    (int) item.rect.bottom
            ));
            node.addAction(AccessibilityNodeInfoCompat.ACTION_CLICK);
        }

        @Override
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            if (action == AccessibilityNodeInfoCompat.ACTION_CLICK) {
                clickConsumer.accept(virtualViewId);
                return true;
            }
            return false;
        }
    }
}
