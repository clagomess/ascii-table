# ascii-table
### List Map
```java
List<Map<String,String>> dadosMap = new LinkedList<>();

Map<String,String> row = new HashMap<>();
row.put("col_a", "value_a");
row.put("col_b", "value_b");
row.put("col_c", "value_c");

// 3 times
dadosMap.add(row);
dadosMap.add(row);
dadosMap.add(row);
```
#### with lines
```java
AsciiTable at = new AsciiTable(dadosMap);
System.out.println(at.build());
```
```
+=========+=========+=========+
| col_c   | col_b   | col_a   |
+=========+=========+=========+
| value_c | value_b | value_a |
+---------+---------+---------+
| value_c | value_b | value_a |
+---------+---------+---------+
| value_c | value_b | value_a |
+---------+---------+---------+
```

#### without lines
```java
AsciiTable at = new AsciiTable(dadosMap, true, false);
System.out.println(at.build());
```
```
+---------+---------+---------+
| col_c   | col_b   | col_a   |
+---------+---------+---------+
| value_c | value_b | value_a |
| value_c | value_b | value_a |
| value_c | value_b | value_a |
+---------+---------+---------+
```

#### without header
```java
AsciiTable at = new AsciiTable(dadosMap, false, true);
System.out.println(at.build());
```

### RowList
```java
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
```
```
+==============+==============+
| col a        | col b        |
+==============+==============+
| row 0: col a | row 0: col b |
+--------------+--------------+
| row 1: col a | row 1: col b |
+--------------+--------------+
| row 2: col a | row 2: col b |
+--------------+--------------+
```

### RowMap
```java
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
```
```
+===================================+==================================+=======+=======+
| col a                             | col b                            | col c | col d |
+===================================+==================================+=======+=======+
| row 0; col a; val +|Et$nbR}R2     | row 0; col b; val ~W             | -     |       |
+-----------------------------------+----------------------------------+-------+-------+
| row 1; col a; val lN>xSe%Xy#.T^+{ | row 1; col b; val a l.d:a_R      | -     |       |
+-----------------------------------+----------------------------------+-------+-------+
| row 2; col a; val ~               | row 2; col b; val tKNsS[J        | -     |       |
+-----------------------------------+----------------------------------+-------+-------+
| row 3; col a; val V=              | row 3; col b; val                | -     |       |
+-----------------------------------+----------------------------------+-------+-------+
| row 4; col a; val  />`]oS .<      | row 4; col b; val 2r 3S1ko&eqWg+ | -     |       |
+-----------------------------------+----------------------------------+-------+-------+
```
