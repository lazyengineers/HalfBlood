package com.lazyengineers.dell1.halfblood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by dell1 on 11/25/2017.
 */
public class ViewPagerBasefragment extends Fragment {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, container, false);
        toolbar=(Toolbar)v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        tabLayout=(TabLayout)v.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)v.findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPagerAdapter.addFragments(new AllDonorsFragment(),"Donors");
        //viewPagerAdapter.addFragments(new FriendsFragment(),"Friends and Family");

        viewPagerAdapter.addFragments(new MeFragment(), "ChatBox");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
//        Bundle extras = v.getIntent().getExtras();
//        String value;
//        if (extras != null) {
//            value = extras.getString("key");
//        }
//        Intent i=new Intent();
//        String json=i.getStringExtra("JSON");
//        Intent e=new Intent(getActivity(),AllDonorsFragment.class);
//        e.putExtra("JSON1",json);
//        startActivity(e);

        return v;
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getActivity().getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            //Toast.makeText(this.getActivity(),"search button clicked").show();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
