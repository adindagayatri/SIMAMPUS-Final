package com.e.simampuscrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_simampus";
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
        db = getWritableDatabase();
    }

    static final String NamaTabel = "tb_buku";
    public static final String COLUMN_1 = "_id";
    public static final String COLUMN_2 = "kode_buku";
    public static final String COLUMN_3 = "judul_buku";
    public static final String COLUMN_4 = "penulis";
    public static final String COLUMN_5 = "penerbit";
    public static final String COLUMN_6 = "tahun_terbit";
    public static final String COLUMN_7 = "jumlah_halaman";
    public static final String COLUMN_8 = "rak_buku";
    public static final String COLUMN_9 = "kategori";
    public static final String COLUMN_10 = "tanggal_masuk_buku";


    static final String NamaTabel2 = "tb_member";
    public static final String COLUMN_11 = "_id";
    public static final String COLUMN_12 = "kode_member";
    public static final String COLUMN_13 = "nama_member";
    public static final String COLUMN_14 = "tempat_lahir";
    public static final String COLUMN_15 = "tanggal_lahir";
    public static final String COLUMN_16 = "jenis_kelamin";
    public static final String COLUMN_17 = "alamat";
    public static final String COLUMN_18 = "no_telepon";

    static final String NamaTabel3 = "tb_peminjaman";
    public static final String COLOUMN_ID = "_id";
    public static final String COLOUMN_NAME = "nama_peminjam";
    public static final String COLOUMN_JUDUL = "judul_buku";
    public static final String COLOUMN_TGL = "tgl_pinjam";
    public static final String COLOUMN_STATUS = "status";


    private  static final String SQL_CREATE_ENTRIES_BUKU = "CREATE TABLE " + NamaTabel + "(" + COLUMN_1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_2 + " TEXT, " + COLUMN_3 + " TEXT, " + COLUMN_4 + " TEXT, "
            + COLUMN_5 + " TEXT, " + COLUMN_6 + " TEXT, " + COLUMN_7 + " TEXT, "
            + COLUMN_8 + " TEXT, " + COLUMN_9 + " TEXT, " + COLUMN_10 + " TEXT)";

    private  static final String SQL_CREATE_ENTRIES_MEMBER = "CREATE TABLE " + NamaTabel2 + "(" + COLUMN_11 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_12 + " TEXT, " + COLUMN_13 + " TEXT, " + COLUMN_14 + " TEXT, "
            + COLUMN_15 + " TEXT, " + COLUMN_16 + " TEXT, " + COLUMN_17 + " TEXT, "
            + COLUMN_18 + " TEXT)";

    private  static final String SQL_CREATE_ENTRIES_PEMINJAMAN = "CREATE TABLE "+ NamaTabel3 + " ("+ COLOUMN_ID +
            " INTEGER PRIMARY KEY autoincrement, " + COLOUMN_NAME + " TEXT NOT NULL," + COLOUMN_JUDUL +
            " TEXT NOT NULL," + COLOUMN_TGL + " TEXT NOT NULL," + COLOUMN_STATUS + " TEXT NOT NULL" + ")";


    public static final String SQL_DELETE_ENTRIES_MEMBER = "DROP TABLE IF EXISTS "+NamaTabel2;
    public static final String SQL_DELETE_ENTRIES_BUKU = "DROP TABLE IF EXISTS "+NamaTabel;
    public static final String SQL_DELETE_ENTRIES_PEMINJAMAN = "DROP TABLE IF EXISTS "+NamaTabel3;


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_BUKU);
        db.execSQL(SQL_CREATE_ENTRIES_MEMBER);
        db.execSQL(SQL_CREATE_ENTRIES_PEMINJAMAN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_BUKU);
        db.execSQL(SQL_DELETE_ENTRIES_MEMBER);
        db.execSQL(SQL_DELETE_ENTRIES_PEMINJAMAN);
    }


    //DATA BUKU
    public Cursor allDataBuku(){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel, null);
        return cur;
    }

    public Cursor oneDataBuku(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel + " WHERE " + COLUMN_1 + "=" + id, null);
        return cur;
    }

    public void insertDataBuku(ContentValues values){

        db.insert(NamaTabel, null, values);
    }
    public void updateDataBuku(ContentValues values, long id){
        db.update(NamaTabel, values, COLUMN_1 + "=" + id, null);
    }
    public void deleteDataBuku(long id){

        db.delete(NamaTabel, COLUMN_1 + "=" + id, null);
    }



    //DATA MEMBER
    public Cursor allDataMember(){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel2, null);
        return cur;
    }

    public Cursor oneDataMember(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel2 + " WHERE " + COLUMN_11 + "=" + id, null);
        return cur;
    }

    public void insertDataMember(ContentValues values){

        db.insert(NamaTabel2, null, values);
    }
    public void updateDataMember(ContentValues values, long id){
        db.update(NamaTabel2, values, COLUMN_11 + "=" + id, null);
    }
    public void deleteDataMember(long id){

        db.delete(NamaTabel2, COLUMN_11 + "=" + id, null);
    }


    //DATA PEMINJAMAN
    public Cursor allDataPeminjaman(){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel3, null);
        return cur;
    }
    public Cursor oneDataPeminjaman(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + NamaTabel3 + " WHERE " + COLOUMN_ID + "=" + id, null);
        return cur;
    }

    public void insertDataPeminjaman(ContentValues values){

        db.insert(NamaTabel3, null, values);
    }

    public void updateDataPeminjaman(ContentValues values, long id){
        db.update(NamaTabel3, values, COLOUMN_ID + "=" + id, null);
    }

    public void deleteDataPeminjaman(long id){

        db.delete(NamaTabel3, COLOUMN_ID + "=" + id, null);
    }

    // GET MEMBER DAN GET BUKU

}
