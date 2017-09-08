package com.android.mobile.databaseexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    //ตัวแปรของ View
    private EditText txtName,txtSurname,txtAge;
    private Button btnEdit;

    //ตัวแปรไว้เก็บว่ำข้อมูลที่จะแก้ไข id เป็นอะไร
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //testGit
        //เชื่อม View
        txtName = (EditText)findViewById(R.id.txtName);
        txtSurname = (EditText)findViewById(R.id.txtSurname);
        txtAge = (EditText)findViewById(R.id.txtAge);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        //รับค่ำจำก MainActivity มำแสดงข้อมูลเพื่อท ำกำรแก้ไข
        this.id = getIntent().getExtras().getInt("keyId");
        txtName.setText(getIntent().getExtras().getString("keyName"));
        txtSurname.setText(getIntent().getExtras().getString("keySurname"));
        txtAge.setText(""+getIntent().getExtras().getInt("keyAge"));
        //***** ในกำรส่งค่ำและรับค่ำ ส่งเป็นตัวแปรชนิดไหน ต้องรับเป็นตัวแปรชนิดนั้น *****//
        //สร้ำง Event ให้ปุ่ มแก้ไขข้อมูล
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                //ตั้งค่ำผลลัพธ์กำรท ำงำนว่ำ RESULT_OK
                setResult(RESULT_OK,i);
                //ส่งข้อมูลกลับไปให้ MainActivity ท ำกำรแก้ไขข้อมูลให้
                i.putExtra("keyId", id);
                i.putExtra("keyName", txtName.getText().toString());
                i.putExtra("keySurname", txtSurname.getText().toString());
                i.putExtra("keyAge", Integer.parseInt(txtAge.getText().toString()));
                //***** ในกำรส่งค่ำและรับค่ำ ส่งเป็นตัวแปรชนิดไหน ต้องรับเป็นตัวแปรชนิดนั้น *****//
                finish();
            }
        });
    }


}
