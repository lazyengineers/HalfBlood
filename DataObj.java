package com.lazyengineers.dell1.halfblood;

import android.content.Context;

import java.util.ArrayList;

public class DataObj {
private static DataObj sData;
    private ArrayList<Data> mData=new ArrayList<>();
    public static DataObj get(Context context) {
        if (sData == null) {
            sData = new DataObj(context);
        }
        return sData;
    }

    public DataObj(Context context) {
        mData=new ArrayList<>();

    }
    public void addData(Data d){
   //    List<Data>mData=new ArrayList<>();
        mData.add(d);
    }
    public ArrayList<Data> getDatas() {
        return mData;
    }
    public void deletedatas(){
        mData=new ArrayList<>();

    }
    public Data getData() {
        for(Data data:mData) {

            return data;
        }
        return null;



    }
}
