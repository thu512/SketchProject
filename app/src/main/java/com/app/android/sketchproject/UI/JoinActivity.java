package com.app.android.sketchproject.UI;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.android.sketchproject.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


//회원가입 액티비티
public class JoinActivity extends AppCompatActivity {
    Button btn1,btn2;                                                                                           //btn1=회원가입, btn2=id중복확인
    Boolean idchecked = false;                                                                              //id중복확인 체크 변수

    EditText text1, text2, text3,text4;                                                                 //아이디,패스워드,이름,이메일
    String str1, str2, str3, str4;                                                                      //위 항목들을 넘길 변수

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        text1 = (EditText) findViewById(R.id.text1);
        text2 = (EditText) findViewById(R.id.edit2);
        text3 = (EditText) findViewById(R.id.text3);
        text4 = (EditText) findViewById(R.id.text4);


        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text1.getText().toString().equals("") || text2.getText().toString().equals("") || text3.getText().toString().equals("") || text4.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"항목을 모두 입력해주세요",Toast.LENGTH_LONG).show();  //항목에 빈칸이 있을때
                    return;
                }
                if(!idchecked){
                    Toast.makeText(getApplicationContext(), "ID중복확인을 해주세요.", Toast.LENGTH_LONG).show(); //id중복확인 안했을 때
                    return;
                }
                //변수에 항목데이터 저장
                str1 = text1.getText().toString();
                str2 = text2.getText().toString();
                str3 = text3.getText().toString();
                str4 = text4.getText().toString();
                insertToDB(str1, str2, str3, str4);                                                 //DB생성
                makeDirectory(str1);                                                                 //디렉토리 생성
            }

        });

        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = text1.getText().toString();
                if(str1.equals("")){
                    Toast.makeText(getApplicationContext(),"ID를 입력해주세요.",Toast.LENGTH_LONG).show();
                    return;
                }

                IdCheck(str1);
            }
        });




    }

    //DB생성 메소드
    private void insertToDB(String str1, String str2, String str3, String str4){
        class InsertData extends AsyncTask<String, Void, String>{



            @Override
            protected String doInBackground(String... params) {
                try {

                    String id = (String)params[0];
                    String pw = (String)params[1];
                    String name = (String)params[2];
                    String email = (String)params[3];

                    String link = "http://211.253.8.156/dbinsert.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");
                    data += "&" + URLEncoder.encode("pw","UTF-8") + "=" + URLEncoder.encode(pw,"UTF-8");
                    data += "&" + URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8");
                    data += "&" + URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!= null){
                        sb.append(line);
                        break;
                    }return sb.toString();

                }catch (Exception e){
                    Log.d("ERROR",e+"");
                    return new String("Exception: "+e.getMessage());
                }

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(JoinActivity.this,"","잠시만 기다려주세요.");
            }
        }

        InsertData task = new InsertData();
        task.execute(str1,str2,str3,str4);
    }

    //디렉토리 생성 메소드
    private void makeDirectory(String id){
        class makeDir extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... strings) {
                try {

                    String id = (String)strings[0];

                    String link = "http://211.253.8.156/mkdir.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!= null){
                        sb.append(line);
                        break;
                    }return sb.toString();

                }catch (Exception e){
                    Log.d("ERROR",e+"");
                    return new String("Exception: "+e.getMessage());
                }

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_LONG).show();
            }
        }

        makeDir dir = new makeDir();
        dir.execute(id);
    }

    //id중복확인 메소드
    private void IdCheck(String id){
        class GetIdCheck extends AsyncTask<String, Void, String>{
            ProgressDialog dialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(JoinActivity.this,"","잠시만 기다려주세요");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dialog.dismiss();

                //id가 없을 때
                if(s.equals("0")){
                    idchecked = true;
                    Toast.makeText(getApplicationContext(),"ID를 사용하실 수 있습니다.",Toast.LENGTH_LONG).show();
                }
                //id가 존재할 때
                else{
                    idchecked = false;
                    Toast.makeText(getApplicationContext(),"ID가 이미 존재합니다.",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... strings) {
                try{

                    String id = (String)strings[0];

                    String link = "http://211.253.8.156/idcheck.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");

                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine())!=null){
                        sb.append(line);
                    }

                    reader.close();

                    return sb.toString();
                }catch (Exception e){

                    return new String("Exception: "+e.getMessage());
                }
            }
        }

        GetIdCheck getIdCheck = new GetIdCheck();
        getIdCheck.execute(id);
    }
}
