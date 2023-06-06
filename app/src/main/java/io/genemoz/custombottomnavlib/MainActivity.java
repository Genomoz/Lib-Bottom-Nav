package io.genemoz.custombottomnavlib;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import io.genemoz.custombottomnav.OnItemSelectedListener;
import io.genemoz.custombottomnavlib.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    FragmentAdapter fragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), getLifecycle());
        binding.viewPager.setAdapter(fragmentAdapter);


        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int pos) {
                if (pos == 0) {
                    binding.viewPager.setCurrentItem(0);
                    binding.bottomBar.setBadge(2);
                }
                else if (pos == 1)
                    binding.viewPager.setCurrentItem(1);
                else if (pos == 2) {
                    binding.viewPager.setCurrentItem(2);
                    binding.bottomBar.removeBadge(2);
                } else if (pos == 3)
                    binding.viewPager.setCurrentItem(3);
                else
                    binding.viewPager.setCurrentItem(0);
                return true;
            }
        });


        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    binding.bottomBar.setItemActiveIndex(0);

                } else if (position == 1) {
                    binding.bottomBar.setItemActiveIndex(1);

                } else if (position == 2) {
                    binding.bottomBar.setItemActiveIndex(2);

                } else if (position == 3) {
                    binding.bottomBar.setItemActiveIndex(3);

                } else {
                    binding.bottomBar.setItemActiveIndex(0);

                }
            }
        });

        binding.bottomBar.setBadge(2);


    }


//        var barBackgroundColor: Int
//        @ColorInt get() = _barBackgroundColor
//        set(@ColorInt value) {
//            _barBackgroundColor = value
//            paintBackground.color = value
//            invalidate()
//        }


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