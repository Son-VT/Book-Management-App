package com.example.lab1_duanmau.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.model.HoaDonChiTiet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ThongKeTop10Adapter extends BaseAdapter {
    Context context;
    ArrayList<HoaDonChiTiet> dstk;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    HoaDonChiTiet hoaDonChiTiet;

    public ThongKeTop10Adapter(Context context, ArrayList<HoaDonChiTiet> dstk) {
        this.context = context;
        this.dstk = dstk;

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("HoaDonCT");

        Log.d("databaseReference", String.valueOf(databaseReference));
    }


    @Override
    public int getCount() {
        return dstk.size();
    }

    @Override
    public Object getItem(int i) {
        return dstk.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();

            LayoutInflater inf = ((Activity) context).getLayoutInflater();
            //  view = inf.inflate(R.layout.activity_adapter_hoadon_chitiet,null);
            view = inf.inflate(R.layout.thongketop10_one_item,null);

//            holder.tvMaHDCT = view.findViewById(R.id.tvMaHDCT);
////            holder.tvmaHDFK = view.findViewById(R.id.tvmaHDFK);
//            holder.tvmaSachFK = view.findViewById(R.id.tvmaSachFK);
//            holder.tvSoLuongMua = view.findViewById(R.id.tvSoLuongMua);
//            holder.chkHDCT = view.findViewById(R.id.chkHDCT);
//            holder.tvThanhTien = view.findViewById(R.id.tvThanhTien);

            holder.tvMaSachTkT10 = view.findViewById(R.id.txtTenSachTk);
            holder.tvSoLuongMuaTop10 = view.findViewById(R.id.txtSoLuongTk);

            view.setTag(holder);
        } else {
            holder = (ThongKeTop10Adapter.ViewHolder) view.getTag();
        }

        final HoaDonChiTiet hdct = dstk.get(i);
//        holder.tvMaHDCT.setText(hdct.getMaHDCT());
//        holder.tvmaHDFK.setText(hdct.getMaHD());
//        holder.tvmaSachFK.setText(hdct.getMaSach());
//        holder.tvSoLuongMua.setText(hdct.getSoLuongMua());


        holder.tvMaSachTkT10.setText(hdct.getTenHDCT());
        holder.tvSoLuongMuaTop10.setText(String.valueOf(hdct.getSoLuongMua()));


//        Log.d("view", String.valueOf(view));
//        Log.d("viewGroup", String.valueOf(viewGroup));
        return view;
    }

    public class ViewHolder {
        public TextView tvMaHDCT, tvmaHDFK, tvmaSachFK, tvSoLuongMua, tvThanhTien;
        public CheckBox chkHDCT;

        public  TextView tvMaSachTkT10, tvSoLuongMuaTop10;
    }

    public void adapterupdateListView(ArrayList<HoaDonChiTiet> dstk){
        this.dstk = dstk;
        notifyDataSetChanged();
    }
}
