package com.github.clagomess.asciitable;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;

import java.util.*;

public class AsciiTableTest {
    @Test
    public void setListMap() throws AsciiTableException {
        List<Map<String,String>> dadosMap = new LinkedList<>();

        Map<String,String> row = new HashMap<>();
        row.put("col_a", "value_a");
        row.put("col_b", "value_b");
        row.put("col_c", "value_c");

        // 3 times
        dadosMap.add(row);
        dadosMap.add(row);
        dadosMap.add(row);

        // with lines
        AsciiTable at = new AsciiTable(dadosMap);
        System.out.println(at.build());

        // without lines
        at = new AsciiTable(dadosMap, true, false);
        System.out.println(at.build());

        // without header
        at = new AsciiTable(dadosMap, false, true);
        System.out.println(at.build());
    }

    @Test(expected = AsciiTableException.class)
    public void setWrong() throws AsciiTableException {
        List<String> header = new LinkedList<>();
        header.add("col a");
        header.add("col b");

        List<List<String>> body = new LinkedList<>();
        for(int i = 0; i < 3; i++){
            List<String> row = new LinkedList<>();
            row.add(String.format("row %s: col a", i));
            row.add(String.format("row %s: col b", i));
            row.add(String.format("row %s: col c", i));

            body.add(row);
        }

        // ok
        AsciiTable at = new AsciiTable(false, true);
        at.setHeader(header);
        at.setBody(body);
        System.out.println(at.build());

        // wrong
        at = new AsciiTable();
        at.setHeader(header);
        at.setBody(body);
        System.out.println(at.build());
    }

    @Test
    public void addRowList() throws AsciiTableException {
        List<String> header = new LinkedList<>();
        header.add("col a");
        header.add("col b");

        AsciiTable at = new AsciiTable();
        at.setHeader(header);

        for(int i = 0; i < 3; i++){
            List<String> row = new LinkedList<>();

            row.add(String.format("row %s: col a", i));
            row.add(String.format("row %s: col b", i));

            at.addBodyRow(row);
        }

        System.out.println(at.build());
    }

    @Test
    public void addRowMap() throws AsciiTableException {
        List<String> header = new LinkedList<>();
        header.add("col a");
        header.add("col b");
        header.add("col c");
        header.add("col d");

        AsciiTable at = new AsciiTable();
        at.setHeader(header);

        for(int i = 0; i < 5; i++){
            Map<String, String> row = new LinkedHashMap<>();
            row.put("col_a", String.format("row %s; col a; val %s", i, RandomStringUtils.randomAscii((int)(Math.random() * 20))));
            row.put("col_b", String.format("row %s; col b; val %s", i, RandomStringUtils.randomAscii((int)(Math.random() * 20))));
            row.put("col_c", null);
            row.put("col_d", "");

            at.addBodyRow(row);
        }

        System.out.println(at.build());
    }
}
