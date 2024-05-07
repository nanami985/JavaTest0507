package jp.co.aforce.practice;

public class ExceptionExample {
    public static void main(String[] args) {
        try {
            testMethod();
        } catch (AforceException2 e) {
            e.printStackTrace();
        }
    }

    public static void testMethod() throws AforceException2 {
        try {
            throw new AforceException1("AforceException1エラーが発生しました");
        } catch (AforceException1 e) {
            throw new AforceException2("AforceException2エラーが発生しました", e);
        }
    }
}