package com.companyname.asset.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author linking
 *         date: 2017/12/29.
 *         description: 统一管理 asset 数据库表、字段名
 */
public class CreateDatabaseAndTables extends SQLiteOpenHelper {

    /* 数据库名 */
    public static final String dbName = "asset";

    /*统一定义数据库表名 */
    public static final String[] assetTablesNames = {"user", "asset"};

    /**
     * 枚举：统一定义除各表id之外的字段名；具体意义请查看 /bean/scan/
     * 修改增减此处的字段，需要同步修改{#UserInfoDatabase}{#AssetInfoDatabase}
    */
    public enum AssetTableFields {
        /* 以下为 asset 表字段 */
        CZZ003("czz003", 2), CZZ004("czz004", 3), 
        ID("id", 9), CZZ018("czz018", 11),
        /* 以下为user表字段 */
        USERID("userId", 21), USERNAME("userName", 22), USERLOGIN("userLogin", 23),
        USERPASSWORD("userPassword", 24), USERMOBILE("userMobile", 27), USERSTATUS("userStatus", 28);

        private String name;
        private int index;

        AssetTableFields(String name, int index) {
            this.name = name;
            this.index = index;
        }

        public static String getName(int index) {
            for (AssetTableFields c : AssetTableFields.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    public static final String CREATE_ASSET_TABLE_USER = "create table IF NOT EXISTS "
            + assetTablesNames[0]
            + " (userId integer primary key autoincrement, " + AssetTableFields.USERNAME.getName() + " text)";

    public static final String CREATE_ASSET_TABLES_ASSET = "create table IF NOT EXISTS "
            + assetTablesNames[1]
            + " (id integer primary key autoincrement, "
            + AssetTableFields.CZZ003.getName() + " text, " + AssetTableFields.CZZ004.getName() + " text, "
            + AssetTableFields.CZZ018.getName() + " text)";

    public CreateDatabaseAndTables(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public CreateDatabaseAndTables(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public CreateDatabaseAndTables(Context context) {
        super(context, dbName, null, 1, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建语句只执行一次，如果后来需要加数据库表或者改结构，需要删除数据库，重新运行
        db.execSQL(CREATE_ASSET_TABLE_USER);
        db.execSQL(CREATE_ASSET_TABLES_ASSET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 删除数据库
     *
     * @param context 上下文
     * @return boolean
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(dbName);
    }
}