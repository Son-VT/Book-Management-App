package com.example.lab1_duanmau.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.lab1_duanmau.NguoiDungActivity;
import com.example.lab1_duanmau.model.NguoiDung;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class NguoiDungDAO {
    private DatabaseReference mDatabase;

    String email;
    Context context;


    public NguoiDungDAO(Context context) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("NguoiDung");
        this.context = context;
    }


    public ArrayList<NguoiDung> getNguoiDung() {

        final ArrayList<NguoiDung> list = new ArrayList<NguoiDung>();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear();
                    Iterable<DataSnapshot> dataSnapshotIterable = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = dataSnapshotIterable.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = (DataSnapshot) iterator.next();
                        NguoiDung nguoiDung = next.getValue(NguoiDung.class);
                        list.add(nguoiDung);
                        Log.i("Loi", list.get(0).getEmail());
                        NguoiDungActivity.nguoiDungAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return list;
    }

    public void insert(NguoiDung s) {
        email = mDatabase.push().getKey();
        mDatabase.child(email).setValue(s)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Log.d("insert","insert Thanh cong");
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("insert","insert That bai");
            }
        });
    }
    public void delete(final NguoiDung s) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.child("userName").getValue(String.class).equalsIgnoreCase(s.getEmail())){
                        email = data.getKey();
                        Log.d("getKey", "onCreate: key :" + email);
                        mDatabase.child(email).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        NguoiDungActivity.nguoiDungAdapter.notifyDataSetChanged();
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
