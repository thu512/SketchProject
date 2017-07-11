package com.app.android.sketchproject.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.app.android.sketchproject.R;

import java.util.ArrayList;

public class DelTimetableActivity extends AppCompatActivity {
    Spinner spinner;
    Button button;
    String select;
    ArrayAdapter<String> adapter;
    ArrayList<String> subject=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_timetable);

        Intent intent=new Intent();
        subject=(ArrayList<String>) getIntent().getSerializableExtra("subject");

        button = (Button)findViewById(R.id.button);
        spinner = (Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("과목 선택");
        for(int i=0; i<subject.size(); i++){
            adapter.add(subject.get(i));
        }
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),TimeTableActivity.class);
                i.putExtra("delSub",select);
                setResult(RESULT_OK, i);
                finish();
            }

        });
    }

}
