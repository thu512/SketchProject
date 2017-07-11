package com.app.android.sketchproject.Sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Changjoo on 2017-07-09.
 */

public class MyDBHandler extends SQLiteOpenHelper {


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS timetableDB (sub TEXT, start TEXT, end TEXT, day TEXT, String color);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String sub, String start, String end, String day, String color) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO timetableDB VALUES('" + sub + "', '" + start + "', '" + end+ "','"+day+"','"+color+"');");
        db.close();
    }

//    public void update(String item, int price) {
//        SQLiteDatabase db = getWritableDatabase();
//        // 입력한 항목과 일치하는 행의 가격 정보 수정
//        db.execSQL("UPDATE timetableDB SET price=" + price + " WHERE item='" + item + "';");
//        db.close();
//    }

    public void delete(String sub) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM timetableDB WHERE sub='" + sub + "';");
        db.close();
    }

    public Table getResult(String sub) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        Table table = new Table();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM timetableDB where sub='"+sub+"';", null);
        while (cursor.moveToNext()) {
            table.setSub(cursor.getString(0));
            table.setStart(cursor.getString(1));
            table.setEnd(cursor.getString(2));
            table.setDay(cursor.getString(3));
            table.setColor(cursor.getString(4));

        }

        return table;
    }

    public ArrayList<Table> getAllResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Table> arrayTable = new ArrayList<>();
        Table table;
        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM timetableDB;", null);
        while (cursor.moveToNext()) {
            table = new Table();
            table.setSub(cursor.getString(0));
            table.setStart(cursor.getString(1));
            table.setEnd(cursor.getString(2));
            table.setDay(cursor.getString(3));
            table.setColor(cursor.getString(4));
            arrayTable.add(table);
        }

        return arrayTable;
    }



}


