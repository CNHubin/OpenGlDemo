package com.hubin.opengldemo.view;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.hubin.opengldemo.obj.Obj3D;
import com.hubin.opengldemo.obj.ObjFilter2;
import com.hubin.opengldemo.obj.ObjReader;
import com.hubin.opengldemo.utils.Gl2Utils;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @项目名： OpenGlDemo
 * @包名： com.hubin.opengldemo.view
 * @文件名: Gl3dPkqRenderer
 * @创建者: 胡英姿
 * @创建时间: 2018/9/15 18:07
 * @描述： 皮卡丘
 */
public class Gl3dPkqRenderer implements GLSurfaceView.Renderer{

    private List<ObjFilter2> filters;
    public Gl3dPkqRenderer(Context context,String fileDir,String fileName) {
        List<Obj3D> model= ObjReader.readMultiObj(context,"assets/"+fileDir+fileName);
        filters=new ArrayList<>();
        for (int i=0;i<model.size();i++){
            ObjFilter2 f=new ObjFilter2(context.getResources(),fileDir);
            f.setObj3D(model.get(i));
            filters.add(f);
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        for (ObjFilter2 f:filters){
            f.create();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        for (ObjFilter2 f:filters){
            f.onSizeChanged(width, height);
            float[] matrix= Gl2Utils.getOriginalMatrix();
            Matrix.translateM(matrix,0,0,-0.3f,0);
            Matrix.scaleM(matrix,0,0.008f,0.008f*width/height,0.008f);
            f.setMatrix(matrix);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        for (ObjFilter2 f:filters){
            Matrix.rotateM(f.getMatrix(),0,0.3f,0,1,0);
            f.draw();
        }
    }
}
