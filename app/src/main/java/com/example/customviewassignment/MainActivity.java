package com.example.customviewassignment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LineGraphView graph;
    EditText date,studentCount;
    Button addData, clearData;
    SeekBar seekBar;
    CheckBox showLinesCB;
    ArrayList<DataObject> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graph = findViewById(R.id.graph);
        date = findViewById(R.id.dateEditText);
        studentCount = findViewById(R.id.studentCountEditText);
        addData = findViewById(R.id.addData);
        clearData = findViewById(R.id.clearData);
        seekBar = findViewById(R.id.seekBar);
         showLinesCB = findViewById(R.id.showLinesCB);

        list = new ArrayList<>();
        addOnClickListener();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                graph.setRadius(progress);
                graph.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void addOnClickListener()
    {
        addData.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                boolean flag = validateInput();
                if(flag) {
                    if(list.size()>=5)
                    {
                        list.remove(0);
                    }
                    list.add(new DataObject(date.getText().toString(), Integer.parseInt(studentCount.getText().toString())));
                     graph.addList(list);

                     graph.invalidate();
                }
            }
        });

        clearData.setOnClickListener(new View.OnClickListener(

        ) {
            @Override
            public void onClick(View v) {
                list.clear();
                graph.addList(list);
                graph.invalidate();
            }
        });

        showLinesCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showLinesCB.isChecked())
                {
                    graph.setDrawLine(true);
                }
                else
                    graph.setDrawLine(false);
                graph.invalidate();
            }
        });
    }


    public boolean validateInput()
    {
        if(studentCount.getText().length() == 0 || date.getText().length() == 0) {
            Toast.makeText(this, "Student Count cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.parseInt(studentCount.getText().toString()) < 0) {
            Toast.makeText(this, "Invalid student count", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(validateDate(date.getText().toString()))
        {
            return true;
        }
        else
            Toast.makeText(this,"Invalid Date", Toast.LENGTH_SHORT).show();
        return false;

    }


    public boolean validateDate(String date)
    {
        String dateValue = date;
        String[] dateValues = dateValue.split("/");
        int month = Integer.parseInt(dateValues[0].toString());
        int day = Integer.parseInt(dateValues[1].toString());

        if(day >= 1 && day <= 31)
        {
            if((month == 1 || month == 3|| month == 5 || month == 7 || month == 8 || month == 10 || month == 12) )
            {
                return true;
            }
            else if (month == 2 && day<=28)
            {
                return true;
            }
            else if((month == 4 || month ==6 || month ==9 || month ==11 ) && (day<= 30))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
            return false;

    }



}
