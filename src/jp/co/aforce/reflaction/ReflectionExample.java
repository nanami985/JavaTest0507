package jp.co.aforce.reflaction;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionExample {
    public static void main(String[] args) {
        // クラス名、フィールド名、メソッド名を取得
        getClassInfo(TestEntity.class);

        // フィールドの値を設定して取得
        TestEntity entity = new TestEntity("123", "John");
        setFieldValues(entity, "456", "Alice");
        getFieldValues(entity);
    }

    public static void getClassInfo(Class<?> clazz) {
        String className = clazz.getSimpleName();
        System.out.println("クラス名: " + className);

        System.out.println("フィールド名:");
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
        }

        System.out.println("メソッド名:");
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName());
        }
    }

    public static void setFieldValues(TestEntity entity, String newId, String newName) {
        try {
            Field idField = TestEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, newId);

            Field nameField = TestEntity.class.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(entity, newName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void getFieldValues(TestEntity entity) {
        try {
            System.out.println("フィールドの値:");
            System.out.println("ID: " + entity.getId());
            System.out.println("Name: " + entity.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//練習問題4
//アノテーションは、プログラムのメタデータを提供する特別な形式のマーカーであり、コードにメタデータを埋め込むための方法。
//アノテーションは @ 記号で始まり、コンパイラや実行時にプログラムの振る舞いや処理を制御するのに役立つ。

//ーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
//練習問題1
//ラムダ（ラムダ式）は、無名関数またはクロージャーの一種であり、コードのブロックを単一の式として表現する方法。
//Javaでは、関数型インタフェースのインスタンスとしてラムダ式を使用でき、簡潔で読みやすいコードを書くための手段として広く利用される。

//練習問題2
//ストリームは、Javaプログラミング言語で導入されたデータ操作のための新しい抽象化。
//要素のシーケンスを表現し、要素に対して一連の操作を行うためのメソッドを提供する。
//ストリームはコレクションとは異なり、要素をメモリに格納せずに処理するため、大量のデータや無限のデータソースにも適している。