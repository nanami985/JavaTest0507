//練習問題1
//Apache Commons ライブラリ: 
//	Apache Commonsは、Apacheソフトウェア財団によって提供される多くのJavaライブラリの集合体。
//	これには、コーディングの効率化や汎用的な問題の解決に役立つ多くのユーティリティが含まれている。
//	例えば、Apache Commons Langライブラリには、文字列操作、配列操作、日付操作などの便利なユーティリティ等。
//Gson (Google Gson) ライブラリ: 
//	Google Gsonは、JavaオブジェクトとJSONデータの変換を行うためのライブラリ。
//	JSONは、Webサービスやデータのやり取りなどで広く使用されているフォーマットであり、
//	Gsonを使用することで、JavaオブジェクトとJSONの間で簡単に変換を行うことができる。

package jp.co.aforce.lib;

public class Person {
	private int id;
    private String name;
    private String gender;
    private int age;
    private String occupation;

    public Person(int id, String name, String gender, int age, String occupation) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.occupation = occupation;
    }

    @Override
    public String toString() {
        return "ID:" + id + " 名前：" + name + " 性別：" + gender + " 年齢：" + age + " 職業：" + occupation;
    }
}