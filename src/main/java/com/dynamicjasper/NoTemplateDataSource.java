package com.dynamicjasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;

import java.util.Iterator;
import java.util.List;

/**
 * Created by abdurrahim.efe on 21.11.2017.
 */
public class NoTemplateDataSource extends JRAbstractBeanDataSource {
    private static final java.lang.String COL_EXPR_PREFIX = "COLUMN";
    private static final java.lang.String COL_HEADER_EXPR_PREFIX = "HEADER";
    private List<String> columnHeaders;
    private List<List<String>> rows;
    private Iterator<List<String>> iterator;
    private List<String> currentRow;

    public NoTemplateDataSource(List<String> columnHeaders, List<List<String>> rows) {
        super(true);
        this.columnHeaders = columnHeaders;
        this.rows = rows;
        if (this.rows != null) {
            this.iterator = this.rows.iterator();
        }
    }



    public boolean next() {
        boolean hasNext = false;
        if (iterator != null) {
            hasNext = iterator.hasNext();
            if (hasNext) {
                this.currentRow = iterator.next();
            }
        }
        return hasNext;
    }

    public Object getFieldValue(JRField field) throws JRException {
        String fieldName = field.getName();
        if (fieldName.startsWith(COL_EXPR_PREFIX)) {
            String indexValue = fieldName.substring(COL_EXPR_PREFIX.length());
            return currentRow.get(Integer.parseInt(indexValue));
        } else if (fieldName.startsWith(COL_HEADER_EXPR_PREFIX)) {
            int indexValue = Integer.parseInt(fieldName.substring(COL_HEADER_EXPR_PREFIX.length()));
            return columnHeaders.get(indexValue);
        }  else {
            throw new RuntimeException("Field:'" + fieldName + " prefix'i belirlenenler dışında!");
        }
    }

    public void moveFirst() {
        if (rows != null) {
            iterator = rows.iterator();
        }
    }
}
