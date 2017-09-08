package com.android.mobile.databaseexample;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //ตัวแปรของ View
    private EditText txtName,txtSurname,txtAge;
    private Button btnAdd;
    private ListView listMember;
    //list ในกำรเก็บข้อมูลของ MemberData
    private ArrayList<MemberData> listData = new ArrayList<MemberData>();
    //ตัวจัดกำรฐำนข้อมูล
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //เชื่อม View
        txtName = (EditText)findViewById(R.id.txtName);
        txtSurname = (EditText)findViewById(R.id.txtSurname);
        txtAge = (EditText)findViewById(R.id.txtAge);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        listMember = (ListView)findViewById(R.id.listMember);
        //สร้ำง Event ให้ปุ่ มเพิ่มข้อมูล
        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMember();
            }
        });
        //สร้ำงตัวจัดกำรฐำนข้อมูล
        dbHelper = new DatabaseHelper(this);
        //น ำตัวจัดกำรฐำนข้อมูลมำใช้งำน
        database = dbHelper.getWritableDatabase();
        //แสดงรำยกำรสมำชิก
        showList();
    }
    //Method แก้ไขข้อมูลใน SQLite
    public void editMember(int id,String name,String surname,int age){
        //เตรียมค่ำต่ำงๆ เพื่อท ำกำรแก้ไข
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("surname", surname);
        values.put("age", age);
        //ให้ Database ท ำกำรแก้ไขข้อมูลที่ id ที่ก ำหนด
        database.update("member", values, "id = ?", new String[] { ""+id });
        //แสดงข้อมูลล่ำสุด
        showList();
    }
    //Method ลบข้อมูลใน SQLite
    public void deleteMember(final int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete Member");
        builder.setMessage("Are you sure detele this member?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                database.delete("member", "id = " + id, null);
                Toast.makeText(MainActivity.this, "Delete Data Id " + id + " Complete",
                        Toast.LENGTH_SHORT).show();
                showList();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
    //Method ดึงข้อมูลจำก SQLite
    private void getMember() {
        //ท ำกำร Query ข้อมูลจำกตำรำง member ใส่ใน Cursor
        Cursor mCursor = database.query(true, "member", new String[] {
                        "id", "name", "surname", "age" }, null,
                null, null, null, null, null);
        //หรือใช้ Cursor mCursor = database.rawQuery("SELECT * FROM member", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            listData.clear();
            //ถ้ำมีข้อมูลจะท ำกำรเก็บข้อมูลใส่ List เพื่อน ำไปแสดง
            if(mCursor.getCount() > 0){
                do {
                    int id = mCursor.getInt(mCursor.getColumnIndex("id"));
                    String name = mCursor.getString(mCursor.getColumnIndex("name"));
                    String surname = mCursor.getString(mCursor.getColumnIndex("surname"));
                    int age = mCursor.getInt(mCursor.getColumnIndex("age"));
                    listData.add(new MemberData(id, name, surname, age));
                }while (mCursor.moveToNext());
            }
        }
    }
    //Method เพิ่มข้อมูลใน SQLite
    private void addMember() {
        // TODO Auto-generated method stub
        if(txtName.length() > 0 && txtSurname.length() > 0 && txtAge.length() > 0){
            //เตรียมข้อมูลส ำหรับใส่ลงไปในตำรำง
            ContentValues values = new ContentValues();
            values.put("name", txtName.getText().toString());
            values.put("surname", txtSurname.getText().toString());
            values.put("age", txtAge.getText().toString());
            //ท ำกำรเพิ่มข้อมูลลงไปในตำรำง member
            database.insert("member", null, values);
            Toast.makeText(this, "Add Data Complete", Toast.LENGTH_SHORT).show();
            //ล้ำงข้อมูล From
            txtName.setText("");
            txtSurname.setText("");
            txtAge.setText("");
            showList();
        }else{
            Toast.makeText(this, "Please Input Data", Toast.LENGTH_SHORT).show();
        }
    }
    private void showList() {
        //ดึงข้อมูลสมำชิกจำก SQLite Database
        getMember();
        //แสดงสมำชิกใน ListView
        listMember.setAdapter(new AdapterListViewData(this,listData));
    }
    public void showEdit(int id,String name,String surname,int age){
        Intent i = new Intent(this,EditActivity.class);
        //ท ำกำรส่งค่ำต่ำงๆ ให้ EditActivity ไปท ำกำรแก้ไข
        i.putExtra("keyId", id);
        i.putExtra("keyName", name);
        i.putExtra("keySurname", surname);
        i.putExtra("keyAge", age);
        //***** ในกำรส่งค่ำและรับค่ำ ส่งเป็นตัวแปรชนิดไหน ต้องรับเป็นตัวแปรชนิดนั้น *****//
        //ท ำกำรเรียก EditActivity โดยให้ Request Code เป็น 1
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //ถ้ำกลับมำหน้ำ MainActivity แล้วผลลัพธ์กำรท ำงำนสมบูรณ์
        if(requestCode == 1 && resultCode == RESULT_OK){
            //เก็บค่ำที่ส่งกลับมำใส่ตัวแปร
            int id = intent.getExtras().getInt("keyId");
            String name = intent.getExtras().getString("keyName");
            String surname = intent.getExtras().getString("keySurname");
            int age = intent.getExtras().getInt("keyAge");
            //***** ในกำรส่งค่ำและรับค่ำ ส่งเป็นตัวแปรชนิดไหน ต้องรับเป็นตัวแปรชนิดนั้น *****//
            //ให้แก้ไขข้อมูลสมำชิก
            editMember(id, name, surname, age);
        }
    }
}
