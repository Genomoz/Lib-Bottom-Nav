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



}