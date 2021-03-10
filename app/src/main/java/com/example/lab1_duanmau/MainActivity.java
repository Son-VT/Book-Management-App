package com.example.lab1_duanmau;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.lab1_duanmau.user.ChangePassWordActivity;

public class MainActivity extends AppCompatActivity {
    ImageView btnLoaiSach, btnSach,btnNguoiDung, btnHoaDon,btnHoaDonCT, btnThongKe;
    GridLayout gridLayout;
    ImageView imageViewMenu;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout = findViewById(R.id.girdviewManager);
        btnLoaiSach = findViewById(R.id.btnLoaiSach);
        btnHoaDonCT = findViewById(R.id.btnHoaDonChiTiet);
        btnHoaDon = findViewById(R.id.btnHoaDon);
        btnSach = findViewById(R.id.btnSach);
        btnThongKe = findViewById(R.id.btnThongKe);
        btnNguoiDung = findViewById(R.id.btnUser);
        imageViewMenu=findViewById(R.id.imageViewMenu);
        btnNguoiDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, NguoiDungActivity.class);
                startActivity(intent);
            }
        });
        btnLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, LoaiSachActivity.class);
                startActivity(intent);
            }
        });
        btnSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, SachActivity.class);
                startActivity(intent);
            }
        });
        btnHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, HoaDonActivity.class);
                startActivity(intent);
            }
        });
        btnHoaDonCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, HoaDonChiTietActivity.class);
                startActivity(intent);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });
        imageViewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view=(ImageView)v;
                final PopupMenu popupMenu=new PopupMenu(MainActivity.this,view);
                popupMenu.getMenuInflater().inflate(R.menu.drawer_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.menuDangXuat:
                                intent=new Intent(MainActivity.this,Welcome.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
                                break;
                        }
                        return true;
                    }
                });

            }

        });
    }
}
