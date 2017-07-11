package com.app.android.sketchproject.UI;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.android.sketchproject.MainActivity;
import com.app.android.sketchproject.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by jieun on 2017-05-27.
 */

//마이페이지(회원탈퇴) 레이아웃
public class AccountActivity extends AppCompatActivity {


    TextView idbox;
    TextView pwbox;
    ProgressDialog progressDialog;
    Button signout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //탈퇴버튼클릭시
        signout = (Button) findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = createDialogBox();
                dialog.show();
            }
        });

        idbox = (TextView) findViewById(R.id.idbox);
        pwbox = (TextView) findViewById(R.id.pwbox);

        SharedPreferences preferences = getSharedPreferences("settings",Activity.MODE_PRIVATE);
        String id = preferences.getString("id","모름");
        String pw = preferences.getString("pw","모름");

        idbox.setText(id);
        pwbox.setText(pw);

    }

    private AlertDialog createDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("회원을 탈퇴하시겠습니까?");

        //예 클릭
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //탈퇴 메소드
                GetPreference();
            }
        });

        //아니요 클릭
        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //이벤트x
            }
        });
        AlertDialog dialog = builder.create();

        return dialog;
    }


    private void GetPreference(){

        //**************************세션 불러오기 부분*****************************
        SharedPreferences preferences = getSharedPreferences("settings", Activity.MODE_PRIVATE);
        String id = preferences.getString("id","");
        String pw = preferences.getString("pw","");
        //**************************************************************************

        DeleteFromDB(id, pw);                                               //DB 삭제 메소드
        RemoveDirectory(id);                                                //디렉토리 삭제 메소드

        //********************세션 삭제*******************************************
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        //************************************************************************

        Intent intent = new Intent(AccountActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    }


    //DB 삭제 메소드
    private void DeleteFromDB(String id, String pw){
        class DeleteData extends AsyncTask<String, Void, String>{

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(AccountActivity.this,"","잠시만 기다려주세요.");
            }

            @Override
            protected String doInBackground(String... strings) {
                try{
                    String id = strings[0];
                    String pw = strings[1];

                    String link = "http://211.253.8.156/dbdelete.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");
                    data += "&" + URLEncoder.encode("pw","UTF-8") + "=" + URLEncoder.encode(pw,"UTF-8");

                    URL url = new URL(data);
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
                    }
                    return sb.toString();
                }catch (Exception e){
                    Log.d("error:",e+"");
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }
        DeleteData deleteData = new DeleteData();
        deleteData.execute(id,pw);
    }

    //디렉토리 삭제 메소드
    private void RemoveDirectory(String id){
        class RemoveDir extends AsyncTask<String, Void, String>{

            @Override
            protected String doInBackground(String... strings) {
                try {

                    String id = (String)strings[0];

                    String link = "http://211.253.8.156/rmdir.php";
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
                Toast.makeText(getApplicationContext(),"탈퇴가 완료되었습니다.",Toast.LENGTH_LONG).show();
            }
        }
        RemoveDir removeDir = new RemoveDir();
        removeDir.execute(id);
    }
}
