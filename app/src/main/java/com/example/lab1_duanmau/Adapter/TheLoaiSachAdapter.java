package com.example.lab1_duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.DAO.TheLoaiSachDAO;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.model.TheLoaiSach;

import java.util.ArrayList;

public class TheLoaiSachAdapter extends BaseAdapter {
    Context context;
    ArrayList<TheLoaiSach> theLoaiSach;
    TheLoaiSachDAO theLoaiSachDAO;
    public TheLoaiSachAdapter(Context context, ArrayList<TheLoaiSach> s) {
        this.context = context;
        this.theLoaiSach = s;
        theLoaiSachDAO = new TheLoaiSachDAO(context);
    }
    @Override
    public int getCount() {
        return theLoaiSach.size();
    }

    @Override
    public Object getItem(int position) {
        return theLoaiSach.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Viewholder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.loaisach_one_item, null);
            holder = new Viewholder();
            holder.TenLoai = convertView.findViewById(R.id.edtTenLS);
            holder.ViTri = convertView.findViewById(R.id.edtViTriLS);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteLS);
            holder.ivEdit = convertView.findViewById(R.id.ivEditLS);
            convertView.setTag(holder);
        } else {
            holder = (TheLoaiSachAdapter.Viewholder) convertView.getTag();
        }
        final TheLoaiSach ls = theLoaiSach.get(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa không");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        theLoaiSachDAO.delete(theLoaiSach.get(position));
                        theLoaiSach.clear();
                        theLoaiSach.addAll(theLoaiSachDAO.getLoaiSach());
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
                final View view = inflater.inflate(R.layout.sualoaisach, null);
                final EditText edtTenLoai = view.findViewById(R.id.edtEditTenTheLoai);
                final EditText edtMoTa = view.findViewById(R.id.edtEditMoTa);
                final EditText edtViTri = view.findViewById(R.id.edtEditViTri);
                Button btnSua = view.findViewById(R.id.btnSuaLS);
                Button btnNhapLai = view.findViewById(R.id.btnNhapLaiLS);

                edtTenLoai.setText(ls.getTenTheLoai());
                edtMoTa.setText(ls.getMota());
                edtViTri.setText(String.valueOf(ls.getViTri()));
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai = edtTenLoai.getText().toString();
                        String mota = edtMoTa.getText().toString();
                        int vitri = Integer.parseInt(edtViTri.getText().toString());
                        TheLoaiSach sualoai = new TheLoaiSach(null,tenloai,mota,vitri);
                        theLoaiSachDAO.update(sualoai);
                        myAlert.dismiss();
                    }
                });
                myAlert.show();
            }
        });
        holder.TenLoai.setText(ls.getTenTheLoai());
        holder.ViTri.setText(String.valueOf(ls.getViTri()));
        return convertView;
    }
    class Viewholder {
        TextView TenLoai,ViTri;
        ImageView ivEdit, ivDelete;
    }
}
