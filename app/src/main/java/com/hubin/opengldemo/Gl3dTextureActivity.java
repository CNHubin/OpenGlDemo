package com.hubin.opengldemo;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.hubin.Utils;
import com.hubin.opengldemo.view.Gl3dTextureRenderer;
import com.hubin.util.LogUtils;

/**
 * 3D纹理添图的 立方体
 */
public class Gl3dTextureActivity extends AppCompatActivity {

    static final float ROTATE_FACTOR = 60;
    //定义手势检测器
    GestureDetector mDetector;
    private Gl3dTextureRenderer mRenderer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        GLSurfaceView mGLSurfaceView = new GLSurfaceView(this);
        mRenderer = new Gl3dTextureRenderer(this);
        mGLSurfaceView.setRenderer(mRenderer);
        setContentView(mGLSurfaceView);
        Utils.init(this, BuildConfig.DEBUG);
        //创建手势检测器
        mDetector = new GestureDetector(this, mOnGestureListener);
    }

    /**
     * 放慢旋转速度的参数
     */
    private final float TOUCH_SCALE_FACTOR = 180.0f / (320 * 5);
    private float mPreviousX;
    private float mPreviousY;

    private double nLenStart = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int nCnt = e.getPointerCount();
        if (nCnt == 1) {
            //单点触控  触发旋转
            float x = e.getX();
            float y = e.getY();
            switch (e.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;
                    //计算沿Y轴旋转的角度 即水平旋转的角度
                    mRenderer.setHorizontalAngley(dx * TOUCH_SCALE_FACTOR);
                    //计算沿X轴旋转的角度 即竖直旋转的角度
                    mRenderer.setVerticalAnglex(dy * TOUCH_SCALE_FACTOR);
            }
            mPreviousX = x;
            mPreviousY = y;
            return true;
        }

        //每次放大的基数
        float scale = 0.1f;
        //多点触控 按下
        if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && nCnt == 2) {
            //
            int xlen = Math.abs((int) e.getX(0) - (int) e.getX(1));
            int ylen = Math.abs((int) e.getY(0) - (int) e.getY(1));
            nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
        } else if ((e.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && nCnt
                == 2) {
            //多点触控放开
            int xlen = Math.abs((int) e.getX(0) - (int) e.getX(1));
            int ylen = Math.abs((int) e.getY(0) - (int) e.getY(1));
            double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
            //通过两个手指开始距离和结束距离，来判断放大缩小
            if (nLenEnd > nLenStart) {
                mRenderer.setScale(scale);//放大
//                mRenderer.setDepth(scale);
            } else {
                mRenderer.setScale(-scale);//缩小
//                mRenderer.setDepth(-scale);
            }
        }


        return true;
        //将该activity上的触摸事件交给GestureDetector处理
//        return mDetector.onTouchEvent(e);
    }

    /**
     * 手势检测器
     */
    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector
            .OnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            LogUtils.d("onFling  D 发生拖动: X方向速度=" + velocityX + " Y方向速度=" + velocityY);

            velocityX = velocityX > 2000 ? 2000 : velocityX;
            velocityX = velocityX < -2000 ? -2000 : velocityX;
            velocityY = velocityY > 2000 ? 2000 : velocityY;
            velocityY = velocityY < -2000 ? -2000 : velocityY;
            //根据横向上的速度计算沿Y轴旋转的角度
            mRenderer.setHorizontalAngley(velocityX * ROTATE_FACTOR / 4000);
            //根据纵向上的速度计算沿X轴旋转的角度
            mRenderer.setVerticalAnglex(velocityY * ROTATE_FACTOR / 4000);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            LogUtils.d("onDown  D : 按下");
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            LogUtils.d("onShowPress  D : 手指已按下并且还未移动和松开");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            LogUtils.d("onSingleTapUp  D : 单击");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            LogUtils.i("onScroll  D : distanceX="+distanceX+" distanceY="+distanceY);
            return false;
        }

        /**
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            LogUtils.d("onLongPress  D : 长按");
        }

    };

}
