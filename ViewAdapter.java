package com.lazyengineers.dell1.halfblood;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.MyViewHolder> {
   private LayoutInflater inflater;
    Context ctx;
    ArrayList<Data> dataList= new ArrayList<>();
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference=storage.getReference();
        public ViewAdapter(Context context,ArrayList<Data> data) {
            inflater=LayoutInflater.from(context);
        this.dataList=data;
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row,parent,false);

        MyViewHolder holder=new MyViewHolder(view,ctx,dataList);
        return holder;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    Data cObj=dataList.get(position);
        String name=cObj.getName();
        String email=cObj.getEmail();
        String pass=cObj.getPassword();
        String bloodGroup=cObj.getBloodGroup();
        String uniqueName=cObj.getUniqueName();
        String uniqueName2=uniqueName+".jpg";
        StorageReference riversRef = storageReference.child(uniqueName2);
        if(Glide.with(holder.cimage.getContext())
                .using(new FirebaseImageLoader())
                .load(riversRef)!=null) {
            Glide.with(holder.cimage.getContext())
                    .using(new FirebaseImageLoader())
                    .load(riversRef)
                    .into(holder.cimage);
        }

        holder.cname.setText(name);
        holder.cemail.setText(email);
        holder.cpass.setText(pass);
        if(bloodGroup.length()>=1){
            bloodGroup=bloodGroup.substring(0,1).toUpperCase()+bloodGroup.substring(1);
        }
        holder.cblood.setText(bloodGroup);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView cname;
        TextView cemail;
        TextView cpass;
        TextView cblood;
        de.hdodenhof.circleimageview.CircleImageView cimage;
        ArrayList<Data> datas=new ArrayList<Data>();
        Context ctx;
        public MyViewHolder(View view,Context ctx,ArrayList<Data> datas) {
            super(view);
            this.datas=datas;
            this.ctx=ctx;
            view.setOnClickListener(this);
            cimage=(de.hdodenhof.circleimageview.CircleImageView)view.findViewById(R.id.cimage);
        cname=(TextView)view.findViewById(R.id.cname);
            cemail=(TextView)view.findViewById(R.id.cemail);
            cpass=(TextView)view.findViewById(R.id.cpass);
            cblood=(TextView)view.findViewById(R.id.cblood);
        }

        @Override
        public void onClick(View v) {

            int position=getAdapterPosition();
            Data data=this.datas.get(position);
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+data.getEmail()));
            v.getContext().startActivity(intent);
        }
    }

}
