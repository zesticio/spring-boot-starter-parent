package io.zestic.common;

public class TestPassByReference {

  public static void main(String[] args) {
    Test test = new Test();
    test.setValue("Hello World");
    TestPassByReference.test(test);
    System.out.println(test.getValue());
  }

  public static void test(Test test) {
    test.setValue("Hi How are you?");
  }
}
