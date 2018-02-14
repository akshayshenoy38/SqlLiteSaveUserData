package com.example.akshay.sqllitesaveuserdata;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etId,etName,etEmail,etMobileNumber,etFrom,etTo,etDate;
    Button btnAddData,btnShowAll;
    RadioGroup rgGender;
    DatabaseHelper myDB;
    String gender = "";
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: MainActivity Started");

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etFrom = (EditText) findViewById(R.id.etFrom);
        etTo = (EditText) findViewById(R.id.etTo);
        etDate = (EditText) findViewById(R.id.etDate);
        etId = (EditText) findViewById(R.id.etId);



        // buttons
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnShowAll = (Button) findViewById(R.id.btnShowAll);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);

        myDB = new DatabaseHelper(this);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.length()!=0 && etEmail.length()!=0 && etName.length()!=0 && etMobileNumber.length()!=0 && etFrom.length()!=0 && etTo.length()!=0 && etDate.length()!=0){
                    addData();
                } else {
                    toastMessage("Please Enter Missing Fields");
                }
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent listIntent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(listIntent);
            }
        });
    }

    public void addData(){


        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String mobile = etMobileNumber.getText().toString();
        String from = etFrom.getText().toString();
        String to = etTo.getText().toString();
        String date = etDate.getText().toString();

        switch (rgGender.getCheckedRadioButtonId()){
             case R.id.rbMale:
                 gender = "Male";
                 break;
             case R.id.rbFemale:
                 gender = "Female";
                 break;
        }


        boolean insertData = myDB.insertData(name,email,mobile,gender,from,to,date);
        if (insertData){
            toastMessage("Data inserted");
        } else {
            toastMessage("Data not inserted");
        }

    }

    public void DeleteData(View view) {
        Integer deletedRows = myDB.deleteData(etId.getText().toString());
        if (deletedRows > 0){
            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_SHORT).show();
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void updateData(View view){
        switch (rgGender.getCheckedRadioButtonId()){
            case R.id.rbMale:
                gender = "Male";
                break;
            case R.id.rbFemale:
                gender = "Female";
                break;
        }
        boolean isUpdate = myDB.updateFunction(etId.getText().toString(),
                etName.getText().toString(),
                etEmail.getText().toString(),
                etMobileNumber.getText().toString(),
                gender,
                etFrom.getText().toString(),
                etTo.getText().toString(),
                etDate.getText().toString());
        if (isUpdate == true) {
            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
