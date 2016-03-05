package com.ccw.happy.utils;

import java.util.Random;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.ccw.happy.application.HappyApplication;
import com.ccw.happy.vo.UserBean;
import com.ccw.happy.vo.YouHuiBean;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-21上午10:47:41
 * @auther: 优惠券的工具类 主要有向服务器写入了优惠券的信息 和 向用户中添加了一条记录
 */
public class RandomYouHuiUtils {
	private static int randomNum = 0;
	private static AlertDialog dialog;

	public static void getYouHui(final Context context) {
		final UserBean user = HappyApplication.getmHappyApplication().getUb();
		if (isRandomNull()) {
			// 创建一个dialog对话框
			Builder builder = new Builder(context);
			builder.setTitle("穷途陌路优惠券");
			builder.setCancelable(false);
			builder.setMessage("运气来了挡都挡不住了,恭喜获得" + randomNum + "元优惠券");
			builder.setPositiveButton("收下", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// 创建一个优惠券对象
					YouHuiBean yhb = new YouHuiBean();
					// 给优惠券设置数据
					yhb.setYouXiaoQi("2016-12-31");
					yhb.setMoney(randomNum + "");
					yhb.setManMoney(randomNum * 10 + "");
					yhb.setUserId(user.getObjectId());
					// 上传服务器
					yhb.save(context, new SaveListener() {
						@Override
						public void onSuccess() {
							Toast.makeText(context, "优惠券已纳入囊中.快去查看吧.",
									Toast.LENGTH_SHORT).show();
							// 把优惠券保存到用户的表中
							UserBean ub = new UserBean();
							ub.increment("youhui");
							ub.update(context, user.getObjectId(),
									new UpdateListener() {

										@Override
										public void onSuccess() {

										}

										@Override
										public void onFailure(int arg0,
												String arg1) {

										}
									});

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Toast.makeText(context, "不好意思,你手太慢了,都被抢完了.",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
			builder.setNegativeButton("不屑", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			dialog = builder.create();
			dialog.show();

		}

	}

	/**
	 * 
	 * @return true 为关闭dialog false 为不关闭
	 */
	public static boolean isDialogIsShow() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return true 为获取到了优惠券 false 为未获取
	 */
	private static boolean isRandomNull() {
		Random random = new Random();
		randomNum = random.nextInt(20);
		if (randomNum == 15) {
			while ((randomNum = random.nextInt(20)) < 2) {

			} 
			return true;
		}
		return false;
	}
}
