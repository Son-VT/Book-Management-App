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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.lab1_duanmau.DAO.NguoiDungDAO;
import com.example.lab1_duanmau.NguoiDungActivity;
import com.example.lab1_duanmau.R;
import com.example.lab1_duanmau.SachActivity;
import com.example.lab1_duanmau.model.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class NguoiDungAdapter extends BaseAdapter {
    Context context;
    ArrayList<NguoiDung> ngdung;
    NguoiDungDAO nguoiDungDAO;
    public NguoiDungAdapter(Context context, ArrayList<NguoiDung> nd) {
        this.context = context;
        this.ngdung = nd;
        nguoiDungDAO = new NguoiDungDAO(context);
    }
    @Override
    public int getCount() {
        return ngdung.size();
    }
    @Override
    public Object getItem(int position) {
        return ngdung.get(position);
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
            convertView = inflater.inflate(R.layout.nguoidung_one_item, null);
            holder = new Viewholder();
            holder.Ten = convertView.findViewById(R.id.txtName);
            holder.SDT = convertView.findViewById(R.id.txtPhone);
            convertView.setTag(holder);
        } else {
            holder = (Viewholder) convertView.getTag();
        }
        final NguoiDung nd = ngdung.get(position);
//        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Thông báo");
//                builder.setMessage("Bạn có chắc chắn xóa không");
//                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        try {
//                        nguoiDungDAO.delete(ngdung.get(position));
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                        user.delete()
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Log.d("test", "User account deleted.");
//                                        }
//                                    }
//                                });
//                        }catch (Exception ex){
//
//                        }
//                       NguoiDungActivity.nguoiDungAdapter.notifyDataSetChanged();
//                    }
//                });
//                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog myAlert = builder.create();
//                myAlert.show();
//            }
//        });
        holder.Ten.setText(nd.getEmail());
        holder.SDT.setText(nd.getSdt());
        return convertView;
    }
    class Viewholder {
        TextView Ten, SDT;
        ImageView ivEdit, ivDelete;
    }
}
