package com.example.dilshad.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class choosen extends AppCompatActivity {

    DatabaseHelper mydata;
    ArrayList<String> found;
    ListView list;
    Calendar myCalender;
    int highest_temp,lowest_temp,month;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosen);
        list=(ListView) findViewById(R.id.list);
        Bundle extras = this.getIntent().getExtras();
        highest_temp = extras.getInt("highest_temp", 0);
        lowest_temp = extras.getInt("lowest_temp", 0);
        myCalender=Calendar.getInstance(TimeZone.getDefault());
        month=1+myCalender.get(Calendar.MONTH);
        goForSearch(lowest_temp, highest_temp, month);

        if (found.size() == 0)
        {
            found.add(0,"No result found.");
        }
        ArrayAdapter adapter=new ArrayAdapter(this,R.layout.info_list_view,R.id.Temp_Name, found);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String CurrentString = found.get(position);
                String[] separated = CurrentString.split(",");
                String city=separated[0].trim();
                Intent intent = new Intent(choosen.this, Current_Info.class);
                intent.putExtra("city", city);
                startActivity(intent);

            }
        });

    }

    private void goForSearch(int lowest_temp, int highest_temp, int month) {

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
            found=mydata.getPlaces(lowest_temp, highest_temp, month);
            mydata.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_choosen, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

    }