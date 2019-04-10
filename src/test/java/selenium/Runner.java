package selenium;

public class Runner{

    static Thread chromeThread = new Thread(new ChromeTest(){
        @Override
        public void run() {
            super.run();
        }
    });

    static Thread safariThread = new Thread(new SafariTest(){
        @Override
        public void run() {
            super.run();
        }
    });

    public static void main(String[] args) {
        chromeThread.start();
        safariThread.start();
    }

}
