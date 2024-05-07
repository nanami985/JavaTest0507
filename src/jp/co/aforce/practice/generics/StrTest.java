//練習問題3
//strパラメータがnullの場合に、その後の str.equals("") の呼び出しで、nullオブジェクトへの参照を使用しているため。
//nullは何も参照していないことを意味し、メソッドやフィールドが存在しないオブジェクトにアクセスしようとするとNullPointerExceptionが発生する。

package jp.co.aforce.practice.generics;

public class StrTest {
    public static void main(String[] args) {
        strCheck(null);
    }

    static void strCheck(String str) {
        if (str != null && str.equals("")) {
            System.out.println("Stringはnullでした。");
        }
    }
}