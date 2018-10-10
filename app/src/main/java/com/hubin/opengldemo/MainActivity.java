package com.hubin.opengldemo;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hubin.opengldemo.view.Gl3dRenderer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
//        Gl2dRenderer mGl2dRenderer = new Gl2dRenderer();
        Gl3dRenderer mGl3dRenderer = new Gl3dRenderer();
        mGLSurfaceView.setRenderer(mGl3dRenderer);

        setContentView(R.layout.activity_main);
    }

    public void gl2d(View view) {
        Intent mIntent = new Intent(this,Gl2dActivity.class);
        startActivity(mIntent);
    }

    public void gl3d(View view) {
        Intent mIntent = new Intent(this,Gl3dActivity.class);
        startActivity(mIntent);
    }


    /**
     * 立方体
     * @param view
     */
    public void gl3dCube(View view) {
        Intent mIntent = new Intent(this,Gl3dCubeActivity.class);
        startActivity(mIntent);
    }
    /**
     * 皮卡丘
     * @param view
     */
    public void gl3dPKQ(View view) {
        Intent mIntent = new Intent(this,Gl3dPkqActivity.class);
        startActivity(mIntent);
    }

    /**
     * 立方体加纹理
     * @param view
     */
    public void gl3dCubeTexture(View view) {
        Intent mIntent = new Intent(this,Gl3dTextureActivity.class);
        startActivity(mIntent);
    }
}
