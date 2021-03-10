package com.example.lab1_duanmau.DAO;

import android.content.Context;
import android.util.Log;


import androidx.annotation.NonNull;


import com.example.lab1_duanmau.SachActivity;
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


public class SachDAO {
    DatabaseReference mDatabase;
    Context context;
    String sachId;
    public SachDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("Sach");
        this.context = context;
    }
    public ArrayList<Sach> getSachSp() {
        final ArrayList<Sach> list = new ArrayList<Sach>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Sach sach = next.getValue(Sach.class);
                        list.add(sach);
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public ArrayList<Sach> getSach() {

        final ArrayList<Sach> list = new ArrayList<Sach>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        Sach sach = next.getValue(Sach.class);
                        list.add(sach);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }
    public void insert(Sach s) {
        sachId = mDatabase.push().getKey();
        String MaSach = mDatabase.child(sachId).getKey();
        s.setMaSach(MaSach);
        mDatabase.child(sachId).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("insert", "insert Thanh cong");
                        SachActivity.adaptersach.notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert", "insert That bai");
            }
        });
    }
    public void update(final Sach s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(s.getMaSach())) {
                        sachId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + sachId);
                        SachActivity.adaptersach.notifyDataSetChanged();
                        mDatabase.child(sachId).setValue(s)
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
    public void delete(final Sach s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("maSach").getValue(String.class).equalsIgnoreCase(s.getMaSach())){
                        sachId = data.getKey();
                        Log.d("getKey", "onCreate: key :" + sachId);
                        mDatabase.child(sachId).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        SachActivity.adaptersach.notifyDataSetChanged();
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
