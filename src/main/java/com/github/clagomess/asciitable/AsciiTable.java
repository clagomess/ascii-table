package com.github.clagomess.asciitable;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class AsciiTable {
    private boolean showRowLine = true;
    private boolean showHeader = true;

    @Setter
    private List<String> header = new LinkedList<>();

    @Setter
    private List<List<String>> body = new LinkedList<>();

    private List<Integer> columnSize = new LinkedList<>();

    public AsciiTable(boolean showHeader, boolean showRowLine){
        this.showRowLine = showRowLine;
        this.showHeader = showHeader;
    }

    public AsciiTable(List<Map<String,String>> map){
        header = new LinkedList<>();
        body = new LinkedList<>();

        for (Map.Entry<String, String> entry : map.get(0).entrySet()) {
            header.add(entry.getKey());
        }

        for (Map<String,String> row : map){
            addBodyRow(row);
        }
    }

    public AsciiTable(List<Map<String,String>> map, boolean showHeader, boolean showRowLine){
        this(map);
        this.showRowLine = showRowLine;
        this.showHeader = showHeader;
    }

    public void addBodyRow(List<String> row){
        body.add(row);
    }

    public void addBodyRow(Map<String, String> row){
        List<String> rowList = new LinkedList<>();

        for (Map.Entry<String, String> entry : row.entrySet()) {
            rowList.add(entry.getValue() != null ? entry.getValue().toString() : null);
        }

        body.add(rowList);
    }

    private void initColumnSize(){
        if(!columnSize.isEmpty()){
            return;
        }

        if(showHeader) {
            for (int idx = 0; idx < header.size(); idx++) {
                columnSize.add(1);
            }
        }

        if(columnSize.isEmpty() && !body.isEmpty()){
            for(int idx = 0; idx < body.get(0).size(); idx++){
                columnSize.add(1);
            }
        }
    }

    private void setColumnSize(List<String> row){
        initColumnSize();
        int idx = 0;

        for (String col : row) {
            final String curr = (col != null ? col : "-");

            if(curr.length() > columnSize.get(idx)){
                columnSize.set(idx, curr.length());
            }

            idx++;
        }
    }

    private StringBuilder getSeparator(char style){
        StringBuilder separador = new StringBuilder();

        for (int size : columnSize) {
            separador.append("+");
            separador.append(style);
            separador.append((new String(new byte[size])).replace('\0', style));
            separador.append(style);
        }

        separador.append("+\r\n");

        return separador;
    }

    private StringBuilder buildRow(List<String> row){
        StringBuilder txt = new StringBuilder();

        int idx = 0;
        for (String col : row) {
            String curr = col == null ? "-" : col;
            Integer paddingLength = columnSize.get(idx) - curr.length();
            String padding = (new String(new byte[paddingLength])).replace("\0", " ");

            txt.append("| ").append(curr).append(padding).append(" ");
            idx++;
        }

        txt.append("|\r\n");

        return txt;
    }

    public String build() throws AsciiTableException {
        StringBuilder txt = new StringBuilder();

        if(body.isEmpty()){
            return "";
        }

        if(header.size() != body.get(0).size() && showHeader){
            throw new AsciiTableException("header size and body size are not equal");
        }

        // Verificar tamanhos colunas: header and body
        if(showHeader) {
            setColumnSize(header);
        }

        for (List<String> row : body) {
            setColumnSize(row);
        }

        // Monta TXT
        if(showHeader) {
            txt.append(getSeparator(showRowLine ? '=' : '-'));
            txt.append(buildRow(header));
            txt.append(getSeparator(showRowLine ? '=' : '-'));
        }else{
            txt.append(getSeparator('-'));
        }

        for (List<String> row : body){
            txt.append(buildRow(row));

            if(showRowLine){
                txt.append(getSeparator('-'));
            }
        }

        if(!showRowLine){
            txt.append(getSeparator('-'));
        }

        return txt.toString();
    }
}
