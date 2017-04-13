package com.example.dilshad.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.ArrayList;


public class  Activity_List extends AppCompatActivity {

    ListView list;
    ArrayList<String> cname = new ArrayList<String>();
    TextView cityName;
    DatabaseHelper mydata;
    EditText et;
    Button button;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        final int minvalue=-50;
        final int maxvalue=50;
        final NumberPicker lowest = (NumberPicker) findViewById(R.id.numberPicker1);
        lowest.setMinValue(0);
        lowest.setMaxValue(maxvalue-minvalue);
        int myCurrentValue=0;
        lowest.setValue(myCurrentValue - minvalue);

        lowest.setFormatter(new NumberPicker.Formatter() {


            public String format(int index) {
                return Integer.toString(index + minvalue);
            }


        });


        final int min2value=-50;
        final int max2value=50;
        final NumberPicker highest = (NumberPicker) findViewById(R.id.numberPicker2);
        highest.setMinValue(0);
        highest.setMaxValue(max2value - min2value);
        int myCurrentValue2=0;
        highest.setValue(myCurrentValue2 - min2value);

        highest.setFormatter(new NumberPicker.Formatter() {


            public String format(int index) {
                return Integer.toString(index + min2value);
            }


        });


        button=(Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int x=highest.getValue()-50;
                int y=lowest.getValue()-50;
                Intent intent = new Intent(Activity_List.this, choosen.class);

                intent.putExtra("highest_temp", x);
                intent.putExtra("lowest_temp", y);

                startActivity(intent);

            }

        });



        list = (ListView) findViewById(R.id.list);
        et = (EditText) findViewById(R.id.SearchBox);

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
            cname = mydata.getName();
            mydata.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, R.layout.city_list_view,R.id.City_Name, cname);
        list.setAdapter(adapter);


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String gotData =(String) (list.getItemAtPosition(i));

                Intent senddata = new Intent(Activity_List.this, Target_Activity.class);
                senddata.putExtra("my_key",gotData);
                startActivity(senddata);
            }
        }      );



    }







}
