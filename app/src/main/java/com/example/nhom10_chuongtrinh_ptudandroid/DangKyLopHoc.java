package com.example.nhom10_chuongtrinh_ptudandroid;


import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.nhom10_chuongtrinh_ptudandroid.Database.DangKyThucHanhHelper;
import com.example.nhom10_chuongtrinh_ptudandroid.Tables.DangKyThucHanh;
import com.example.nhom10_chuongtrinh_ptudandroid.PhanCongTrucNhat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
                    int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String ca = selectedRadioButton.getText().toString();
                    String ngay = editTime.getText().toString();
                    try {
                        if (dkh.getColNgayWhere(tenlop) == null){
                            if (dkh.check(ca, ngay, tenphong)) {
                                Toast.makeText(getApplicationContext(), "Không thể đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                            } else {
                                dangKyThucHanhList.add(new DangKyThucHanh(tenlop, ca, ngay, tenphong));
                                dkh.addRecord(dangKyThucHanhList);
                                Toast.makeText(getApplicationContext(), "Đã đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else if (dkh.getColNgayWhere(tenlop) != null) {
                            if (dkh.check(ca, ngay, tenphong) || compareDates(ngay, dkh.getColNgayWhere(tenlop)) < 0 || (dkh.getColCaWhere(tenlop).equals("Chiều") && compareDates(ngay, dkh.getColNgayWhere(tenlop)) == 0)) {
                                Toast.makeText(getApplicationContext(), "Không thể đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                            } else {
                                dangKyThucHanhList.add(new DangKyThucHanh(tenlop, ca, ngay, tenphong));
                                dkh.addRecord(dangKyThucHanhList);
                                Toast.makeText(getApplicationContext(), "Đã đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Không thể đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private int compareDates(String date1, String date2) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date d1 = dateFormat.parse(date1);
        Date d2 = dateFormat.parse(date2);
        return d1.compareTo(d2);
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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

