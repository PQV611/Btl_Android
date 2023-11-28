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
                    String ca = getSelectedRadioText();
                    if(ca.equals("Chiều")){
                        Log.d("abc", "====================");
                    } else {
                        Log.d("abc","xxxxxxxxxxxxxxxxxxxxxx");
                    }
                    String ngay = editTime.getText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date date = null, date1 = null;
                    try {
                        if (dkh.getColNgay(tenlop) != null) {
                            date = dateFormat.parse(ngay);
                            date1 = dateFormat.parse(dkh.getColNgay(tenlop));
                            if ((date.compareTo(date1) > 0) || (date.compareTo(date1) == 0 && dkh.getColCa(tenlop).equals("Sáng") && ca.equals("Chiều"))) {
                                dangKyThucHanhList.add(new DangKyThucHanh(tenlop, ca, ngay, tenphong));
                                dkh.addRecord(dangKyThucHanhList);
                                Toast.makeText(getApplicationContext(), "Đã đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Không thể đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            dangKyThucHanhList.add(new DangKyThucHanh(tenlop, ca, ngay, tenphong));
                            dkh.addRecord(dangKyThucHanhList);
                            Toast.makeText(getApplicationContext(), "Đã đăng ký lịch thực hành", Toast.LENGTH_SHORT).show();
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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

