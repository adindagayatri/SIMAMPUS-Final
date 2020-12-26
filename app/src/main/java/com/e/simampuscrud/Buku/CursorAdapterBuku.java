package com.e.simampuscrud.Buku;

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

public class CursorAdapterBuku extends CursorAdapter {

    private LayoutInflater layoutInflater;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CursorAdapterBuku(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = layoutInflater.inflate(R.layout.row_buku, viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.ListID = (TextView)v.findViewById(R.id.listID);
        holder.ListJudulBuku = (TextView)v.findViewById(R.id.listJudulBuku);
        holder.ListPenulis = (TextView)v.findViewById(R.id.listPenulis);
        holder.ListTahunTerbit = (TextView)v.findViewById(R.id.listTahunTerbit);
        holder.ListRakBuku = (TextView)v.findViewById(R.id.listRakBuku);
        v.setTag(holder);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder)view.getTag();

        holder.ListID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_1)));
        holder.ListJudulBuku.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_3)));
        holder.ListPenulis.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_4)));
        holder.ListTahunTerbit.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_6)));
        holder.ListRakBuku.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_8)));
    }

    class MyHolder{
        TextView ListID;
        TextView ListJudulBuku;
        TextView ListPenulis;
        TextView ListTahunTerbit;
        TextView ListRakBuku;
    }
}