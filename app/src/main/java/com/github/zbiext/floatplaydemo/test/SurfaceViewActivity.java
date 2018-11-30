package com.github.zbiext.floatplaydemo.test;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.github.zbiext.floatplaydemo.R;
import com.github.zbiext.floatplaydemo.test.base.BaseActivity;

import java.io.IOException;

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
public class SurfaceViewActivity extends BaseActivity implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {

    @BindView(R.id.play_video)
    SurfaceView mPlayView1;

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics         dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = mPlayView1.getLayoutParams();
        lp.width = dm.widthPixels;
        lp.height = (int) ((float) lp.width / 16 * 9);
        mPlayView1.setLayoutParams(lp);
        mPlayView1.getHolder().addCallback(this);

        prepareInput();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
        }
    }

    private void prepareInput() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0, 0);
        mMediaPlayer.setOnPreparedListener(this);

        AssetFileDescriptor afd1 = getResources().openRawResourceFd(R.raw.big_buck_bunny_720p_20mb);

        try {
            mMediaPlayer.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_surface_view;
    }

    @Override
    protected void play() {
        mMediaPlayer.prepareAsync();
        findViewById(R.id.play).setEnabled(false);
    }


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mMediaPlayer.setDisplay(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
