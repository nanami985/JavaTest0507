package jp.co.aforce.practice.generics;

public class Generics_Main {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
		Generics<String> stringジェネリクス = new Generics<>();
		stringジェネリクス.setData("Hello");
		System.out.println(stringジェネリクス.getData());
		Generics<Integer> integerジェネリクス = new Generics<>();
		integerジェネリクス.setData(123);
		System.out.println(integerジェネリクス.getData());
	}

}