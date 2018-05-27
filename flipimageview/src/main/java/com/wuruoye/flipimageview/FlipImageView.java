package com.wuruoye.flipimageview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;

/**
 * @Created : wuruoye
 * @Date : 2018/5/26 21:28.
 * @Description : 可翻转 ImageView
 */
public class FlipImageView extends ImageView
        implements ValueAnimator.AnimatorUpdateListener {
    public static final String TAG = "FlipImageView";
    public static final int MAX_ALPHA = 255;
    public static final int DEFAULT_DURATION = 300;
    public static final float DEFAULT_SCALE = 0.6F;
    public static final int DEFAULT_DRAWABLE = R.drawable.ic_check;
    public static final int DEFAULT_BG_COLOR = Color.BLACK;
    public static final int DEFAULT_CHECK_COLOR = Color.WHITE;
    public static final Interpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    public static final float START = 0F;
    public static final float MIDDLE_1 = 0.5F;
    public static final float MIDDLE_2 = 1F;
    public static final float END = 1.5F;
    public static final int LR = 1;
    public static final int UD = 2;

    private int colorBg;
    private int colorCheck;
    private int resourceCheck;
    private float mScale;
    private long mDuration;
    private int mDirection;

    private Paint mBgPaint;
    private Drawable mOriginDrawable;
    private Drawable mCheckDrawable;
    private Rect mAllRect;
    private Rect mCheckRect;
    private RectF mAllRectF;

    private ValueAnimator mAnimator;
    private float mProgress;
    private float mLastProgress = -1;
    private OnFlipListener mOnFlipListener;

    public FlipImageView(Context context) {
        super(context);
        loadDefaultAttr(context);
        init();
    }

    public FlipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttr(context, attrs);
        init();
    }

    public FlipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttr(context, attrs);
        init();
    }

    private void loadAttr(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FlipImageView);

        colorBg = ta.getColor(R.styleable.FlipImageView_flipBgColor, DEFAULT_BG_COLOR);
        colorCheck = ta.getColor(R.styleable.FlipImageView_flipCheckColor, DEFAULT_CHECK_COLOR);
        resourceCheck = ta.getResourceId(R.styleable.FlipImageView_flipCheckDrawable,
                DEFAULT_DRAWABLE);
        mScale = ta.getFloat(R.styleable.FlipImageView_flipScale, DEFAULT_SCALE);
        mDuration = ta.getInteger(R.styleable.FlipImageView_flipDuration, DEFAULT_DURATION);
        mDirection = ta.getInt(R.styleable.FlipImageView_flipDirection, LR);

        ta.recycle();
    }

    private void loadDefaultAttr(Context context) {
        colorBg = DEFAULT_BG_COLOR;
        colorCheck = DEFAULT_CHECK_COLOR;
        resourceCheck = DEFAULT_DRAWABLE;
        mScale = DEFAULT_SCALE;
        mDuration = DEFAULT_DURATION;
        mDirection = LR;
    }

    private void init() {
        mAnimator = new ValueAnimator();
        mAnimator.setDuration(mDuration);
        mAnimator.setInterpolator(DEFAULT_INTERPOLATOR);
        mAnimator.addUpdateListener(this);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mOnFlipListener != null) {
                    mOnFlipListener.onFlipStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mOnFlipListener != null) {
                    mOnFlipListener.onFlipEnd();
                }
            }
        });

        mAllRect = new Rect();
        mCheckRect = new Rect();
        mAllRectF = new RectF();

        mBgPaint = new Paint();
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.FILL);
        mBgPaint.setColor(colorBg);

        setCheckDrawable(getResources().getDrawable(resourceCheck));
        initDrawable();
    }

    public void flip(boolean start) {
        if (start) {
            mAnimator.setFloatValues(START, END);
        }else {
            mAnimator.setFloatValues(END, START);
        }
        mAnimator.start();
    }

    public void setScale(float scale) {
        mScale = scale;
    }

    public void setDuration(long duration) {
        mDuration = duration;
        mAnimator.setDuration(duration);
    }

    public void setBgColor(int color) {
        colorBg = color;
        mBgPaint.setColor(color);
    }

    public void setCheckColor(int color) {
        colorCheck = color;
        FlipUtil.tintDrawable(mCheckDrawable, color);
    }

    public void setCheckDrawable(Drawable drawable) {
        mCheckDrawable = drawable;
        if (mCheckDrawable != null) {
            FlipUtil.tintDrawable(mCheckDrawable, colorCheck);
        }
    }

    public void setCheckDrawable(int resourceId) {
        setCheckDrawable(getResources().getDrawable(resourceId));
    }

    public void setCheckDrawable(Bitmap bitmap) {
        setCheckDrawable(new BitmapDrawable(getResources(), bitmap));
    }

    public void setOnFlipListener(OnFlipListener listener) {
        mOnFlipListener = listener;
    }

    private void initDrawable() {
        mOriginDrawable = getDrawable();
        if (mOriginDrawable != null) {
            setImageDrawable(null);
        }
    }

    private void setRect() {
        if (mOriginDrawable != null) {
            mOriginDrawable.setBounds(mAllRect);
        }
    }

    private float firstProgress(float progress) {
        return 1 - progress * 2;
    }

    private float secondProgress(float progress) {
        return progress * 2 - 1;
    }

    private float thirdProgress(float progress) {
        return progress * 2 - 2;
    }

    private void scaleCanvas(Canvas canvas, float progress) {
        int center = canvas.getWidth() / 2;
        if (mDirection == LR) {
            canvas.scale(progress, 1, center, center);
        }else {
            canvas.scale(1, progress, center, center);
        }
    }

    private int getAlpha(float progress) {
        if (progress > 1) {
            progress = 1;
        }else if (progress < 0) {
            progress = 0;
        }

        return (int) (progress * MAX_ALPHA);
    }

    private void beforeProgress(float lastProgress, float progress) {
        if (mOnFlipListener != null) {
//            Log.e(TAG, "beforeProgress: " + lastProgress + " " + progress );
            if (lastProgress < MIDDLE_1 && progress >= MIDDLE_1) {
                mOnFlipListener.onSecondStart();
            }else if (lastProgress < MIDDLE_2 && progress >= MIDDLE_2) {
                mOnFlipListener.onThirdStart();
            }else if (lastProgress > MIDDLE_2 && progress <= MIDDLE_2) {
                mOnFlipListener.onSecondStart();
            }else if (lastProgress > MIDDLE_1 && progress <= MIDDLE_1) {
                mOnFlipListener.onThirdStart();
            }
            mLastProgress = progress;
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        mProgress = (float) valueAnimator.getAnimatedValue();
//        Log.e(TAG, "onAnimationUpdate: " + mProgress);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float progress = mProgress;

        beforeProgress(mLastProgress, progress);
        canvas.save();
        int center = canvas.getWidth() / 2;

        if (progress < 0.5) {
            // TODO: 2018/5/26 如果在 xml 文件中 imageSrc 和 flipCheckDrawable 使用同一个 resource，
            // 会导致两者共享透明度，需要在此手动重新定义透明度
            mOriginDrawable.setAlpha(MAX_ALPHA);

            progress = firstProgress(progress);
            scaleCanvas(canvas, progress);
            if (mOriginDrawable != null) {
                mOriginDrawable.draw(canvas);
            }
        }else if (progress < 1) {
            progress = secondProgress(progress);
            scaleCanvas(canvas, progress);
            canvas.drawOval(mAllRectF, mBgPaint);
        }else {
            progress = thirdProgress(progress);
            canvas.drawOval(mAllRectF, mBgPaint);
            if (mCheckDrawable != null) {
                mCheckDrawable.setAlpha(getAlpha(progress));
                int w = (int) (center * mScale * progress);
                mCheckRect.set(center - w, center - w, center + w, center + w);
                mCheckDrawable.setBounds(mCheckRect);
                mCheckDrawable.draw(canvas);
            }
        }

        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int wM = MeasureSpec.getMode(widthMeasureSpec);
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int hM = MeasureSpec.getMode(heightMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);

        if (w > h) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(h, wM);
        }else if (h > w) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(w, hM);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int l = w / 4;
        mAllRectF.set(0, 0, w, w);
        mAllRect.set(0, 0, w, w);
        mCheckRect.set(l, l, w - l, w - l);
        setRect();
    }
}
