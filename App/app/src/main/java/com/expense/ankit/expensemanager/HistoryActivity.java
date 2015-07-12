package com.expense.ankit.expensemanager;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Window;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;

//import org.apache.http.client.methods.HttpPost;


public class HistoryActivity extends ActionBarActivity implements DownloadResultReceiver.Receiver {

    private ListView listView = null;

    private listViewAdapter listviewAdapter = null;

    private DownloadResultReceiver mReceiver;

    final String url = "http://192.168.1.2:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
                /* Allow activity to show indeterminate progressbar */
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = (ListView) findViewById(R.id.listview);

        /* Starting Download Service */
        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, DownloadService.class);

        /* Send optional extras to Download IntentService */
        intent.putExtra("url", url);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

        startService(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
                /* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                String[] results = resultData.getStringArray("result");
                SessionManager session = new SessionManager(getApplicationContext());
               ArrayList<User> users = new ArrayList<User>();
                for (int i=0;i<results.length;i++)
                {   String[] usr = results[i].split("/");
                    User temp = new User();
                    temp.name = usr[0];
                    temp.userId = Integer.parseInt(usr[1]);
                    temp.balance = Double.parseDouble(usr[2]);
                    users.add(temp);
                    session.createSession(temp.userId,usr[0],usr[2]);
                }
                //System.out.println(results[0]);
                /* Update ListView with result */
                listviewAdapter = new listViewAdapter(HistoryActivity.this, users);
                listView.setAdapter(listviewAdapter);
               // listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
               //     @Override
               //     public void onItemClick(AdapterView<?> parent, View view, int position,
               //                             long id) {
               //         User  item = (User) listView.getItemAtPosition(position);
               //         Intent myIntent = new Intent(arg0.getContext(), NewPage.class);
               //         myIntent.putExtra("Url", d.link);
               //         startActivity(myIntent);
                      //  String item = ((TextView) view).getText().toString();
                       // Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();

               //     }
               // });
                break;
            case DownloadService.STATUS_ERROR:
                /* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
