package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lab1_duanmau.Adapter.SachAdapter;
import com.example.lab1_duanmau.Adapter.TheLoaiSachAdapter;
import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.DAO.TheLoaiSachDAO;
import com.example.lab1_duanmau.model.Sach;
import com.example.lab1_duanmau.model.TheLoaiSach;

import java.util.ArrayList;
import java.util.Date;

public class LoaiSachActivity extends AppCompatActivity {
    EditText edtMaLoai, edtTenloai, edtMota,edtViTri, edtSearch;
    Button btnThemLS,btnThem,btnNhapLai;
    ListView lv;
    TheLoaiSachDAO theLoaiSachDAO;
    ArrayList<TheLoaiSach> list  = new ArrayList<TheLoaiSach>();
    public static TheLoaiSachAdapter theLoaiSachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_sach);
        lv = findViewById(R.id.listLoaiSach);
        btnThemLS = findViewById(R.id.btnAddLoaiSach);
        init();
        theLoaiSachDAO = new TheLoaiSachDAO(LoaiSachActivity.this);
        list = theLoaiSachDAO.getLoaiSach();
        theLoaiSachAdapter = new TheLoaiSachAdapter(LoaiSachActivity.this,list);
        lv.setAdapter(theLoaiSachAdapter);
        btnThemLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoaiSachActivity.this);
                final View view = getLayoutInflater().inflate(R.layout.themtheloaisach,null);
                final EditText edtTenLoai = view.findViewById(R.id.edtAddTenTheLoai);
                final EditText edtMoTa = view.findViewById(R.id.edtAddMoTa);
                final EditText edtViTri = view.findViewById(R.id.edtAddViTri);
                btnThem = view.findViewById(R.id.btnThemLS);
                btnNhapLai = view.findViewById(R.id.btnNhapLaiLS);
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai = edtTenLoai.getText().toString();
                        String mota = edtMoTa.getText().toString();
                        int vitri = Integer.parseInt(edtViTri.getText().toString());
                        TheLoaiSach themsach = new TheLoaiSach(null,tenloai,mota,vitri);
                        theLoaiSachDAO.insert(themsach);
                        myAlert.dismiss();
                    }
                });
                btnNhapLai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            edtMaLoai.setText("");
                            edtTenLoai.setText("");
                            edtMota.setText("");
                            edtViTri.setText("");
                    }
                });
                myAlert.show();
            }
        });

    }
    private void  init(){
        edtTenloai = findViewById(R.id.edtAddTenTheLoai);
        edtMota = findViewById(R.id.edtAddMoTa);
        edtViTri = findViewById(R.id.edtAddViTri);
    }
}
