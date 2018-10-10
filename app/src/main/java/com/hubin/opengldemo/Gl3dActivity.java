package com.hubin.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hubin.opengldemo.view.Gl3dRenderer;

public class Gl3dActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
        Gl3dRenderer mGl3dRenderer = new Gl3dRenderer();
        mGLSurfaceView.setRenderer(mGl3dRenderer);
        setContentView(mGLSurfaceView);
    }
}
