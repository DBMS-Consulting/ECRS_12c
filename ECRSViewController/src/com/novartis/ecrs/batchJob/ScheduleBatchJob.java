package com.novartis.ecrs.batchJob;

import com.novartis.ecrs.ui.bean.ScheduleComponentReportBean;
import com.novartis.ecrs.ui.utility.ADFUtils;
import com.novartis.ecrs.ui.utility.ExcelExportUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import oracle.javatools.resourcebundle.BundleFactory;
import oracle.jbo.RowSetIterator;
import oracle.security.crypto.util.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ScheduleBatchJob implements Job{
    private static int count;
    public ScheduleBatchJob() {
        super();
    }

    public void execute(JobExecutionContext jobContext) throws JobExecutionException {
        System.out.println("--------------------------------------------------------------------");
               System.out.println("MyJob start1: " + jobContext.getFireTime());
               JobDetail jobDetail = jobContext.getJobDetail(); 
               System.out.println("-----------Batch Job is called at----------"+new Date());
               System.out.println("MyJob next scheduled time: " + jobContext.getNextFireTime());
               System.out.println("--------------------------------------------------------------------");
               
        // Add event code here...
        //  _logger.info("Start of CRSReportsBean:onAdminReportItmes()");
        Workbook workbook = null;
        FileOutputStream outputStream = null;
        Connection conn = null;

        //OutputStream outputStream = null;
        InputStream excelInputStream = getExcelInpStream();
        try {
            
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/EcrsDS");
            conn = ds.getConnection();
            PreparedStatement ps;
            PreparedStatement ps1;
            ps1 = conn.prepareStatement("select prop_value from CRS_PROPERTIES where Prop_name = 'DOWNLOAD_DIRECTORY'");
            ResultSet rs1 = ps1.executeQuery();
            while(rs1.next()){
                String outputFolder = rs1.getString("prop_value");
                try {
//                    outputStream =
//                        new FileOutputStream(new File("C:\\Users\\DileepKumar\\Desktop\\Donna\\MedraReport\\MedDRAComponentsReport.xls"));
                    outputFolder = outputFolder.concat("\\MedDRAComponentsReport.xls");
                    outputStream = new FileOutputStream(new File(outputFolder));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            ps1.close();
            ps = conn.prepareStatement("SELECT d.meddra_term,\n" + 
            "          d.meddra_extension,\n" + 
            "          r.safety_topic_of_interest,\n" + 
            "          c.crs_name,\n" + 
            "          r.risk_purpose_list,\n" + 
            "          r.soc_term\n" + 
            "    FROM crs_content          c,\n" + 
            "         crs_risk_relations   r,\n" + 
            "         crs_risk_definitions d\n" + 
            "    WHERE c.crs_id = r.crs_id\n" + 
            "      AND r.crs_risk_id = d.crs_risk_id\n" + 
            "    ORDER BY d.meddra_term, r.safety_topic_of_interest, c.crs_name");
            ResultSet rs = ps.executeQuery();
      
            RowSetIterator rowSet = null;
            int rowStartIndex = 8;
            int cellStartIndex = 0;
            String emptyValReplace = null;
            String dateCellFormat = "M/dd/yyyy";

            workbook = WorkbookFactory.create(excelInputStream);
            LinkedHashMap columnMap = new LinkedHashMap();
            ResourceBundle rsBundle =
                BundleFactory.getBundle("com.novartis.ecrs.model.ECRSModelBundle");
            //Here Key will be ViewObject Attribute
            columnMap.put("MeddraTerm", rsBundle.getString("DEFINITIONS"));
            columnMap.put("MeddraExtension", rsBundle.getString("LEVEL"));
            columnMap.put("SafetyTopicOfInterest",
                          rsBundle.getString("SAFETY_TOPIC"));
            columnMap.put("CrsName", rsBundle.getString("CRS_NAME"));
            columnMap.put("RiskPurposeList", rsBundle.getString("PURPOSE"));
            columnMap.put("SocTerm", rsBundle.getString("MQ_GROUP_OR_SOC"));
            workbook.setMissingCellPolicy(org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
            Sheet sheet = workbook.getSheetAt(0);
            int i = 0;
            
            List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
            while(rs.next()){
                Map<String,String> dataMap = new HashMap<String,String>();
                dataMap.put("MeddraTerm", rs.getString("meddra_term"));
                dataMap.put("MeddraExtension", rs.getString("meddra_extension"));
                dataMap.put("SafetyTopicOfInterest", rs.getString("safety_topic_of_interest"));
                dataMap.put("CrsName", rs.getString("crs_name"));
                dataMap.put("RiskPurposeList", rs.getString("risk_purpose_list"));
                dataMap.put("SocTerm", rs.getString("soc_term"));
                dataList.add(dataMap);
            }
            ps.close();
            ExcelExportUtils.writeExcelSheet(sheet, dataList, rowStartIndex,
                                             cellStartIndex, columnMap, null,
                                             dateCellFormat, emptyValReplace,getImageInpStream());
            //write image to sheet
            //ExcelExportUtils.writeImageTOExcel(sheet,getImageInpStream());
        } catch (InvalidFormatException invalidFormatException) {
            invalidFormatException.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null)
                    workbook.write(outputStream);
                if (excelInputStream != null)
                    excelInputStream.close();
                if (outputStream != null)
                    outputStream.close();
                if (conn != null)
                    conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
                      
    }
    
    /**
     * @return InputStream
     */
    public InputStream getExcelInpStream() {
        ExcelExportUtils excelUtils = new ExcelExportUtils();
        return excelUtils.getExcelInpStream();
    }
    
    /**
     * @return InputStream
     */
    public InputStream getImageInpStream() {
        ExcelExportUtils excelUtils = new ExcelExportUtils();
        return excelUtils.getImageInpStream();
    }
    
}
