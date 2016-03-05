package com.ccw.happy.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

/**
 * 
 * @����: �´���
 * @ʱ��: 2015-11-11����8:44:59
 * @auther: ѹ��ͼƬ�Ĺ����� ѹ�����ڴ��� Bitmap ���и�������ר������ѹ��ͼƬ�� compress�������������� 1����ѹ����ͼƬ�ĸ�ʽ
 *          2��ͼƬ��Ʒ�� 3���ڴ���
 */
public class ImageYaSuoUtils {
	public static Bitmap getBitmap50k(Bitmap bitmap, int i) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(CompressFormat.JPEG, quality, out);
		// ������ͼƬ���� ���п���ѹ����ò���50k��ͼƬ ����Ҫ����ѭ��ѹ��
		while (out.toByteArray().length > (i * 1024)) {
			// ���ڴ��еĿռ����ͼƬ�Ĵ�Сʱ����ֹͣѹ��
			// ÿһ��ѹ����Ҫ�ѿռ�����ã���������ڴ�����ȵȴ���Ҳ��ֹͼƬԽ��Խ��
			out.reset();
			quality = quality * 99 / 100;
			bitmap.compress(CompressFormat.JPEG, quality, out);
			// ����ͼƬѹ����10֮�� ��û�дﵽ50k����ֹͣѹ�����˳�ѭ��
			if (quality < 10) {
				break;
			}
		}
		// ��ѭ��������Ҳ����ѹ��֮�� ���ڴ��»�ȡһ���µ�bitmap,������
		bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0,
				out.toByteArray().length);
		return bitmap;
	}
}
