package com.utili.aarzee.logoquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by Ram on 2/1/2017.
 */

public class SqliteController extends SQLiteOpenHelper {

    //basics to initialize the database
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db_quiz.db";
    private static String CURRENT_DB_PATH = Environment.getDataDirectory() + "/com.utili.aarzee.logoquiz/databases/db_quiz.db";
    private final Context myContext;

    private ArrayList<Group_Model> cartList1 = new ArrayList<Group_Model>();
    private ArrayList<Item_Model> cartList2 = new ArrayList<Item_Model>();
    String symptoms;

    // constructor
    public SqliteController(Context applicationcontext) {
        super(applicationcontext, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = applicationcontext;
        //Log.d(KEY_LOG, KEY_LOG);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }

    // get all list of group from database
    public ArrayList<Group_Model> getGroup() {

        cartList1.clear();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_group", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Group_Model group = new Group_Model();

                    group.id = cursor.getInt(cursor.getColumnIndex("id"));
                    group.grp_name = cursor.getString(cursor.getColumnIndex("grp_name"));
                    group.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    cartList1.add(group);


                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList1;
    }


    // get success list from database
    public ArrayList<Item_Model> getSuccessList() {
        cartList2.clear();
//symptoms="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT grp_id,sum(result='success') as success_no,sum(result='fail') as fail_no,sum(result='hanged') as hang_no,count(result) as total_no,proper_name FROM tbl_item group by grp_id", null);
//                "SELECT grp_id,count(result) as success_no FROM tbl_item WHERE result = 'success' GROUP BY grp_id", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();


                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    item.countSuccess = cursor.getInt(cursor.getColumnIndex("success_no"));
                    item.countFail = cursor.getInt(cursor.getColumnIndex("fail_no"));
                    item.countHanged = cursor.getInt(cursor.getColumnIndex("hang_no"));
                    item.countTotal = cursor.getInt(cursor.getColumnIndex("total_no"));
                    item.proper_name = cursor.getString(cursor.getColumnIndex("proper_name"));
                    cartList2.add(item);


                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }


    // get all list of item from database
    public ArrayList<Item_Model> getItem(String grpFilter) {
        cartList2.clear();
//symptoms="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_item WHERE grp_id = '"+grpFilter+"'", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();

                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.item_name = cursor.getString(cursor.getColumnIndex("item_name"));
                    item.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    item.result = cursor.getString(cursor.getColumnIndex("result"));
                    item.correct_try = cursor.getString(cursor.getColumnIndex("correct_try"));
                    item.proper_name = cursor.getString(cursor.getColumnIndex("proper_name"));
                    cartList2.add(item);


                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }

    // get all list of item for quiz from database
    public ArrayList<Item_Model> getQuiz(String itemFilter) {
        cartList2.clear();
//symptoms="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_item WHERE item_id = '"+itemFilter+"'", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();

                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.item_name = cursor.getString(cursor.getColumnIndex("item_name"));
                    item.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    item.result = cursor.getString(cursor.getColumnIndex("result"));
                    item.hang_no = cursor.getInt(cursor.getColumnIndex("hang_no"));
                    item.option = cursor.getString(cursor.getColumnIndex("option"));
                    item.correct_try = cursor.getString(cursor.getColumnIndex("correct_try"));
                    item.hint1 = cursor.getString(cursor.getColumnIndex("hint1"));
                    item.hint2 = cursor.getString(cursor.getColumnIndex("hint2"));
                    item.wiki_link = cursor.getString(cursor.getColumnIndex("wiki_link"));
                    item.reference_try = cursor.getString(cursor.getColumnIndex("reference_try"));
                    item.proper_name = cursor.getString(cursor.getColumnIndex("proper_name"));
                    item.image2_status = cursor.getInt(cursor.getColumnIndex("image2_status"));
                    item.image3_status = cursor.getInt(cursor.getColumnIndex("image3_status"));
                    item.image4_status = cursor.getInt(cursor.getColumnIndex("image4_status"));
                    item.reference_options = cursor.getString(cursor.getColumnIndex("reference_options"));
                    item.item_name_filtered = cursor.getString(cursor.getColumnIndex("item_name_filtered"));
                    cartList2.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }


    // get all list of item from database
    public ArrayList<Item_Model> getAllItem() {
        cartList2.clear();
//symptoms="";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_item", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();

                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.item_name = cursor.getString(cursor.getColumnIndex("item_name"));
                    item.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    item.result = cursor.getString(cursor.getColumnIndex("result"));
                    item.hang_no = cursor.getInt(cursor.getColumnIndex("hang_no"));
                    item.option = cursor.getString(cursor.getColumnIndex("option"));
                    item.correct_try = cursor.getString(cursor.getColumnIndex("correct_try"));
                    item.hint1 = cursor.getString(cursor.getColumnIndex("hint1"));
                    item.hint2 = cursor.getString(cursor.getColumnIndex("hint2"));
                    item.wiki_link = cursor.getString(cursor.getColumnIndex("wiki_link"));
                    item.reference_try = cursor.getString(cursor.getColumnIndex("reference_try"));
                    item.proper_name = cursor.getString(cursor.getColumnIndex("proper_name"));
                    item.image2_status = cursor.getInt(cursor.getColumnIndex("image2_status"));
                    item.image3_status = cursor.getInt(cursor.getColumnIndex("image3_status"));
                    item.image4_status = cursor.getInt(cursor.getColumnIndex("image4_status"));
                    item.reference_options = cursor.getString(cursor.getColumnIndex("reference_options"));
                    item.item_name_filtered = cursor.getString(cursor.getColumnIndex("item_name_filtered"));
                    cartList2.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }

    public ArrayList<Item_Model> getNextQuiz(String grp_name, Integer i_id){
        cartList2.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_item WHERE grp_id = '"+grp_name+"' and result ='fail' and id >"+i_id+" order by id asc limit 1", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();

                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    cartList2.add(item);


                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }

    public ArrayList<Item_Model> getPreviousQuiz(String grp_name, Integer i_id){
        cartList2.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM tbl_item WHERE grp_id = '"+grp_name+"' and result ='fail' and id <"+i_id+" order by id desc limit 1", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Item_Model item = new Item_Model();

                    item.id = cursor.getInt(cursor.getColumnIndex("id"));
                    item.item_id = cursor.getString(cursor.getColumnIndex("item_id"));
                    item.grp_id = cursor.getString(cursor.getColumnIndex("grp_id"));
                    cartList2.add(item);


                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return cartList2;
    }


    //for update hang no
    public void updateHang(String itemFilter,Integer hNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hang_no", hNo);


        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }

    //for update result
    public void updateResult(String itemFilter,String resultVal) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("result", resultVal);

        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }

    //for update option
    public void updateOption(String itemFilter,String remOption) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("option",remOption);

        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }

