package com.dynamicjasper;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;


public class NoTemplateReportBuilder implements IReportBuilder {

    private final static int TOTAL_PAGE_WIDTH = 1000;
    private final static int SPACE_BETWEEN_COLS = 0;
    private final static int COLUMN_HEIGHT = 22;
    private final static int BAND_HEIGHT = 48;
    private final static int MARGIN = 0;
    private  static int Y_POS = 0;

    private int numberOfColumns;
    private JasperDesign jasperDesign;
    private JRDesignStyle columnStyle;

    public NoTemplateReportBuilder(int numberOfColumns) {
        this.numberOfColumns=numberOfColumns;
        createTemplate();

    }


    public void addDynamicColumns() throws Exception {
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(COLUMN_HEIGHT);
        JRDesignBand headerBand = new JRDesignBand();
        JRDesignBand titleBand=new JRDesignBand();
        titleBand.setHeight(BAND_HEIGHT);
        JRDesignStyle columnStyle = getColumnStyle();
        JRDesignStyle headerStyle = getHeaderStyle();
        jasperDesign.addStyle(columnStyle);
        jasperDesign.addStyle(headerStyle);

        int xPos = MARGIN;
        int columnWidth = (TOTAL_PAGE_WIDTH - (SPACE_BETWEEN_COLS * (numberOfColumns - 1))) / numberOfColumns;
        xPos = MARGIN;

        for (int i = 0; i < numberOfColumns; i++) {
            //Header Bind
            addField(ColumnType.HEADER.getName() + i);
            JRDesignTextField headerField = addValue(headerStyle, xPos, Y_POS, i, columnWidth, COLUMN_HEIGHT, ColumnType.HEADER.getName());
            titleBand.addElement(headerField);
           // Column Binding
            addField(ColumnType.COLUMN.getName() + i);
            JRDesignTextField colField = addValue(columnStyle, xPos, Y_POS, i, columnWidth, COLUMN_HEIGHT, ColumnType.COLUMN.getName());
            detailBand.addElement(colField);
            xPos = xPos + columnWidth + SPACE_BETWEEN_COLS;
        }
        jasperDesign.setTitle(titleBand);
        jasperDesign.setColumnHeader(headerBand);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

    }

    public void addField(String prefix) throws JRException {
        JRDesignField field = new JRDesignField();
        field.setName(prefix);
        field.setValueClass(String.class);
        jasperDesign.addField(field);
    }
    private JRDesignTextField addValue(JRDesignStyle columnHeaderStyle, int xPos,int yPos, int order,int columnWidth,int columnHeight, String colExprPrefix) {
        JRDesignTextField columnField = new JRDesignTextField();
        columnField.setX(xPos);
        columnField.setY(yPos);
        columnField.setWidth(columnWidth);
        columnField.setHeight(columnHeight);
        columnField.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        columnField.setStyle(columnHeaderStyle);
        JRDesignExpression headerExpression = new JRDesignExpression();
        headerExpression.setValueClass(String.class);
        headerExpression.setText("$F{" + colExprPrefix + order+ "}");
        columnField.setExpression(headerExpression);
        return columnField;
    }
    private void createTemplate() {
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("sampleDynamicJasperDesign");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(5021);
        jasperDesign.setColumnWidth(120);
        jasperDesign.setColumnSpacing(0);
        jasperDesign.setLeftMargin(0);
        jasperDesign.setRightMargin(0);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
        this.jasperDesign=jasperDesign;
    }

    public JasperDesign getJasperDesign() {
        return jasperDesign;
    }


    public JRDesignStyle getColumnStyle() {
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("Calibri");
        normalStyle.setFontSize(10f);
        normalStyle.setBlankWhenNull(true);
        normalStyle.setPdfFontName("Calibri");
        normalStyle.setPdfEncoding("Cp1254");
        normalStyle.setPdfEmbedded(false);
        normalStyle.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        normalStyle.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        return normalStyle;
    }

    public JRDesignStyle getHeaderStyle() {
        JRDesignStyle columnHeaderStyle = new JRDesignStyle();
        columnHeaderStyle.setName("Calibri");
        columnHeaderStyle.setBold(true);
        columnHeaderStyle.setBackcolor(new Color(221,235,247));
        columnHeaderStyle.setMode(ModeEnum.OPAQUE);
        columnHeaderStyle.setForecolor(new Color(0,0,0));
        return columnHeaderStyle;
    }
}
