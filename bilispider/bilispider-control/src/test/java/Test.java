import org.flamemad.bilispider.Tools;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
