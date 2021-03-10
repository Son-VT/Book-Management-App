package com.example.lab1_duanmau.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_duanmau.Adapter.HoaDonChiTietAdapter;
import com.example.lab1_duanmau.HoaDonActivity;
import com.example.lab1_duanmau.HoaDonChiTietActivity;
import com.example.lab1_duanmau.SachActivity;
import com.example.lab1_duanmau.model.HoaDon;
import com.example.lab1_duanmau.model.HoaDonChiTiet;
import com.example.lab1_duanmau.model.Sach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class HoaDonChiTietDAO {
    private DatabaseReference mDatabase;
    Context context;
    String HoaDonCTId;
    public HoaDonChiTietDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("HoaDonCT");
        this.context = context;


    }


    public ArrayList<HoaDonChiTiet> getHoaDonCT() {
        final ArrayList<HoaDonChiTiet> list = new ArrayList<HoaDonChiTiet>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        HoaDonChiTiet hoaDonct = next.getValue(HoaDonChiTiet.class);
                        list.add(hoaDonct);
                        Log.i("Loi", list.get(0).getMaHD());
                        HoaDonChiTietActivity.adapterHDCT.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }


    public void insert(HoaDonChiTiet s) {
        HoaDonCTId = mDatabase.push().getKey();
        String MaHDCT = mDatabase.child(HoaDonCTId).push().getKey();
        s.setMaHDCT(MaHDCT);
        mDatabase.child(HoaDonCTId).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("insert","insert Thanh cong");
                        HoaDonChiTietActivity.adapterHDCT.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert","insert That bai");
            }
        });


    }
    public void update(final HoaDonChiTiet s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maHDCT").getValue(String.class).equalsIgnoreCase(s.getMaHDCT())) {
                        HoaDonCTId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + HoaDonCTId);
                        HoaDonChiTietActivity.adapterHDCT.notifyDataSetChanged();
                        mDatabase.child(HoaDonCTId).setValue(s)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("update", "update Thanh cong");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("update", "update That bai");
                                    }
                                });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void delete(final HoaDonChiTiet s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maHDCT").getValue(String.class).equalsIgnoreCase(s.getMaHDCT())){
                        HoaDonCTId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + HoaDonCTId);
                        HoaDonChiTietActivity.adapterHDCT.notifyDataSetChanged();
                        mDatabase.child(HoaDonCTId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("delete","delete Thanh cong");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("delete","delete That bai");
                                    }
                                });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
