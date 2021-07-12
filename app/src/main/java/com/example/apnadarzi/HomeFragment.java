package com.example.apnadarzi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private static final String ARG_NAME = "arg_name";

    public static HomeFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_NAME, name);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
            setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        HomeFragmentPageAdapter adapter =
                new HomeFragmentPageAdapter(
                        getChildFragmentManager(), requireArguments().getString(ARG_NAME));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount()-1);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLifecycle().addObserver(new TimberLogger(this));
    }


    private static class HomeFragmentPageAdapter extends FragmentPagerAdapter {

        private final String name;

        public HomeFragmentPageAdapter(FragmentManager fm, String name) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.name = name;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            if (position == 0) {
                return HomeChildFragmentMen.newInstance(position, name);
            } else if (position == 1) {
                return HomeChildFragmentWomen.newInstance(position, name);
            } else {
                return HomeChildFragmentKid.newInstance(position, name);
            }
            //return HomeChildFragment.newInstance(position, name);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

           if( position==0){
               return"Men";
           }else if(position==1)
           {
               return "Women";
           }
           else {
               return "Kids";
           }
        }
    }

}
