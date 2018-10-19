package com.xtp.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.xtp.library.R;


/**
 * 支持常见的圆形，圆角矩形及其他图形
 * <p>
 * 支持边框
 * <p>
 * 支持按下态
 * <p>
 */

public class MultiShapeView extends AppCompatImageView {

    private Context mContext;

    /**
     * 显示图片
     */
    private Bitmap mBitmap;

    /**
     * Bitmap Paint
     */
    private Paint mBitmapPaint;


    /**
     * 边框 Paint
     */
    private Paint mBorderPaint;


    /**
     * 当前是否被按下
     */
    private boolean mIsPressed;


    private Shader mBitmapShader;


    /**
     * 变换矩阵
     */
    private Matrix mShaderMatrix;


    /**
     * 形状
     */
    private int mShape;


    /**
     * Rect Bitmap
     */
    private RectF mRcBitmap;


    /**
     * Rect Border
     */
    private RectF mRcBorder;

    /**
     * 圆角半径
     */
    private float mRoundRadius;

    /**
     * 边框半径
     */
    private float mBorderRadius;


    /**
     * 图片半径
     */
    private float mCircleRadius;


    /**
     * 边框color
     */
    private int mBorderColor;


    /**
     * 边框大小
     */
    private int mBorderWidth;


    /**
     * 按下态蒙层color
     */
    private int mCoverColor;


    /**
     * 默认BorderWidth
     */
    private static final int DEFAULT_BORDER_WIDTH = 0;

    /**
     * 默认BorderColor
     */
    private static final int DEFAULT_BORDER_COLOR = Color.TRANSPARENT;

    /**
     * 默认按下态蒙层Color
     */
    private static final int DEFAULT_COVER_COLOR = Color.parseColor("#40333333");

    /**
     * 默认圆角半径
     */
    private static final int DEFAULT_ROUND_RADIUS = 0;

    /**
     * 默认形状
     */
    public static final int SHAPE_REC = 1; // 矩形
    public static final int SHAPE_CIRCLE = 2; // 圆形


    public MultiShapeView(Context context) {
        this(context, null);
    }

    public MultiShapeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiShapeView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attr, R.styleable.MultiShapeView, defStyleAttr, 0);

        mContext = context;
        mBorderColor = typedArray.getColor(R.styleable.MultiShapeView_border_color, DEFAULT_BORDER_COLOR);
        mCoverColor = typedArray.getColor(R.styleable.MultiShapeView_cover_color, DEFAULT_COVER_COLOR);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.MultiShapeView_border_width, DEFAULT_BORDER_WIDTH);
        mShape = typedArray.getInteger(R.styleable.MultiShapeView_shape, SHAPE_REC);
        mRoundRadius = typedArray.getDimensionPixelSize(R.styleable.MultiShapeView_round_radius, DEFAULT_ROUND_RADIUS);
        typedArray.recycle();

        init();

    }

    private void init() {
        mBitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderWidth);

        mRcBitmap = new RectF();
        mRcBorder = new RectF();
        mShaderMatrix = new Matrix();
    }


    public void setImageBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        preDraw();
    }


    public void setImageDrawable(Drawable drawable) {
        mBitmap = getBitmapFromDrawable(drawable);
        preDraw();
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            try {
                mBitmap = getBitmapFromDrawable(mContext.getResources().getDrawable(resId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        preDraw();
    }


    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    /**
     * 初始化基本参数
     */
    private void preDraw() {

        if (mBitmap == null) {
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapPaint.setShader(mBitmapShader);

        mRcBorder.set(0, 0, getWidth(), getHeight());
        mBorderRadius = Math.min((mRcBorder.height() - mBorderWidth) / 2, (mRcBorder.width() - mBorderWidth) / 2);

        //！！！注意，圆角矩形边框注意位置，否则边框和矩形之间会露出空白
        if (mShape == SHAPE_CIRCLE) {
            mRcBitmap.set(mBorderWidth, mBorderWidth, mRcBorder.width() - mBorderWidth, mRcBorder.height() - mBorderWidth);
        } else if (mShape == SHAPE_REC) {
            mRcBitmap.set(mBorderWidth / 2f, mBorderWidth / 2f, mRcBorder.width() - mBorderWidth / 2f, mRcBorder.height() - mBorderWidth / 2f);
        }
        mCircleRadius = Math.min(mRcBitmap.height() / 2, mRcBitmap.width() / 2);
        updateShaderMatrix();
        invalidate();
    }


    /**
     * 伸缩变换
     */
    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmap.getWidth() * mRcBitmap.height() > mRcBitmap.width() * mBitmap.getHeight()) {
            scale = mRcBitmap.height() / (float) mBitmap.getHeight();
            dx = (mRcBitmap.width() - mBitmap.getWidth() * scale) * 0.5f;
        } else {
            scale = mRcBitmap.width() / (float) mBitmap.getWidth();
            dy = (mRcBitmap.height() - mBitmap.getHeight() * scale) * 0.5f;
        }

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth, (int) (dy + 0.5f) + mBorderWidth);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        preDraw();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        preDraw();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            if (mShape == SHAPE_CIRCLE) {
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, mCircleRadius, mBitmapPaint);
                canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius, mBorderPaint);
            } else if (mShape == SHAPE_REC) {
                canvas.drawRoundRect(mRcBitmap, mRoundRadius, mRoundRadius, mBitmapPaint);
                canvas.drawRoundRect(mRcBorder, mRoundRadius, mRoundRadius, mBorderPaint);
            }
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (mIsPressed == pressed) {
            return;
        }
        mIsPressed = pressed;
        if (mIsPressed) {
            mBitmapPaint.setColorFilter(new PorterDuffColorFilter(mCoverColor, PorterDuff.Mode.SRC_ATOP));
        } else {
            mBitmapPaint.setColorFilter(null);
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            //高度根据使得图片的宽度充满屏幕计算而得
            int height = (int) Math.ceil((float) width * (float) d.getIntrinsicHeight() / (float) d.getIntrinsicWidth());
            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    //-----------------------------------------------getter/setter------------------------------

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        if (borderColor == mBorderColor) {
            return;
        }
        mBorderColor = borderColor;
        mBorderPaint.setColor(mBorderColor);
        invalidate();
    }

    public int getBorderWidth() {
        return mBorderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        if (borderWidth == mBorderWidth) {
            return;
        }
        mBorderWidth = borderWidth;
        mBorderPaint.setStrokeWidth(mBorderWidth);
        preDraw();
    }

    public int getCoverColor() {
        return mCoverColor;
    }

    public void setCoverColor(int coverColor) {
        if (coverColor == mCoverColor) {
            return;
        }
        mCoverColor = coverColor;
    }

    public int getShape() {
        return mShape;
    }

    public void setShape(int shape) {
        mShape = shape;
        preDraw();
    }

    public float getRoundRadius() {
        return mRoundRadius;
    }

    public void setRoundRadius(float roundRadius) {
        mRoundRadius = roundRadius;
        preDraw();
    }
}