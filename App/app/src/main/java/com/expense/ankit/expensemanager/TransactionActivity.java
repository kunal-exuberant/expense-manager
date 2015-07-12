package com.expense.ankit.expensemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class TransactionActivity extends ActionBarActivity {

    private ListView listView = null;
    private ListView obj;
    private listViewAdapter lAdaptor = null;
    private SQLiteDBHelper myDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        obj = (ListView) findViewById(R.id.listview);

        myDb = new SQLiteDBHelper(this);
        final ArrayList<User> array_List = myDb.getAllTransasction();
        if(array_List.size()!=0) {
            lAdaptor = new listViewAdapter(this, array_List);
            obj.setAdapter(lAdaptor);
            obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    int id_To_Search = arg2 + 1;

                    Bundle dataBundle = new Bundle();
                    String email = array_List.get(id_To_Search).email;
                    dataBundle.putString("Email",email );
                    dataBundle.putInt("id",id_To_Search);
                    Intent intent = new Intent(getApplicationContext(), DisplayTran.class);
                    intent.putExtras(dataBundle);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_new) {
            Intent i = new Intent(getApplicationContext(), DisplayTran.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
