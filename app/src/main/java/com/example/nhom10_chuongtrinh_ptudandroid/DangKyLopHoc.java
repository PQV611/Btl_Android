package com.example.nhom10_chuongtrinh_ptudandroid;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhom10_chuongtrinh_ptudandroid.Database.DangKyThucHanhHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.DangKyThucHanh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DangKyLopHoc extends AppCompatActivity {
    private EditText editTenLop, editTime;
    private RadioGroup radioGroup;
    private Button btnDK;
    private DangKyThucHanhHelper dkh;
    private String tenphong;
    private List<DangKyThucHanh> dangKyThucHanhList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_ky_lop_hoc);
        getFormWidget();

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });

        btnDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidInput()) {
                    String tenlop = editTenLop.getText().toString();
                    String ca = getSelectedRadioText();
                    String ngay = editTime.getText().toString();
                    dangKyThucHanhList.add(new DangKyThucHanh(tenlop, ca, ngay, tenphong));
                    dkh.addRecord(dangKyThucHanhList);
                }
            }
        });
    }

    private void getFormWidget() {
        editTenLop = findViewById(R.id.editTenLop);
        editTime = findViewById(R.id.editTime);
        radioGroup = findViewById(R.id.rdG);
        btnDK = findViewById(R.id.btnDangKy);
        dkh = new DangKyThucHanhHelper(getApplicationContext());
        Intent intent = getIntent();
        tenphong = intent.getStringExtra("tenPhong");
    }

    private void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                editTime.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    private boolean isValidInput() {
        if (editTenLop.getText().toString().isEmpty() || editTime.getText().toString().isEmpty() || getSelectedRadioText().isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getSelectedRadioText() {
        int selectedRadioId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioId);
            return selectedRadioButton.getText().toString();
        }
        return "";
    }
}

