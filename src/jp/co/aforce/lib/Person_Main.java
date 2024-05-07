package jp.co.aforce.lib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Person_Main {
    public static void main(String[] args) {
        String csvFile = "src/jp/co/aforce/lib/input.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // ヘッダー行を読み飛ばす
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] rowData = line.split(csvSplitBy);
                int id = Integer.parseInt(rowData[0]);
                String name = rowData[1];
                String gender = rowData[2];
                int age = Integer.parseInt(rowData[3]);
                String occupation = rowData[4];

                Person person = new Person(id, name, gender, age, occupation);
                System.out.println(person);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}