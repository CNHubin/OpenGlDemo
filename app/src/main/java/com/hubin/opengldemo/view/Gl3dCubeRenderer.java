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
 * @文件名: Gl3dCubeRenderer
 * @创建者: 胡英姿
 * @创建时间: 2018/9/20 10:47
 * @描述： TODO
 */
public class Gl3dCubeRenderer implements GLSurfaceView.Renderer {

    /**
     * 三棱椎的4个顶点
     */
    float[] taperVertices = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, -0.2f,
            0.5f, -0.5f, -0.2f,
            0.0f, -0.2f, 0.2f,
    };

    /**
     * 三棱椎的4个顶点颜色
     */
    int[] taperColors = new int[]{
            65535, 0, 0, 0, //红色
            0, 65535, 0, 0, //绿色
            0, 0, 65535, 0, //蓝色
            65535, 65535, 0, 0, //黄色
    };

    /**
     * 三棱椎的4个三角面
     */
    byte[] taperFacests = new byte[]{
            0, 1, 2,
            0, 1, 3,
            1, 2, 3,
            0, 2, 3
    };

    /**
     * 立方体的8个顶点
     */
    float[] cubeVertices = new float[]{
            //上顶面正方形的4个顶点
            0.5f, 0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            -0.5f, 0.5f, 0.5f,
            //下底面正方形的4个顶点
            0.5f, 0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, 0.5f, -0.5f,
    };

    /**
     * 立方体所需的6个面
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

    private float rotate;
    private FloatBuffer taperVerticesBuffer;
    private ByteBuffer taperFacestsBuffer;
    private IntBuffer taperColorsBuffer;
    private FloatBuffer cubeVerticesBuffer;
    private ByteBuffer cubeFacetsBuffer;

    public Gl3dCubeRenderer() {
         taperVerticesBuffer = GlUtils.floatBuffer(taperVertices);
         taperFacestsBuffer = ByteBuffer.wrap(taperFacests);
         taperColorsBuffer = GlUtils.intBuffer(taperColors);
         cubeVerticesBuffer = GlUtils.floatBuffer(cubeVertices);
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
        //启用深度测试：与glDisable（）对应，跟踪Z轴深度，避免后面的物体遮挡前面的物体
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置3D视窗的大小及位置
        gl.glViewport(0,0,width,height);
        //将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视视窗的宽度、高度比
        float ratio = (float)width/height;
        //调用此方法设置透视视窗的空间大小
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
        //沿Y轴旋转
        gl.glRotatef(rotate,0f,0.2f,0f);
        //设置顶点的位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,taperVerticesBuffer);
        //颜色
        gl.glColorPointer(4,GL10.GL_FIXED,0,taperColorsBuffer);
        //绘制
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,taperFacestsBuffer.remaining(),GL10.GL_UNSIGNED_BYTE,taperFacestsBuffer);

        //--------------------------绘制第二个图形--------------------------
        gl.glLoadIdentity();
        gl.glTranslatef(0.7f,0.0f,-2.2f);
        //沿Y轴旋转
        gl.glRotatef(rotate,0f,0.2f,0f);
        //沿Y轴旋转
        gl.glRotatef(rotate,1.0f,0f,0f);
        //设置顶点的位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,cubeVerticesBuffer);
         //绘制
        gl.glDrawElements(GL10.GL_TRIANGLE_STRIP,cubeFacetsBuffer.remaining(),GL10.GL_UNSIGNED_BYTE,cubeFacetsBuffer);

        //绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //旋转角度增加
        rotate +=1;

    }
}
