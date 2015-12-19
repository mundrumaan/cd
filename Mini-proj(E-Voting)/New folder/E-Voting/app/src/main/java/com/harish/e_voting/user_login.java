package com.harish.e_voting;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.harish.e_voting.restapi.RestIntraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class user_login extends Activity {

    TextView tv_rtv_link;
    EditText txt_voterID, txt_aadharUID;
    Button btn_submit;
    //progress dialog
    private ProgressDialog pDialog;
    //url to authenticate user
    private static String url_auth_user = "http://192.168.43.15:80/hksdev/auth_user.php";
    //JSON Node Names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_UID = "UID";

    String vid, uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        tv_rtv_link = (TextView) findViewById(R.id.rtv_link);
        btn_submit = (Button) findViewById(R.id.btnSubmit);
        txt_voterID = (EditText) findViewById(R.id.txtVoterID);
        txt_aadharUID = (EditText) findViewById(R.id.txtAadharUID);

        onTouch();
        onClick();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    private void onTouch() {
        tv_rtv_link.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_UP == event.getAction()) {
                    /*new MaterialDialog.Builder(user_login.this)
                            .title("Right To Vote")
                            .content(getResources().getString(R.string.rtv_string_res))
                            .negativeText("OK")
                            .show();*/
                }
                return true;
            }
        });
    }

    private void onClick(){
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                vid = txt_voterID.getText().toString();
                uid = txt_aadharUID.getText().toString();

                new AuthUser().execute();


            }
        });
    }
    public void show_toast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }





    class AuthUser extends AsyncTask<Void, Void, Void> {

        private RestIntraction restIntraction;
        private String response, message="-";
        int success=0;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            pDialog = new ProgressDialog(user_login.this);
            pDialog.setMessage("Please Wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params){
            try{
                restIntraction = new RestIntraction(url_auth_user);
                restIntraction.AddParam("vid", vid);
                restIntraction.AddParam("uid", uid);
                restIntraction.Execute(1);
                response = restIntraction.getResponse();

                JSONObject jsonObject = new JSONObject(response);

                Log.d("Response: ", jsonObject.toString());

                if(jsonObject != null) {
                    if (jsonObject.has(TAG_SUCCESS)) {
                        success = jsonObject.getInt(TAG_SUCCESS);
                        message = jsonObject.getString(TAG_MESSAGE);
                    }
                }
            }catch(Exception e){
                Log.d("Exception Error: ", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            if (success == 1) {
                Intent in = new Intent(user_login.this, party_list.class);
                in.putExtra(TAG_UID, uid);
                user_login.this.startActivityForResult(in, 100);
            } else {
                Toast.makeText(getApplicationContext(), "Failure...", Toast.LENGTH_LONG);
            }
        }

    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 100){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
