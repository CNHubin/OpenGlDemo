package com.hubin.opengldemo.view;

import android.opengl.GLSurfaceView;

import com.hubin.opengldemo.utils.GlUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @项目名： OpenGlDemo
 * @包名： com.hubin.opengldemo.view
 * @文件名: Gl2dRenderer
 * @创建者: 胡英姿
 * @创建时间: 2018/9/10 19:52
 * @描述： TODO
 */
public class Gl2dRenderer implements GLSurfaceView.Renderer {
    /**
     * 三角形数据
     */
    float[] triangleData = new float[]{
            0.1f, 0.6f, 0.0f, //上顶点
            -0.3f, 0.0f, 0.0f,//左顶点
            0.3f, 0.1f, 0.0f  //右顶点
    };
    /**
     * 三角形颜色
     */
    int[] triangleColor = new int[]{
            65535,0,0,0, //上顶点红色
            0,65535,0,0,//左顶点绿色
            0,0,65535,0//右顶点蓝色
    };
    /**
     * 矩形顶点数据
     */
    float[] rectData =new float[]{
            0.4f,0.4f,0.0f, //右上顶点
            0.4f,-0.4f,0.0f, //右下顶点
            -0.4f,0.4f,0.0f,//左上顶点
            -0.4f,-0.4f,0.0f,//左下顶点
    };

    /**
     * 矩形颜色
     */
    int[] rectColor = new int[]{
            0,65535,0,0, //右上顶点绿色
            0,0,65535,0, //右下顶点蓝色
            65535,0,0,0, //左上顶点红色
            65535,65535,0,0 //左下顶点黄色
    };

    float[] rect2Data =new float[]{
            -0.4f,0.4f,0.0f, //右上顶点
            0.4f,0.4f,0.0f, //右下顶点
            0.4f,-0.4f,0.0f,//左上顶点
            -0.4f,-0.4f,0.0f,//左下顶点
    };

    /**
     * 五角星数据
     */
    float[] pentacle =new float[]{
            0.4f,0.4f,0.0f, //右上顶点
            -0.2f,0.3f,0.0f, //右下顶点
            0.5f,0.0f,0f,//左上顶点
            -0.4f,0.0f,0f,//左下顶点
            -0.1f,-0.3f,0f,//左下顶点
    };

    FloatBuffer triangleDataBuffer;
    FloatBuffer rectDataBuffer;
    FloatBuffer rect2DataBuffer;
    FloatBuffer pentacleBuffer;
    IntBuffer triangleColorBuffer;
    IntBuffer rectColorBuffer;

    /**
     * 控制旋转角度
     */
    private float rotate;

    public Gl2dRenderer() {
        //将顶点数据转换成FloatBuffer
        triangleDataBuffer = GlUtils.floatBuffer(triangleData);
        rectDataBuffer = GlUtils.floatBuffer(rectData);
        rect2DataBuffer = GlUtils.floatBuffer(rect2Data);
        pentacleBuffer = GlUtils.floatBuffer(pentacle);
        //将顶点颜色数据数组转换成IntBuffer
        triangleColorBuffer = GlUtils.intBuffer(triangleColor);
        rectColorBuffer = GlUtils.intBuffer(rectColor);
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
        gl.glShadeModel(GL10.GL_DEPTH_TEST);
        //设置深度测试类型：与glDisable（）对应，跟踪Z轴深度，避免后面的物体遮挡前面的物体
        gl.glEnable(GL10.GL_LEQUAL);
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
        gl.glTranslatef(-0.32f,0.35f,-1.2f);
        //设置顶点的位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,triangleDataBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4,GL10.GL_FIXED,0,triangleColorBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLES,0,3);//------ 核心代码


        //--------------------------绘制第二个图形--------------------------
        gl.glLoadIdentity();
        gl.glTranslatef(0.6f,0.8f,-1.5f);
        //旋转
        gl.glRotatef(rotate,0f,0f,0.1f);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,rectDataBuffer);
        gl.glColorPointer(4,GL10.GL_FIXED,0,rectColorBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);//------ 核心代码


        //--------------------------绘制第三个图形--------------------------
        gl.glLoadIdentity();
        gl.glTranslatef(-0.4f,-0.5f,-1.5f);
        //旋转
        gl.glRotatef(rotate,0f,0.2f,0f);
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,rect2DataBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);//------ 核心代码

        //--------------------------绘制第四个图形 五角星--------------------------
        gl.glLoadIdentity();
        gl.glTranslatef(0.4f,-0.5f,-1.5f);
        //设置使用纯色填充 使用纯色填充时需禁用顶点颜色数组glDisableClientState
        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //设置顶点位置数据
        gl.glVertexPointer(3,GL10.GL_FLOAT,0,pentacleBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,5);

        //绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //旋转角度增加
        rotate+=1;
    }


}
