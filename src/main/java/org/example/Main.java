package org.example;

import java.util.*;


public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        String[] routs = new String[1000];
        for (int i = 0; i < routs.length; i++) {
            routs[i] = generateRoute("RLRFR", 100);
        }
        List<Thread> threads = new ArrayList<>();
        for (String route : routs) {
            Runnable runnable = () -> {
                Integer amountR = 0;
                for (int i = 0; i < route.length(); i++) {
                    if (route.charAt(i) == 'R') {
                        amountR++;
                    }
                }

                System.out.println(route.substring(0, 100) + " -> " + amountR);
                synchronized (amountR) {
                    if (sizeToFreq.containsKey(amountR)) {
                        sizeToFreq.put(amountR, sizeToFreq.get(amountR) + 1);
                    } else {
                        sizeToFreq.put(amountR, 1);
                    }
                }

            };
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();

        }
        for (Thread thread : threads) {
            thread.join();
        }
        Integer maxKey = 0;
        Integer maxRepet = 0;
        for (Integer key : sizeToFreq.keySet()) {
            if (sizeToFreq.get(key) > maxRepet) {
                key = maxKey;
                maxRepet = sizeToFreq.get(key);
            }
        }
        System.out.println("Самое частое количество повторений " + maxKey + " (встретилось " + maxRepet + " раз)");
        sizeToFreq.remove(maxKey);

        System.out.println("Другие размеры:");
        for (Integer key : sizeToFreq.keySet()) {
            int repet = sizeToFreq.get(key);
            System.out.println("- " + key + " (" + repet + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
