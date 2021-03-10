package com.example.lab1_duanmau.model;

import java.io.Serializable;
import java.util.Date;

public class HoaDon implements Serializable {
    String MaHD;
    String TenHD;
    Date NgayMua;

    public HoaDon() {
    }

    public HoaDon(String maHD, String tenHD, Date ngayMua) {
        MaHD = maHD;
        TenHD = tenHD;
        NgayMua = ngayMua;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getTenHD() {
        return TenHD;
    }

    public void setTenHD(String tenHD) {
        TenHD = tenHD;
    }

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date ngayMua) {
        NgayMua = ngayMua;
    }
    @Override
    public String toString() {
        return TenHD;
    }
}
