package com.app.android.sketchproject.UI;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.app.android.sketchproject.R;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

//로그인 액티비티

public class LoginActivity extends AppCompatActivity {

    Button btn1, btn2, btnlogout;   //로그인, 회원가입버튼
    EditText text1, text2;  //아이디,패스워드

    CheckBox loginCheck;                 //자동로그인 체크박스
    Boolean autoLogin = false;          //자동로그인 체크변수, 초기값은 false

    OAuthLoginButton buttonOAuthLoginImg; //네이버 아이디로 로그인


    private static String OAUTH_CLIENT_ID = "PVLuNmERnPSu0BxhacyZ";     //클라이언트 ID
    private static String OAUTH_CLIENT_SECRET = "XzSGPnoUAG";           //클라이언트 SECRET
    private static String OAUTH_CLIENT_NAME = "CamBook";                //애플리케이션 NAME

    private static String sns_id;
    private static String sns_name;
    private static String sns_email;

    private static Context context;
    private static OAuthLogin mOAuthLoginInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        /*
        text1 = (EditText) findViewById(R.id.edit1);
        text2 = (EditText) findViewById(R.id.edit2);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);

        loginCheck = (CheckBox)findViewById(R.id.loginCheck);
        */

        //네이버아이디로그인 메소드 초기화
        mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.init(context,OAUTH_CLIENT_ID,OAUTH_CLIENT_SECRET,OAUTH_CLIENT_NAME);

