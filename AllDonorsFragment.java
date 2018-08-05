package com.lazyengineers.dell1.halfblood;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.SearchView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AllDonorsFragment extends android.support.v4.app.Fragment implements SearchView.OnQueryTextListener {


    String json;
    String json2;
    String bloodType2;
    String inputFromUser;
    String searchJSON;

    String city2;
    String district2;
    String filterJson;
    String filterJson2;
    String friendsJson;
    JSONObject jsonObject;
    JSONArray jsonArray;
    private RecyclerView mRecyclerView;
    private ViewAdapter mAdapter;
    private EditText bloodType;
    private EditText city;
    private EditText district;
    private Button filter;
    String userNameFile="com.example.dell1.halfblood.usernames";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private DatabaseReference mMessage=myRef.child("friendRequests");
    public AllDonorsFragment() {
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.menu_items, menu);
        //.d("_options", "on create options menu called");
        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // .d("GLOW_", "Oncreate called");
        View v = inflater.inflate(R.layout.fragment_alldonors, container, false);
        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView1);
      bloodType=(EditText)v.findViewById(R.id.filter_bloodType);
        city=(EditText)v.findViewById(R.id.filter_city);
        district=(EditText)v.findViewById(R.id.filter_district);
        filter=(Button)v.findViewById(R.id.filter_button);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//layout manager handles TextView positioning and defines scrolling behaviour.
       filter.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String method = "getFilterResult";
              // .d("_filter", "clicked");
              // ActivityManager mngr = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);

              // List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

//               if(taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
//                   .d("_filter", "This is last activity in the stack");
//                   Intent launch = new Intent(getActivity(),ViewPagerBaseActivity.class );
//                   launch.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                   startActivity(launch);
//               }else{
//                   .d("_filter","not");
//               }

               bloodType2 = bloodType.getText().toString();
               city2 = city.getText().toString();
               district2 = district.getText().toString();
               if (filterJson == null) {
                   BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                   backgroundTask.execute(method, bloodType2, city2, district2);
               }
               }
       });

        getDataFromNet();
       // getActivity().finish();
       // parseJsonAndAddData();
        //updateUI();
        return v;
    }
