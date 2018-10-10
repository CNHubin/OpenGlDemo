package com.hubin.opengldemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.hubin.opengldemo.R;
import com.hubin.opengldemo.utils.GlUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @项目名： OpenGlDemo
 * @包名： com.hubin.opengldemo.view
 * @文件名: Gl3dTextureRenderer
 * @创建者: 胡英姿
 * @创建时间: 2018/9/20 14:37
 * @描述： 立方体带纹理添图片
 */
public class Gl3dTextureRenderer implements GLSurfaceView.Renderer {
    private Context mContext;
    //定义旋转角度
    private float anglex = 0f;
    private float angley = 0f;

    public float xScalef = 1f;//缩放大小
    public float yScalef = 1f;//缩放大小
    public float zScalef = 1f;//缩放大小

    /**
     * 视图深度
     */
    private float depth = -2.0f;


    private FloatBuffer cubeVerticesBuffer;
    private ByteBuffer cubeFacetsBuffer;
    private FloatBuffer cubeTexturesBuffer;
    //定义本程序所使用的纹理
    private int texture;
    /**
     * 立方体顶点坐标（36个顶点，组成12个三角形）
     */
    private float[] cubeVertices = {
            -0.6f, -0.6f, -0.6f, -0.6f, 0.6f,
            -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, -0.6f, -0.6f,
            -0.6f, -0.6f, -0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f,
            0.6f, 0.6f, 0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, -0.6f,
            0.6f, -0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, 0.6f,
            0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, -0.6f, 0.6f,
            -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, 0.6f, 0.6f, 0.6f,
            0.6f, 0.6f, -0.6f, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f,
            -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, 0.6f, -0.6f, 0.6f, 0.6f, 0.6f,
            0.6f, 0.6f, 0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, -0.6f,
            -0.6f, -0.6f, -0.6f, 0.6f, -0.6f, -0.6f, 0.6f, -0.6f, 0.6f, 0.6f,
            -0.6f, 0.6f, -0.6f,
    };
    /**
     * 立方体所需要的六个面（一共是12个三角形所需的顶点）
     */
    private byte[] cubeFacets = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
            13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33,
            34, 35,
    };
    /**
     * 定义纹理贴图的坐标数据
     */
    private float[] cubeTextures = {1.0000f, 1.0000f, 1.0000f, 0.0000f,
            0.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f, 1.0000f,
            1.0000f, 0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f,
            1.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f, 0.0000f,
            1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f, 1.0000f, 0.0000f,
            0.0000f, 0.0000f, 0.0000f, 1.0000f, 0.0000f, 1.0000f, 1.0000f,
            1.0000f, 1.0000f, 0.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f,
            0.0000f, 1.0000f, 0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f,
            0.0000f, 1.0000f, 0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f,
            0.0000f, 1.0000f, 1.0000f, 1.0000f, 1.0000f, 0.0000f, 1.0000f,
            0.0000f, 0.0000f, 0.0000f, 0.0000f, 1.0000f,
    };

    public Gl3dTextureRenderer(Context context) {
        mContext = context;
        cubeVerticesBuffer = GlUtils.floatBuffer(cubeVertices);
        cubeFacetsBuffer = ByteBuffer.wrap(cubeFacets);
        cubeTexturesBuffer = GlUtils.floatBuffer(cubeTextures);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        //设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        //设置清屏时所用的颜色，4个参数分别设置红。绿。蓝。透明度的值，0为最小值1为最大值
        gl.glClearColor(0, 0, 0, 0);//黑色
        //设置阴影模式：阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        //启用深度测试：与glDisable（）对应，跟踪Z轴深度，避免后面的物体遮挡前面的物体
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);


        //启用2D纹理贴图
        gl.glEnable(GL10.GL_TEXTURE_2D);
        //加载纹理
        loadTexture(gl);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        //将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视视窗的宽度、高度比
        float ratio = (float) width / height;
        //调用此方法设置透视视窗的空间大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启用添图坐标数组数据
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //设置当前矩阵模式为模型视图
        gl.glMatrixMode(GL10.GL_MODELVIEW);

        ///////////////绘制
        gl.glLoadIdentity();
        //将绘图中心移入屏幕2个单位
        gl.glTranslatef(0f, 0.0f, depth);
        //旋转图形
        gl.glRotatef(anglex, 1, 0, 0);
        gl.glRotatef(angley, 0, 1, 0);

        //缩小放大
        gl.glScalef(xScalef, yScalef, zScalef);

        //设置顶点位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, cubeVerticesBuffer);
        //设置贴图的坐标数据
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, cubeTexturesBuffer);
        //执行纹理贴图
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
        //按照cubeFacetsBuffer指定的面绘制三角形
        gl.glDrawElements(GL10.GL_TRIANGLES, cubeFacetsBuffer.remaining(), GL10.GL_UNSIGNED_BYTE,
                cubeFacetsBuffer);

        //绘制结束
        gl.glFinish();

        //禁用顶点、纹理坐标数组
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);


    }

    /**
     * 加载纹理
     *
     * @param gl
     */
    private void loadTexture(GL10 gl) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.wenli4);
            int[] textures = new int[1];
            //指定生成n个纹理（第一个参数指定生成一个纹理）
            //textures数组将负责存储所有纹理代号
            gl.glGenTextures(1, textures, 0);
            //获取texttures纹理数组中的第一个纹理
            texture = textures[0];
            //通知OpenGl将texture纹理绑定大到GL10.GL_TEXTURE_2D目标中
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
            //设置纹理被缩小（距离视点很远时被缩小）时的滤波方式
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
            //设置纹理被放大（距离视点很近时被放大）时的滤波方式
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
            //设置在横向、纵向上都是平铺纹理
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
            //加载位图生成纹理
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }


    /**
     * 设置水平旋转的角度  即沿着Y轴旋转 的增量
     *
     * @param dx x方向的增量
     */
    public void setHorizontalAngley(float dx) {
        angley += dx;
    }

    /**
     * 设置垂直旋转的角度  即沿着X轴旋转 的增量
     *
     * @param dy Y方向的增量
     */
    public void setVerticalAnglex(float dy) {
        anglex += dy;
    }

    /**
     * 设置缩放
     *
     * @param scalef
     */
    public void setScale(float scalef) {
        xScalef += scalef;
        yScalef += scalef;
        zScalef += scalef;
    }

    /**
     * 设置视图深度
     *
     * @param depth
     */
    public void setDepth(float depth) {
        this.depth += depth;
    }
}

