package com.lazyengineers.dell1.halfblood;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileForm extends AppCompatActivity {
    EditText name;
    EditText phoneNumber;
    EditText city;
    EditText address;
    RadioGroup status;
    RadioGroup bloodGroup;
    Button submit;
    String name1;
    String phoneNumber1;
    String city1;
    String address1;
    String json;
    String filterJson2;
    JSONObject jsonObject;
    JSONArray jsonArray;
   static String status1;
    static int status2;
   static String bloodGroup1;
    static int bloodGroup2;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference=storage.getReference();
    private de.hdodenhof.circleimageview.CircleImageView Image;
    static final int GALLERY_REQUEST =3;
    static final int CAMERA_REQUEST=4;

    String userNameFile="com.example.dell1.halfblood.usernames";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler, new IntentFilter("com.example.dell1.halfblood.2"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_form);
        name=(EditText)findViewById(R.id.profile_name);
        phoneNumber=(EditText)findViewById(R.id.profile_number);
        city=(EditText)findViewById(R.id.profile_city);
        address=(EditText)findViewById(R.id.profile_address);
        status=(RadioGroup)findViewById(R.id.profile_status);
        bloodGroup=(RadioGroup)findViewById(R.id.profile_bloodGroup);
        submit = (Button) findViewById(R.id.profile_submit);

//                if(status.getCheckedRadioButtonId()!=-1){
//                View view=status.findViewById(status.getCheckedRadioButtonId());
//                    view.getTex
                status.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.available:
                                status1 = "Available";break;
                            case R.id.unavailable:
                                status1 = "Unavailable";break;
                            default:status1="Unavailable";
                        }

                    }
                });
                bloodGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.a:
                                bloodGroup1 = "a+";
                                //.d("BloodGroup_error","checkedid on a"+Integer.toString(checkedId));

                                break;
                            case R.id.a2:
                                bloodGroup1 = "a-";
                                break;
                            case R.id.b:
                                bloodGroup1 = "b+";
                                //.d("BloodGroup_error","checkedid on b"+Integer.toString(checkedId));
                                break;
                            case R.id.b2:
                                bloodGroup1 = "b-";break;
                            case R.id.o:
                                bloodGroup1 = "o+";break;
                            case R.id.o2:
                                bloodGroup1 = "o-";break;
                            case R.id.ab:
                                bloodGroup1 = "ab+";break;
                            case R.id.ab2:
                                bloodGroup1 = "ab-";break;
                            case R.id.bbg:
                                bloodGroup1 = "bbg";break;
                            default:
                                bloodGroup1="Unavailable";
                                //.d("BloodGroup_error","default is used"+Integer.toString(checkedId));
                        }
                        //.d("BloodGroup_error","0"+ bloodGroup1);

                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name1 = name.getText().toString();
                        phoneNumber1 = phoneNumber.getText().toString();
                        city1 = city.getText().toString();
                        address1 = address.getText().toString();
                        Intent o = new Intent();
                        o.putExtra("name", name1);
                        o.putExtra("phoneNumber", phoneNumber1);
                        o.putExtra("city", city1);
                        o.putExtra("address", address1);
                        o.putExtra("status", status1);
                        o.putExtra("bloodGroup", bloodGroup1);
                       // .d("BloodGroup_error","0"+ bloodGroup1);
                        setResult(Activity.RESULT_OK, o);
                        finish();


                    }
                });
        Image=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_pic);
        if(savedInstanceState==null) {
            downloadImage();
        }
        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder = new AlertDialog.Builder(ProfileForm.this).create();
                View view = LayoutInflater.from(ProfileForm.this).inflate(R.layout.customdialog, null);
                Button camera = (Button) view.findViewById(R.id.camera);
                Button gallery = (Button) view.findViewById(R.id.gallery);
                //builder.setTitle("Choose an option :");
                builder.setView(view);
                builder.show();
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent();
                        i.setType("image/*");
                        i.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(i, GALLERY_REQUEST);
                        builder.dismiss();
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                        builder.dismiss();
                    }
                });
            }
        });


        getDataFromNet();
    }
    private BroadcastReceiver mHandler=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            json=intent.getStringExtra("User_data");
            getDataFromNet();

        }
    };
    public void getDataFromNet(){

        if (json==null) {
            String method="getUserDetails";
            SharedPreferences sharedPref =this.getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
            String uniqueName=sharedPref.getString("name", "null");

            BackgroundTask backgroundTask=new BackgroundTask(this);
            backgroundTask.execute(method, uniqueName);

        }else if(json!=null){
            filterJson2 = json.substring(31);

            parseJsonAndAddData(filterJson2);
        }

    }
    public void parseJsonAndAddData(String json2) {
        try {
            jsonObject = new JSONObject(json2);
            jsonArray = jsonObject.getJSONArray("user_response");
            int count = 0;
            String name3, status3, city3,address3,blood_group3,phonenumber3;
            //  DataObj1 dataObj=DataObj1.get(this);
//            if(dataObj.getDatas()!=null) {
//                DataObj.get(this).deletedatas();
//            }
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                name3 = JO.getString("Name");
                status3 = JO.getString("Status");
                city3 = JO.getString("City");
                address3 = JO.getString("Address");
                blood_group3 = JO.getString("Blood_group");
                phonenumber3=JO.getString("Phone_number");
                name.setText(name3);
                //email.setText(email3);
                city.setText(city3);
                address.setText(address3);
                if(status3!=null){
                    switch(status3){
                        case "Available":
                            status2 = R.id.available;break;
                        case "Unavailable":
                            status2 = R.id.unavailable;break;
                        default:status2=R.id.unavailable;
                    }
                    status.check(status2);
                }
                //bloodGroup.setText(blood_group3);
                if(blood_group3!=null){
                    switch(blood_group3){
                        case "a+":
                            bloodGroup2 = R.id.a;
                            break;
                        case "a-":
                            bloodGroup2 = R.id.a2;break;
                        case "b+":
                            bloodGroup2 = R.id.b;break;
                        case "b-":
                            bloodGroup2 = R.id.b2;break;
                        case "o+":
                            bloodGroup2 = R.id.o;break;
                        case "o-":
                            bloodGroup2 = R.id.o2;break;
                        case "ab+":
                            bloodGroup2 =R.id.ab;break;
                        case "ab-":
                            bloodGroup2 =R.id.ab2;break;
                        case "bbg":
                            bloodGroup2 =R.id.bbg;break;
                        default:
                            bloodGroup2=R.id.unavailable;
                    }
                    bloodGroup.check(bloodGroup2);

                }
                phoneNumber.setText(phonenumber3);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void downloadImage(){
        SharedPreferences sharedPref = this.getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name", "null");

        String filename = uniqueName +".jpg";


        StorageReference riversRef = storageReference.child(filename);
        final long ONE_MEGABYTE = 1024 * 1024;
        riversRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                Image.setImageBitmap(Bitmap.createScaledBitmap(bmp, Image.getWidth(),
                        Image.getHeight(), false));
                //Toast.makeText(getActivity(),"pic downloaded",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(getApplicationContext(), "unable to download pic", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap;
        if(requestCode== GALLERY_REQUEST && resultCode==Activity.RESULT_OK && data!=null){
            Uri path=data.getData();
            try {


                bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),path);
                uploadImage(path);
                Image.setImageBitmap(bitmap);
                // count2=1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode ==Activity.RESULT_OK) {
            Uri path=data.getData();
            bitmap = (Bitmap) data.getExtras().get("data");
            uploadImage(bitmap);
            //uploadImage(path);

            Image.setImageBitmap(bitmap);
            //count2=2;

        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }

    }
    public void uploadImage(Uri file){
        SharedPreferences sharedPref = this.getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name", "null");

        String filename = uniqueName +".jpg";


        final StorageReference riversRef = storageReference.child(filename);
        UploadTask uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(getApplicationContext(),"pic upload unsuccessfull",Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(getApplicationContext(),"pic uploaded from gallery",Toast.LENGTH_SHORT).show();

                Uri downloadUrl = taskSnapshot.getDownloadUrl();


            }
        });
    }
    public void uploadImage(Bitmap bitmap){
        //  UploadTask uploadTask=null;
//if(count2==1) {
        SharedPreferences sharedPref = this.getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
        String uniqueName=sharedPref.getString("name", "null");


        String filename = uniqueName +".jpg";

        StorageReference image1 = storageReference.child(filename);
        //Image.setDrawingCacheEnabled(true);
        //Image.buildDrawingCache();
        //Bitmap bitmap = Image.getDrawingCache();
        //  Bitmap bitmap = ((BitmapDrawable) Image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask  uploadTask = image1.putBytes(data);
//} else if(count2==2){


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Toast.makeText(getApplicationContext(),"pic uploaded",Toast.LENGTH_SHORT).show();
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

            }
        });
    }

}
