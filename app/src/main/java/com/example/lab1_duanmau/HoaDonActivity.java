package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lab1_duanmau.Adapter.HoaDonAdapter;
import com.example.lab1_duanmau.Adapter.SachAdapter;
import com.example.lab1_duanmau.Adapter.TheLoaiSachAdapter;
import com.example.lab1_duanmau.DAO.HoaDonDAO;
import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.DAO.TheLoaiSachDAO;
import com.example.lab1_duanmau.model.HoaDon;
import com.example.lab1_duanmau.model.Sach;
import com.example.lab1_duanmau.model.TheLoaiSach;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HoaDonActivity extends AppCompatActivity {
    HoaDonDAO hoaDonDAO;
    Button btnThemHD , btnThem, btnNhapLai;
    ArrayList<HoaDon> listHd = new ArrayList<HoaDon>();
    ListView lv;
    public static HoaDonAdapter hoaDonAdapter;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
        btnThemHD = findViewById(R.id.btnAddHoaDon);
        lv = findViewById(R.id.listHoaDon);
        listHd = hoaDonDAO.getHoaDon();
        hoaDonAdapter = new HoaDonAdapter(HoaDonActivity.this, listHd);
        lv.setAdapter(hoaDonAdapter);
        btnThemHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HoaDonActivity.this);
                View view = getLayoutInflater().inflate(R.layout.themhoadon,null);
                final EditText edtTenHoaDon = view.findViewById(R.id.txtAddTenHD);
                final TextView edtNgayXuat = view.findViewById(R.id.txtAddNgayXuatHD);
                btnThem = view.findViewById(R.id.btnThemHd);
                btnNhapLai = view.findViewById(R.id.btnNhapLai);
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                edtNgayXuat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(HoaDonActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String startDay = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtNgayXuat.setText(startDay);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String tenhd = edtTenHoaDon.getText().toString();
                            Date ngayxuat = sdf.parse(edtNgayXuat.getText().toString());

                            HoaDon themhd = new HoaDon(null,tenhd,ngayxuat);
//                            mDatabase.push().setValue(themsach).addOnCompleteListener(new OnCompleteListener<Void>() {
////                                @Override
////                                public void onComplete(@NonNull Task<Void> task) {
////                                    adapter.notifyDataSetChanged();
////                                }
////                            });
                            hoaDonDAO.insert(themhd);
                            myAlert.dismiss();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
                btnNhapLai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            edtTenHoaDon.setText("");
                            edtNgayXuat.setText("");
                        }catch (Exception ex){

                        }
                    }
                });
                myAlert.show();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}