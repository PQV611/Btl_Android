package com.example.nhom10_chuongtrinh_ptudandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginMainActivity extends AppCompatActivity {
    EditText userName, password;
    RadioGroup rdGroup;
    RadioButton rdGv, rdSv;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main_activity);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        rdGroup = findViewById(R.id.rdGroup);
        btnLogin = findViewById(R.id.btnLogin);
        rdGv = findViewById(R.id.rdGv);
        rdSv = findViewById(R.id.rdSv);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int selectedId = rdGroup.getCheckedRadioButtonId();
                if(rdGv.isChecked()){
                    Intent intent = new  Intent(LoginMainActivity.this, ManHinhGiaoVien.class);
                    startActivity(intent);
                } else if (rdSv.isChecked()){
                    //Chua code
                    //Chua code
                    //Chua code
                    //Chua code
                    //Chua code
                    //Chua code
                }
            }
        });
    }
}