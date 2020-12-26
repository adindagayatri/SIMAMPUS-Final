package com.e.simampuscrud.Peminjaman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddPeminjaman extends AppCompatActivity {
    DBHelper helper;
    EditText txTanggalPinjam;
    AutoCompleteTextView txJudulBuku, txNamaPeminjam;
    Spinner txStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    private static final String[] JUDUL = new String[]{"Persona", "Vio: Don't Mess Up",
            "Laskar Pelangi", "Rahasia Tergelap"};
    private static final String[] NAMA = new String[]{"Dinda", "Candra", "Sheila", "Budi", "Citra"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peminjaman);

        helper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.COLOUMN_ID, 0);
        txJudulBuku = (AutoCompleteTextView) findViewById(R.id.txJudul_Buku_Add);
        txNamaPeminjam = (AutoCompleteTextView) findViewById(R.id.txNama_Add);
        txTanggalPinjam = (EditText) findViewById(R.id.txTanggalPinjam_add);
        txStatus = (Spinner) findViewById(R.id.spStatus_add);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, JUDUL);
        txJudulBuku.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, NAMA);
        txNamaPeminjam.setAdapter(adapter2);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        txTanggalPinjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                txTanggalPinjam.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
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
                String judulBuku = txJudulBuku.getText().toString().trim();
                String namaPeminjamn = txNamaPeminjam.getText().toString().trim();
                String tglPinjam = txTanggalPinjam.getText().toString().trim();
                String statusPinjam = txStatus.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.COLOUMN_JUDUL, judulBuku);
                values.put(DBHelper.COLOUMN_NAME, namaPeminjamn);
                values.put(DBHelper.COLOUMN_TGL, tglPinjam);
                values.put(DBHelper.COLOUMN_STATUS, statusPinjam);

                if (judulBuku.equals("") || namaPeminjamn.equals("") ||
                        tglPinjam.equals("") || statusPinjam.equals("")){
                    Toast.makeText(AddPeminjaman.this, "Data Peminjaman Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertDataPeminjaman(values);
                    Toast.makeText(AddPeminjaman.this, "Data Peminjaman Tersimpan !", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
