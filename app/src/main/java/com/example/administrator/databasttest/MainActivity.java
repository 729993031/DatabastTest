package com.example.administrator.databasttest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.sql.SQLData;

public class MainActivity extends AppCompatActivity {
private MyDataBaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new MyDataBaseHelper(this,"BookStore.db",null,2);
        dbHelper=new MyDataBaseHelper(this,"BookStore.db",null,1);
        Button createDatabase=(Button)findViewById(R.id.create_database);
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();

            }
        });
        Button addData=(Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","陈");
                values.put("author","dan brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                values.clear();
                values.put("name","chen");
                values.put("author","danBORN");
                values.put("pafes",510);
                values.put("price",19.95);
                db.insert("Book",null,values);


            }
        });
        Button updateData=(Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",10.99);
                db.update("book",values,"name=?",new String[]{"The da vinci Code"});
            }
        });
        Button deleteButton = (Button) findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("BOOK","pages>?",new  String[]{"500"});
            }
        });
        Button queryButton=(Button)findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity","book name is "+name);
                        Log.d("MainActivity","book author is "+author);
                        Log.d("MainActivity","book pages is "+pages);
                        Log.d("MainActivity","book price is "+price);

                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
        Button replaceData=(Button)findViewById(R.id.replace_data);
        replaceData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("Book",null,null);
                    if (true){
                        throw new NullPointerException();
                    }
                    ContentValues values=new ContentValues();
                    values.put("name","game of thro");
                    values.put("author","game");
                    values.put("pages",720);
                    values.put("price",20.85);
                    db.insert("Book",null,values);
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
    }

}
