package com.wuruoye.flipimageview;

import android.content.Context;
import android.util.TypedValue;

/**
 * @Created : wuruoye
 * @Date : 2018/5/26 21:39.
 * @Description :
 */
public class FlipUtil {
    public static int getPrimaryColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static int getAccentColor(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }
}
