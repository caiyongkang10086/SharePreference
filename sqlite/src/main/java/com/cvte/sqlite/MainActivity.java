package com.cvte.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SqliteDataBase sqliteDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //version增大时，调用onUpgrade
        sqliteDataBase = new SqliteDataBase(this, "CYKDateBase.db", null , 1);

        Button createDataBase = (Button) findViewById(R.id.databaseButton);
        Button addData = (Button) findViewById(R.id.addDataButton);
        Button update = (Button) findViewById(R.id.updateDataButton);
        Button delete = (Button) findViewById(R.id.deleteDataButton);

        createDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqliteDataBase.getWritableDatabase();
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase data = sqliteDataBase.getWritableDatabase();
                ContentValues values = new ContentValues();
                //first add
                values.put("name", "Android First Line");
                values.put("author", "郭霖");
                values.put("pages","582");
                values.put("price","3");
                data.insert("Book", null, values);
                //second add
                values.clear();
                values.put("name", "Android Improve");
                values.put("author", "刘望舒");
                values.put("pages","666");
                values.put("price","45.6");
                data.insert("Book", null, values);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase data = sqliteDataBase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price","48.8");
                data.update("Book", values, "name = ?", new String[] {"Android Improve"});
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase data = sqliteDataBase.getWritableDatabase();
                data.delete("Book", "name = ?", new String[] {"Android Improve"});
            }
        });
    }
}