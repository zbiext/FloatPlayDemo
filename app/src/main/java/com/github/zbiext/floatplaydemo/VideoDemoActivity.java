package com.github.zbiext.floatplaydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.zbiext.floatplaydemo.test.DragTextureViewActivity;
import com.github.zbiext.floatplaydemo.test.GLSurfaceViewActivity;
import com.github.zbiext.floatplaydemo.test.SurfaceViewActivity;
import com.github.zbiext.floatplaydemo.test.TextureViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VideoDemoActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_demo);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @OnClick({
            R.id.test_surface_view,
            R.id.test_texture_view,
            R.id.test_glsurface_view,
            R.id.drag_texture_view,
            R.id.test_tv1,
            R.id.test_tv2
    })
    public void onClick(View v) {
        switch (v.getId())   {
            case R.id.test_surface_view:
                startActivity(new Intent(this, SurfaceViewActivity.class));
                break;
            case R.id.test_texture_view:
                startActivity(new Intent(this, TextureViewActivity.class));
                break;
            case R.id.test_glsurface_view:
                startActivity(new Intent(this, GLSurfaceViewActivity.class));
                break;
            case R.id.drag_texture_view:
                startActivity(new Intent(this, DragTextureViewActivity.class));
                break;
            case R.id.test_tv1:
                Toast.makeText(this, "保留1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.test_tv2:
                Toast.makeText(this, "保留2", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
