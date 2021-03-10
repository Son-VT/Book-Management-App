package com.example.lab1_duanmau.model;

import java.io.Serializable;

public class NguoiDung implements Serializable {
    String Email;
    String PassWord;
    String Sdt;


    public NguoiDung() {
    }

    public NguoiDung(String email, String passWord, String sdt) {
        Email = email;
        PassWord = passWord;
        Sdt = sdt;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getSdt() {
        return Sdt;
    }

    public void setSdt(String sdt) {
        Sdt = sdt;
    }
}
