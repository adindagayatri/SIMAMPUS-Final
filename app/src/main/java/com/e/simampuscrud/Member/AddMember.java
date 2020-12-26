package com.e.simampuscrud.Member;

import androidx.appcompat.app.AppCompatActivity;

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

import com.e.simampuscrud.DBHelper;
import com.e.simampuscrud.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMember extends AppCompatActivity {
    DBHelper helper;
    EditText TxKodeMember, TxNamaMember, TxTempatLahir, TxTglLahir, TxAlamat, TxNoTelp;
    Spinner SpJK;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        helper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.COLUMN_11, 0);

        TxKodeMember = (EditText)findViewById(R.id.txKode_Member_Add);
        TxNamaMember = (EditText)findViewById(R.id.txNama_Add);
        TxTempatLahir = (EditText)findViewById(R.id.txTempat_Lahir_Add);
        TxTglLahir = (EditText)findViewById(R.id.txTanggal_Lahir_Add);
        TxAlamat = (EditText)findViewById(R.id.txAlamat_Add);
        TxNoTelp = (EditText)findViewById(R.id.txNo_Telepon_Add);
        SpJK = (Spinner)findViewById(R.id.spJenis_Kelamin_Add);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        TxTglLahir.setOnClickListener(new View.OnClickListener() {
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
                TxTglLahir.setText(dateFormatter.format(newDate.getTime()));
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
                String kode_member = TxKodeMember.getText().toString().trim();
                String nama_member = TxNamaMember.getText().toString().trim();
                String tempat_lahir = TxTempatLahir.getText().toString().trim();
                String tanggal_lahir = TxTglLahir.getText().toString().trim();
                String alamat = TxAlamat.getText().toString().trim();
                String no_telp = TxNoTelp.getText().toString().trim();
                String jenis_kelamin = SpJK.getSelectedItem().toString().trim();

                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_12, kode_member);
                values.put(DBHelper.COLUMN_13, nama_member);
                values.put(DBHelper.COLUMN_14, tempat_lahir);
                values.put(DBHelper.COLUMN_15, tanggal_lahir);
                values.put(DBHelper.COLUMN_16, jenis_kelamin);
                values.put(DBHelper.COLUMN_17, alamat);
                values.put(DBHelper.COLUMN_18, no_telp);

                if (kode_member.equals("") || nama_member.equals("") ||
                        tempat_lahir.equals("") || tanggal_lahir.equals("") ||
                        alamat.equals("") || no_telp.equals("")
                ){
                    Toast.makeText(AddMember.this, "Data Member Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                }else{
                    helper.insertDataMember(values);
                    Toast.makeText(AddMember.this, "Data Member Tersimpan !", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}