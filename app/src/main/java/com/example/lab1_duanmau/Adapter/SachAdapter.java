package com.example.lab1_duanmau.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;



import com.example.lab1_duanmau.DAO.SachDAO;
import com.example.lab1_duanmau.DAO.TheLoaiSachDAO;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.SachActivity;
import com.example.lab1_duanmau.model.Sach;
import com.example.lab1_duanmau.model.TheLoaiSach;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SachAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sach> sach;
    ArrayList<TheLoaiSach> ls = new ArrayList<TheLoaiSach>();
    SachDAO sachDAO;
    String chonTenMaLoaiSpn;
    TheLoaiSachDAO theLoaiSachDAO;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public SachAdapter(Context context, ArrayList<Sach> sach) {
        this.context = context;
        this.sach = sach;
        sachDAO = new SachDAO(context);
    }

    @Override

    public int getCount() {
        return sach.size();
    }

    @Override
    public Object getItem(int position) {
        return sach.get(position);
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
            convertView = inflater.inflate(R.layout.sach_one_item, null);
            holder = new Viewholder();
            theLoaiSachDAO = new TheLoaiSachDAO(context);
            ls = theLoaiSachDAO.getLoaiSachSp();
            holder.TenSach = convertView.findViewById(R.id.txtTenSach);
            holder.TacGia = convertView.findViewById(R.id.txtTacGia);
            holder.NgayXB = convertView.findViewById(R.id.txtNgayXB);
            holder.ivDelete = convertView.findViewById(R.id.ivDeleteSach);
            holder.ivEdit = convertView.findViewById(R.id.ivEditSach);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        final Sach s = sach.get(position);
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc chắn xóa không");
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sachDAO.delete(sach.get(position));
                        SachActivity.adaptersach.notifyDataSetChanged();
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
                View view = inflater.inflate(R.layout.suasach, null);
                final EditText edtTenSach = view.findViewById(R.id.edtEditTenSach);
                final Spinner spMaTheLoai = view.findViewById(R.id.spEditMaTheLoai);
                final TextView edtNgayXB = view.findViewById(R.id.edtEditNXB);
                final EditText edtTacGia = view.findViewById(R.id.edtEditTacGia);
                final EditText edtGiaBia = view.findViewById(R.id.edtEditGiaBia);
                final EditText edtSoLuong = view.findViewById(R.id.edtEditSoLuong);
                Button btnSua = view.findViewById(R.id.btnSuaSach);
                Button btnNhapLai = view.findViewById(R.id.btnNhapLai);
                edtNgayXB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Calendar calendar = Calendar.getInstance();
                        final TheLoaiSach theLoaiSach =(TheLoaiSach) spMaTheLoai.getSelectedItem();
                        final String matheloai = theLoaiSach.getMaTheLoai();
                        int d = calendar.get(Calendar.DAY_OF_MONTH);
                        int m = calendar.get(Calendar.MONTH);
                        int y = calendar.get(Calendar.YEAR);
                        datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                final String startDay = dayOfMonth + "/" + (month + 1) + "/" + year;
                                edtNgayXB.setText(startDay);
                            }
                        }, y, m, d);
                        datePickerDialog.show();
                    }
                });

                ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, ls);
                spMaTheLoai.setAdapter(adapter);
//                spMaTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        chonTenMaLoaiSpn = adapterView.getItemAtPosition(i).toString();
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });
                    edtTenSach.setText(s.getTenSach());
                    edtTacGia.setText(s.getTacGia());
                    edtNgayXB.setText(sdf.format(s.getNXB()));
                    edtGiaBia.setText(String.valueOf(s.getGiaBia()));
                    edtSoLuong.setText(String.valueOf(s.getSoLuong()));
                int giatri = -1;
                for (int i = 0; i < ls.size(); i++) {
                    if (ls.get(i).getMaTheLoai().toString().equalsIgnoreCase(s.getMaTheLoai())) {
                        giatri = i;
                        break;
                    }
                }
                spMaTheLoai.setSelection(giatri);
                builder.setView(view);
                final AlertDialog myAlert = builder.create();
                btnSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String masach = s.getMaSach().toString();
                            String tensach = edtTenSach.getText().toString();
                            Date nxb = sdf.parse(edtNgayXB.getText().toString());
                            String tacgia = edtTacGia.getText().toString();
                            double gia = Double.parseDouble(edtGiaBia.getText().toString());
                            int soluong = Integer.parseInt(edtSoLuong.getText().toString());
                            TheLoaiSach theLoaiSach =(TheLoaiSach) spMaTheLoai.getSelectedItem();
                            String matheloai = theLoaiSach.getMaTheLoai();

                            Sach suasach = new Sach(masach,matheloai,tensach,tacgia,nxb,gia,soluong);
                            sachDAO.update(suasach);
                            myAlert.dismiss();
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                });
                myAlert.show();
            }
        });
        holder.TenSach.setText(s.getTenSach());
        holder.TacGia.setText(s.getTacGia());
        holder.NgayXB.setText(sdf.format(s.getNXB()));

        return convertView;
    }


    class Viewholder {
        TextView TenSach, TacGia,NgayXB;
        ImageView ivEdit, ivDelete;
    }
}
