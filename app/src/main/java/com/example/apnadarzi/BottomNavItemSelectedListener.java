package com.example.apnadarzi;


import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavItemSelectedListener
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final Toolbar toolbar;
    private final ViewPager viewPager;

    public BottomNavItemSelectedListener(ViewPager viewPager, Toolbar toolbar) {
        this.toolbar = toolbar;
        this.viewPager = viewPager;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        toolbar.setTitle(item.getTitle());
        int itemId = item.getItemId();
        if (itemId == R.id.navigation_home) {
            viewPager.setCurrentItem(0);
            return true;
        } else if (itemId == R.id.navigation_designer) {
            viewPager.setCurrentItem(1);
            return true;
        } else if (itemId == R.id.navigation_account) {
            viewPager.setCurrentItem(2);
            return true;
        }
        return false;
    }
}
