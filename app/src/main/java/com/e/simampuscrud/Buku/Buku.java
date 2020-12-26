package com.e.simampuscrud.Buku;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Buku extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBHelper helper;
    LayoutInflater inflater;
    View dialogView;
    TextView TvKode, TvJudul, TvPenulis, TvPenerbit, TvTahunTerbit, TvJmlHalaman, TvRakBuku, TvTglMasukBuku, TvKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buku);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Buku.this, AddBuku.class));
            }
        });

        helper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list_buku);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_buku, menu);
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
        Cursor cursor = helper.allDataBuku();
        CursorAdapterBuku customCursorAdapter = new CursorAdapterBuku(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long x) {
        TextView getId = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneDataBuku(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(Buku.this);
        builder.setTitle("Pilih Opsi");

        //Add a list
        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(Buku.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_buku, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        TvKode = (TextView)dialogView.findViewById(R.id.tv_Kode);
                        TvJudul = (TextView)dialogView.findViewById(R.id.tv_JudulBuku);
                        TvPenulis = (TextView)dialogView.findViewById(R.id.tv_Penulis);
                        TvPenerbit = (TextView)dialogView.findViewById(R.id.tv_Penerbit);
                        TvTahunTerbit = (TextView)dialogView.findViewById(R.id.tv_TahunTerbit);
                        TvJmlHalaman = (TextView)dialogView.findViewById(R.id.tv_JumlahHalaman);
                        TvRakBuku = (TextView)dialogView.findViewById(R.id.tv_RakBuku);
                        TvTglMasukBuku = (TextView)dialogView.findViewById(R.id.tv_TglMasukBuku);
                        TvKategori = (TextView)dialogView.findViewById(R.id.tv_Kategori);

                        TvKode.setText("Kode Buku                   : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_2)));
                        TvJudul.setText("Judul Buku                  : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_3)));
                        TvPenulis.setText("Penulis                         : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_4)));
                        TvPenerbit.setText("Penerbit                       : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_5)));
                        TvTahunTerbit.setText("Tahun Terbit                : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_6)));
                        TvJmlHalaman.setText("Jumlah Halaman        : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_7)));
                        TvRakBuku.setText("Rak Buku                      : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_8)));
                        TvKategori.setText("Kategori                        : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_9)));
                        TvTglMasukBuku.setText("Tanggal Masuk Buku  : " + cur.getString(cur.getColumnIndex(DBHelper.COLUMN_10)));

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
                        Intent iddata = new Intent(Buku.this, EditBuku.class);
                        iddata.putExtra(DBHelper.COLUMN_1, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Buku.this);
                        builder1.setMessage("Hapus Data Buku ?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteDataBuku(id);
                                Toast.makeText(Buku.this, "Data Buku Berhasil Di Hapus !", Toast.LENGTH_SHORT).show();
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