        //네이버아이디로그인 버튼 초기화
        buttonOAuthLoginImg = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler);
        buttonOAuthLoginImg.setBgResourceId(R.mipmap.img_loginbtn_usercustom);

        /*
        //로그인버튼 클릭
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(text1.getText().toString().equals("") || text2.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"항목을 모두 입력해주세요",Toast.LENGTH_LONG).show();
                return;
            }
                String str1 = text1.getText().toString();
                String str2 = text2.getText().toString();

                getID(str1, str2);
            }

        });

        //자동로그인 체크박스 클릭
        loginCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {            //자동로그인 체크 이벤트
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(loginCheck.isChecked()){
                    autoLogin = true;               //자동로그인 체크 시 관련 변수를 true로 바꿔줌
                }
            }
        });

        //회원가입 버튼 클릭
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        */

        //연동해제 버튼 클릭
        btnlogout = (Button) findViewById(R.id.btnlogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });


        String s = mOAuthLoginInstance.getAccessToken(context);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();

    }

    //뷰 초기화
    private void initView(){


    }

    //id와 pw를 이용해 php로 넘겨서 쿼리값을 가져오는 함수
    /*
    private void getID(final String str1, String str2){
        class getCnt extends AsyncTask<String, Void, String>{
            ProgressDialog progressDialog;

            @Override
            protected String doInBackground(String... strings) {

                try{
                    String id = (String)strings[0];
                    String pw = (String)strings[1];

                    String link = "http://211.253.8.156/idselect.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");
                    data += "&" + URLEncoder.encode("pw","UTF-8") + "=" + URLEncoder.encode(pw,"UTF-8");

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

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this,"","잠시만 기다려주세요.");
            }

            //로그인 동작
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                //**********************ID와 PW가 일치할 때
                if(s.equals("1")){
                    String id = text1.getText().toString();
                    String pw = text2.getText().toString();
                    if(autoLogin) {setAutoLogin(id, pw, true);} //id와 pw가 일치하고 자동로그인 체크되어있을 때
                    else {setAutoLogin(id, pw, false);}         //id와 pw가 일치하고 자동로그인 체크가 되어있지 않을 때
                    Intent intent = new Intent(LoginActivity.this,AccountActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }
                //**********************일치하지 않을 때
                else if(s.equals("0")){
                    Toast.makeText(getApplicationContext(),"아이디나 비밀번호를 다시 입력해주세요.",Toast.LENGTH_LONG).show();
                }


            }
        }

        getCnt g = new getCnt();
        g.execute(str1,str2);
    }
    */

    //SharedPreferences를 이용해서 폰 안에 로그인정보 저장하기
    /*
    private void setAutoLogin(String id, String pw, boolean check){
        SharedPreferences preferences = getSharedPreferences("settings",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",id);
        editor.putString("pw",pw);
        editor.putBoolean("autochecked",check);
        editor.commit();
    }

    */


    //네아로 핸들러 클래스
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean b) {
            if (b) {
                //토큰 정보 가져오기
                String accessToken = mOAuthLoginInstance.getAccessToken(context);
                String refreshToken = mOAuthLoginInstance.getRefreshToken(context);
                long expiresAt = mOAuthLoginInstance.getExpiresAt(context);
                String tokenType = mOAuthLoginInstance.getTokenType(context);

                //회원정보 api 가져오기
                getSNSProfile(accessToken);

            } else {
                String errorCode = mOAuthLoginInstance.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginInstance.getLastErrorDesc(context);
                Toast.makeText(context, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };

    //회원정보 api 가져오기 메소드
    void getSNSProfile(String str){
        class getTokenID extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                getRequestApi(s);
            }

            @Override
            protected String doInBackground(String... strings) {

                String token = (String)strings[0];          //네이버 엑세스 토큰
                String header = "Bearer " + token;          //엑세스 토큰을 전달하는 헤더(토큰 타입은 Bearer값으로 고정되어있음)
                try {
                    String apiURL = "https://openapi.naver.com/v1/nid/me";
                    return mOAuthLoginInstance.requestApi(context, token, apiURL);          //회원정보 api 가져오는 메소드(반환형: String)
                }catch(Exception e){
                    return new String("Exception: "+e.getMessage());
                }

            }
        }
        getTokenID tokenID = new getTokenID();
        tokenID.execute(str);
    }

    //회원정보 결과를 String객체에 각각 넣기
    void getRequestApi(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);               //jSON {}불러오기
            JSONObject c = jsonObject.getJSONObject("response");            //response 태그 불러오기
            sns_id = c.getString("id");                                  //response/id 태그 불러오기
            sns_name = c.getString("name");                              //response/name 태그 불러오기
            sns_email = c.getString("email");                            //response/email 태그 불러오기

            //Toast.makeText(context,"아이디: "+sns_id+"\n 이름: "+sns_name+"\n이메일: "+sns_email,Toast.LENGTH_LONG).show();
            CheckID(sns_id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //네이버 아이디가 서버DB에 존재하는지 확인
    void CheckID(String id){
        class CheckSNSID extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);

                //id가 존재하지 않을 때 - DB생성
                if(s.equals("0")){
                    InsertToDB();
                    makeDirectory();
                    setAutoLogin();
                }
                //id가 존재할 때 - DB생성X
                else{

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
        CheckSNSID snsid = new CheckSNSID();
        snsid.execute(id);
    }

    //DB 생성
    void InsertToDB(){
        class createSNSID extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    String link = "http://211.253.8.156/dbinsert.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(sns_id,"UTF-8");
                    data += "&" + URLEncoder.encode("pw","UTF-8") + "=" + URLEncoder.encode("","UTF-8");
                    data += "&" + URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(sns_name,"UTF-8");
                    data += "&" + URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(sns_email,"UTF-8");

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
                    return new String("Exception: "+e.getMessage());
                }
            }
        }
        createSNSID snsid = new createSNSID();
        snsid.execute();
    }


    //디렉 생성
    void makeDirectory(){
        class makeDir extends AsyncTask<String, Void, String>{
            @Override
            protected String doInBackground(String... strings) {
                try {

                    String link = "http://211.253.8.156/mkdir.php";
                    String data = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(sns_id,"UTF-8");

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
                Toast.makeText(context,"회원가입이 완료되었습니다.",Toast.LENGTH_LONG).show();
            }
        }

        makeDir dir = new makeDir();
        dir.execute();
    }

    //세션 생성
    private void setAutoLogin(){
        SharedPreferences preferences = getSharedPreferences("settings",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("id",sns_id);
        editor.commit();
    }

    //네이버 아이디 연동해제
    private void LogOut(){
        class deleteToken extends AsyncTask<String, Void, String>{
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(String... strings) {
                try{

                    boolean isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(context);

                    if(!isSuccessDeleteToken){
                        Log.d("TAG","errorCode:"+mOAuthLoginInstance.getLastErrorCode(context));
                        Log.d("TAG","errorDesc:"+mOAuthLoginInstance.getLastErrorDesc(context));
                    }

                    return null;
                }catch(Exception e){
                    return null;
                }
            }
        }
        deleteToken dt = new deleteToken();
        dt.execute();
    }


}
