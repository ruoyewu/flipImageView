package com.wuruoye.flipimageview;

import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * @Created : wuruoye
 * @Date : 2018/5/26 21:39.
 * @Description : 工具类
 */
public class FlipUtil {
    public static void tintDrawable(Drawable drawable, int color) {
        DrawableCompat.setTint(drawable, color);
    }
}
