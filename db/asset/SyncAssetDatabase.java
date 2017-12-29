package com.companyname.asset.db.asset;

import android.app.Activity;
import android.content.Context;

import com.companyname.asset.bean.scan.Asset;

import java.util.List;

/**
 * @author linking
 *         date: 2017/12/27.
 *         description: 缓存用户信息
 */
public class SyncAssetDatabase {

    public interface SynchronizeListener {
        void onSuccess(byte[] arg2);

        void onFailure();
    }

    private AssetInfoDatabase assetDb;
    private List<Asset> localDatas;
    private Activity activity;

    public SyncAssetDatabase(Context context) {
        assetDb = new AssetInfoDatabase(context);
    }

    public void downloadUserInfo(Activity activity, Asset asset, SyncAssetDatabase.SynchronizeListener cb) {
//        assetDb = new AssetInfoDatabase(activity);
        localDatas = assetDb.query(" where id=" + asset.getID());
        this.activity = activity;
        if (localDatas.size() == 0) {
            assetDb.insert(asset);
        } else {
            assetDb.update(asset);
        }
    }

    /**
     * 判断 asset db 不为空
     *
     * @param activity
     * @return
     */
    public boolean isAssetDBNotEmpty(Activity activity) {
//        assetDb = new AssetInfoDatabase(activity);
        localDatas = assetDb.query();
        this.activity = activity;
        return (localDatas.size() > 0);
    }

    /**
     * 批量存储 asset list
     */
    public void insertAssetListData(Activity activity, List<Asset> data) {
        if (data.size() > 0) {
//            assetDb = new AssetInfoDatabase(activity);
            for (Asset asset : data) {
                assetDb.insert(asset);
            }
        }
    }

    /**
     * 测试期间用
     */
    public void deleteDatabase(Context context) {
        assetDb.deleteDatabase(context);
    }

}