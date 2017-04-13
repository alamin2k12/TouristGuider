package com.example.dilshad.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Target_Activity extends AppCompatActivity {
    TextView data;
    DatabaseHelper mydata;
    ArrayList<String> cname;
    String gotId;
    String value;
    ListView mylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        mylist=(ListView) findViewById(R.id.list);


        Bundle extras=this.getIntent().getExtras();
        value =extras.getString("my_key");
        try {
            mydata = new DatabaseHelper(this);
            mydata.createdatabase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            mydata.opendatabase();
            gotId = mydata.getAll(value);
            cname = mydata.getallinfo(gotId);
            mydata.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.info_list_view,R.id.Temp_Name, cname);
        mylist.setAdapter(adapter );


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_target_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
