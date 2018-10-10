package com.hubin.opengldemo.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @项目名： OpenGlDemo
 * @包名： com.hubin.opengldemo.utils
 * @文件名: GlUtils
 * @创建者: 胡英姿
 * @创建时间: 2018/9/10 20:11
 * @描述： TODO
 */
public class GlUtils {

    /**
     * 将float[]数组转换为OpenGL ES所需的FloatBuffer
     * @param arr
     */
    public static FloatBuffer floatBuffer(float[] arr) {
        FloatBuffer mBuffer;
        //初始化ByteBuffer,长度为arr数组的长度*4，因为一个int占4个字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
        //数组排列用nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asFloatBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;
    }

    /**
     * 将int[] 数组转换为OpenGL ES所需的IntBuffer
     * @param arr
     * @return
     */
    public static IntBuffer intBuffer(int[] arr) {
        IntBuffer mBuffer;
        //由于Android平台的严格限制，要求Buffer必须是native Buffer，
        // 并且该Buffer必须是排序的
        //初始化ByteBuffer,长度为arr数组的长度*4，因为一个int占4字节
        ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length*4);
        //数组排列nativeOrder
        qbb.order(ByteOrder.nativeOrder());
        mBuffer = qbb.asIntBuffer();
        mBuffer.put(arr);
        mBuffer.position(0);
        return mBuffer;

    }

}
