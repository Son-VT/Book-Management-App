package com.example.lab1_duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lab1_duanmau.Adapter.ThongKeTop10Adapter;
import com.example.lab1_duanmau.model.HoaDon;
import com.example.lab1_duanmau.model.HoaDonChiTiet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThongKeActivity extends AppCompatActivity {
    ThongKeTop10Adapter thongKeTop10Adapter;
    ListView lvthongke;
    SearchView searchView;
    HoaDonChiTiet hoaDonChiTiet;
    HoaDon hoaDon;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    ArrayList<HoaDonChiTiet> listhdct;
    ArrayList<HoaDon> listHoaDon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        lvthongke = findViewById(R.id.listThongKe);
        databaseReference = FirebaseDatabase.getInstance().getReference("HoaDonCT");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("HoaDon");
        listhdct = new ArrayList<>();
        listHoaDon = new ArrayList<>();
        listView();
        listHoaDon();

    }

    public void listView() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    HoaDonChiTiet hoaDonChiTiet = dataSnapshot1.getValue(HoaDonChiTiet.class);
                    listhdct.add(hoaDonChiTiet);
                }

                thongKeTop10Adapter = new ThongKeTop10Adapter(ThongKeActivity.this, listhdct);
                lvthongke.setAdapter(thongKeTop10Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void listHoaDon() {


        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    hoaDon = dataSnapshot2.getValue(HoaDon.class);


                    Log.d("maHD", String.valueOf(hoaDon.getMaHD()));


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
