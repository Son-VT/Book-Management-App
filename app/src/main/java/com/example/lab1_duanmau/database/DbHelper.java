package com.example.lab1_duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context,"DuAnMau",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE TheLoaiSach(MaTheLoai text primary key, " +
                "TenTheLoai nchar not null ," +
                "MoTa text," +
                "Vitri int)";
        db.execSQL(sql);
                sql = "CREATE TABLE Sach(MaSach text primary key, " +
                "MaTheLoai text references TheLoaiSach(MaTheLoai) not null ," +
                "TacGia text," +
                "NXB text, " +
                "GiaBia float not null,"+
                "SoLuong int not null )";
        db.execSQL(sql);
        sql = "CREATE TABLE HoaDon (MaHoaDon text primary key, NgayMua date not null )";
        db.execSQL(sql);
        sql = "CREATE TABLE HoaDonChiTiet (MaHDCT INTEGER primary key autoincrement, " +
                "MaHoaDon text references HoaDon(MaHoaDon) not null," +
                "MaSach text references Sach(MaSach) not null," +
                "SoLuongMua int not null )";
        db.execSQL(sql);
        sql = "CREATE TABLE NguoiDung (UserName text primary key, " +
                "Password text not null," +
                "SDT text not null," +
                "Hoten text)";
        db.execSQL(sql);
        sql = "INSERT INTO NguoiDung VALUES('admin','admin','0966192404','Vũ Trường Sơn')";
        db.execSQL(sql);
        sql = "INSERT INTO NguoiDung VALUES('son','admin','0944789323','Đồng Phương Quang')";
        db.execSQL(sql);
        sql = "INSERT INTO NguoiDung VALUES('ngoc','admin','0956789324','Trần Minh Ngọc')";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE TheLoaiSach";
        db.execSQL(sql);
        sql = "DROP TABLE Sach";
        db.execSQL(sql);
        sql = "DROP TABLE HoaDon";
        db.execSQL(sql);
        sql = "DROP TABLE HoaDonChiTiet";
        db.execSQL(sql);
        sql = "DROP TABLE NguoiDung";
        db.execSQL(sql);
    }
}
