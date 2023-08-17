package cn.chendahai.chy.demo.tools;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueTest {

    public static void main(String[] args) {
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();

        queue.add("a");
        queue.add("b");
        queue.add("c");

        System.out.println(queue);

        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
