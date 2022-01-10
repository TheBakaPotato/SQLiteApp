package com.example.sqliteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_viewAll, btn_add;
    EditText et_name, et_age;
    Switch sw_active;
    ListView lv_customerList;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_active = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);
        fill_List();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try{
                    try {
                        customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_active.isChecked());
                        Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        customerModel = new CustomerModel(-1, "error", 0,false);
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                    Boolean success = dataBaseHelper.addOne(customerModel);
                    Toast.makeText(MainActivity.this, "Operation result: "+success , Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "The error happens here", Toast.LENGTH_SHORT).show();
                }
                fill_List();
            }
        });
        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fill_List();
            }
        });
        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deletOne(clickedCustomer);
                fill_List();
                Toast.makeText(MainActivity.this, "You just deleted "+clickedCustomer.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fill_List() {
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        List<CustomerModel> everyone = dataBaseHelper.getEveryone();
        ArrayAdapter customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1, everyone);
        lv_customerList.setAdapter(customerArrayAdapter);
        Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
    }
}