package com.example.lab1_duanmau.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_duanmau.HoaDonActivity;
import com.example.lab1_duanmau.model.HoaDon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class HoaDonDAO {
    private DatabaseReference mDatabase;
    Context context;
    String HoaDonId;

    public HoaDonDAO(Context context) {

        this.mDatabase = FirebaseDatabase.getInstance().getReference("HoaDon");
        this.context = context;
    }

    public ArrayList<HoaDon> getHoaDon() {
        final ArrayList<HoaDon> list = new ArrayList<HoaDon>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        HoaDon hoaDon = next.getValue(HoaDon.class);
                        list.add(hoaDon);
                        Log.i("Loi", list.get(0).getMaHD());
                        HoaDonActivity.hoaDonAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public ArrayList<HoaDon> getHoaDonSp() {
        final ArrayList<HoaDon> list = new ArrayList<HoaDon>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        HoaDon hoaDon = next.getValue(HoaDon.class);
                        list.add(hoaDon);
                        Log.i("Loi", list.get(0).getMaHD());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public void insert(HoaDon s) {
        HoaDonId = mDatabase.push().getKey();
        String MaHD = mDatabase.child(HoaDonId).push().getKey();
        s.setMaHD(MaHD);
        mDatabase.child(HoaDonId).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("insert","insert Thanh cong");
                        HoaDonActivity.hoaDonAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert","insert That bai");
            }
        });
    }
    public void delete(final HoaDon s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maHD").getValue(String.class).equalsIgnoreCase(s.getMaHD())){
                        HoaDonId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + HoaDonId);
                        HoaDonActivity.hoaDonAdapter.notifyDataSetChanged();
                        mDatabase.child(HoaDonId).removeValue()
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
