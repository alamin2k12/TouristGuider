package com.example.dilshad.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static Cursor query;
    private Context mycontext;

    private String DB_PATH = "/data/data/com.example.dilshad.myapplication/databases/";
    private static String DB_NAME = "weather_report.db";
    public SQLiteDatabase myDataBase;
    public static final String KEY_NAME="city_name";


    public DatabaseHelper(Context context) throws IOException, SQLException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            createdatabase();
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();

            this.getReadableDatabase();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database");
            }

    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);

            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = mycontext.getAssets().open(DB_NAME);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);


    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList getName() {

        String myString="";
        ArrayList<String> mylist=new ArrayList<String>();
        String[] columns=new String[]{"city_name","country_name"};
        Cursor c=myDataBase.query("weather_table", columns, null, null, null, null, null);

        int iRow=c.getColumnIndex("city_name");
        int iCountry=c.getColumnIndex("country_name");

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {

            myString=c.getString(iRow) +" "+","+  " "+c.getString(iCountry);
            mylist.add(myString);

        }


        return mylist;
    }

    public String getAll(String data) {
       
        
        String gotId = null;
        ArrayList<String> result;

        String myString="";
        ArrayList<String> mylist=new ArrayList<String>();
        String[] columns=new String[]{"city_name","country_name"};
        Cursor c=myDataBase.query("weather_table",columns,null,null,null,null,null);

        int iRow=c.getColumnIndex("city_name");
        int iCountry=c.getColumnIndex("country_name");
        int iRowid=c.getColumnIndex("_rowid_");

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {

            myString=c.getString(iRow) +" "+","+" "+c.getString(iCountry);
            if (myString.equals(data))
            {
                
                gotId=c.getString(iRow);
                break;
            }

        }

        return gotId;

    }

    public ArrayList<String> getallinfo(String gotId) {

        ArrayList<String> result = new ArrayList<>();
        String WHERE= KEY_NAME + "=?";
        String[] columns=new String[]{KEY_NAME,"country_name","gmt_of_city","lattitude","longitude",
                "day_length","jan_avg_temperature","feb_avg_temperature","mar_avg_temperature","apr_avg_temperature",
                "may_avg_temperature","jun_avg_temperature","jul_avg_temperature","aug_avg_temperature",
                "sep_avg_temperature","oct_avg_temperature","nov_avg_temperature","dec_avg_temperature",
                "jan_avg_humidity","feb_avg_humidity","mar_avg_humidity","apr_avg_humidity","may_avg_humidity",
                "jun_avg_humidity","jul_avg_humidity","aug_avg_humidity","sep_avg_humidity","oct_avg_humidity",
                "nov_avg_humidity","dec_avg_humidity","feb_avg_humidity","description_of_city"};

        Cursor c=myDataBase.query("weather_table",columns,WHERE,new String[]{gotId},null,null,null);

        if(c !=null)
        {
            String city_name;
            c.moveToFirst();
            city_name="Country Name: " +c.getString(1);
            result.add(city_name);
            city_name="GMT: " +c.getString(2);
            result.add(city_name);
            city_name="Latitude: " +c.getString(3);
            result.add(city_name);
            city_name="Longitude: " +c.getString(4);
            result.add(city_name);
            city_name="Length of day: " +c.getString(5);
            result.add(city_name);
            city_name="Jan_avg_temperature(°C): " +c.getString(6);
            result.add(city_name);
            city_name="Feb_avg_temperature(°C): " +c.getString(7);
            result.add(city_name);
            city_name="Mar_avg_temperature(°C): " +c.getString(8);
            result.add(city_name);
            city_name="Apr_avg_temperature(°C): " +c.getString(9);
            result.add(city_name);
            city_name="May_avg_temperature(°C): " +c.getString(10);
            result.add(city_name);
            city_name="Jun_avg_temperature(°C): " +c.getString(11);
            result.add(city_name);
            city_name="Jul_avg_temperature(°C): " +c.getString(12);
            result.add(city_name);
            city_name="Aug_avg_temperature(°C): " +c.getString(13);
            result.add(city_name);
            city_name="Sep_avg_temperature(°C): " +c.getString(14);
            result.add(city_name);
            city_name="Oct_avg_temperature(°C): " +c.getString(15);
            result.add(city_name);
            city_name="Nov_avg_temperature(°C): " +c.getString(16);
            result.add(city_name);
            city_name="Dec_avg_temperature(°C): " +c.getString(17);
            result.add(city_name);
            city_name="Jan_avg_humidity(%): " +c.getString(18);
            result.add(city_name);
            city_name="Feb_avg_humidity(%): " +c.getString(19);
            result.add(city_name);
            city_name="Mar_avg_humidity(%): " +c.getString(20);
            result.add(city_name);
            city_name="Apr_avg_humidity(%): " +c.getString(21);
            result.add(city_name);
            city_name="May_avg_humidity(%): " +c.getString(22);
            result.add(city_name);
            city_name="Jun_avg_humidity(%): " +c.getString(23);
            result.add(city_name);
            city_name="Jul_avg_humidity(%): " +c.getString(24);
            result.add(city_name);
            city_name="Aug_avg_humidity(%): " +c.getString(25);
            result.add(city_name);
            city_name="Sep_avg_humidity(%): " +c.getString(26);
            result.add(city_name);
            city_name="Oct_avg_humidity(%): " +c.getString(27);
            result.add(city_name);
            city_name="Nov_avg_humidity(%): " +c.getString(28);
            result.add(city_name);
            city_name="Dec_avg_humidity(%): " +c.getString(29);
            result.add(city_name);
            city_name="Important Places : " +c.getString(31);
            result.add(city_name);
            return result;
        }
        return null;

    }


    public ArrayList<String> getPlaces(int lowest_temp, int highest_temp, int month) {


        String result;
        ArrayList<String> found=new ArrayList<>();
        String[] columns=new String[]{KEY_NAME,"country_name","jan_avg_temperature","feb_avg_temperature","mar_avg_temperature","apr_avg_temperature",
                "may_avg_temperature","jun_avg_temperature","jul_avg_temperature","aug_avg_temperature",
                "sep_avg_temperature","oct_avg_temperature","nov_avg_temperature","dec_avg_temperature",
                };

        Cursor c=myDataBase.query("weather_table",columns,null,null,null,null,null);
        String myString="";
        String city_name;
        String country_name;
        ArrayList<String> mylist=new ArrayList<String>();
        int iRow=c.getColumnIndex(KEY_NAME);
        int iCountry=c.getColumnIndex("country_name");
        int iRowid=c.getColumnIndex("_rowid_");
        int iJan=c.getColumnIndex("jan_avg_temp");
        double temp=0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {

            if(month==1){
                temp=Double.parseDouble(c.getString(6));

            }
            else if(month==2){
                temp=Double.parseDouble(c.getString(7));

            }

            else if(month==3){
                temp=Double.parseDouble(c.getString(8));

            }

            else if(month==4){
                temp=Double.parseDouble(c.getString(9));

            }

            else if(month==5){
                temp=Double.parseDouble(c.getString(10));

            }


            else if(month==6){
                temp=Double.parseDouble(c.getString(11));

            }


            else if(month==7){
                temp=Double.parseDouble(c.getString(12));

            }

            else if(month==8){
                temp=Double.parseDouble(c.getString(13));

            }

            else if(month==9){
                temp=Double.parseDouble(c.getString(14));

            }

            else if(month==10){
                temp=Double.parseDouble(c.getString(15));

            }

            else if(month==11){
                temp=Double.parseDouble(c.getString(16));

            }

            else if(month==12){
                temp=Double.parseDouble(c.getString(17));

            }

            if(temp<=highest_temp&&temp>=lowest_temp){

                city_name=c.getString(0);
                country_name=c.getString(1);

                result=city_name +","+country_name+"  Temp(°C) : "+temp;
                found.add(result);
            }
        }
        return found;
    }
}