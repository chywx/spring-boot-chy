package cn.chendahai.chy.demo.test;

import java.nio.charset.StandardCharsets;

public class OctetStringExample {

    public static void main(String[] args) {
        String phoneNumberStr = "+255652924502";

        // Convert the phone number string to a byte array using UTF-8 encoding
        byte[] phoneNumberBytes = phoneNumberStr.getBytes(StandardCharsets.UTF_8);

        // Create an Octet String object from the byte array
        OctetString octetString = new OctetString(phoneNumberBytes);

        // Print the Octet String
        System.out.println("Octet String: " + octetString.toString());

        System.out.println(0x01);
        System.out.println(0x02);
        System.out.println(0x11);
        System.out.println(0x03);
        System.out.println(0011);

        int binaryNum = 0b10110; // 二进制数 22
        int octalNum = 027; // 八进制数 23
        int hexNum = 0x17; // 十六进制数 23

        System.out.println(binaryNum); // 输出: 22
        System.out.println(octalNum); // 输出: 23
        System.out.println(hexNum); // 输出: 23

        System.out.println(">>>>>>>>>>>>>");
        System.out.println(0x03);
        System.out.println((int) 0x03);
        System.out.println((byte) (int) 0x03);
        System.out.println((byte) 0x03);
        System.out.println((byte) 0x13);
        System.out.println((byte) (int) 0x13);


        System.out.println(">>>");
        System.out.println(Byte.parseByte("1"));
        System.out.println("1".getBytes());
    }
}

class OctetString {

    private byte[] bytes;

    public OctetString(byte[] byteArray) {
        this.bytes = byteArray;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}