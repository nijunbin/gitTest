package com.kankan.RegressionTest.junbin;

import android.content.Context;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by nijunbin on 2016/4/21.
 */
public class Login  {
    private Login(){}
    public static boolean login(UiDevice device,String user,String pwd) throws UiObjectNotFoundException {
        ShareMethod.writeRecord("用户登录");
        UiObject userText = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "account"));
        userText.setText(user);
        ShareMethod.sleep(1000);
        UiObject pwdText = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "password"));
        pwdText.click();
        pwdText.setText(pwd);
        ShareMethod.sleep(1000);
        UiObject loginButton = device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "login_submit"));
        loginButton.clickAndWaitForNewWindow();
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).waitForExists(15000)){
            ShareMethod.writeRecord("用户登录 成功");
            return true;
        }else{
            ShareMethod.writeRecord("用户登录 失败");
            return false;
        }
    }

    public static boolean loginByWeibo(UiDevice device,Context context,String user,String pwd)throws UiObjectNotFoundException{
        ShareMethod.writeRecord("微博第三方客户端登录");
        if(!ShareMethod.isPkgInstalled(context,AndroidConfig.weiboPkg)){
            ShareMethod.writeRecord("微博第三方客户端未安装!");
            return false;
        }
        UiObject weibo = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "login_weibo_submit"));
        weibo.clickAndWaitForNewWindow();
        //假如该手机微博客户端已经登录了,点击第三方登录的时候无需输用户名和密码就会自动登录
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).waitForExists(10000)){
            ShareMethod.writeRecord("微博第三方客户端登录 成功");
            return true;
        } else {
            UiObject userText = device.findObject(new UiSelector().resourceId("com.sina.weibo:id/etLoginUsername"));
            userText.click();
            userText.setText(user);
            UiObject pwdText = device.findObject(new UiSelector().resourceId("com.sina.weibo:id/etPwd"));
            pwdText.click();
            pwdText.setText(pwd);
            UiObject submit = device.findObject(new UiSelector().resourceId("com.sina.weibo:id/bnLogin"));
            submit.clickAndWaitForNewWindow();
            if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).waitForExists(15000)){
                ShareMethod.writeRecord("微博第三方客户端登录 成功");
                return true;
            }else{
                ShareMethod.writeRecord("微博第三方客户端登录 失败");
                return false;
            }
        }
    }

    public static boolean loginByQQ(UiDevice device,Context context,String user,String pwd)throws UiObjectNotFoundException{
        ShareMethod.writeRecord("QQ第三方客户端登录");
        if(!ShareMethod.isPkgInstalled(context,AndroidConfig.qqPkg)){
            ShareMethod.writeRecord("QQ第三方客户端未安装!");
            return false;
        }
        UiObject qq = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "login_qq_submit"));
        qq.clickAndWaitForNewWindow();
        //假如该手机QQ客户端已经登录了,点击第三方登录的时候无需输用户名和密码就会自动登录
        if(device.findObject(new UiSelector().resourceId("com.tencent.mobileqq:id/name").text("你已对该应用授权")).waitForExists(15000)){
            device.findObject(new UiSelector().resourceId("com.tencent.mobileqq:id/name").text("登录")).clickAndWaitForNewWindow();
        }
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).waitForExists(15000)){
            return true;
        } else {
            UiObject userText = device.findObject(new UiSelector().resourceId("com.tencent.mobileqq:id/account"));
            userText.click();
            userText.setText(user);
            UiObject pwdText = device.findObject(new UiSelector().resourceId("com.tencent.mobileqq:id/password"));
            pwdText.click();
            pwdText.setText(pwd);
            UiObject submit = device.findObject(new UiSelector().resourceId("com.tencent.mobileqq:id/name").text("登 录"));
            submit.clickAndWaitForNewWindow();
            if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).waitForExists(15000)){
                ShareMethod.writeRecord("QQ第三方客户端登录 成功");
                return true;
            }else{
                ShareMethod.writeRecord("QQ第三方客户端登录 失败");
                return false;
            }
        }
    }


    public static boolean loginFromMyTab(UiDevice device, String user, String pwd) throws UiObjectNotFoundException {
        //切到mytab
        if (!ShareMethod.goMyTab(device)) {
            return false;
        }
        //如果已经有登录了,先退出
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).exists()){
            Logout.logout(device);
        }
        //点击登录按钮
        UiObject login = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "my_status_nologin_img"));
        login.clickAndWaitForNewWindow();
        return login(device,user,pwd);
    }

    public static boolean loginFromMyTabByWeibo(UiDevice device, Context context, String user, String pwd) throws UiObjectNotFoundException {
        //切到mytab
        if (!ShareMethod.goMyTab(device)) {
            return false;
        }
        //如果已经有用户登录了响巢客户端,先退出
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).exists()){
            Logout.logout(device);
        }
        //点击登录按钮
        UiObject login = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "my_status_nologin_img"));
        login.clickAndWaitForNewWindow();
        return loginByWeibo(device,context,user,pwd);
    }


    public static boolean loginFromMyTabByQQ(UiDevice device,Context context,String user,String pwd) throws UiObjectNotFoundException {
        //切到mytab
        if (!ShareMethod.goMyTab(device)) {
            return false;
        }
        //如果已经有登录了,先退出
        if(device.findObject(new UiSelector().resourceId(AndroidDevice.ID +  "my_status_logined_txt_name")).exists()){
            Logout.logout(device);
        }

        UiObject login = device.findObject(new UiSelector().resourceId(AndroidDevice.ID + "my_status_nologin_img"));
        login.clickAndWaitForNewWindow();
        return loginByQQ(device,context,user,pwd);
    }
}