//    public void getFilterData(){
//    Intent intent=getActivity().getIntent();
//        filterJson=intent.getStringExtra("Filter");
//    parseJsonAndAddData(filterJson);
//    }
    public void getDataFromNet(){
      //  .d("GLOW_", "getDataFromNet called");

        String method="getJson";
        Intent intent = getActivity().getIntent();
       // .d("GLOW_","TOKEN recevied===="+intent.toString());
        json = intent.getStringExtra("JSON");
        filterJson=intent.getStringExtra("Filter");
        searchJSON=intent.getStringExtra("Search");
        friendsJson=intent.getStringExtra("getFullname");

        String name =intent.getStringExtra("onClickName");
        String email=intent.getStringExtra("onClickEmail");
        String password=intent.getStringExtra("onClickPassword");
        String bloodGroup=intent.getStringExtra("onClickBloodGroup");

         if(json==null&&filterJson==null&&searchJSON==null && name==null && friendsJson==null) {
            //    if(count ==0){
          //  .d("GLOW_", "executing background task json==null&&filterJson==null&&searchJSON==null"+null);

            BackgroundTask backgroundTask = new BackgroundTask(getActivity());
            backgroundTask.execute(method);


        }
//        else if(email!=null&&password!=null&&bloodGroup!=null){
//            String method1="getToken";
////            .d("Var_","name1="+name);
////            .d("Var_","email1="+name);
////            .d("Var_","phone number1="+name);
////            .d("Var_","bloodgroup1="+name);
//            .d("GLOW_", "on click info received"+name+email+password+bloodGroup);
//
//            BackgroundTask backgroundTask = new BackgroundTask(getActivity());
//            backgroundTask.execute(method1,name,email,password,bloodGroup);
//        }else if(token!=null){
//
//            .d("GLOW_","final area token" +token);

//        }
    else if(json!=null&&filterJson==null&&searchJSON==null && name==null&& friendsJson==null){
            json2= json.substring(31);
          //  .d("GLOW_", "parsing downloaded json json!=null&&filterJson==null&&searchJSON==null"+name);

            parseJsonAndAddData(json2);            //updateUI();
//         Intent i=new Intent(getActivity(),.class);
//            i.putExtra("JSON1",name);
//            getActivity().startActivity(i);
            // allDonors = (TextView) v.findViewById(R.id.alldonors);

            //updateUI();
            //allDonors.setText(json);
        }
        else if(filterJson!=null){
            filterJson2= filterJson.substring(31);
          //  .d("GLOW_", "parsing downloaded filtered json filterJson!=null");

            parseJsonAndAddData(filterJson2);
        }
        else if(searchJSON!=null)
        {
          //  .d("GLOW_","intent for search searchJSON!=null");

            searchJSON=intent.getStringExtra("Search");


            searchJSON= searchJSON.substring(31);
            parseJsonAndAddData(searchJSON);
        }else if (name!=null && email!=null && password!=null&&bloodGroup!=null&& friendsJson==null){

               //  .d("GLOWF_","friendsJson==null");
                    method="getFullname";
                 BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                 backgroundTask.execute(method,name,email,password,bloodGroup);

         }else if(friendsJson!=null){
             JSONObject jsonObject;
             JSONArray jsonArray;
           //  .d("GLOWF_","friendsJson!=null");

             friendsJson= friendsJson.substring(31);
             //parseJsonAndAddData(friendsJson);
             try {
                 jsonObject = new JSONObject(friendsJson);
                 jsonArray = jsonObject.getJSONArray("getFullname");
              //   .d("GLOWF_","array====="+jsonArray.toString());

                 //int count = 0;
                String friendsFullname;
                // while (count < jsonArray.length()) {
                     JSONObject JO = jsonArray.getJSONObject(0);

                 friendsFullname = JO.getString("fullname");
                   // .d("GLOWF_", "===================" + friendsFullname);
                   // myRef.setValue();
                 String key = myRef.push().getKey();
                 mMessage.child(key).child("friendRequest").setValue("friend request for " + friendsFullname + " from " + getUniqueName());

//                 PrintWriter writer=new PrintWriter(getUniqueName(),"UTF-8");
//
//                  writer.println(friendsFullname);
                  //   count++;
                // }

                 } catch (JSONException e) {
                 e.printStackTrace();
             }
// catch(FileNotFoundException e){
//                 e.printStackTrace();
//             } catch (UnsupportedEncodingException e) {
//                 e.printStackTrace();
//             }

         }


    }




    @Override
    public void onResume() {
        super.onResume();
       // .d("GLOW_", "onResume called");

       // getDataFromNet();
        updateUI();
    }

    private void updateUI(){
       // .d("GLOW_", "update ui called");

        DataObj dataObj=DataObj.get(getActivity());
        ArrayList<Data> e=dataObj.getDatas();
        if(mAdapter==null) {
           // .d("GLOW_", "adapter is null");

            mAdapter = new ViewAdapter(getActivity(), e);
            mRecyclerView.setAdapter(mAdapter);
        }else{
            //.d("GLOW_", "adapter is not null");

            mAdapter.notifyDataSetChanged();
        }
    }


    public void parseJsonAndAddData(String json2) {
    try {
      //  .d("GLOW_", "parse json and add data called");

        jsonObject = new JSONObject(json2);
        if(searchJSON!=null){
            jsonArray = jsonObject.getJSONArray("search_response");
        }
        if(filterJson!=null){
            jsonArray = jsonObject.getJSONArray("filter_response");
        }
       else if(json!=null) {
            jsonArray = jsonObject.getJSONArray("server_response");
        }
        int count = 0;
        String name, email, password,bloodType3,uniqueName;
       DataObj dataObj=DataObj.get(getActivity());
        if(dataObj.getDatas()!=null) {
            DataObj.get(getActivity()).deletedatas();
        }
        while (count < jsonArray.length()) {
            JSONObject JO = jsonArray.getJSONObject(count);
            name = JO.getString("fullname");
            email = JO.getString("email");
            password = JO.getString("password");
            bloodType3=JO.getString("bloodGroup");
            uniqueName=JO.getString("uniqueName");
            Data d = new Data();
            d.setName(name);
            d.setEmail(email);
            d.setPassword(password);
            d.setBloodGroup(bloodType3);
            d.setUniqueName(uniqueName);
            DataObj.get(getActivity()).addData(d);
            count++;

        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
}
    public String getUniqueName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name","null");
        return uniqueName;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        inputFromUser=query;
        if(searchJSON==null){
            String method="getSearchResult";
            BackgroundTask backgroundTask=new BackgroundTask(getActivity());
            backgroundTask.execute(method, inputFromUser);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}



