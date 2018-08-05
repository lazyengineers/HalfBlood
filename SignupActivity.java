package com.lazyengineers.dell1.halfblood;

import android.support.v4.app.Fragment;

/**
 * Created by dell1 on 11/25/2017.
 */
public class SignupActivity extends SingleFragmentActivity {
    protected Fragment createFragment(){
        return new SignupFragment();


    }
}