package com.shtoone.shtw.ui.LoadingDrawable;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public abstract class LoadingRenderer {  //渲染器
    private static final long ANIMATION_DURATION = 1333;
    private static final float DEFAULT_SIZE = 56.0f;

    private final ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener//监听动画的属性变化，变化之后执行回调
            = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            computeRender((float) animation.getAnimatedValue());
            invalidateSelf();
        }
    };

    /**
     * Whenever {@link LoadingDrawable} boundary changes mBounds will be updated.
     * More details you can see {@link LoadingDrawable#onBoundsChange(Rect)}
     */
    protected final Rect mBounds = new Rect();  //矩形

    private Drawable.Callback mCallback;
    private ValueAnimator mRenderAnimator;

    protected long mDuration;

    protected float mWidth;
    protected float mHeight;

    public LoadingRenderer(Context context) {
        initParams(context);
        setupAnimators();
    }

    @Deprecated//用 @Deprecated注解的程序元素，不鼓励程序员使用这样的元素，通常是因为它很危险或存在更好的选择。在使用不被赞成的程序元素或在不被赞成的代码中执行重写时，编译器会发出警告。
    protected void draw(Canvas canvas, Rect bounds) {
    }

    protected void draw(Canvas canvas) {
        draw(canvas, mBounds);
    }

    protected abstract void computeRender(float renderProgress);

    protected abstract void setAlpha(int alpha);

    protected abstract void setColorFilter(ColorFilter cf);

    protected abstract void reset();

    protected void addRenderListener(Animator.AnimatorListener animatorListener) {
        mRenderAnimator.addListener(animatorListener);
    }

    void start() {
        reset();
        mRenderAnimator.addUpdateListener(mAnimatorUpdateListener);

        mRenderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mRenderAnimator.setDuration(mDuration);
        mRenderAnimator.start();
    }

    void stop() {
        // if I just call mRenderAnimator.end(),
        // it will always call the method onAnimationUpdate(ValueAnimator animation)
        // why ? if you know why please send email to me (dinus_developer@163.com)
        mRenderAnimator.removeUpdateListener(mAnimatorUpdateListener);

        mRenderAnimator.setRepeatCount(0);
        mRenderAnimator.setDuration(0);
        mRenderAnimator.end();
    }

    boolean isRunning() {
        return mRenderAnimator.isRunning();
    }

    void setCallback(Drawable.Callback callback) {
        this.mCallback = callback;
    }

    void setBounds(Rect bounds) {
        mBounds.set(bounds);
    }

    private void initParams(Context context) {
        mWidth = DensityUtil.dip2px(context, DEFAULT_SIZE);//dp————px格式
        mHeight = DensityUtil.dip2px(context, DEFAULT_SIZE);

        mDuration = ANIMATION_DURATION;
    }

    private void setupAnimators() {
        mRenderAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);//返回一个在此区间的valueanimatior
        mRenderAnimator.setRepeatCount(Animation.INFINITE);//设置重复的数量  实际数量=重复数量+1
        mRenderAnimator.setRepeatMode(Animation.RESTART);//设置动画重复模式
        mRenderAnimator.setDuration(mDuration);//设置动画执行时间
        //fuck you! the default interpolator is AccelerateDecelerateInterpolator
        mRenderAnimator.setInterpolator(new LinearInterpolator());//设置线性插补器，设置执行速率，默认为先加速后减速，这样设置后是匀速执行
        mRenderAnimator.addUpdateListener(mAnimatorUpdateListener);//设置属性变化监听
    }

    private void invalidateSelf() {
        mCallback.invalidateDrawable(null);
    }

}
