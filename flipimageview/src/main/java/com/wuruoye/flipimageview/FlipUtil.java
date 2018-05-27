package com.wuruoye.flipimageview;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * @Created : wuruoye
 * @Date : 2018/5/26 21:39.
 * @Description : 工具类
 */
public class FlipUtil {
    public static void tintDrawable(Drawable drawable, int color) {
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }
}
