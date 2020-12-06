package com.example.uangku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahKeuanganActivity extends AppCompatActivity {

    private ImageView icBack;
    private Button btnSave;
    private ImageButton btnDate, btnRp, btnTipe;
    private TextView tvDate, tvTipe;
    private EditText etRp, etNote;

    private String tanggal, tipe, jumlah, catatan;
    private String date;
    private RadioGroup mKategori;
    private RadioButton mKategoriOption;
    private String kategori;

    private String total, pemasukan, pengeluaran;

    private boolean buttonOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_keuangan);


        initView();
    }

    private void initView() {

        icBack = findViewById(R.id.icBack);
        btnDate = findViewById(R.id.btnDate);
        tvDate = findViewById(R.id.tvDate);
        btnSave = findViewById(R.id.btnSave);
        btnRp = findViewById(R.id.btnRp);
        etRp = findViewById(R.id.etRp);
        btnTipe = findViewById(R.id.btnTipe);
        tvTipe = findViewById(R.id.tvTipe);
        etNote = findViewById(R.id.etNote);

        etRp.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                btnRp.setBackground(getResources().getDrawable(R.drawable.ic_rp));
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                btnRp.setBackground(getResources().getDrawable(R.drawable.ic_rp_active));
            }

            public void afterTextChanged(Editable s) {

                btnRp.setBackground(getResources().getDrawable(R.drawable.ic_rp_active));
            }
        });

        icBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDate();

            }
        });
        btnTipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTipe();
            }
        });

    }

    private void save() {

        tanggal = tvDate.getText().toString();
        tipe = tvTipe.getText().toString();
        jumlah = etRp.getText().toString();
        catatan = etNote.getText().toString();

        if (tipe.equals("Pemasukan")) {
            pemasukan = jumlah;
            total = pemasukan;
            Log.e(total, "pemasukan");
        } else {
            pengeluaran = jumlah;
            total = pengeluaran;
            Log.e(total, "pengeluaran");
        }

        if (TextUtils.isEmpty(tanggal)) {
            Toast.makeText(getApplicationContext(), "Tanggal tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(tipe)) {
            Toast.makeText(getApplicationContext(), "Tipe tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(jumlah)) {
            Toast.makeText(getApplicationContext(), "Jumlah tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(catatan)) {
            Toast.makeText(getApplicationContext(), "Catatan tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        Intent save = new Intent(getApplicationContext(), MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("pemasukan", pemasukan);
        bundle.putString("pengeluaran", pengeluaran);
        bundle.putString("total", total);
        save.putExtras(bundle);
        save.putExtra("keyActivity", "main");
        startActivity(save);
        finish();
        Toast.makeText(getApplicationContext(), "Anda telah menambahkan data" + "\n" + tanggal + "\n" + tipe + "\n" + jumlah + "\n" + catatan, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    private void dialogDate() {

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TambahKeuanganActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                calendar.set(year, month, day);
                date = sdf.format(calendar.getTime());
                tvDate.setText(date);

                if (TextUtils.isEmpty(date)) {
                    buttonOn = false;
                    btnDate.setBackground(getResources().getDrawable(R.drawable.ic_calendar));
                } else {
                    buttonOn = true;
                    btnDate.setBackground(getResources().getDrawable(R.drawable.ic_calendar_active));
                }

            }
        }, year, month, day);

        datePickerDialog.show();

    }

    private void dialogTipe() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(TambahKeuanganActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_tipe, null);
        Button pilih = (Button) view.findViewById(R.id.btn_pilih_kategori);

        mKategori = (RadioGroup) view.findViewById(R.id.rg_kategori);

        mKategori.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mKategoriOption = mKategori.findViewById(checkedId);

                switch (checkedId) {
                    case R.id.pemasukan:
                    case R.id.pengeluaran:
                        kategori = mKategoriOption.getText().toString();
                        break;
                    default:
                }
            }
        });

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String value_kategori = mKategoriOption.getText().toString();
                tvTipe.setText(value_kategori);
                btnTipe.setBackground(getResources().getDrawable(R.drawable.ic_down_active));
                dialog.dismiss();
            }
        });
    }

}