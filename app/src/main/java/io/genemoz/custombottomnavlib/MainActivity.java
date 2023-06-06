package io.genemoz.custombottomnavlib;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.Objects;

import io.genemoz.custombottomnavlib.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



//        var barBackgroundColor: Int
//        @ColorInt get() = _barBackgroundColor
//        set(@ColorInt value) {
//            _barBackgroundColor = value
//            paintBackground.color = value
//            invalidate()
//        }

    }

//    int barBackgroundColor = 0;
//    Paint paintBackground = new Paint();
//
//    public void setBarBackgroundColor(@ColorInt int value) {
//        barBackgroundColor = value;
//        paintBackground.setColor(value);
//        invalidate();
//    }
//
//    public int getBarBackgroundColor() {
//        return barBackgroundColor;
//    }

//    data class BottomBarItem (
//            var title: String,
//            var contentDescription : String,
//            val icon:Drawable,
//            var rect:RectF= RectF(),
//            var alpha: Int
//)
//
//    public class BottomBarItem {
//
//        String title;
//        String contentDescription;
//        Drawable icon;
//        RectF rect;
//        int alpha;
//
//        public BottomBarItem(String title, String contentDescription, Drawable icon, RectF rect, int alpha) {
//            this.title = title;
//            this.contentDescription = contentDescription;
//            this.icon = icon;
//            this.rect = rect;
//            this.alpha = alpha;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getContentDescription() {
//            return contentDescription;
//        }
//
//        public void setContentDescription(String contentDescription) {
//            this.contentDescription = contentDescription;
//        }
//
//        public Drawable getIcon() {
//            return icon;
//        }
//
//        public void setIcon(Drawable icon) {
//            this.icon = icon;
//        }
//
//        public RectF getRect() {
//            return rect;
//        }
//
//        public void setRect(RectF rect) {
//            this.rect = rect;
//        }
//
//        public int getAlpha() {
//            return alpha;
//        }
//
//        public void setAlpha(int alpha) {
//            this.alpha = alpha;
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (!(o instanceof BottomBarItem)) return false;
//            BottomBarItem that = (BottomBarItem) o;
//            return alpha == that.alpha && Objects.equals(title, that.title) && Objects.equals(contentDescription, that.contentDescription) && Objects.equals(icon, that.icon) && Objects.equals(rect, that.rect);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(title, contentDescription, icon, rect, alpha);
//        }
//    }

}