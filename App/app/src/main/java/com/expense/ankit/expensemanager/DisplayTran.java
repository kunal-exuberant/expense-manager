package com.expense.ankit.expensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class DisplayTran extends ActionBarActivity {

    private SQLiteDBHelper myDb = null;
    TextView name ;
    TextView email;
    TextView amount;
    Button btn;
    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tran_row);
        name = (TextView) findViewById(R.id.nameText);
        email = (TextView) findViewById(R.id.emailText);
        amount = (TextView) findViewById(R.id.amountText);
        btn = (Button) findViewById(R.id.btnAdd);
        myDb = new SQLiteDBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            int value = extras.getInt("id");
            if(value>0)
            {
                Cursor rs = myDb.getData(value);
                id_To_Update = value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(SQLiteDBHelper.TRANSACTION_COLUMN_NAME));
                //String phon = rs.getString(rs.getColumnIndex(SQLiteDBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(SQLiteDBHelper.TRANSACTION_COLUMN_EMAIL));
                String amou = rs.getString(rs.getColumnIndex(SQLiteDBHelper.TRANSACTION_COLUMN_AMOUNT));

                if (!rs.isClosed())
                {
                    rs.close();
                }

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                email.setText((CharSequence) emai);
                email.setFocusable(false);
                email.setClickable(false);

                amount.setText((CharSequence) amou);

                btn.setText((CharSequence)"Update");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_tran, menu);
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

    public void onAddHit(View view)
    {
        Button btn1 = (Button) view;
        if(btn1.getText().toString() == "Update")
        {
            //int id = myDb.numberOfRows() + 1;
            myDb.updateTransaction(id_To_Update, name.getText().toString(), email.getText().toString(), Double.parseDouble(amount.getText().toString()));
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int id = myDb.numberOfRows() + 1;
            myDb.insertTransaction(id, name.getText().toString(), email.getText().toString(), Double.parseDouble(amount.getText().toString()));
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            Intent i = new Intent(getApplicationContext(), TransactionActivity.class);
            startActivity(i);
        }
        return true;
    }
}
