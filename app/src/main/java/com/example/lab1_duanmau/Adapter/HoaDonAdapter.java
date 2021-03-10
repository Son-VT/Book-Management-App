package com.example.lab1_duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.lab1_duanmau.DAO.HoaDonDAO;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.model.HoaDon;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HoaDonAdapter extends BaseAdapter {
    Context context;
    ListView lvhoaDon;
    ArrayList<HoaDon> hoaDons;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public HoaDonAdapter(Context context, ArrayList<HoaDon> s) {
        this.context = context;
        this.hoaDons = s;
        hoaDonDAO = new HoaDonDAO(context);
    }
    @Override
    public int getCount() {
        return hoaDons.size();
    }

    @Override
    public Object getItem(int position) {
        return hoaDons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HoaDonAdapter.Viewholder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.hoadon_one_item, null);
            holder = new HoaDonAdapter.Viewholder();
            lvhoaDon = convertView.findViewById(R.id.listHoaDon);
            holder.MaHoaDon = convertView.findViewById(R.id.txtMaHoaDon);
            holder.NgayMua = convertView.findViewById(R.id.txtNgayMuaHd);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteHD);
            convertView.setTag(holder);
        } else {
            holder = (HoaDonAdapter.Viewholder) convertView.getTag();
        }
        final HoaDon hoaDon = hoaDons.get(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa không");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hoaDonDAO.delete(hoaDons.get(position));
                        hoaDons.clear();
                        hoaDons.addAll(hoaDonDAO.getHoaDon());
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

        holder.MaHoaDon.setText(hoaDon.getTenHD());
        holder.NgayMua.setText(sdf.format(hoaDon.getNgayMua()));
        return convertView;
    }
    class Viewholder {
        TextView MaHoaDon,NgayMua;
        ImageView  ivDelete;
    }
}
