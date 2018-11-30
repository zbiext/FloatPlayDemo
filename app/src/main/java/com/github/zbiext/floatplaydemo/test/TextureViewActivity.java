package com.github.zbiext.floatplaydemo.test;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;

import com.github.zbiext.floatplaydemo.R;
import com.github.zbiext.floatplaydemo.RenderThread;
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
public class TextureViewActivity extends BaseActivity implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, SurfaceTexture.OnFrameAvailableListener {

    @BindView(R.id.play_video)
    TextureView mTextureView;

    private MediaPlayer    mMediaPlayer;
    private RenderThread   mRenderThread;
    private SurfaceTexture mSurfaceTexture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics         dm = getResources().getDisplayMetrics();
        ViewGroup.LayoutParams lp = mTextureView.getLayoutParams();
        lp.width = dm.widthPixels;
        lp.height = (int) ((float) lp.width / 16 * 9);
        mTextureView.setLayoutParams(lp);

        mTextureView.setSurfaceTextureListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_texture_view;
    }

    @Override
    protected void play() {
        mMediaPlayer.prepareAsync();
        findViewById(R.id.play).setEnabled(false);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        initVideo();

        mRenderThread = new RenderThread(surfaceTexture, this);
        mRenderThread.setRegion(width, height);
        mRenderThread.start();
    }

    private void initVideo() {
        mMediaPlayer = new MediaPlayer();

        mMediaPlayer.setOnPreparedListener(this);

        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.big_buck_bunny_720p_20mb);
        if (afd == null) return;

        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        mRenderThread.setRegion(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        synchronized (mRenderThread) {
            mRenderThread.release();
            mRenderThread.notify();

            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }

        mMediaPlayer.release();
        mMediaPlayer = null;

        findViewById(R.id.play).setEnabled(true);
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    public void starVideo(int texture) {
        if (null == mSurfaceTexture) {
            mSurfaceTexture = new SurfaceTexture(texture);
            mSurfaceTexture.setOnFrameAvailableListener(this);

            try {
                mMediaPlayer.setSurface(new Surface(mSurfaceTexture));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        synchronized (mRenderThread) {
            mRenderThread.notify();
        }
    }

    public void updateFrame() {
        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();
        }
    }
}
