package com.novartis.ecrs.ui.bean;

import com.novartis.ecrs.model.view.CrsExportPTCurrentVORowImpl;
import com.novartis.ecrs.model.view.ExportPTRVORowImpl;
import com.novartis.ecrs.ui.utility.ADFUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.nav.RichButton;

import oracle.binding.OperationBinding;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import oracle.security.crypto.util.InvalidFormatException;

import org.apache.log4j.Logger;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

public class GeneratePtExportByCrsBean {
    private RichButton btn;

    public GeneratePtExportByCrsBean() {
        super();
    }
    public static final Logger logger = Logger.getLogger(GeneratePtExportByCrsBean.class);
    
    public void generateReport(ActionEvent actionEvent) {
        logger.info("Start of GeneratePtExportByCrsBean:generateReport()");
        DCIteratorBinding iter = ADFUtils.findIterator("ExportPTRVOIterator");
        Row filteredRows[] = iter.getViewObject().getFilteredRows("SelectRow", true);
        Workbook workbook = null;
        FileOutputStream outputStream = null;
        Connection conn = null;
        String outputFolder = null;
        Context ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/EcrsDS");
            conn = ds.getConnection();
            PreparedStatement ps;
            PreparedStatement ps1;
            ps1 = conn.prepareStatement("select prop_value from CRS_PROPERTIES where Prop_name = 'REPORT_SOURCE'");
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                outputFolder = rs1.getString("prop_value");

            }
            ps1.close();
        } catch (NamingException e) {
        } catch (SQLException e) {
        }


        
        if(filteredRows != null && filteredRows.length > 0){
            for(Row row : filteredRows){
            String fileAbsPath = null;
            fileAbsPath = outputFolder;   
            try {
            ExportPTRVORowImpl exportPTRVORowImpl = (ExportPTRVORowImpl)row;
            //List wb = exportPTPendingReport(exportPTRVORowImpl);
            List wb = exportPTPendingReport(exportPTRVORowImpl.getCrsId(),conn);
            workbook = (HSSFWorkbook) wb.get(0);

                //                    outputStream =
                //                        new FileOutputStream(new File("C:\\Users\\DileepKumar\\Desktop\\Donna\\MedraReport\\MedDRAComponentsReport.xls"));
            fileAbsPath = fileAbsPath.concat("\\PtExport\\"+exportPTRVORowImpl.getCrsId()+".xls");
            outputStream = new FileOutputStream(new File(fileAbsPath));
            workbook.write(outputStream);
            outputStream.flush();
            if(outputStream != null)
            outputStream.close();
            DCBindingContainer bc = ADFUtils.getDCBindingContainer();
            OperationBinding updateCrsPtExport = bc.getOperationBinding("updateCrsPtExport");
            updateCrsPtExport.getParamsMap().put("crsId", exportPTRVORowImpl.getCrsId());
            updateCrsPtExport.execute();
                
            } catch (IOException ex) {
            ex.printStackTrace();
            }
        }
            logger.info("End of GeneratePtExportByCrsBean:generateReport()");
            
        }else{
            ADFUtils.showFacesMessage("Please select atleast one row", FacesMessage.SEVERITY_INFO);
        }
    }
    
    public List exportPTPendingReport(ExportPTRVORowImpl exportPTRVORowImpl) {
        DCBindingContainer bc = ADFUtils.getDCBindingContainer();
        OperationBinding copyOper = bc.getOperationBinding("executeCrsExportPTCurrentByCrsId");
        copyOper.getParamsMap().put("crsId", exportPTRVORowImpl.getCrsId());
        copyOper.execute();
        
        
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
    //Creating styles code ends
    HSSFRow row = null;
    
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsExportPTCurrentExportIterator");
        ViewObject vo = itrBinding.getViewObject();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    
        Map params1 = new HashMap<String, Object>();
        params1.put("role", "CRS_TASL");
        params1.put("userName", exportPTRVORowImpl.getTaslName());
        
        Map params2 = new HashMap<String, Object>();
        params2.put("role", "CRS_BSL");
        params2.put("userName", exportPTRVORowImpl.getBslName());
        String taslName = "";
        String bslName = "";
        
        try {
            if(exportPTRVORowImpl.getTaslName() != null && !"".equalsIgnoreCase(exportPTRVORowImpl.getTaslName()))
            taslName = (String) ADFUtils.executeAction("findRoleDescription", params1);
            if(exportPTRVORowImpl.getBslName() != null && !"".equalsIgnoreCase(exportPTRVORowImpl.getBslName()))
            bslName = (String) ADFUtils.executeAction("findRoleDescription", params2);
        } catch (Exception e) {
        }
    while (vo.hasNext()) { 
    CrsExportPTCurrentVORowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.next();
            } else {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.first();
            }
    if(firstRow){
    row = sheet.createRow(idx); //creating 1st row
    row.createCell(0).setCellValue("CRS Name: "+exportPTRVORowImpl.getCrsId());
    row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("CRS ID: "+exportPTRVORowImpl.getCrsName());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        if(ADFUtils.evaluateEL("#{sessionScope.dictVersion}") != null)
        row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
        else
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
        row.createCell(0).setCellValue("State: "+exportPTRVORowImpl.getStateName());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("GPSL: "+bslName);
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("HPS: "+taslName);
        row.getCell(0).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("Designee: "+exportPTRVORowImpl.getDesigneeName());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        sheet.createRow(idx);
        idx = idx + 1;
        sheet.createRow(idx);
    
    idx = idx + 1;
    }
    row = sheet.createRow(idx); //creating 1st row
    firstRow = false;
    if(flag == false){
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
    row = sheet.createRow(idx); //creating 2nd row
    }
        
           
        if (viewObjectRow.getSafetyTopicOfInterest() != null){
            row.createCell(0).setCellValue(viewObjectRow.getSafetyTopicOfInterest().toString());
        }
        else
            row.createCell(0).setCellValue("");
        sheet.autoSizeColumn(0);

        if (viewObjectRow.getRiskPurposeList() != null){
            row.createCell(1).setCellValue(viewObjectRow.getRiskPurposeList().toString());
        }
        else
            row.createCell(1).setCellValue("");
        sheet.autoSizeColumn(1);

        if (viewObjectRow.getMeddraTerm() != null){
            row.createCell(2).setCellValue(viewObjectRow.getMeddraTerm());
        }
        else
            row.createCell(2).setCellValue("");
        sheet.autoSizeColumn(2);
        
        if (viewObjectRow.getPtName() != null){
            row.createCell(3).setCellValue(viewObjectRow.getPtName());
        }
        else
            row.createCell(3).setCellValue("");
        sheet.autoSizeColumn(3);
        
        if (viewObjectRow.getPtCode() != null){
            row.createCell(4).setCellValue(viewObjectRow.getPtCode());
        }
        else
            row.createCell(4).setCellValue("");
        sheet.autoSizeColumn(4);
        
        
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
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
    
    public DCBindingContainer getDCBindingContainer(){
        DCBindingContainer dcBindingContainer = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        return dcBindingContainer;
    }
    
    public void generatePtReportFlowTrue(){
        ADFUtils.setPageFlowScopeValue("generatePTFlow", true);
    }
    
    public void generatePtReportFlowFalse(){
        ADFUtils.setPageFlowScopeValue("generatePTFlow", false);
    }
    
    public void downloadPtReport(FacesContext facesContext,
                                         OutputStream outputStream) throws IOException {
        DCIteratorBinding iter = ADFUtils.findIterator("ExportPTRVOIterator");
        ExportPTRVORowImpl row = (ExportPTRVORowImpl)iter.getViewObject().getCurrentRow();
        Connection conn = null;
        InputStream inputStream = null;
        try {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/EcrsDS");
        conn = ds.getConnection();
        PreparedStatement ps;
        ps = conn.prepareStatement("select prop_value from CRS_PROPERTIES where Prop_name = 'REPORT_SOURCE'");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String outputFolder = rs.getString("prop_value");
            try {
                    ADFUtils.setPageFlowScopeValue("CrsId", row.getCrsId());
                    outputFolder = outputFolder.concat("\\PtExport\\"+row.getCrsId()+".xls");
                    File file = new File(outputFolder);
                    inputStream = new FileInputStream(file);
                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = inputStream.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                    }
                } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ps.close();
            
        } catch (InvalidFormatException invalidFormatException) {
            invalidFormatException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
    }
   
}
    public void prepareForDownloadAction(ActionEvent act) {
         DCIteratorBinding iter = ADFUtils.findIterator("ExportPTRVOIterator");
         ExportPTRVORowImpl row = (ExportPTRVORowImpl)iter.getViewObject().getCurrentRow();
         if(row == null){
           ADFUtils.showFacesMessage("Please select row to download report", FacesMessage.SEVERITY_INFO); 
         }else{
         Connection conn = null;
         InputStream inputStream = null;
         try {
         Context ctx = new InitialContext();
         DataSource ds = (DataSource) ctx.lookup("jdbc/EcrsDS");
         conn = ds.getConnection();
         PreparedStatement ps;
         ps = conn.prepareStatement("select prop_value from CRS_PROPERTIES where Prop_name = 'REPORT_SOURCE'");
         ResultSet rs = ps.executeQuery();
         while(rs.next()){
             String outputFolder = rs.getString("prop_value");
             try {
                     ADFUtils.setPageFlowScopeValue("CrsId", row.getCrsId());
                     outputFolder = outputFolder.concat("\\PtExport\\"+row.getCrsId()+".xls");
                     File file = new File(outputFolder);
                     inputStream = new FileInputStream(file);
                 
                     FacesContext context = FacesContext.getCurrentInstance();
                            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(),ExtendedRenderKitService.class);
                            erks.addScript(context, "customHandler();");
                 
                 } catch (FileNotFoundException e) {
                 ADFUtils.showFacesMessage("Report not generated for selected CRS", FacesMessage.SEVERITY_INFO); 
             }
         }
         ps.close();
             
         } catch (InvalidFormatException invalidFormatException) {
             invalidFormatException.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if(conn != null){
                 try {
                     conn.close();
                 } catch (SQLException e) {
                 }
             }
         }
     }

}
    public boolean getDisableGenerateButton(){
        DCIteratorBinding iter = ADFUtils.findIterator("ExportPTRVOIterator");
        Row filteredRows[] = iter.getViewObject().getFilteredRows("SelectRow", true);
        if(filteredRows != null && filteredRows.length > 0){
            return false;
        }else{
            return true;
        }
    }
}
