package io.zestic.activemq.test;

public class TestTernaryOperator {

    private static Integer throughput;
    private static final Integer THROUGHPUT = 128;

    public static void main(String[] args) {
        System.out.println((throughput != null && throughput >= 1) ? throughput : THROUGHPUT);
        throughput = 256;
        System.out.println((throughput != null && throughput >= 1) ? throughput : THROUGHPUT);
    }
}
