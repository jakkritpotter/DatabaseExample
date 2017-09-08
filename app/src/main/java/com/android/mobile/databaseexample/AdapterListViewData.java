package com.android.mobile.databaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 5N1P3R on 22/2/2560.
 */

public class AdapterListViewData extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context context;
    private MainActivity control;
    //list ในกำรเก็บข้อมูลของ MemberData
    private ArrayList<MemberData> listData = new ArrayList<MemberData>();
    public AdapterListViewData(MainActivity control,ArrayList<MemberData> listData) {
        this.control = control;
        this.context = control.getBaseContext();
        this.mInflater = LayoutInflater.from(context);
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HolderListAdapter holderListAdapter; //เก็บส่วนประกอบของ List แต่ละอัน
        if(convertView == null)
        {
            //ใช้ Layout ของ List เรำเรำสร้ำงขึ้นเอง (adapter_listview.xml)
            convertView = mInflater.inflate(R.layout.adapter_listview, null);
            //สร้ำงตัวเก็บส่วนประกอบของ List แต่ละอัน
            holderListAdapter = new HolderListAdapter();
            //เชื่อมส่วนประกอบต่ำงๆ ของ List เข้ำกับ View
            holderListAdapter.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holderListAdapter.txtSurname = (TextView) convertView.findViewById(R.id.txtSurname);
            holderListAdapter.txtAge = (TextView) convertView.findViewById(R.id.txtAge);
            holderListAdapter.btnEdit = (Button) convertView.findViewById(R.id.btnEdit);
            holderListAdapter.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holderListAdapter);
        }else{
            holderListAdapter = (HolderListAdapter) convertView.getTag();
        }
        //ดึงข้อมูลจำก listData มำแสดงทีละ position
        final int id = listData.get(position).getId();
        final String name = listData.get(position).getName();
        final String surname = listData.get(position).getSurname();
        final int age = listData.get(position).getAge();
        holderListAdapter.txtName.setText("name : "+name);
        holderListAdapter.txtSurname.setText("surname : "+surname);
        holderListAdapter.txtAge.setText("age : "+age);
        //สร้ำง Event ให้ปุ่ ม Delete
        holderListAdapter.btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                control.deleteMember(id);
            }
        });
        //สร้ำง Event ให้ปุ่ ม Edit
        holderListAdapter.btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                control.showEdit(id, name, surname, age);
            }
        });
        return convertView;
    }
}
