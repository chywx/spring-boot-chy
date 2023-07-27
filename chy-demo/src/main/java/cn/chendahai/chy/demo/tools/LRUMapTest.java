package cn.chendahai.chy.demo.tools;


import org.apache.commons.collections4.map.LRUMap;

public class LRUMapTest {

    public static void main(String[] args) {
        LRUMap<String, String> map = new LRUMap<>(3);
        map.put("A", "a");
        map.put("B", "b");
        map.put("C", "c");
        System.out.println(map);
        map.put("D", "d");
        map.put("E", "e");
        System.out.println(map);
        map.put("F", "f");
        System.out.println(map);

        String s = map.get("D");
        System.out.println(s);
        map.put("T","t");
        System.out.println(map);

    }
}
