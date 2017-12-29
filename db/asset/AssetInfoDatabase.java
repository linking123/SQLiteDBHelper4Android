package com.companyname.asset.db.asset;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.companyname.asset.bean.scan.Asset;
import com.companyname.asset.db.CreateDatabaseAndTables;

import java.util.ArrayList;
import java.util.List;

/**
 * @author linking
 *         date: 2017/12/27.
 *         description: 资产本地数据库 增删改查
 */
public class AssetInfoDatabase {

    private final CreateDatabaseAndTables sqLiteHelper4UserInfo;

    public AssetInfoDatabase(Context context) {
        sqLiteHelper4UserInfo = new CreateDatabaseAndTables(context);
    }

    /**
     * 增
     *
     * @param data Asset
     */
    public void insert(Asset data) {
        String sql = "insert into " + CreateDatabaseAndTables.assetTablesNames[1];

        sql += "(" + CreateDatabaseAndTables.assetTableFields[0] + "," +  CreateDatabaseAndTables.assetTableFields[1] + ","
                +  CreateDatabaseAndTables.assetTableFields[2] + ")"
                + " values(?, ?, ?)";

        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        sqlite.beginTransaction();
        try {
            sqlite.execSQL(sql, new String[]{data.getCZZ003() + "" + data.getCZZ004() + ""
                    + data.getCZZ018() + ""});
            sqlite.setTransactionSuccessful();
        } finally {
            sqlite.endTransaction();
        }
        sqlite.close();
    }

    /**
     * 删
     *
     * @param id id
     */
    public void delete(int id) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        String sql = ("delete from " + CreateDatabaseAndTables.assetTablesNames[1] + " where id=?");
        sqlite.execSQL(sql, new Integer[]{id});
        sqlite.close();
    }

    /**
     * 改
     *
     * @param data Asset
     */
    public void update(Asset data) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
        String sql = ("update " + CreateDatabaseAndTables.assetTablesNames[1] + " set " +  CreateDatabaseAndTables.assetTableFields[0] + "=?,"
                +  CreateDatabaseAndTables.assetTableFields[1] + "=?, " +  CreateDatabaseAndTables.assetTableFields[2] + "=? where id=?");
        sqlite.execSQL(sql,
                new String[]{data.getCZZ003() + "", data.getCZZ004() + "", data.getCZZ018() + "",
                        data.getID() + ""});
        sqlite.close();
    }

    /**
     * 取列表所有值
     *
     * @return list
     */
    public List<Asset> query() {
        return query(" ");
    }

    /**
     * 查
     *
     * @param where 查询条件String
     * @return
     */
    public List<Asset> query(String where) {
        SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getReadableDatabase();
        ArrayList<Asset> data;
        data = new ArrayList<>();
        Cursor cursor = sqlite.rawQuery("select * from "
                + CreateDatabaseAndTables.assetTablesNames[1] + where, null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Asset asset = new Asset();
            asset.setID(cursor.getInt(0));
            asset.setCZZ003(cursor.getString(1));
            asset.setCZZ004(cursor.getString(2));
            asset.setCZZ018(cursor.getString(3));
            data.add(asset);
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
     * @param datas List
     */
    public void reset(List<Asset> datas) {
        if (datas != null) {
            SQLiteDatabase sqlite = sqLiteHelper4UserInfo.getWritableDatabase();
            // 删除全部
            sqlite.execSQL("delete from " + CreateDatabaseAndTables.assetTablesNames[1]);
            // 重新添加
            for (Asset data : datas) {
                insert(data);
            }
            sqlite.close();
        }
    }

    /**
     * 保存一条数据到本地(若已存在则直接覆盖)
     *
     * @param data Asset
     */
    public void save(Asset data) {
        List<Asset> datas = query(" where id=" + data.getID());
        if (datas != null && !datas.isEmpty()) {
            update(data);
        } else {
            insert(data);
        }
    }

    public void destroy() {
        sqLiteHelper4UserInfo.close();
    }

    /**
     * 删除数据库
     *
     * @param context Context
     */
    public void deleteDatabase(Context context) {
        sqLiteHelper4UserInfo.deleteDatabase(context);
    }
}
