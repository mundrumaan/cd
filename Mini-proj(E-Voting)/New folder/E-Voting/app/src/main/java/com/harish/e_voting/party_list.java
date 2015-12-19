package com.harish.e_voting;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.harish.e_voting.restapi.RestIntraction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class party_list extends AppCompatActivity {

    //progress dialog
    private ProgressDialog pDialog;
    //url to authenticate user
    private static String url_auth_user = "http://192.168.43.15:80/hksdev/get_all_parties.php";
    private static String url_add_vote = "http://192.168.43.15:80/hksdev/add_user_vote.php";
    //JSON Node Names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_PARTIES = "parties";
    private static final String TAG_PID = "pid";
    private static final String TAG_PNAME = "pname";
    private static final String TAG_CNAME = "cname";
    private static final String TAG_UID = "UID";


    JSONArray parties = null;

    ListView plist;

    String uid;

    PartyListAdapter adapter;
    public ArrayList<party_list_model> lvArr = new ArrayList<party_list_model>();

    private static party_list pListActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pListActivity = this;
        setContentView(R.layout.activity_party_list);

        plist = (ListView) findViewById(R.id.party_lv);
        setupToolbar();


        Intent i = getIntent();
        uid = i.getStringExtra(TAG_UID);
        new LoadAllParties().execute();
    }


    protected void setupToolbar() {
        Toolbar actionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBarToolbar);
    }

    public void userVoteClick(String pid) {
        new AddVote().execute(uid, pid);
    }


    class AddVote extends AsyncTask<String, Void, Void> {

        private RestIntraction restIntraction;
        private String response, message = "-";
        int success = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(party_list.this);
            pDialog.setMessage("Submitting Vote Please Wait");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String uidStr = params[0];
                String pidStr = params[1];
                restIntraction = new RestIntraction(url_add_vote);
                restIntraction.AddParam("pid", pidStr);
                restIntraction.AddParam("uid", uidStr);
                restIntraction.Execute(1);
                response = restIntraction.getResponse();
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject != null) {
                    if (jsonObject.has(TAG_SUCCESS)) {
                        success = jsonObject.getInt(TAG_SUCCESS);
                        message = jsonObject.getString(TAG_MESSAGE);
                        System.out.println(message);
                    }
                }
            } catch (JSONException e) {
                System.out.println("JSONException Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception Error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            pDialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            if (success == 1) {
                Intent in = new Intent(party_list.this, user_login.class);
                Toast.makeText(party_list.this, "Vote submitted successfully", Toast.LENGTH_SHORT).show();
                startActivity(in);
            } else {
                Toast.makeText(getApplicationContext(), "Failure...", Toast.LENGTH_LONG);
            }
        }
    }

    class LoadAllParties extends AsyncTask<Void, Void, Void> {

        private RestIntraction restIntraction;
        private String response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(party_list.this);
            pDialog.setMessage("Loading Parties. Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                restIntraction = new RestIntraction(url_auth_user);
                restIntraction.Execute(1);
                response = restIntraction.getResponse();
                JSONObject jsonObject = new JSONObject(response);

                int success = jsonObject.getInt(TAG_SUCCESS);
                if (success == 1) {
                    parties = jsonObject.getJSONArray(TAG_PARTIES);
                    for (int i = 0; i < parties.length(); i++) {
                        JSONObject c = parties.getJSONObject(i);

                        String pname = c.getString(TAG_PNAME);
                        String cname = c.getString(TAG_CNAME);
                        String pid = c.getString(TAG_PID);

                        party_list_model model = new party_list_model();
                        model.setPid(pid);
                        model.setcName(cname);
                        model.setpName(pname);
                        lvArr.add(model);
                    }
                } else {
                    System.out.println("Parties not found");
                }
            } catch (JSONException e) {
                System.out.println("JSONException Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Exception Error: " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void avoid) {
            pDialog.dismiss();
            adapter = new PartyListAdapter(party_list.this, lvArr);
            plist.setAdapter(adapter);
        }
    }

    public static party_list getInstance() {
        return pListActivity;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_party_list, menu);
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
