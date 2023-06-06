package io.genemoz.custombottomnavlib;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new HomeFragment();
        else if (position == 1)
            return new ChatFragment();
        else if (position == 2)
            return new MediaFragment();
        else if (position == 3)
            return new FunFragment();
        else
            return new HomeFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
