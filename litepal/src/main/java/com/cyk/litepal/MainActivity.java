package com.cyk.litepal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button creatDataBase = findViewById(R.id.buttonLitePal);
        Button addData = findViewById(R.id.addData);
        Button updateData = findViewById(R.id.updateData);
        Button updateAllData = findViewById(R.id.updateAllData);
        Button queryData = findViewById(R.id.queryData);
        Button deleteData = findViewById(R.id.deleteData);

        creatDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setAuthor("guolin");
                book.setName("The first line of Android");
                book.setPrice(58.88);
                book.save();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setPrice(66.66);
                book.updateAll("name = ?", "The first line of Android");
            }
        });

        updateAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setToDefault("price");
                book.updateAll();
            }
        });

        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = LitePal.findAll(Book.class);
                for (Book book : books) {
                    String information = "data : "
                            + "\n" + "name : " + book.getName()
                            + "\n" + "author : " + book.getAuthor()
                            + "\n" + "price : " + book.getPrice();
                    Toast.makeText(getApplicationContext(),information, Toast.LENGTH_SHORT).show();
                }

            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LitePal.deleteAll(Book.class, "name = ?", "The first line of Android");
            }
        });
    }
}