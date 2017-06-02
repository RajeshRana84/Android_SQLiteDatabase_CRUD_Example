package com.example.rajeshrana.sqlitedatabaseexample;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    EditText userId;

    Button btnSave;
    Button btnLoad;
    Button btnDelete;
    Button btnUpdate;

    DBManager dbManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        userId = (EditText)findViewById(R.id.etId);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnLoad = (Button)findViewById(R.id.btnLoad);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);


         dbManager = new DBManager(this);
    }

    // Insert data
    public void onSaveClick(View view) {
        EditText etuserName = (EditText)findViewById(R.id.etUsername);
        EditText etPassword = (EditText)findViewById(R.id.etPassword);

        ContentValues values = new ContentValues();

        values.put(DBManager.COL_USERNAME,etuserName.getText().toString());
        values.put(DBManager.COL_PASSWORD, etPassword.getText().toString());

        long id = dbManager.Insert(values);

        if(id>0){
            Toast.makeText(getApplicationContext(), "User Inserted: "+id, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Error Inserting "+id, Toast.LENGTH_SHORT).show();
        }


    }

    // Get Data
    public void onLoadClick(View view) {

        Cursor res = dbManager.getData();
        StringBuffer buffer = null;
        if(res.getCount() == 0){
            Toast.makeText(this, "No Data Returned !!!", Toast.LENGTH_SHORT).show();
        }else {
             buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append(" Id : "+res.getString(0) + " User : "+res.getString(1) + " Password : "+res.getString(2) + "\n");
            }
        }
        Toast.makeText(this, buffer, Toast.LENGTH_SHORT).show();

    }


    public void onDeleteClick(View view) {

       // int rowCountDeleted = dbManager.deleteData();
        int count = dbManager.deleteData(userId.getText().toString());
        Toast.makeText(this, "Data Deleted with Count "+count, Toast.LENGTH_SHORT).show();

    }


    public void onUpdateClick(View view) {

            String id = userId.getText().toString();
            String mUsername =  username.getText().toString();
            String mPassword = password.getText().toString();

            boolean status = dbManager.updateData(id , mUsername , mPassword);

        Toast.makeText(this, "Data Inserted ? : "+status, Toast.LENGTH_SHORT).show();

    }
}
