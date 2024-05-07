//練習問題1
//マルチスレッド環境におけるデータの同期: 
//	マルチスレッド環境では、複数のスレッドが同じデータに同時にアクセスすることがある。
//	例えば、複数のスレッドが共有変数に対して読み書きを行う場合、データの整合性が保たれない可能性があるが、
//	排他制御を用いることで、複数のスレッドが同時に共有変数にアクセスすることを防ぎ、データの整合性を確保することができる。
//	具体的な実装例としては、スレッドセーフなコレクションやロック機構を使用することが挙げられる。
//データベーストランザクションの管理: 
//	データベースでは、複数のクライアントが同時にデータを更新することがある。
//	複数のトランザクションが同じデータに対して同時に変更を加えようとすると、データの整合性が損なわれる可能性があるが、
//	排他制御を用いることで、トランザクション間の競合を防ぎ、データの整合性を保つことができる。
//	具体的な実装例としては、トランザクションのロック機構やデータベースの行レベルのロックを使用することが挙げられる。

//練習問題2
//public class BankAccount {
//    private int balance;
//
//    public BankAccount(int b) {
//        balance = b;
//    }
//
//    public synchronized int getMoney(int request) {
//        if (balance + request >= 0) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            balance += request;
//            System.out.println(request + "円取引されました");
//            System.out.println("残高は" + balance + "円です");
//            return -request;
//        } else {
//            System.out.println("お金が足りません");
//            System.out.println("残高は" + balance + "円です");
//            return 0;
//        }
//    }
//}
//
//class Person extends Thread {
//    private BankAccount account;
//    private int cash;
//
//    public Person(BankAccount m, int c) {
//        account = m;
//        cash = c;
//    }
//
//    public void run() {
//        System.out.println("現金が" + cash + "なので銀行に来ます");
//        synchronized (account) {
//            cash += account.getMoney(cash);
//        }
//        System.out.println("現金が" + cash + "円になりました");
//    }
//}
//
//public class ThreadTest {
//    public static void main(String[] args) {
//        BankAccount account = new BankAccount(3000);
//        Person husband = new Person(account, -2000);
//        Person wife = new Person(account, -2000);
//        husband.start();
//        wife.start();
//    }
//}