package com.github.zbiext.floatplaydemo.test.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.github.zbiext.floatplaydemo.R;
import com.github.zbiext.floatplaydemo.drag.DragVideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by user on 2018/11/30.
 * 项目名           FloatPlayDemo
 * 包名             com.github.zbiext.floatplaydemo.test.base
 * 创建时间         2018/11/30 15:08
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            Activity 基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @BindView(R.id.play_video)
    View          mPlayView;
    @BindView(R.id.drag_view)
    DragVideoView mDragVideoView;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);

        mDragVideoView.setCallback(new DragVideoView.Callback() {
            @Override
            public void onDisappear(int direct) {
                BaseActivity.this.onDisappear();
            }
        });
    }

    protected void onDisappear() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected @LayoutRes
    int getLayoutId() {
        return R.layout.layout_base;
    }

    @OnClick({
            R.id.play,
            R.id.anim,
            R.id.anim_t,
            R.id.anim_s,
            R.id.anim_a,
            R.id.anim_r
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                mDragVideoView.show();
                play();
                break;
            case R.id.anim:
                break;
            case R.id.anim_t:
                ObjectAnimator animator1    = ObjectAnimator.ofFloat(mPlayView, "translationY", 0, 400);
                AnimatorSet    animatorSet1 = new AnimatorSet();
                animatorSet1.play(animator1);
                animatorSet1.setDuration(1000);
                animatorSet1.setInterpolator(new BounceInterpolator());
                animatorSet1.start();
                break;
            case R.id.anim_s:
                ObjectAnimator animator2    = ObjectAnimator.ofFloat(mPlayView, "scaleX", 1f, 0.5f);
                AnimatorSet    animatorSet2 = new AnimatorSet();
                animatorSet2.play(animator2);
                animatorSet2.setDuration(1000);
                animatorSet2.setInterpolator(new BounceInterpolator());
                animatorSet2.start();
                break;
            case R.id.anim_a:
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(mPlayView, "alpha", 0.1f, 1f);
                AnimatorSet animatorSet3 = new AnimatorSet();
                animatorSet3.play(animator3);
                animatorSet3.setDuration(1000);
                animatorSet3.setInterpolator(new BounceInterpolator());
                animatorSet3.start();
                break;
            case R.id.anim_r:
                ObjectAnimator animator4 = ObjectAnimator.ofFloat(mPlayView, "rotation", 0, 180, 0);
                AnimatorSet animatorSet4 = new AnimatorSet();
                animatorSet4.play(animator4);
                animatorSet4.setDuration(1000);
                animatorSet4.setInterpolator(new BounceInterpolator());
                animatorSet4.start();
                break;
        }
    }

    protected void play() {
    }

    public void starVideo(int texture) {
    }

    public void updateFrame() {
    }
}
