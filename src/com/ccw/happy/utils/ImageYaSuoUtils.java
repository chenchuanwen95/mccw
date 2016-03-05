package com.ccw.happy.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;

/**
 * 
 * @作者: 陈传稳
 * @时间: 2015-11-11下午8:44:59
 * @auther: 压缩图片的工具类 压缩进内存中 Bitmap 中有个方法是专门用来压缩图片的 compress里面有三个参数 1、是压缩后图片的格式
 *          2、图片的品质 3、内存流
 */
public class ImageYaSuoUtils {
	public static Bitmap getBitmap50k(Bitmap bitmap, int i) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(CompressFormat.JPEG, quality, out);
		// 如果你的图片过大 ，有可能压缩后得不到50k的图片 所以要考虑循环压缩
		while (out.toByteArray().length > (i * 1024)) {
			// 当内存中的空间大于图片的大小时，就停止压缩
			// 每一次压缩都要把空间给重置，以免造成内存溢出等等错误，也防止图片越来越大
			out.reset();
			quality = quality * 99 / 100;
			bitmap.compress(CompressFormat.JPEG, quality, out);
			// 就算图片压缩到10之后 还没有达到50k，就停止压缩并退出循环
			if (quality < 10) {
				break;
			}
		}
		// 当循环结束后，也就是压缩之后 ，在从新获取一个新的bitmap,并返回
		bitmap = BitmapFactory.decodeByteArray(out.toByteArray(), 0,
				out.toByteArray().length);
		return bitmap;
	}
}
