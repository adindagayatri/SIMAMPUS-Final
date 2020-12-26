package com.e.simampuscrud.Peminjaman;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.Member.CursorAdapterMember;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.simampuscrud.R;

import org.w3c.dom.Text;

public class Peminjaman extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView listView;
    DBHelper helper;
    LayoutInflater inflater;
    View dialogView;
    TextView id_peminjaman, nama_peminjam, judul_buku, tanggal_pinjam, status_pinjam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peminjaman);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Peminjaman.this, AddPeminjaman.class));
            }
        });

        helper = new DBHelper(this);
        listView = (ListView) findViewById(R.id.list_peminjaman);
        listView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_peminjaman, menu);
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
        Cursor cursor = helper.allDataPeminjaman();
        CursorAdapterPeminjaman customCursorAdapter = new CursorAdapterPeminjaman(this, cursor, 1);
        listView.setAdapter(customCursorAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int i, long x){
        TextView getId = (TextView)view.findViewById(R.id.listID);
        final long id = Long.parseLong(getId.getText().toString());
        final Cursor cur = helper.oneDataPeminjaman(id);
        cur.moveToFirst();

        final AlertDialog.Builder builder = new AlertDialog.Builder(Peminjaman.this);
        builder.setTitle("Pilih Opsi");

        String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        final AlertDialog.Builder viewData = new AlertDialog.Builder(Peminjaman.this);
                        inflater = getLayoutInflater();
                        dialogView = inflater.inflate(R.layout.view_peminjaman, null);
                        viewData.setView(dialogView);
                        viewData.setTitle("Lihat Data");

                        id_peminjaman = (TextView) dialogView.findViewById(R.id.idPeminjaman);
                        nama_peminjam = (TextView) dialogView.findViewById(R.id.NamaPeminjam);
                        judul_buku = (TextView) dialogView.findViewById(R.id.judulBuku);
                        tanggal_pinjam = (TextView) dialogView.findViewById(R.id.tanggalPeminjaman);
                        status_pinjam = (TextView) dialogView.findViewById(R.id.statusPeminjaman);

                        id_peminjaman.setText("Kode Peminjaman       :  " + cur.getString(cur.getColumnIndex(DBHelper.COLOUMN_ID)));
                        nama_peminjam.setText("Nama Peminjam          : " + cur.getString(cur.getColumnIndex(DBHelper.COLOUMN_NAME)));
                        judul_buku.setText("Judul Buku                    : " + cur.getString(cur.getColumnIndex(DBHelper.COLOUMN_JUDUL)));
                        tanggal_pinjam.setText("Tanggal Peminjaman  :  " + cur.getString(cur.getColumnIndex(DBHelper.COLOUMN_TGL)));
                        status_pinjam.setText( "Status Peminjaman    : " + cur.getString(cur.getColumnIndex(DBHelper.COLOUMN_STATUS)));

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
                        Intent iddata = new Intent(Peminjaman.this, EditPeminjaman.class);
                        iddata.putExtra(DBHelper.COLOUMN_ID, id);
                        startActivity(iddata);
                }
                switch (which){
                    case 2:
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(Peminjaman.this);
                        builder1.setMessage("Hapus Data Peminjaman ?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteDataPeminjaman(id);
                                Toast.makeText(Peminjaman.this, "Data Peminjaman Berhasil Di Hapus !", Toast.LENGTH_SHORT).show();
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