package com.lazyengineers.dell1.halfblood;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class DataObj1 {
private static DataObj1 sData;
    private List<Data1> mData=new ArrayList<>();
    public static DataObj1 get(Context context) {
        if (sData == null) {
            sData = new DataObj1(context);
        }
        return sData;
    }

    public DataObj1(Context context) {
        mData=new ArrayList<>();

    }
    public void addData(Data1 d){
   //    List<Data>mData=new ArrayList<>();
        mData.add(d);
    }
    public List<Data1> getDatas() {
        return mData;
    }
    public void deletedatas(){
       // mData=new ArrayList<>();
        mData.clear();

    }
    public Data1 getData() {
        for(Data1 data:mData) {

            return data;
        }
        return null;



    }
}
