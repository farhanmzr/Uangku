package com.example.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView btnAdd;
    private TextView tvTotal, tvPemasukan, tvPengeluaran;

    private String total, pemasukan, pengeluaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().hasExtra("keyActivity")) {
            String keyActivity = getIntent().getStringExtra("keyActivity");
            if (keyActivity.equals("main")) {

                Bundle bundle = getIntent().getExtras();
                total = bundle.getString("total");
                pemasukan = bundle.getString("pemasukan");
                pengeluaran = bundle.getString("pengeluaran");
                initView();
                //firestore

                tvTotal.setText("Rp. " + total);
                tvPengeluaran.setText("Rp. " + pengeluaran);
                tvPemasukan.setText("Rp. " + pemasukan);

            }
            else {
                initView();
            }
        }



        initView();
    }

    private void initView() {


        tvTotal = findViewById(R.id.tvTotal);
        tvPemasukan = findViewById(R.id.tvPemasukan);
        tvPengeluaran = findViewById(R.id.tvPengeluaran);
        //
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(getApplicationContext(), TambahKeuanganActivity.class);
                startActivity(add);
            }
        });

    }
}