    //for update correct try
    public void updateCorrectTry(String itemFilter,String ansList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("correct_try", ansList);

        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }

    //for update image option
    public void updateImageOption(String itemFilter,String columnName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(columnName,1);

        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }

    //for reset data
    public void resetData(String itemFilter, String reference_try,String reference_options) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("result", "fail");
        contentValues.put("hang_no", 0);
        contentValues.put("image2_status", 0);
        contentValues.put("image3_status", 0);
        contentValues.put("image4_status", 0);
        contentValues.put("option", reference_options);
        contentValues.put("correct_try", reference_try);

        db.update("tbl_item", contentValues, "item_id = '"+itemFilter+"'",null);
        db.close();
    }


    //for reset data
    public void resetAllData(ArrayList<Item_Model> all_list) {

        SQLiteDatabase db = this.getWritableDatabase();
        for(Item_Model obj: all_list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("result", "fail");
            contentValues.put("hang_no", 0);
            contentValues.put("image2_status",0);
            contentValues.put("image3_status",0);
            contentValues.put("image4_status",0);
            contentValues.put("option", obj.getReference_options());
            contentValues.put("correct_try", obj.getReference_try());

            db.update("tbl_item", contentValues, "item_id ='"+obj.getItem_id()+"'", null);
        }
        db.close();
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();
        }

    }
    public boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = "/data" + CURRENT_DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database does't exist yet.
        }

        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }


    // database from asset
    public boolean importDBFromAsset() throws IOException {
        String DB_PATH = "/data/data/com.utili.aarzee.logoquiz/databases/";
        try {
            // Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open("db_quiz.db");
            // Path to the just created empty db
            String outFileName = DB_PATH + "db_quiz.db";
            // Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();

        } catch (Exception e) {
            return false;
        }
        return true;
    }


}


