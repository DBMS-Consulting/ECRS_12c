package com.novartis.ecrs.batchJob;

import com.novartis.ecrs.ui.utility.ADFUtils;
import com.novartis.ecrs.ui.utility.ExcelExportUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.javatools.resourcebundle.BundleFactory;

import oracle.jbo.RowSetIterator;

import oracle.jdbc.OracleCallableStatement;

import oracle.security.crypto.util.InvalidFormatException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ScheduleBatchJob implements Job{
    private static int count;
    public static final Logger logger = Logger.getLogger(ScheduleBatchJob.class);
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
            rs.close();
            ps.close();
            ExcelExportUtils.writeExcelSheet(sheet, dataList, rowStartIndex,
                                             cellStartIndex, columnMap, null,
                                             dateCellFormat, emptyValReplace,getImageInpStream());
            //write image to sheet
            //ExcelExportUtils.writeImageTOExcel(sheet,getImageInpStream());
            logger.info("End of generating MedDRA Report ");
            generatePTReport(conn);
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
    
    public void generatePTReport(Connection conn){
        logger.info("Start of ScheduleBatchJob:generatePTReport()");
        Workbook workbook = null;
        FileOutputStream outputStream = null;
        String outputFolder = null;
        PreparedStatement ps;
    try {
        ps = conn.prepareStatement("Select crs_ID from CRS_PT_EXPORT where GENERATE_EXPORT = 'Y'");
        ResultSet rs = ps.executeQuery();

            PreparedStatement ps1;
            ps1 = conn.prepareStatement("select prop_value from CRS_PROPERTIES where Prop_name = 'REPORT_SOURCE'");
            ResultSet rs1 = ps1.executeQuery();
        
            while(rs1.next()){
                outputFolder = rs1.getString("prop_value");
            }
            rs1.close();
            ps1.close();

        while(rs.next()){
            String fileAbsPath = null;
            fileAbsPath = outputFolder;   
            Long crs_ID = rs.getLong("crs_ID");
                logger.info("Start of generating PT Report for CRS "+crs_ID);
            List wb = exportPTPendingReport(crs_ID, conn);
            workbook = (HSSFWorkbook) wb.get(0);
            fileAbsPath = fileAbsPath.concat("\\PtExport\\"+crs_ID.toString()+".xls");
            outputStream = new FileOutputStream(new File(fileAbsPath));
            workbook.write(outputStream);
            outputStream.flush();
            if(outputStream != null)
            outputStream.close();
            
            OracleCallableStatement cstmt = null;
            cstmt = (OracleCallableStatement)conn.prepareCall("call crs_ui_tms_utils.update_crs_pt_export(?,SYSDATE)");
            cstmt.setNUMBER(1, new oracle.jbo.domain.Number(crs_ID));
            //cstmt.setDATE(2, new oracle.jbo.domain.Date());
            cstmt.execute();  
                logger.info("End of generating PT Report for CRS "+crs_ID);
            }   
        rs.close();
        ps.close();
        }  catch (SQLException e) {
        e.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
    }
        logger.info("End of ScheduleBatchJob:generateReport()");
    }
    
    public List exportPTPendingReport(Long crs_ID, Connection conn) {

    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("PT Export"); //Sheet Name
    int idx = 0; // rows index
    //Creating styles code starts
    HSSFFont colHdrFont = wb.createFont();
    colHdrFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFFont fontSizeHrd = wb.createFont();
    fontSizeHrd.setFontHeightInPoints((short) 16); //setting Headding font size
    fontSizeHrd.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
    HSSFCellStyle colStyleHrdWithFont = wb.createCellStyle();
    colStyleHrdWithFont.setFont(fontSizeHrd);
    
        HSSFFont greenBoldFont = wb.createFont();
        greenBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        greenBoldFont.setColor(IndexedColors.GREEN.getIndex());
        
        HSSFFont redBoldFont = wb.createFont();
        redBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        redBoldFont.setColor(IndexedColors.RED.getIndex());
        
        HSSFFont orangeBoldFont = wb.createFont();
        orangeBoldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        orangeBoldFont.setColor(IndexedColors.ORANGE.getIndex());
        
        HSSFCellStyle greenColourCellStyle = wb.createCellStyle();
        greenColourCellStyle.setFont(greenBoldFont);
        
        HSSFCellStyle redColourCellStyle = wb.createCellStyle();
        redColourCellStyle.setFont(redBoldFont);
        
        HSSFCellStyle orangeColourCellStyle = wb.createCellStyle();
        orangeColourCellStyle.setFont(orangeBoldFont);

    HSSFCellStyle colStyleTopLeft = wb.createCellStyle();
    colStyleTopLeft.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopLeft.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopLeft.setFont(colHdrFont);
    HSSFCellStyle colStyleTopLeftWithCenter = wb.createCellStyle();
    colStyleTopLeftWithCenter.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopLeftWithCenter.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopLeftWithCenter.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopLeftWithCenter.setAlignment(CellStyle.ALIGN_CENTER);
    colStyleTopLeftWithCenter.setFont(colHdrFont);
    HSSFCellStyle colStyleLeft = wb.createCellStyle();
    colStyleLeft.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeft.setFont(colHdrFont);
    HSSFCellStyle colStyleLeftDealNo = wb.createCellStyle();
    colStyleLeftDealNo.setAlignment(CellStyle.ALIGN_LEFT);
    HSSFCellStyle colStyleLeftDept = wb.createCellStyle();
    colStyleLeftDept.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeftDept.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeftDept.setFont(colHdrFont);
    HSSFCellStyle colStyleOnlyRight = wb.createCellStyle();
    colStyleOnlyRight.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleOnlyRightDept = wb.createCellStyle();
    colStyleOnlyRightDept.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    colStyleOnlyRightDept.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleOnlyRightDept.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleLeftBottom = wb.createCellStyle();
    colStyleLeftBottom.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeftBottom.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeftBottom.setFont(colHdrFont);
    HSSFCellStyle colStyleLeftBottomWithOutHrd = wb.createCellStyle();
    colStyleLeftBottomWithOutHrd.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleLeftBottomWithOutHrd.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleRightBottomWithOutHrd = wb.createCellStyle();
    colStyleRightBottomWithOutHrd.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleRightBottomWithOutHrd.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleRightBottomWithOutHrdDept = wb.createCellStyle();
    colStyleRightBottomWithOutHrdDept.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleRightBottomWithOutHrdDept.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    colStyleRightBottomWithOutHrdDept.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleTop = wb.createCellStyle();
    colStyleTop.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    colStyleTop.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleTop.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleTop.setAlignment(CellStyle.ALIGN_CENTER);
    colStyleTop.setFont(colHdrFont);
    HSSFCellStyle colStyleTopRight = wb.createCellStyle();
    colStyleTopRight.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopRight.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopRight.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    colStyleTopRight.setAlignment(CellStyle.ALIGN_CENTER);
    colStyleTopRight.setFont(colHdrFont);
    HSSFCellStyle colStyleTopWithOutHrd = wb.createCellStyle();
    colStyleTopWithOutHrd.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleBottom = wb.createCellStyle();
    colStyleBottom.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    HSSFCellStyle colStyleBottomWithHrd = wb.createCellStyle();
    colStyleBottomWithHrd.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleBottomWithHrd.setFont(colHdrFont);
    HSSFCellStyle colStyleRight = wb.createCellStyle();
    colStyleRight.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
    colStyleRight.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
    colStyleRight.setFont(colHdrFont);
    HSSFCellStyle colStyleHrd = wb.createCellStyle();
    colStyleHrd.setFont(colHdrFont);
    HSSFCellStyle colStyleHrdDept = wb.createCellStyle();
    colStyleHrdDept.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
    colStyleHrdDept.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
    colStyleHrdDept.setFont(colHdrFont);
    
    PreparedStatement ps;
    String taslName = null;
    String bslName = null;
    String crsName = null;
    String stateName = null;
    String designeeName = null;
    try{
        
        PreparedStatement ps1;
        ps1 = conn.prepareStatement("SELECT             \n" + 
        "    CrsContentEO.CRS_ID,             \n" + 
        "    CrsContentEO.CRS_NAME,                    \n" + 
        "    CrsCompoundEO.COMPOUND_TYPE,             \n" + 
        "    CrsCompoundEO.COMPOUND_CODE,       \n" + 
        "    CrsContentEO.BSL_NAME,      \n" + 
        "    CrsContentEO.TASL_NAME,   \n" + 
        "    CrsStatesEO.STATE_NAME,  \n" + 
        "    crs_ui_tms_utils.get_name_list_from_usernames(CrsContentEO.DESIGNEE) designee_name  \n" + 
        "FROM             \n" + 
        "    CRS_CONTENT CrsContentEO,             \n" + 
        "    CRS_COMPOUNDS CrsCompoundEO,     \n" + 
        "    CRS_STATES CrsStatesEO     \n" + 
        "WHERE CrsContentEO.CRS_ID = " + crs_ID +"\n"+
        "AND CrsContentEO.COMPOUND_ID = CrsCompoundEO.COMPOUND_ID      \n" + 
        "AND CrsContentEO.STATE_ID = CrsStatesEO.STATE_ID    \n");
        ResultSet rs1 = ps1.executeQuery();
        while(rs1.next()){
            crsName = rs1.getString("CRS_NAME");
            stateName = rs1.getString("STATE_NAME");
            designeeName = rs1.getString("designee_name");
            taslName = rs1.getString("TASL_NAME");
            bslName = rs1.getString("BSL_NAME");
        }
        rs1.close();
        ps1.close();
        
        PreparedStatement ps2;
        ps2 = conn.prepareStatement("SELECT RTRIM(last_name  \n" + 
        "  ||', '  \n" + 
        "  ||first_name,', ') full_name  \n" + 
        "  FROM crs_roles c,  \n" + 
        "    opa.opa_accounts a,  \n" + 
        "    dba_role_privs r  \n" + 
        "  WHERE C.ORACLE_ROLE_NAME = R.GRANTED_ROLE  \n" + 
        "  AND R.GRANTEE            = a.ACCOUNT_NAME\n" + 
        "  AND a.END_TS            = '15-AUG-3501'\n" + 
        "  AND c.role_name          = 'CRS_TASL'  \n" + 
        "  AND upper(a.account_name) = '"+taslName+"'");
        ResultSet rs2 = ps2.executeQuery();
        while(rs2.next()){
            taslName = rs2.getString("full_name");
        }
        rs2.close();
        ps2.close();
        
        PreparedStatement ps3;
        ps3 = conn.prepareStatement("SELECT RTRIM(last_name  \n" + 
        "  ||', '  \n" + 
        "  ||first_name,', ') full_name  \n" + 
        "  FROM crs_roles c,  \n" + 
        "    opa.opa_accounts a,  \n" + 
        "    dba_role_privs r  \n" + 
        "  WHERE C.ORACLE_ROLE_NAME = R.GRANTED_ROLE  \n" + 
        "  AND R.GRANTEE            = a.ACCOUNT_NAME\n" + 
        "  AND a.END_TS            = '15-AUG-3501'\n" + 
        "  AND c.role_name          = 'CRS_BSL'  \n" + 
        "  AND upper(a.account_name) = '"+bslName+"'");
        ResultSet rs3 = ps3.executeQuery();
        while(rs3.next()){
            bslName = rs3.getString("full_name");
        }
        rs3.close();
        ps3.close();
        
    //Creating styles code ends
    ps = conn.prepareStatement("SELECT crs_id,     \n" + 
    "  crs_name,     \n" + 
    "  safety_topic_of_interest,     \n" + 
    "  risk_purpose_list,     \n" + 
    "  meddra_term,     \n" + 
    "  pt_name,     \n" + 
    "  pt_code,\n" + 
    "  DOMAIN_NAME\n" + 
    "FROM CRS_MEDDRA_PT_CUR     \n" + 
    "where meddra_term is not null and crs_id = " + crs_ID +"\n"+
    "ORDER BY safety_topic_of_interest,     \n" + 
    "  meddra_term,     \n" + 
    "  pt_name");
    ResultSet rs = ps.executeQuery();
      
        HSSFRow row = null;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("CRS Name: "+crs_ID);
        row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("CRS ID: "+crsName);
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
           // if(ADFUtils.evaluateEL("#{sessionScope.dictVersion}") != null)
//            row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
//            else
            row.createCell(0).setCellValue("Dictionary Version: ");
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("Status: ");
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
            row.createCell(0).setCellValue("Downloaded Time: "+sdf.format(new Date()));
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("Release Status: Current");
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("State: "+stateName);
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("GPSL: "+bslName);
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("HPS: "+taslName);
            row.getCell(0).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("Designee: "+designeeName);
            row.getCell(0).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            sheet.createRow(idx);
            idx = idx + 1;
            sheet.createRow(idx);
        
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
                    row.createCell(0).setCellValue("Safety Topic Of Interest"); //setting column heading
                    sheet.autoSizeColumn(0);
                    row.getCell(0).setCellStyle(colStyleTopLeft);
                    row.createCell(1).setCellValue("Risk Purpose List");
                    sheet.autoSizeColumn(1);
                    row.getCell(1).setCellStyle(colStyleTopLeft);
                    row.createCell(2).setCellValue("MedDRA Term");
                    sheet.autoSizeColumn(2);
                    row.getCell(2).setCellStyle(colStyleTopLeft);
                    row.createCell(3).setCellValue("PT Name");
                    sheet.autoSizeColumn(3);
                    row.getCell(3).setCellStyle(colStyleTopLeft);
                    row.createCell(4).setCellValue("PT Code");
                    sheet.autoSizeColumn(4);
                    row.getCell(4).setCellStyle(colStyleTopLeft);
        
        idx = idx + 1;
        
        String safetyTopicOfInterest = null;
        String riskPurposeList = null;
        String meddraTerm = null;
        String ptName = null;
        String ptCode = null;
    while (rs.next()) { 
        System.out.println("----- number of lines processed -----"+idx);
        safetyTopicOfInterest = rs.getString("safety_topic_of_interest");
        riskPurposeList = rs.getString("risk_purpose_list");
        meddraTerm = rs.getString("meddra_term");
        ptName = rs.getString("pt_name");
        ptCode = rs.getString("pt_code");
        row = sheet.createRow(idx); //creating 2nd row
        if (safetyTopicOfInterest != null){
            row.createCell(0).setCellValue(safetyTopicOfInterest);
        }
        else
            row.createCell(0).setCellValue("");
        sheet.autoSizeColumn(0);

        if (riskPurposeList != null){
            row.createCell(1).setCellValue(riskPurposeList);
        }
        else
            row.createCell(1).setCellValue("");
        sheet.autoSizeColumn(1);

        if (meddraTerm != null){
            row.createCell(2).setCellValue(meddraTerm);
        }
        else
            row.createCell(2).setCellValue("");
        sheet.autoSizeColumn(2);
        
        if (ptName != null){
            row.createCell(3).setCellValue(ptName);
        }
        else
            row.createCell(3).setCellValue("");
        sheet.autoSizeColumn(3);
        
        if (ptCode != null){
            row.createCell(4).setCellValue(ptCode);
        }
        else
            row.createCell(4).setCellValue("");
        sheet.autoSizeColumn(4);       
    //2nd Row ends
    idx = idx + 1;
    }
        rs.close();
        ps.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
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
