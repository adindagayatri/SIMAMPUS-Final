package com.e.simampuscrud.Member;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

public class CursorAdapterMember extends CursorAdapter {

    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CursorAdapterMember(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_member, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListNamaMember = (TextView)v.findViewById(R.id.listNamaMember);
        holder.ListAlamat = (TextView)v.findViewById(R.id.listAlamat);
        holder.ListNoTelepon = (TextView)v.findViewById(R.id.listNoTelp);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CursorAdapterMember.MyHolder holder = (CursorAdapterMember.MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_11)));
        holder.ListNamaMember.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_13)));
        holder.ListAlamat.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_17)));
        holder.ListNoTelepon.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_18)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListNamaMember;
        TextView ListAlamat;
        TextView ListNoTelepon;
    }
}