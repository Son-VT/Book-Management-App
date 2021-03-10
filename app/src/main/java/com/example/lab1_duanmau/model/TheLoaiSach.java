package com.example.lab1_duanmau.model;

import java.io.Serializable;

public class TheLoaiSach implements Serializable {
    String MaTheLoai;
    String TenTheLoai;
    String Mota;
    int ViTri;

    public TheLoaiSach() {
    }

    public TheLoaiSach(String maTheLoai, String tenTheLoai, String mota, int viTri) {
        MaTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
        Mota = mota;
        ViTri = viTri;
    }

    public String getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(String maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public int getViTri() {
        return ViTri;
    }

    public void setViTri(int viTri) {
        ViTri = viTri;
    }

    @Override
    public String toString() {
       return TenTheLoai;
    }
}
