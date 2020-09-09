package com.takaaki.urcap.eipmonitor.impl.rtde;

import java.nio.ByteBuffer;
import java.util.Map.Entry;

import com.takaaki.urcap.eipmonitor.impl.converter.TypeConverter;

public class RTDEBuffer {


    public static Object decode(Entry<String, String> entry, ByteBuffer buf) {

        String type = entry.getValue();

        if (type.equals("NOT_FOUND"))
            return null;

        if (type.equals("IN_USE"))
            return null;

        if (type.equals("BOOL"))
            return buf.get();

        if (type.equals("UINT8"))
            return buf.get();

        if (type.equals("UINT32"))
            return buf.getInt();

        if (type.equals("UINT64"))
            return buf.getLong();

        if (type.equals("INT32"))
            return buf.getInt();

        if (type.equals("DOUBLE"))
            return buf.getDouble();

        if (type.equals("VECTOR3D")) {
            double[] d = new double[3];
            for (int i = 0; i < 3; i++) {
                d[i] = buf.getDouble();
            }
            return d;
        }

        if (type.equals("VECTOR6D")) {
            double[] d = new double[6];
            for (int i = 0; i < 6; i++) {
                d[i] = buf.getDouble();
            }
            return d;
        }

        if (type.equals("VECTOR6INT32")) {
            int[] d = new int[6];
            for (int i = 0; i < 6; i++) {
                d[i] = buf.getInt();
            }
            return d;
        }

        if (type.equals("VECTOR6UINT32")) {
            int[] d = new int[6];
            for (int i = 0; i < 6; i++) {
                d[i] = buf.getInt();
            }
            return d;
        }

        return null;
    }

    public static byte[] encode(Entry<String, String> entry, Object value) {

        String type = entry.getValue();

        if (type.equals("NOT_FOUND"))
            return null;

        if (type.equals("IN_USE"))
            return null;

        if (type.equals("BOOL")) {
            byte[] bytes = new byte[1];
            bytes[0] = (Byte) value;
            return bytes;
        }

        if (type.equals("UINT8")) {
            byte[] bytes = new byte[1];
            bytes[0] = (Byte) value;
            return bytes;

        }
        if (type.equals("UINT32"))
            return TypeConverter.intToBytes((Integer) value);

        if (type.equals("UINT64"))
            return TypeConverter.longToBytes((Long) value);

        if (type.equals("INT32"))
            return TypeConverter.intToBytes((Integer) value);

        if (type.equals("DOUBLE"))
            return TypeConverter.doubleToBytes((Double) value);

        if (type.equals("VECTOR3D")) {
            byte[] bytes = new byte[3 * Double.BYTES];
            double[] doubles = (double[]) value;

            for (int i = 0; i < 3; i++) {
                System.arraycopy(TypeConverter.doubleToBytes(doubles[i]), 0, bytes, Double.BYTES * i, Double.BYTES);
            }

            return bytes;

        }

        if (type.equals("VECTOR6D")) {
            byte[] bytes = new byte[6 * Double.BYTES];
            double[] doubles = (double[]) value;

            for (int i = 0; i < 6; i++) {
                System.arraycopy(TypeConverter.doubleToBytes(doubles[i]), 0, bytes, Double.BYTES * i, Double.BYTES);
            }

            return bytes;

        }

        if (type.equals("VECTOR6INT32")) {
            byte[] bytes = new byte[6 * Integer.BYTES];
            int[] ints = (int[]) value;

            for (int i = 0; i < 6; i++) {
                System.arraycopy(TypeConverter.intToBytes(ints[i]), 0, bytes, Integer.BYTES * i, Integer.BYTES);
            }

            return bytes;

        }

        if (type.equals("VECTOR6UINT32")) {
            byte[] bytes = new byte[6 * Integer.BYTES];
            int[] ints = (int[]) value;

            for (int i = 0; i < 6; i++) {
                System.arraycopy(TypeConverter.intToBytes(ints[i]), 0, bytes, Integer.BYTES * i, Integer.BYTES);
            }

            return bytes;

        }

        return null;
    }
}