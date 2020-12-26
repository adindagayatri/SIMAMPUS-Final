package com.e.simampuscrud.Peminjaman;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditPeminjaman extends AppCompatActivity {

    DBHelper helper;
    TextView TxJudulBuku, TxNamaPeminjam, TxTanggalPinjam;
    Spinner TxStatusPinjam;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_peminjaman);

        helper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.COLOUMN_ID, 0);

        TxJudulBuku = (TextView) findViewById(R.id.txJudul_Buku_Edit);
        TxNamaPeminjam = (TextView) findViewById(R.id.txNama_Edit);
        TxTanggalPinjam = (TextView) findViewById(R.id.txTanggal_Pinjam_Edit);
        TxStatusPinjam = (Spinner) findViewById(R.id.spStatus_Edit);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTanggalPinjam.setOnClickListener(new View.OnClickListener() {
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
                TxTanggalPinjam.setText(dateFormatter.format(newDate.getTime()));
            }
        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData(){
        Cursor cursor = helper.oneDataPeminjaman(id);
        if(cursor.moveToFirst()){
            String judulBuku = cursor.getString(cursor.getColumnIndex(DBHelper.COLOUMN_JUDUL));
            String namaPeminjamn = cursor.getString(cursor.getColumnIndex(DBHelper.COLOUMN_NAME));
            String tglPinjam = cursor.getString(cursor.getColumnIndex(DBHelper.COLOUMN_TGL));
            String statusPinjam = cursor.getString(cursor.getColumnIndex(DBHelper.COLOUMN_STATUS));

            TxJudulBuku.setText(judulBuku);
            TxNamaPeminjam.setText(namaPeminjamn);
            TxTanggalPinjam.setText(tglPinjam);

            if (statusPinjam.equals("Belum Kembali")){
                TxStatusPinjam.setSelection(0);
            }else if(statusPinjam.equals("Sudah Kembali")){
                TxStatusPinjam.setSelection(1);
            }
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
                String judulBuku = TxJudulBuku.getText().toString().trim();
                String namaPeminjam = TxNamaPeminjam.getText().toString().trim();
                String tglPeminjaman = TxTanggalPinjam.getText().toString().trim();
                String statusPinjam = TxStatusPinjam.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.COLOUMN_JUDUL, judulBuku);
                values.put(DBHelper.COLOUMN_NAME, namaPeminjam);
                values.put(DBHelper.COLOUMN_TGL, tglPeminjaman);
                values.put(DBHelper.COLOUMN_STATUS, statusPinjam);

                if (judulBuku.equals("") || namaPeminjam.equals("") || tglPeminjaman.equals("") || statusPinjam.equals("")){
                    Toast.makeText(EditPeminjaman.this, "Data Peminjaman Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                }
                else{
                    helper.updateDataPeminjaman(values, id);
                    Toast.makeText(EditPeminjaman.this, "Data Peminjaman Berhasil Di Edit !", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        switch (item.getItemId()){
            case R.id.delete_edit:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditPeminjaman.this);
                builder.setMessage("Hapus Data Peminjaman ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        helper.deleteDataPeminjaman(id);
                        Toast.makeText(EditPeminjaman.this, "Data Peminjaman Berhasil Di Hapus !", Toast.LENGTH_SHORT).show();
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

