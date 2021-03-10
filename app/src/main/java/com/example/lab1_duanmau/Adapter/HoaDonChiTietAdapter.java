package com.example.lab1_duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lab1_duanmau.DAO.HoaDonChiTietDAO;
import com.example.lab1_duanmau.DAO.HoaDonDAO;
import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.model.HoaDon;
import com.example.lab1_duanmau.model.HoaDonChiTiet;
import com.example.lab1_duanmau.model.Sach;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class HoaDonChiTietAdapter extends BaseAdapter {
    Context context;
    ListView lvhoaDonCT;
    HoaDonDAO hoaDonDAO;
    SachDAO sachDAO;
    double thanhTien;
    Sach sach;
    ArrayList<HoaDonChiTiet> hoaDonChiTiets;
    ArrayList<HoaDon> listHD = new ArrayList<>();
    ArrayList<Sach> listSach = new ArrayList<>();
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Sach");
    public HoaDonChiTietAdapter(Context context, ArrayList<HoaDonChiTiet> hdct) {
        this.context = context;
        this.hoaDonChiTiets = hdct;
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }
    @Override
    public int getCount() {
        return hoaDonChiTiets.size();
    }

    @Override
    public Object getItem(int position) {
        return hoaDonChiTiets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HoaDonChiTietAdapter.Viewholder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.hoadonchitet_one_item, null);
            holder = new HoaDonChiTietAdapter.Viewholder();
            hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
            hoaDonDAO = new HoaDonDAO(context);
            sachDAO = new SachDAO(context);
            listHD = hoaDonDAO.getHoaDonSp();
            listSach = sachDAO.getSachSp();
            lvhoaDonCT = convertView.findViewById(R.id.listHoaDonCT);
            holder.TenHDCT = convertView.findViewById(R.id.txtTenHDCT);
            holder.SoLuong = convertView.findViewById(R.id.txtSoLuong);
            holder.ThanhTien = convertView.findViewById(R.id.txtThanhTien);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteHD);
            holder.ivEdit = convertView.findViewById(R.id.ivEditHDCT);
            convertView.setTag(holder);
        } else {
            holder = (HoaDonChiTietAdapter.Viewholder) convertView.getTag();
        }
        final HoaDonChiTiet hoaDonct = hoaDonChiTiets.get(position);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    double giaBia = dataSnapshot1.child("giaBia").getValue(double.class);
                    list.add(String.valueOf(giaBia));
                    sach = dataSnapshot1.getValue(Sach.class);

                    for (String l : list) {
                        if (hoaDonct.getMaSach().equalsIgnoreCase(sach.getMaSach())) {
                            Log.d("kiem tra 1 list", String.valueOf(l));
                            thanhTien = Double.valueOf(l) * Double.valueOf(hoaDonct.getSoLuongMua());
                            holder.ThanhTien.setText(String.valueOf(thanhTien));
                            Log.i("soluong", String.valueOf(giaBia));
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa không");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hoaDonChiTietDAO.delete(hoaDonChiTiets.get(position));
                        hoaDonChiTiets.clear();
                        hoaDonChiTiets.addAll(hoaDonChiTietDAO.getHoaDonCT());
                    }
                });
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog myAlert = builder.create();
                myAlert.show();
            }
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.suahoadonchitiet, null);
                final EditText edtEditSoLuong = view.findViewById(R.id.txtEditSoLuong);
                final EditText edtEditTenHDCT = view.findViewById(R.id.txtEditTenHDCT);

                Button btnSua = view.findViewById(R.id.btnSuaHDCT);
                Button btnNhapLai = view.findViewById(R.id.btnNhapLai);

                edtEditTenHDCT.setText(hoaDonct.getTenHDCT());
                edtEditSoLuong.setText(String.valueOf(hoaDonct.getSoLuongMua()));


                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String mahdct = hoaDonct.getMaHDCT();
                            String tenhdct = hoaDonct.getTenHDCT();
                            String mahd = hoaDonct.getMaHD();
                            String masach = hoaDonct.getMaSach();
                            int soluong = Integer.parseInt(edtEditSoLuong.getText().toString());

                            HoaDonChiTiet hdct = new HoaDonChiTiet(mahdct,tenhdct,mahd,masach,soluong);
                            hoaDonChiTietDAO.update(hdct);
                            myAlert.dismiss();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
                myAlert.show();
            }
        });
        holder.TenHDCT.setText(hoaDonct.getTenHDCT());
        holder.SoLuong.setText(String.valueOf(hoaDonct.getSoLuongMua()));
        holder.ThanhTien.setText(String.valueOf(thanhTien));
        return convertView;
    }
    class Viewholder {
        TextView TenHDCT, SoLuong, ThanhTien;
        ImageView ivDelete, ivEdit;
    }
}
