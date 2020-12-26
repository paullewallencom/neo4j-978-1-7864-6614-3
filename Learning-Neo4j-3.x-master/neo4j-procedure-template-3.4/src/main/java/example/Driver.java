package example;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        Join join = new Join();
        List<String> list = new ArrayList<>();

        list.add("Neo4j");
        list.add("MySql");
        list.add("Oracle");
        list.add("PostgreSql");

        System.out.println(join.join(list, ","));
    }
}
