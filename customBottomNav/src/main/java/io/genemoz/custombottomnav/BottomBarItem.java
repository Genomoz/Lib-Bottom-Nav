package io.genemoz.custombottomnav;

import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BottomBarItem {
    private String title;
    private String contentDescription;
    private Drawable icon;
    private RectF rect;
    private int alpha;

    public BottomBarItem(String title, String contentDescription, Drawable icon, RectF rect, int alpha) {
        this.title = title;
        this.contentDescription = contentDescription;
        this.icon = icon;
        this.rect = (rect != null) ? rect : new RectF();
        this.alpha = alpha;
    }

    public BottomBarItem(String title, String contentDescription, Drawable icon, int alpha) {
        this(title, contentDescription, icon, null, alpha);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentDescription() {
        return contentDescription;
    }

    public void setContentDescription(String contentDescription) {
        this.contentDescription = contentDescription;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BottomBarItem that = (BottomBarItem) o;

        if (alpha != that.alpha) return false;
        if (!title.equals(that.title)) return false;
        if (!contentDescription.equals(that.contentDescription)) return false;
        if (!icon.equals(that.icon)) return false;
        return rect.equals(that.rect);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + contentDescription.hashCode();
        result = 31 * result + icon.hashCode();
        result = 31 * result + rect.hashCode();
        result = 31 * result + alpha;
        return result;
    }
}
