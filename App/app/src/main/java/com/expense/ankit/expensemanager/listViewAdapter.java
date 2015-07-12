package com.expense.ankit.expensemanager;

/**
 * Created by SAnkit on 7/12/2015.
 */
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class listViewAdapter extends BaseAdapter {
    public ArrayList<User> list;
    Activity activity;

    public listViewAdapter(Activity activity, ArrayList<User> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.viewName);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.viewBalance);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        User map = list.get(position);
        holder.txtFirst.setText(map.get(Constants.FIRST_COLUMN).toString());
        holder.txtSecond.setText(map.get(Constants.SECOND_COLUMN).toString());
        return convertView;
    }

}
