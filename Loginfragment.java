package com.lazyengineers.dell1.halfblood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
//import android.util.;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginfragment extends Fragment {
    Button login;
    Button signup;
    Button skip;
    String userNameFile="com.example.dell1.halfblood.usernames";
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login, container, false);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mHandler, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
      //  MobileAds.initialize(getActivity(), "ca-app-pub-1793665008632836~7137744298");
//if(!isNetworkAvailable()){
////    Intent in = new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS );
//    Intent intent = new Intent(Intent.ACTION_MAIN);
//    intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
//    startActivity(intent);
//}

        mAuth = FirebaseAuth.getInstance();

        login=(Button)v.findViewById(R.id.login);
        signup=(Button)v.findViewById(R.id.signup);
        skip=(Button)v.findViewById(R.id.skip);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Boolean nameisnull=getUniqueName().equals("null");
       // .d("_name123",nameisnull+getUniqueName());
        if(!isNetworkAvailable()){
            login.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
            skip.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(), "Connect to the internet and try again", Toast.LENGTH_LONG).show();
        }
        if(getUniqueName()==null||nameisnull){
            skip.setVisibility(View.INVISIBLE);
         //   .d("_name123","skip should be invisible");
            skip.setBackgroundColor(getResources().getColor(R.color.white));

        }
        if(getUniqueName()!=null&&isNetworkAvailable()&&!nameisnull&&currentUser!=null){
            Intent i=new Intent(getActivity(),ViewPagerBaseActivity.class);
            startActivity(i);
           // getActivity().finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getActivity(),Login2Activity.class);
                startActivity(i);

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),SignupActivity.class);
                startActivity(i);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String method="getJson";
//                BackgroundTask backgroundTask =new BackgroundTask(getActivity());
//                backgroundTask.execute(method);

               Intent i=new Intent(getActivity(),ViewPagerBaseActivity.class);
                startActivity(i);
               // getActivity().finish();

            }
        });
        return v;
    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            if(isNetworkAvailable())
            {
                login.setVisibility(View.VISIBLE);
                signup.setVisibility(View.VISIBLE);
            }
            }

        };
    private Boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo active=connectivityManager.getActiveNetworkInfo();
        return active!=null&&active.isConnected();
    }
    public String getUniqueName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name", "null");
        return uniqueName;
    }


}

