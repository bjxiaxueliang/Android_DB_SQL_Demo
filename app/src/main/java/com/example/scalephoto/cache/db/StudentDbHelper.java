package com.example.scalephoto.cache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.scalephoto.cache.models.StudentModel;
import com.example.scalephoto.cache.utils.SerializeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * BI DB helper
 */
public class StudentDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "xiaxl: ";


    public StudentDbHelper(Context context) {
        super(context, StudentDbContent.DATABASE_NAME, null, StudentDbContent.DATABASE_VERSION);
    }

    // 建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表
        createStudentTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级后，删除旧表
        if (newVersion >= 2 && oldVersion < 2) {
            destoryStudentTable(db);
        }
        // 升级后，重建表
        onCreate(db);
    }
    //#############################################################建表###删表###############################################################

    /**
     * 建立 数据存储表
     *
     * @param db
     */
    private void createStudentTable(SQLiteDatabase db) {
        StringBuffer createCacheSql = new StringBuffer()
                .append("CREATE TABLE IF NOT EXISTS ")
                //
                .append(StudentDbContent.StudentTable.TABLE_NAME)
                //
                .append(" (")
                //
                .append("_id integer primary key autoincrement,")
                // String 姓名
                .append(StudentDbContent.StudentTable.NAME)
                .append(" varchar(255),")
                // Long 学号
                .append(StudentDbContent.StudentTable.SID)
                .append(" INTEGER,")
                // Int 年龄
                .append(StudentDbContent.StudentTable.AGE)
                .append(" INTEGER,")
                //.append(" INTEGER CHECK(age>18 and age<60),")
                // Int 性别
                .append(StudentDbContent.StudentTable.GENDER)
                .append(" INTEGER,")
                // Float 成绩
                .append(StudentDbContent.StudentTable.SCORE)
                .append(" REAL")
                //
                .append(");");
        Log.d(TAG, "createStudentTable: " + createCacheSql.toString());
        db.execSQL(createCacheSql.toString());
    }

    /**
     * destory  数据存储表
     *
     * @param db
     */
    private void destoryStudentTable(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        sb.append(";");
        Log.d(TAG, "destoryStudentTable: " + sb.toString());
        db.execSQL(sb.toString());
    }

    //##################################################################SnsBi 数据库的 增删改查 begin####################################################################

    /**
     * @param data
     * @return
     */
    public synchronized boolean insert_StudentTable(StudentModel data) {
        Log.i(TAG, "insert_StudentTable");
        if (data == null) {
            return false;
        }
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return false;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        // ---表中的列---
        sb.append(" (");
        sb.append(StudentDbContent.StudentTable.NAME);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.SID);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.AGE);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.GENDER);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.SCORE);
        sb.append(")");
        // ---插入的数据---
        sb.append(" VALUES ('");
        sb.append(data.name);
        sb.append("', ");
        //
        sb.append(data.sid);
        sb.append(", ");
        //
        sb.append(data.age);
        sb.append(", ");
        //
        sb.append(data.gender);
        sb.append(", ");
        //
        sb.append(data.score);
        sb.append(");");

        Log.d(TAG, "insertStudentTable: " + sb.toString());
        //
        try {
            db.execSQL(sb.toString());
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "insert SQL error:" + e.getMessage(), e);
        } catch (SQLiteException e) {
            Log.e(TAG, "insert database error!", e);
        } catch (SQLException e) {
            Log.e(TAG, "insert fail!", e);
        }


        return true;
    }

    // SELECT DISTINCT 列名称 FROM 表名称
    public synchronized List<StudentModel> query_StudentTable() {
        Log.i(TAG, "query_StudentTableByScoreAge:");
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT *");
        // 表名称
        sb.append(" from ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        // ---排序---
        sb.append(" ORDER BY ");
        sb.append(StudentDbContent.StudentTable.SID);
        sb.append(" DESC;"); // 降序

        Log.d(TAG, "queryStudentTable: " + sb.toString());

        List<StudentModel> dataList = null;

        Cursor queryResult = null;
        try {
            queryResult = db.rawQuery(sb.toString(), new String[]{});


            if (queryResult != null) {
                //
                dataList = new ArrayList<StudentModel>();
                //
                if (queryResult.getCount() > 0 && queryResult.moveToFirst()) {
                    int nameColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.NAME);
                    int sidColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.SID);
                    int ageColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.AGE);
                    int genderColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.GENDER);
                    int scoreColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.SCORE);
                    do {
                        StudentModel data = new StudentModel();
                        //
                        data.name = queryResult.getString(nameColumn);
                        data.sid = queryResult.getLong(sidColumn);
                        data.age = queryResult.getInt(ageColumn);
                        data.gender = queryResult.getInt(genderColumn);
                        data.score = queryResult.getFloat(scoreColumn);
                        //
                        dataList.add(data);

                    } while (queryResult.moveToNext());
                }
            }


        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Query SQL error:" + e.getMessage(), e);
        } catch (SQLiteException e) {
            Log.e(TAG, "Query database error!", e);
        } catch (SQLException e) {
            Log.e(TAG, "Query fail!", e);
        } finally {
            if (queryResult != null) {
                queryResult.close();
                queryResult = null;
            }
        }

        return dataList;
    }

    // SELECT DISTINCT 列名称 FROM 表名称
    public synchronized List<StudentModel> query_StudentTableByScoreAge(float score, int age) {
        Log.i(TAG, "query_StudentTableByScoreAge:");
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT ");
        // ---列名称---
        sb.append(StudentDbContent.StudentTable.NAME);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.SID);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.AGE);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.GENDER);
        sb.append(",");
        sb.append(StudentDbContent.StudentTable.SCORE);
        // 表名称
        sb.append(" from ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        // 表名称
        sb.append(" where ");
        sb.append(StudentDbContent.StudentTable.SCORE);
        sb.append(">=");
        sb.append(score);
        sb.append(" and ");
        sb.append(StudentDbContent.StudentTable.AGE);
        sb.append("<=");
        sb.append(age);
        // ---排序---
        sb.append(" ORDER BY ");
        sb.append(StudentDbContent.StudentTable.SID);
        sb.append(" DESC;"); // 降序

        Log.d(TAG, "queryStudentTable: " + sb.toString());

        List<StudentModel> dataList = null;

        Cursor queryResult = null;
        try {
            queryResult = db.rawQuery(sb.toString(), new String[]{});


            if (queryResult != null) {
                //
                dataList = new ArrayList<StudentModel>();
                //
                if (queryResult.getCount() > 0 && queryResult.moveToFirst()) {
                    int nameColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.NAME);
                    int sidColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.SID);
                    int ageColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.AGE);
                    int genderColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.GENDER);
                    int scoreColumn = queryResult
                            .getColumnIndex(StudentDbContent.StudentTable.SCORE);
                    do {
                        StudentModel data = new StudentModel();
                        //
                        data.name = queryResult.getString(nameColumn);
                        data.sid = queryResult.getLong(sidColumn);
                        data.age = queryResult.getInt(ageColumn);
                        data.gender = queryResult.getInt(genderColumn);
                        data.score = queryResult.getFloat(scoreColumn);
                        //
                        dataList.add(data);

                    } while (queryResult.moveToNext());
                }
            }


        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Query SQL error:" + e.getMessage(), e);
        } catch (SQLiteException e) {
            Log.e(TAG, "Query database error!", e);
        } catch (SQLException e) {
            Log.e(TAG, "Query fail!", e);
        } finally {
            if (queryResult != null) {
                queryResult.close();
                queryResult = null;
            }
        }

        return dataList;
    }



    /**
     * 删除
     *
     * @return
     */
    public synchronized boolean _delete_StudentTableByScore(float score) {
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return false;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        sb.append(" where ");
        sb.append(StudentDbContent.StudentTable.SCORE);
        sb.append("<=");
        sb.append(score);
        sb.append(";");

        Log.d(TAG, "deleteStudentTable: " + sb.toString());

        try {
            db.execSQL(sb.toString());
            return true;
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Delete fail!", e);
        }
        return false;
    }

    /**
     * 更新
     *
     * @return
     */
    public synchronized boolean _update_StudentTableScoreByName(float score, String name) {
        //
        SQLiteDatabase db = this.getSnsBiDb();
        if (db == null) {
            return false;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE ");
        sb.append(StudentDbContent.StudentTable.TABLE_NAME);
        //  ----更新的字段----
        sb.append(" SET ");
        sb.append(StudentDbContent.StudentTable.SCORE);
        sb.append("=");
        sb.append(score);
        // ----------
        sb.append(" WHERE ");
        sb.append(StudentDbContent.StudentTable.NAME);
        sb.append("='");
        sb.append(name);
        sb.append("';");

        Log.d(TAG, "updateStudentTable: " + sb.toString());
        try {
            db.execSQL(sb.toString());
            return true;
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "_update_StudentTableScoreByName fail!", e);
        }
        return false;
    }

    /**
     * Get instance of SQLiteDatabase,deal with exception
     *
     * @return
     */
    public synchronized SQLiteDatabase getSnsBiDb() {
        return this.getWritableDatabase();
    }


}
