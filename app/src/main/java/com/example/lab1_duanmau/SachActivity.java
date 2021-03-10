package com.example.lab1_duanmau;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.lab1_duanmau.Adapter.SachAdapter;
import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.DAO.TheLoaiSachDAO;
import com.example.lab1_duanmau.model.Sach;
import com.example.lab1_duanmau.model.TheLoaiSach;
import com.google.firebase.database.DatabaseReference;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SachActivity extends AppCompatActivity {
    ListView lv;
    public static SachAdapter adaptersach;
    EditText edtMaSach,  edtGiaBia,edtSoLuong,edtTacGia;
    TextView edtNgayXB;
    DatabaseReference mDatabase;
    String mals;
    String chonTenMaLoaiSpn;
    ArrayList<TheLoaiSach> ls = new ArrayList<>();
    Spinner spMaTheLoai;
    TheLoaiSachDAO theLoaiSachDAO;
    ArrayList<Sach> list  = new ArrayList<Sach>();
    Button btnThemSach,btnThem,btnNhapLai;
    SachDAO sachDAO;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);
        theLoaiSachDAO = new TheLoaiSachDAO(SachActivity.this);
        btnThemSach = findViewById(R.id.btnAddSach);
        lv = findViewById(R.id.listSach);
        init();
        ls = theLoaiSachDAO.getLoaiSachSp();
        sachDAO = new SachDAO(SachActivity.this);
        list = sachDAO.getSach();
        adaptersach = new SachAdapter(SachActivity.this,list);
        lv.setAdapter(adaptersach);
        btnThemSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SachActivity.this);
                View view = getLayoutInflater().inflate(R.layout.themsach,null);
                final EditText edtTenSach = view.findViewById(R.id.edtAddTenSach);
                spMaTheLoai = (Spinner) view.findViewById(R.id.spAddMaTheLoai);
                final TextView edtNgayXB = view.findViewById(R.id.edtAddNXB);
                final EditText edtTacGia = view.findViewById(R.id.edtAddTacGia);
                final EditText edtGiaBia = view.findViewById(R.id.edtAddGiaBia);
                final EditText edtSoLuong = view.findViewById(R.id.edtAddSoLuong);
                btnThem = view.findViewById(R.id.btnThemSach);
                btnNhapLai = view.findViewById(R.id.btnNhapLai);
                ArrayAdapter adapter = new ArrayAdapter (SachActivity.this, android.R.layout.simple_spinner_item, ls);
                spMaTheLoai.setAdapter(adapter);
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                edtNgayXB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(SachActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String startDay = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtNgayXB.setText(startDay);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });
                btnThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String tensach = edtTenSach.getText().toString();
                            Date nxb = sdf.parse(edtNgayXB.getText().toString());
                            String tacgia = edtTacGia.getText().toString();
                            double gia = Double.parseDouble(edtGiaBia.getText().toString());
                            int soluong = Integer.parseInt(edtSoLuong.getText().toString());
                            TheLoaiSach loai = (TheLoaiSach) spMaTheLoai.getSelectedItem();
                            String matheloai = loai.getMaTheLoai();
                            Sach themsach = new Sach(null,matheloai,tensach,tacgia,nxb,gia,soluong);
                            sachDAO.insert(themsach);
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
                            edtMaSach.setText("");
                            edtGiaBia.setText("");
                            sdf.parse("");
                            edtSoLuong.setText("");
                            edtTacGia.setText("");
                        }catch (Exception ex){

                        }
                    }
                });
                myAlert.show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    private void  init(){
        edtTacGia = findViewById(R.id.edtAddTacGia);
        spMaTheLoai = findViewById(R.id.spAddMaTheLoai);
        edtGiaBia = findViewById(R.id.edtAddGiaBia);
        edtNgayXB = findViewById(R.id.edtAddNXB);
        edtSoLuong = findViewById(R.id.edtAddSoLuong);
        btnNhapLai = findViewById(R.id.btnNhapLai);
    }
}
