package com.example.projcopy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.projcopy.Adapter.FragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity4 extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager2 viewpager2;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        tablayout=findViewById(R.id.tablayout);
        viewpager2=findViewById(R.id.viewpager2);

        tablayout.addTab(tablayout.newTab().setText("SIGN IN"));
        tablayout.addTab(tablayout.newTab().setText("SIGN UP"));

        FragmentManager fragmentManager=getSupportFragmentManager();
        adapter=new FragmentAdapter(fragmentManager , getLifecycle());
        viewpager2.setAdapter(adapter);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tablayout.selectTab(tablayout.getTabAt(position));
            }
        });

    }
}