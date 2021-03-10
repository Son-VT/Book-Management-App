package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lab1_duanmau.Adapter.HoaDonChiTietAdapter;
import com.example.lab1_duanmau.Adapter.SachAdapter;
import com.example.lab1_duanmau.DAO.HoaDonChiTietDAO;
import com.example.lab1_duanmau.DAO.HoaDonDAO;
import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.model.HoaDon;
import com.example.lab1_duanmau.model.HoaDonChiTiet;
import com.example.lab1_duanmau.model.Sach;
import com.example.lab1_duanmau.model.TheLoaiSach;

import java.util.ArrayList;

public class HoaDonChiTietActivity extends AppCompatActivity {
    public static HoaDonChiTietAdapter adapterHDCT;
    ArrayList<HoaDonChiTiet> list  = new ArrayList<HoaDonChiTiet>();
    ArrayList<HoaDon> listHD = new ArrayList<>();
    ArrayList<Sach> listSach = new ArrayList<>();
    ListView lv;
    Button btnThemHDCT;
    HoaDonDAO hoaDonDAO;
    SachDAO sachDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        lv = findViewById(R.id.listHoaDonCT);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonChiTietActivity.this);
        hoaDonDAO = new HoaDonDAO(HoaDonChiTietActivity.this);
        sachDAO = new SachDAO(HoaDonChiTietActivity.this);
        listHD = hoaDonDAO.getHoaDonSp();
        listSach = sachDAO.getSach();
        btnThemHDCT = findViewById(R.id.btnAddHoaDonCT);
        list = hoaDonChiTietDAO.getHoaDonCT();
        adapterHDCT = new HoaDonChiTietAdapter(HoaDonChiTietActivity.this, list);
        lv.setAdapter(adapterHDCT);
        btnThemHDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonChiTietActivity.this);
                View view = getLayoutInflater().inflate(R.layout.themhoadonchitiet,null);
                final EditText edtTenHDCT = view.findViewById(R.id.txtAddTenHDCT);
                final Spinner spTenHD = view.findViewById(R.id.spAddMaHD);
                final Spinner spTenSach = view.findViewById(R.id.spAddMaSach);
                final EditText edtSoLuong = view.findViewById(R.id.txtAddSoLuong);
                final EditText edtGiaBia = view.findViewById(R.id.edtAddGiaBia);
                Button btnThem = view.findViewById(R.id.btnThemHDCT);
                Button btnNhapLai = view.findViewById(R.id.btnNhapLai);
                ArrayAdapter adapter = new ArrayAdapter (HoaDonChiTietActivity.this, android.R.layout.simple_spinner_dropdown_item, listHD);
                spTenHD.setAdapter(adapter);
                ArrayAdapter adapter1 = new ArrayAdapter (HoaDonChiTietActivity.this, android.R.layout.simple_spinner_dropdown_item, listSach);
                spTenSach.setAdapter(adapter1);
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenhdct = edtTenHDCT.getText().toString();
                        HoaDon hoaDon = (HoaDon) spTenHD.getSelectedItem();
                        String mahd = hoaDon.getMaHD();
                        Sach sach = (Sach) spTenSach.getSelectedItem();
                        String masach = sach.getMaSach();
                        int soluong = Integer.parseInt(edtSoLuong.getText().toString());
                        HoaDonChiTiet themhdct = new HoaDonChiTiet(null,tenhdct,mahd,masach,soluong);
                        hoaDonChiTietDAO.insert(themhdct);
                        myAlert.dismiss();
                    }
                });
                myAlert.show();
            }
        });
    }
}