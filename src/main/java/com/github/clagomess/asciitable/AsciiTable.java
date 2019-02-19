package com.github.clagomess.asciitable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AsciiTable {
    public static String build(List<Map<String,String>> dadosMap) {
        StringBuilder txt = new StringBuilder();

        if(dadosMap.isEmpty()){
            return "";
        }

        Map<String, Integer> columnSize = new LinkedHashMap<>();
        StringBuilder separador = new StringBuilder();

        // Verificar tamanhos colunas
        boolean checkHeadSize = false;
        for (Map<String,String> row : dadosMap){
            for (Map.Entry<String, String> entry : row.entrySet()) {
                final String curr = (entry.getValue() != null ? entry.getValue() : "-");

                if(!columnSize.containsKey(entry.getKey())){
                    columnSize.put(entry.getKey(), 1);
                }

                if(curr.length() > columnSize.get(entry.getKey())){
                    columnSize.put(entry.getKey(), curr.length());
                }

                if(!checkHeadSize && entry.getKey().length() > columnSize.get(entry.getKey())){
                    columnSize.put(entry.getKey(), entry.getKey().length());
                }
            }

            checkHeadSize = true;
        }

        // Monta separador
        for (Map.Entry<String, Integer> entry : columnSize.entrySet()) {
            separador.append("+-");
            separador.append((new String(new byte[entry.getValue()])).replace("\0", "-"));
            separador.append("-");
        }
        separador.append("+\r\n");

        // Monta TXT
        txt.append(separador.toString().replace("-", "="));
        for (Map.Entry<String, String> entry : dadosMap.get(0).entrySet()) {
            String curr = entry.getKey();
            Integer paddingLength = columnSize.get(curr) - curr.length();
            String padding = (new String(new byte[paddingLength])).replace("\0", " ");

            txt.append("| ").append(curr).append(padding).append(" ");
        }
        txt.append("|\r\n");
        txt.append(separador.toString().replace("-", "="));

        for (Map<String,String> row : dadosMap){
            for (Map.Entry<String, String> entry : row.entrySet()) {
                String curr = (entry.getValue() != null ? entry.getValue() : "");
                Integer paddingLength = columnSize.get(entry.getKey()) - curr.length();
                String padding = (new String(new byte[paddingLength])).replace("\0", " ");

                txt.append("| ").append(curr).append(padding).append(" ");
            }
            txt.append("|\r\n");
            txt.append(separador);
        }

        return txt.toString();
    }
}
