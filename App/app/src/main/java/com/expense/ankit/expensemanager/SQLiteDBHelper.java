package com.expense.ankit.expensemanager;

/**
 * Created by SAnkit on 7/12/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Expensedetail.db";
    public static final String TRANSACTION_TABLE_NAME = "tran_saction";
    public static final String TRANSACTION_COLUMN_ID = "userid";
    public static final String TRANSACTION_COLUMN_NAME = "name";
    public static final String TRANSACTION_COLUMN_EMAIL = "email";
    public static final String TRANSACTION_COLUMN_AMOUNT = "sharedamount";
    //public static final String TRANSACTION_COLUMN_CITY = "place";
    //public static final String TRANSACTION_COLUMN_PHONE = "phone";
    private HashMap hp;

    public SQLiteDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table tran_saction " +
                        "(userid int primary key,email text, name text, sharedamount money)"
        );
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from tran_saction where userid="+id+"", null );
        return res;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS tran_saction");
        onCreate(db);
    }

    public boolean insertTransaction  (int id,String name, String email, Double amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userid",id);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("sharedamount", amount);
        db.insert("tran_saction", null, contentValues);
        return true;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TRANSACTION_TABLE_NAME);
        return numRows;
    }

    public boolean updateTransaction (int id,String name, String email, Double sharedamount)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("sharedamount", sharedamount);
        db.update("tran_saction", contentValues, "userid = ? ", new String[] { Integer.toString(id)} );
        return true;
    }

    public Integer deleteTransaction (int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tran_saction",
                "userid = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<User> getAllTransasction() {
        ArrayList<User> array_list = new ArrayList<User>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from tran_saction", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            User temp = new User();
            temp.userId = Integer.parseInt(res.getString(res.getColumnIndex(TRANSACTION_COLUMN_ID)));
            temp.name = res.getString(res.getColumnIndex(TRANSACTION_COLUMN_NAME));
            temp.email = res.getString(res.getColumnIndex(TRANSACTION_COLUMN_EMAIL));
            temp.balance = Double.parseDouble(res.getString(res.getColumnIndex(TRANSACTION_COLUMN_AMOUNT)));
            array_list.add(temp);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean deleteAllTransaction() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Delete from tran_saction");
    return true;
    }
}
