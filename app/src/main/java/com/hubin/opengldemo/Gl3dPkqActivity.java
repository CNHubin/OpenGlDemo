package com.hubin.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hubin.opengldemo.obj.ObjFilter2;
import com.hubin.opengldemo.view.Gl3dPkqRenderer;
import com.hubin.util.LogUtils;

import java.util.List;

public class Gl3dPkqActivity extends AppCompatActivity {
    private List<ObjFilter2> filters;
    private GLSurfaceView mGLSurfaceView;

        public static final String obj_dir = "3dres/pikachu/";
        public static final String obj_file = "pikachu.obj";
    /*public static final String obj_dir = "3dres/girl/";
    public static final String obj_file = "girl.obj";
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl3d_pkg);
        mGLSurfaceView = findViewById(R.id.mGLView);
        mGLSurfaceView.setEGLContextClientVersion(2);

        LogUtils.timeStart("创建");
        Gl3dPkqRenderer renderer = new Gl3dPkqRenderer(Gl3dPkqActivity.this, obj_dir,obj_file);
        LogUtils.timeEnd("创建");
        mGLSurfaceView.setRenderer(renderer);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    }



    @Override
    protected void onResume() {
        super.onResume();
            mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView.onPause();
    }


}
