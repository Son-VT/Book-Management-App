package com.example.lab1_duanmau.model;

import java.io.Serializable;
import java.util.Date;

public class Sach implements Serializable {
     String MaSach;
     String MaTheLoai;
     String TenSach;
     String TacGia;
     Date NXB;
     double GiaBia;
     int SoLuong;

    public Sach() {
    }

    public Sach(String maSach, String maTheLoai, String tenSach, String tacGia, Date NXB, double giaBia, int soLuong) {
        MaSach = maSach;
        MaTheLoai = maTheLoai;
        TenSach = tenSach;
        TacGia = tacGia;
        this.NXB = NXB;
        GiaBia = giaBia;
        SoLuong = soLuong;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String maSach) {
        MaSach = maSach;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public String getTacGia() {
        return TacGia;
    }

    public void setTacGia(String tacGia) {
        TacGia = tacGia;
    }

    public Date getNXB() {
        return NXB;
    }

    public void setNXB(Date NXB) {
        this.NXB = NXB;
    }

    public double getGiaBia() {
        return GiaBia;
    }

    @Override
    public String toString() {
        return  TenSach +
                "  GiaBia: " + GiaBia ;
    }

    public void setGiaBia(double giaBia) {
        GiaBia = giaBia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

}
