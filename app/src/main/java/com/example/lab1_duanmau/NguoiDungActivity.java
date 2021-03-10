package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab1_duanmau.Adapter.NguoiDungAdapter;
import com.example.lab1_duanmau.DAO.NguoiDungDAO;
import com.example.lab1_duanmau.model.NguoiDung;

import java.util.ArrayList;

public class NguoiDungActivity extends AppCompatActivity {
    ListView lv;
    public static NguoiDungAdapter nguoiDungAdapter;
    Button btnThem;
    EditText etUserName, etPass, etSdt , etHoTen;
    NguoiDungDAO nguoiDungDAO;
    ArrayList<NguoiDung> ngdung  = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        init();
        lv = findViewById(R.id.listNguoiDung);
        btnThem = findViewById(R.id.btnAddNguoiDung);
        try {
            nguoiDungDAO = new NguoiDungDAO(NguoiDungActivity.this);
            ngdung = nguoiDungDAO.getNguoiDung();
            nguoiDungAdapter = new NguoiDungAdapter(NguoiDungActivity.this,ngdung);
            lv.setAdapter(nguoiDungAdapter);
        }catch (Exception ex){

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        ngdung.clear();
        ngdung.addAll(nguoiDungDAO.getNguoiDung());
        nguoiDungAdapter.notifyDataSetChanged();
    }
    private void  init(){
        etUserName= findViewById(R.id.edtAddUserName);
        etPass = findViewById(R.id.edtPassword);
        etSdt = findViewById(R.id.edtAddSdt);
        etHoTen = findViewById(R.id.edtAddHoTen);
    }
}
