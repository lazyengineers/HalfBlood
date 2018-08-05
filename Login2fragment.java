package com.lazyengineers.dell1.halfblood;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class Login2fragment extends Fragment {
    Button login;
    EditText Email;
    EditText Password;
    private FirebaseAuth mAuth;
    String method;
    String email;
    String password;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login2, container, false);
        login=(Button)v.findViewById(R.id.login2);
        Email=(EditText)v.findViewById(R.id.lemail);
        Password=(EditText)v.findViewById(R.id.lpassword);
        mAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressBar();
                 method="login";
                 email=Email.getText().toString();
                 password=Password.getText().toString();
                if(email!=null&&password!=null) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                      //  .d("FIRE_LOGIN", "signInWithEmail:success");
                                        progressDialog.dismiss();
                                        BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                                        backgroundTask.execute(method, email, password);
                                        //getActivity().finish();
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progressDialog.dismiss();
                                      //  .w("FIRE_LOGIN", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Login failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }

            }
        });
        return v;
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
    public void showProgressBar(){
        progressDialog=new ProgressDialog(getActivity(),R.style.Loadingdialog);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

    }

}