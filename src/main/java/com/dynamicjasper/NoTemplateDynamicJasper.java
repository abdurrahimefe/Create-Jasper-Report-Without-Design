package com.dynamicjasper;
import com.dynamicjasper.IReportBuilder;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRValidationFault;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class NoTemplateDynamicJasper {

    private JRRewindableDataSource dataSource;
    private IReportBuilder reportBuilder;

    public NoTemplateDynamicJasper(IReportBuilder reportBuilder,JRRewindableDataSource dataSource){
        this.reportBuilder=reportBuilder;
        this.dataSource=dataSource;
    }
    public void run() throws Exception {
        reportBuilder.addDynamicColumns();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportBuilder.getJasperDesign());
        Map<String, Object> params = new HashMap<String, Object>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
        writeToFile(jasperPrint);
    }


    private void writeToFile(JasperPrint jasperPrint) throws JRException, IOException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        File outputFile = new File("output.xlsx");
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setOnePagePerSheet(false);
        configuration.setWhitePageBackground(false);
        configuration.setFreezeRow(2);
	    configuration.setFreezeColumn("B");
        configuration.setWrapText(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
}
