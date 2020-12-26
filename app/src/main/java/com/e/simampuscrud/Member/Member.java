package com.e.simampuscrud.Member;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Member extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBHelper helper;
    LayoutInflater inflater;
    View dialogView;
    TextView TvKode, TvNama, TvTempatLahir, TvTglLahir, TvJenisKelamin, TvAlamat, TvNoTelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Member.this, AddMember.class));
            }
        });

        helper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list_member);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_member, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Buku/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setListView(){
        Cursor cursor = helper.allDataMember();
        CursorAdapterMember customCursorAdapter = new CursorAdapterMember(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneDataMember(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(Member.this);
        builder.setTitle("Pilih Opsi");

        //Add a list
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(Member.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_member, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        TvKode = (TextView)dialogView.findViewById(R.id.tv_Kode);
                        TvNama = (TextView)dialogView.findViewById(R.id.tv_Nama);
                        TvTempatLahir = (TextView)dialogView.findViewById(R.id.tv_Tempat_Lahir);
                        TvTglLahir = (TextView)dialogView.findViewById(R.id.tv_Tanggal_Lahir);
                        TvJenisKelamin = (TextView)dialogView.findViewById(R.id.tv_Jenis_Kelamin);
                        TvAlamat = (TextView)dialogView.findViewById(R.id.tv_Alamat);
                        TvNoTelp = (TextView)dialogView.findViewById(R.id.tv_No_Telepon);

                        TvKode.setText("Kode Member    : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_12)));
                        TvNama.setText("Nama                  : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_13)));
                        TvTempatLahir.setText("Tempat Lahir     : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_14)));
                        TvTglLahir.setText("Tanggal Lahir     : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_15)));
                        TvJenisKelamin.setText("Jenis kelamin    : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_16)));
                        TvAlamat.setText("Alamat                : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_17)));
                        TvNoTelp.setText("No Telepon        : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_18)));

                        viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        viewData.show();
                }
                switch (which){
                    case 1:
                        Intent iddata = new Intent(Member.this, EditMember.class);
                        iddata.putExtra(DBHelper.COLUMN_11, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Member.this);
                        builder1.setMessage("Hapus Data Member ?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteDataMember(id);
                                Toast.makeText(Member.this, "Data Member Berhasil Di Hapus !", Toast.LENGTH_SHORT).show();
                                setListView();
                            }
                        });
                        builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder1.create();
                        alertDialog.show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }
}