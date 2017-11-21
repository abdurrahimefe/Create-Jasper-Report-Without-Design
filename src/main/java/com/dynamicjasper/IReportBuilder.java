package com.dynamicjasper;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.design.JasperDesign;

public interface IReportBuilder {

    void addDynamicColumns() throws Exception;

    JasperDesign getJasperDesign();
}
