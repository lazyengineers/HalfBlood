package com.lazyengineers.dell1.halfblood;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
//import android.util.;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends android.support.v4.app.Fragment {

    Button getbloodrequest;
    private RecyclerView mRecyclerView;
    private ViewAdapterRequestDonor mAdapter;
static int count3=0;
    Button showMyProfile;
    static final int PROFILE_FORM=0;
    String userNameFile="com.example.dell1.halfblood.usernames";

    public MeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_items2, menu);
        //.d("_options", "on create options menu called");
        //MenuItem menuItem=menu.findItem(R.id.action_profile);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId()==R.id.action_profile){
//            Intent i=new Intent(getActivity(),ProfileForm.class);
//            startActivityForResult(i,PROFILE_FORM);
         if(item.getItemId()==R.id.signout){

            AuthUI.getInstance()
                    .signOut();
                setUniqueNameToNull();
             Intent i=new Intent(getActivity(),LoginActivity.class);
            // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(i);
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        public void onComplete(@NonNull Task<Void> task) {
//                            // ...
//                        }
//                    });
        }else if(item.getItemId()==R.id.Your_Donor_Profile){
            Intent i=new Intent(getActivity(),ProfileForm.class);
            startActivityForResult(i,PROFILE_FORM);
        }
        return super.onOptionsItemSelected(item);
    }

    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference=firebaseDatabase.getReference();
    private DatabaseReference mMessage=databaseReference.child("Message1");
    String time1;
    String result1;
    EditText input;
    FloatingActionButton sendButton;
    FirebaseAuth AuthUI;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_me, container, false);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_action_name);
        setHasOptionsMenu(true);
        //layout manager handles TextView positioning and defines scrolling behaviour.
        AuthUI=FirebaseAuth.getInstance();
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerViewforChat);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        FirebaseRecyclerOptions<Data1> options =
//                new FirebaseRecyclerOptions.Builder<Data1>()
//                        .setQuery(query, Data1.class)
//                        .build();

        input=(EditText)v.findViewById(R.id.inputText);
        sendButton=(FloatingActionButton)v.findViewById(R.id.inputSend);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
        sendButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red_700)));
        }
            sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                //Toast.makeText(getActivity(), "input given", Toast.LENGTH_SHORT).show();
                result1= input.getText().toString();
                input.setText("");
                 time1 = new SimpleDateFormat("EEE d MMM, h:mm a").format(Calendar.getInstance().getTime());

                String key = databaseReference.push().getKey();

                Data1 d = new Data1(key, result1, time1,getUniqueName());

                mMessage.child(key).setValue(d);
                mMessage.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = dataSnapshot.getChildrenCount();
                        if (count != 0) {
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                Data1 d = data.getValue(Data1.class);
                                DataObj1.get(getActivity()).addData(d);
                            }
                            updateUI();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

displayChat(v);

        return v;
    }



    private void updateUI(){

      //  .d("LIST_", "update ui called");

        DataObj1 dataObj=DataObj1.get(getActivity());
        List<Data1> e=dataObj.getDatas();
        if(mAdapter==null) {
          //  .d("LISTF_", "adapter is null");
            mAdapter = new ViewAdapterRequestDonor(getActivity(), e);
            mRecyclerView.setAdapter(mAdapter);
        }else{
          //  .d("LISTF_", "adapter is not null");

            mAdapter.notifyDataSetChanged();
           // smoothScroller.setTargetPosition(mAdapter.getItemCount() - 1);

        }
    }



    @Override
    public void onResume() {
        super.onResume();
    updateUI();
    }
//    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
//        @Override protected int getVerticalSnapPreference() {
//            return LinearSmoothScroller.SNAP_TO_START;
//        }
//    };
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == PROFILE_FORM) {
            String method = "fill blood database";
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            String city = data.getStringExtra("city");
            String address = data.getStringExtra("address");
            String status = data.getStringExtra("status");
            String bloodType = data.getStringExtra("bloodGroup");
          //  .d("BloodGroup_error",bloodType);
            BackgroundTask backgroundTask = new BackgroundTask(getActivity());
            backgroundTask.execute(method, name, phoneNumber, status, bloodType, city, address);

        }

    }

public void deleteData(){
    DataObj1 dataObj=DataObj1.get(getActivity());
 //   .d("LIST_","delete data called");
    if(dataObj.getDatas()!=null) {
        DataObj1.get(getActivity()).deletedatas();
     //   .d("LIST_", "data deleted");
    }
}
    public String getUniqueName(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name","null");
        return uniqueName;
    }
    public void setUniqueNameToNull(){
        SharedPreferences sharedPref = getActivity().getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
    }

    public void displayChat(View v){
        ListView l=(ListView)v.findViewById(R.id.listViewforChat);
            l.setStackFromBottom(true);
        FirebaseListAdapter<Data1> cadapter=new FirebaseListAdapter<Data1>(getActivity(),Data1.class,R.layout.custom_row3,mMessage) {
            @Override
            protected void populateView(View view, Data1 data1, int i) {
                TextView name,message,time;
                name=(TextView)view.findViewById(R.id.uniqueName2);
                message=(TextView)view.findViewById(R.id.message2);
                time=(TextView)view.findViewById(R.id.time_3);
                char c=data1.getName().charAt(0);
                name.setTextColor(color(c));
                name.setText(data1.getName());
                message.setText(data1.getMessage());
                time.setText(data1.getTime());
            }

        };
                l.setAdapter(cadapter);

     }
    public int color(char c){
        int s=0;
        switch (c) {
            case'A': s = Color.parseColor("#FF9800");break;
            case'B': s = Color.parseColor("#673AB7");break;
            case'C': s = Color.parseColor("#03A9F4");break;
            case'D': s = Color.parseColor("#4CAF50");break;
            case'E': s = Color.parseColor("#FFEB3B");break;
            case'F': s = Color.parseColor("#FF5722");break;
            case'G': s = Color.parseColor("#607D8B");break;
            case'H': s = Color.parseColor("#E91E63");break;
            case'I': s = Color.parseColor("#3F51B5");break;
            case'J': s = Color.parseColor("#00BCD4");break;
            case'K': s = Color.parseColor("#8BC34A");break;
            case'L': s = Color.parseColor("#FFC107");break;
            case'M': s = Color.parseColor("#795548");break;
            case'N': s = Color.parseColor("#9C27B0");break;
            case'O': s = Color.parseColor("#2196F3");break;
            case'P': s = Color.parseColor("#009688");break;
            case'Q': s = Color.parseColor("#CDDC39");break;
            case'R': s = Color.parseColor("#FF9800");break;
            case'S': s = Color.parseColor("#F44336");break;
            case'T': s = Color.parseColor("#673AB7");break;
            case'U': s = Color.parseColor("#03A9F4");break;
            case'V': s = Color.parseColor("#4CAF50");break;
            case'W': s = Color.parseColor("#FFEB3B");break;
            case'X': s = Color.parseColor("#FF5722");break;
            case'Y': s = Color.parseColor("#607D8B");break;
            case'Z': s = Color.parseColor("#E91E63");break;
            default:s=Color.parseColor("#E91E63");
        }
        return s;
    }

//    private void scrollMyListViewToBottom() {
//        l.post(new Runnable() {
//            @Override
//            public void run() {
//                // Select the last row so it will scroll into view...
//                l.setSelection(adapter.getCount() - 1);
//            }
//        });
//    }
}

