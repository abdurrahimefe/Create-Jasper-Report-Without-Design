package com.dynamicjasper;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) throws Exception {
        List<String> headers=createDummyHeaders();
        List<List<String>> rows=createDummyRows(headers.size());
        NoTemplateDataSource dataSource = new NoTemplateDataSource(headers,rows);
        IReportBuilder reportBuilder = new NoTemplateReportBuilder(headers.size());
        new NoTemplateDynamicJasper(reportBuilder, dataSource).run();
    }

    private static List<List<String>> createDummyRows(int columnCount) {
        List<List<String>> rows = new ArrayList<List<String>>();
        List<String> currentRow=null;
        for(int rowNumber=0;rowNumber<50;rowNumber++){
            currentRow=new ArrayList<String>();

            for(int columnNumber=0;columnNumber<columnCount;columnNumber++){
                currentRow.add("Data"+rowNumber+columnNumber);
            }
            rows.add(currentRow);
        }
        return rows;
    }

    private static List<String> createDummyHeaders() {
        ArrayList<String> headers = new ArrayList<>();
        for(int i=0;i<10;i++){
            headers.add("Header"+i);
        }
        return headers;
    }
}
