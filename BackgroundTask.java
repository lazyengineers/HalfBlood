package com.lazyengineers.dell1.halfblood;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
//import android.util.;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class BackgroundTask extends AsyncTask<String,Void,String> {
//    AlertDialog alertDialog;
    String userNameFile="com.example.dell1.halfblood.usernames";
    Context ctx;
    String Json_String;
    ProgressDialog progressDialog;
    BackgroundTask(Context ctx){

        this.ctx= ctx;
    }
    @Override
    protected void onPreExecute() {
    showProgressBar();
    }




    @Override
    protected String doInBackground(String... params) {
        //String reg_url="http://192.168.15.236/php/register.php";
       // String reg_url="https://nicestudent20.000webhostapp.com/register2.php";

        //String login_url="http://192.168.15.236/php/login.php";
       // String login_url="https://nicestudent20.000webhostapp.com/login2.php";
        String reg_url="https://nicestudent20.000webhostapp.com/register2.php";
        String login_url="https://nicestudent20.000webhostapp.com/login2.php";
        String json="https://nicestudent20.000webhostapp.com/json_get_array.php";
        String registerBlood="https://nicestudent20.000webhostapp.com/registerblood.php";
        String filterResult="https://nicestudent20.000webhostapp.com/filter_result.php";
        String userInfo="https://nicestudent20.000webhostapp.com/getUserData.php";
        String searchResult="https://nicestudent20.000webhostapp.com/search.php";
        String uniqueName2="https://nicestudent20.000webhostapp.com/uniquename.php";
      //  int file_length=0;
        String method=params[0];//parameter is execute method
        if(method.equals("register"))
        {
            String name=params[1];
            String user_name=params[2];
            String user_pass=params[3];

            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);//true bcoz we send data
                OutputStream OS=httpURLConnection.getOutputStream();
              //  .d("_register","0");
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+//encode data
                        URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(user_pass,"UTF-8");
               // .d("_register","1");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
              //  .d("_register", "2");
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
              //  .d("_register","3");
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
               // .d("_register", response);
                return response;
            }
            catch (MalformedURLException e) {
              //  .d("_register","e1");
                e.printStackTrace();
            }
            catch (ProtocolException e) {
               // .d("_register","e2");
                e.printStackTrace();
            }
            catch (IOException e) {
              //  .d("_register","e3");
                e.printStackTrace();
               // .e("MYAPP", "exception", e);
            }
        }else if(method.equals("login"))
        {
            String login_name=params[1];
            String login_pass=params[2];
            try {
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data=URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"+
                        URLEncoder.encode("login_pass","UTF-8")+"="+URLEncoder.encode(login_pass,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                //.e("MYAPP2", "exception", e);

            }
        }else if (method.equals("getJson")){
            try {
                URL url=new URL(json);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
               // file_length=httpURLConnection.getContentLength();
                InputStream inputStream =httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((Json_String=bufferedReader.readLine())!=null){
                stringBuilder.append(Json_String+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("fill blood database")){
            try {
                String name=params[1];
                String phone_number=params[2];
                String status=params[3];
                String blood_group=params[4];
                String city=params[5];
                String address=params[6];
                String uniqueName=getUniqueName();
                URL url=new URL(registerBlood);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);//true bcoz we send data
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+//encode data
                        URLEncoder.encode("phone_number","UTF-8")+"="+URLEncoder.encode(phone_number,"UTF-8")+"&"+
                        URLEncoder.encode("status","UTF-8")+"="+URLEncoder.encode(status,"UTF-8")+"&"+
                        URLEncoder.encode("blood_group","UTF-8")+"="+URLEncoder.encode(blood_group,"UTF-8")+"&"+
                        URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                        URLEncoder.encode("uniqueName","UTF-8")+"="+URLEncoder.encode(uniqueName, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                //res="Blood donor registration successfull";
               // Toast.makeText(ctx,response, Toast.LENGTH_LONG).show();

                return  response;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
                // .e("MYAPP", "exception", e);
            }
        }else if(method.equals("getFilterResult")){
            String bloodGroup=params[1];
            String city=params[2];
            String district=params[3];
            try {
                URL url=new URL(filterResult);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);//true bcoz we send data
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("bloodGroup","UTF-8")+"="+URLEncoder.encode(bloodGroup,"UTF-8")+"&"+//encode data
                        URLEncoder.encode("city","UTF-8")+"="+URLEncoder.encode(city,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(district,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream inputStream =httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((Json_String=bufferedReader.readLine())!=null){
                    stringBuilder.append(Json_String+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            }else if(method.equals("getUserDetails")) {
            String unique_name = params[1];

            try {
                URL url = new URL(userInfo);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("unique_name", "UTF-8") + "=" + URLEncoder.encode(unique_name, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS, "iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                while ((Json_String = bufferedReader.readLine()) != null) {
                    stringBuilder.append(Json_String + "\n");
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                //.e("MYAPP2", "exception", e);

            }
        }
            else if(method.equals("getSearchResult")){
                String input=params[1];

                try {
                    URL url=new URL(searchResult);
                    HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);//true bcoz we send data
                    OutputStream OS=httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                    String data=URLEncoder.encode("input","UTF-8")+"="+URLEncoder.encode(input,"UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();

                    InputStream inputStream =httpURLConnection.getInputStream();
                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while((Json_String=bufferedReader.readLine())!=null){
                        stringBuilder.append(Json_String+"\n");
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return stringBuilder.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
 else if(method.equals("uniquename"))
        {
            String name=params[1];

            try {
                URL url=new URL(uniqueName2);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);//true bcoz we send data
                OutputStream OS=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String data= URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name, "UTF-8");//encode data

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
                // .e("MYAPP", "exception", e);
            }
        }
    return null;
    }
    @Override
    protected void onPostExecute(String result) {
        //progressDialog.hide();
        progressDialog.dismiss();
        if(result!=null){
            if(result.trim().equals("Database connection successfullData inserted")){
                Toast.makeText(ctx, "Registration successfull", Toast.LENGTH_SHORT).show();
                //.d("_register", result);
                Intent i=new Intent(ctx,LoginActivity.class);
                ctx.startActivity(i);
            }

        else if (result.trim().contains("Database connection successfull Login successfull...Welcome"))
        {
            Toast.makeText(ctx, "Login successfull", Toast.LENGTH_SHORT).show();
            String name=result.substring(60).trim();
            setUniqueName1(name);


            Intent i=new Intent(ctx,ViewPagerBaseActivity.class);
          //  i.putExtra("JSON",Json_String);
            ctx.startActivity(i);

//            Bundle bundle=new Bundle();
//            bundle.putString(Json_String,"Json");
//            ctx.setArguments(bundle);




            } else if(result.trim().contains("Database connection successfullLogin Failed.Try Again")){
            Toast.makeText(ctx,"Login Failed.Try Again", Toast.LENGTH_SHORT).show();

        }else if(result.contains("server_response")){
//            Fragment fragment = new Fragment();
//            Bundle bundle = new Bundle();
//            bundle.putString("JSON", result);
//            fragment.setArguments(bundle);
//            AllDonorsFragment allDonorsFragment=new AllDonorsFragment() ;
//            LayoutInflater inflater = (LayoutInflater)   ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            allDonorsFragment.getJson(result, inflater.inflate(R.layout.fragment_alldonors,null));
//            if() {
            Intent i = new Intent(ctx, ViewPagerBaseActivity.class);
              i.putExtra("JSON",result);
                ctx.startActivity(i);
            }else if(result.contains("filter_response")){
//            CoordinatorLayout coordinatorLayout=new CoordinatorLayout(ctx);
//            Snackbar snackbar=Snackbar.make(coordinatorLayout,"filtered result downloaded",Snackbar.LENGTH_LONG);
//            snackbar.show();

            Toast.makeText(ctx,"result is filtered", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(ctx,ViewPagerBaseActivity.class);
            i.putExtra("Filter",result);
            ctx.startActivity(i);
        }else if(result.contains("user_response")){
            Toast.makeText(ctx,"user data downloaded", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent("com.example.dell1.halfblood.2");
            intent.putExtra("User_data",result);
            LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(ctx);
            localBroadcastManager.sendBroadcast(intent);
        }else if(result.contains("search_response")){
            Toast.makeText(ctx,"searched data downloaded", Toast.LENGTH_SHORT).show();

            Intent i=new Intent(ctx,ViewPagerBaseActivity.class);
            i.putExtra("Search",result);
            ctx.startActivity(i);
//        }else if(result.contains("FireBaseToken")){
//            Toast.makeText(ctx,result, Toast.LENGTH_SHORT).show();
//
        }
             else if(result.contains("getFullname")){
              //  .d("GLOWF_", "onPostMethod called");

                Intent i=new Intent(ctx,ViewPagerBaseActivity.class);
                i.putExtra("getFullname",result);
              //  .d("GLOWF_",result);

                ctx.startActivity(i);
             }else if(result.contains("This username is ")){
           // .d("_userName",result);
            if(result.contains("This username is not available.Try another one")){
                Toast.makeText(ctx, "This username is not available.Try another one", Toast.LENGTH_LONG).show();
//                Intent i=new Intent(ctx,SignupActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                i.putExtra("username", "false");
//                ctx.startActivity(i);
                Intent intent = new Intent("com.example.dell1.halfblood");
                intent.putExtra("username", "false");
                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
            }else if(result.contains("This username is available")){
                Toast.makeText(ctx,"This username is available",Toast.LENGTH_LONG).show();
//                Intent i=new Intent(ctx,SignupActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                i.putExtra("username", "true");
//                ctx.startActivity(i);
                Intent intent = new Intent("com.example.dell1.halfblood");
                intent.putExtra("username", "true");
                LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);

            }
        }

        }
        else{
            String res="Check your internet connectivity";
            Toast.makeText(ctx, res, Toast.LENGTH_LONG).show();
        }
    }


    public  String getUniqueName(){
    SharedPreferences sharedPref = ctx.getSharedPreferences(userNameFile, Context.MODE_PRIVATE);
    String uniqueName=sharedPref.getString("name","null");
        //uniqueName.toUpperCase();
    return uniqueName;
}

public void setUniqueName1(String name){
    SharedPreferences sharedPref = ctx.getSharedPreferences(userNameFile,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();
    if(name.length()>=1){
    name=name.substring(0,1).toUpperCase()+name.substring(1);
    }
  //  .d("_name123",name+"set");
    editor.putString("name", name);
    editor.commit();
}
          //  alertDialog.setMessage(result);
           // alertDialog.show();

    @Override
    protected void onCancelled() {
        super.onCancelled();
        progressDialog.dismiss();
    }
public void showProgressBar(){
    progressDialog=new ProgressDialog(ctx,R.style.Loadingdialog);

    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    progressDialog.show();

}
}



