package com.vv.wanandroid.manager;

import android.text.TextUtils;
import android.util.Base64;

import com.google.gson.Gson;
import com.vv.wanandroid.bean.UserBean;
import com.vv.wanandroid.common.Const;
import com.vv.wanandroid.utils.AesEncryptionUtils;
import com.vv.wanandroid.utils.SPUtils;

import javax.crypto.spec.SecretKeySpec;

/**
 * @author ShenZhenWei
 * @date 2018/7/3
 */
public class UserInfoManager {
    public static UserBean getUserInfo() {
        UserBean userBean = null;
        SecretKeySpec keySpec = getAesKey();
        String userInfo = AesEncryptionUtils.decrypt(keySpec, SPUtils.getInstance().getString(Const.UserInfoKey.USER_INFO, ""));
        if (TextUtils.isEmpty(userInfo)) {
            return null;
        }

        userBean = new Gson().fromJson(userInfo, UserBean.class);
        return userBean;
    }

    public static void saveUserInfo(UserBean userBean) {
        String userInfo = new Gson().toJson(userBean);
        SecretKeySpec key = AesEncryptionUtils.createKey();
        String aesContent = AesEncryptionUtils.encrypt(key, userInfo);
        SPUtils.getInstance().put(Const.UserInfoKey.USER_INFO, aesContent);
        saveAesKey(key);
    }

    private static void saveAesKey(SecretKeySpec key) {
        SPUtils.getInstance().put(Const.UserInfoKey.AES, Base64.encodeToString(key.getEncoded(), Base64.DEFAULT));
    }

    public static SecretKeySpec getAesKey() {
        String keyStr = SPUtils.getInstance().getString(Const.UserInfoKey.AES, "");
        return AesEncryptionUtils.getSecretKey(Base64.decode(keyStr, Base64.DEFAULT));
    }

    public static boolean isLogin() {
        return SPUtils.getInstance().getBoolean(Const.UserInfoKey.IS_LOGIN, false);
    }

    public static void saveIsLogin(boolean isLogin) {
        SPUtils.getInstance().put(Const.UserInfoKey.IS_LOGIN, isLogin);
    }
}
