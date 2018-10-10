package com.hubin.opengldemo.view;

import android.opengl.GLSurfaceView;

import com.hubin.opengldemo.utils.GlUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @项目名： OpenGlDemo
 * @包名： com.hubin.opengldemo.view
 * @文件名: Gl3dRenderer
 * @创建者: 胡英姿
 * @创建时间: 2018/9/11 20:17
 * @描述： TODO
 */
public class Gl3dRenderer implements GLSurfaceView.Renderer {

    /**
     * 三棱椎的四个顶点
     */
    float[] taperVertices = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, -0.2f,
            0.5f, -0.5f, -0.2f,
            0.0f, -0.2f, 0.2f,
    };

    /**
     * 三棱锥的四个顶点颜色
     */
    int[] taperColors = new int[]{
            65535, 0, 0, 0,        //红色
            0, 65535, 0, 0,        //绿色
            0, 0, 65535, 0,        //蓝色
            65535, 65535, 0, 0    //黄色
    };
    /**
     * 三棱锥的4个三角面
     */
    byte[] taperFacets = new byte[]{
            0, 1, 2,  //0.1.2三个顶点组成一个面
            0, 1, 3,  //0.1.3三个顶点组成一个面
            1, 2, 3,  //1.2.3三个顶点组成一个面
            0, 2, 3   //0.2.3三个顶点组成一个面
    };

    /**
     * 立方体的8个顶点
     */
    float[] cubeVertuces = new float[]{
            // 上顶面正方形的4个顶点
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            // 下底面正方形的4个顶点
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
    };

    /**
     * 立方体所需的6个面（一共是12个三角形所需要的顶点）
     */
    byte[] cubeFacets = new byte[]{
            0, 1, 2,
            0, 2, 3,
            2, 3, 7,
            2, 6, 7,
            0, 3, 7,
            0, 4, 7,
            4, 5, 6,
            4, 6, 7,
            0, 1, 4,
            1, 4, 5,
            1, 2, 6,
            1, 5, 6
    };

    FloatBuffer taperVerticesBuffer;
    ByteBuffer taperFacetsBuffer;
    IntBuffer taperColorsBuffer;
    FloatBuffer cubeVertucesBuffer;
    ByteBuffer cubeFacetsBuffer;
    /**
     * 控制旋转角度
     */
    private float rotate;


    public Gl3dRenderer() {
         taperVerticesBuffer = GlUtils.floatBuffer(taperVertices);
         taperFacetsBuffer = ByteBuffer.wrap(taperFacets);
         taperColorsBuffer = GlUtils.intBuffer(taperColors);
         cubeVertucesBuffer = GlUtils.floatBuffer(cubeVertuces);
         cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        //设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
        //设置清屏时所用的颜色，4个参数分别设置红。绿。蓝。透明度的值，0为最小值1为最大值
        gl.glClearColor(0,0,0,0);//黑色
        //设置阴影模式：阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        //设置深度测试类型：与glDisable（）对应，跟踪Z轴深度，避免后面的物体遮挡前面的物体
        gl.glEnable(GL10.GL_LEQUAL);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置3D视窗的大小及位置
        gl.glViewport(0,0,width,height);
        //将当前矩阵模式设置为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视视窗的宽度、高度比
        float ratio = (float)width/height;
        //设置透视视窗的空间大小
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        //启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        //--------------------------绘制第一个图形--------------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        //该方法类似Matrix的setTranslate方法，它们都用于移动绘图中心，区别
        //在绘制图形之前调用即可保证把图形绘制在指定的中心点
        gl.glTranslatef(-0.6f,0.0f,-1.5f);
        //沿着Y轴旋转
        gl.glRotatef(rotate,0f,0.2f,0f);
        //设置顶点的位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,taperVerticesBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4,GL10.GL_FIXED,0,taperColorsBuffer);
        //按taperFacetsBuffer指定的面绘制三角形
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,taperFacetsBuffer.remaining(),GL10.GL_UNSIGNED_BYTE,taperFacetsBuffer);

        //--------------------------绘制第二个图形--------------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        //该方法类似Matrix的setTranslate方法，它们都用于移动绘图中心，区别
        //在绘制图形之前调用即可保证把图形绘制在指定的中心点
        gl.glTranslatef(0.7f,0.0f,-2.2f);
        //沿着Y轴旋转
        gl.glRotatef(rotate,0f,0.2f,0f);
        //沿着X轴旋转
        gl.glRotatef(rotate,1f,0f,0f);
        //设置顶点的位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,cubeVertucesBuffer);
        //不设置顶点的颜色数据还用以前的颜色数据
        //按cubeVertucesBuffer指定的面绘制三角形
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,taperFacetsBuffer.remaining(),GL10.GL_UNSIGNED_BYTE,cubeFacetsBuffer);

        //绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //旋转角度增加1
        rotate+=1;

    }
}
