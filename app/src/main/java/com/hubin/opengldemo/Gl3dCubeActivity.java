package com.hubin.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hubin.opengldemo.view.Gl3dCubeRenderer;

public class Gl3dCubeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
        Gl3dCubeRenderer mGl3dCubeRenderer = new Gl3dCubeRenderer();
        mGLSurfaceView.setRenderer(mGl3dCubeRenderer);
        setContentView(mGLSurfaceView);
    }
}
