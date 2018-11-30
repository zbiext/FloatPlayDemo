package com.github.zbiext.floatplaydemo.test;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;

import com.github.zbiext.floatplaydemo.GLSurfaceVideoView;
import com.github.zbiext.floatplaydemo.R;
import com.github.zbiext.floatplaydemo.test.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by user on 2018/11/30.
 * 项目名           FloatPlayDemo
 * 包名             com.github.zbiext.floatplaydemo.test
 * 创建时间         2018/11/30 15:44
 * 创建者           zbie
 * 邮箱             hyxt2011@163.com
 * Github:         https://github.com/zbiext
 * 简书:           http://www.jianshu.com/
 * QQ&WX：         1677208059
 * 描述            TODO
 */
public class GLSurfaceViewActivity extends BaseActivity {

    @BindView(R.id.play_video)
    GLSurfaceVideoView mVideoView1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics         dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = mVideoView1.getLayoutParams();
        lp.width = dm.widthPixels;
        lp.height = (int) ((float) lp.width / 16 * 9);
        mVideoView1.setLayoutParams(lp);
        prepareInput();
    }

    private void prepareInput() {
        mVideoView1.getPlayer().setLooping(true);
        mVideoView1.mute();

        AssetFileDescriptor afd1 = getResources().openRawResourceFd(R.raw.big_buck_bunny_720p_20mb);
        mVideoView1.setDataSource(afd1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView1!= null) {
            mVideoView1.release();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gl_surface_view;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView1.pause();
    }

    @Override
    protected void play() {
        mVideoView1.start();
        findViewById(R.id.play).setEnabled(false);
    }
}
