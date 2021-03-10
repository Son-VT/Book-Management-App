package com.example.lab1_duanmau.model;

import java.io.Serializable;

public class HoaDonChiTiet implements Serializable {
    String MaHDCT;
    String TenHDCT;
    String MaHD;
    String MaSach;
    int SoLuongMua;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String maHDCT, String tenHDCT, String maHD, String maSach, int soLuongMua) {
        MaHDCT = maHDCT;
        TenHDCT = tenHDCT;
        MaHD = maHD;
        MaSach = maSach;
        SoLuongMua = soLuongMua;
    }

    public String getMaHDCT() {
        return MaHDCT;
    }

    public void setMaHDCT(String maHDCT) {
        MaHDCT = maHDCT;
    }

    public String getTenHDCT() {
        return TenHDCT;
    }

    public void setTenHDCT(String tenHDCT) {
        TenHDCT = tenHDCT;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String maHD) {
        MaHD = maHD;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        MaSach = maSach;
    }

    public int getSoLuongMua() {
        return SoLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        SoLuongMua = soLuongMua;
    }
}
