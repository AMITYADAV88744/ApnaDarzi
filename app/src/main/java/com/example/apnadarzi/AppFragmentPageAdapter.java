package com.example.apnadarzi;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class AppFragmentPageAdapter extends FragmentPagerAdapter {

    public AppFragmentPageAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeFragment.newInstance("amit");
            case 1:
                return DesignerFragment.newInstance();
            case 2:
                return AccountFragment.newInstance();
            default:
                throw new RuntimeException("Not supported");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
