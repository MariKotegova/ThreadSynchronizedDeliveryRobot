import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {

    public static final Map<Integer, Integer> sizeToFread = new HashMap<>();
    public static final int LENGHT = 100;
    public static final char SYMBOL = 'R';
    public static final int COUNTER_THREAD = 1000;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < LENGHT; i++) {
            sizeToFread.put(i + 1, 0);
        }

        for (int j = 0; j < COUNTER_THREAD; j++) {
            Thread thread = new Thread(() -> {
                String text = generateRoute("RLRFR", LENGHT);
                int r = 0;
                for (int i = 0; i < text.length(); i++) {
                    if (text.charAt(i) == SYMBOL) {
                        r++;
                    }
                }
                // System.out.println("Получаем список: " + text);
                //System.out.println("Буква " + SYMBOL + " встречается " + r + " раз.");
                int counter = sizeToFread.get(r);
                sizeToFread.put(r, counter + 1);
            });
            thread.start();
            thread.join();
        }

        int count = 0;
        int key = 0;
        for (int i = 0; i < LENGHT; i++) {
            if (sizeToFread.get(i + 1) != 0 && sizeToFread.get(i + 1) > count) {
                count = sizeToFread.get(i + 1);
                key = (i + 1);
            }
        }
        System.out.println("Самое частое колличество повторений " + key + " (встретилось " + count + " раз)");
        System.out.println("Другие значения: ");
        for (int i = 0; i < LENGHT; i++) {
            if (sizeToFread.get(i + 1) != 0) {
                System.out.println((i + 1) + " повторяется " + sizeToFread.get(i + 1) + " раз.");
            }
            if (sizeToFread.get(i + 1) == 0) {
                sizeToFread.put(i + 1, 1);
            }
        }
    }

    public synchronized static String generateRoute(String letters, int lenght) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < lenght; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
