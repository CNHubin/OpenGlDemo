package com.hubin.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hubin.opengldemo.view.Gl2dRenderer;

public class Gl2dActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
        Gl2dRenderer mGl2dRenderer = new Gl2dRenderer();
        mGLSurfaceView.setRenderer(mGl2dRenderer);
        setContentView(mGLSurfaceView);
    }
}
