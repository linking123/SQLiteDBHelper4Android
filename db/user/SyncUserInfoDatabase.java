package com.suncreate.asset.db.user;

import android.app.Activity;
import android.content.Context;

import com.suncreate.asset.bean.scan.User;

import java.util.List;

/**
 * @author linking
 *         date: 2017/12/22.
 *         description: 缓存用户信息
 */
public class SyncUserInfoDatabase {

    public interface SynchronizeListener {
        void onSuccess(byte[] arg2);

        void onFailure();
    }

    private UserInfoDatabase userDb;
    private List<User> localDatas;
    private Activity activity;

    public SyncUserInfoDatabase(Context context) {
        userDb = new UserInfoDatabase(context);
    }

    public void downloadUserInfo(Activity activity, User user, SyncUserInfoDatabase.SynchronizeListener cb) {
        try {
//            userDb = new UserInfoDatabase(activity);
            localDatas = userDb.query(" where userId=" + user.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.activity = activity;
        if (localDatas.size() == 0) {
            userDb.insert(user); //一次只能存一个用户；把之前的删除掉；
        } else {
            //这里不能简单的更新用户；必须是删除，只保留一个；
            userDb.update(user);
        }
    }

}
