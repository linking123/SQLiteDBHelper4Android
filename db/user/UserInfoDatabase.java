package com.companyname.asset.db.user;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.companyname.asset.bean.scan.User;
import com.companyname.asset.db.CreateDatabaseAndTables;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linking
 *         date: 2017/12/22.
 *         description: User table CRUD
 */
public class UserInfoDatabase {

    private final CreateDatabaseAndTables sqLiteHelper4UserInfo;

    public UserInfoDatabase(Context context) {
        sqLiteHelper4UserInfo = new CreateDatabaseAndTables(context);
    }

    /**
     * 增
     *
     * @param data
     */
    public void insert(User data) {
        String sql = "insert into " + CreateDatabaseAndTables.assetTablesNames[0];

        sql += "(" + CreateDatabaseAndTables.userTableFields[0] + ") values(?)";

        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        sqlite.execSQL(sql, new String[]{data.getUserName() + ""});
        sqlite.close();
    }

    /**
     * 删
     *
     * @param id
     */
    public void delete(int id) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        String sql = ("delete from " + CreateDatabaseAndTables.assetTablesNames[0] + " where userId=?");
        sqlite.execSQL(sql, new Integer[]{id});
        sqlite.close();
    }

    /**
     * 改
     *
     * @param data
     */
    public void update(User data) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        String sql = ("update " + CreateDatabaseAndTables.assetTablesNames[0] + " set " + CreateDatabaseAndTables.userTableFields[0]
                    + "=? where userId=?");
        sqlite.execSQL(sql,
                new String[]{data.getUserName() + "", data.getUserId() + ""});
        sqlite.close();
    }

    public List<User> query() {
        return query(" ");
    }

    /**
     * 查
     *
     * @param where
     * @return
     */
    public List<User> query(String where) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getReadableDatabase();
        ArrayList<User> data ;
        data = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery("select * from "
                + CreateDatabaseAndTables.assetTablesNames[0] + where, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            User User = new User();
            User.setUserId(cursor.getInt(0));
            User.setUserName(cursor.getString(1));
            data.add(User);
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();

        return data;
    }

    /**
     * 重置
     *
     * @param datas
     */
    public void reset(List<User> datas) {
        if (datas != null) {
            SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + CreateDatabaseAndTables.assetTablesNames[0]);
            // 重新添加
            for (User data : datas) {
                insert(data);
            }
            sqlite.close();
        }
    }

    /**
     * 保存一条数据到本地(若已存在则直接覆盖)
     *
     * @param data
     */
    public void save(User data) {
        List<User> datas = query(" where userId=" + data.getUserId());
        if (datas != null && !datas.isEmpty()) {
            update(data);
        } else {
            insert(data);
        }
    }

    public void destroy() {
        sqLiteHelper4UserInfo.close();
    }

    public void deleteDatabase(Context context) {
        sqLiteHelper4UserInfo.deleteDatabase(context);
    }


}
