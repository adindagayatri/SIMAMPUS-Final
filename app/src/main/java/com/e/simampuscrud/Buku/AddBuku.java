package com.e.simampuscrud.Buku;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBuku extends AppCompatActivity {

    DBHelper helper;
    EditText TxKodeBuku, TxJudulBuku, TxPenulis, TxPenerbit, TxTahunTerbit, TxJumlahHalaman, TxRakBuku, TxTanggalMasukBuku;
    Spinner SpKategori;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buku);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.COLUMN_1, 0);

        TxKodeBuku = (EditText)findViewById(R.id.txKode_buku_Add);
        TxJudulBuku = (EditText)findViewById(R.id.txJudul_Buku_Add);
        TxPenulis = (EditText)findViewById(R.id.txPenulis_Buku_Add);
        TxPenerbit = (EditText)findViewById(R.id.txPenerbit_Buku_Add);
        TxTahunTerbit = (EditText)findViewById(R.id.txTahun_Terbit_Add);
        TxJumlahHalaman = (EditText)findViewById(R.id.txJumlah_Halaman_Add);
        TxRakBuku = (EditText)findViewById(R.id.txRak_Buku_Add);
        TxTanggalMasukBuku = (EditText)findViewById(R.id.txTglMasuk_Buku_Add);
        SpKategori = (Spinner)findViewById(R.id.spKategori_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTanggalMasukBuku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_add:
                String kode_buku = TxKodeBuku.getText().toString().trim();
                String judul_buku = TxJudulBuku.getText().toString().trim();
                String penulis_buku = TxPenulis.getText().toString().trim();
                String penerbit_buku = TxPenerbit.getText().toString().trim();
                String tahun_terbit = TxTahunTerbit.getText().toString().trim();
                String jumlah_halaman = TxJumlahHalaman.getText().toString().trim();
                String rak_buku = TxRakBuku.getText().toString().trim();
                String tgl_masuk_buku = TxTanggalMasukBuku.getText().toString().trim();
                String kategori_buku = SpKategori.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_2, kode_buku);
                values.put(DBHelper.COLUMN_3, judul_buku);
                values.put(DBHelper.COLUMN_4, penulis_buku);
                values.put(DBHelper.COLUMN_5, penerbit_buku);
                values.put(DBHelper.COLUMN_6, tahun_terbit);
                values.put(DBHelper.COLUMN_7, jumlah_halaman);
                values.put(DBHelper.COLUMN_8, rak_buku);
                values.put(DBHelper.COLUMN_9, kategori_buku);
                values.put(DBHelper.COLUMN_10, tgl_masuk_buku);

                if (kode_buku.equals("") || judul_buku.equals("") ||
                        penulis_buku.equals("") || penerbit_buku.equals("") ||
                        tahun_terbit.equals("") || jumlah_halaman.equals("") ||
                        rak_buku.equals("") || tgl_masuk_buku.equals("")
                ){
                    Toast.makeText(AddBuku.this, "Data Buku Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertDataBuku(values);
                    Toast.makeText(AddBuku.this, "Data Buku Tersimpan !", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}