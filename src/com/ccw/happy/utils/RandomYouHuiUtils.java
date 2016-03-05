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
 * @����: �´���
 * @ʱ��: 2015-11-21����10:47:41
 * @auther: �Ż�ȯ�Ĺ����� ��Ҫ���������д�����Ż�ȯ����Ϣ �� ���û��������һ����¼
 */
public class RandomYouHuiUtils {
	private static int randomNum = 0;
	private static AlertDialog dialog;

	public static void getYouHui(final Context context) {
		final UserBean user = HappyApplication.getmHappyApplication().getUb();
		if (isRandomNull()) {
			// ����һ��dialog�Ի���
			Builder builder = new Builder(context);
			builder.setTitle("��;İ·�Ż�ȯ");
			builder.setCancelable(false);
			builder.setMessage("�������˵�������ס��,��ϲ���" + randomNum + "Ԫ�Ż�ȯ");
			builder.setPositiveButton("����", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// ����һ���Ż�ȯ����
					YouHuiBean yhb = new YouHuiBean();
					// ���Ż�ȯ��������
					yhb.setYouXiaoQi("2016-12-31");
					yhb.setMoney(randomNum + "");
					yhb.setManMoney(randomNum * 10 + "");
					yhb.setUserId(user.getObjectId());
					// �ϴ�������
					yhb.save(context, new SaveListener() {
						@Override
						public void onSuccess() {
							Toast.makeText(context, "�Ż�ȯ����������.��ȥ�鿴��.",
									Toast.LENGTH_SHORT).show();
							// ���Ż�ȯ���浽�û��ı���
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
							Toast.makeText(context, "������˼,����̫����,����������.",
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			});
			builder.setNegativeButton("��м", new OnClickListener() {

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
	 * @return true Ϊ�ر�dialog false Ϊ���ر�
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
	 * @return true Ϊ��ȡ�����Ż�ȯ false Ϊδ��ȡ
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
