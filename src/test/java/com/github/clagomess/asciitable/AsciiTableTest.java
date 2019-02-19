package com.github.clagomess.asciitable;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AsciiTableTest {
    @Test
    public void build(){
        List<Map<String,String>> dadosMap = new LinkedList<>();

        Map<String,String> row = new HashMap<>();
        row.put("col_a", "value_a");
        row.put("col_b", "value_b");
        row.put("col_c", "value_c");

        // 3 times
        dadosMap.add(row);
        dadosMap.add(row);
        dadosMap.add(row);

        System.out.println(AsciiTable.build(dadosMap));
    }
}
