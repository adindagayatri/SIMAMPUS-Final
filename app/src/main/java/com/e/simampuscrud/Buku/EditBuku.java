package com.e.simampuscrud.Buku;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditBuku extends AppCompatActivity {

    DBHelper helper;
    EditText TxKodeBuku, TxJudulBuku, TxPenulis, TxPenerbit, TxTahunTerbit, TxJumlahHalaman, TxRakBuku, TxTanggalMasukBuku;
    Spinner SpKategori;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_buku);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.COLUMN_1, 0);

        TxKodeBuku = (EditText)findViewById(R.id.txKode_Buku_Edit);
        TxJudulBuku = (EditText)findViewById(R.id.txJudul_Buku_Edit);
        TxPenulis = (EditText)findViewById(R.id.txPenulis_Buku_Edit);
        TxPenerbit = (EditText)findViewById(R.id.txPenerbit_Buku_Edit);
        TxTahunTerbit = (EditText)findViewById(R.id.txTahun_Terbit_Edit);
        TxJumlahHalaman = (EditText)findViewById(R.id.txJumlah_Halaman_Edit);
        TxRakBuku = (EditText)findViewById(R.id.txRak_Buku_Edit);
        TxTanggalMasukBuku = (EditText)findViewById(R.id.txTglMasuk_Buku_Edit);
        SpKategori = (Spinner)findViewById(R.id.spKategori_Edit);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTanggalMasukBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        getData();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                TxTanggalMasukBuku.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData(){
        Cursor cursor = helper.oneDataBuku(id);
        if(cursor.moveToFirst()){
            String kodeBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_2));
            String judulBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_3));
            String penulisBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_4));
            String penerbitBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_5));
            String tahunTerbit = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_6));
            String jumlahHalaman = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_7));
            String rakBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_8));
            String tglMasukBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_10));
            String kategoriBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_9));

            TxKodeBuku.setText(kodeBuku);
            TxJudulBuku.setText(judulBuku);
            TxPenulis.setText(penulisBuku);
            TxPenerbit.setText(penerbitBuku);
            TxTahunTerbit.setText(tahunTerbit);
            TxJumlahHalaman.setText(jumlahHalaman);
            TxRakBuku.setText(rakBuku);

            if (kategoriBuku.equals("Romance")){
                SpKategori.setSelection(0);
            }else if(kategoriBuku.equals("Fiksi")){
                SpKategori.setSelection(1);
            }else if(kategoriBuku.equals("Ilmiah")) {
                SpKategori.setSelection(2);
            }else if(kategoriBuku.equals("Non Fiksi")) {
                SpKategori.setSelection(3);
            }else if(kategoriBuku.equals("Cerpen")) {
                SpKategori.setSelection(4);
            }else if(kategoriBuku.equals("Sejarah")) {
                SpKategori.setSelection(5);
            }else if(kategoriBuku.equals("Teknologi")) {
                SpKategori.setSelection(6);
            }

            TxTanggalMasukBuku.setText(tglMasukBuku);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_edit:
                String kodeBuku = TxKodeBuku.getText().toString().trim();
                String judulBuku = TxJudulBuku.getText().toString().trim();
                String penulisBuku = TxPenulis.getText().toString().trim();
                String penerbitBuku = TxPenerbit.getText().toString().trim();
                String tahunTerbit = TxTahunTerbit.getText().toString().trim();
                String jumlahHalaman = TxJumlahHalaman.getText().toString().trim();
                String rakBuku = TxRakBuku.getText().toString().trim();
                String tglMasukBuku = TxTanggalMasukBuku.getText().toString().trim();
                String kategoriBuku = SpKategori.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_2, kodeBuku);
                values.put(DBHelper.COLUMN_3, judulBuku);
                values.put(DBHelper.COLUMN_4, penulisBuku);
                values.put(DBHelper.COLUMN_5, penerbitBuku);
                values.put(DBHelper.COLUMN_6, tahunTerbit);
                values.put(DBHelper.COLUMN_7, jumlahHalaman);
                values.put(DBHelper.COLUMN_8, rakBuku);
                values.put(DBHelper.COLUMN_9, kategoriBuku);
                values.put(DBHelper.COLUMN_10, tglMasukBuku);

                if (kodeBuku.equals("") || judulBuku.equals("") ||
                        penulisBuku.equals("") || penerbitBuku.equals("") ||
                        tahunTerbit.equals("") || jumlahHalaman.equals("") ||
                        rakBuku.equals("") || tglMasukBuku.equals("")
                ){
                    Toast.makeText(EditBuku.this, "Data Buku Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                }else{
                    helper.updateDataBuku(values, id);
                    Toast.makeText(EditBuku.this, "Data Buku Berhasil Di Edit !", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditBuku.this);
                builder.setMessage("Hapus Data Buku ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteDataBuku(id);
                        Toast.makeText(EditBuku.this, "Data Buku Berhasil Di Hapus !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}