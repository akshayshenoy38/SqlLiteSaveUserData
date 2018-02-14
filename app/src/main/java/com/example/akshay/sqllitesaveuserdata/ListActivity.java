package com.example.akshay.sqllitesaveuserdata;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.util.Arrays.asList;

public class ListActivity extends AppCompatActivity {

    private ListView lvList;
    private static final String TAG = "ListActivity";
    DatabaseHelper myDB;
    StringBuffer buffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Log.d(TAG, "onCreate: ListActivity Started");
        myDB = new DatabaseHelper(this);
        lvList = (ListView) findViewById(R.id.lvList);


        populateListView();

    }
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Display Data in ListView");
        final Cursor data = myDB.getAllData();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            listData.add(data.getString(1));
        }
        // adapter
        ListAdapter adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listData);
        lvList.setAdapter(adapter);

        // set onClick
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Log.d(TAG, "onItemClick: You Clicked on ");
                //toastMessage("You Clicked on ");

                toastMessage(listData.get(i));
                viewAll(listData.get(i));

                buffer.delete(0,buffer.length());


            }
        });
    }

    private void viewAll(String name) {


        Cursor res = myDB.getAllData();
        Log.d(TAG, "viewAll: Got All items for this name");


        if (res.getCount() == 0){

            showMessage("Error","Nothing foung");
            return;
        }



        while (res.moveToNext()){

            if (name.equals(res.getString(1))) {
                Log.d(TAG, "viewAll: " + name + "is shown");
                buffer.append("ID:" + res.getString(0) + "\n");
                buffer.append("NAME:" + res.getString(1) + "\n");
                buffer.append("EMAIL:" + res.getString(2) + "\n");
                buffer.append("Mobile:" + res.getString(3) + "\n");
                buffer.append("GENDER:" + res.getString(4) + "\n");
                buffer.append("FROM:" + res.getString(5) + "\n");
                buffer.append("TO:" + res.getString(6) + "\n");
                buffer.append("DATE:" + res.getString(7) + "\n\n");
            }


        }
        showMessage("DATA",buffer.toString());


    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
    
    
}
