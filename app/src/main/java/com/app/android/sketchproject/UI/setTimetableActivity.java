package com.app.android.sketchproject.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.android.sketchproject.R;

import java.util.ArrayList;

import petrov.kristiyan.colorpicker.ColorPicker;

public class setTimetableActivity extends AppCompatActivity {
    public static final String WEEK="week";
    public static final String START_TIME="s_time";
    public static final String END_TIME="e_time";
    public static final String SUBJECT="subject";
    public static final String COLOR="color";
    String bgColor;
    String item;
    String s_time;
    String e_time;
    EditText et;
    Button submit;
    Button color;
    TextView colorView;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    Spinner sp2;
    Spinner sp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timetable);

        submit = (Button) findViewById(R.id.submit);
        et = (EditText) findViewById(R.id.editText);
        color=(Button)findViewById(R.id.color);
        colorView = (TextView) findViewById(R.id.colorView);
        sp2 = (Spinner) findViewById(R.id.spinner1);
        sp3 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("선택하세요");
        adapter.add("월");
        adapter.add("화");
        adapter.add("수");
        adapter.add("목");
        adapter.add("금");

        Spinner sp1 = (Spinner) findViewById(R.id.spinner);
        sp1.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                item=(String) parent.getSelectedItem();
                if(parent.getSelectedItem().toString().equals("선택하세요")){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.add("시작 교시");
        adapter1.add("1");
        adapter1.add("2");
        adapter1.add("3");
        adapter1.add("4");
        adapter1.add("5");
        adapter1.add("6");
        adapter1.add("7");
        adapter1.add("8");
        adapter1.add("9");
        adapter1.add("10");
        adapter1.add("11");
        sp2.setAdapter(adapter1);


        adapter2 = new ArrayAdapter<String>(setTimetableActivity.this,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("시작교시를 선택하세요.");
        sp3.setAdapter(adapter2);


        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                s_time=(String) parent.getSelectedItem();

                if(parent.getSelectedItem().toString().equals("시작 교시")){

                }
                else{
                    adapter2.clear();
                    adapter2.add("끝 교시");
                    for(int i=Integer.parseInt(s_time); i<=11; i++){
                        adapter2.add(Integer.toString(i));
                    }
                    sp3.setAdapter(adapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                e_time=(String) parent.getSelectedItem();
                if(parent.getSelectedItem().toString().equals("끝 교시")){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    public void onSelectColor(View view){
        final ColorPicker colorPicker = new ColorPicker(setTimetableActivity.this);
        final ArrayList<String> colors = new ArrayList<>();
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFF00");
        colors.add("#3C8D2F");
        colors.add("#FA9F00");
        colors.add("#FF0000");

        colorPicker.setColors(colors).setDefaultColorButton(Color.parseColor("#f84c44")).setColumns(5).setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {

                Log.d("parse_color",""+Integer.toHexString(Color.parseColor(colors.get(position))));// will be fired only when OK button was tapped
                bgColor=colors.get(position);
                colorView.setBackgroundColor(Color.parseColor(colors.get(position)));

            }

            @Override
            public void onCancel() {

            }
        }).addListenerButton("newButton", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int color) {
                Log.d("position",""+position);
            }
        }).setRoundColorButton(true).show();
    }



    public  void onSubmit(View view){
        Intent i = new Intent();
        i.putExtra(WEEK, item);
        i.putExtra(START_TIME, s_time);
        i.putExtra(END_TIME, e_time);
        i.putExtra(SUBJECT, et.getText().toString());
        i.putExtra(COLOR,bgColor);
        setResult(RESULT_OK, i);
        finish();
    }

}
