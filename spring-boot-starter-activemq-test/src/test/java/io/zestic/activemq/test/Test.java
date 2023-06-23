package io.zestic.activemq.test;

public class Test {

    public static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    public static void main(String[] args) {
        for (int index = 0; index <= 100; index++) {
            System.out.println("Output " + index % 6);
        }
    }
}
