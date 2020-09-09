package com.takaaki.urcap.eipmonitor.impl.converter;

import java.nio.ByteBuffer;

/**
 * Byte型⇔他変換用ライブラリ
 * 
 */
public class TypeConverter {

    public TypeConverter() {

    }

    /**
     * double型をByte配列に変換
     * 
     * @param value 変換元double型
     * @return Byte配列
     */
    public static byte[] doubleToBytes(double value) {

        ByteBuffer buf = ByteBuffer.allocate(Double.SIZE / 8);
        return buf.putDouble(value).array();

    }

    /**
     * short型をByte配列に変換
     * 
     * @param value 変換元short型
     * @return Byte配列
     */
    public static byte[] shortToBytes(short value) {

        ByteBuffer buf = ByteBuffer.allocate(Short.SIZE / 8);
        return buf.putShort(value).array();

    }

    /**
     * int型をByte配列に変換
     * 
     * @param value 変換元int型
     * @return Byte配列
     */
    public static byte[] intToBytes(int value) {

        ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE / 8);
        return buf.putInt(value).array();

    }

    /**
     * long型をByte配列に変換
     * 
     * @param value 変換元long型
     * @return Byte配列
     */
    public static byte[] longToBytes(long value) {

        ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / 8);
        return buf.putLong(value).array();

    }

    /**
     * Byte配列をdouble配列に変換
     * 
     * @param value 変換元Byte配列
     * @return double配列
     */
    public static double[] bytesToDoubles(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        double[] values = new double[bytes.length / (Double.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getDouble();
        }
        return values;
    }

    /**
     * Byte配列をint配列に変換
     * 
     * @param value 変換元Byte配列
     * @return int配列
     */
    public static int[] bytesToInts(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        int[] values = new int[bytes.length / (Integer.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getInt();
        }
        return values;
    }

    /**
     * Byte配列をshort配列に変換
     * 
     * @param value 変換元Byte配列
     * @return short配列
     */
    public static short[] bytesToShorts(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        short[] values = new short[bytes.length / (Short.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getShort();
        }
        return values;
    }

    /**
     * int型をbit配列に変換
     * 
     * @param value 変換元int型
     * @return bit配列
     */
    public static boolean[] intToBits(int value) {
        boolean[] result = new boolean[Integer.SIZE];

        for (int i = 0; i < Integer.SIZE; i++) {
            result[i] = ((value & 1) == 1) ? true : false;
            value = value >> 1;
        }

        return result;
    }
}