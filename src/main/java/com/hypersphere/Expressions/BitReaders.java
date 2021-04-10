package com.hypersphere.Expressions;

public class BitReaders {

    public static BitReader<Integer> INT = (b) -> {
        byte[] bytes = convertToBytes(b);
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8 ) |
                ((bytes[3] & 0xFF));
    };
    public static BitReader<Long> LONG = (bytes) -> {
        byte[] b = convertToBytes(bytes);
        return ((long) b[7] << 56)
                | ((long) b[6] & 0xff) << 48
                | ((long) b[5] & 0xff) << 40
                | ((long) b[4] & 0xff) << 32
                | ((long) b[3] & 0xff) << 24
                | ((long) b[2] & 0xff) << 16
                | ((long) b[1] & 0xff) << 8
                | ((long) b[0] & 0xff);
    };
    public static BitReader<Byte> BYTE = (b) -> convertToBytes(b)[0];
    public static BitReader<Integer> SHORT = (b) -> {
        byte[] bytes = convertToBytes(b);
        return ((bytes[0] & 0xFF) << 8 ) |
                ((bytes[1] & 0xFF));
    };
    public static BitReader<boolean[]> PASS = (b) -> b;
    public static byte[] convertToBytes(boolean[] b){
        byte[] bytes = new byte[b.length / 8];
        for(int i = 0; i < b.length; i += 8){
            bytes[i / 8] = 0;
            for(int i2 = 0; i2 < 8; ++i2){
                if(b[i2]) bytes[i/8] |= 1 << i2;
            }
        }
        return bytes;
    }
}
