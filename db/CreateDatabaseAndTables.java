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

    /**
     * 数据库名
     */
    public static final String dbName = "asset";

    /*统一定义数据库表名 */
    public static final String[] assetTablesNames = {"user", "asset"};

    //除id之外的字段名，统一定义字段名
    public static final String[] assetTableFields = {"assetCode", "assetName", "assetStatus"};
    public static final String[] userTableFields = {"userName"};

    public static final String CREATE_ASSET_TABLES_ASSET = "create table IF NOT EXISTS "
            + assetTablesNames[1]
            + " (id integer primary key autoincrement, "
            + assetTableFields[0] + " text, " + assetTableFields[1] + " text, "+ assetTableFields[2] + " text)";

    public static final String CREATE_ASSET_TABLE_USER = "create table IF NOT EXISTS "
            + assetTablesNames[0]
            + " (userId integer primary key autoincrement, userName text)";

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
        db.execSQL(CREATE_ASSET_TABLE_USER);
        db.execSQL(CREATE_ASSET_TABLES_ASSET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 删除数据库
     *
     * @param context
     * @return
     */
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(dbName);
    }
}