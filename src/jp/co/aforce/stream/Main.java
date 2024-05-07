package jp.co.aforce.stream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
練習問題1
public class Main {
    public static void main(String[] args) {
        String csvFile = "src/jp/co/aforce/lib/input.csv";
        String csvSplitBy = ",";

        Map<Integer, String[]> personDataMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); 

            br.lines() 
                .map(line -> line.split(csvSplitBy)) 
                .forEach(rowData -> { 
                    int id = Integer.parseInt(rowData[0]);
                    personDataMap.put(id, rowData);
                });

            for (int id : new int[]{1, 4, 2, 5, 3}) {
                String[] rowData = personDataMap.get(id);
                if (rowData != null) {
                    String name = rowData[1];
                    String gender = rowData[2];
                    int age = Integer.parseInt(rowData[3]);
                    String occupation = rowData[4];
                    System.out.println("ID:" + id + " 名前：" + name + " 性別：" + gender + " 年齢：" + age + " 職業：" + occupation);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
*/

/*
練習問題2
public class Main {
    public static void main(String[] args) {
        String csvFile = "src/jp/co/aforce/lib/input.csv";
        String csvSplitBy = ",";

        Map<Integer, String[]> personDataMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine(); 

            br.lines()
                .map(line -> line.split(csvSplitBy)) 
                .forEach(rowData -> { 
                    int id = Integer.parseInt(rowData[0]);
                    personDataMap.put(id, rowData);
                });

            for (int id : new int[]{1, 2, 3}) {
                String[] rowData = personDataMap.get(id);
                if (rowData != null) {
                    String name = rowData[1];
                    String gender = rowData[2];
                    int age = Integer.parseInt(rowData[3]);
                    String occupation = rowData[4];
                    System.out.println("ID:" + id + " 名前：" + name + " 性別：" + gender + " 年齢：" + age + " 職業：" + occupation);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
*/
/*練習問題3*/
public class Main {
    public static void main(String[] args) {
        String csvFile = "src/jp/co/aforce/lib/input.csv";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            br.lines()
                .map(line -> line.split(csvSplitBy))
                .forEach(rowData -> {
                    int id = Integer.parseInt(rowData[0]);
                    String name = rowData[1];
                    String gender = rowData[2];
                    int age = Integer.parseInt(rowData[3]);
                    String occupation = rowData[4];
                    System.out.println("ID:" + id + " 名前：" + name + " 性別：" + gender + " 年齢：" + age + " 職業：" + occupation);
                });
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}