package com.example.lab1_duanmau.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;


import com.example.lab1_duanmau.LoaiSachActivity;
import com.example.lab1_duanmau.model.TheLoaiSach;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class TheLoaiSachDAO {
    DatabaseReference mDatabase;
    Context context;
    String LsachId;

    public TheLoaiSachDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("LoaiSach");
        this.context = context;
    }

    public ArrayList<TheLoaiSach> getLoaiSachSp() {
        final ArrayList<TheLoaiSach> list1 = new ArrayList<TheLoaiSach>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list1.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        TheLoaiSach theLoai = next.getValue(TheLoaiSach.class);
                        list1.add(theLoai);
                        Log.i("Loi", list1.get(0).getTenTheLoai());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list1;
    }

    public ArrayList<TheLoaiSach> getLoaiSach() {
        final ArrayList<TheLoaiSach> list = new ArrayList<TheLoaiSach>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        TheLoaiSach theLoai = next.getValue(TheLoaiSach.class);
                        list.add(theLoai);
                        Log.i("Loi", list.get(0).getTenTheLoai());
                    }
                    LoaiSachActivity.theLoaiSachAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void insert(TheLoaiSach s) {
        LsachId = mDatabase.push().getKey();
        String MaLSach = mDatabase.child(LsachId).push().getKey();
        s.setMaTheLoai(MaLSach);
        mDatabase.child(LsachId).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("insert", "insert Thanh cong");
                        LoaiSachActivity.theLoaiSachAdapter.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert", "insert That bai");
            }
        });


    }

    public void update(final TheLoaiSach s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maTheLoai").getValue(String.class).equalsIgnoreCase(s.getMaTheLoai())) {
                        LsachId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + LsachId);
                        LoaiSachActivity.theLoaiSachAdapter.notifyDataSetChanged();
                        mDatabase.child(LsachId).setValue(s)
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

    public void delete(final TheLoaiSach s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    if (data.child("maTheLoai").getValue(String.class).equalsIgnoreCase(s.getMaTheLoai())) {
                        LsachId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + LsachId);
                        LoaiSachActivity.theLoaiSachAdapter.notifyDataSetChanged();
                        mDatabase.child(LsachId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("delete", "delete Thanh cong");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("delete", "delete That bai");
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
