package com.takaaki.urcap.eipmonitor.impl.converter;

import java.nio.ByteBuffer;

public class TypeConverter {

    public TypeConverter() {

    }

    public static byte[] doubleToBytes(double value) {

        ByteBuffer buf = ByteBuffer.allocate(Double.SIZE / 8);
        return buf.putDouble(value).array();

    }

    public static byte[] shortToBytes(short value) {

        ByteBuffer buf = ByteBuffer.allocate(Short.SIZE / 8);
        return buf.putShort(value).array();

    }

    public static byte[] intToBytes(int value) {

        ByteBuffer buf = ByteBuffer.allocate(Integer.SIZE / 8);
        return buf.putInt(value).array();

    }

    public static byte[] longToBytes(long value) {

        ByteBuffer buf = ByteBuffer.allocate(Long.SIZE / 8);
        return buf.putLong(value).array();

    }

    public static double[] bytesToDoubles(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        double[] values = new double[bytes.length / (Double.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getDouble();
        }
        return values;
    }

    public static int[] bytesToInts(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        int[] values = new int[bytes.length / (Integer.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getInt();
        }
        return values;
    }

    public static short[] bytesToShorts(byte[] bytes) {

        ByteBuffer buf = ByteBuffer.wrap(bytes);
        short[] values = new short[bytes.length / (Short.SIZE / 8)];

        for (int i = 0; i < values.length; i++) {
            values[i] = buf.getShort();
        }
        return values;
    }

    public static boolean[] intToBits(int value) {
        boolean[] result = new boolean[Integer.SIZE];

        for (int i = 0; i < Integer.SIZE; i++) {
            result[i] = ((value & 1) == 1) ? true : false;
            value = value >> 1;
        }

        return result;
    }
}