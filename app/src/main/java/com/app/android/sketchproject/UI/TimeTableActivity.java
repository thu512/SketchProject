package com.app.android.sketchproject.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.android.sketchproject.R;
import com.app.android.sketchproject.Sqlite.MyDBHandler;
import com.app.android.sketchproject.Sqlite.Table;

import java.util.ArrayList;
import java.util.Iterator;

public class TimeTableActivity extends AppCompatActivity {

    TextView s1[] = new TextView[12]; //월
    TextView s2[] = new TextView[12]; //화
    TextView s3[] = new TextView[12]; //수
    TextView s4[] = new TextView[12]; //목
    TextView s5[] = new TextView[12]; //금
    Button b1; //삽입
    Button b2; //삭제

    //삭제 시 중복없이 과목명을 저장하고 있도록 해서 삭제 액티비티 스피너에서 사용
    ArrayList<String> subject = new ArrayList<>();

    //SQLite 핸들러 객체
    final MyDBHandler myDBHandler = new MyDBHandler(this, "timetableDB.db",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        b1 = (Button) findViewById(R.id.b_tt_add_class);
        b2 = (Button) findViewById(R.id.b_tt_normal_setting);
        s1[1] = (TextView)findViewById(R.id.textview1_1);
        s1[2] = (TextView)findViewById(R.id.textview1_2);
        s1[3] = (TextView)findViewById(R.id.textview1_3);
        s1[4] = (TextView)findViewById(R.id.textview1_4);
        s1[5] = (TextView)findViewById(R.id.textview1_5);
        s1[6] = (TextView)findViewById(R.id.textview1_6);
        s1[7] = (TextView)findViewById(R.id.textview1_7);
        s1[8] = (TextView)findViewById(R.id.textview1_8);
        s1[9] = (TextView)findViewById(R.id.textview1_9);
        s1[10] = (TextView)findViewById(R.id.textview1_10);
        s1[11] = (TextView)findViewById(R.id.textview1_11);

        s2[1] = (TextView)findViewById(R.id.textview2_1);
        s2[2] = (TextView)findViewById(R.id.textview2_2);
        s2[3] = (TextView)findViewById(R.id.textview2_3);
        s2[4] = (TextView)findViewById(R.id.textview2_4);
        s2[5] = (TextView)findViewById(R.id.textview2_5);
        s2[6] = (TextView)findViewById(R.id.textview2_6);
        s2[7] = (TextView)findViewById(R.id.textview2_7);
        s2[8] = (TextView)findViewById(R.id.textview2_8);
        s2[9] = (TextView)findViewById(R.id.textview2_9);
        s2[10] = (TextView)findViewById(R.id.textview2_10);
        s2[11] = (TextView)findViewById(R.id.textview2_11);

        s3[1] = (TextView)findViewById(R.id.textview3_1);
        s3[2] = (TextView)findViewById(R.id.textview3_2);
        s3[3] = (TextView)findViewById(R.id.textview3_3);
        s3[4] = (TextView)findViewById(R.id.textview3_4);
        s3[5] = (TextView)findViewById(R.id.textview3_5);
        s3[6] = (TextView)findViewById(R.id.textview3_6);
        s3[7] = (TextView)findViewById(R.id.textview3_7);
        s3[8] = (TextView)findViewById(R.id.textview3_8);
        s3[9] = (TextView)findViewById(R.id.textview3_9);
        s3[10] = (TextView)findViewById(R.id.textview3_10);
        s3[11] = (TextView)findViewById(R.id.textview3_11);

        s4[1] = (TextView)findViewById(R.id.textview4_1);
        s4[2] = (TextView)findViewById(R.id.textview4_2);
        s4[3] = (TextView)findViewById(R.id.textview4_3);
        s4[4] = (TextView)findViewById(R.id.textview4_4);
        s4[5] = (TextView)findViewById(R.id.textview4_5);
        s4[6] = (TextView)findViewById(R.id.textview4_6);
        s4[7] = (TextView)findViewById(R.id.textview4_7);
        s4[8] = (TextView)findViewById(R.id.textview4_8);
        s4[9] = (TextView)findViewById(R.id.textview4_9);
        s4[10] = (TextView)findViewById(R.id.textview4_10);
        s4[11] = (TextView)findViewById(R.id.textview4_11);

        s5[1] = (TextView)findViewById(R.id.textview5_1);
        s5[2] = (TextView)findViewById(R.id.textview5_2);
        s5[3] = (TextView)findViewById(R.id.textview5_3);
        s5[4] = (TextView)findViewById(R.id.textview5_4);
        s5[5] = (TextView)findViewById(R.id.textview5_5);
        s5[6] = (TextView)findViewById(R.id.textview5_6);
        s5[7] = (TextView)findViewById(R.id.textview5_7);
        s5[8] = (TextView)findViewById(R.id.textview5_8);
        s5[9] = (TextView)findViewById(R.id.textview5_9);
        s5[10] = (TextView)findViewById(R.id.textview5_10);
        s5[11] = (TextView)findViewById(R.id.textview5_11);

        subject.add("----"); //subject 0번값 지정 -> 추후 중복 체크 for문을 위해서 더미 데이터 한개 추가

        //Table 객체는 시간표에 과목 삽입시 저장 되는 그릇

        //getAllResult 메소드는 전체 디비 테이블을 읽어오기위해 생성 -> 리턴타입 ArrayList<Table>
        ArrayList<Table> tb = myDBHandler.getAllResult();

        //매뉴에서 시간표액티비티로 들어올때 sqlite에서 기존에 저장되있던 시간표 데이터 불러와서 시간표 세팅
        for(int i=0; i<tb.size(); i++){

            //삭제를 위한 subject 세팅
            boolean flag=true;
            Table ta=tb.get(i);
            for(int k=0; k<subject.size(); k++){
                if(ta.getSub().equals(subject.get(k))){
                    flag=false;
                }
            }
            if(flag){
                subject.add(ta.getSub());
            }

            //시간표 세팅
            int start;
            int end;
            switch (ta.getDay()){
                case "월":
                    start=Integer.parseInt(ta.getStart());
                    end=Integer.parseInt(ta.getEnd());
                    for(int j=start; j<=end; j++ ){
                        s1[j].setText(ta.getSub());
                        s1[j].setBackgroundColor(Color.parseColor(ta.getColor()));
                    }
                    break;
                case "화":
                    start=Integer.parseInt(ta.getStart());
                    end=Integer.parseInt(ta.getEnd());
                    for(int j=start; j<=end; j++ ){
                        s2[j].setText(ta.getSub());
                        s2[j].setBackgroundColor(Color.parseColor(ta.getColor()));
                    }
                    break;
                case "수":
                    start=Integer.parseInt(ta.getStart());
                    end=Integer.parseInt(ta.getEnd());
                    for(int j=start; j<=end; j++ ){
                        s3[j].setText(ta.getSub());
                        s3[j].setBackgroundColor(Color.parseColor(ta.getColor()));
                    }
                    break;
                case "목":
                    start=Integer.parseInt(ta.getStart());
                    end=Integer.parseInt(ta.getEnd());
                    for(int j=start; j<=end; j++ ){
                        s4[j].setText(ta.getSub());
                        s4[j].setBackgroundColor(Color.parseColor(ta.getColor()));
                    }
                    break;
                case "금":
                    start=Integer.parseInt(ta.getStart());
                    end=Integer.parseInt(ta.getEnd());
                    for(int j=start; j<=end; j++ ){
                        s5[j].setText(ta.getSub());
                        s5[j].setBackgroundColor(Color.parseColor(ta.getColor()));
                    }
                    break;
                default:
                    break;

            }
        }

        //삽입
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),setTimetableActivity.class);
                startActivityForResult(i,0);
            }

        });

        //삭제
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeTableActivity.this,DelTimetableActivity.class);
                Log.i("sub","cilck"+subject.toString());
                intent.putExtra("subject",subject);
                startActivityForResult(intent,1);
            }

        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            /////////////////////////////////////////삽입 처리
            case 0:
                if(data!=null){
                    String item=(String) data.getStringExtra(setTimetableActivity.WEEK);
                    String stime=(String) data.getStringExtra(setTimetableActivity.START_TIME);
                    String etime=(String) data.getStringExtra(setTimetableActivity.END_TIME);
                    String sub=(String) data.getStringExtra(setTimetableActivity.SUBJECT);
                    String color=(String)data.getStringExtra(setTimetableActivity.COLOR);

                    //subject배열에 과목 이름을 중복없이 저장-> 중복된 과목 존재시 배열에만 추가안함
                    boolean flag=true;
                    for(int k=0; k<subject.size(); k++){
                        if(sub.equals(subject.get(k))){
                            flag=false;
                        }
                    }
                    if(flag){
                        subject.add(sub);
                    }


                    for(int i = Integer.parseInt(stime); i<= Integer.parseInt(etime); i++){
                        //중복시 삽입 불가 처리
                    }

                    //sqlite 테이블에 과목 정보 삽입
                    myDBHandler.insert(sub,stime,etime,item,color);

                    //시간표에 세팅
                    int start;
                    int end;
                    switch (item){
                        case "월":
                            start=Integer.parseInt(stime);
                            end=Integer.parseInt(etime);
                            for(int i=start; i<=end; i++ ){
                                s1[i].setText(sub);
                                s1[i].setBackgroundColor(Color.parseColor(color));
                            }
                            break;
                        case "화":
                            start=Integer.parseInt(stime);
                            end=Integer.parseInt(etime);
                            for(int i=start; i<=end; i++ ){
                                s2[i].setText(sub);
                                s2[i].setBackgroundColor(Color.parseColor(color));
                            }
                            break;
                        case "수":
                            start=Integer.parseInt(stime);
                            end=Integer.parseInt(etime);
                            for(int i=start; i<=end; i++ ){
                                s3[i].setText(sub);
                                s3[i].setBackgroundColor(Color.parseColor(color));
                            }
                            break;
                        case "목":
                            start=Integer.parseInt(stime);
                            end=Integer.parseInt(etime);
                            for(int i=start; i<=end; i++ ){
                                s4[i].setText(sub);
                                s4[i].setBackgroundColor(Color.parseColor(color));
                            }
                            break;
                        case "금":
                            start=Integer.parseInt(stime);
                            end=Integer.parseInt(etime);
                            for(int i=start; i<=end; i++ ){
                                s5[i].setText(sub);
                                s5[i].setBackgroundColor(Color.parseColor(color));
                            }
                            break;
                        default:
                            break;

                    }
                }
                break;


            /////////////////////////////////////////삭제 처리
            case 1:
                if(data!=null){
                    String name = (String) data.getStringExtra("delSub");
                    Table table = new Table();
                    table = myDBHandler.getResult(name);
                    int start;
                    int end;

                    //subject에 삭제시킨과목명 삭제
                    Iterator it = subject.iterator();
                    while(it.hasNext()){
                        String value = (String) it.next();
                        if(value.equals(name)){
                            it.remove();
                        }
                    }


                    //시간표에서 과목 삭제
                    for(int i=1; i<=11; i++){
                        if(s1[i].getText().toString().equals(table.getSub())){
                            s1[i].setText("");
                            s1[i].setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                        if(s2[i].getText().toString().equals(table.getSub())){
                            s2[i].setText("");
                            s2[i].setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                        if(s3[i].getText().toString().equals(table.getSub())){
                            s3[i].setText("");
                            s3[i].setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                        if(s4[i].getText().toString().equals(table.getSub())){
                            s4[i].setText("");
                            s4[i].setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                        if(s5[i].getText().toString().equals(table.getSub())){
                            s5[i].setText("");
                            s5[i].setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }

                    //sqlite에서 과목 삭제
                    myDBHandler.delete(table.getSub());
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
