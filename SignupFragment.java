package com.lazyengineers.dell1.halfblood;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
//import android.util.;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupFragment extends Fragment {
    Button Signup;
    EditText Fullname;
    EditText Email;
    EditText Password;
    EditText CPassword;
    private FirebaseAuth mAuth;
    Boolean colorOfNameIsGreen=false;
    String name;
    String user_name;
    String user_pass;
    String user_cpass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.signup, container, false);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mHandler, new IntentFilter("com.example.dell1.halfblood"));
        Signup=(Button)v.findViewById(R.id.signup2);
        Fullname=(EditText)v.findViewById(R.id.sfullname);

        Email=(EditText)v.findViewById(R.id.semail);
        Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    checkuniquename();

                    }
            }
        });
        Password=(EditText)v.findViewById(R.id.spassword);
        CPassword=(EditText)v.findViewById(R.id.scpassword);
        mAuth = FirebaseAuth.getInstance();
        Signup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name=Fullname.getText().toString();
            user_name=Email.getText().toString();
            user_pass=Password.getText().toString();
            user_cpass=CPassword.getText().toString();
            Boolean email=isEmailValid(user_name);
            Boolean allValueEntered=!name.matches("")&&!user_name.matches("")&&!user_pass.matches("")&&!user_cpass.matches("");
            Boolean password=user_pass.equals(user_cpass);
            checkuniquename();
           if( Fullname.getCurrentTextColor()==getResources().getColor(R.color.green_500)){
               colorOfNameIsGreen=true;
           }
            if(email&&allValueEntered&&password&&min6char(name, user_name, user_pass)&&colorOfNameIsGreen) {
                mAuth.createUserWithEmailAndPassword(user_name, user_pass)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                  //  .d("FIRE_LOGIN", "createUserWithEmail:success");
                                    String method = "register";
                                    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                                    backgroundTask.execute(method, name, user_name, user_pass);
                                    //getActivity().finish();

                                    // FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                  //  .d("FIRE_LOGIN", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "This email is already registered", Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });


            }else if(!email){
                Toast.makeText(getActivity(),"Enter a valid email id", Toast.LENGTH_SHORT).show();

            }else if(!allValueEntered){
                Toast.makeText(getActivity(),"Enter all details", Toast.LENGTH_SHORT).show();

            }else if(!password) {
                Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_SHORT).show();
            }else if(!min6char(name, user_name, user_pass)){
                Toast.makeText(getActivity(),"Add minimum 6 characters",Toast.LENGTH_SHORT).show();
            }


                //              Intent i=new Intent(getActivity(),Login2Activity.class);
//            startActivity(i);
        }
    });
        return v;

    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title=intent.getStringExtra("username");
            if(title.equals("true")){
              //  .d("_userName1","true");

                Fullname.setTextColor(getResources().getColor(R.color.green_500));

            }else if(title.equals("false")){
              //  .d("_userName1","false");

                Fullname.setTextColor(getResources().getColor(R.color.red_500));
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mHandler);

    }

    boolean min6char(String name,String email,String password){
        if(name.length()>=6&&email.length()>=6&&password.length()>=6){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){

//            Intent i=new Intent(getActivity(),ViewPagerBaseActivity.class);
//            startActivity(i);
        }
    }
public void checkuniquename(){
    String method="uniquename";
    BackgroundTask backgroundTask = new BackgroundTask(getActivity());
    backgroundTask.execute(method, Fullname.getText().toString());

}
}
