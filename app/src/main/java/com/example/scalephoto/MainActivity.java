package com.example.scalephoto;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.scalephoto.cache.db.StudentDbHelper;
import com.example.scalephoto.cache.models.StudentModel;

import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {


    //
    private StudentDbHelper mSnsBiDbHelper = null;
    //
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);
        //
        mSnsBiDbHelper = new StudentDbHelper(MainActivity.this);

        //
        mTextView = (TextView) findViewById(R.id.show_textView);

        findViewById(R.id.button01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-------------------
                StudentModel data01 = new StudentModel();
                data01.name = "123";
                data01.sid = 1l;
                data01.age = 7;
                data01.gender = 0;
                data01.score = 90;
                mSnsBiDbHelper.insert_StudentTable(data01);
                //-------------------
                data01 = new StudentModel();
                data01.name = "456";
                data01.sid = 2l;
                data01.age = 8;
                data01.gender = 0;
                data01.score = 80;
                mSnsBiDbHelper.insert_StudentTable(data01);
                //-------------------
                data01 = new StudentModel();
                data01.name = "789";
                data01.sid = 3l;
                data01.age = 9;
                data01.gender = 0;
                data01.score = 70;
                mSnsBiDbHelper.insert_StudentTable(data01);


            }
        });

        findViewById(R.id.button02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StudentModel> mList = mSnsBiDbHelper.query_StudentTable();
                //
                mTextView.setText(mList.toString());

            }
        });

        findViewById(R.id.button03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<StudentModel> mList = mSnsBiDbHelper.query_StudentTableByScoreAge(80,10);
                //
                mTextView.setText(mList.toString());

            }
        });

        findViewById(R.id.button04).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnsBiDbHelper._update_StudentTableScoreByName(80,"789");

            }
        });

        findViewById(R.id.button05).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnsBiDbHelper._delete_StudentTableByScore(70);

            }
        });


    }
}