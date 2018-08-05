package com.lazyengineers.dell1.halfblood;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
//import android.util.;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class ViewAdapterRequestDonor extends RecyclerView.Adapter<ViewAdapterRequestDonor.MyViewHolder> {
   private LayoutInflater inflater;
    List<Data1> dataList= Collections.emptyList();

        public ViewAdapterRequestDonor(Context context, List<Data1> data) {
            inflater=LayoutInflater.from(context);
        this.dataList=data;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row1,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    Data1 cObj=dataList.get(position);
        String name=cObj.getMessage();
        String email=cObj.getTime();
       // .d("LISTF_", "onBindViewHolder called" + name + email);
        String uniqueName=cObj.getName();
        holder.cname.setText(name);
        char c=uniqueName.charAt(0);
//        .d("_name123",uniqueName+c);
//        String s="R.color."+c;
//       // int i=Integer.parseInt(s);
        //MeFragment.color(s);
//MeFragment m=new MeFragment();
        holder.cUname.setTextColor(color(c));
        holder.cemail.setText(email);
        holder.cUname.setText(uniqueName);

           }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView cname;
        TextView cemail;
        TextView cUname;
        public MyViewHolder(View view) {
            super(view);
        cname=(TextView)view.findViewById(R.id.message);
            cemail=(TextView)view.findViewById(R.id.time_1);
            cUname=(TextView)view.findViewById(R.id.uniqueName);


        }
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

}

