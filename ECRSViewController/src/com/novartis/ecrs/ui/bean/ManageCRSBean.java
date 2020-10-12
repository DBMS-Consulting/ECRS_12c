package com.novartis.ecrs.ui.bean;


import com.novartis.ecrs.model.constants.ModelConstants;
import com.novartis.ecrs.model.lov.UserRoleVORowImpl;
import com.novartis.ecrs.model.view.CRSVersionComparePendingViewRowImpl;
import com.novartis.ecrs.model.view.CRSVersionCompareVORowImpl;
import com.novartis.ecrs.model.view.CrsContentVORowImpl;
import com.novartis.ecrs.model.view.CrsExportPTCurrentVOImpl;
import com.novartis.ecrs.model.view.CrsExportPTCurrentVORowImpl;
import com.novartis.ecrs.model.view.CrsExportPTPendingImpl;
import com.novartis.ecrs.model.view.CrsExportPTPendingRowImpl;
import com.novartis.ecrs.model.view.CrsRiskRelationVORowImpl;
import com.novartis.ecrs.model.view.ExportPTRVORowImpl;
import com.novartis.ecrs.model.view.HierarchyChildVORowImpl;
import com.novartis.ecrs.model.view.base.CrsContentBaseVORowImpl;
import com.novartis.ecrs.model.view.report.PTReportVOImpl;
import com.novartis.ecrs.ui.constants.ViewConstants;
import com.novartis.ecrs.ui.utility.ADFUtils;
import com.novartis.ecrs.ui.utility.ExcelExportUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichDialog;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.data.RichTreeTable;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelBox;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;
import oracle.adf.view.rich.component.rich.layout.RichPanelLabelAndMessage;
import oracle.adf.view.rich.component.rich.layout.RichToolbar;
import oracle.adf.view.rich.component.rich.output.RichImage;
import oracle.adf.view.rich.component.rich.output.RichOutputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.datatransfer.DataFlavor;
import oracle.adf.view.rich.datatransfer.Transferable;
import oracle.adf.view.rich.dnd.DnDAction;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.event.DropEvent;
import oracle.adf.view.rich.event.PopupCanceledEvent;
import oracle.adf.view.rich.model.FilterableQueryDescriptor;
import oracle.adf.view.rich.util.ResetUtils;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.javatools.resourcebundle.BundleFactory;

import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.RowIterator;
import oracle.jbo.RowSetIterator;
import oracle.jbo.SortCriteria;
import oracle.jbo.ViewObject;
import oracle.jbo.server.ViewObjectImpl;
import oracle.jbo.uicli.binding.JUCtrlHierNodeBinding;

import oracle.security.crypto.util.InvalidFormatException;

import org.apache.log4j.Logger;
import org.apache.myfaces.trinidad.component.UIXCollection;
import org.apache.myfaces.trinidad.component.UIXEditableValue;
import org.apache.myfaces.trinidad.component.UIXSwitcher;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.event.SortEvent;
import org.apache.myfaces.trinidad.model.ChildPropertyTreeModel;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.model.RowKeySetTreeImpl;
import org.apache.myfaces.trinidad.model.SortCriterion;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;


public class ManageCRSBean implements Serializable {
    @SuppressWarnings("compatibility:7725300081501535999")
    private static final long serialVersionUID = 2040469805807166652L;
    private List<SelectItem> designeeList;
    private List<String> selDesigneeList;
    private String selectedCrsName;
    private transient RichPopup successPopupBinding;
    private transient RichPopup riskDefPopup;
    private transient RichTable riskDefTable;
    private transient RichPopup successPopup;
    private List<String> selDatabases;
    private List<SelectItem> databaseList;
    private List<String> selRiskPurposes;
    private List<String> ageSubGroup;
    private List<String> ageSubCurrent;
    private transient RichPopup reviewSubmitPopup;
    private transient RichSelectOneChoice crsStateSOC;
    private transient RichSelectOneChoice crsStatusSOC;
    private transient RichPopup crsApprovePopup;
    private transient RichPanelBox workflowPanelBox;
    private transient RichPopup crsRejectPopup;
    private transient RichInputText taslCommentsInputText;
    private transient RichInputText mlCommentsInputText;
    private String dictionary = ViewConstants.MEDDRA_DICTIONARY;
    private String level = ViewConstants.SOC;
    private String term;
    private transient RichPopup hierPopup;
    private boolean crsFieldsUpdatable;
    private transient RichPopup crsRetirePopup;
    private transient RichPopup crsReactivatePopup;
    private transient RichPopup crsReviewedPopup;
    private transient RichPopup meddraError;
    private transient RichPopup delConfPopupBinding;
    private transient RichPopup crsPublishPopupBinding;
    private transient RichPopup crsDemoteDraftPopupBinding;
    private List<SelectItem> filterItems;
    private List<SelectItem> meddraItems;
    private List<SelectItem> levelItems;
    private String contentId;
    private String childScope;
    private transient RichTreeTable childTreeTable;
    private transient RichPopup parentError;
    private String safetyTopicOfInterest;
    private transient RichPopup copyPopup;
    private transient RichPanelGroupLayout copyPanel;
    private transient RichPopup pendingPopup;
    private transient RichTable copyRiskDefTable;
    private transient RichPanelLabelAndMessage savedSuccessMessage;
    private transient RichPanelLabelAndMessage copySuccessMessage;
    private transient RichOutputText hiddenPopupAlign;
    private transient RichInputText stoiBinding;
    private transient RichSelectManyChoice copyDBListBinding;
    private transient RichSelectManyChoice copyRPListBinding;
    private transient UIXSwitcher searchSwitherBinding;
    private Boolean repoRefreshed;
    private String baseOrStaging=ModelConstants.BASE_FACET;
    private transient ChildPropertyTreeModel hierChildTreeModel;
    private transient List<HierarchyChildUIBean> hierChildList;
    private transient RichTable searchBaseTableBinding;
    private transient RichDialog reasonChangePopup;
    private transient RichPopup modifyReasonChngPopup;
    private transient RichInputText retireReactvteReasonPopup;
    private transient RichPopup reactivatePopupBinding;
    private transient RichPopup retirePopupBinding;
    private String reasonForChange;
    private transient RichPopup errorPLSqlPopup;
    private String filterBy1;
    private String filterBy2;
    private String filterBy3;
    private String filterValue1;
    private String filterValue2;
    private String filterValue3;
    private String filterCri1="OR";
    private String filterCri2="OR";
    private transient RichPopup advancedFilterPopup;
    private transient RichTable searchStagingTableBinding;
    private String currReleaseStatus = ModelConstants.STATUS_PENDING;
    private transient RichPopup publishPopupBinding;
    private transient RichPopup submitApprovalPopup;
    private Map<Integer,String> statesMap = null;
    private boolean nonCompoundSelected;
    private transient UIXSwitcher stateSwitcherBinding;
    public static final Logger logger = Logger.getLogger(ManageCRSBean.class);
    private transient RichPanelGroupLayout riskDefPopupPanel;
    private transient RichTable stagingTable;
    private transient ResourceBundle uiBundle =
        BundleFactory.getBundle("com.novartis.ecrs.view.ECRSViewControllerBundle");
    private transient RichPanelGroupLayout workflowPG;
    private transient RichPopup delSTIConfPopup;
    private transient RichSelectOneChoice socTermSOC;
   // private boolean socTermRequired = true;
    private boolean meddraSearch = false;
    
    private transient HierarchyChildUIBean root;
    private transient Enumeration rows;
    private transient HashMap <String , HierarchyChildUIBean> parentNodesByLevel;
    //private boolean searchCriteriaRequired = false;
    private transient RichInputText searchCriteriaDetails;
    private boolean routineRiskRelationCopied = false;
    
    private transient RichToolbar cntrlStatusBar;

    private transient  RichImage iconCRSChanged;
    private transient RichImage iconCRSSaveError;
    private transient RichImage iconCRSSaved;
    
    private Boolean currentUserInDesignee = Boolean.FALSE;
    private transient RichInputText searchCriteriaDetailsCopy;
    private transient RichSelectOneChoice socTermSOCCopy;
    
    private transient RichToolbar cntrlStatusBarCopy;
    private transient RichPanelGroupLayout copyRiskDefPopupPanel;
    private transient  RichImage iconCopyCRSChanged;
    private transient RichImage iconCopyCRSSaveError;
    private transient RichImage iconCopyCRSSaved;
    
    private transient RichPopup riskBasePopup;
    private transient RichPanelGroupLayout riskBasePopupPanel;
    private List<String> selRiskPurposesBase;
    private RichPopup cancelWarningPopup;
    private RichDialog riskDialog;
    private RichPopup versionComparePopup;
    private RichTable crsVersionsTable;
    private RichTable baseCrsVersionsTable;
    
    private Boolean crsVersionsCurrent;
    private Boolean crsVersionsPrevious;
    private Boolean baseCrsVersionsCurrent;
    private Boolean baseCrsVersionsPrevious;
    private String selectedCrsId;
    private String selectedState;
    private String selectedTASL;
    private String selectedDesignee;
    private String selectedStatus;
    private String selectedReleaseStatus;
    private String selectedBSL;
    private RichPopup ptExportPendingPopup;
    private RichPopup ptExportPendingDetailPopup;
    private Boolean showADR;
    private Boolean showMedicalHistory;
    private Boolean showCopyADR;
    private Boolean showCopyMedicalHistory;
    private Boolean showAdrTentative;
    private Boolean disableHeirarchyBtn;
    private RichSelectOneChoice crsDomainValue;
    private RichPopup riskDefOtherSelectionPopup;
    private RichSelectOneChoice copyRiskDefOthersPopup;
    private RichPopup copyRiskDefOtherSelectionPopup;
    private RichSelectManyChoice ageSubGroupComponent;
    private RichPopup ageGroupChangePopup;
    private RichPopup combinationNotAllowedPopup;
    private RichPopup maxThreeAllowedPopup;
    private RichTable crsRiskBaseTable;

    public void setSelectedCrsId(String selectedCrsId) {
        this.selectedCrsId = selectedCrsId;
    }

    public String getSelectedCrsId() {
        return selectedCrsId;
    }

    public void setSelectedState(String selectedState) {
        this.selectedState = selectedState;
    }

    public String getSelectedState() {
        return selectedState;
    }

    public void setSelectedTASL(String selectedTASL) {
        this.selectedTASL = selectedTASL;
    }

    public String getSelectedTASL() {
        return selectedTASL;
    }

    public void setSelectedDesignee(String selectedDesignee) {
        this.selectedDesignee = selectedDesignee;
    }

    public String getSelectedDesignee() {
        return selectedDesignee;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedReleaseStatus(String selectedReleaseStatus) {
        this.selectedReleaseStatus = selectedReleaseStatus;
    }

    public String getSelectedReleaseStatus() {
        return selectedReleaseStatus;
    }

    public void setSelectedBSL(String selectedBSL) {
        this.selectedBSL = selectedBSL;
    }

    public String getSelectedBSL() {
        return selectedBSL;
    }

    public ManageCRSBean() {
        super();
        getUserRole();
        getCrsFlowType();
        // save flow type to session - CompoundVO uses flowType as bind param
        ADFUtils.setSessionScopeValue("flowType", flowType);
        if (ViewConstants.FLOW_TYPE_CREATE.equals(flowType) ||
            ViewConstants.FLOW_TYPE_UPDATE.equals(flowType)) {
            setBaseOrStaging(ModelConstants.STAGING_FACET);
        } else
            setBaseOrStaging(ModelConstants.BASE_FACET);
    }
    
    private String flowType;
    private boolean inboxDisable;
    private String loggedInUserRole;
    private String userName;
    
    /**
     * Set the flowType to bean variable and initailize the bsl facet name with 
     * appropriate value.
     */
    public void getCrsFlowType() {
        // Add event code here...
        if (ADFUtils.evaluateEL("#{pageFlowScope.flowType}") != null) {
            flowType =
                    (String)ADFUtils.evaluateEL("#{pageFlowScope.flowType}");
        }
    }

    /**
     * @param flowType
     */
    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    /**
     * @return
     */
    public String getFlowType() {
        return flowType;
    }

    /**
     * Invoke commit operation of DB.
     * @param actionEvent
     */
    public void onClickCreateSave(ActionEvent actionEvent) {
        //Code to copy routine risk relations on creation of compound crs
        if (ViewConstants.FLOW_TYPE_CREATE.equalsIgnoreCase(this.flowType) && !this.isRoutineRiskRelationCopied()){
            DCBindingContainer bc = ADFUtils.getDCBindingContainer();
            DCIteratorBinding iter = bc.findIteratorBinding("CrsContentVOIterator");
            logger.info("--After createInsert--");
            if (iter != null && iter.getCurrentRow() != null){
                CrsContentVORowImpl row = (CrsContentVORowImpl)iter.getCurrentRow();
                if (row.getCompoundType() != null &&
                        ModelConstants.COMPOUND_TYPE_COMPOUND.equalsIgnoreCase(row.getCompoundType())) {
                        logger.info("--Before  copyRoutineDefinition--");
                        OperationBinding copyOper = bc.getOperationBinding("copyRoutineDefinition");
                        copyOper.getParamsMap().put("crsId", row.getCrsId());
                        copyOper.execute();
                        this.setRoutineRiskRelationCopied(true);
                        logger.info("--After-ManageCRSBean:onClickCreateSave--");
                }
            }
        }
        
        if(selDesigneeList != null && selDesigneeList.size() > 0){
            String designees = "";
            for(String des : selDesigneeList){
                designees = designees + "," + des;
            }
            ADFUtils.setEL("#{bindings.Designee.inputValue}", designees.substring(1));
        } else
            ADFUtils.setEL("#{bindings.Designee.inputValue}",null);
//        ADFUtils.setEL("#{bindings.CrsName.inputValue}", "ROUTINE");
        OperationBinding oper = ADFUtils.findOperation("Commit");
        oper.execute();
        if (oper.getErrors().size() > 0)
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
        else {
//            String flowType = (String)ADFUtils.evaluateEL("#{pageFlowScope.flowType}");
//            if (flowType != null && "C".equalsIgnoreCase(flowType)) {
//                Long crsId = (Long)ADFUtils.evaluateEL("#{bindings.CrsId.inputValue}");
//                OperationBinding copyOper = ADFUtils.findOperation("copyRoutineDefinition");
//                copyOper.getParamsMap().put("crsId", crsId);
//                copyOper.execute();
//                if (copyOper.getErrors().size() > 0)
//                    ADFUtils.showFacesMessage("An internal error has occured. Please try later.",
//                                              FacesMessage.SEVERITY_ERROR);
//                else {
//                    ADFUtils.showPopup(getSuccessPopupBinding());
//                    ADFUtils.addPartialTarget(getWorkflowPanelBox());
//                }
//            } else {
                 ADFUtils.showPopup(getSuccessPopupBinding());
               // ADFUtils.addPartialTarget(getWorkflowPanelBox());
//            }
        }
    }

    /**
     * Creates a row in CRS_CONTENT table.
     */
    public void createCrsRow() {
        // Add event code here...
        logger.info("--Start-ManageCRSBean:createCrsRow--");
        DCBindingContainer bc =  ADFUtils.findBindingContainerByName("com_novartis_ecrs_view_createCRSPageDef");
        OperationBinding ob =  bc.getOperationBinding("CreateInsert");
        ob.execute();
        DCIteratorBinding iter = bc.findIteratorBinding("CrsContentVOIterator");
        logger.info("--After createInsert--");
        if (iter != null && iter.getCurrentRow() != null){
            CrsContentVORowImpl row = (CrsContentVORowImpl)iter.getCurrentRow();
            row.setBslName(ADFContext.getCurrent().getSecurityContext().getUserName().toUpperCase());
            row.setStateId(ModelConstants.STATE_DRAFT);
            row.setReviewApproveRequiredFlag(ModelConstants.REVIEW_REQD_YES);
            row.setReleaseStatusFlag(ModelConstants.STATUS_PENDING);
            row.setCrsEffectiveDt(ADFUtils.getJBOTimeStamp());
        }
        this.setCurrReleaseStatus(ModelConstants.STATUS_PENDING);
        logger.info("--End-ManageCRSBean:createCrsRow--");
    }

    /**
     * @param designeeList
     */
    public void setDesigneeList(List<SelectItem> designeeList) {
        this.designeeList = designeeList;
    }

    /**
     * @return
     */
    public List<SelectItem> getDesigneeList() {
        if(designeeList == null){
            designeeList = new ArrayList<SelectItem>();
            DCBindingContainer bc = ADFUtils.getDCBindingContainer();
            OperationBinding ob = bc.getOperationBinding("fetchDesignees");
            List<UserRoleVORowImpl> designees = (List<UserRoleVORowImpl>)ob.execute();
            if(designees != null && designees.size() > 0){
                for(UserRoleVORowImpl designee : designees){
                    SelectItem item = new SelectItem(designee.getUserName(), designee.getFullName());
                    designeeList.add(item);
                }
            }
        }
        return designeeList;
    }

    /**
     * @param selDesigneeList
     */
    public void setSelDesigneeList(List<String> selDesigneeList) {
        this.selDesigneeList = selDesigneeList;
    }

    /**
     * @return
     */
    public List<String> getSelDesigneeList() {
        return selDesigneeList;
    }

    /**
     * Invokes AMImpl method to filter CRSContent vo with entered search criteria.
     * @param actionEvent
     */
    public void onClickSearch(ActionEvent actionEvent) {
        logger.info("--Start-ManageCRSBean:onClickSearch--");
        
        DCBindingContainer bc =
            ADFUtils.findBindingContainerByName(ViewConstants.PAGE_DEF_SEARCH);
        
        //set release staus to binding
        String releaseStatus = "";
        if ("anonymous".equalsIgnoreCase(userName)){
            releaseStatus = ModelConstants.STATUS_CURRENT;
            this.setCurrReleaseStatus(releaseStatus);
        } else if (ViewConstants.FLOW_TYPE_UPDATE.equals(getFlowType()) &&
                 (ModelConstants.ROLE_ML.equals(loggedInUserRole) ||
                  ModelConstants.ROLE_MQM.equals(loggedInUserRole) ||
                  ModelConstants.ROLE_TASL.equals(loggedInUserRole))){
            releaseStatus = ModelConstants.STATUS_PENDING;
        } else {
            releaseStatus = getCurrReleaseStatus();
        }
        
        //set releaseStatus variable to current row attribute
        ADFUtils.setEL("#{bindings.ReleaseStatus.inputValue}",
                       releaseStatus);
        
        //If mode is B&S ,and release status is 'CURRENT' set state to activated 
        if (ViewConstants.ANONYMOUS_ROLE.equalsIgnoreCase(userName)) {
            ADFUtils.setEL("#{bindings.State.inputValue}",
                           ModelConstants.STATE_ACTIVATED);
        }

        //if mode is U and logged in is BSL, set Compound Type to COMPOUND
        if (ViewConstants.FLOW_TYPE_UPDATE.equals(flowType)
            && ModelConstants.ROLE_BSL.equals(loggedInUserRole)) {
            ADFUtils.setEL("#{bindings.CompoundType.inputValue}", ModelConstants.COMPOUND_TYPE_COMPOUND);
        }
        
        
        OperationBinding ob = bc.getOperationBinding("filterCRSContent");
        ob.getParamsMap().put("userInRole", loggedInUserRole);
        ob.getParamsMap().put("userName", getUserName());
        ob.getParamsMap().put("isInboxDisable", isInboxDisable());
        ob.getParamsMap().put("flowType", getFlowType());
        ob.execute();
        
        if (ModelConstants.STATUS_PENDING.equals(ADFUtils.evaluateEL("#{bindings.ReleaseStatus.inputValue}"))) {
            setBaseOrStaging(ModelConstants.STAGING_FACET);
        } else{
            setBaseOrStaging(ModelConstants.BASE_FACET);
            getSearchBaseTableBinding().resetStampState();
            ADFUtils.addPartialTarget(getSearchBaseTableBinding());
        }
        if(getSearchSwitherBinding() != null){
        ADFUtils.addPartialTarget(getSearchSwitherBinding());
        }
        if (ob.getErrors().size() > 0)
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"),
                                      FacesMessage.SEVERITY_ERROR);
        //TODO log the error 
        logger.info("--End-ManageCRSBean:onClickSearch--");
    }

    /**
     * Invokes execute empty row set on crs content vo.
     */
    public void invokeEmptyRowSetOnContentVO() {
        logger.info("--Start-ManageCRSBean:invokeEmptyRowSetOnContentVO--");
        DCBindingContainer bc =
            ADFUtils.findBindingContainerByName(ViewConstants.PAGE_DEF_SEARCH);
        DCIteratorBinding searchIter =  bc.findIteratorBinding("ECrsSearchVOIterator");
        if(searchIter.getEstimatedRowCount() == 0){
            Row row = searchIter.getViewObject().createRow();
            searchIter.getViewObject().insertRow(row);
            searchIter.getViewObject().setCurrentRow(row);
        }
        //Mode is Update and role is BSL,settting CompoundType to COMPOUND
        if (searchIter != null && searchIter.getCurrentRow() != null &&
            ViewConstants.FLOW_TYPE_UPDATE.equals(getFlowType()) &&
            ModelConstants.ROLE_BSL.equals(getLoggedInUserRole())) {
            searchIter.getCurrentRow().setAttribute("CompoundType",
                                                    ModelConstants.COMPOUND_TYPE_COMPOUND);
            logger.info("Update mode and role is BSL,settting CompoundType to COMPOUND");
        }
        DCIteratorBinding iter = bc.findIteratorBinding("CrsContentVOIterator");
        if (iter.getViewObject() != null)
            iter.getViewObject().executeEmptyRowSet();
        DCIteratorBinding baseIter = bc.findIteratorBinding("CrsContentBaseVOIterator");
        if (baseIter.getViewObject() != null)
            baseIter.getViewObject().executeEmptyRowSet();
        logger.info("--End-ManageCRSBean:invokeEmptyRowSetOnContentVO--");
    }

    /**
     * Custom selection listener to populate crs name and designee list.
     * @param selectionEvent
     */
    public void searchTableSelectionListener(SelectionEvent selectionEvent) {
        logger.info("--Start-ManageCRSBean:searchTableSelectionListener--");
        ADFUtils.invokeEL("#{bindings.CrsContentVO.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},
                                 new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        CrsContentVORowImpl selectedRow =
                   (CrsContentVORowImpl)ADFUtils.evaluateEL("#{bindings.CrsContentVOIterator.currentRow}");
        setSelectedCrsName(selectedRow.getCrsName());
        ADFUtils.setPageFlowScopeValue("crsId", selectedRow.getCrsId());
        ADFUtils.setPageFlowScopeValue("crsPendingPublished", selectedRow.getCrsPendingPublished());
        this.setSelectedCrsId(selectedRow.getCrsId().toString());
        Map params = new HashMap<String, Object>();
        params.put("stateId", selectedRow.getStateId());
        
        Map params1 = new HashMap<String, Object>();
        params1.put("role", "CRS_TASL");
        params1.put("userName", selectedRow.getTaslName());
        
        Map params2 = new HashMap<String, Object>();
        params2.put("role", "CRS_BSL");
        params2.put("userName", selectedRow.getBslName());
        String state = "";
        String taslName = "";
        String bslName = "";
        try {
            if(selectedRow.getStateId() != null)
            state = (String) ADFUtils.executeAction("findStateDescription", params);
            if(selectedRow.getTaslName() != null && !"".equalsIgnoreCase(selectedRow.getTaslName()))
            taslName = (String) ADFUtils.executeAction("findRoleDescription", params1);
            if(selectedRow.getBslName() != null && !"".equalsIgnoreCase(selectedRow.getBslName()))
            bslName = (String) ADFUtils.executeAction("findRoleDescription", params2);
        } catch (Exception e) {
        }
        this.setSelectedState(state);
        this.setSelectedTASL(taslName);
        if(selectedRow.getDesigneeName() != null && !"".equalsIgnoreCase(selectedRow.getDesigneeName())){
        this.setSelectedDesignee(selectedRow.getDesigneeName());
        }else{
            this.setSelectedDesignee("");   
        }
        this.setSelectedStatus("");
        this.setSelectedBSL(bslName);
        setSelDesigneeList(null);
        List<String> designeeList = new ArrayList<String>();
        if (selectedRow.getDesignee() != null) {
            String[] designeeArray = selectedRow.getDesignee().split("[,]");
            if (designeeArray.length > 0) {
                for (int i = 0; i < designeeArray.length; i++) {
                    designeeList.add(designeeArray[i]);
                }
            }
            setSelDesigneeList(designeeList);
        }
//        if (ModelConstants.COMPOUND_TYPE_NON_COMPOUND.equals(selectedRow.getCrsCompoundType())) {
//            setNonCompoundSelected(Boolean.TRUE);
//        } else
//            setNonCompoundSelected(Boolean.FALSE);
        logger.info("--End-ManageCRSBean:searchTableSelectionListener--");
    }

    /**
     * @param selectedCrsName
     */
    public void setSelectedCrsName(String selectedCrsName) {
        this.selectedCrsName = selectedCrsName;
    }

    /**
     * @return
     */
    public String getSelectedCrsName() {
        return selectedCrsName;
    }

    /**
     * @return
     */
    public String onClickNext() {
        logger.info("Navigating to next train stop");
        String returnValue = (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getNext}");
        return returnValue;
    }
    
    public void initRisRel(){
            String crsName = (String)ADFUtils.evaluateEL("#{bindings.CrsName.inputValue}");
            Long crsId = (Long)ADFUtils.evaluateEL("#{bindings.CrsId.inputValue}");
            if(crsId == null){
                crsName = (String)ADFUtils.evaluateEL("#{bindings.CrsNameBase.inputValue}");
                crsId = (Long)ADFUtils.evaluateEL("#{bindings.CrsIdBase.inputValue}");
            }
            ADFUtils.setPageFlowScopeValue("crsId", crsId);
            ADFUtils.setPageFlowScopeValue("crsName", crsName);
            Map params = new HashMap<String, Object>();
            params.put("crsId", crsId);
            params.put("status", getBaseOrStaging());
            logger.info("Init risk Relation : current Crs ID :: "+crsId);
            logger.info("Init risk Relation : Base or Staging :: "+getBaseOrStaging());
            try {
                logger.info("Calling AM method initRiskRelation");
                ADFUtils.executeAction("initRiskRelation", params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setRepoRefreshed(Boolean.FALSE);
        
    }

    public void onSelectInbox(ValueChangeEvent vce) {
        // Add event code here...
        if (vce!=null) {
            if (vce.getNewValue() != null &&
                !vce.getNewValue().equals(vce.getOldValue()) &&
                (Boolean)vce.getNewValue()) {               
                    setInboxDisable(Boolean.TRUE);                
            } else
                setInboxDisable(Boolean.FALSE);

            ADFUtils.addPartialTarget(vce.getComponent().getParent().getParent());
        }
    }

    /**
     * @param inboxDisable
     */
    public void setInboxDisable(boolean inboxDisable) {
        this.inboxDisable = inboxDisable;
    }

    /**
     * @return
     */
    public boolean isInboxDisable() {
        return inboxDisable;
    }

    /**
     * @param loggedInUserRole
     */
    public void setLoggedInUserRole(String loggedInUserRole) {
        this.loggedInUserRole = loggedInUserRole;
    }

    /**
     * @return
     */
    public String getLoggedInUserRole() {
        return loggedInUserRole;
    }

    /**
     * Set null to trade,generic,indication and isMarketed attributes.
     * @param vce
     */
    public void onCompCodeSelect(ValueChangeEvent vce) {
        logger.info("Start-ManageCRSBean:onCompCodeSelect()");
        if (vce != null) {
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            Long crsId = (Long)ADFUtils.evaluateEL("#{bindings.CrsId.inputValue}");
            setNonCompoundSelected(Boolean.FALSE);
            if (vce.getNewValue() != null && !vce.getNewValue().equals(vce.getOldValue()) &&
                ModelConstants.COMPOUND_TYPE_NON_COMPOUND.equals(ADFUtils.evaluateEL("#{bindings.CompoundType.inputValue}"))) {
                ADFUtils.setEL("#{bindings.TradeName.inputValue}", null);
                ADFUtils.setEL("#{bindings.GenericName.inputValue}", null);
                ADFUtils.setEL("#{bindings.Indication.inputValue}", null);
                //TODO make this enable when isMarketedFlag null
                ADFUtils.setEL("#{bindings.IsMarketedFlag.inputValue}", "N");
                ADFUtils.setEL("#{bindings.BslName.inputValue}", null);
                ADFUtils.setEL("#{bindings.TaslName.inputValue}", null);
//                ADFUtils.setEL("#{bindings.MedicalLeadName.inputValue}", null);
                ADFUtils.setEL("#{bindings.ReviewApproveRequiredFlag1.inputValue}",
                               "N");
                ADFUtils.setEL("#{bindings.ReviewApproveRequiredFlag.inputValue}",
                               "N");
//                ADFUtils.setEL("#{bindings.MedicalLeadName.inputValue}", null);
                setSelDesigneeList(null);
                setNonCompoundSelected(Boolean.TRUE);
                logger.info("--------------Selected non compound value----------");
            }
            String crsCompCode = (String)ADFUtils.evaluateEL("#{bindings.CrsCompoundCode.inputValue}");
            String compCode = (String)ADFUtils.evaluateEL("#{bindings.CompoundCode.inputValue}");
            String indication = (String)ADFUtils.evaluateEL("#{bindings.Indication.inputValue}");
            ADFUtils.setEL("#{bindings.CrsName.inputValue}",
                           (compCode != null ? compCode : crsCompCode) + (indication != null ? (" "+indication) : ""));
            resetCrsName(indication, compCode, crsCompCode);
            //ResetUtils.reset(vce.getComponent().getParent());
            ADFUtils.addPartialTarget(vce.getComponent().getParent());
        }
        logger.info("End-ManageCRSBean:onCompCodeSelect()");
    }

    /**
     * @param successPopupBinding
     */
    public void setSuccessPopupBinding(RichPopup successPopupBinding) {
        this.successPopupBinding = successPopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getSuccessPopupBinding() {
        return successPopupBinding;
    }

    /**
     * Invoked from constructor and intializes the userRole variable.
     */
    private void getUserRole() {
        if (ADFUtils.evaluateEL("#{sessionBean.userRole}") != null) {
            loggedInUserRole =
                    (String)ADFUtils.evaluateEL("#{sessionBean.userRole}");
            logger.info("loggedInUser role----------"+loggedInUserRole);
        }
        if (ADFUtils.evaluateEL("#{securityContext.userName}") != null) {
            setUserName(ADFUtils.evaluateEL("#{securityContext.userName}").toString().toUpperCase());
            logger.info("user name from security context-------" +
                        ADFUtils.evaluateEL("#{securityContext.userName}"));
        }
    }

    public void addRiskDefinition(ActionEvent actionEvent) {
        try {
       // String isMedraDictExists = (String) ADFUtils.executeAction("executeMedraExistsQuery", null);
        
        DCIteratorBinding realtionIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        ViewObject relationVO = realtionIter.getViewObject();
        Row relationRow = relationVO.createRow();
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        logger.info("AddRiskDefinition crsId "+crsId);
        relationRow.setAttribute("CrsId", crsId);
////            if("Y".equalsIgnoreCase(isMedraDictExists)){
////             relationRow.setAttribute("VDomainOther", "OTHER");   
////            }else{
//             relationRow.setAttribute("VDomainOther", "OTHER1");  
//           // }
        relationVO.insertRow(relationRow);
        relationVO.setCurrentRow(relationRow);
        logger.info("Popup mode is add, opens blank risk defintion popup.");
        ADFUtils.setPageFlowScopeValue("popupMode", "Add");
        setSelDatabases(null);
        setSelRiskPurposes(null);
        if(savedSuccessMessage != null){
            savedSuccessMessage.setVisible(Boolean.FALSE);
            ResetUtils.reset(savedSuccessMessage);
        }
        if (null != cntrlStatusBarCopy){
            cntrlStatusBarCopy.setRendered(false);
        }
        showStatus(ViewConstants.CRS_MODIFIED);
        if(riskDefPopupPanel != null)
            ResetUtils.reset(riskDefPopupPanel);
        if(copyRiskDefPopupPanel != null)
            ResetUtils.reset(copyRiskDefPopupPanel);
        ADFUtils.showPopup(riskDefPopup);
        } catch (Exception e) {
        }
    }

    public void setRiskDefPopup(RichPopup riskDefPopup) {
        this.riskDefPopup = riskDefPopup;
    }

    public RichPopup getRiskDefPopup() {
        return riskDefPopup;
    }

    public void editRiskDefinition(ActionEvent actionEvent) {
        //Added because, when coming from copy current flow, the new ID is not there in the EO and giving error while setting current row.
        DCIteratorBinding relationIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        relationIter.getViewObject().clearCache();
        relationIter.executeQuery();
        
        Long riskId = (Long)ADFUtils.evaluateEL("#{row.CrsRiskId}");
        relationIter.setCurrentRowWithKey((new Key(new Object[]{riskId})).toStringFormat(true));
        CrsRiskRelationVORowImpl relationRow = (CrsRiskRelationVORowImpl)relationIter.getCurrentRow();
        
         relationRow.getCrsRiskDefinitionsVO().reset();
         
        logger.info("Editing Risk definition, popup mode edit.");
        ADFUtils.setPageFlowScopeValue("popupMode", "Edit");
        //Dileep: commented below and added above
        //Long riskId = (Long)ADFUtils.evaluateEL("#{row.CrsRiskId}");
        ADFUtils.setPageFlowScopeValue("crsRiskId", riskId);
        String dataDomain = (String)ADFUtils.evaluateEL("#{row.DataDomain}");
        Integer domainId = 1;
        if (null != dataDomain){
            Map params2 = new HashMap<String, Object>();
            params2.put("domainName", dataDomain);
            try {
               domainId = (Integer) ADFUtils.executeAction("fetchDomainIdFromName", params2);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        if (null != domainId && domainId.intValue() != 1){
            ADFUtils.setEL("#{bindings.DomainId.inputValue}", domainId);
        }
        relationRow.setDomainId(new Integer(domainId));
        logger.info("Current crs risk id "+riskId);
//        String databaseList = (String)ADFUtils.evaluateEL("#{row.DatabaseList}");
//        List<String> dbList = new ArrayList<String>();
//        if(databaseList != null){
//            String split[] = databaseList.split(",");
//            for(String db : split){
//                dbList.add(db);
//            }
//        }
//        setSelDatabases(dbList);
        String riskPurposeList = (String)ADFUtils.evaluateEL("#{row.RiskPurposeList}");
        List<String> rpList = new ArrayList<String>();
        if(riskPurposeList != null){
            if(riskPurposeList.endsWith(",")){
                riskPurposeList = riskPurposeList.substring(0, riskPurposeList.length()-1);
            }
            String split[] = riskPurposeList.split(",");
            for(String rp : split){
                rpList.add(rp);
            }
        }
        logger.info("Selected risk purpose list :: "+rpList);
        setSelRiskPurposes(rpList);
        
        Map params = new HashMap<String, Object>();
        params.put("rowKey", riskId);
        try {
            ADFUtils.executeAction("setCurrentRiskRelation", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(riskDefPopupPanel != null)
            ResetUtils.reset(riskDefPopupPanel);
        if(savedSuccessMessage != null){
            savedSuccessMessage.setVisible(Boolean.FALSE);
            ResetUtils.reset(savedSuccessMessage);
        }
        if(copyRiskDefPopupPanel != null)
            ResetUtils.reset(copyRiskDefPopupPanel);
        if (null != cntrlStatusBarCopy){
            this.iconCopyCRSChanged.setVisible(false);
            this.iconCopyCRSSaved.setVisible(false);
            this.iconCopyCRSSaveError.setVisible(false);
            cntrlStatusBarCopy.setRendered(false);
        }
        if (null != cntrlStatusBar){
            this.iconCRSChanged.setVisible(false);
            this.iconCRSSaved.setVisible(false);
            this.iconCRSSaveError.setVisible(false);
            ADFUtils.addPartialTarget(cntrlStatusBar);
        }
        ViewObject riskDefVO = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator").getViewObject();
        RowSetIterator rs = riskDefVO.createRowSetIterator(null);
        if(!rs.hasNext()){
            System.err.println(riskDefVO.getQuery());
            riskDefVO.executeQuery();
        }
        while(rs.hasNext()){
            Row row = rs.next();
            if(row != null){
                row.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
                logger.info("Closing CrsRisk Popup -- refresh risk def row.");
            }
        }
        this.setAgeSubGroup(null);
        ADFUtils.showPopup(riskDefPopup);
        System.err.println("NITISH 2 :: "+relationRow.getCrsRiskDefinitionsVO().getRowCount());
    }

    public void deleteRiskDefinitions() {
        logger.info("Deleting Selected Risk Definitions");
        DCIteratorBinding riskDefIter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = riskDefIter.getViewObject();
        Row[] rows= riskDefVO.getFilteredRows("SelectAttr", Boolean.TRUE);
        for(Row row : rows){
            row.remove();
        }
        showStatus(ViewConstants.CRS_MODIFIED);
//        RowKeySet rowKeySet = (RowKeySet)riskDefTable.getSelectedRowKeys();
//        CollectionModel cm = (CollectionModel)riskDefTable.getValue();
//        for (Object facesTreeRowKey : rowKeySet) {
//            cm.setRowKey(facesTreeRowKey);
//            JUCtrlHierNodeBinding rowData = (JUCtrlHierNodeBinding)cm.getRowData();
//            rowData.getRow().remove();
//        }
    }

    public void setRiskDefTable(RichTable riskDefTable) {
        this.riskDefTable = riskDefTable;
    }

    public RichTable getRiskDefTable() {
        return riskDefTable;
    }

    public void saveRiskDefs(ActionEvent actionEvent) {
        System.out.println(ADFUtils.evaluateEL("#{pageFlowScope.ageSubGroups}"));
        List<String> riskPursposeList = this.getSelRiskPurposes();
        AttributeBinding attr = (AttributeBinding)getBindings().getControlBinding("Adr"); 
        String adrValue =(String)attr.getInputValue();
        //String adrValue = (String)ADFUtils.invokeEL("#{bindings.Adr.inputValue}");
        if((riskPursposeList != null) && (riskPursposeList.contains("CD")) && ((adrValue == null) || ("".equalsIgnoreCase(adrValue)))){
        ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Please select ADR value");
        return;
        }
        ADFUtils.setEL("#{bindings.RiskPurposeList.inputValue}",null);
        
        String baseOrStaging = (String)ADFUtils.evaluateEL("#{pageFlowScope.manageCRSBean.baseOrStaging}");
        Row currRow = null;
        String key = null;
        if(baseOrStaging != null && "BASE".equalsIgnoreCase(baseOrStaging)){
            DCIteratorBinding riskBaseIter = ADFUtils.findIterator("CrsRiskBaseVOIterator");
            currRow = riskBaseIter.getCurrentRow();
            key = riskBaseIter.getCurrentRowKeyString();
        }
        else{
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsRiskVOIterator");
            currRow = riskIter.getCurrentRow();
            key = riskIter.getCurrentRowKeyString();
        }
        DCIteratorBinding riskRelIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        if(null != riskRelIter){
            Row relationRow = riskRelIter.getCurrentRow();
            if (null != relationRow){
                String safetyTopic = (String) relationRow.getAttribute("SafetyTopicOfInterest");
                if(safetyTopic != null && safetyTopic.contains("'")){
                    String[] str = safetyTopic.split("'");
                    safetyTopic = str[0]+ "''" + str[1];
                }
                if(safetyTopic == null || "".equals(safetyTopic)){
                    ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, uiBundle.getString("STOI_MANDATE_ERROR"));
                    return;
                }
                String riskPurposes = "";
                if(selRiskPurposes != null && selRiskPurposes.size() > 0){
//                    if(selRiskPurposes.contains("A2") && (selRiskPurposes.contains("RM") || selRiskPurposes.contains("PS"))){
//                        ADFUtils.showFacesMessage("If A2 is selected,  RM or PS cannot also be selected.  Please ensure that only A2 is selected or removed.", FacesMessage.SEVERITY_ERROR);
//                        return;
//                    }
                    for(String riskPurpose : selRiskPurposes){
                        riskPurposes = riskPurposes + "," + riskPurpose;
                    }
                    //ADFUtils.setEL("#{bindings.RiskPurposeList.inputValue}", riskPurposes.substring(1));
                    relationRow.setAttribute("RiskPurposeList", riskPurposes.substring(1));
                } else{
                    ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Please select at least one Risk Purpose.");
                    ADFUtils.setEL("#{bindings.RiskPurposeList.inputValue}",null);
                    return;
                }
                Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");  
                String riskPurposeList = riskPurposes.substring(1);
                //String safetyTopic = (String)ADFUtils.evaluateEL("#{bindings.SafetyTopicOfInterest.inputValue}");
                
                //Integer domain = (Integer)ADFUtils.evaluateEL("#{bindings.DomainId.inputValue}");
                Integer domain = (Integer) relationRow.getAttribute("DomainId");
                String soc = (String) relationRow.getAttribute("SocTerm");
                String searchAppliedTo = (String) relationRow.getAttribute("SearchAppliedTo");
                if(domain == null){
                    ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, uiBundle.getString("DATA_DOMAIN_MANDATE_ERROR"));
                    return;
                } else if(domain != null && (domain.intValue() == 1)){
                    //String soc = (String) ADFUtils.evaluateEL("#{bindings.SocTerm.inputValue}");
                    if(soc == null || "".equals(soc)){
                        ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, uiBundle.getString("SOC_MANDATE_ERROR"));
                        return;
                    }
                    DCIteratorBinding iter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
                    ViewObject riskDefVO = iter.getViewObject();
                    if(riskDefVO.getEstimatedRowCount() == 0){
                        ADFUtils.showFacesMessage(uiBundle.getString("MEDDRA_MANDATE_ERROR"), FacesMessage.SEVERITY_ERROR);
                        return;
                    }
                }
               
                String searchCriteriaDetails = (String) relationRow.getAttribute("SearchCriteriaDetails");
                String genderCode = (String) relationRow.getAttribute("GenderCode");
                BigDecimal crsAgeGrpId = (BigDecimal) relationRow.getAttribute("CrsAgeGrpId");
                if (domain != null && domain.intValue() != 1 && "A".equalsIgnoreCase(genderCode) && crsAgeGrpId.compareTo(new BigDecimal(1)) == 0){
                   // ADFUtils.setEL("#{bindings.SocTerm.inputValue}", null);
                   relationRow.setAttribute("SocTerm" , null); 
                   // String searchCriteriaDetails = (String)ADFUtils.evaluateEL("#{bindings.SearchCriteriaDetails.inputValue}");
                    if(searchCriteriaDetails == null || "".equals(searchCriteriaDetails)){
                        ADFUtils.addMessage(FacesMessage.SEVERITY_ERROR, uiBundle.getString("SCD_MANDATE_ERROR"));
                        return;
                    }
                }
                
                Long crsRiskId = (Long) relationRow.getAttribute("CrsRiskId");
                
                logger.info("crsId : "+selRiskPurposes);
                logger.info("crsRiskId : "+ crsRiskId);
                logger.info("Selected risk purposes : "+selRiskPurposes);
                logger.info("safetyTopic  :: " + safetyTopic);
                logger.info("Domain selected :: " + domain);
                logger.info("searchCriteriaDetails :: " + searchCriteriaDetails);
                logger.info("searchAppliedTo :: " + searchAppliedTo);
                logger.info("Saving risk defs.");
                
                Map params1 = new HashMap<String, Object>();
                params1.put("crsId", crsId);
                params1.put("safetyTopic", safetyTopic);
                params1.put("rpList", riskPurposeList);
                params1.put("crsRiskId", crsRiskId);
                params1.put("domainId", domain);
                params1.put("socTerm", searchAppliedTo);
                try {
                    logger.info("Calling model method validateSafetyTopic");
                    Boolean invalid = (Boolean)ADFUtils.executeAction("validateSafetyTopic", params1);
                    if(invalid){
                        ADFUtils.showFacesMessage(uiBundle.getString("STOI_UNIQUE_ERROR"), FacesMessage.SEVERITY_ERROR);
                        return;
                    }
                } catch (Exception e) {
                    logger.error("Exception occured in validateSafetyTopic()"+e);
                }
                // Save desingee if not saved the CRS in details tab on creating new CRS
                if (!isRoutineRiskRelationCopied() && null != this.flowType && ViewConstants.FLOW_TYPE_CREATE.equalsIgnoreCase(this.flowType)){
                    if (selDesigneeList != null && selDesigneeList.size() > 0){
                            String designees = "";
                            for(String des : selDesigneeList){
                                designees = designees + "," + des;
                            }
                            ADFUtils.setEL("#{bindings.Designee.inputValue}", designees.substring(1));
                    }
                }
                OperationBinding oper = ADFUtils.findOperation("Commit");
                oper.execute();
                if (oper.getErrors().size() > 0) {
                    ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
                    if(savedSuccessMessage != null){
                        savedSuccessMessage.setVisible(Boolean.FALSE);
                        ADFUtils.addPartialTarget(savedSuccessMessage);
                        ResetUtils.reset(savedSuccessMessage);
                    }
                    if(copySuccessMessage != null){
                        copySuccessMessage.setVisible(Boolean.FALSE);
                        ADFUtils.addPartialTarget(copySuccessMessage);
                        ResetUtils.reset(savedSuccessMessage);
                    }
                    showStatus(ViewConstants.CRS_SAVE_ERROR);
                }
                else{
                //            ADFUtils.showPopup(successPopup);
                    if(savedSuccessMessage != null){
                        savedSuccessMessage.setVisible(Boolean.TRUE);
                        ADFUtils.addPartialTarget(savedSuccessMessage);
                        ResetUtils.reset(savedSuccessMessage);
                    }
                    if(copySuccessMessage != null){
                        copySuccessMessage.setVisible(Boolean.TRUE);
                        ADFUtils.addPartialTarget(copySuccessMessage);
                        ResetUtils.reset(copySuccessMessage);
                    }
                    showStatus(ViewConstants.CRS_SAVED);
                }
            }
        }
        if(baseOrStaging != null && "BASE".equalsIgnoreCase(baseOrStaging)){
            DCIteratorBinding riskBaseIter = ADFUtils.findIterator("CrsRiskBaseVOIterator");
            riskBaseIter.setCurrentRowWithKey(key);
            riskBaseIter.getViewObject().setCurrentRow(currRow);
        }
        else{
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsRiskVOIterator");
            riskIter.setCurrentRowWithKey(key);
            riskIter.getViewObject().setCurrentRow(currRow);
        }
    }

    public void setSuccessPopup(RichPopup successPopup) {
        this.successPopup = successPopup;
    }

    public RichPopup getSuccessPopup() {
        return successPopup;
    }

    public void onCloseRiskPopup(PopupCanceledEvent popupCanceledEvent) {
        logger.info("Closing risk defintions popup");
        DCIteratorBinding iter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = iter.getViewObject();
        Row currRow = riskDefVO.getCurrentRow();
        if(currRow != null){
            currRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk def row.");
        }
        riskDefVO.clearCache();
        riskDefVO.executeQuery();
        ResetUtils.reset(riskDefTable);
        riskDefTable.resetStampState();
        System.err.println("NIITSH");
        DCIteratorBinding relIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        ViewObject riskRelVO = relIter.getViewObject();
        Row relCurrRow = riskRelVO.getCurrentRow();
        if(relCurrRow != null){
            relCurrRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk relations row.");
        }
        
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        OperationBinding oper = ADFUtils.findOperation("initRiskRelation");
        logger.info("Reexecuting the table with crsId : "+crsId+" state : "+getBaseOrStaging());
        oper.getParamsMap().put("crsId", crsId);
        oper.getParamsMap().put("status", getBaseOrStaging());
        oper.execute();
        if (oper.getErrors().size() > 0) 
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
//        ADFUtils.addPartialTarget(riskDefTable);
        if(riskDefTable != null)
            ADFUtils.addPartialTarget(riskDefTable);
        if(stagingTable != null){
            ADFUtils.addPartialTarget(stagingTable);
        }
        if(riskDefPopup != null){
            if (null != cntrlStatusBar && cntrlStatusBar.isRendered()){
                this.iconCRSSaved.setVisible(false);
                this.iconCRSSaveError.setVisible(false);
                this.iconCRSChanged.setVisible(false);
                ADFUtils.addPartialTarget(cntrlStatusBar);
            }
            riskDefPopup.hide();
        }
        if(copyPopup != null){
            if (null != cntrlStatusBarCopy && cntrlStatusBarCopy.isRendered()){
                this.iconCopyCRSSaved.setVisible(false);
                this.iconCopyCRSSaveError.setVisible(false);
                this.iconCopyCRSChanged.setVisible(false);
                ADFUtils.addPartialTarget(cntrlStatusBarCopy);
            }
            copyPopup.hide();
        }
        
        setRepoRefreshed(Boolean.FALSE);
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param selDatabases
     */
    public void setSelDatabases(List<String> selDatabases) {
        this.selDatabases = selDatabases;
    }

    /**
     * @return
     */
    public List<String> getSelDatabases() {
        return selDatabases;
    }

    /**
     * @param databaseList
     */
    public void setDatabaseList(List<SelectItem> databaseList) {
        this.databaseList = databaseList;
    }

    /**
     * @return
     */
    public List<SelectItem> getDatabaseList() {
        if(databaseList == null){
            databaseList = new ArrayList<SelectItem>();
            DCBindingContainer bc = ADFUtils.getDCBindingContainer();
            OperationBinding ob = bc.getOperationBinding("fetchDatabases");
            List<String> databases = (List<String>)ob.execute();
            if(databases != null && databases.size() > 0){
                for(String database : databases){
                    SelectItem item = new SelectItem(database, database);
                    databaseList.add(item);
                }
            }
            logger.info("databaseList -->"+databaseList);
        }
        return databaseList;
    }

    /**
     * @param selRiskPurposes
     */
    public void setSelRiskPurposes(List<String> selRiskPurposes) {
        this.selRiskPurposes = selRiskPurposes;
    }

    /**
     * @return
     */
    public List<String> getSelRiskPurposes() {
        return selRiskPurposes;
    }

    /**
     * @param reviewSubmitPopup
     */
    public void setReviewSubmitPopup(RichPopup reviewSubmitPopup) {
        this.reviewSubmitPopup = reviewSubmitPopup;
    }

    /**
     * @return
     */
    public RichPopup getReviewSubmitPopup() {
        return reviewSubmitPopup;
    }

    /**
     * @param crsStateSOC
     */
    public void setCrsStateSOC(RichSelectOneChoice crsStateSOC) {
        this.crsStateSOC = crsStateSOC;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getCrsStateSOC() {
        return crsStateSOC;
    }

    /**
     * @param crsStatusSOC
     */
    public void setCrsStatusSOC(RichSelectOneChoice crsStatusSOC) {
        this.crsStatusSOC = crsStatusSOC;
    }

    /**
     * @return
     */
    public RichSelectOneChoice getCrsStatusSOC() {
        return crsStatusSOC;
    }
    
    /** DRAFT to REVIEW */
     public void processReviewDialog(DialogEvent dialogEvent) {
         logger.info("--------processing Review action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome()))
            processStateChange(ModelConstants.STATE_REVIEW, getReviewSubmitPopup());

    }
       
    /** REVIEW to REVIEWED */
     public void processReviewedDialog(DialogEvent dialogEvent) {
         logger.info("--------processing Reviewed action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome()))
             processStateChange(ModelConstants.STATE_REVIEWED, getCrsReviewedPopup());    
     }
       
    /** REVIEWED to TASL APPROVE */
     public void processTaslReviewSubmit(DialogEvent dialogEvent) {
         logger.info("--------processing TaslReviewSubmit action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome()))
             processStateChange(ModelConstants.STATE_TASLAPPROVE, getSubmitApprovalPopup());
     }
       
    /** TASL APPROVE to ML APPROVE */ 
     public void processTaslApprove(DialogEvent dialogEvent) {
         logger.info("--------processing TaslApprove action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) 
             //AS ML is no more there, we are directly changing to approved
//             processStateChange(ModelConstants.STATE_MLAPPROVE, getCrsApprovePopup());
             processStateChange(ModelConstants.STATE_APPROVED, getCrsApprovePopup());
     }
      
    /** TASL APPROVE to DRAFT */ 
     public void processTaslReject(DialogEvent dialogEvent) {
        logger.info("--------processing TaslReject action---------");
        if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) {
            String taslComments = (String)ADFUtils.evaluateEL("#{bindings.TaslRejectComment.inputValue}");
            if (taslComments == null || (taslComments != null && "".equals(taslComments.trim()))){
                ADFUtils.showFacesMessage("Please enter your comments for rejection.",
                                          FacesMessage.SEVERITY_ERROR, getTaslCommentsInputText());                
                return;
            }
            
            // change to DRAFT state
            processStateChange(ModelConstants.STATE_DRAFT, getCrsRejectPopup());
        }
     }
   
    /** ML APPROVE to APPROVED */ 
     public void processMLApprove(DialogEvent dialogEvent) {
         logger.info("--------processing MLApprove action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome()))
             processStateChange(ModelConstants.STATE_APPROVED, getCrsApprovePopup());
     }
       
    /** ML APPROVE to DRAFT */ 
     public void processMLReject(DialogEvent dialogEvent) {
         logger.info("--------processing MLReject action---------");
        if (DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) {
            String mlComments = (String)ADFUtils.evaluateEL("#{bindings.MedicalLeadRejectComment.inputValue}");
            if (mlComments == null || (mlComments != null && "".equals(mlComments.trim()))) {
                ADFUtils.showFacesMessage("Please enter your comments for rejection.",
                                          FacesMessage.SEVERITY_ERROR, getMlCommentsInputText());
                return;
            }

            // change to DRAFT state
            processStateChange(ModelConstants.STATE_DRAFT, getCrsRejectPopup());
        }
     }
       
     /** BSL DEMOTE - any state to DRAFT */
    public void processDemoteToDraftDialog(DialogEvent dialogEvent) {
         logger.info("--------processing DemoteToDraft action---------");
         if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome()))
             processStateChange(ModelConstants.STATE_DRAFT, getCrsDemoteDraftPopupBinding());
    }

    /** APPROVED to PUBLISHED */ 
     public void processPublishDialog(DialogEvent dialogEvent) {
         logger.info("--------processing Publish action---------");
        if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) {
            if (!isRiskRelationsExistsForCRS()){
                ADFUtils.showFacesMessage(uiBundle.getString("RISK_RELATION_REQURIED_MSG"), FacesMessage.SEVERITY_ERROR);
                return;
            }
//            OperationBinding oper = ADFUtils.findOperation("Commit");
//            oper.execute();
//            if (oper.getErrors().size() > 0){
//                ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
//            } else {
                OperationBinding op = ADFUtils.findOperation("activateCrs");
                Map params = op.getParamsMap();
                params.put("pCRSId", ADFUtils.evaluateEL("#{bindings.CrsId.inputValue}"));
                params.put("pReasonForChange", getReasonForChange());
                String msg = (String)op.execute();
                logger.info("activate crs..msg==" + msg);
                if (op.getErrors() != null && op.getErrors().size() > 0) {
                    ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"),
                                              FacesMessage.SEVERITY_ERROR);
                } else {

                    // if NOT a success
                    if (null != msg && !ModelConstants.PLSQL_CALL_SUCCESS.equals(msg)) {
                        if (msg.indexOf(ModelConstants.CRS_ACTIVATION_ERROR_CODE) > -1 ){
                            ADFUtils.showFacesMessage(uiBundle.getString("CRS_ACTIVATION_ERROR_ON_SAME_DAY"),
                                       FacesMessage.SEVERITY_ERROR);
                        } else {
                            ADFUtils.showFacesMessage("<html> <body> <p> An internal error has occured. Please contact the Administrator </p> <p>"+msg+"</p> </body> </html>",
                                                   FacesMessage.SEVERITY_ERROR);
                        }
                        // if success - show popup which on ack takes user to search page
                    } else
                        ADFUtils.showPopup(getCrsPublishPopupBinding());
                }
//            }
        }
     }
    
    private void processStateChange(Integer newState, RichPopup infoPopup) {
        logger.info("--------processing StateChange ---------");
        ADFUtils.setEL("#{bindings.StateId.inputValue}", newState);
        // before save, check atleast one risk relation exists exists for the crs 
        if (newState != ModelConstants.STATE_DRAFT && !isRiskRelationsExistsForCRS()){
            ADFUtils.showFacesMessage(uiBundle.getString("RISK_RELATION_REQURIED_MSG"), FacesMessage.SEVERITY_ERROR);
        } else {
//            if (ModelConstants.STATE_MLAPPROVE.equals(newState)){
//                ADFUtils.setEL("#{bindings.TaslRejectComment.inputValue}", null);
//            } else 
              if (ModelConstants.STATE_APPROVED.equals(newState)){
                ADFUtils.setEL("#{bindings.TaslRejectComment.inputValue}", null);
            }
            
            if (ModelConstants.STATE_TASLAPPROVE.equals(newState)){
                OperationBinding oper = ADFUtils.findOperation("removeMQMComments");
                oper.execute();
                if (oper.getErrors().size() > 0)
                    ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
            }
            
            OperationBinding oper = ADFUtils.findOperation("Commit");
            oper.execute();
            if (oper.getErrors().size() > 0)
                ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
            else {
                ADFUtils.showPopup(infoPopup);
               // ADFUtils.addPartialTarget(getCrsStateSOC());
                ADFUtils.addPartialTarget(getWorkflowPG());
            }
        }
    }

    /**
     * @param crsApprovePopup
     */
    public void setCrsApprovePopup(RichPopup crsApprovePopup) {
        this.crsApprovePopup = crsApprovePopup;
    }

    /**
     * @return
     */
    public RichPopup getCrsApprovePopup() {
        return crsApprovePopup;
    }

    /**
     * @param workflowPanelBox
     */
    public void setWorkflowPanelBox(RichPanelBox workflowPanelBox) {
        this.workflowPanelBox = workflowPanelBox;
    }

    /**
     * @return
     */
    public RichPanelBox getWorkflowPanelBox() {
        return workflowPanelBox;
    }

    /**
     * @param crsRejectPopup
     */
    public void setCrsRejectPopup(RichPopup crsRejectPopup) {
        this.crsRejectPopup = crsRejectPopup;
    }

    /**
     * @return
     */
    public RichPopup getCrsRejectPopup() {
        return crsRejectPopup;
    }

    /**
     * @param taslCommentsInputText
     */
    public void setTaslCommentsInputText(RichInputText taslCommentsInputText) {
        this.taslCommentsInputText = taslCommentsInputText;
    }

    /**
     * @return
     */
    public RichInputText getTaslCommentsInputText() {
        return taslCommentsInputText;
    }

    /**
     * @param mlCommentsInputText
     */
    public void setMlCommentsInputText(RichInputText mlCommentsInputText) {
        this.mlCommentsInputText = mlCommentsInputText;
    }

    /**
     * @return
     */
    public RichInputText getMlCommentsInputText() {
        return mlCommentsInputText;
    }

    /**
     * @param dictionary
     */
    public void setDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * @return
     */
    public String getDictionary() {
        return dictionary;
    }

    /**
     * @param level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return
     */
    public String getTerm() {
        return term;
    }

    public void searchHierarchy(ActionEvent actionEvent) {
        logger.info("Querying Hierarchy search");
        DCIteratorBinding iter = ADFUtils.findIterator("HierarchySearchVOIterator");
        ViewObject hierVO = iter.getViewObject();
        logger.info("Entered search criteria : term : "+term+" level : "+level+" dictionary : "+dictionary);
        hierVO.setNamedWhereClauseParam("pTerm", (term != null && !term.isEmpty()) ? term : "%");
        hierVO.setNamedWhereClauseParam("pLevel", level != null ? level : null);
        //hierVO.setNamedWhereClauseParam("pDict", "NMATMED");
        hierVO.setNamedWhereClauseParam("pDict", dictionary != null ? dictionary : null);
        logger.info(" Hierarchy search Query..." + hierVO.getQuery());
        if("NMATSMQ".equalsIgnoreCase(dictionary) || "MEDSMQ".equalsIgnoreCase(dictionary)){
            hierVO.setWhereClause("MQSTATUS <> 'I'");
        }
        else{
            hierVO.setWhereClause(null);
        }
        hierVO.executeQuery();
        if (null != this.childTreeTable){
            this.childTreeTable.setVisible(false);
        }
    }

    public void onClickHierarchySearch(ActionEvent actionEvent) {
        clickHierarchy();
        logger.info("Opening the blank hierarchy popup, aligning to the right of risk definition popup.");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
//        hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, this.getHiddenPopupAlign());
//        hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN, RichPopup.PopupHints.AlignTypes.ALIGN_END_BEFORE);
        hierPopup.show(hints);
//        ADFUtils.showPopup(hierPopup);
    }
    
    public void onClickCopyHierarchySearch(ActionEvent actionEvent) {
        clickHierarchy();
        logger.info("Opening the blank hierarchy popup, aligning to the right of copy risk definition popup.");
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
//        hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN_ID, this.getCopyRiskDefTable());
//        hints.add(RichPopup.PopupHints.HintTypes.HINT_ALIGN, RichPopup.PopupHints.AlignTypes.ALIGN_END_AFTER);
        hierPopup.show(hints);
    }
    
    private void clickHierarchy(){
        logger.info("-------clickHierarchy ---------");
        DCIteratorBinding iter = ADFUtils.findIterator("HierarchySearchVOIterator");
        ViewObject hierVO = iter.getViewObject();
        hierVO.executeEmptyRowSet();
        DCIteratorBinding childIter = ADFUtils.findIterator("HierarchyChildVOIterator");
        ViewObject childVO = childIter.getViewObject();
        childVO.executeEmptyRowSet();
        setTerm(null);
        //setLevel(null);
        //setDictionary(null);
        setLevel(ViewConstants.SOC);
        setDictionary(ViewConstants.MEDDRA_DICTIONARY);
        setContentId(null);
        setLevelItems(getMeddraItems());
        this.setMeddraSearch(true);
        if(childTreeTable != null)
            childTreeTable.setVisible(false);
    }

    /**
     * @param hierPopup
     */
    public void setHierPopup(RichPopup hierPopup) {
        this.hierPopup = hierPopup;
    }

    /**
     * @return
     */
    public RichPopup getHierPopup() {
        return hierPopup;
    }

    public DnDAction dragDropListener(DropEvent dropEvent) {
        logger.info("Performed drag and drop.");
        DCIteratorBinding riskDefIter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = riskDefIter.getViewObject();

        UIXCollection dragTable = (UIXCollection)dropEvent.getDragComponent();
        RichTable dropTable = (RichTable)dropEvent.getDropComponent();
        String dragNodeVO = null;
        Transferable t = dropEvent.getTransferable();
        DataFlavor<RowKeySet> df = DataFlavor.getDataFlavor(RowKeySet.class, "copyRows");
        RowKeySet rks = t.getData(df);
        Iterator iter = rks.iterator();

        Object dragCurrentRowKey = dragTable.getRowKey();
        Row dragRow = null;
        while (iter.hasNext()) {
            List key = (List)iter.next();
            dragTable.setRowKey(key);
            Object dataObj = dragTable.getRowData();
            if (dataObj instanceof HierarchyChildUIBean) {
                logger.info("Dragged child hierarchy row. (second level tree table)");
                HierarchyChildUIBean selRow = (HierarchyChildUIBean)dragTable.getRowData();
                if(selRow.getLevelName().equals(new Long(0))){
                    logger.info("Dragged parent hierarchy row in tree. Diallowed");
                    ADFUtils.showPopup(parentError);
                    dragTable.setRowKey(dragCurrentRowKey);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                    AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                    return DnDAction.NONE;
                }
                else{
                    
                    Row filterRow[] = riskDefVO.getFilteredRows("TmsDictContentId", selRow.getTmsDictContentId());
                    if(filterRow.length > 0){
                        ADFUtils.showFacesMessage(uiBundle.getString("TERM_UNIQUE_ERROR"), FacesMessage.SEVERITY_ERROR);
                        dragTable.setRowKey(dragCurrentRowKey);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                        return DnDAction.NONE;
                    }
                    
                    String version = selRow.getDictContentAltCode();
                    String dict = selRow.getDictShortName();
                    if (dict != null && ViewConstants.MEDDRA_DICTIONARY.equalsIgnoreCase(dict)) {
                        Row rows[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.MEDDRA_DICTIONARY);
                        if (rows.length > 0) {
                            ADFUtils.showPopup(meddraError);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                        Row rows1[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.FILTER_DICTIONARY);
                        if (rows1.length > 0) {
                            ADFUtils.showFacesMessage(uiBundle.getString("MEDDRA_FILTER_COMBO_ERROR"), FacesMessage.SEVERITY_ERROR);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                    }
                    else if(dict != null && ViewConstants.FILTER_DICTIONARY.equalsIgnoreCase(dict)){
                        Row rows[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.MEDDRA_DICTIONARY);
                        if (rows.length > 0) {
                            ADFUtils.showFacesMessage(uiBundle.getString("MEDDRA_FILTER_COMBO_ERROR"), FacesMessage.SEVERITY_ERROR);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                    }
                    Row riskDefRow = riskDefVO.createRow();
                    riskDefRow.setAttribute("MeddraCode", selRow.getDictContentCode());
                    riskDefRow.setAttribute("MeddraLevel", selRow.getLevelName());
                    riskDefRow.setAttribute("MeddraTerm", selRow.getTerm());
                    riskDefRow.setAttribute("MeddraDict", selRow.getDictShortName());
                    riskDefRow.setAttribute("MeddraVersion", ADFUtils.getPageFlowScopeValue("childVersion"));
                    riskDefRow.setAttribute("MeddraVersionDate", ADFUtils.getPageFlowScopeValue("childDate"));

                    if (dict != null && ViewConstants.FILTER_DICTIONARY.equalsIgnoreCase(dict)) {
                        if (selRow.getTerm() != null && selRow.getTerm().contains(ViewConstants.NMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.NMQ);
                        else if (selRow.getTerm() != null && selRow.getTerm().contains(ViewConstants.CMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.CMQ);
                        else if (selRow.getTerm() != null && selRow.getTerm().contains(ViewConstants.SMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.SMQ);
                        else
                            riskDefRow.setAttribute("MeddraExtension", selRow.getLevelName());
                    } else
                        riskDefRow.setAttribute("MeddraExtension", selRow.getLevelName());


//                    riskDefRow.setAttribute("MeddraQualifier", getChildScope());
                    riskDefRow.setAttribute("TmsDictContentEntryTs", selRow.getTmsDictContentEntryTs());
                    riskDefRow.setAttribute("TmsDictContentId", selRow.getTmsDictContentId());
                    riskDefRow.setAttribute("TmsEndTs", selRow.getTmsEndTs());
                    riskDefRow.setAttribute("MeddraQualifier", selRow.getQual());
                    riskDefRow.setAttribute("MeddraQualifierUpdFlag", selRow.getQualFlag());
                    riskDefRow.setAttribute("CrsQualifier", selRow.getQual());
                    riskDefVO.insertRow(riskDefRow);
                }
            } else {
                logger.info("Dragged hierarchy row from 1st table");
                JUCtrlHierNodeBinding rowBinding = (JUCtrlHierNodeBinding)dragTable.getRowData();
                dragRow = rowBinding.getRow();
                dragNodeVO = dragRow.getStructureDef().getDefName();
                if ("HierarchySearchVO".equalsIgnoreCase(dragNodeVO)) {
                    
                    Row filterRow[] = riskDefVO.getFilteredRows("TmsDictContentId", dragRow.getAttribute("DictContentId"));
                    if(filterRow.length > 0){
                        ADFUtils.showFacesMessage(uiBundle.getString("TERM_UNIQUE_ERROR"), FacesMessage.SEVERITY_ERROR);
                        dragTable.setRowKey(dragCurrentRowKey);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                        AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                        return DnDAction.NONE;
                    }
                    
                    String term = (String)dragRow.getAttribute("Mqterm");
                    String code = (String)dragRow.getAttribute("Mqcode");
                    String level = (String)dragRow.getAttribute("Mqlevel");
                    String qual = (String)dragRow.getAttribute("Mqcrtev");
                    String dict = (String)dragRow.getAttribute("DictNm");
                    String version = (String)dragRow.getAttribute("DictVersion");

                    if (dict != null && ViewConstants.MEDDRA_DICTIONARY.equalsIgnoreCase(dict)) {
                        Row rows[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.MEDDRA_DICTIONARY);
                        if (rows.length > 0) {
                            logger.info("Dragged another meddra row, disallowed");
                            ADFUtils.showPopup(meddraError);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                        Row rows1[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.FILTER_DICTIONARY);
                        if (rows1.length > 0) {
                            ADFUtils.showFacesMessage(uiBundle.getString("MEDDRA_FILTER_COMBO_ERROR"), FacesMessage.SEVERITY_ERROR);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                    }
                    else if(dict != null && ViewConstants.FILTER_DICTIONARY.equalsIgnoreCase(dict)){
                        Row rows[] = riskDefVO.getFilteredRows("MeddraDict", ViewConstants.MEDDRA_DICTIONARY);
                        if (rows.length > 0) {
                            ADFUtils.showFacesMessage(uiBundle.getString("MEDDRA_FILTER_COMBO_ERROR"), FacesMessage.SEVERITY_ERROR);
                            dragTable.setRowKey(dragCurrentRowKey);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
                            AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
                            return DnDAction.NONE;
                        }
                    }
                    
                    Row riskDefRow = riskDefVO.createRow();
                    riskDefRow.setAttribute("MeddraCode", code);
                    riskDefRow.setAttribute("MeddraLevel", level);
                    riskDefRow.setAttribute("MeddraTerm", term);
                    riskDefRow.setAttribute("MeddraDict", dict);
                    riskDefRow.setAttribute("MeddraVersion", version);
                    riskDefRow.setAttribute("MeddraVersionDate", dragRow.getAttribute("DictVersionDate"));
                    riskDefRow.setAttribute("TmsDictContentEntryTs", dragRow.getAttribute("DictContentEntryTs"));
                    riskDefRow.setAttribute("TmsDictContentId", dragRow.getAttribute("DictContentId"));
                    riskDefRow.setAttribute("TmsEndTs", dragRow.getAttribute("EndTs"));
                    if (dict != null && ViewConstants.FILTER_DICTIONARY.equalsIgnoreCase(dict)) {
                        if (term != null && term.contains(ViewConstants.NMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.NMQ);
                        else if (term != null && term.contains(ViewConstants.CMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.CMQ);
                        else if (term != null && term.contains(ViewConstants.SMQ))
                            riskDefRow.setAttribute("MeddraExtension", ViewConstants.SMQ);
                    } else
                        riskDefRow.setAttribute("MeddraExtension", level);

               
                    riskDefRow.setAttribute("MeddraQualifier", dragRow.getAttribute("Qual"));
                    riskDefRow.setAttribute("CrsQualifier", dragRow.getAttribute("Qual"));
                    //                meddra_qualifier IN ('BROAD','NARROW','CHILD NARROW')
                    
                    riskDefRow.setAttribute("MeddraQualifierUpdFlag", dragRow.getAttribute("QualFlag"));
                
                    riskDefVO.insertRow(riskDefRow);
                }
            }
        }
        dragTable.setRowKey(dragCurrentRowKey);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dragTable);
        AdfFacesContext.getCurrentInstance().addPartialTarget(dropTable);
        showStatus(ViewConstants.CRS_MODIFIED);
        return DnDAction.COPY;
    }


    /**
     * @param crsFieldsUpdatable
     */
    public void setCrsFieldsUpdatable(boolean crsFieldsUpdatable) {
        this.crsFieldsUpdatable = crsFieldsUpdatable;
    }

    /**
     * @return
     */
    public boolean isCrsFieldsUpdatable() {
        boolean isCrsFieldsUpdatable = false;
        Integer crsState = (Integer)ADFUtils.evaluateEL("#{bindings.StateId.inputValue}");
        String crsStatus = (String)ADFUtils.evaluateEL("#{bindings.ReleaseStatusFlag.inputValue}");
        
        // SEARCH FLOW - always read only
        if(ViewConstants.FLOW_TYPE_SEARCH.equals(getFlowType())){
            isCrsFieldsUpdatable = false;
            return isCrsFieldsUpdatable;
        }
            
        //  BSL LOGIN
        if (ADFContext.getCurrent().getSecurityContext().isUserInRole(ModelConstants.ROLE_BSL)) {
            logger.info("--Entering : isCrsFieldsUpdatable: BSL Loggedin block-------------");
            if (ModelConstants.STATUS_CURRENT.equals(crsStatus)) {


            } else if (ModelConstants.STATUS_PENDING.equals(crsStatus)) {
                
                if(ModelConstants.STATE_DRAFT.equals(crsState) 
                   || ModelConstants.STATE_REVIEWED.equals(crsState)
                   || ModelConstants.STATE_APPROVED.equals(crsState)
                   || ModelConstants.STATE_RETIRED.equals(crsState)
                   || ModelConstants.STATE_ACTIVATED.equals(crsState)
                   || ModelConstants.STATE_PUBLISHED.equals(crsState)
                )
                    isCrsFieldsUpdatable = true;                                
            }
            logger.info("--End : isCrsFieldsUpdatable:  BSL Loggedin block-------------");
        }
        
        // ADMIN LOGIN - admin can update any CRS in any state
       else if (ADFContext.getCurrent().getSecurityContext().isUserInRole(ModelConstants.ROLE_CRSADMIN))
            isCrsFieldsUpdatable = true;
        
        return isCrsFieldsUpdatable;
    }

    public void processDeleteDialog(DialogEvent dialogEvent) {
        logger.info("Showing delete confirmation popup.");
        String returnMessage = "";
        if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())){
            OperationBinding oper = ADFUtils.findOperation("deleteCrs");
            Map params1 = oper.getParamsMap();
            Long crsId = (Long)ADFUtils.evaluateEL("#{bindings.crsId.inputValue}");
            params1.put("crsId" , crsId);
            returnMessage = (String) oper.execute();
            logger.info("returnMessage from deleteCrs call..." + returnMessage);
            if (oper.getErrors().size() > 0)
                ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
            else{
                if (this.flowType.equalsIgnoreCase(ViewConstants.FLOW_TYPE_CREATE)){
                    ADFUtils.navigateToControlFlowCase("home");
                } else {
                    ADFUtils.navigateToControlFlowCase("reloadSearchPage");
                }
//                String returnValue = (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getPrevious}");
//                if(returnValue == null){
//                    ADFUtils.navigateToControlFlowCase("home");
//                }else{
//                    ADFUtils.navigateToControlFlowCase(returnValue);
//                }
            }
        }
    }


    /**
     * @param crsRetirePopup
     */
    public void setCrsRetirePopup(RichPopup crsRetirePopup) {
        this.crsRetirePopup = crsRetirePopup;
    }

    /**
     * @return
     */
    public RichPopup getCrsRetirePopup() {
        return crsRetirePopup;
    }

    /**
     * @param crsReactivatePopup
     */
    public void setCrsReactivatePopup(RichPopup crsReactivatePopup) {
        this.crsReactivatePopup = crsReactivatePopup;
    }

    /**
     * @return
     */
    public RichPopup getCrsReactivatePopup() {
        return crsReactivatePopup;
    }

    /**
     * @param crsReviewedPopup
     */
    public void setCrsReviewedPopup(RichPopup crsReviewedPopup) {
        this.crsReviewedPopup = crsReviewedPopup;
    }

    /**
     * @return
     */
    public RichPopup getCrsReviewedPopup() {
        return crsReviewedPopup;
    }

    /**
     * @param meddraError
     */
    public void setMeddraError(RichPopup meddraError) {
        this.meddraError = meddraError;
    }

    /**
     * @return
     */
    public RichPopup getMeddraError() {
        return meddraError;
    }

    /**
     * This method is used to frame the crs name with the
     * selected compound code and indication
     * @param vce
     */
    public void onChangeIndication(ValueChangeEvent vce) {
        if (vce != null) {
            logger.info("-- onChangeIndication : append commpound and indication--->");
            vce.getComponent().processUpdates(FacesContext.getCurrentInstance());
            if (vce.getNewValue() != null &&
                !vce.getNewValue().equals(vce.getOldValue())) {
                String crsCompCode =
                    (String)ADFUtils.evaluateEL("#{bindings.CrsCompoundCode.inputValue}");
                String compCode =
                    (String)ADFUtils.evaluateEL("#{bindings.CompoundCode.inputValue}");
                String indication =
                    (String)ADFUtils.evaluateEL("#{bindings.Indication.inputValue}");
                if (indication != null) {
                    ADFUtils.setEL("#{bindings.CrsName.inputValue}",
                                   (compCode != null ? compCode :
                                    crsCompCode) + " " + indication);
                }
                resetCrsName(indication, compCode, crsCompCode);
                
            }
        }
    }

    /**
     * @param facesContext
     * @param outputStream
     * @throws IOException
     */
    public void exportRiskDefinitions(FacesContext facesContext,
                                      OutputStream outputStream) throws IOException {
        // Add event code here...
        logger.info("Start of CRSReportsBean:onAdminReportItmes()");
        Workbook workbook = null;
        ExcelExportUtils excUtils = new ExcelExportUtils();
        InputStream excelInputStream = excUtils.getExcelInpStream();
        InputStream imageInputStream = excUtils.getImageInpStream();
        try {
            //create sheet
            DCIteratorBinding iter = null;
            if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
                iter = ADFUtils.findIterator("CrsRiskBaseVOIterator");
            } else
                iter = ADFUtils.findIterator("CrsRiskVOIterator");

            RowSetIterator rowSet = null;
            int rowStartIndex = 14;
            int cellStartIndex = 0;
            String emptyValReplace = null;
            String dateCellFormat = "M/dd/yyyy";
            if (iter != null) {
                iter.setRangeSize(-1);
                rowSet = iter.getRowSetIterator();
            }
            workbook = WorkbookFactory.create(excelInputStream);
            LinkedHashMap columnMap = new LinkedHashMap();
            ResourceBundle rsBundle =
                BundleFactory.getBundle("com.novartis.ecrs.model.ECRSModelBundle");
            //Here Key will be ViewObject Attribute
            columnMap.put("SafetyTopicOfInterest",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.SafetyTopicOfInterest_LABEL"));
            columnMap.put("RiskPurposeSpFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeSpFlag_LABEL"));
            columnMap.put("RiskPurposeDsFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeDsFlag_LABEL"));
            columnMap.put("RiskPurposeRmFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeRmFlag_LABEL"));
            columnMap.put("RiskPurposePsFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposePsFlag_LABEL"));
            columnMap.put("RiskPurposeIbFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeIbFlag_LABEL"));
            columnMap.put("RiskPurposeCdFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeCdFlag_LABEL"));
            columnMap.put("RiskPurposeOsFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeOsFlag_LABEL"));
            columnMap.put("RiskPurposeMiFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeMiFlag_LABEL"));
            columnMap.put("RiskPurposeErFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeErFlag_LABEL"));
            columnMap.put("RiskPurposeUdFlag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeUdFlag_LABEL"));
            columnMap.put("RiskPurposeA1Flag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeA1Flag_LABEL"));
            columnMap.put("RiskPurposeA2Flag",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.RiskPurposeA2Flag_LABEL"));
            columnMap.put("SocTerm",
                          rsBundle.getString("SOC_AS_ASSIGNED_TO_THE_ADR"));
            
            if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
                columnMap.put("Gender","Gender");
                columnMap.put("Age","Age");
                columnMap.put("PediatricGroup","Pediatric Sub-Group");
            }else{
                columnMap.put("Gender","Gender");
                columnMap.put("Age","Age");
                columnMap.put("PediatricGroup","Pediatric Sub-Group");
            }
            
//            columnMap.put("DatabaseList",
//                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.DatabaseId_LABEL"));
            columnMap.put("DataDomain",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.DataDomain_LABEL"));
            columnMap.put("SearchCriteriaDetails",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.SearchCriteriaDetails_LABEL"));
            columnMap.put("MeddraCode",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.MeddraCode_LABEL"));
            columnMap.put("MeddraTerm", rsBundle.getString("MEDDRA_TERM"));
            columnMap.put("MeddraLevel", rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskDefinitionsVO.MeddraLevel_LABEL"));
            columnMap.put("CrsQualifier",
                          rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskDefinitionsVO.MeddraQualifier_LABEL"));
            //columnMap.put("SearchCriteriaDetails",
            //              rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskDefinitionsVO.SearchCriteriaDetails_LABEL"));
            columnMap.put("NonMeddraComponentComment", rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskRelationVO.NonMeddraComponentComment_LABEL"));
            columnMap.put("Adr", "ADR");
            columnMap.put("SearchAppliedTo", "Search Applied To");
            //BSL, LoggedinUser in Designee, Admin,MQM
            String bsl = "";
            String designee = "";
            if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
                bsl =
(String)ADFUtils.evaluateEL("#{bindings.BslNameBase.inputValue}");

            } else
                bsl =
(String)ADFUtils.evaluateEL("#{bindings.BslName.inputValue}");

            if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
                designee =
                        (String)ADFUtils.evaluateEL("#{bindings.DesigneeBase.inputValue}");
            } else
                designee =
                        (String)ADFUtils.evaluateEL("#{bindings.Designee.inputValue}");

            if (getUserName() != null && getUserName().equals(bsl) ||
                ViewConstants.isNotEmpty(designee) &&
                designee.contains(getUserName()) ||
                ModelConstants.ROLE_CRSADMIN.equals(loggedInUserRole) ||
                ModelConstants.ROLE_MQM.equals(loggedInUserRole)) {
                columnMap.put("MqmComment",
                              rsBundle.getString("com.novartis.ecrs.model.view.CrsRiskVO.MqmComment_LABEL"));
            }
            workbook.setMissingCellPolicy(org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
            Sheet sheet = workbook.getSheetAt(0);
            writeHeaderData(sheet,0,4,7,10);
            ExcelExportUtils.writeExcelSheet(sheet, rowSet, rowStartIndex,
                                             cellStartIndex, columnMap, null,
                                             dateCellFormat, emptyValReplace,imageInputStream);
            //write image to sheet
            //ExcelExportUtils.writeImageTOExcel(sheet,imageInputStream);
            
        } catch (InvalidFormatException invalidFormatException) {
            logger.error("Exception occured in onAdminReportItmes()"+invalidFormatException);
        } catch (IOException ioe) {
            logger.error("Exception occured in onAdminReportItmes()"+ioe);
        } catch (Exception e) {
            logger.error("Exception occured in onAdminReportItmes()"+e);
        } finally {
            workbook.write(outputStream);
            excelInputStream.close();
            outputStream.close(); 
        }
        logger.info("End of CRSReportsBean:onAdminReportItmes()");
    }


    /**
     * @param delConfPopupBinding
     */
    public void setDelConfPopupBinding(RichPopup delConfPopupBinding) {
        this.delConfPopupBinding = delConfPopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getDelConfPopupBinding() {
        return delConfPopupBinding;
    }

    /**
     * @param publishPopupBinding
     */
    public void setCrsPublishPopupBinding(RichPopup publishPopupBinding) {
        this.crsPublishPopupBinding = publishPopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getCrsPublishPopupBinding() {
        return crsPublishPopupBinding;
    }

    /**
     * @param crsDemoteDraftPopupBinding
     */
    public void setCrsDemoteDraftPopupBinding(RichPopup crsDemoteDraftPopupBinding) {
        this.crsDemoteDraftPopupBinding = crsDemoteDraftPopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getCrsDemoteDraftPopupBinding() {
        return crsDemoteDraftPopupBinding;
    }

    /**
     * @param filterItems
     */
    public void setFilterItems(List<SelectItem> filterItems) {
        this.filterItems = filterItems;
    }

    /**
     * @return
     */
    public List<SelectItem> getFilterItems() {
        logger.info("Getting list of values for Filter in hierarchy search.");
        if(filterItems == null){
            filterItems = new ArrayList<SelectItem>();
            SelectItem item1 = new SelectItem(ViewConstants.MQ1, ViewConstants.SMQ1);
            SelectItem item2 = new SelectItem(ViewConstants.MQ2, ViewConstants.SMQ2);
            SelectItem item3 = new SelectItem(ViewConstants.MQ3, ViewConstants.SMQ3);
            SelectItem item4 = new SelectItem(ViewConstants.MQ4, ViewConstants.SMQ4);
            SelectItem item5 = new SelectItem(ViewConstants.MQ5, ViewConstants.SMQ5);
            SelectItem item11 = new SelectItem(ViewConstants.MQ6, ViewConstants.SMQ6);
            SelectItem item6 = new SelectItem(ViewConstants.NMQ1, ViewConstants.CUSTOM1);
            SelectItem item7 = new SelectItem(ViewConstants.NMQ2, ViewConstants.CUSTOM2);
            SelectItem item8 = new SelectItem(ViewConstants.NMQ3, ViewConstants.CUSTOM3);
            SelectItem item9 = new SelectItem(ViewConstants.NMQ4, ViewConstants.CUSTOM4);
            SelectItem item10 = new SelectItem(ViewConstants.NMQ5, ViewConstants.CUSTOM5);
            SelectItem item12 = new SelectItem(ViewConstants.NMQ6, ViewConstants.CUSTOM6);
            filterItems.add(item1);
            filterItems.add(item2);
            filterItems.add(item3);
            filterItems.add(item4);
            filterItems.add(item5);
            filterItems.add(item11);
            filterItems.add(item6);
            filterItems.add(item7);
            filterItems.add(item8);
            filterItems.add(item9);
            filterItems.add(item10);
            filterItems.add(item12);
        }
        return filterItems;
    }

    /**
     * @param meddraItems
     */
    public void setMeddraItems(List<SelectItem> meddraItems) {
        this.meddraItems = meddraItems;
    }

    /**
     * @return
     */
    public List<SelectItem> getMeddraItems() {
        logger.info("Fetching list of values for MEDDRA LOV in hierarchy search");
        if(meddraItems == null){
            meddraItems = new ArrayList<SelectItem>();
            SelectItem item1 = new SelectItem(ViewConstants.SOC, ViewConstants.SOC);
            SelectItem item2 = new SelectItem(ViewConstants.HLGT, ViewConstants.HLGT);
            SelectItem item3 = new SelectItem(ViewConstants.HLT, ViewConstants.HLT);
            SelectItem item4 = new SelectItem(ViewConstants.PT, ViewConstants.PT);
            meddraItems.add(item1);
            meddraItems.add(item2);
            meddraItems.add(item3);
            meddraItems.add(item4);
        }
        return meddraItems;
    }

    public void dictionaryVC(ValueChangeEvent valueChangeEvent) {
        logger.info("Refreshing Level LOV based on the dictionary selected");
        if(valueChangeEvent.getNewValue() != null && valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue()){
            if(ViewConstants.MEDDRA_DICTIONARY.equalsIgnoreCase((String)valueChangeEvent.getNewValue())){
                setLevelItems(getMeddraItems());
                this.setMeddraSearch(true);
                this.setLevel(ViewConstants.SOC);
            }else{
                setLevelItems(getFilterItems());
                this.setMeddraSearch(false);
                this.setLevel(ViewConstants.MQ1);
            }
        }
    }

    /**
     * @param levelItems
     */
    public void setLevelItems(List<SelectItem> levelItems) {
        this.levelItems = levelItems;
    }

    /**
     * @return
     */
    public List<SelectItem> getLevelItems() {
        return levelItems;
    }

    /**
     * @param actionEvent
     */
    public void onCancelCrsRiskPopup(ActionEvent actionEvent) {
        if(this.iconCRSChanged != null && this.iconCRSChanged.isVisible()){
            ADFUtils.showPopup(getCancelWarningPopup());
        }
        else{
            ADFUtils.setPageFlowScopeValue("isCancelClicked",true);
            cancelRisk();
        }
        
    }
    
    /**
     * @param actionEvent
     */
    public void onCancelCrsRiskWarningPopup(ActionEvent actionEvent) {

            cancelRisk();
        getCancelWarningPopup().hide();
    }
    
    private void cancelRisk(){
        String baseOrStaging = (String)ADFUtils.evaluateEL("#{pageFlowScope.manageCRSBean.baseOrStaging}");
        Row currentRow = null;
        String key = null;
        if(baseOrStaging != null && "BASE".equalsIgnoreCase(baseOrStaging)){
            DCIteratorBinding riskBaseIter = ADFUtils.findIterator("CrsContentBaseVOIterator");
            currentRow = riskBaseIter.getCurrentRow();
            key = riskBaseIter.getCurrentRowKeyString();
        }
        else{
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsContentVOIterator");
            currentRow = riskIter.getCurrentRow();
            key = riskIter.getCurrentRowKeyString();
            System.err.println("NIT 3 : "+currentRow.getAttribute("CrsId"));
        }
        logger.info("Closing CrsRisk Popup, rolling back any unsaved changes.");
        DCIteratorBinding iter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = iter.getViewObject();
        riskDefVO.clearCache();
        riskDefVO.executeQuery();
        if(riskDefTable != null){
            ResetUtils.reset(riskDefTable);
            riskDefTable.resetStampState();
        }
        if(riskDialog != null){
            ADFUtils.addPartialTarget(riskDialog);
        }
        riskDefVO.executeEmptyRowSet();
        RowSetIterator rs = riskDefVO.createRowSetIterator(null);
        while(rs.hasNext()){
            Row row = rs.next();
            if(row != null){
                row.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
                logger.info("Closing CrsRisk Popup -- refresh risk def row.");
            }
        }
        Row currRow = riskDefVO.getCurrentRow();
        if(currRow != null){
            currRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk def row.");
        }
        DCIteratorBinding relIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        ViewObject riskRelVO = relIter.getViewObject();
        Row relCurrRow = riskRelVO.getCurrentRow();
        
        if(relCurrRow != null){
            relCurrRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk relations row.");
        }
        
        riskRelVO.clearCache();
        riskRelVO.executeQuery();
        riskRelVO.executeEmptyRowSet();
        
        
        //START ADDITION BY NITISH FOR CANCEL ISSUE
        CrsContentVORowImpl selectedRow =
                   (CrsContentVORowImpl)ADFUtils.evaluateEL("#{bindings.CrsContentVOIterator.currentRow}");
        
        
                OperationBinding oper = ADFUtils.findOperation("Rollback");
                oper.execute();
                if (oper.getErrors().size() > 0)
                    ADFUtils.showFacesMessage("An internal error has occured. Please try later.", FacesMessage.SEVERITY_ERROR);
        DCBindingContainer bc =
            ADFUtils.findBindingContainerByName(ViewConstants.PAGE_DEF_SEARCH);
        DCIteratorBinding searchIter =  bc.findIteratorBinding("ECrsSearchVOIterator");
        if(searchIter.getEstimatedRowCount() == 0){
            Row row = searchIter.getViewObject().createRow();
            searchIter.getViewObject().insertRow(row);
            searchIter.getViewObject().setCurrentRow(row);
        }
        
        // get the selected row , by this you can get any attribute of that row
   System.err.println("NITKI : : " +selectedRow);
        ViewObject vo = (ViewObject)ADFUtils.findIterator("CrsContentVOIterator").getViewObject();
        vo.setWhereClause("CRS_ID = "+selectedRow.getAttribute("CrsId"));
        vo.executeQuery();
        
        System.err.println("NITKI 2 :: "+vo);
        
        if(vo.getEstimatedRowCount() > 0)
            vo.setCurrentRow(vo.first());

        setSelectedCrsName(selectedRow.getCrsName());
        setSelDesigneeList(null);
        List<String> designeeList = new ArrayList<String>();
        if (selectedRow.getDesignee() != null) {
            String[] designeeArray = selectedRow.getDesignee().split("[,]");
            if (designeeArray.length > 0) {
                for (int i = 0; i < designeeArray.length; i++) {
                    designeeList.add(designeeArray[i]);
                }
            }
            setSelDesigneeList(designeeList);
        }
        onClickSearch(null);
        
        //END ADDITION BY NITISH -- For cancel issue
                        
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        OperationBinding oper1 = ADFUtils.findOperation("initRiskRelation");
        oper1.getParamsMap().put("crsId", crsId);
        oper1.getParamsMap().put("status", getBaseOrStaging());
        logger.info("CrsId : "+crsId+" state: "+getBaseOrStaging());
        oper1.execute();
        if (oper1.getErrors().size() > 0) 
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
        //        if(riskDefTable != null)
        //            ADFUtils.addPartialTarget(riskDefTable);
        if(stagingTable != null){
            ADFUtils.addPartialTarget(stagingTable);
        }
        if(riskDefPopup != null){
            if (null != cntrlStatusBar && cntrlStatusBar.isRendered()){
                if(this.iconCRSSaved !=null)
                    this.iconCRSSaved.setVisible(false);
                if(this.iconCRSSaveError !=null)
                    this.iconCRSSaveError.setVisible(false);
                if(this.iconCRSChanged !=null)
                    this.iconCRSChanged.setVisible(false);
                ADFUtils.addPartialTarget(cntrlStatusBar);
            }
            riskDefPopup.hide();
        }
        if(copyPopup != null){
            if (null != cntrlStatusBarCopy && cntrlStatusBarCopy.isRendered()){
                if(this.iconCRSSaved !=null)
                    this.iconCopyCRSSaved.setVisible(false);
                this.iconCopyCRSSaveError.setVisible(false);
                if(this.iconCopyCRSChanged !=null)
                    this.iconCopyCRSChanged.setVisible(false);
                ADFUtils.addPartialTarget(cntrlStatusBarCopy);
            }
            copyPopup.hide();
        }
        if(baseOrStaging != null && "BASE".equalsIgnoreCase(baseOrStaging)){
            DCIteratorBinding riskBaseIter = ADFUtils.findIterator("CrsContentBaseVOIterator");
            riskBaseIter.setCurrentRowWithKey(key);
            riskBaseIter.getViewObject().setCurrentRow(currentRow);
        }
        else{
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsContentVOIterator");
            riskIter.setCurrentRowWithKey(key);
            riskIter.getViewObject().setCurrentRow(currentRow);
        }
    }

    /**
     * @param contentId
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param childScope
     */
    public void setChildScope(String childScope) {
        this.childScope = childScope;
    }

    /**
     * @return
     */
    public String getChildScope() {
        return childScope;
    }

    /**
     * @param actionEvent
     */
    public void executeHierarchyChild(ActionEvent actionEvent) {
            DCIteratorBinding childIter = ADFUtils.findIterator("HierarchyChildVOIterator");
            ViewObject childVO = childIter.getViewObject();
            logger.info("Executing hierachy child for selected content ID");
            childVO.setNamedWhereClauseParam("bContentId", ADFUtils.evaluateEL("#{row.ContentId}"));
            childVO.setRangeSize(-1);
            childVO.executeQuery();
            if (childVO.getEstimatedRowCount() > 0) {
                HierarchyChildUIBean parRow = new HierarchyChildUIBean(childVO.first());
                parRow = new HierarchyChildUIBean(childVO.first());
                logger.info("Executing hierachy child for selected content ID==" + parRow.getTmsDictContentId());
                childVO.setCurrentRow(childVO.first());
                HierarchyChildVORowImpl parVORow = (HierarchyChildVORowImpl)childVO.first();
                RowIterator rs = parVORow.getHierarchyChildDetailVO();
                parVORow.getHierarchyChildDetailVO().setRangeSize(-1);
                Row[] rows = parVORow.getHierarchyChildDetailVO().getAllRowsInRange();
                List<HierarchyChildUIBean> childRows = new ArrayList<HierarchyChildUIBean>();
//                while (rs.hasNext()) {
                for(Row childRow : rows){
//                    Row childRow = rs.next();
                    childRows.add(new HierarchyChildUIBean(childRow));
                }
                parRow.setChildren(childRows);
                hierChildList = new ArrayList<HierarchyChildUIBean>();
                hierChildList.add(parRow);
            }
            hierChildTreeModel = new ChildPropertyTreeModel(hierChildList, "children");
            getChildTreeTable().setVisible(Boolean.TRUE);
        
            ADFUtils.setPageFlowScopeValue("childVersion", ADFUtils.evaluateEL("#{row.DictVersion}"));
            ADFUtils.setPageFlowScopeValue("childDate", ADFUtils.evaluateEL("#{row.DictVersionDate}"));

            ADFUtils.addPartialTarget(getChildTreeTable());
    }

    /**
     * @param childTreeTable
     */
    public void setChildTreeTable(RichTreeTable childTreeTable) {
        this.childTreeTable = childTreeTable;
    }

    /**
     * @return
     */
    public RichTreeTable getChildTreeTable() {
        return childTreeTable;
    }

    /**
     * @param parentError
     */
    public void setParentError(RichPopup parentError) {
        this.parentError = parentError;
    }

    /**
     * @return
     */
    public RichPopup getParentError() {
        return parentError;
    }
    
    public void searchCrs(ActionEvent actionEvent) {
        String stoi = getSafetyTopicOfInterest();
        logger.info("Searching exisitng current safety topic of interests with entered value : " + stoi);
        DCIteratorBinding iter = ADFUtils.findIterator("CopyCrsRiskVOIterator");
        ViewObject crsSearchVO = iter.getViewObject();
        String stoiParam = "%";
        stoiParam = (null != stoi && !stoi.isEmpty()) ? stoi : stoiParam;
        crsSearchVO.setWhereClause("UPPER(SAFETY_TOPIC_OF_INTEREST) like UPPER('%"+stoiParam+"%')"+ " and STATE_ID = " + ModelConstants.STATE_ACTIVATED);
        logger.info("Searching Safety tpoic of Interest:: " + crsSearchVO.getQuery());
        crsSearchVO.executeQuery();
    }

    /**
     * @param safetyTopicOfInterest
     */
    public void setSafetyTopicOfInterest(String safetyTopicOfInterest) {
        this.safetyTopicOfInterest = safetyTopicOfInterest;
    }

    /**
     * @return
     */
    public String getSafetyTopicOfInterest() {
        return safetyTopicOfInterest;
    }

    /**
     * @param actionEvent
     */
    public void copyCrsRiskRelation(ActionEvent actionEvent) {
        //Clear the previously copied risk relation in same session if not saved to database
        DCIteratorBinding iter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = iter.getViewObject();
        Row currRow = riskDefVO.getCurrentRow();
        if(currRow != null){
            currRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk def row.");
        }
        DCIteratorBinding relIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        ViewObject riskRelVO = relIter.getViewObject();
        Row relCurrRow = riskRelVO.getCurrentRow();
        if(relCurrRow != null){
            relCurrRow.refresh(Row.REFRESH_REMOVE_NEW_ROWS | Row.REFRESH_WITH_DB_FORGET_CHANGES | Row.REFRESH_UNDO_CHANGES);
            logger.info("Closing CrsRisk Popup -- refresh risk relations row.");
        }
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");  
        String riskPurposeList = (String)ADFUtils.evaluateEL("#{copyRow.RiskPurposeList}");
        String safetyTopic = (String)ADFUtils.evaluateEL("#{copyRow.SafetyTopicOfInterest}");
        String socTerm = (String)ADFUtils.evaluateEL("#{copyRow.SocTerm}");
        Map params2 = new HashMap<String, Object>();
        params2.put("domainName", ADFUtils.evaluateEL("#{copyRow.DataDomain}"));
        Integer domainId = 1;
        try {
           domainId = (Integer) ADFUtils.executeAction("fetchDomainIdFromName", params2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map params1 = new HashMap<String, Object>();
        logger.info("Validating it this CRS "+crsId+" has this safety topic already.");
        params1.put("crsId", crsId);
        params1.put("safetyTopic", safetyTopic);
        params1.put("rpList", riskPurposeList);
        params1.put("domainId", domainId);
        params1.put("socTerm", socTerm);
//        params1.put("crsRiskId", ADFUtils.evaluateEL("#{bindings.CrsRiskId.inputValue}"));
        try {
            logger.info("Calling model method validateSafetyTopic");
            Boolean invalid = (Boolean)ADFUtils.executeAction("validateSafetyTopic", params1);
            if(invalid){
                ADFUtils.showFacesMessage(uiBundle.getString("STOI_UNIQUE_ERROR"), FacesMessage.SEVERITY_ERROR);
                return;
            }
        } catch (Exception e) {
            logger.error("Exception occured in validateSafetyTopic()"+e);
        }
        
//        DCBindingContainer dcbind =(DCBindingContainer)getBindings();
//        Boolean dirty = dcbind.getDataControl().isTransactionModified();
//        if(dirty){
//            ADFUtils.showPopup(pendingPopup);
//            return;
//        }
        
        ADFUtils.setPageFlowScopeValue("popupMode", "Edit");
        Long riskId = (Long)ADFUtils.evaluateEL("#{copyRow.CrsRiskId}");
        
//        String databaseList = (String)ADFUtils.evaluateEL("#{copyRow.DatabaseList}");
//        List<String> dbList = new ArrayList<String>();
//        if(databaseList != null){
//            String split[] = databaseList.split(",");
//            for(String db : split){
//                dbList.add(db.trim());
//            }
//        }
//        setSelDatabases(dbList);       
        logger.info("Copying crsRiskRelations from crsId : "+crsId+" riskID : "+riskId);
        
        List<String> rpList = new ArrayList<String>();
        if(riskPurposeList != null){
            if(riskPurposeList.endsWith(",")){
                riskPurposeList = riskPurposeList.substring(0, riskPurposeList.length()-1);
            }
            String split[] = riskPurposeList.split(",");
            for(String rp : split){
                rpList.add(rp.trim());
            }
        }
        setSelRiskPurposes(rpList);
        logger.info("Selected risk purposes "+rpList);
//        if(copyDBListBinding != null)
//            ResetUtils.reset(copyDBListBinding);
        if(copyRPListBinding != null)
            ResetUtils.reset(copyRPListBinding);
        Map params = new HashMap<String, Object>();
        logger.info("Copying from source risk Id : "+riskId+ " destination crsId "+crsId);
        params.put("srcRiskId", riskId);
        params.put("destCrsId", crsId);
        try {
            logger.info("Calling model method copyCurrentRiskRelation");
            ADFUtils.executeAction("copyCurrentRiskRelation", params);
        } catch (Exception e) {
            logger.error("Exception occured in copyCrsRiskRelation()"+e);
        }
        if (null != cntrlStatusBarCopy){
            cntrlStatusBarCopy.setRendered(true);
        }
        showStatus(ViewConstants.CRS_MODIFIED);
        copyPanel.setVisible(true);
        ADFUtils.addPartialTarget(copyPanel);
        ADFUtils.addPartialTarget(socTermSOCCopy);
        ADFUtils.addPartialTarget(searchCriteriaDetailsCopy);
    }

    /**
     * @return
     */
    public BindingContainer getBindings() {
        return BindingContext.getCurrent().getCurrentBindingsEntry();
    }

    /**
     * @param copyPopup
     */
    public void setCopyPopup(RichPopup copyPopup) {
        this.copyPopup = copyPopup;
    }

    /**
     * @return
     */
    public RichPopup getCopyPopup() {
        return copyPopup;
    }

    /**
     * @param copyPanel
     */
    public void setCopyPanel(RichPanelGroupLayout copyPanel) {
        this.copyPanel = copyPanel;
    }

    /**
     * @return
     */
    public RichPanelGroupLayout getCopyPanel() {
        return copyPanel;
    }

    /**
     * @param pendingPopup
     */
    public void setPendingPopup(RichPopup pendingPopup) {
        this.pendingPopup = pendingPopup;
    }

    /**
     * @return
     */
    public RichPopup getPendingPopup() {
        return pendingPopup;
    }

    /**
     * @param actionEvent
     */
    public void onClickYes(ActionEvent actionEvent) {
        logger.info("On click yes on the pending changes popup, Rolling back");
        OperationBinding oper = ADFUtils.findOperation("Rollback");
        oper.execute();
        if (oper.getErrors().size() > 0) 
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
         else
            copyCrsRiskRelation(actionEvent);
    }

    /**
     * @param actionEvent
     */
    public void onClickCopy(ActionEvent actionEvent) {
        logger.info("Opening the blank copy Risk Defintions popup");
        setSafetyTopicOfInterest(null);
        if(stoiBinding != null)
            ResetUtils.reset(stoiBinding);
        //setSelDatabases(null);
        //setSelDesigneeList(null);
        DCIteratorBinding iter = ADFUtils.findIterator("CopyCrsRiskVOIterator");
        ViewObject crsSearchVO = iter.getViewObject();
        crsSearchVO.executeEmptyRowSet();
        if (null != copyRiskDefPopupPanel){
            ResetUtils.reset(copyRiskDefPopupPanel);
        }
        if(riskDefPopupPanel != null)
            ResetUtils.reset(riskDefPopupPanel);
        if(copyPanel != null)
            copyPanel.setVisible(Boolean.FALSE);
        if(copySuccessMessage != null)
            copySuccessMessage.setVisible(Boolean.FALSE);
        ADFUtils.showPopup(copyPopup);
    }

    /**
     * @param actionEvent
     */
    public void deleteCopiedRiskDefs(ActionEvent actionEvent) {
        logger.info("Delete selected risk definitions in copy popup.");
        DCIteratorBinding riskDefIter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = riskDefIter.getViewObject();
        Row[] rows= riskDefVO.getFilteredRows("SelectAttr", Boolean.TRUE);
        for(Row row : rows){
            row.remove();
        }
        showStatus(ViewConstants.CRS_MODIFIED);
    }

    /**
     * @param copyRiskDefTable
     */
    public void setCopyRiskDefTable(RichTable copyRiskDefTable) {
        this.copyRiskDefTable = copyRiskDefTable;
    }

    /**
     * @return
     */
    public RichTable getCopyRiskDefTable() {
        return copyRiskDefTable;
    }

    /**
     * @param savedSuccessMessage
     */
    public void setSavedSuccessMessage(RichPanelLabelAndMessage savedSuccessMessage) {
        this.savedSuccessMessage = savedSuccessMessage;
    }

    /**
     * @return
     */
    public RichPanelLabelAndMessage getSavedSuccessMessage() {
        return savedSuccessMessage;
    }

    /**
     * @param copySuccessMessage
     */
    public void setCopySuccessMessage(RichPanelLabelAndMessage copySuccessMessage) {
        this.copySuccessMessage = copySuccessMessage;
    }

    /**
     * @return
     */
    public RichPanelLabelAndMessage getCopySuccessMessage() {
        return copySuccessMessage;
    }

    /**
     * @param hiddenPopupAlign
     */
    public void setHiddenPopupAlign(RichOutputText hiddenPopupAlign) {
        this.hiddenPopupAlign = hiddenPopupAlign;
    }

    /**
     * @return
     */
    public RichOutputText getHiddenPopupAlign() {
        return hiddenPopupAlign;
    }

    /**
     * @param stoiBinding
     */
    public void setStoiBinding(RichInputText stoiBinding) {
        this.stoiBinding = stoiBinding;
    }

    /**
     * @return
     */
    public RichInputText getStoiBinding() {
        return stoiBinding;
    }

    /**
     * @param copyDBListBinding
     */
    public void setCopyDBListBinding(RichSelectManyChoice copyDBListBinding) {
        this.copyDBListBinding = copyDBListBinding;
    }

    /**
     * @return
     */
    public RichSelectManyChoice getCopyDBListBinding() {
        return copyDBListBinding;
    }

    /**
     * @param copyRPListBinding
     */
    public void setCopyRPListBinding(RichSelectManyChoice copyRPListBinding) {
        this.copyRPListBinding = copyRPListBinding;
    }

    /**
     * @return
     */
    public RichSelectManyChoice getCopyRPListBinding() {
        return copyRPListBinding;
    }

    /**
     * @param searchSwitherBinding
     */
    public void setSearchSwitherBinding(UIXSwitcher searchSwitherBinding) {
        this.searchSwitherBinding = searchSwitherBinding;
    }

    /**
     * @return
     */
    public UIXSwitcher getSearchSwitherBinding() {
        return searchSwitherBinding;
    }

    /**
     * @param selectionEvent
     */
    public void baseContentVOSelectionListener(SelectionEvent selectionEvent) {
        logger.info("Start- ManageCRSBean:baseContentVOSelectionListener--");
        ADFUtils.invokeEL("#{bindings.CrsContentBaseVO.collectionModel.makeCurrent}", new Class[] {SelectionEvent.class},
                                 new Object[] { selectionEvent });
        // get the selected row , by this you can get any attribute of that row
        CrsContentBaseVORowImpl selectedRow =
                   (CrsContentBaseVORowImpl)ADFUtils.evaluateEL("#{bindings.CrsContentBaseVOIterator.currentRow}");
        setSelectedCrsName(selectedRow.getCrsName());
        ADFUtils.setPageFlowScopeValue("crsId", selectedRow.getCrsId());
        ADFUtils.setPageFlowScopeValue("crsCurrentPublished", selectedRow.getCrsCurrentPublished());
        ADFUtils.setPageFlowScopeValue("flowType", this.getFlowType());
        
        Map params1 = new HashMap<String, Object>();
        params1.put("role", "CRS_TASL");
        params1.put("userName", selectedRow.getTaslName());
        
        Map params2 = new HashMap<String, Object>();
        params2.put("role", "CRS_BSL");
        params2.put("userName", selectedRow.getBslName());
        String taslName = "";
        String bslName = "";
        
        try {
            if(selectedRow.getTaslName() != null && !"".equalsIgnoreCase(selectedRow.getTaslName()))
            taslName = (String) ADFUtils.executeAction("findRoleDescription", params1);
            if(selectedRow.getBslName() != null && !"".equalsIgnoreCase(selectedRow.getBslName()))
            bslName = (String) ADFUtils.executeAction("findRoleDescription", params2);
        } catch (Exception e) {
        }
        
        this.setSelectedCrsId(selectedRow.getCrsId().toString());
        this.setSelectedState(selectedRow.getStateName());
        this.setSelectedTASL(taslName);
        if(selectedRow.getDesigneeName() != null && !"".equalsIgnoreCase(selectedRow.getDesigneeName())){
        this.setSelectedDesignee(selectedRow.getDesigneeName());
        }else{
            this.setSelectedDesignee("");   
        }
        this.setSelectedStatus("");
        this.setSelectedBSL(bslName);
        
//        this.setSelectedCrsId(selectedRow.getCrsId().toString());
//        Map params = new HashMap<String, Object>();
//        params.put("stateId", selectedRow.getStateId());
//        
//        Map params1 = new HashMap<String, Object>();
//        params1.put("role", "CRS_TASL");
//        params1.put("userName", selectedRow.getTaslName());
//        
//        Map params2 = new HashMap<String, Object>();
//        params2.put("role", "CRS_BSL");
//        params2.put("userName", selectedRow.getBslName());
//        String state = "";
//        String taslName = "";
//        String bslName = "";
//        try {
//            state = (String) ADFUtils.executeAction("findStateDescription", params);
//            taslName = (String) ADFUtils.executeAction("findRoleDescription", params1);
//            bslName = (String) ADFUtils.executeAction("findRoleDescription", params2);
//        } catch (Exception e) {
//        }
//        this.setSelectedState(state);
//        this.setSelectedTASL(taslName);
//        this.setSelectedDesignee(selectedRow.getDesigneeName());
//        this.setSelectedStatus("");
//        this.setSelectedBSL(bslName);
        setSelDesigneeList(null);
        List<String> designeeList = new ArrayList<String>();
        if (selectedRow.getDesignee() != null) {
            String[] designeeArray = selectedRow.getDesignee().split("[,]");
            if (designeeArray.length > 0) {
                for (int i = 0; i < designeeArray.length; i++) {
                    designeeList.add(designeeArray[i]);
                }
            }
            setSelDesigneeList(designeeList);
        }
        try {
//            Boolean isMultiVersionsAvailable = (Boolean) ADFUtils.executeAction("isMultiVersionsAvailable", null);
//            if(isMultiVersionsAvailable){
//            ADFUtils.setPageFlowScopeValue("crsCurrentPublished","Z");
//            }else{
//            ADFUtils.setPageFlowScopeValue("crsCurrentPublished","N");    
//            }
            
            String isMultiVersionsAvailable = (String) ADFUtils.executeAction("isMultiVersionsAvailableString", null);
            ADFUtils.setPageFlowScopeValue("crsCurrentPublished",isMultiVersionsAvailable);
            
        } catch (Exception e) {
        }
        
        logger.info("End- ManageCRSBean:baseContentVOSelectionListener--");
    }

    public void refreshRepository(ActionEvent actionEvent) {
        Map params = new HashMap<String, Object>();
        params.put("crsId", ADFUtils.getPageFlowScopeValue("crsId"));
        logger.info("Executing refreshRepository function call for crsID :: "+ADFUtils.getPageFlowScopeValue("crsId"));
        try {
            ADFUtils.executeAction("refreshRepository", params);
            setRepoRefreshed(Boolean.TRUE);
            ADFUtils.addPartialTarget(riskDefTable);
            ADFUtils.addPartialTarget(stagingTable);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * @param repoRefreshed
     */
    public void setRepoRefreshed(Boolean repoRefreshed) {
        this.repoRefreshed = repoRefreshed;
    }

    /**
     * @return
     */
    public Boolean getRepoRefreshed() {
        return repoRefreshed;
    }


    /**
     * @return
     */
    public String initializeCreateUpdateCRS() {
        // chk if there exists a CRS in staging table with the same as selected CRS in base table
        if (!ViewConstants.ANONYMOUS_ROLE.equalsIgnoreCase(userName) &&
            ModelConstants.BASE_FACET.equals(getBaseOrStaging()) &&
            ViewConstants.FLOW_TYPE_UPDATE.equals(getFlowType())) {
            setReasonForChange(null);
            ADFUtils.showPopup(getModifyReasonChngPopup());
            return "toSearch";
        } else
            return "createUpdateCRS";
    }

    /**
     * @param baseOrStaging
     */
    public void setBaseOrStaging(String baseOrStaging) {
        this.baseOrStaging = baseOrStaging;
    }

    /**
     * @return
     */
    public String getBaseOrStaging() {
        return baseOrStaging;
    }

    /**
     * @param hierChildTreeModel
     */
    public void setHierChildTreeModel(ChildPropertyTreeModel hierChildTreeModel) {
        this.hierChildTreeModel = hierChildTreeModel;
    }

    /**
     * @return
     */
    public ChildPropertyTreeModel getHierChildTreeModel() {
        return hierChildTreeModel;
    }

    /**
     * @param hierChildList
     */
    public void setHierChildList(List<HierarchyChildUIBean> hierChildList) {
        this.hierChildList = hierChildList;
    }

    /**
     * @return
     */
    public List<HierarchyChildUIBean> getHierChildList() {
        return hierChildList;
    }

    /**
     * @param actionEvent
     */
    public void reactivateCRS(ActionEvent actionEvent) {
        // Add event code here...
        setReasonForChange(null);
        ADFUtils.showPopup(getReactivatePopupBinding());
    }

    /**
     * @param actionEvent
     */
    public void retireCRS(ActionEvent actionEvent) {
        // Add event code here...
        setReasonForChange(null);
        ADFUtils.showPopup(getRetirePopupBinding());
    }

    /**
     * @param searchBaseTableBinding
     */
    public void setSearchBaseTableBinding(RichTable searchBaseTableBinding) {
        this.searchBaseTableBinding = searchBaseTableBinding;
    }

    /**
     * @return
     */
    public RichTable getSearchBaseTableBinding() {
        return searchBaseTableBinding;
    }

    /**
     * @param reasonChangePopup
     */
    public void setReasonChangePopup(RichDialog reasonChangePopup) {
        this.reasonChangePopup = reasonChangePopup;
    }

    /**
     * @return
     */
    public RichDialog getReasonChangePopup() {
        return reasonChangePopup;
    }

    /**
     * @param modifyReasonChngPopup
     */
    public void setModifyReasonChngPopup(RichPopup modifyReasonChngPopup) {
        this.modifyReasonChngPopup = modifyReasonChngPopup;
    }

    /**
     * @return
     */
    public RichPopup getModifyReasonChngPopup() {
        return modifyReasonChngPopup;
    }

    /**
     * @param retireReactvteReasonPopup
     */
    public void setRetireReactvteReasonPopup(RichInputText retireReactvteReasonPopup) {
        this.retireReactvteReasonPopup = retireReactvteReasonPopup;
    }

    /**
     * @return
     */
    public RichInputText getRetireReactvteReasonPopup() {
        return retireReactvteReasonPopup;
    }

    /**
     * @param dialogEvent
     */
    public void retireConfirmDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) {
            logger.info("Start- ManageCRSBean:retireConfirmDialogListener--");
            String reasonForChangeText = getReasonForChange();
            CrsContentBaseVORowImpl row =
                (CrsContentBaseVORowImpl)ADFUtils.evaluateEL("#{bindings.CrsContentBaseVOIterator.currentRow}");
            if (row.getCrsId() != null) {
                if (null == reasonForChangeText || reasonForChangeText.isEmpty()){
                    ADFUtils.showFacesMessage(uiBundle.getString("REASON_FOR_CHANGE_REQUIRED"), FacesMessage.SEVERITY_ERROR);
                } else {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("pCRSId", row.getCrsId());
                    params.put("pReasonForChange", getReasonForChange());
                    try {
                        String msg =
                            (String)ADFUtils.executeAction("retireCrs", params);
                        logger.info("Model result message--"+msg);
                        if (!ModelConstants.PLSQL_CALL_SUCCESS.equals(msg)) {
                            ADFUtils.setEL("#{pageFlowScope.plsqlerror}", msg);
                            ADFUtils.showPopup(getErrorPLSqlPopup());
                            return;
                        }
                        onClickSearch(new ActionEvent((UIComponent)dialogEvent.getSource()));
                        getSearchBaseTableBinding().resetStampState();
                        ADFUtils.addPartialTarget(getSearchBaseTableBinding());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            logger.info("End- ManageCRSBean:retireConfirmDialogListener--");
        }
    }

    /**
     * @param dialogEvent
     */
    public void reactivateConfirmDialogListener(DialogEvent dialogEvent) {
        // Add event code here...
        if (DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())) {
            logger.info("Start- ManageCRSBean:reactivateConfirmDialogListener--");
            CrsContentBaseVORowImpl row =
                (CrsContentBaseVORowImpl)ADFUtils.evaluateEL("#{bindings.CrsContentBaseVOIterator.currentRow}");
            if (row.getCrsId() != null) {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("pCRSId", row.getCrsId());
                params.put("pReasonForChange", getReasonForChange());
                try {
                    String msg =
                        (String)ADFUtils.executeAction("reactivateCrs",
                                                       params);
                    if (!ModelConstants.PLSQL_CALL_SUCCESS.equals(msg)) {
                        ADFUtils.setEL("#{pageFlowScope.plsqlerror}", msg);
                        ADFUtils.showPopup(getErrorPLSqlPopup());
                    }
                    onClickSearch(new ActionEvent((UIComponent)dialogEvent.getSource()));
                    getSearchBaseTableBinding().resetStampState();
                    ADFUtils.addPartialTarget(getSearchBaseTableBinding());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            logger.info("End- ManageCRSBean:reactivateConfirmDialogListener--");
        }
    }

    /**
     * @param reactivatePopupBinding
     */
    public void setReactivatePopupBinding(RichPopup reactivatePopupBinding) {
        this.reactivatePopupBinding = reactivatePopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getReactivatePopupBinding() {
        return reactivatePopupBinding;
    }

    /**
     * @param retirePopupBinding
     */
    public void setRetirePopupBinding(RichPopup retirePopupBinding) {
        this.retirePopupBinding = retirePopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getRetirePopupBinding() {
        return retirePopupBinding;
    }

    /**
     * @param reasonForChange
     */
    public void setReasonForChange(String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }

    /**
     * @return
     */
    public String getReasonForChange() {
        return reasonForChange;
    }

    /**
     * @param errorPLSqlPopup
     */
    public void setErrorPLSqlPopup(RichPopup errorPLSqlPopup) {
        this.errorPLSqlPopup = errorPLSqlPopup;
    }

    /**
     * @return
     */
    public RichPopup getErrorPLSqlPopup() {
        return errorPLSqlPopup;
    }

    public String onClickModifyCrs() {
        logger.info("Start- ManageCRSBean:onClickModifyCrs--");
        DCBindingContainer bc =
            ADFUtils.findBindingContainerByName(ViewConstants.PAGE_DEF_SEARCH);
        DCIteratorBinding iter =
            bc.findIteratorBinding("CrsContentBaseVOIterator");
        Long crsId = null;
        if (iter.getCurrentRow() != null) {
            crsId = (Long)iter.getCurrentRow().getAttribute("CrsId");

            //invoke vc on stg table with this crs id
            boolean isCrsFound = Boolean.FALSE;
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("pCrsId", crsId);
            OperationBinding op =
                ADFUtils.getOperBindFromPageDef(ViewConstants.PAGE_DEF_SEARCH,
                                                "findByCrsFromStg");
            op.getParamsMap().put("pCrsId", crsId);
            isCrsFound = (Boolean)op.execute();
            logger.info("isCrsFound --"+isCrsFound);
            if (op.getErrors() != null && op.getErrors().size() > 0) {
                logger.info("Error occured from findByCrsFromStg,Navigating to search page --");
                ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"),
                                          FacesMessage.SEVERITY_ERROR);
                setBaseOrStaging(ModelConstants.BASE_FACET);
                ADFUtils.closeDialog(getModifyReasonChngPopup());
                return "navToSearch";
            } else {
                logger.info("If isCrsFound true Navigating to search page ---");
                // if found - show faces message that the CRS already in update process
                if (isCrsFound) {
                    ADFUtils.showFacesMessage("The selected CRS is already in update process",
                                              FacesMessage.SEVERITY_INFO);
                    setBaseOrStaging(ModelConstants.BASE_FACET);
                    ADFUtils.closeDialog(getModifyReasonChngPopup());
                    return "navToSearch";
                } else {
                    logger.info("If isCrsFound false invoke modifyCrs ---");
                    // if NOT found - call MODIDY_CRS
                    String resultMsg = null;
                    op =
 ADFUtils.getOperBindFromPageDef(ViewConstants.PAGE_DEF_SEARCH, "modifyCrs");
                    op.getParamsMap().put("pCRSId", crsId);
                    op.getParamsMap().put("pReasonForChange", getReasonForChange());
                    resultMsg = (String)op.execute();

                    if (op.getErrors() != null && op.getErrors().size() > 0) {
                        logger.info("If error from invoke modifyCrs,navigate to search page ---");
                        ADFUtils.setEL("#{pageFlowScope.plsqlerror}", resultMsg);
                        ADFUtils.showPopup(getErrorPLSqlPopup());
                        setBaseOrStaging(ModelConstants.BASE_FACET);
                        ADFUtils.closeDialog(getModifyReasonChngPopup());
                        return "navToSearch";
                    } else {
                        logger.info("If succes from modifyCrs,set crsid to current row ---");
                        // if PL/SQL call return value is success - set current row of staging table to CRS ID
                        if (ModelConstants.PLSQL_CALL_SUCCESS.equals(resultMsg)) {
                            //set the staging table to current crs id
                            bc.findIteratorBinding("CrsContentVOIterator").getViewObject().applyViewCriteria(null);
                            bc.findIteratorBinding("CrsContentVOIterator").executeQuery();
                            ADFUtils.setEL("#{pageFlowScope.crsId}", crsId);

                            // page should navigate to add details page
                            op =
 ADFUtils.getOperBindFromPageDef(ViewConstants.PAGE_DEF_CREATE,
                                 "setCurrentRowWithKeyValue");
                            op.execute();
                            if (op.getErrors() != null &&
                                op.getErrors().size() > 0) {
                                ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"),
                                                          FacesMessage.SEVERITY_ERROR);
                                setBaseOrStaging(ModelConstants.BASE_FACET);
                                ADFUtils.closeDialog(getModifyReasonChngPopup());
                                return "navToSearch";
                            } else {
                                // set mode to staging
                                setBaseOrStaging(ModelConstants.STAGING_FACET);
                                ADFUtils.closeDialog(getModifyReasonChngPopup());
                                this.setCurrReleaseStatus("P");
                                return "navToCreate";
                            }
                        } else {
                            setBaseOrStaging(ModelConstants.BASE_FACET);
                            ADFUtils.setEL("#{pageFlowScope.plsqlerror}", resultMsg);
                            ADFUtils.showPopup(getErrorPLSqlPopup());
                            ADFUtils.closeDialog(getModifyReasonChngPopup());
                            return "navToSearch";
                        }
                    }
                }
            }
        } else {
            logger.info(" current row null ,navigating to search page ---");
            ADFUtils.showFacesMessage(uiBundle.getString("NAV_ERROR"),
                                      FacesMessage.SEVERITY_INFO);
            setBaseOrStaging(ModelConstants.BASE_FACET);
            ADFUtils.closeDialog(getModifyReasonChngPopup());
            return "navToSearch";
        }
        //default nav to createUpdate
        //return "createUpdate";
    }

    /**
     * @return
     */
    public String cancelModifyCrs() {
        // Add event code here...
        ADFUtils.closeDialog(getModifyReasonChngPopup());
        return "navToSearch";
    }

    /**
     * @param actionEvent
     */
    public void refreshRepoInPopup(ActionEvent actionEvent) {
        logger.info(" Start-ManageCRSBean :refreshRepoInPopup ---");
        Map params = new HashMap<String, Object>();
        params.put("crsId", ADFUtils.getPageFlowScopeValue("crsId"));
        logger.info("Executing refreshRepository function call for crsId :: "+ADFUtils.getPageFlowScopeValue("crsId"));
        try {
            ADFUtils.executeAction("refreshRepository", params);
            setRepoRefreshed(Boolean.TRUE);
            ADFUtils.addPartialTarget(riskDefTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(" End-ManageCRSBean :refreshRepoInPopup ---");
    }

    /**
     * @return
     */
    public boolean isCrsFieldsUpdatable1() {
        return crsFieldsUpdatable;
    }

    /**
     * @param filterBy1
     */
    public void setFilterBy1(String filterBy1) {
        this.filterBy1 = filterBy1;
    }

    /**
     * @return
     */
    public String getFilterBy1() {
        return filterBy1;
    }

    /**
     * @param filterBy2
     */
    public void setFilterBy2(String filterBy2) {
        this.filterBy2 = filterBy2;
    }

    /**
     * @return
     */
    public String getFilterBy2() {
        return filterBy2;
    }

    /**
     * @param filterBy3
     */
    public void setFilterBy3(String filterBy3) {
        this.filterBy3 = filterBy3;
    }

    /**
     * @return
     */
    public String getFilterBy3() {
        return filterBy3;
    }

    /**
     * @param filterValue1
     */
    public void setFilterValue1(String filterValue1) {
        this.filterValue1 = filterValue1;
    }

    /**
     * @return
     */
    public String getFilterValue1() {
        return filterValue1;
    }

    /**
     * @param filterValue2
     */
    public void setFilterValue2(String filterValue2) {
        this.filterValue2 = filterValue2;
    }

    /**
     * @return
     */
    public String getFilterValue2() {
        return filterValue2;
    }

    /**
     * @param filterValue3
     */
    public void setFilterValue3(String filterValue3) {
        this.filterValue3 = filterValue3;
    }

    /**
     * @return
     */
    public String getFilterValue3() {
        return filterValue3;
    }

    /**
     * @param filterCri1
     */
    public void setFilterCri1(String filterCri1) {
        this.filterCri1 = filterCri1;
    }

    /**
     * @return
     */
    public String getFilterCri1() {
        return filterCri1;
    }

    /**
     * @param filterCri2
     */
    public void setFilterCri2(String filterCri2) {
        this.filterCri2 = filterCri2;
    }

    /**
     * @return
     */
    public String getFilterCri2() {
        return filterCri2;
    }

    /**
     * @param actionEvent
     */
    public void onOkFilter(ActionEvent actionEvent) {
        logger.info("Performing advanced filter on the table");
        DCIteratorBinding iter = null;
        if (null != this.baseOrStaging && ViewConstants.STAGING.equals(this.baseOrStaging)){
            iter = ADFUtils.findIterator("CrsRiskVOIterator");
        } else {
            iter = ADFUtils.findIterator("CrsRiskBaseVOIterator"); 
        }
        if (null != iter){
            ViewObject riskVO = iter.getViewObject();
            Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
            StringBuilder whereClause = new StringBuilder("CRS_ID = "+crsId);
            if(filterBy1 != null && filterValue1 != null)
                whereClause.append(" AND ("+filterBy1 + " LIKE '"+filterValue1+"' ");
            if(filterBy2 != null && filterValue2 != null)
                whereClause.append(filterCri1 + " " + filterBy2 + " LIKE '"+filterValue2+"' ");
            if(filterBy3 != null && filterValue3 != null)
                whereClause.append(filterCri2 + " " + filterBy3 + " LIKE '"+filterValue3+"' ");  
            if(filterBy1 != null && filterValue1 != null)
                whereClause.append(")");
            riskVO.setWhereClause(whereClause.toString());
            System.err.println(riskVO.getQuery());
            riskVO.executeQuery(); 
        }
        advancedFilterPopup.hide();
    }

    /**
     * Fetches the dictionary version from session
     */
    public void initManageCrs(){
        logger.info("Initalizing CRS taskflow, fetching the dictionary version from session");
        String dictVersion = (String)ADFUtils.getSessionScopeValue("dictVersion");
        if(dictVersion == null){
            OperationBinding oper = ADFUtils.findOperation("fetchDictionaryVersion");
            dictVersion = (String)oper.execute();  
            ADFUtils.setSessionScopeValue("dictVersion", dictVersion);
            logger.info("Dict Version is :: "+dictVersion);
        }
    }

    /**
     * @param advancedFilterPopup
     */
    public void setAdvancedFilterPopup(RichPopup advancedFilterPopup) {
        this.advancedFilterPopup = advancedFilterPopup;
    }

    /**
     * @return
     */
    public RichPopup getAdvancedFilterPopup() {
        return advancedFilterPopup;
    }

    /**
     * @param actionEvent
     */
    public void clearFilters(ActionEvent actionEvent) {
        logger.info("Clearing advanced filters");
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        OperationBinding oper = ADFUtils.findOperation("initRiskRelation");
        oper.getParamsMap().put("crsId", crsId);
        oper.getParamsMap().put("status", getBaseOrStaging());
        oper.execute();
        if (oper.getErrors().size() > 0) 
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
        setFilterBy1(null);
        setFilterBy2(null);
        setFilterBy3(null);
        setFilterValue1(null);
        setFilterValue2(null);
        setFilterValue3(null);
        setFilterCri1("OR");
        setFilterCri2("OR");
    }

    /**
     * Reload the iteractors on search page.
     */
    public void reloadSearchResults() {
        // Add event code here...
        ADFUtils.closeDialog(getCrsPublishPopupBinding());
        DCBindingContainer bc =
            ADFUtils.findBindingContainerByName(ViewConstants.PAGE_DEF_SEARCH);
        if (bc != null) {
            DCIteratorBinding stgIter =
                bc.findIteratorBinding("CrsContentVOIterator");
            if (stgIter != null) {
                stgIter.executeQuery();
            }
            DCIteratorBinding baseIter =
                bc.findIteratorBinding("CrsContentBaseVOIterator");
            if (baseIter != null) {
                baseIter.executeQuery();
            }
            if (getSearchBaseTableBinding() != null)
                getSearchBaseTableBinding().resetStampState();
            if (getSearchStagingTableBinding() != null)
                getSearchStagingTableBinding().resetStampState();
        }
    }

    /**
     * @param searchStagingTableBinding
     */
    public void setSearchStagingTableBinding(RichTable searchStagingTableBinding) {
        this.searchStagingTableBinding = searchStagingTableBinding;
    }

    /**
     * @return
     */
    public RichTable getSearchStagingTableBinding() {
        return searchStagingTableBinding;
    }

    /**
     * @param currReleaseStatus
     */
    public void setCurrReleaseStatus(String currReleaseStatus) {
        this.currReleaseStatus = currReleaseStatus;
    }

    /**
     * @return
     */
    public String getCurrReleaseStatus() {
        return currReleaseStatus;
    }

    /**
     * This methodd default reason for change to 'initial version'
     * and shows the pubish popup
     * @param actionEvent
     */
    public void onClickPublish(ActionEvent actionEvent) {
        //check if already reasonForChange is popultaed ,then show it
        String reason =
            (String)ADFUtils.evaluateEL("#{bindings.ReasonForChange.inputValue}");
        //default reason for change to initial version
        if (ViewConstants.isNotEmpty(reason) &&
            !"None".equalsIgnoreCase(reason))
            reasonForChange = reason;
        else
            reasonForChange = ViewConstants.REASON_DEFAULT_VALUE;
        ADFUtils.showPopup(getPublishPopupBinding());
    }

    /**
     * @param publishPopupBinding
     */
    public void setPublishPopupBinding(RichPopup publishPopupBinding) {
        this.publishPopupBinding = publishPopupBinding;
    }

    /**
     * @return
     */
    public RichPopup getPublishPopupBinding() {
        return publishPopupBinding;
    }

    /**
     * @param submitApprovalPopup
     */
    public void setSubmitApprovalPopup(RichPopup submitApprovalPopup) {
        this.submitApprovalPopup = submitApprovalPopup;
    }

    /**
     * @return
     */
    public RichPopup getSubmitApprovalPopup() {
        return submitApprovalPopup;
    }

    /**
     * This method write header data to risk definition and PT report.
     * @param sheet
     */
    private void writeHeaderData(Sheet sheet,int firstPalletStartIndx,
                                 int firstPalletEndIndx,
                                 int secondPalletStartIndx,
                                 int secondPalletEndIndx) {
        //        Excel report Header data to include
        int count = 6;
        //invoke prepareStatesMap to get state names
        if (statesMap == null || (statesMap != null && statesMap.size() == 0))
            prepareStatesMap();
        
        //  •1 CRS Name
        org.apache.poi.ss.usermodel.Row row1 = sheet.createRow(count);
        Cell cell11 = row1.createCell((short)firstPalletStartIndx);
        cell11.setCellValue("CRS Name :" +
                            ADFUtils.evaluateEL("#{pageFlowScope.crsName}"));
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell11.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
        //•1 CRS ID
        //CRS ID
        Cell cell12 = row1.createCell((short)secondPalletStartIndx);
        cell12.setCellValue("CRS ID : " +
                            (Long)ADFUtils.getPageFlowScopeValue("crsId"));
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell12.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, secondPalletStartIndx, secondPalletEndIndx));
        count++;
        //        • 2 Dictionary Version
        org.apache.poi.ss.usermodel.Row row2 = sheet.createRow(count);
        //dictionary version
        Cell cell21 = row2.createCell((short)firstPalletStartIndx);
        cell21.setCellValue("Dictionary Version: " +
                            ADFUtils.evaluateEL("#{sessionScope.dictVersion}"));
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell21.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
        //Status //        • 2 Status (Active or Inactive)
        String status = "";
        if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
            int stateIdBase =
                (Integer)ADFUtils.evaluateEL("#{bindings.StateIdBase.inputValue}");
            if (stateIdBase == ModelConstants.STATE_RETIRED.intValue())
                status = ModelConstants.CRS_INACTIVE;
            else
                status = ModelConstants.CRS_ACTIVE;
        }
        Cell cell22 = row2.createCell((short)secondPalletStartIndx);
        // TODO
        cell22.setCellValue("Status: " + status);
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell22.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, secondPalletStartIndx, secondPalletEndIndx));
        count++;

        //•3 Date and time the report is run
        //Report time
        org.apache.poi.ss.usermodel.Row row3 = sheet.createRow(count);
        Cell cell31 = row3.createCell((short)firstPalletStartIndx);
        cell31.setCellValue("Downloaded Time: " +
                            ModelConstants.getCustomTimeStamp());
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell31.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
        //•3 Release Status (CURRENT or PENDING)
        String relFlag =
            (String)ADFUtils.evaluateEL("#{bindings.ReleaseStatusFlag.inputValue}");
        String relstatus = "";
        if ("P".equals(relFlag))
            relstatus = ViewConstants.PENDING;
        else
            relstatus = ViewConstants.CURRENT;
        Cell cell32 = row3.createCell((short)secondPalletStartIndx);
        cell32.setCellValue("Release Status: " + relstatus);
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell32.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, secondPalletStartIndx, secondPalletEndIndx));
        sheet.setColumnWidth(4, 6000);
        count++;
        //        •4 State (only displays the value for PENDING CRSs
        //        •4 BSL
        //BSL
        org.apache.poi.ss.usermodel.Row row4 = sheet.createRow(count);
       
        Cell cell41 = row4.createCell((short)firstPalletStartIndx);
        int stateIdstg = 0;
        if (ModelConstants.STAGING_FACET.equals(getBaseOrStaging())) {
            stateIdstg =
                (Integer)ADFUtils.evaluateEL("#{bindings.StateId.inputValue}");
        }
        cell41.setCellValue("State:  " + (statesMap.get(stateIdstg) == null ?
                            "" : statesMap.get(stateIdstg)));
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell41.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
        
        Cell cell42 = row4.createCell((short)secondPalletStartIndx);
        String bsl = null;
        if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
            bsl =
(String)ADFUtils.evaluateEL("#{bindings.BslNameBase.inputValue}");
        } else
            bsl =
(String)ADFUtils.evaluateEL("#{bindings.BslName.inputValue}");
        bsl = getFullNamesForAccName(bsl);
        cell42.setCellValue("GPSL: " + bsl);
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell42.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, secondPalletStartIndx, secondPalletEndIndx));
        count++;
        //        •5 TASL
        //        •6 Medical Lead
        //TASL
        org.apache.poi.ss.usermodel.Row row5 = sheet.createRow(count);
        Cell cell51 = row5.createCell((short)firstPalletStartIndx);
        String tasl = null;
        if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
            tasl =
(String)ADFUtils.evaluateEL("#{bindings.TaslNameBase.inputValue}");
        } else
            tasl =
(String)ADFUtils.evaluateEL("#{bindings.TaslName.inputValue}");
        tasl = getFullNamesForAccName(tasl);
        cell51.setCellValue("HPS :  " + tasl);
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell51.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
        //ML name
//        Cell cell52 = row5.createCell((short)secondPalletStartIndx);
//        String medLLead = null;
//        if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
//            medLLead =
//                    (String)ADFUtils.evaluateEL("#{bindings.MedicalLeadNameBase.inputValue}");
//        } else
//            medLLead =
//                    (String)ADFUtils.evaluateEL("#{bindings.MedicalLeadName.inputValue}");
//        medLLead = getFullNamesForAccName(medLLead);
//        cell52.setCellValue("Medical Lead: " + medLLead);
//        ExcelExportUtils.setHeaderCellStyle(sheet, count,
//                                            cell52.getColumnIndex(), false,
//                                            CellStyle.ALIGN_LEFT);
//        sheet.addMergedRegion(new CellRangeAddress(count, count, secondPalletStartIndx, secondPalletEndIndx));
        count++;
        org.apache.poi.ss.usermodel.Row row6 = sheet.createRow(count);
        String designee = "";
        if (ModelConstants.BASE_FACET.equals(getBaseOrStaging())) {
            designee =
                    (String)ADFUtils.evaluateEL("#{bindings.DesigneeBase.inputValue}");
        } else
            designee =
                    (String)ADFUtils.evaluateEL("#{bindings.Designee.inputValue}");
        //designee = getFullNamesForAccName(designee);
        String designeeName = getFullNamesForDesignee(designee);
        Cell cell61 = row6.createCell((short)firstPalletStartIndx);
        cell61.setCellValue("Designee: " + designeeName);
        ExcelExportUtils.setHeaderCellStyle(sheet, count,
                                            cell61.getColumnIndex(), false,
                                            CellStyle.ALIGN_LEFT);
        sheet.addMergedRegion(new CellRangeAddress(count, count, firstPalletStartIndx, firstPalletEndIndx));
    }

    /**
     * This method is used to prepare map with stateid and stateName
     */
    private void prepareStatesMap() {
        statesMap = new HashMap<Integer, String>();
        DCIteratorBinding iter = ADFUtils.findIterator("CrsStateVOIterator");
        if (iter != null) {
            Row[] rows = iter.getAllRowsInRange();
            Integer stateId = null;
            String stateName = null;
            for (Row row : rows) {
                if (row != null) {
                    stateId = (Integer)row.getAttribute("StateId");
                    stateName = (String)row.getAttribute("StateName");
                    statesMap.put(stateId, stateName);
                }
            }
        }
    }

    /**
     * @param nonCompoundSelected
     */
    public void setNonCompoundSelected(boolean nonCompoundSelected) {
        this.nonCompoundSelected = nonCompoundSelected;
    }

    /**
     * @return
     */
    public boolean isNonCompoundSelected() {
        return nonCompoundSelected;
    }

    /**
     * @param stateSwitcherBinding
     */
    public void setStateSwitcherBinding(UIXSwitcher stateSwitcherBinding) {
        this.stateSwitcherBinding = stateSwitcherBinding;
    }

    /**
     * @return
     */
    public UIXSwitcher getStateSwitcherBinding() {
        return stateSwitcherBinding;
    }

    /**
     * This method set state null or activated based on the flow type.
     * @param vce
     */
    public void onChangeReleaseStatus(ValueChangeEvent vce) {
        // Add event code here...
        if (vce != null && !vce.getNewValue().equals(vce.getOldValue())) {
            System.out.println("-------vce.getNewValue()--------"+vce.getNewValue());
//            if (ViewConstants.FLOW_TYPE_SEARCH.equals(getFlowType())) {
//                if (ModelConstants.STATUS_PENDING.equals((String)vce.getNewValue())) {
//                    ADFUtils.setEL("#{bindings.State.inputValue}", null);
//                } else {
//                    ADFUtils.setEL("#{bindings.State.inputValue}",
//                                   ModelConstants.STATE_ACTIVATED);
//                }
//            }
            ADFUtils.addPartialTarget(stateSwitcherBinding);
            
//            if("C".equalsIgnoreCase((String)vce.getNewValue())){
//                refreshPage();
//            }
        }
        
    }

    protected void refreshPage() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String page = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, page);
        UIV.setViewId(page);
        fctx.setViewRoot(UIV);
    }

    /**
     * This method exports PT report for the current CRS id.
     * @param facesContext
     * @param outputStream
     */
    public void exportPTReport(FacesContext facesContext,
                               OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:exportPTReport()");
       
        Workbook workbook = null;
        DCIteratorBinding iter =
            ADFUtils.findIterator("PTReportVOIterator");
        if(iter!=null&& iter.getViewObject()!=null){
            PTReportVOImpl vo = (PTReportVOImpl)iter.getViewObject();
            if (this.currReleaseStatus.equalsIgnoreCase(ModelConstants.STATUS_PENDING)){
                vo.setpCRSStatus(ViewConstants.STAGE);
            } else if (this.currReleaseStatus.equalsIgnoreCase(ModelConstants.STATUS_CURRENT)){
                vo.setpCRSStatus(ViewConstants.PROD);
            }
            vo.setpRelScope(ModelConstants.SCOPE_NARROW);
            Long crsIdVal = (Long) ADFUtils.getPageFlowScopeValue("crsId");
            BigDecimal pcrsid = BigDecimal.valueOf(crsIdVal.longValue());
            
            vo.setpCrsId(pcrsid);
            
            Long crsRiskIdVal = (Long) ADFUtils.getPageFlowScopeValue("crsRiskId");
            BigDecimal crsRiskId = BigDecimal.valueOf(crsRiskIdVal.longValue());
            String wrClause = "crs_risk_id = "+crsRiskId;
            vo.setWhereClause(wrClause);
            vo.executeQuery();
        }
        ExcelExportUtils excUtils = new ExcelExportUtils();
        InputStream excelInputStream = excUtils.getExcelInpStream();
        InputStream imageInputStream = excUtils.getImageInpStream();
            try {
                //create sheet
                RowSetIterator rowSet = null;
                int rowStartIndex = 14;
                int cellStartIndex = 0;
                String emptyValReplace = null;
                String dateCellFormat = "M/dd/yyyy";
                if (iter != null) {
                    iter.setRangeSize(-1);
                    rowSet = iter.getRowSetIterator();
                }
                workbook = WorkbookFactory.create(excelInputStream);
                LinkedHashMap columnMap = new LinkedHashMap();
                ResourceBundle rsBundle =
                    BundleFactory.getBundle("com.novartis.ecrs.view.ECRSViewControllerBundle");
                //Here Key will be ViewObject Attribute
                //columnMap.put("CrsName", rsBundle.getString("CRS_NAME"));
                //columnMap.put("CrsId", rsBundle.getString("CRS_ID"));
                columnMap.put("SafetyTopicOfInterest", rsBundle.getString("SAFETY_TOPIC_OF_INTEREST"));
                columnMap.put("RiskPurposeList", rsBundle.getString("RISK_PURPOSE_LIST"));
                columnMap.put("MeddraTerm", rsBundle.getString("MEDDRA_TERM"));
                columnMap.put("CrsQualifier", rsBundle.getString("CRS_MEDDRA_SCOPE"));
                columnMap.put("PtName", rsBundle.getString("PT_NAME"));
                columnMap.put("TmsRelationScope", rsBundle.getString("PT_SCOPE"));
                columnMap.put("PtCode", rsBundle.getString("PT_CODE"));
                workbook.setMissingCellPolicy(org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
                Sheet sheet = workbook.getSheetAt(0);
                writeHeaderData(sheet,0,2,3,4);
                ExcelExportUtils.writeExcelSheet(sheet, rowSet, rowStartIndex,
                                                 cellStartIndex, columnMap, null,
                                                 dateCellFormat, emptyValReplace,
                                                 imageInputStream);

            } catch (IOException ioe) {
                // TODO: Add catch code
                logger.info("IOException..." + ioe.getMessage());
                ioe.printStackTrace();
            } catch (InvalidFormatException ife) {
                // TODO: Add catch code
                logger.info("InvalidFormatException..." + ife.getMessage());
                ife.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                workbook.write(outputStream);
                excelInputStream.close();
                outputStream.close();
            } catch (IOException ioe) {
                // TODO: Add catch code
                logger.info("IOException2..." + ioe.getMessage());
                ADFUtils.showFacesMessage(ioe.getMessage(), FacesMessage.SEVERITY_ERROR);
            }
            }
        logger.info("End of CRSReportsBean:exportPTReport()");
    }
    
    public void exportPTCurrentReport(FacesContext facesContext,
                               OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportPTCurrentReport(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }
    
    public void exportPTCurrentReportDetail(FacesContext facesContext,
                               OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportPTCurrentReportDetail(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }
    
    public void exportPTPendingReport(FacesContext facesContext,
                               OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportPTPendingReport(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }
    
    public void exportPTPendingReportDetail(FacesContext facesContext,
                               OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportPTPendingReportDetail(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }

    /**
     * This method returns fullname from input account name of the user.
     * @param accName
     * @return
     */
    private String getFullNamesForAccName(String accName){
        //invoke AMImpl with bsl,tasl,ml,designee acc names as keys
        DCIteratorBinding iter = ADFUtils.findIterator("UserFullNameIterator");
        String fullName = "";
        if(iter!=null && iter.getViewObject()!=null){
            ViewObjectImpl vo = (ViewObjectImpl)iter.getViewObject();
            vo.setWhereClause("user_name = '"+ accName+"'");
            vo.executeQuery();
            if(vo.first()!=null)
               fullName = (String) vo.first().getAttribute("FullName");
            logger.info("FullName for AccountName :-"+accName+" is -"+fullName);
        }
        return fullName;
    }

    /**
     * @param riskDefPopupPanel
     */
    public void setRiskDefPopupPanel(RichPanelGroupLayout riskDefPopupPanel) {
        this.riskDefPopupPanel = riskDefPopupPanel;
    }

    /**
     * @return
     */
    public RichPanelGroupLayout getRiskDefPopupPanel() {
        return riskDefPopupPanel;
    }

    public void deleteSafetyTopicOfInterest() {
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        DCIteratorBinding relationIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
        CrsRiskRelationVORowImpl row1 = (CrsRiskRelationVORowImpl)relationIter.getCurrentRow();
        System.out.println("-----"+row1.getCrsRiskId());
//        DCIteratorBinding definitionIter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
//        ViewObject definitionVO = definitionIter.getViewObject();
//        Row[] defRows = definitionVO.getAllRowsInRange();
//        for(Row row : defRows)
//            row.remove();
//        relationIter.getCurrentRow().remove();
        try{
        Long crsRiskId = row1.getCrsRiskId();
        Map params1 = new HashMap<String, Object>();
        params1.put("crsId", crsId);
        params1.put("crsRiskId", crsRiskId);
        ADFUtils.executeAction("deleteSafetyTopicOfInterest", params1);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        OperationBinding oper = ADFUtils.findOperation("Commit");
//        oper.execute();
//        relationIter.getViewObject().executeQuery();
//        if (oper.getErrors().size() > 0)
//            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
        riskDefPopup.hide();
        //Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        Map params = new HashMap<String, Object>();
        params.put("crsId", crsId);
        params.put("status", ViewConstants.STAGING);
        logger.info("Init risk Relation : current Crs ID :: "+crsId);
        logger.info("Init risk Relation : Base or Staging :: "+getBaseOrStaging());
        try {
            logger.info("Calling AM method initRiskRelation");
            ADFUtils.executeAction("initRiskRelation", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ADFUtils.addPartialTarget(stagingTable);
    }

    public void setStagingTable(RichTable stagingTable) {
        this.stagingTable = stagingTable;
    }

    public RichTable getStagingTable() {
        return stagingTable;
    }
    public String initConfirmPage(){
        logger.info("initConfirmPage....enter");
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        String flowTypeConfirm = (String) ADFUtils.getPageFlowScopeValue("flowType");
        logger.info("initConfirmPage : current flowType :: " + flowTypeConfirm);
        logger.info("initConfirmPage : current Crs ID :: " + crsId);
        logger.info("initConfirmPage : Base or Staging :: " + getBaseOrStaging());
       /* DCBindingContainer bc =  ADFUtils.findBindingContainerByName("com_novartis_ecrs_view_confirmCRSPageDef");
        if (null != bc){
            DCIteratorBinding iter = null;
            
            if (null != getBaseOrStaging() && getBaseOrStaging().equalsIgnoreCase(ViewConstants.STAGING)){
                iter = bc.findIteratorBinding("CrsContentVOIterator"); 
            } else {
                iter = bc.findIteratorBinding("CrsContentBaseVOIterator");
            }
            ViewObject crsContentVO = iter.getViewObject();
            logger.info("initConfirmPage : current flowType :: " + flowTypeConfirm);
            logger.info("initConfirmPage : current Crs ID :: " + crsId);
            logger.info("initConfirmPage : Base or Staging :: " + getBaseOrStaging());
            logger.info("initConfirmPage : User Role :: " + loggedInUserRole);
            if (null != crsContentVO){
                ViewCriteria vc = crsContentVO.getViewCriteriaManager().getViewCriteria("findByCrsId");
                if (null != vc){
                    crsContentVO.setNamedWhereClauseParam("pCrsId", crsId);
                    crsContentVO.applyViewCriteria(vc);
                    crsContentVO.executeQuery();
                    Row currentRow = crsContentVO.first();
                    crsContentVO.setCurrentRow(currentRow);
                    //loadDesineeList((String)currentRow.getAttribute("Designee"));
                    crsContentVO.applyViewCriteria(null);                    
                }
                
            }
        } else {
            logger.info("initConfirmPage....binding container is null");
        }
        */
        logger.info("Exit initConfirmPage....");
        return "confirm"; 
    }
    
    public String initVersionsPage(){
        logger.info("initConfirmPage....enter");
        Long crsId = (Long)ADFUtils.getPageFlowScopeValue("crsId");
        String flowTypeConfirm = (String) ADFUtils.getPageFlowScopeValue("flowType");
        logger.info("initConfirmPage : current flowType :: " + flowTypeConfirm);
        logger.info("initConfirmPage : current Crs ID :: " + crsId);
        logger.info("initConfirmPage : Base or Staging :: " + getBaseOrStaging());
        logger.info("Exit initConfirmPage....");
        return "versions"; 
    }
    private void loadDesineeList(String designee){
        List<String> designeeList = new ArrayList<String>();
        if (designee != null) {
            String[] designeeArray = designee.split("[,]");
            if (designeeArray.length > 0) {
                for (int i = 0; i < designeeArray.length; i++) {
                    designeeList.add(designeeArray[i]);
                }
            }
            setSelDesigneeList(designeeList);
        }
    }

    public void setWorkflowPG(RichPanelGroupLayout workflowPG) {
        this.workflowPG = workflowPG;
    }

    public RichPanelGroupLayout getWorkflowPG() {
        return workflowPG;
    }
    public void processDeleteRiskDefinitionsDialog(DialogEvent dialogEvent) {
        logger.info("Showing delete confirmation popup.");
        if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())){
            deleteRiskDefinitions();
            ADFUtils.addPartialTarget(riskDefTable);
        }
    }
    public void processDeleteSaftyTopicOfIntDialog(DialogEvent dialogEvent) {
        logger.info("Showing delete safty topic of intereset confirmation popup.");
        if(DialogEvent.Outcome.yes.equals(dialogEvent.getOutcome())){
            deleteSafetyTopicOfInterest();
        }
    }

    public void setDelSTIConfPopup(RichPopup delSTIConfPopup) {
        this.delSTIConfPopup = delSTIConfPopup;
    }

    public RichPopup getDelSTIConfPopup() {
        return delSTIConfPopup;
    }
    public void onDomainIdChange(ValueChangeEvent valueChangeEvent) {
        logger.info("Refreshing SOC LOV based on the domain selected");
        Integer newValue = (Integer)valueChangeEvent.getNewValue();
        OperationBinding op = ADFUtils.findOperation("domainName");
        Map params = op.getParamsMap();
        params.put("domainId", newValue);
        String domainName = (String)op.execute();
        if("OTHER".equalsIgnoreCase(domainName)){
            if(!"".equalsIgnoreCase(valueChangeEvent.getOldValue().toString())){
            OperationBinding op1 = ADFUtils.findOperation("executeRelationsExistsQuery");
            Map params1 = op1.getParamsMap();
            params1.put("crsId", ADFUtils.evaluateEL("#{pageFlowScope.crsId}"));
            params1.put("domainId", (Integer)valueChangeEvent.getOldValue());
            params1.put("safetyTopicOfInterest",ADFUtils.evaluateEL("#{bindings.SafetyTopicOfInterest.inputValue}") );
            String relationsExists = (String)op1.execute();
            if("YES".equalsIgnoreCase(relationsExists)){
                    ADFContext adfCtx = ADFContext.getCurrent();
                    Map pageFlowScope = adfCtx.getPageFlowScope();
                    pageFlowScope.put("domainOldValue", (Integer)valueChangeEvent.getOldValue());
                    pageFlowScope.put("domainNewValue", (Integer)valueChangeEvent.getNewValue());
                    ADFUtils.showPopup(this.getRiskDefOtherSelectionPopup());
                }else{
                    valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                    logger.info("Domain selected :: " + newValue);
                    ADFUtils.addPartialTarget(searchCriteriaDetails);
                    ADFUtils.addPartialTarget(socTermSOC);
                    showStatus(ViewConstants.CRS_MODIFIED);
                }
        }
        }else{
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            logger.info("Domain selected :: " + newValue);
            ADFUtils.addPartialTarget(searchCriteriaDetails);
            ADFUtils.addPartialTarget(socTermSOC);
            showStatus(ViewConstants.CRS_MODIFIED);
        }
       
        //ADFUtils.addPartialTarget(searchCriteriaDetails);
        //ADFUtils.addPartialTarget(socTermSOC);
    }

    public void setSocTermSOC(RichSelectOneChoice socTermSOC) {
        this.socTermSOC = socTermSOC;
    }

    public RichSelectOneChoice getSocTermSOC() {
        return socTermSOC;
    }

    public void setMeddraSearch(boolean meddraSearch) {
        this.meddraSearch = meddraSearch;
    }

    public boolean isMeddraSearch() {
        return meddraSearch;
    }
    /**
     * This method returns fullname from input account name of the user.
     * @return
     */
    private String getFullNamesForDesignee(String designee){
        //invoke AMImpl with bsl,tasl,ml,designee acc names as keys
        DCIteratorBinding iter = ADFUtils.findIterator("DesigneeFullNameVOIterator");
        String fullName = "";
        if(iter!=null && iter.getViewObject()!=null){
            ViewObjectImpl vo = (ViewObjectImpl)iter.getViewObject();
            vo.setNamedWhereClauseParam("pDesignee", designee);
            vo.executeQuery();
            if(vo.first()!=null)
               fullName = (String) vo.first().getAttribute("DesigneeName");
            logger.info("FullName for designee :-"+designee+" is -"+fullName);
        }
        return fullName;
    }
    /**
     * @param actionEvent
     */
    public void executeHierarchyChildNew(ActionEvent actionEvent) {
            DCIteratorBinding childIter = ADFUtils.findIterator("HierarchyChildVOIterator");
            ViewObject childVO = childIter.getViewObject();
            logger.info("executeHierarchyChildNew for selected content ID");
            childVO.setNamedWhereClauseParam("bContentId", ADFUtils.evaluateEL("#{row.ContentId}"));
            logger.info("executeHierarchyChildNew Query.." + childVO.getQuery());
            childVO.executeQuery();
            logger.info("After executeHierarchyChildNew Query..");
            if (childVO.getEstimatedRowCount() > 0) {
               // HierarchyChildUIBean parRow = new HierarchyChildUIBean(childVO.first());
                root = new HierarchyChildUIBean(childVO.first());
                logger.info("executeHierarchyChildNew for selected content ID==" + root.getTmsDictContentId());
                childVO.setCurrentRow(childVO.first());
                HierarchyChildVORowImpl parVORow = (HierarchyChildVORowImpl)childVO.first();
                rows = childIter.getRowSetIterator().enumerateRowsInRange();
                rows.nextElement();
                parentNodesByLevel = new HashMap <String, HierarchyChildUIBean>();
                parentNodesByLevel.put(root.getTmsDictContentId().toString(), root);
                List<HierarchyChildUIBean> childRows = new ArrayList<HierarchyChildUIBean>();
                Row childRow = null;
                HierarchyChildUIBean childNode = null;
                while (rows.hasMoreElements()) {
                    childRow = (Row)rows.nextElement();
                    childNode = new HierarchyChildUIBean(childRow);
                    childNode.setParentNode(root);
                    parentNodesByLevel.put(childNode.getTmsDictContentId().toString(), childNode);
                    childRows.add(childNode);
                }
                root.setChildren(childRows);
                hierChildList = new ArrayList<HierarchyChildUIBean>();
                hierChildList.add(root);
            }
            hierChildTreeModel = new ChildPropertyTreeModel(hierChildList, "children");
            getChildTreeTable().setVisible(Boolean.TRUE);
        
            ADFUtils.setPageFlowScopeValue("childVersion", ADFUtils.evaluateEL("#{row.DictVersion}"));
            ADFUtils.setPageFlowScopeValue("childDate", ADFUtils.evaluateEL("#{row.DictVersionDate}"));

            ADFUtils.addPartialTarget(getChildTreeTable());
    }

    /**
         * @param actionEvent
         */
        public void expandHierarchyChild(ActionEvent actionEvent) {
            HierarchyChildUIBean newRootNode = null;
            RichTreeTable tree = this.getChildTreeTable();
            clearKeys(tree);
            RowKeySet droppedValue = tree.getSelectedRowKeys();
            logger.info("In expandHierarchyChild--1");
            Object[] keys = droppedValue.toArray();
            Object oldRowKey = tree.getRowKey();
            try{
                for (int i = 0; i <keys.length; i++) {
                    List list = (List)keys[i];
                    int depth = list.size();
                    //int rootKey = Integer.parseInt(list.get(0).toString());
                    HierarchyChildUIBean c1 = null;
                    HierarchyChildUIBean c2 = null;
                    HierarchyChildUIBean c3 = null;
                    HierarchyChildUIBean c4 = null;

                    int c1key;
                    int c2key;
                    int c3key;
                    int c4key;

                    switch (depth) {

                        case 1:
                            newRootNode = root;
                            break;
                        case 2:
                            c1key = Integer.parseInt(list.get(1).toString());
                            c1 = (HierarchyChildUIBean)root.getChildren().get(c1key);
                            newRootNode = c1;
                            break;
                        case 3:
                            c1key = Integer.parseInt(list.get(1).toString());
                            c1 = (HierarchyChildUIBean)root.getChildren().get(c1key);
                            c2key = Integer.parseInt(list.get(2).toString());
                            c2 = (HierarchyChildUIBean)c1.getChildren().get(c2key);
                            newRootNode = c2;
                            break;
                        case 4:
                            c1key = Integer.parseInt(list.get(1).toString());
                            c1 = (HierarchyChildUIBean)root.getChildren().get(c1key);
                            c2key = Integer.parseInt(list.get(2).toString());
                            c2 = (HierarchyChildUIBean)c1.getChildren().get(c2key);
                            c3key = Integer.parseInt(list.get(3).toString());
                            c3 = (HierarchyChildUIBean)c2.getChildren().get(c3key);
                            newRootNode = c3;
                            break;
                        case 5:
                            c1key = Integer.parseInt(list.get(1).toString());
                            c1 = (HierarchyChildUIBean)root.getChildren().get(c1key);
                            c2key = Integer.parseInt(list.get(2).toString());
                            c2 = (HierarchyChildUIBean)c1.getChildren().get(c2key);
                            c3key = Integer.parseInt(list.get(3).toString());
                            c3 = (HierarchyChildUIBean)c2.getChildren().get(c3key);
                            c4key = Integer.parseInt(list.get(4).toString());
                            c4 = (HierarchyChildUIBean)c3.getChildren().get(c4key);
                            newRootNode = c4;
                            break;
                        }
                    }
                    logger.info("newRootNode content id ==" + newRootNode.getTmsDictContentId() + "::"
                                + newRootNode.getTerm());
                    logger.info("newRootNode parent content id ==" + newRootNode.getParent());
                    if (newRootNode.isIsExpanded()) return; // don't requery if already done
                    DCIteratorBinding childIter = ADFUtils.findIterator("HierarchyChildVOIterator");
                    ViewObject childVO = childIter.getViewObject();
                    childVO.setNamedWhereClauseParam("bContentId", newRootNode.getTmsDictContentId());
                    childVO.executeQuery();
                    rows = childIter.getRowSetIterator().enumerateRowsInRange();
                    // skip the first row, since it is the parent
                    rows.nextElement();                    
                    populateTreeNodesInHierarchy(newRootNode);
                    newRootNode.setIsExpanded(true); // prevent it from being called again
                    RowKeySet rks = new RowKeySetTreeImpl(true);
                    rks.setCollectionModel(hierChildTreeModel);
                    tree.setDisclosedRowKeys(rks);
                }finally{
                   //Restore the original rowKey
                    tree.setRowKey(oldRowKey);
            }
            AdfFacesContext.getCurrentInstance().addPartialTarget(tree);
            AdfFacesContext.getCurrentInstance().partialUpdateNotify(tree);
        }
        private void clearKeys (RichTreeTable tree) {
            if (tree != null && tree.getDisclosedRowKeys()!=null ){
                    tree.getDisclosedRowKeys().clear();//to resolve NoRowAvailableException
            }
        }
        private HierarchyChildUIBean populateTreeNodesInHierarchy(HierarchyChildUIBean node) {
            logger.info("In populateTreeNodesInHierarchy for selected content ID:::" + node.getTmsDictContentId());
            if (parentNodesByLevel == null) return null;
                Row childRow = null;
                while (rows.hasMoreElements()) {
                    childRow = (Row)rows.nextElement();
                    HierarchyChildUIBean termNode = new HierarchyChildUIBean(childRow);
                    String showMoreChildren = (String)childRow.getAttribute("ChildExists");
                    if ("Y".equals(showMoreChildren)) {
                        termNode.setShowHasChildrenButton(true);
                    }
                    HierarchyChildUIBean parentNode = (HierarchyChildUIBean)parentNodesByLevel.get(termNode.getParent());
                    String levelName = termNode.getLevelName();
                    parentNodesByLevel.put(termNode.getTmsDictContentId().toString(), termNode);
                    if (null != levelName && !levelName.equalsIgnoreCase("LLT")){
                        if (null != parentNode) {
                            termNode.setParentNode(parentNode);      // set the parent for the child
                            if (null ==  parentNode.getChildren()){
                                parentNode.setChildren(new ArrayList<HierarchyChildUIBean>());
                            }
                            parentNode.getChildren().add(termNode);  // add to the parent
                        } else {
                            logger.info("In populateTreeNodesInHierarchy parentNode is null....");
                        }
                    }
                    populateTreeNodesInHierarchy(termNode);
                }
            return node;

        }

    public void setSearchCriteriaDetails(RichInputText searchCriteriaDetails) {
        this.searchCriteriaDetails = searchCriteriaDetails;
    }

    public RichInputText getSearchCriteriaDetails() {
        return searchCriteriaDetails;
    }
    public void onClickOfRiskDefDelete(ActionEvent event){
        //Check any risk definition records selected
        DCIteratorBinding riskDefIter = ADFUtils.findIterator("CrsRiskDefinitionsVOIterator");
        ViewObject riskDefVO = riskDefIter.getViewObject();
        long rowCount = riskDefVO.getEstimatedRowCount();
        if (rowCount == 0){
            ADFUtils.showFacesMessage(uiBundle.getString("NO_RISK_DEFINITIONS_TO_DELETE"), FacesMessage.SEVERITY_INFO);
        } else {
            Row[] rows = riskDefVO.getFilteredRows("SelectAttr", Boolean.TRUE);
            if (null != rows && rows.length > 0){
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getDelConfPopupBinding().show(hints);
            } else {
                // Show error message
                ADFUtils.showFacesMessage(uiBundle.getString("SELECT_RISK_DEFINITIONS_TO_DELETE"), FacesMessage.SEVERITY_INFO);
            }
        }
    }
    /**
     * Invoke commit operation of DB.
     * @param actionEvent
     */
    public void onClickRiskDefSave(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding oper = ADFUtils.findOperation("Commit");
        oper.execute();
        if (oper.getErrors().size() > 0){
            showStatus(ViewConstants.CRS_SAVE_ERROR);
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"), FacesMessage.SEVERITY_ERROR);
        } else {
            showStatus(ViewConstants.CRS_SAVED);
            //ADFUtils.showPopup(getSuccessPopup());
        }
    }

    public void setRoutineRiskRelationCopied(boolean routineRiskRelationCopied) {
        this.routineRiskRelationCopied = routineRiskRelationCopied;
    }

    public boolean isRoutineRiskRelationCopied() {
        return routineRiskRelationCopied;
    }
    private void resetCrsName(String indication, String compCode, String crsCompCode){
          this.selectedCrsName =  (compCode != null ? compCode : crsCompCode) + (indication != null ? (" "+indication) : "");
    }
    

    public void setCntrlStatusBar(RichToolbar cntrlStatusBar) {
        this.cntrlStatusBar = cntrlStatusBar;
    }

    public RichToolbar getCntrlStatusBar() {
        return cntrlStatusBar;
    }


    public void setIconCRSChanged(RichImage iconCRSChanged) {
        this.iconCRSChanged = iconCRSChanged;
    }

    public RichImage getIconCRSChanged() {
        return iconCRSChanged;
    }

    public void setIconCRSSaveError(RichImage iconCRSSaveError) {
        this.iconCRSSaveError = iconCRSSaveError;
    }

    public RichImage getIconCRSSaveError() {
        return iconCRSSaveError;
    }

    public void setIconCRSSaved(RichImage iconCRSSaved) {
        this.iconCRSSaved = iconCRSSaved;
    }

    public RichImage getIconCRSSaved() {
        return iconCRSSaved;
    }
    
    public void showStatus (int code) {

        try {
            if (null != cntrlStatusBarCopy && cntrlStatusBarCopy.isRendered()){
                this.iconCopyCRSSaved.setVisible(false);
                this.iconCopyCRSSaveError.setVisible(false);
                this.iconCopyCRSChanged.setVisible(false);
                
                switch (code) {
                        case ViewConstants.CRS_SAVED:
                                this.iconCopyCRSSaved.setVisible(true);
                                break;
                        case ViewConstants.CRS_SAVE_ERROR:
                                this.iconCopyCRSSaveError.setVisible(true);
                                break;
                        case ViewConstants.CRS_MODIFIED:
                                this.iconCopyCRSChanged.setVisible(true);
                                break;
                }
                logger.info("In show Status ..cntrlStatusBarCopy");
                ADFUtils.addPartialTarget(cntrlStatusBarCopy);
            } else if (null != cntrlStatusBar){
                this.iconCRSSaved.setVisible(false);
                this.iconCRSSaveError.setVisible(false);
                this.iconCRSChanged.setVisible(false);
                
                switch (code) {
                        case ViewConstants.CRS_SAVED:
                                this.iconCRSSaved.setVisible(true);
                                break;
                        case ViewConstants.CRS_SAVE_ERROR:
                                this.iconCRSSaveError.setVisible(true);
                                break;
                        case ViewConstants.CRS_MODIFIED:
                                this.iconCRSChanged.setVisible(true);
                                break;
                }
                ADFUtils.addPartialTarget(cntrlStatusBar);
                logger.info("In show Status ..cntrlStatusBar");
            }
            
            if (null != cntrlStatusBarCopy && cntrlStatusBarCopy.isRendered()){
                logger.info("In show Status ..cntrlStatusBarCopy");
                ADFUtils.addPartialTarget(cntrlStatusBarCopy);
            } else if (null != cntrlStatusBar){
                ADFUtils.addPartialTarget(cntrlStatusBar);
                logger.info("In show Status ..cntrlStatusBar");
            }
        }
        catch (java.lang.NullPointerException e) {} //ignore it
            
    }

    public void onRiskDetailsUpdate(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        showStatus(ViewConstants.CRS_MODIFIED);
    }
    
    public Boolean isRiskRelationsExistsForCRS(){
        logger.info("In isRiskRelationsExistsForCRS...");
        OperationBinding oper = ADFUtils.findOperation("isRiskRelationsExistForCRS");
        Boolean retVal = (Boolean)oper.execute();
        logger.info("isRiskRelationsExistsForCRS..." + retVal);
        return retVal;
    }
    public void setIsCurrentUserInDesignee(Boolean currentUserInDesignee) {
        this.currentUserInDesignee = currentUserInDesignee;
    }

    public Boolean getCurrentUserInDesignee() {
        if (!this.userName.equalsIgnoreCase(ViewConstants.ANONYMOUS_ROLE) && null != selDesigneeList){
            if (selDesigneeList.contains(this.userName)){
                this.currentUserInDesignee = Boolean.TRUE;
            }
        }
        logger.info("isCurrentUserInDesignee..." + this.currentUserInDesignee);
        return this.currentUserInDesignee;
    }

    public void onChangeOfReviewApprovalRequiredFlag(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        logger.info("ReviewApprovalRequiredFlag..." + valueChangeEvent.getNewValue());
        if (isCRSVersionInitial() && valueChangeEvent.getNewValue().equals(Boolean.FALSE)){
            ((UIXEditableValue)valueChangeEvent.getComponent()).resetValue();
            ADFUtils.showFacesMessage(uiBundle.getString("CRS_REVIEW_APPROVAL_REQ_MSG"), FacesMessage.SEVERITY_ERROR);
        }
    }
    
    private Boolean isCRSVersionInitial(){
        Boolean initialVersion = Boolean.TRUE;
        OperationBinding oper = ADFUtils.findOperation("isCRSVersionInitial");
        if (null != this.selectedCrsName && !this.selectedCrsName.isEmpty()){
            if (null != oper){
                initialVersion = (Boolean) oper.execute();
                if (null == initialVersion){
                    initialVersion = Boolean.TRUE;
                    logger.info("isCRSVersionInitial...initialVersion is null from AM");
                } else {
                    logger.info("isCRSVersionInitial..." + initialVersion);
                }
            }
        }
        
        return initialVersion;
    }
    public Boolean getIsCRSVersionInitial(){
        return isCRSVersionInitial();
    }
    public void onMedDRAQualifierUpdate(ValueChangeEvent valueChangeEvent) {
        // Add event code here...
        ADFUtils.setEL("#{row.bindings.MeddraQualifier.inputValue}",valueChangeEvent.getNewValue());
        showStatus(ViewConstants.CRS_MODIFIED);
    }
    
    /**
     * Reload the search page after publish.
     */
    public void reloadSearchPage() {
        logger.info("Begin reloadSearchPage after publishing...");
        ADFUtils.closeDialog(getCrsPublishPopupBinding());
        DCBindingContainer bc = ADFUtils.getDCBindingContainer();
        OperationBinding ob = bc.getOperationBinding("filterCRSContent");
        ob.getParamsMap().put("userInRole", loggedInUserRole);
        ob.getParamsMap().put("userName", getUserName());
        ob.getParamsMap().put("isInboxDisable", isInboxDisable());
        ob.getParamsMap().put("flowType", getFlowType());
        ob.execute();
        if (ob.getErrors().size() > 0)
            ADFUtils.showFacesMessage(uiBundle.getString("INTERNAL_ERROR"),
                                      FacesMessage.SEVERITY_ERROR);
        logger.info("End reloadSearchPage after publishing...");
    }

    public void setSearchCriteriaDetailsCopy(RichInputText searchCriteriaDetailsCopy) {
        this.searchCriteriaDetailsCopy = searchCriteriaDetailsCopy;
    }

    public RichInputText getSearchCriteriaDetailsCopy() {
        return searchCriteriaDetailsCopy;
    }

    public void setSocTermSOCCopy(RichSelectOneChoice socTermSOCCopy) {
        this.socTermSOCCopy = socTermSOCCopy;
    }

    public RichSelectOneChoice getSocTermSOCCopy() {
        return socTermSOCCopy;
    }
    
    public void onDomainIdChangeInCopyRisk(ValueChangeEvent valueChangeEvent) {
        logger.info("Refreshing SOC LOV based on the domain selected");
        
        Integer newValue = (Integer)valueChangeEvent.getNewValue();
        OperationBinding op = ADFUtils.findOperation("domainName");
        Map params = op.getParamsMap();
        params.put("domainId", newValue);
        String domainName = (String)op.execute();
        if("OTHER".equalsIgnoreCase(domainName)){
            OperationBinding op1 = ADFUtils.findOperation("executeRelationsExistsQuery");
            Map params1 = op1.getParamsMap();
            params1.put("crsId", ADFUtils.evaluateEL("#{pageFlowScope.crsId}"));
            params1.put("domainId", (Integer)valueChangeEvent.getOldValue());
            params1.put("safetyTopicOfInterest",ADFUtils.evaluateEL("#{bindings.SafetyTopicOfInterest.inputValue}") );
            String relationsExists = (String)op1.execute();
            if("YES".equalsIgnoreCase(relationsExists)){
                    ADFContext adfCtx = ADFContext.getCurrent();
                    Map pageFlowScope = adfCtx.getPageFlowScope();
                    pageFlowScope.put("domainOldValue", (Integer)valueChangeEvent.getOldValue());
                    pageFlowScope.put("domainNewValue", (Integer)valueChangeEvent.getNewValue());
                    ADFUtils.showPopup(this.getCopyRiskDefOtherSelectionPopup());
                }else{
                    valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
                    logger.info("Domain selected :: " + newValue);
                    ADFUtils.addPartialTarget(searchCriteriaDetailsCopy);
                    ADFUtils.addPartialTarget(socTermSOCCopy);
                    showStatus(ViewConstants.CRS_MODIFIED);
                }
        }else{
            valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
            logger.info("Domain selected :: " + newValue);
            ADFUtils.addPartialTarget(searchCriteriaDetailsCopy);
            ADFUtils.addPartialTarget(socTermSOCCopy);
            showStatus(ViewConstants.CRS_MODIFIED);
        }
        
        //ADFUtils.addPartialTarget(searchCriteriaDetails);
        //ADFUtils.addPartialTarget(socTermSOC);
    }

    public void redirectAfterPublishCRS(ActionEvent actionEvent) {
        // Add event code here...
        if (this.flowType.equalsIgnoreCase(ViewConstants.FLOW_TYPE_CREATE)){
            ADFUtils.navigateToControlFlowCase("home");
        } else {
            ADFUtils.navigateToControlFlowCase("reloadSearchPage");
        }
    }

    public void setCntrlStatusBarCopy(RichToolbar cntrlStatusBarCopy) {
        this.cntrlStatusBarCopy = cntrlStatusBarCopy;
    }

    public RichToolbar getCntrlStatusBarCopy() {
        return cntrlStatusBarCopy;
    }

    public void setCopyRiskDefPopupPanel(RichPanelGroupLayout copyRiskDefPopupPanel) {
        this.copyRiskDefPopupPanel = copyRiskDefPopupPanel;
    }

    public RichPanelGroupLayout getCopyRiskDefPopupPanel() {
        return copyRiskDefPopupPanel;
    }

    public void setIconCopyCRSChanged(RichImage iconCopyCRSChanged) {
        this.iconCopyCRSChanged = iconCopyCRSChanged;
    }

    public RichImage getIconCopyCRSChanged() {
        return iconCopyCRSChanged;
    }

    public void setIconCopyCRSSaveError(RichImage iconCopyCRSSaveError) {
        this.iconCopyCRSSaveError = iconCopyCRSSaveError;
    }

    public RichImage getIconCopyCRSSaveError() {
        return iconCopyCRSSaveError;
    }

    public void setIconCopyCRSSaved(RichImage iconCopyCRSSaved) {
        this.iconCopyCRSSaved = iconCopyCRSSaved;
    }

    public RichImage getIconCopyCRSSaved() {
        return iconCopyCRSSaved;
    }

    public void setRiskBasePopup(RichPopup riskBasePopup) {
        this.riskBasePopup = riskBasePopup;
    }

    public RichPopup getRiskBasePopup() {
        return riskBasePopup;
    }
    
    /**
     * @param actionEvent
     */
    public void onCancelCrsRiskBasePopup(ActionEvent actionEvent) {
        logger.info("Closing CrsRiskBase Popup.");
        if(riskBasePopup != null){
              riskBasePopup.hide();
        }
    }

    public void setRiskBasePopupPanel(RichPanelGroupLayout riskBasePopupPanel) {
        this.riskBasePopupPanel = riskBasePopupPanel;
    }

    public RichPanelGroupLayout getRiskBasePopupPanel() {
        return riskBasePopupPanel;
    }

    public void setSelRiskPurposesBase(List<String> selRiskPurposesBase) {
        this.selRiskPurposesBase = selRiskPurposesBase;
    }

    public List<String> getSelRiskPurposesBase() {
        return selRiskPurposesBase;
    }
    
    public void loadRiskDefinitionsBase(ActionEvent actionEvent) {
        //Added because, when coming from copy current flow, the new ID is not there in the EO and giving error while setting current row.
        DCIteratorBinding relationIter = ADFUtils.findIterator("CrsRiskRelationsBaseVOIterator");
        relationIter.executeQuery();
        
        logger.info("load RiskDefinitions Base .");
        //ADFUtils.setPageFlowScopeValue("popupMode", "Edit");
        Long riskId = (Long)ADFUtils.evaluateEL("#{row.CrsRiskId}");
        ADFUtils.setPageFlowScopeValue("crsRiskId", riskId);
        String dataDomain = (String)ADFUtils.evaluateEL("#{row.DataDomain}");
        ADFUtils.setPageFlowScopeValue("dataDomainBase", dataDomain);
        String socTermBase = (String)ADFUtils.evaluateEL("#{row.SocTerm}");
        ADFUtils.setPageFlowScopeValue("socTermBase", socTermBase);
        logger.info("dataDomain ==>" + dataDomain);
        logger.info("socTermBase ==>" + socTermBase);
        Integer domainId = 1;
        if (null != dataDomain){
            Map params2 = new HashMap<String, Object>();
            params2.put("domainName", dataDomain);
            try {
               domainId = (Integer) ADFUtils.executeAction("fetchDomainIdFromName", params2);
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        logger.info("DomainIdBase ==>" + domainId);
        if (null != domainId && domainId.intValue() != 1){
            ADFUtils.setEL("#{bindings.DomainId2.inputValue}", domainId);
        }
        logger.info("Current crs risk id "+riskId);
    //        String databaseList = (String)ADFUtils.evaluateEL("#{row.DatabaseList}");
    //        List<String> dbList = new ArrayList<String>();
    //        if(databaseList != null){
    //            String split[] = databaseList.split(",");
    //            for(String db : split){
    //                dbList.add(db);
    //            }
    //        }
    //        setSelDatabases(dbList);
        String riskPurposeList = (String)ADFUtils.evaluateEL("#{row.RiskPurposeList}");
        List<String> rpList = new ArrayList<String>();
        if(riskPurposeList != null){
            if(riskPurposeList.endsWith(",")){
                riskPurposeList = riskPurposeList.substring(0, riskPurposeList.length()-1);
            }
            String split[] = riskPurposeList.split(",");
            for(String rp : split){
                rpList.add(rp);
            }
        }
        logger.info("Selected risk purpose list :: "+rpList);
        setSelRiskPurposesBase(rpList);
        
        Map params = new HashMap<String, Object>();
        params.put("rowKey", riskId);
        try {
            ADFUtils.executeAction("setCurrentRiskRelationBase", params);
        } catch (Exception e) {
            logger.info("Exception in setCurrentRiskRelationBase :: "+e.getMessage());
            e.printStackTrace();
            
        }
        if(riskBasePopupPanel != null)
            ResetUtils.reset(riskBasePopupPanel);
        ADFUtils.showPopup(riskBasePopup);
    }

    public void setCancelWarningPopup(RichPopup cancelWarningPopup) {
        this.cancelWarningPopup = cancelWarningPopup;
    }

    public RichPopup getCancelWarningPopup() {
        return cancelWarningPopup;
    }

    public void onRiskPurposeVC(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() && valueChangeEvent.getNewValue()!= null){
            List<String> selRiskPurposes = (List<String>) valueChangeEvent.getNewValue();
            
            if(!selRiskPurposes.contains("CD")){
            ADFUtils.setEL("#{bindings.Adr.inputValue}", null);
            }
            
//            if(selRiskPurposes.contains("A2") && (selRiskPurposes.contains("RM") || selRiskPurposes.contains("PS"))){
//                ADFUtils.showFacesMessage("If A2 is selected,  RM or PS cannot also be selected.  Please ensure that only A2 is selected or removed.", FacesMessage.SEVERITY_ERROR);
//            }
        }
        onRiskDetailsUpdate(valueChangeEvent);
    }

    public void onCopyRiskPurposeVC(ValueChangeEvent valueChangeEvent) {
        List<String> selRiskPurposes = (List<String>) valueChangeEvent.getNewValue();
        if(valueChangeEvent.getNewValue() != valueChangeEvent.getOldValue() && valueChangeEvent.getNewValue()!= null){
            if(!selRiskPurposes.contains("CD")){
            ADFUtils.setEL("#{bindings.Adr.inputValue}", null);
            }
//            if(selRiskPurposes.contains("A2") && (selRiskPurposes.contains("RM") || selRiskPurposes.contains("PS"))){
//                ADFUtils.showFacesMessage("If A2 is selected,  RM or PS cannot also be selected.  Please ensure that only A2 is selected or removed.", FacesMessage.SEVERITY_ERROR);
//            }
        }
        onRiskDetailsUpdate(valueChangeEvent);
    }

    public void onSaveRiskDefsWarningPopup(ActionEvent actionEvent) {
        if(selRiskPurposes != null && selRiskPurposes.size() > 0){
//            if(selRiskPurposes.contains("A2") && (selRiskPurposes.contains("RM") || selRiskPurposes.contains("PS"))){
//                ADFUtils.showFacesMessage("If A2 is selected,  RM or PS cannot also be selected.  Please ensure that only A2 is selected or removed.", FacesMessage.SEVERITY_ERROR);
//                return;
//            }
        }
        saveRiskDefs(actionEvent);
        cancelRisk();
        getCancelWarningPopup().hide();
        getRiskDefPopup().hide();
    }

    public void onClickRiskDefSaveWarningPopup(ActionEvent actionEvent) {
        onClickRiskDefSave(actionEvent);
        getCancelWarningPopup().hide();
        getRiskDefPopup().hide();
    }

    public void stoiVC(ValueChangeEvent valueChangeEvent) {
//        DCIteratorBinding realtionIter = ADFUtils.findIterator("CrsRiskRelationVOIterator");
//        ViewObject relations = realtionIter.getViewObject();
//        Row relationRow = relations.getCurrentRow(); 
//        RowQualifier rq = new RowQualifier("CrsId= " + row.getAttribute("CrsId") +" AND SafetyTopicOfInterest= '" +row.getAttribute("SafetyTopicOfInterest") + "'");
//        Row[] rows = relations.getFilteredRows(rq);
//        for(Row row : rows){
//            
//        }
        String val = (String)valueChangeEvent.getNewValue();
               if(val != null && val.contains("'")){
                   String[] str = val.split("'");
                   val = str[0]+ "''" + str[1];
                   ADFUtils.setEL("#{bindings.SafetyTopicOfInterest.inputValue}", (val));
               }
               ADFUtils.addPartialTarget(valueChangeEvent.getComponent());
    }

    public void setRiskDialog(RichDialog riskDialog) {
        this.riskDialog = riskDialog;
    }

    public RichDialog getRiskDialog() {
        return riskDialog;
    }

    public void validateSpecialCharactors(FacesContext facesContext, UIComponent uIComponent, Object object) {
                if(object!=null){
                    String name=object.toString();
                    String msg="Special characters @:,$=.+?;&\\/ are not allowed";
            if (name.contains("@") || name.contains(":") || name.contains(",") || name.contains("$") ||
                name.contains("=") || name.contains(".") || name.contains("+") || name.contains("?") ||
                name.contains(";") || name.contains("&") || name.contains("\\") || name.contains("/")) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,null));
            }
                }

    }

    public void setVersionComparePopup(RichPopup versionComparePopup) {
        this.versionComparePopup = versionComparePopup;
    }

    public RichPopup getVersionComparePopup() {
        return versionComparePopup;
    }

    public void compareCurrentVersion(ActionEvent actionEvent) {
        try {
            ADFUtils.executeAction("executeCrsVersionCompare", null);
        } catch (Exception e) {
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getVersionComparePopup().show(hints);
    }

    public void comparePreviousVersions(ActionEvent actionEvent) {
        try {
            ADFUtils.executeAction("executeCrsVersionCompare", null);
        } catch (Exception e) {
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getVersionComparePopup().show(hints);
    }

    public void baseCompareCurrentVersion(ActionEvent actionEvent) {      
        try {
            ADFUtils.executeAction("executeBaseCrsVersionCompare", null);
        } catch (Exception e) {
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getVersionComparePopup().show(hints);
    }

    public void baseComparePreviousVersions(ActionEvent actionEvent) {
       
        try {
            ADFUtils.executeAction("executeBaseCrsVersionCompare", null);
        } catch (Exception e) {
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getVersionComparePopup().show(hints);

    }

    public void setCrsVersionsTable(RichTable crsVersionsTable) {
        this.crsVersionsTable = crsVersionsTable;
    }

    public RichTable getCrsVersionsTable() {
        return crsVersionsTable;
    }

    public void setBaseCrsVersionsTable(RichTable baseCrsVersionsTable) {
        this.baseCrsVersionsTable = baseCrsVersionsTable;
    }

    public RichTable getBaseCrsVersionsTable() {
        return baseCrsVersionsTable;
    }

    public void onVersionRowSelection(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());        
//        DCBindingContainer bindings = this.getDCBindingContainer();
//        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsVersionsIterator");
//        ViewObject vo = itrBinding.getViewObject();
//        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
//        
//        if(selectedRows.length == 1){
//            this.setCrsVersionsCurrent(true);
//            this.setCrsVersionsPrevious(false);
//        }else if(selectedRows.length == 2){
//            this.setCrsVersionsCurrent(false);
//            this.setCrsVersionsPrevious(true);
//        }else{
//            this.setCrsVersionsCurrent(false);
//            this.setCrsVersionsPrevious(false);
//        }
}
    

    public void onBaseVersionRowSelection(ValueChangeEvent valueChangeEvent) {
                valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());       
//        DCBindingContainer bindings = this.getDCBindingContainer();
//        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsBaseVersionsIterator");
//        ViewObject vo = itrBinding.getViewObject();
//        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
//        
//        if(selectedRows.length == 1){
//            this.setBaseCrsVersionsCurrent(true);
//            this.setBaseCrsVersionsPrevious(false);
//        }else if(selectedRows.length == 2){
//            this.setBaseCrsVersionsCurrent(false);
//            this.setBaseCrsVersionsPrevious(true);
//        }else{
//            this.setBaseCrsVersionsCurrent(false);
//            this.setBaseCrsVersionsPrevious(false);
//        }
        
    }

    public DCBindingContainer getDCBindingContainer(){
        DCBindingContainer dcBindingContainer = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
        return dcBindingContainer;
    }

    public void setCrsVersionsCurrent(Boolean crsVersionsCurrent) {
        this.crsVersionsCurrent = crsVersionsCurrent;
    }

    public Boolean getCrsVersionsCurrent() {
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsVersionsIterator");
        ViewObject vo = itrBinding.getViewObject();
        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
        
        if(selectedRows.length == 1){
            return false;
        }else if(selectedRows.length == 2){
            return true;
        }else{
            return true;
        }
        //return crsVersionsCurrent;
    }

    public void setCrsVersionsPrevious(Boolean crsVersionsPrevious) {
        this.crsVersionsPrevious = crsVersionsPrevious;
    }

    public Boolean getCrsVersionsPrevious() {
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsVersionsIterator");
        ViewObject vo = itrBinding.getViewObject();
        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
        
        if(selectedRows.length == 1){
            return true;
        }else if(selectedRows.length == 2){
            return false;
        }else{
            return true;
        }
        //return crsVersionsPrevious;
    }

    public void setBaseCrsVersionsCurrent(Boolean baseCrsVersionsCurrent) {
        this.baseCrsVersionsCurrent = baseCrsVersionsCurrent;
    }

    public Boolean getBaseCrsVersionsCurrent() {
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsBaseVersionsIterator");
        ViewObject vo = itrBinding.getViewObject();
        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
        
        if(selectedRows.length == 1){
            return false;
        }else if(selectedRows.length == 2){
            return true;
        }else{
            return true;
        }
        //return baseCrsVersionsCurrent;
    }

    public void setBaseCrsVersionsPrevious(Boolean baseCrsVersionsPrevious) {
        this.baseCrsVersionsPrevious = baseCrsVersionsPrevious;
    }

    public Boolean getBaseCrsVersionsPrevious() {
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsBaseVersionsIterator");
        ViewObject vo = itrBinding.getViewObject();
        Row[] selectedRows = vo.getFilteredRows("SelectRow", true);
        
        if(selectedRows.length == 1){
            return true;
        }else if(selectedRows.length == 2){
            return false;
        }else{
            return true;
        }
        //return baseCrsVersionsPrevious;
    }

    public void deleteVersionsAndClosePopup(ActionEvent actionEvent) {
        try {
            ADFUtils.executeAction("deleteVersions", null);
        } catch (Exception e) {
        }
        this.getVersionComparePopup().hide();
    }
    
    public void versionComaparePending(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportVersionComparePending(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }
    
    public void versionComapareCurrent(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...
        logger.info("Start of CRSReportsBean:versionComapare()");
        try {
        List wb = exportVersionCompareCurrent(); //method for creating WB
        HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
        workbook.write(outputStream);
        outputStream.flush();
        } catch (IOException ex) {
        ex.printStackTrace();
        }
        logger.info("End of CRSReportsBean:versionComapare()");
    }
    
    public List exportVersionComparePending() {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("Version 1"); //Sheet Name
    HSSFSheet sheet1 = wb.createSheet("Version 2");
    int idx = 0; // rows index
    int idx1 = 0; // rows index
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
    HSSFRow row1 = null;
    
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CRSVersionComparePendingIterator");
        ViewObject vo = itrBinding.getViewObject();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    while (vo.hasNext()) { 
    CRSVersionComparePendingViewRowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CRSVersionComparePendingViewRowImpl) vo.next();
            } else {
                viewObjectRow = (CRSVersionComparePendingViewRowImpl) vo.first();
            }
    if(firstRow){
    row = sheet.createRow(idx); //creating 1st row
    row.createCell(1).setCellValue("Purpose Of Risk Definition");
    row.getCell(1).setCellStyle(colStyleTopLeft);
    sheet.addMergedRegion(new CellRangeAddress(0,0,1,12));
    row.createCell(17).setCellValue("Safety Topic Definition");
    row.getCell(17).setCellStyle(colStyleTopLeft);
    sheet.addMergedRegion(new CellRangeAddress(0,0,17,21));
    
    row1 = sheet1.createRow(idx1); //creating 1st row
    row1.createCell(1).setCellValue("Purpose Of Risk Definition");
    row1.getCell(1).setCellStyle(colStyleTopLeft);
    sheet1.addMergedRegion(new CellRangeAddress(0,0,1,12));
    row1.createCell(17).setCellValue("Safety Topic Definition");
    row1.getCell(17).setCellStyle(colStyleTopLeft);
    sheet1.addMergedRegion(new CellRangeAddress(0,0,17,21));
    idx = idx + 1;
    idx1 = idx1 + 1;
    }
    row = sheet.createRow(idx); //creating 1st row
    row1 = sheet1.createRow(idx1); //creating 1st row
    firstRow = false;
    if(flag == false){
                row.createCell(0).setCellValue("Safety Topic Of Interest"); //setting column heading
                sheet.autoSizeColumn(0);
                row.getCell(0).setCellStyle(colStyleTopLeft);
                row.createCell(1).setCellValue("SP");
                sheet.autoSizeColumn(1);
                row.getCell(1).setCellStyle(colStyleTopLeft);
                row.createCell(2).setCellValue("DS");
                sheet.autoSizeColumn(2);
                row.getCell(2).setCellStyle(colStyleTopLeft);
                row.createCell(3).setCellValue("RM");
                sheet.autoSizeColumn(3);
                row.getCell(3).setCellStyle(colStyleTopLeft);
                row.createCell(4).setCellValue("PS");
                sheet.autoSizeColumn(4);
                row.getCell(4).setCellStyle(colStyleTopLeft);
                row.createCell(5).setCellValue("IB");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("CD");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);
                row.createCell(7).setCellValue("OS");
                sheet.autoSizeColumn(7);
                row.getCell(7).setCellStyle(colStyleTopLeft);
                row.createCell(8).setCellValue("MI");
                sheet.autoSizeColumn(8);
                row.getCell(8).setCellStyle(colStyleTopLeft);
                row.createCell(9).setCellValue("ER");
                sheet.autoSizeColumn(9);
                row.getCell(9).setCellStyle(colStyleTopLeft);
                row.createCell(10).setCellValue("UD");
                sheet.autoSizeColumn(10);
                row.getCell(10).setCellStyle(colStyleTopLeft);
                row.createCell(11).setCellValue("A1");
                sheet.autoSizeColumn(11);
                row.getCell(11).setCellStyle(colStyleTopLeft);
                row.createCell(12).setCellValue("A2");
                sheet.autoSizeColumn(12);
                row.getCell(12).setCellStyle(colStyleTopLeft);
                row.createCell(13).setCellValue("SOC");
                sheet.autoSizeColumn(13);
                row.getCell(13).setCellStyle(colStyleTopLeft);
        row.createCell(14).setCellValue("Gender");
        sheet.autoSizeColumn(14);
        row.getCell(14).setCellStyle(colStyleTopLeft);
        row.createCell(15).setCellValue("Age");
        sheet.autoSizeColumn(15);
        row.getCell(15).setCellStyle(colStyleTopLeft);
                row.createCell(16).setCellValue("Data Domain");
                sheet.autoSizeColumn(16);
                row.getCell(16).setCellStyle(colStyleTopLeft);
                row.createCell(17).setCellValue("Search Criteria Details");
                sheet.autoSizeColumn(17);
                row.getCell(17).setCellStyle(colStyleTopLeft);
                row.createCell(18).setCellValue("Search Applied To");
                sheet.autoSizeColumn(18);
                row.getCell(18).setCellStyle(colStyleTopLeft);
                row.createCell(19).setCellValue("MedDRA Code");
                sheet.autoSizeColumn(19);
                row.getCell(19).setCellStyle(colStyleTopLeft);
                row.createCell(20).setCellValue("MedDRA Term");
                sheet.autoSizeColumn(20);
                row.getCell(20).setCellStyle(colStyleTopLeft);
                row.createCell(21).setCellValue("MedDRA Level");
                sheet.autoSizeColumn(21);
                row.getCell(21).setCellStyle(colStyleTopLeft);
                row.createCell(22).setCellValue("MedDRA Qualifier");
                sheet.autoSizeColumn(22);
                row.getCell(22).setCellStyle(colStyleTopLeft); /*  */
                row.createCell(23).setCellValue("Comment");
                sheet.autoSizeColumn(23);
                row.getCell(23).setCellStyle(colStyleTopLeft);
                
        row1.createCell(0).setCellValue("Safety Topic Of Interest"); //setting column heading
        sheet1.autoSizeColumn(0);
        row1.getCell(0).setCellStyle(colStyleTopLeft);
        row1.createCell(1).setCellValue("SP");
        sheet1.autoSizeColumn(1);
        row1.getCell(1).setCellStyle(colStyleTopLeft);
        row1.createCell(2).setCellValue("DS");
        sheet1.autoSizeColumn(2);
        row1.getCell(2).setCellStyle(colStyleTopLeft);
        row1.createCell(3).setCellValue("RM");
        sheet1.autoSizeColumn(3);
        row1.getCell(3).setCellStyle(colStyleTopLeft);
        row1.createCell(4).setCellValue("PS");
        sheet1.autoSizeColumn(4);
        row1.getCell(4).setCellStyle(colStyleTopLeft);
        row1.createCell(5).setCellValue("IB");
        sheet1.autoSizeColumn(5);
        row1.getCell(5).setCellStyle(colStyleTopLeft);
        row1.createCell(6).setCellValue("CD");
        sheet1.autoSizeColumn(6);
        row1.getCell(6).setCellStyle(colStyleTopLeft);
        row1.createCell(7).setCellValue("OS");
        sheet1.autoSizeColumn(7);
        row1.getCell(7).setCellStyle(colStyleTopLeft);
        row1.createCell(8).setCellValue("MI");
        sheet1.autoSizeColumn(8);
        row1.getCell(8).setCellStyle(colStyleTopLeft);
        row1.createCell(9).setCellValue("ER");
        sheet1.autoSizeColumn(9);
        row1.getCell(9).setCellStyle(colStyleTopLeft);
        row1.createCell(10).setCellValue("UD");
        sheet1.autoSizeColumn(10);
        row1.getCell(10).setCellStyle(colStyleTopLeft);
        row1.createCell(11).setCellValue("A1");
        sheet1.autoSizeColumn(11);
        row1.getCell(11).setCellStyle(colStyleTopLeft);
        row1.createCell(12).setCellValue("A2");
        sheet1.autoSizeColumn(12);
        row1.getCell(12).setCellStyle(colStyleTopLeft);
        row1.createCell(13).setCellValue("SOC");
        sheet1.autoSizeColumn(13);
        row1.getCell(13).setCellStyle(colStyleTopLeft);
        row1.createCell(14).setCellValue("Gender");
        sheet1.autoSizeColumn(14);
        row1.getCell(14).setCellStyle(colStyleTopLeft);
        row1.createCell(15).setCellValue("Age");
        sheet1.autoSizeColumn(15);
        row1.getCell(15).setCellStyle(colStyleTopLeft);
        row1.createCell(16).setCellValue("Data Domain");
        sheet1.autoSizeColumn(16);
        row1.getCell(16).setCellStyle(colStyleTopLeft);
        row1.createCell(17).setCellValue("Search Criteria Details");
        sheet1.autoSizeColumn(17);
        row1.getCell(17).setCellStyle(colStyleTopLeft);
        row1.createCell(18).setCellValue("Search Applied To");
        sheet1.autoSizeColumn(18);
        row1.getCell(18).setCellStyle(colStyleTopLeft);
        row1.createCell(19).setCellValue("MedDRA Code");
        sheet1.autoSizeColumn(19);
        row1.getCell(19).setCellStyle(colStyleTopLeft);
        row1.createCell(20).setCellValue("MedDRA Term");
        sheet1.autoSizeColumn(20);
        row1.getCell(20).setCellStyle(colStyleTopLeft);
        row1.createCell(21).setCellValue("MedDRA Level");
        sheet1.autoSizeColumn(21);
        row1.getCell(21).setCellStyle(colStyleTopLeft);
        row1.createCell(22).setCellValue("MedDRA Qualifier");
        sheet1.autoSizeColumn(22);
        row1.getCell(22).setCellStyle(colStyleTopLeft); /*  */
        row1.createCell(23).setCellValue("Comment");
        sheet1.autoSizeColumn(23);
        row1.getCell(23).setCellStyle(colStyleTopLeft);
    
    idx = idx + 1;
    idx1 = idx1 + 1;
    row = sheet.createRow(idx); //creating 2nd row
    row1 = sheet1.createRow(idx1); //creating 2nd row
    }
        
           
        if (viewObjectRow.getEarliestSafetyTopic() != null){
            row.createCell(0).setCellValue(viewObjectRow.getEarliestSafetyTopic().toString());
            
            if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
            row.getCell(0).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
                row.getCell(0).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
                row.getCell(0).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(0).setCellValue("");
        sheet.autoSizeColumn(0);

        if (viewObjectRow.getEarliestSpp() != null){
            row.createCell(1).setCellValue(viewObjectRow.getEarliestSpp().toString());
            if((viewObjectRow.getEarliestSppColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
            row.getCell(1).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSppColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
                row.getCell(1).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSppColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
                row.getCell(1).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(1).setCellValue("");
        sheet.autoSizeColumn(1);

        if (viewObjectRow.getEarliestDsur() != null){
            row.createCell(2).setCellValue(viewObjectRow.getEarliestDsur());
            if((viewObjectRow.getEarliestDsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
            row.getCell(2).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestDsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
                row.getCell(2).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestDsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
                row.getCell(2).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(2).setCellValue("");
        sheet.autoSizeColumn(2);
        
        if (viewObjectRow.getEarliestRmp() != null){
            row.createCell(3).setCellValue(viewObjectRow.getEarliestRmp());
            if((viewObjectRow.getEarliestRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
            row.getCell(3).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
                row.getCell(3).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
                row.getCell(3).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(3).setCellValue("");
        sheet.autoSizeColumn(3);
        
        if (viewObjectRow.getEarliestPsur() != null){
            row.createCell(4).setCellValue(viewObjectRow.getEarliestPsur());
            if((viewObjectRow.getEarliestPsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
            row.getCell(4).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestPsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
                row.getCell(4).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestPsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
                row.getCell(4).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(4).setCellValue("");
        sheet.autoSizeColumn(4);
        
        if (viewObjectRow.getEarliestIb() != null){
            row.createCell(5).setCellValue(viewObjectRow.getEarliestIb());
        if((viewObjectRow.getEarliestIbColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
        row.getCell(5).setCellStyle(greenColourCellStyle);
        }else if((viewObjectRow.getEarliestIbColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
            row.getCell(5).setCellStyle(redColourCellStyle); 
        }else if((viewObjectRow.getEarliestIbColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
            row.getCell(5).setCellStyle(orangeColourCellStyle);
        }
        }
        else
            row.createCell(5).setCellValue("");
        sheet.autoSizeColumn(5);
        
        if (viewObjectRow.getEarliestCds() != null){
            row.createCell(6).setCellValue(viewObjectRow.getEarliestCds());
            if((viewObjectRow.getEarliestCdsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
            row.getCell(6).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestCdsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
                row.getCell(6).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestCdsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
                row.getCell(6).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(6).setCellValue("");
        sheet.autoSizeColumn(6);
        
        if (viewObjectRow.getEarliestOtherSearch() != null){
            row.createCell(7).setCellValue(viewObjectRow.getEarliestOtherSearch());
            if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
            row.getCell(7).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
                row.getCell(7).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
                row.getCell(7).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(7).setCellValue("");
        sheet.autoSizeColumn(7);
        
        if (viewObjectRow.getEarliestMissingInformation() != null){
            row.createCell(8).setCellValue(viewObjectRow.getEarliestMissingInformation());
            if((viewObjectRow.getEarliestMissingInformColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
            row.getCell(8).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMissingInformColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
                row.getCell(8).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMissingInformColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
                row.getCell(8).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(8).setCellValue("");
        sheet.autoSizeColumn(8);
        
        if (viewObjectRow.getEarliestExpeditingRules() != null){
            row.createCell(9).setCellValue(viewObjectRow.getEarliestExpeditingRules());
            if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
            row.getCell(9).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
                row.getCell(9).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
                row.getCell(9).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(9).setCellValue("");
        sheet.autoSizeColumn(9);
        
        if (viewObjectRow.getEarliestUnderlyingDisease() != null){
            row.createCell(10).setCellValue(viewObjectRow.getEarliestUnderlyingDisease());
            if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
            row.getCell(10).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
                row.getCell(10).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
                row.getCell(10).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(10).setCellValue("");
        sheet.autoSizeColumn(10);
        
        if (viewObjectRow.getEarliestAesiForNisProtcol() != null){
            row.createCell(11).setCellValue(viewObjectRow.getEarliestAesiForNisProtcol());
            if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
            row.getCell(11).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
                row.getCell(11).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
                row.getCell(11).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(11).setCellValue("");
        sheet.autoSizeColumn(11);
        
        if (viewObjectRow.getEarliestAesiNotRmp() != null){
            row.createCell(12).setCellValue(viewObjectRow.getEarliestAesiNotRmp());
            if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
            row.getCell(12).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
                row.getCell(12).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
                row.getCell(12).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(12).setCellValue("");
        sheet.autoSizeColumn(12);
        
        if (viewObjectRow.getEarliestSoc() != null){
            row.createCell(13).setCellValue(viewObjectRow.getEarliestSoc());
            if((viewObjectRow.getEarliestSocColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
            row.getCell(13).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSocColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
                row.getCell(13).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSocColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
                row.getCell(13).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(13).setCellValue("");
        sheet.autoSizeColumn(13);
        
        if (viewObjectRow.getEarliestGender() != null){
            row.createCell(14).setCellValue(viewObjectRow.getEarliestGender());
            if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
            row.getCell(14).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                row.getCell(14).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                row.getCell(14).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(14).setCellValue("");
        sheet.autoSizeColumn(14);
        
        if (viewObjectRow.getEarliestCombAgeSubGrp() != null){
            row.createCell(15).setCellValue(viewObjectRow.getEarliestCombAgeSubGrp());
            if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
            row.getCell(15).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                row.getCell(15).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                row.getCell(15).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(15).setCellValue("");
        sheet.autoSizeColumn(15);
        
        if (viewObjectRow.getEarliestDataDomain() != null){
            row.createCell(16).setCellValue(viewObjectRow.getEarliestDataDomain());
            if((viewObjectRow.getEarliestDataDomainColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
            row.getCell(16).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestDataDomainColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
                row.getCell(16).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestDataDomainColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
                row.getCell(16).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(16).setCellValue("");
        sheet.autoSizeColumn(16);
        
        if (viewObjectRow.getEarliestSearchDetails() != null){
            row.createCell(17).setCellValue(viewObjectRow.getEarliestSearchDetails());
            if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
            row.getCell(17).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
                row.getCell(17).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
                row.getCell(17).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(17).setCellValue("");
        sheet.autoSizeColumn(17);
        
        if (viewObjectRow.getEarliestSearchAppliedTo() != null){
            row.createCell(18).setCellValue(viewObjectRow.getEarliestSearchAppliedTo());
            if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
            row.getCell(18).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
                row.getCell(18).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
                row.getCell(18).setCellStyle(orangeColourCellStyle);
            }
        }
        else
        row.createCell(18).setCellValue("");
        sheet.autoSizeColumn(18);
        
        if (viewObjectRow.getEarliestMeddraCode() != null){
            row.createCell(19).setCellValue(viewObjectRow.getEarliestMeddraCode());
            if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
            row.getCell(19).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
                row.getCell(19).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
                row.getCell(19).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(19).setCellValue("");
        sheet.autoSizeColumn(19);
        
        if (viewObjectRow.getEarliestMeddraTerm() != null){
            row.createCell(20).setCellValue(viewObjectRow.getEarliestMeddraTerm());
            if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
            row.getCell(20).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
                row.getCell(20).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
                row.getCell(20).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(20).setCellValue("");
        sheet.autoSizeColumn(20);
        
        if (viewObjectRow.getEarliestMeddraExtension() != null){
            row.createCell(21).setCellValue(viewObjectRow.getEarliestMeddraExtension());
            if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
            row.getCell(21).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
                row.getCell(21).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
                row.getCell(21).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(21).setCellValue("");
        sheet.autoSizeColumn(21);
        
        if (viewObjectRow.getEarliestMeddraQualifier() != null){
            row.createCell(22).setCellValue(viewObjectRow.getEarliestMeddraQualifier());
            if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
            row.getCell(22).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
                row.getCell(22).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
                row.getCell(22).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(22).setCellValue("");
        sheet.autoSizeColumn(22);
        
        if (viewObjectRow.getEarliestNonMeddraCompCmt() != null){
            row.createCell(23).setCellValue(viewObjectRow.getEarliestNonMeddraCompCmt());
            if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
            row.getCell(23).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
                row.getCell(23).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
                row.getCell(23).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(23).setCellValue("");
        sheet.autoSizeColumn(23);
               
        
        if (viewObjectRow.getLatestSafetyTopic() != null){
            row1.createCell(0).setCellValue(viewObjectRow.getLatestSafetyTopic().toString());
            if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
            row1.getCell(0).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
                row1.getCell(0).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
                row1.getCell(0).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(0).setCellValue("");
        sheet1.autoSizeColumn(0);

        if (viewObjectRow.getLatestSpp() != null){
            row1.createCell(1).setCellValue(viewObjectRow.getLatestSpp().toString());
            if((viewObjectRow.getLatestSppColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
            row1.getCell(1).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSppColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
                row1.getCell(1).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSppColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
                row1.getCell(1).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(1).setCellValue("");
        sheet1.autoSizeColumn(1);

        if (viewObjectRow.getLatestDsur() != null){
            row1.createCell(2).setCellValue(viewObjectRow.getLatestDsur());
            if((viewObjectRow.getLatestDsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
            row1.getCell(2).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestDsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
                row1.getCell(2).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestDsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
                row1.getCell(2).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(2).setCellValue("");
        sheet1.autoSizeColumn(2);
        
        if (viewObjectRow.getLatestRmp() != null){
            row1.createCell(3).setCellValue(viewObjectRow.getLatestRmp());
            if((viewObjectRow.getLatestRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
            row1.getCell(3).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
                row1.getCell(3).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
                row1.getCell(3).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(3).setCellValue("");
        sheet1.autoSizeColumn(3);
        
        if (viewObjectRow.getLatestPsur() != null){
            row1.createCell(4).setCellValue(viewObjectRow.getLatestPsur());
            if((viewObjectRow.getLatestPsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
            row1.getCell(4).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestPsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
                row1.getCell(4).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestPsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
                row1.getCell(4).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(4).setCellValue("");
        sheet1.autoSizeColumn(4);
        
        if (viewObjectRow.getLatestIb() != null){
            row1.createCell(5).setCellValue(viewObjectRow.getLatestIb());
            if((viewObjectRow.getLatestIbColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                    row1.getCell(5).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getLatestIbColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                        row1.getCell(5).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getLatestIbColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                        row1.getCell(5).setCellStyle(orangeColourCellStyle);
                    }
        }
        else
            row1.createCell(5).setCellValue("");
        sheet1.autoSizeColumn(5);
        
        if (viewObjectRow.getLatestCds() != null){
            row1.createCell(6).setCellValue(viewObjectRow.getLatestCds());
            if((viewObjectRow.getLatestCdsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
            row1.getCell(6).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestCdsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
                row1.getCell(6).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestCdsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
                row1.getCell(6).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(6).setCellValue("");
        sheet1.autoSizeColumn(6);
        
        if (viewObjectRow.getLatestOtherSearch() != null){
            row1.createCell(7).setCellValue(viewObjectRow.getLatestOtherSearch());
            if((viewObjectRow.getLatestOtherSearchColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
            row1.getCell(7).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestOtherSearchColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
                row1.getCell(7).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestOtherSearchColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
                row1.getCell(7).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(7).setCellValue("");
        sheet1.autoSizeColumn(7);
        
        if (viewObjectRow.getLatestMissingInformation() != null){
            row1.createCell(8).setCellValue(viewObjectRow.getLatestMissingInformation());
            if((viewObjectRow.getLatestMissingInfoColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
            row1.getCell(8).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMissingInfoColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
                row1.getCell(8).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMissingInfoColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
                row1.getCell(8).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(8).setCellValue("");
        sheet1.autoSizeColumn(8);
        
        if (viewObjectRow.getLatestExpeditingRules() != null){
            row1.createCell(9).setCellValue(viewObjectRow.getLatestExpeditingRules());
            if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
            row1.getCell(9).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
                row1.getCell(9).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
                row1.getCell(9).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(9).setCellValue("");
        sheet1.autoSizeColumn(9);
        
        if (viewObjectRow.getLatestUnderlyingDisease() != null){
            row1.createCell(10).setCellValue(viewObjectRow.getLatestUnderlyingDisease());
                if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                row1.getCell(10).setCellStyle(greenColourCellStyle);
                }else if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                    row1.getCell(10).setCellStyle(redColourCellStyle); 
                }else if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                    row1.getCell(10).setCellStyle(orangeColourCellStyle);
                }
        }
        else
            row1.createCell(10).setCellValue("");
        sheet1.autoSizeColumn(10);
        
        if (viewObjectRow.getLatestAesiForNisProtocol() != null){
            row1.createCell(11).setCellValue(viewObjectRow.getLatestAesiForNisProtocol());
            if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
            row1.getCell(11).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
                row1.getCell(11).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
                row1.getCell(11).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(11).setCellValue("");
        sheet1.autoSizeColumn(11);
        
        if (viewObjectRow.getLatestAesiNotRmp() != null){
            row1.createCell(12).setCellValue(viewObjectRow.getLatestAesiNotRmp());
            if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
            row1.getCell(12).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
                row1.getCell(12).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
                row1.getCell(12).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(12).setCellValue("");
        sheet1.autoSizeColumn(12);
        
        if (viewObjectRow.getLatestSoc() != null){
            row1.createCell(13).setCellValue(viewObjectRow.getLatestSoc());
            if((viewObjectRow.getLatestSocColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
            row1.getCell(13).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSocColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
                row1.getCell(13).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSocColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
                row1.getCell(13).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(13).setCellValue("");
        sheet1.autoSizeColumn(13);
        
        if (viewObjectRow.getLatestGender() != null){
            row1.createCell(14).setCellValue(viewObjectRow.getLatestGender());
            if((viewObjectRow.getLatestGenderCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestGenderCodeColor())){
            row1.getCell(14).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestGenderCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestGenderCodeColor())){
                row1.getCell(14).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestGenderCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestGenderCodeColor())){
                row1.getCell(14).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(14).setCellValue("");
        sheet1.autoSizeColumn(14);
        
        if (viewObjectRow.getLatestCombAgeSubGrp() != null){
            row1.createCell(15).setCellValue(viewObjectRow.getLatestCombAgeSubGrp());
            if((viewObjectRow.getLatestCombAgeSubGrpClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestCombAgeSubGrpClr())){
            row1.getCell(15).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestCombAgeSubGrpClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestCombAgeSubGrpClr())){
                row1.getCell(15).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestCombAgeSubGrpClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestCombAgeSubGrpClr())){
                row1.getCell(15).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(15).setCellValue("");
        sheet1.autoSizeColumn(15);
        
        if (viewObjectRow.getLatestDataDomain() != null){
            row1.createCell(16).setCellValue(viewObjectRow.getLatestDataDomain());
            if((viewObjectRow.getLatestDataDomainColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
            row.getCell(16).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestDataDomainColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
                row.getCell(16).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestDataDomainColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
                row.getCell(16).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(16).setCellValue("");
        sheet1.autoSizeColumn(16);
        
        if (viewObjectRow.getLatestSearchDetails() != null){
            row1.createCell(17).setCellValue(viewObjectRow.getLatestSearchDetails());
            if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
            row1.getCell(17).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
                row1.getCell(17).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
                row1.getCell(17).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(17).setCellValue("");
        sheet1.autoSizeColumn(17);
        
        if (viewObjectRow.getLatestSearchAppliedTo() != null){
            row1.createCell(18).setCellValue(viewObjectRow.getLatestSearchAppliedTo());
            if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
            row1.getCell(18).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
                row1.getCell(18).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
                row1.getCell(18).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(18).setCellValue("");
        sheet1.autoSizeColumn(18);
        
        if (viewObjectRow.getLatestMeddraCode() != null){
            row1.createCell(19).setCellValue(viewObjectRow.getLatestMeddraCode());
            if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
            row1.getCell(19).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
                row1.getCell(19).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
                row1.getCell(19).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(19).setCellValue("");
        sheet1.autoSizeColumn(19);
        
        if (viewObjectRow.getLatestMeddraTerm() != null){
            row1.createCell(20).setCellValue(viewObjectRow.getLatestMeddraTerm());
            if((viewObjectRow.getLatestMeddraTermColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
            row1.getCell(20).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraTermColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
                row1.getCell(20).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraTermColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
                row1.getCell(20).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(20).setCellValue("");
        sheet1.autoSizeColumn(20);
        
        if (viewObjectRow.getLatestMeddraExtension() != null){
            row1.createCell(21).setCellValue(viewObjectRow.getLatestMeddraExtension());
            if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
            row1.getCell(21).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
                row1.getCell(21).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
                row1.getCell(21).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(21).setCellValue("");
        sheet1.autoSizeColumn(21);
        
        if (viewObjectRow.getLatestMeddraQualifier() != null){
            row1.createCell(22).setCellValue(viewObjectRow.getLatestMeddraQualifier());
            if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
            row1.getCell(22).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
                row1.getCell(22).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
                row1.getCell(22).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(22).setCellValue("");
        sheet1.autoSizeColumn(22);
        
        if (viewObjectRow.getLatestNonMeddraCompCmt() != null){
            row1.createCell(23).setCellValue(viewObjectRow.getLatestNonMeddraCompCmt());
            if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
            row1.getCell(23).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
                row1.getCell(23).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
                row1.getCell(23).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(23).setCellValue("");
        sheet1.autoSizeColumn(23);
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    idx1 = idx1 + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }
    
    public List exportVersionCompareCurrent() {
    HSSFWorkbook wb = new HSSFWorkbook();
    HSSFSheet sheet = wb.createSheet("Version 1"); //Sheet Name
    HSSFSheet sheet1 = wb.createSheet("Version 2");
    int idx = 0; // rows index
    int idx1 = 0; // rows index
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
    HSSFRow row1 = null;
    
        DCBindingContainer bindings = this.getDCBindingContainer();
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsVersionCompareIterator");
        ViewObject vo = itrBinding.getViewObject();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    while (vo.hasNext()) { 
    CRSVersionCompareVORowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CRSVersionCompareVORowImpl) vo.next();
            } else {
                viewObjectRow = (CRSVersionCompareVORowImpl) vo.first();
            }
    if(firstRow){
    row = sheet.createRow(idx); //creating 1st row
    row.createCell(1).setCellValue("Purpose Of Risk Definition");
    row.getCell(1).setCellStyle(colStyleTopLeft);
    sheet.addMergedRegion(new CellRangeAddress(0,0,1,12));
    row.createCell(17).setCellValue("Safety Topic Definition");
    row.getCell(17).setCellStyle(colStyleTopLeft);
    sheet.addMergedRegion(new CellRangeAddress(0,0,17,20));
    
    row1 = sheet1.createRow(idx1); //creating 1st row
    row1.createCell(1).setCellValue("Purpose Of Risk Definition");
    row1.getCell(1).setCellStyle(colStyleTopLeft);
    sheet1.addMergedRegion(new CellRangeAddress(0,0,1,12));
    row1.createCell(17).setCellValue("Safety Topic Definition");
    row1.getCell(17).setCellStyle(colStyleTopLeft);
    sheet1.addMergedRegion(new CellRangeAddress(0,0,17,20));
    
    idx = idx + 1;
    idx1 = idx1 + 1;
    }
    row = sheet.createRow(idx); //creating 1st row
    row1 = sheet1.createRow(idx1); //creating 1st row
    firstRow = false;
    if(flag == false){
                row.createCell(0).setCellValue("Safety Topic Of Interest"); //setting column heading
                sheet.autoSizeColumn(0);
                row.getCell(0).setCellStyle(colStyleTopLeft);
                row.createCell(1).setCellValue("SP");
                sheet.autoSizeColumn(1);
                row.getCell(1).setCellStyle(colStyleTopLeft);
                row.createCell(2).setCellValue("DS");
                sheet.autoSizeColumn(2);
                row.getCell(2).setCellStyle(colStyleTopLeft);
                row.createCell(3).setCellValue("RM");
                sheet.autoSizeColumn(3);
                row.getCell(3).setCellStyle(colStyleTopLeft);
                row.createCell(4).setCellValue("PS");
                sheet.autoSizeColumn(4);
                row.getCell(4).setCellStyle(colStyleTopLeft);
                row.createCell(5).setCellValue("IB");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("CD");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);
                row.createCell(7).setCellValue("OS");
                sheet.autoSizeColumn(7);
                row.getCell(7).setCellStyle(colStyleTopLeft);
                row.createCell(8).setCellValue("MI");
                sheet.autoSizeColumn(8);
                row.getCell(8).setCellStyle(colStyleTopLeft);
                row.createCell(9).setCellValue("ER");
                sheet.autoSizeColumn(9);
                row.getCell(9).setCellStyle(colStyleTopLeft);
                row.createCell(10).setCellValue("UD");
                sheet.autoSizeColumn(10);
                row.getCell(10).setCellStyle(colStyleTopLeft);
                row.createCell(11).setCellValue("A1");
                sheet.autoSizeColumn(11);
                row.getCell(11).setCellStyle(colStyleTopLeft);
                row.createCell(12).setCellValue("A2");
                sheet.autoSizeColumn(12);
                row.getCell(12).setCellStyle(colStyleTopLeft);
                row.createCell(13).setCellValue("SOC");
                sheet.autoSizeColumn(13);
                row.getCell(13).setCellStyle(colStyleTopLeft);
                row.createCell(14).setCellValue("Gender");
                sheet.autoSizeColumn(14);
                row.getCell(14).setCellStyle(colStyleTopLeft);
                row.createCell(15).setCellValue("Age");
                sheet.autoSizeColumn(15);
                row.getCell(15).setCellStyle(colStyleTopLeft);
                row.createCell(16).setCellValue("Data Domain");
                sheet.autoSizeColumn(16);
                row.getCell(16).setCellStyle(colStyleTopLeft);
                row.createCell(17).setCellValue("Search Criteria Details");
                sheet.autoSizeColumn(17);
                row.getCell(17).setCellStyle(colStyleTopLeft);
                row.createCell(18).setCellValue("Search Applied To");
                sheet.autoSizeColumn(18);
                row.getCell(18).setCellStyle(colStyleTopLeft);
                row.createCell(19).setCellValue("MedDRA Code");
                sheet.autoSizeColumn(19);
                row.getCell(19).setCellStyle(colStyleTopLeft);
                row.createCell(20).setCellValue("MedDRA Term");
                sheet.autoSizeColumn(20);
                row.getCell(20).setCellStyle(colStyleTopLeft);
                row.createCell(21).setCellValue("MedDRA Level");
                sheet.autoSizeColumn(21);
                row.getCell(21).setCellStyle(colStyleTopLeft);
                row.createCell(22).setCellValue("MedDRA Qualifier");
                sheet.autoSizeColumn(22);
                row.getCell(22).setCellStyle(colStyleTopLeft); /*  */
                row.createCell(23).setCellValue("Comment");
                sheet.autoSizeColumn(23);
                row.getCell(23).setCellStyle(colStyleTopLeft);
                
        row1.createCell(0).setCellValue("Safety Topic Of Interest"); //setting column heading
        sheet1.autoSizeColumn(0);
        row1.getCell(0).setCellStyle(colStyleTopLeft);
        row1.createCell(1).setCellValue("SP");
        sheet1.autoSizeColumn(1);
        row1.getCell(1).setCellStyle(colStyleTopLeft);
        row1.createCell(2).setCellValue("DS");
        sheet1.autoSizeColumn(2);
        row1.getCell(2).setCellStyle(colStyleTopLeft);
        row1.createCell(3).setCellValue("RM");
        sheet1.autoSizeColumn(3);
        row1.getCell(3).setCellStyle(colStyleTopLeft);
        row1.createCell(4).setCellValue("PS");
        sheet1.autoSizeColumn(4);
        row1.getCell(4).setCellStyle(colStyleTopLeft);
        row1.createCell(5).setCellValue("IB");
        sheet1.autoSizeColumn(5);
        row1.getCell(5).setCellStyle(colStyleTopLeft);
        row1.createCell(6).setCellValue("CD");
        sheet1.autoSizeColumn(6);
        row1.getCell(6).setCellStyle(colStyleTopLeft);
        row1.createCell(7).setCellValue("OS");
        sheet1.autoSizeColumn(7);
        row1.getCell(7).setCellStyle(colStyleTopLeft);
        row1.createCell(8).setCellValue("MI");
        sheet1.autoSizeColumn(8);
        row1.getCell(8).setCellStyle(colStyleTopLeft);
        row1.createCell(9).setCellValue("ER");
        sheet1.autoSizeColumn(9);
        row1.getCell(9).setCellStyle(colStyleTopLeft);
        row1.createCell(10).setCellValue("UD");
        sheet1.autoSizeColumn(10);
        row1.getCell(10).setCellStyle(colStyleTopLeft);
        row1.createCell(11).setCellValue("A1");
        sheet1.autoSizeColumn(11);
        row1.getCell(11).setCellStyle(colStyleTopLeft);
        row1.createCell(12).setCellValue("A2");
        sheet1.autoSizeColumn(12);
        row1.getCell(12).setCellStyle(colStyleTopLeft);
        row1.createCell(13).setCellValue("SOC");
        sheet1.autoSizeColumn(13);
        row1.getCell(13).setCellStyle(colStyleTopLeft);
        row.createCell(14).setCellValue("Gender");
                sheet.autoSizeColumn(14);
                row.getCell(14).setCellStyle(colStyleTopLeft);
                row.createCell(15).setCellValue("Age");
                sheet.autoSizeColumn(15);
                row.getCell(15).setCellStyle(colStyleTopLeft);
        row1.createCell(16).setCellValue("Data Domain");
        sheet1.autoSizeColumn(16);
        row1.getCell(16).setCellStyle(colStyleTopLeft);
        row1.createCell(17).setCellValue("Search Criteria Details");
        sheet1.autoSizeColumn(17);
        row1.getCell(17).setCellStyle(colStyleTopLeft);
        row1.createCell(18).setCellValue("Search Applied To");
        sheet1.autoSizeColumn(18);
        row1.getCell(18).setCellStyle(colStyleTopLeft);
        row1.createCell(19).setCellValue("MedDRA Code");
        sheet1.autoSizeColumn(19);
        row1.getCell(19).setCellStyle(colStyleTopLeft);
        row1.createCell(20).setCellValue("MedDRA Term");
        sheet1.autoSizeColumn(20);
        row1.getCell(20).setCellStyle(colStyleTopLeft);
        row1.createCell(21).setCellValue("MedDRA Level");
        sheet1.autoSizeColumn(21);
        row1.getCell(21).setCellStyle(colStyleTopLeft);
        row1.createCell(22).setCellValue("MedDRA Qualifier");
        sheet1.autoSizeColumn(22);
        row1.getCell(22).setCellStyle(colStyleTopLeft); /*  */
        row1.createCell(23).setCellValue("Comment");
        sheet1.autoSizeColumn(23);
        row1.getCell(23).setCellStyle(colStyleTopLeft);
    
    idx = idx + 1;
    idx1 = idx1 + 1;
    row = sheet.createRow(idx); //creating 2nd row
    row1 = sheet1.createRow(idx1); //creating 2nd row
    }
        
           
        if (viewObjectRow.getEarliestSafetyTopic() != null){
            row.createCell(0).setCellValue(viewObjectRow.getEarliestSafetyTopic().toString());
            
            if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
            row.getCell(0).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
                row.getCell(0).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSafetyTopicColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSafetyTopicColor())){
                row.getCell(0).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(0).setCellValue("");
        sheet.autoSizeColumn(0);

        if (viewObjectRow.getEarliestSpp() != null){
            row.createCell(1).setCellValue(viewObjectRow.getEarliestSpp().toString());
            if((viewObjectRow.getEarliestSppColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
            row.getCell(1).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSppColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
                row.getCell(1).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSppColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSppColor())){
                row.getCell(1).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(1).setCellValue("");
        sheet.autoSizeColumn(1);

        if (viewObjectRow.getEarliestDsur() != null){
            row.createCell(2).setCellValue(viewObjectRow.getEarliestDsur());
            if((viewObjectRow.getEarliestDsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
            row.getCell(2).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestDsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
                row.getCell(2).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestDsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestDsurColor())){
                row.getCell(2).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(2).setCellValue("");
        sheet.autoSizeColumn(2);
        
        if (viewObjectRow.getEarliestRmp() != null){
            row.createCell(3).setCellValue(viewObjectRow.getEarliestRmp());
            if((viewObjectRow.getEarliestRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
            row.getCell(3).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
                row.getCell(3).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestRmpColor())){
                row.getCell(3).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(3).setCellValue("");
        sheet.autoSizeColumn(3);
        
        if (viewObjectRow.getEarliestPsur() != null){
            row.createCell(4).setCellValue(viewObjectRow.getEarliestPsur());
            if((viewObjectRow.getEarliestPsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
            row.getCell(4).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestPsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
                row.getCell(4).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestPsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestPsurColor())){
                row.getCell(4).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(4).setCellValue("");
        sheet.autoSizeColumn(4);
        
        if (viewObjectRow.getEarliestIb() != null){
            row.createCell(5).setCellValue(viewObjectRow.getEarliestIb());
        if((viewObjectRow.getEarliestIbColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
        row.getCell(5).setCellStyle(greenColourCellStyle);
        }else if((viewObjectRow.getEarliestIbColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
            row.getCell(5).setCellStyle(redColourCellStyle); 
        }else if((viewObjectRow.getEarliestIbColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestIbColor())){
            row.getCell(5).setCellStyle(orangeColourCellStyle);
        }
        }
        else
            row.createCell(5).setCellValue("");
        sheet.autoSizeColumn(5);
        
        if (viewObjectRow.getEarliestCds() != null){
            row.createCell(6).setCellValue(viewObjectRow.getEarliestCds());
            if((viewObjectRow.getEarliestCdsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
            row.getCell(6).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestCdsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
                row.getCell(6).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestCdsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestCdsColor())){
                row.getCell(6).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(6).setCellValue("");
        sheet.autoSizeColumn(6);
        
        if (viewObjectRow.getEarliestOtherSearch() != null){
            row.createCell(7).setCellValue(viewObjectRow.getEarliestOtherSearch());
            if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
            row.getCell(7).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
                row.getCell(7).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestOtherSearchColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestOtherSearchColor())){
                row.getCell(7).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(7).setCellValue("");
        sheet.autoSizeColumn(7);
        
        if (viewObjectRow.getEarliestMissingInformation() != null){
            row.createCell(8).setCellValue(viewObjectRow.getEarliestMissingInformation());
            if((viewObjectRow.getEarliestMissingInformColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
            row.getCell(8).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMissingInformColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
                row.getCell(8).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMissingInformColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMissingInformColor())){
                row.getCell(8).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(8).setCellValue("");
        sheet.autoSizeColumn(8);
        
        if (viewObjectRow.getEarliestExpeditingRules() != null){
            row.createCell(9).setCellValue(viewObjectRow.getEarliestExpeditingRules());
            if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
            row.getCell(9).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
                row.getCell(9).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestExpeditingRulColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestExpeditingRulColor())){
                row.getCell(9).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(9).setCellValue("");
        sheet.autoSizeColumn(9);
        
        if (viewObjectRow.getEarliestUnderlyingDisease() != null){
            row.createCell(10).setCellValue(viewObjectRow.getEarliestUnderlyingDisease());
            if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
            row.getCell(10).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
                row.getCell(10).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestUnderlyingDisColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestUnderlyingDisColor())){
                row.getCell(10).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(10).setCellValue("");
        sheet.autoSizeColumn(10);
        
        if (viewObjectRow.getEarliestAesiForNisProtcol() != null){
            row.createCell(11).setCellValue(viewObjectRow.getEarliestAesiForNisProtcol());
            if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
            row.getCell(11).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
                row.getCell(11).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestAesiFrNisProClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestAesiFrNisProClr())){
                row.getCell(11).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(11).setCellValue("");
        sheet.autoSizeColumn(11);
        
        if (viewObjectRow.getEarliestAesiNotRmp() != null){
            row.createCell(12).setCellValue(viewObjectRow.getEarliestAesiNotRmp());
            if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
            row.getCell(12).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
                row.getCell(12).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestAesiNotRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestAesiNotRmpColor())){
                row.getCell(12).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(12).setCellValue("");
        sheet.autoSizeColumn(12);
        
        if (viewObjectRow.getEarliestSoc() != null){
            row.createCell(13).setCellValue(viewObjectRow.getEarliestSoc());
            if((viewObjectRow.getEarliestSocColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
            row.getCell(13).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSocColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
                row.getCell(13).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSocColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSocColor())){
                row.getCell(13).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(13).setCellValue("");
        sheet.autoSizeColumn(13);
        
        if (viewObjectRow.getEarliestGender() != null){
                    row.createCell(14).setCellValue(viewObjectRow.getEarliestGender());
                    if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                    row.getCell(14).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                        row.getCell(14).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                        row.getCell(14).setCellStyle(orangeColourCellStyle);
                    }
                }
                else
                    row.createCell(14).setCellValue("");
                sheet.autoSizeColumn(14);
                
                if (viewObjectRow.getEarliestCombAgeSubGrp() != null){
                    row.createCell(15).setCellValue(viewObjectRow.getEarliestCombAgeSubGrp());
                    if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                    row.getCell(15).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                        row.getCell(15).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                        row.getCell(15).setCellStyle(orangeColourCellStyle);
                    }
                }
                else
                    row.createCell(15).setCellValue("");
                sheet.autoSizeColumn(15);
        
        if (viewObjectRow.getEarliestDataDomain() != null){
            row.createCell(16).setCellValue(viewObjectRow.getEarliestDataDomain());
            if((viewObjectRow.getEarliestDataDomainColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
            row.getCell(16).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestDataDomainColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
                row.getCell(16).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestDataDomainColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestDataDomainColor())){
                row.getCell(16).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(16).setCellValue("");
        sheet.autoSizeColumn(16);
        
        if (viewObjectRow.getEarliestSearchDetails() != null){
            row.createCell(17).setCellValue(viewObjectRow.getEarliestSearchDetails());
            if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
            row.getCell(17).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
                row.getCell(17).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSearchDetailsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSearchDetailsColor())){
                row.getCell(17).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(17).setCellValue("");
        sheet.autoSizeColumn(17);
        
        if (viewObjectRow.getEarliestSearchAppliedTo() != null){
            row.createCell(18).setCellValue(viewObjectRow.getEarliestSearchAppliedTo());
            if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
            row.getCell(18).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
                row.getCell(18).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestSearchAppliedToClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestSearchAppliedToClr())){
                row.getCell(18).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(18).setCellValue("");
            sheet.autoSizeColumn(18);
        
        if (viewObjectRow.getEarliestMeddraCode() != null){
            row.createCell(19).setCellValue(viewObjectRow.getEarliestMeddraCode());
            if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
            row.getCell(19).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
                row.getCell(19).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraCodeColor())){
                row.getCell(19).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(19).setCellValue("");
            sheet.autoSizeColumn(19);
        
        if (viewObjectRow.getEarliestMeddraTerm() != null){
            row.createCell(20).setCellValue(viewObjectRow.getEarliestMeddraTerm());
            if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
            row.getCell(20).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
                row.getCell(20).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraTermColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraTermColor())){
                row.getCell(20).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(20).setCellValue("");
        sheet.autoSizeColumn(20);
        
        if (viewObjectRow.getEarliestMeddraExtension() != null){
            row.createCell(21).setCellValue(viewObjectRow.getEarliestMeddraExtension());
            if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
            row.getCell(21).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
                row.getCell(21).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraExtColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraExtColor())){
                row.getCell(21).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(21).setCellValue("");
        sheet.autoSizeColumn(21);
        
        if (viewObjectRow.getEarliestMeddraQualifier() != null){
            row.createCell(22).setCellValue(viewObjectRow.getEarliestMeddraQualifier());
            if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
            row.getCell(22).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
                row.getCell(22).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestMeddraQualColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestMeddraQualColor())){
                row.getCell(22).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(22).setCellValue("");
        sheet.autoSizeColumn(22);
        
        if (viewObjectRow.getEarliestNonMeddraCompCmt() != null){
            row.createCell(23).setCellValue(viewObjectRow.getEarliestNonMeddraCompCmt());
            if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
            row.getCell(23).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
                row.getCell(23).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getEarliestNonMedCompCmtClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestNonMedCompCmtClr())){
                row.getCell(23).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row.createCell(23).setCellValue("");
        sheet.autoSizeColumn(23);
               
        
        if (viewObjectRow.getLatestSafetyTopic() != null){
            row1.createCell(0).setCellValue(viewObjectRow.getLatestSafetyTopic().toString());
            if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
            row1.getCell(0).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
                row1.getCell(0).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSafetyTopicColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSafetyTopicColor())){
                row1.getCell(0).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(0).setCellValue("");
        sheet1.autoSizeColumn(0);

        if (viewObjectRow.getLatestSpp() != null){
            row1.createCell(1).setCellValue(viewObjectRow.getLatestSpp().toString());
            if((viewObjectRow.getLatestSppColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
            row1.getCell(1).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSppColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
                row1.getCell(1).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSppColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSppColor())){
                row1.getCell(1).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(1).setCellValue("");
        sheet1.autoSizeColumn(1);

        if (viewObjectRow.getLatestDsur() != null){
            row1.createCell(2).setCellValue(viewObjectRow.getLatestDsur());
            if((viewObjectRow.getLatestDsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
            row1.getCell(2).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestDsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
                row1.getCell(2).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestDsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestDsurColor())){
                row1.getCell(2).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(2).setCellValue("");
        sheet1.autoSizeColumn(2);
        
        if (viewObjectRow.getLatestRmp() != null){
            row1.createCell(3).setCellValue(viewObjectRow.getLatestRmp());
            if((viewObjectRow.getLatestRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
            row1.getCell(3).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
                row1.getCell(3).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestRmpColor())){
                row1.getCell(3).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(3).setCellValue("");
        sheet1.autoSizeColumn(3);
        
        if (viewObjectRow.getLatestPsur() != null){
            row1.createCell(4).setCellValue(viewObjectRow.getLatestPsur());
            if((viewObjectRow.getLatestPsurColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
            row1.getCell(4).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestPsurColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
                row1.getCell(4).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestPsurColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestPsurColor())){
                row1.getCell(4).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(4).setCellValue("");
        sheet1.autoSizeColumn(4);
        
        if (viewObjectRow.getLatestIb() != null){
            row1.createCell(5).setCellValue(viewObjectRow.getLatestIb());
            if((viewObjectRow.getLatestIbColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                    row1.getCell(5).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getLatestIbColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                        row1.getCell(5).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getLatestIbColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestIbColor())){
                        row1.getCell(5).setCellStyle(orangeColourCellStyle);
                    }
        }
        else
            row1.createCell(5).setCellValue("");
        sheet1.autoSizeColumn(5);
        
        if (viewObjectRow.getLatestCds() != null){
            row1.createCell(6).setCellValue(viewObjectRow.getLatestCds());
            if((viewObjectRow.getLatestCdsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
            row1.getCell(6).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestCdsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
                row1.getCell(6).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestCdsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestCdsColor())){
                row1.getCell(6).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(6).setCellValue("");
        sheet1.autoSizeColumn(6);
        
        if (viewObjectRow.getLatestOtherSearch() != null){
            row1.createCell(7).setCellValue(viewObjectRow.getLatestOtherSearch());
            if((viewObjectRow.getLatestOtherSearchColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
            row1.getCell(7).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestOtherSearchColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
                row1.getCell(7).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestOtherSearchColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestOtherSearchColor())){
                row1.getCell(7).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(7).setCellValue("");
        sheet1.autoSizeColumn(7);
        
        if (viewObjectRow.getLatestMissingInformation() != null){
            row1.createCell(8).setCellValue(viewObjectRow.getLatestMissingInformation());
            if((viewObjectRow.getLatestMissingInfoColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
            row1.getCell(8).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMissingInfoColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
                row1.getCell(8).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMissingInfoColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMissingInfoColor())){
                row1.getCell(8).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(8).setCellValue("");
        sheet1.autoSizeColumn(8);
        
        if (viewObjectRow.getLatestExpeditingRules() != null){
            row1.createCell(9).setCellValue(viewObjectRow.getLatestExpeditingRules());
            if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
            row1.getCell(9).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
                row1.getCell(9).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestExpeditingRulesColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestExpeditingRulesColor())){
                row1.getCell(9).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(9).setCellValue("");
        sheet1.autoSizeColumn(9);
        
        if (viewObjectRow.getLatestUnderlyingDisease() != null){
            row1.createCell(10).setCellValue(viewObjectRow.getLatestUnderlyingDisease());
                if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                row1.getCell(10).setCellStyle(greenColourCellStyle);
                }else if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                    row1.getCell(10).setCellStyle(redColourCellStyle); 
                }else if((viewObjectRow.getLatestUnderlyingDisColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestUnderlyingDisColor())){
                    row1.getCell(10).setCellStyle(orangeColourCellStyle);
                }
        }
        else
            row1.createCell(10).setCellValue("");
        sheet1.autoSizeColumn(10);
        
        if (viewObjectRow.getLatestAesiForNisProtocol() != null){
            row1.createCell(11).setCellValue(viewObjectRow.getLatestAesiForNisProtocol());
            if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
            row1.getCell(11).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
                row1.getCell(11).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestAesiForNisProColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestAesiForNisProColor())){
                row1.getCell(11).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(11).setCellValue("");
        sheet1.autoSizeColumn(11);
        
        if (viewObjectRow.getLatestAesiNotRmp() != null){
            row1.createCell(12).setCellValue(viewObjectRow.getLatestAesiNotRmp());
            if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
            row1.getCell(12).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
                row1.getCell(12).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestAesiNotRmpColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestAesiNotRmpColor())){
                row1.getCell(12).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(12).setCellValue("");
        sheet1.autoSizeColumn(12);
        
        if (viewObjectRow.getLatestSoc() != null){
            row1.createCell(13).setCellValue(viewObjectRow.getLatestSoc());
            if((viewObjectRow.getLatestSocColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
            row1.getCell(13).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSocColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
                row1.getCell(13).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSocColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSocColor())){
                row1.getCell(13).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(13).setCellValue("");
        sheet1.autoSizeColumn(13);
        
        if (viewObjectRow.getEarliestGender() != null){
                    row.createCell(14).setCellValue(viewObjectRow.getEarliestGender());
                    if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                    row.getCell(14).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                        row.getCell(14).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getEarliestGenderCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestGenderCodeColor())){
                        row.getCell(14).setCellStyle(orangeColourCellStyle);
                    }
                }
                else
                    row.createCell(14).setCellValue("");
                sheet.autoSizeColumn(14);
                
                if (viewObjectRow.getEarliestCombAgeSubGrp() != null){
                    row.createCell(15).setCellValue(viewObjectRow.getEarliestCombAgeSubGrp());
                    if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("G").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                    row.getCell(15).setCellStyle(greenColourCellStyle);
                    }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("R").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                        row.getCell(15).setCellStyle(redColourCellStyle); 
                    }else if((viewObjectRow.getEarliestCombAgeSubGrpClr() != null) && ("O").equalsIgnoreCase(viewObjectRow.getEarliestCombAgeSubGrpClr())){
                        row.getCell(15).setCellStyle(orangeColourCellStyle);
                    }
                }
                else
                    row.createCell(15).setCellValue("");
                sheet.autoSizeColumn(15);
        
        if (viewObjectRow.getLatestDataDomain() != null){
            row1.createCell(16).setCellValue(viewObjectRow.getLatestDataDomain());
                if((viewObjectRow.getLatestDataDomainColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
                row.getCell(16).setCellStyle(greenColourCellStyle);
                }else if((viewObjectRow.getLatestDataDomainColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
                    row.getCell(16).setCellStyle(redColourCellStyle);
                }else if((viewObjectRow.getLatestDataDomainColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestDataDomainColor())){
                    row.getCell(16).setCellStyle(orangeColourCellStyle);
                }
        }
        else
            row1.createCell(16).setCellValue("");
        sheet1.autoSizeColumn(16);
        
        if (viewObjectRow.getLatestSearchDetails() != null){
            row1.createCell(17).setCellValue(viewObjectRow.getLatestSearchDetails());
            if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
            row1.getCell(17).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
                row1.getCell(17).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSearchDetailsColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSearchDetailsColor())){
                row1.getCell(17).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(17).setCellValue("");
        sheet1.autoSizeColumn(17);
        
        if (viewObjectRow.getLatestSearchAppliedTo() != null){
            row1.createCell(18).setCellValue(viewObjectRow.getLatestSearchAppliedTo());
            if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
            row1.getCell(18).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
                row1.getCell(18).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestSearchAppliedToColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestSearchAppliedToColor())){
                row1.getCell(18).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(18).setCellValue("");
            sheet1.autoSizeColumn(18);
        
        if (viewObjectRow.getLatestMeddraCode() != null){
            row1.createCell(19).setCellValue(viewObjectRow.getLatestMeddraCode());
            if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
            row1.getCell(19).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
                row1.getCell(19).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraCodeColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraCodeColor())){
                row1.getCell(19).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(19).setCellValue("");
        sheet1.autoSizeColumn(19);
        
        if (viewObjectRow.getLatestMeddraTerm() != null){
            row1.createCell(20).setCellValue(viewObjectRow.getLatestMeddraTerm());
            if((viewObjectRow.getLatestMeddraTermColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
            row1.getCell(20).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraTermColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
                row1.getCell(20).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraTermColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraTermColor())){
                row1.getCell(20).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(20).setCellValue("");
        sheet1.autoSizeColumn(20);
        
        if (viewObjectRow.getLatestMeddraExtension() != null){
            row1.createCell(21).setCellValue(viewObjectRow.getLatestMeddraExtension());
            if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
            row1.getCell(21).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
                row1.getCell(21).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraExtensionColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraExtensionColor())){
                row1.getCell(21).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(21).setCellValue("");
        sheet1.autoSizeColumn(21);
        
        if (viewObjectRow.getLatestMeddraQualifier() != null){
            row1.createCell(22).setCellValue(viewObjectRow.getLatestMeddraQualifier());
            if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
            row1.getCell(22).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
                row1.getCell(22).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestMeddraQualifierColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestMeddraQualifierColor())){
                row1.getCell(22).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(22).setCellValue("");
        sheet1.autoSizeColumn(22);
        
        if (viewObjectRow.getLatestNonMeddraCompCmt() != null){
            row1.createCell(23).setCellValue(viewObjectRow.getLatestNonMeddraCompCmt());
            if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("G").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
            row1.getCell(23).setCellStyle(greenColourCellStyle);
            }else if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("R").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
                row1.getCell(23).setCellStyle(redColourCellStyle); 
            }else if((viewObjectRow.getLatestNonMedCompCmtColor() != null) && ("O").equalsIgnoreCase(viewObjectRow.getLatestNonMedCompCmtColor())){
                row1.getCell(23).setCellStyle(orangeColourCellStyle);
            }
        }
        else
            row1.createCell(23).setCellValue("");
        sheet1.autoSizeColumn(23);
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    idx1 = idx1 + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }
    
    public List exportPTCurrentReport() {
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
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsExportPTCurrentIterator");
        ViewObject vo = itrBinding.getViewObject();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    while (vo.hasNext()) { 
    CrsExportPTCurrentVORowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.next();
            } else {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.first();
            }
    if(firstRow){
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("CRS Name: "+this.getSelectedCrsName());
        row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("CRS ID: "+this.getSelectedCrsId());
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("Status: "+this.getSelectedStatus());
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
            row.createCell(0).setCellValue("State: "+this.getSelectedState());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("GPSL: "+this.getSelectedBSL());
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("HPS: "+this.getSelectedTASL());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("Designee: "+this.getSelectedDesignee());
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
                row.getCell(4).setCellStyle(colStyleTopLeft);
                row.createCell(5).setCellValue("Gender Code");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("Age");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);    
    
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
        
        if (viewObjectRow.getGender() != null){
                    row.createCell(5).setCellValue(viewObjectRow.getGender());
                }
                else
                    row.createCell(5).setCellValue("");
                sheet.autoSizeColumn(5);
                
                if (viewObjectRow.getAge() != null){
                    row.createCell(6).setCellValue(viewObjectRow.getAge());
                }
                else
                    row.createCell(6).setCellValue("");
                sheet.autoSizeColumn(6);
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }
    
    public List exportPTCurrentReportDetail() {
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
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsExportPTCurrentDetailIterator");
        CrsExportPTCurrentVOImpl vo = (CrsExportPTCurrentVOImpl)itrBinding.getViewObject();
        
        DCIteratorBinding itrBinding1 = bindings.findIteratorBinding("CrsRiskBaseVOIterator");
        ViewObject vo1 = itrBinding1.getViewObject();
        Row crsRiskRelationRow = vo1.getCurrentRow();
        String safetyTopicOfInterest = (String)crsRiskRelationRow.getAttribute("SafetyTopicOfInterest");
        String dataDomain = (String)crsRiskRelationRow.getAttribute("DataDomain");
        vo.setbindSafetyInterestTopic(safetyTopicOfInterest);
        vo.setbindDomainName(dataDomain);
        vo.executeQuery();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    while (vo.hasNext()) { 
    CrsExportPTCurrentVORowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.next();
            } else {
                viewObjectRow = (CrsExportPTCurrentVORowImpl) vo.first();
            }
    if(firstRow){
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("CRS Name: "+this.getSelectedCrsName());
        row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("CRS ID: "+this.getSelectedCrsId());
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("Status: "+this.getSelectedStatus());
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
            row.createCell(0).setCellValue("State: "+this.getSelectedState());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            row.createCell(2).setCellValue("GPSL: "+this.getSelectedBSL());
            row.getCell(2).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("HPS: "+this.getSelectedTASL());
            row.getCell(0).setCellStyle(colStyleTopLeft);
            idx = idx + 1;
            row = sheet.createRow(idx); //creating 1st row
            row.createCell(0).setCellValue("Designee: "+this.getSelectedDesignee());
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
                row.getCell(4).setCellStyle(colStyleTopLeft);
                row.createCell(5).setCellValue("Gender Code");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("Age");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);
    
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
        
        if (viewObjectRow.getGender() != null){
                    row.createCell(5).setCellValue(viewObjectRow.getGender());
                }
                else
                    row.createCell(5).setCellValue("");
                sheet.autoSizeColumn(5);
                
                if (viewObjectRow.getAge() != null){
                    row.createCell(6).setCellValue(viewObjectRow.getAge());
                }
                else
                    row.createCell(6).setCellValue("");
                sheet.autoSizeColumn(6);
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }
    
    public List exportPTPendingReport() {
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
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsExportPTPendingIterator");
        ViewObject vo = itrBinding.getViewObject();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    int i = 0;
    while (vo.hasNext()) { 
        i++;
    CrsExportPTPendingRowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CrsExportPTPendingRowImpl) vo.next();
            } else {
                viewObjectRow = (CrsExportPTPendingRowImpl) vo.first();
            }
    if(firstRow){
    row = sheet.createRow(idx); //creating 1st row
    row.createCell(0).setCellValue("CRS Name: "+this.getSelectedCrsName());
    row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("CRS ID: "+this.getSelectedCrsId());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("Status: "+this.getSelectedStatus());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        row.createCell(0).setCellValue("Downloaded Time: "+sdf.format(new Date()));
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("Release Status: Pending");
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("State: "+this.getSelectedState());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("GPSL: "+this.getSelectedBSL());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("HPS: "+this.getSelectedTASL());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("Designee: "+this.getSelectedDesignee());
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
                row.createCell(5).setCellValue("Gender Code");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("Age");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);
    
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
        
        if (viewObjectRow.getGender() != null){
            row.createCell(5).setCellValue(viewObjectRow.getGender());
        }
        else
            row.createCell(5).setCellValue("");
        sheet.autoSizeColumn(5);
        
        if (viewObjectRow.getAge() != null){
            row.createCell(6).setCellValue(viewObjectRow.getAge());
        }
        else
            row.createCell(6).setCellValue("");
        sheet.autoSizeColumn(6);
        
        
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }
    
    public List exportPTPendingReportDetail() {
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
        DCIteratorBinding itrBinding = bindings.findIteratorBinding("CrsExportPTPendingDetailIterator");
        CrsExportPTPendingImpl vo = (CrsExportPTPendingImpl)itrBinding.getViewObject();
        
        DCIteratorBinding itrBinding1 = bindings.findIteratorBinding("CrsRiskVOIterator");
        ViewObject vo1 = itrBinding1.getViewObject();
        Row crsRiskRelationRow = vo1.getCurrentRow();
        String safetyTopicOfInterest = (String)crsRiskRelationRow.getAttribute("SafetyTopicOfInterest");
        String dataDomain = (String)crsRiskRelationRow.getAttribute("DataDomain");
        vo.setbindSafetyInterestTopic(safetyTopicOfInterest);
        vo.setbindDomainName(dataDomain);
        vo.executeQuery();
        vo.reset();
    Boolean flag = false;
    Boolean firstRow = true;
    while (vo.hasNext()) { 
    CrsExportPTPendingRowImpl viewObjectRow;
            if (!firstRow) {
                viewObjectRow = (CrsExportPTPendingRowImpl) vo.next();
            } else {
                viewObjectRow = (CrsExportPTPendingRowImpl) vo.first();
            }
    if(firstRow){
    row = sheet.createRow(idx); //creating 1st row
    row.createCell(0).setCellValue("CRS Name: "+this.getSelectedCrsName());
    row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("CRS ID: "+this.getSelectedCrsId());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("Dictionary Version: "+ADFUtils.evaluateEL("#{sessionScope.dictVersion}").toString());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("Status: "+this.getSelectedStatus());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        row.createCell(0).setCellValue("Downloaded Time: "+sdf.format(new Date()));
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("Release Status: Pending");
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("State: "+this.getSelectedState());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        row.createCell(2).setCellValue("GPSL: "+this.getSelectedBSL());
        row.getCell(2).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("HPS: "+this.getSelectedTASL());
        row.getCell(0).setCellStyle(colStyleTopLeft);
        idx = idx + 1;
        row = sheet.createRow(idx); //creating 1st row
        row.createCell(0).setCellValue("Designee: "+this.getSelectedDesignee());
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
                row.createCell(5).setCellValue("Gender Code");
                sheet.autoSizeColumn(5);
                row.getCell(5).setCellStyle(colStyleTopLeft);
                row.createCell(6).setCellValue("Age");
                sheet.autoSizeColumn(6);
                row.getCell(6).setCellStyle(colStyleTopLeft);
    
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
        
        if (viewObjectRow.getGender() != null){
            row.createCell(5).setCellValue(viewObjectRow.getGender());
        }
        else
            row.createCell(5).setCellValue("");
        sheet.autoSizeColumn(5);
        
        if (viewObjectRow.getAge() != null){
            row.createCell(6).setCellValue(viewObjectRow.getAge());
        }
        else
            row.createCell(6).setCellValue("");
        sheet.autoSizeColumn(6);
        
        
        
    //2nd Row ends
    flag = true; 
    idx = idx + 1;
    }
    List list = new ArrayList();
    list.add(wb);
    return list;
    }

    public void closeDownloadPTPendingPopup(ActionEvent actionEvent) {
        this.getPtExportPendingPopup().hide();
    }

    public void setPtExportPendingPopup(RichPopup ptExportPendingPopup) {
        this.ptExportPendingPopup = ptExportPendingPopup;
    }

    public RichPopup getPtExportPendingPopup() {
        return ptExportPendingPopup;
    }

    public void setPtExportPendingDetailPopup(RichPopup ptExportPendingDetailPopup) {
        this.ptExportPendingDetailPopup = ptExportPendingDetailPopup;
    }

    public RichPopup getPtExportPendingDetailPopup() {
        return ptExportPendingDetailPopup;
    }

    public void exportPTPendingDetailReport(FacesContext facesContext, OutputStream outputStream) {
        // Add event code here...

    }

    public void closeDownloadPTPendingDetailPopup(ActionEvent actionEvent) {
        this.getPtExportPendingDetailPopup().hide();
    }

    public void setShowADR(Boolean showADR) {
        this.showADR = showADR;
    }

    public Boolean getShowADR() {
        List<String> riskPursposeList = this.getSelRiskPurposes();
        if((riskPursposeList != null) && (riskPursposeList.contains("CD"))){
            return true;
        }else{
            return false;
        }
        //return showADR;
    }

    public void setShowMedicalHistory(Boolean showMedicalHistory) {
        this.showMedicalHistory = showMedicalHistory;
    }

    public Boolean getShowMedicalHistory() {
        List<String> riskPursposeList = this.getSelRiskPurposes();
        if((riskPursposeList == null) || (riskPursposeList.contains("CD") || riskPursposeList.contains("A1") || riskPursposeList.contains("A2") || riskPursposeList.contains("UD") || riskPursposeList.contains("ER")) ){
            Boolean isCancelClicked =  (Boolean)ADFUtils.getPageFlowScopeValue("isCancelClicked");
            if(!((isCancelClicked != null) && isCancelClicked)){
            ADFUtils.setEL("#{bindings.SearchAppliedTo.inputValue}", "Adverse Event");
            }
            ADFUtils.setPageFlowScopeValue("isCancelClicked",false);
            return false;
        }else{
            return true;
        }
       // return showMedicalHistory;
    }

    public void setShowCopyADR(Boolean showCopyADR) {
        this.showCopyADR = showCopyADR;
    }

    public Boolean getShowCopyADR() {
        List<String> riskPursposeList = this.getSelRiskPurposes();
        if((riskPursposeList != null) && (riskPursposeList.contains("CD"))){
            return true;
        }else{
            return false;
        }
        //return showCopyADR;
    }

    public void setShowCopyMedicalHistory(Boolean showCopyMedicalHistory) {
        this.showCopyMedicalHistory = showCopyMedicalHistory;
    }

    public Boolean getShowCopyMedicalHistory() {
        List<String> riskPursposeList = this.getSelRiskPurposes();
        if((riskPursposeList == null) || (riskPursposeList.contains("CD") || riskPursposeList.contains("A1") || riskPursposeList.contains("A2") || riskPursposeList.contains("UD") || riskPursposeList.contains("ER")) ){
            return false;
        }else{
            return true;
        }
        //return showCopyMedicalHistory;
    }

    public void setShowAdrTentative(Boolean showAdrTentative) {
        this.showAdrTentative = showAdrTentative;
    }

    public Boolean getShowAdrTentative() {
        if("BASE".equalsIgnoreCase(this.getBaseOrStaging())){
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsRiskBaseVOIterator");
            RowSetIterator rsi = riskIter.getViewObject().createRowSetIterator(null);
            rsi.reset();
            while (rsi.hasNext()) {
                     Row row = rsi.next();
                   String cd = (String)row.getAttribute("RiskPurposeCdFlag");
                if("T".equalsIgnoreCase(cd)){
                    rsi.closeRowSetIterator();
                    return true;
                }
                  }
            rsi.closeRowSetIterator();  
        }else{
            DCIteratorBinding riskIter = ADFUtils.findIterator("CrsRiskVOIterator");
            RowSetIterator rsi = riskIter.getViewObject().createRowSetIterator(null);
            rsi.reset();
            while (rsi.hasNext()) {
                     Row row = rsi.next();
                   String cd = (String)row.getAttribute("RiskPurposeCdFlag");
                if("T".equalsIgnoreCase(cd)){
                    rsi.closeRowSetIterator();
                    return true;
                }
                  }
            rsi.closeRowSetIterator();
        }   
        return false;
    }

    public void setDisableHeirarchyBtn(Boolean disableHeirarchyBtn) {
        this.disableHeirarchyBtn = disableHeirarchyBtn;
    }

    public Boolean getDisableHeirarchyBtn() {
        OperationBinding op = ADFUtils.findOperation("domainName");
        Map params = op.getParamsMap();
        params.put("domainId", ADFUtils.evaluateEL("#{bindings.DomainId.inputValue}"));
        String domainName = (String)op.execute();
        if("OTHER".equalsIgnoreCase(domainName)){
            return true;
        }else{
            return false;
        }
        //return disableHeirarchyBtn;
    }

    public void setCrsDomainValue(RichSelectOneChoice crsDomainValue) {
        this.crsDomainValue = crsDomainValue;
    }

    public RichSelectOneChoice getCrsDomainValue() {
        return crsDomainValue;
    }

    public void setRiskDefOtherSelectionPopup(RichPopup riskDefOtherSelectionPopup) {
        this.riskDefOtherSelectionPopup = riskDefOtherSelectionPopup;
    }

    public RichPopup getRiskDefOtherSelectionPopup() {
        return riskDefOtherSelectionPopup;
    }

    public void closeRiskDefOthersPopup(ActionEvent actionEvent) {
        this.getRiskDefOtherSelectionPopup().hide();
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Integer domainOldValue = (Integer)pageFlowScope.get("domainOldValue");
        this.getCrsDomainValue().setValue(domainOldValue);
        ADFUtils.setEL("#{bindings.DomainId.inputValue}",domainOldValue);
    }

    public void setCopyRiskDefOthersPopup(RichSelectOneChoice copyRiskDefOthersPopup) {
        this.copyRiskDefOthersPopup = copyRiskDefOthersPopup;
    }

    public RichSelectOneChoice getCopyRiskDefOthersPopup() {
        return copyRiskDefOthersPopup;
    }

    public void setCopyRiskDefOtherSelectionPopup(RichPopup copyRiskDefOtherSelectionPopup) {
        this.copyRiskDefOtherSelectionPopup = copyRiskDefOtherSelectionPopup;
    }

    public RichPopup getCopyRiskDefOtherSelectionPopup() {
        return copyRiskDefOtherSelectionPopup;
    }

    public void copyCloseRiskDefOthersPopup(ActionEvent actionEvent) {
        
        this.getCopyRiskDefOtherSelectionPopup().hide();
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Integer domainOldValue = (Integer)pageFlowScope.get("domainOldValue");
        this.getCrsDomainValue().setValue(domainOldValue);
        ADFUtils.setEL("#{bindings.DomainId.inputValue}",domainOldValue);
    }

    public void setAgeSubGroup(List<String> ageSubGroup) {
        this.ageSubGroup = ageSubGroup;
    }

    public List<String> getAgeSubGroup() {
        BigDecimal crsAgeGrpId = (BigDecimal)ADFUtils.evaluateEL("#{bindings.CrsAgeGrpId.inputValue}");
        if(new BigDecimal(2).equals(crsAgeGrpId)){
        if(ageSubGroup == null){
           String listString = (String)ADFUtils.evaluateEL("#{bindings.CrsAgeSubGrpId.inputValue}");
           if(listString != null && !"0".equalsIgnoreCase(listString)){
        String[] elements = listString.split(",");
        ageSubGroup = Arrays.asList(elements);
           }else{
               String[] elements = {"1","2","3","4"};
               ageSubGroup = Arrays.asList(elements); 
           }
        }
        }
        return ageSubGroup;
    }

    public void onSubAgeGroupChange(ValueChangeEvent valueChangeEvent) {
        List<String> list = (List<String>)valueChangeEvent.getNewValue();
//        if(list != null && list.size() > 3){
//            ADFUtils.showPopup(getMaxThreeAllowedPopup());
//        }else if(list != null && list.size() <= 3){
        if(list != null){
            String subAgeGroupsCommaSeparated = String.join(",", list);
            if(allowedSubGroup().contains(subAgeGroupsCommaSeparated)){
                ADFUtils.setEL("#{bindings.CrsAgeSubGrpId.inputValue}",subAgeGroupsCommaSeparated);
            }else{
                ADFUtils.showPopup(getCombinationNotAllowedPopup());
            }
        }
    }

    public void setAgeSubGroupComponent(RichSelectManyChoice ageSubGroupComponent) {
        this.ageSubGroupComponent = ageSubGroupComponent;
    }

    public RichSelectManyChoice getAgeSubGroupComponent() {
        return ageSubGroupComponent;
    }
    
    private List<String> allowedSubGroup(){
        List<String> allowedSubGroupList = new ArrayList<String>();
        allowedSubGroupList.add("1");
        allowedSubGroupList.add("2");
        allowedSubGroupList.add("3");
        allowedSubGroupList.add("4");
        allowedSubGroupList.add("1,2");
        allowedSubGroupList.add("2,3");
        allowedSubGroupList.add("3,4");
        allowedSubGroupList.add("1,2,3");
        allowedSubGroupList.add("2,3,4");
        allowedSubGroupList.add("1,2,3,4");
        
        return allowedSubGroupList;
    }

    public void onAgeGroupChange(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getOldValue() != null && valueChangeEvent.getOldValue().equals(new BigDecimal(2))){
            ADFUtils.showPopup(getAgeGroupChangePopup());
        }
    }

    public void setAgeGroupChangePopup(RichPopup ageGroupChangePopup) {
        this.ageGroupChangePopup = ageGroupChangePopup;
    }

    public RichPopup getAgeGroupChangePopup() {
        return ageGroupChangePopup;
    }

    public String agreeOnAgeGroupChange() {
        this.setAgeSubGroup(null);
        ADFUtils.setEL("#{bindings.CrsAgeSubGrpId.inputValue}",null);
        ADFUtils.addPartialTarget(getAgeSubGroupComponent());
        getAgeGroupChangePopup().hide();
        return null;
    }

    public void setCombinationNotAllowedPopup(RichPopup combinationNotAllowedPopup) {
        this.combinationNotAllowedPopup = combinationNotAllowedPopup;
    }

    public RichPopup getCombinationNotAllowedPopup() {
        return combinationNotAllowedPopup;
    }

    public String combinationNotAllowed() {
        this.setAgeSubGroup(null);
        ADFUtils.setEL("#{bindings.CrsAgeSubGrpId.inputValue}",null);
        getCombinationNotAllowedPopup().hide();
        ADFUtils.addPartialTarget(getAgeSubGroupComponent());
        return null;
    }

    public void setMaxThreeAllowedPopup(RichPopup maxThreeAllowedPopup) {
        this.maxThreeAllowedPopup = maxThreeAllowedPopup;
    }

    public RichPopup getMaxThreeAllowedPopup() {
        return maxThreeAllowedPopup;
    }

    public String maxThreeValuesAllowed() {
        this.setAgeSubGroup(null);
        ADFUtils.setEL("#{bindings.CrsAgeSubGrpId.inputValue}",null);
        getMaxThreeAllowedPopup().hide();
        ADFUtils.addPartialTarget(getAgeSubGroupComponent());
        return null;
    }

    public void setAgeSubCurrent(List<String> ageSubCurrent) {
        this.ageSubCurrent = ageSubCurrent;
    }

    public List<String> getAgeSubCurrent() {
        if(ageSubCurrent == null){
           String listString = (String)ADFUtils.evaluateEL("#{bindings.CrsAgeSubGrpId1.inputValue}");
           if(listString != null){
        String[] elements = listString.split(",");
        ageSubCurrent = Arrays.asList(elements);
           }
        }
        return ageSubCurrent;
    }
    
    public static void clearFilterCriteria(RichTable targetTable, String iterator) {
    targetTable.queueEvent(new SortEvent(targetTable,new ArrayList<SortCriterion>()));
    SortCriteria[] sc = new SortCriteria[0];
    //Clears the Sort Criteria        
    ADFUtils.findIterator(iterator).applySortCriteria(sc);
    FilterableQueryDescriptor queryDescriptor = (FilterableQueryDescriptor)targetTable.getFilterModel();  
    if (queryDescriptor != null && queryDescriptor.getFilterCriteria() != null) {           
        // Clears the Filter Criteria            
        queryDescriptor.getFilterCriteria().clear();}
    }

    public void cancelExitAddRiskDefPage(ActionEvent actionEvent) {
        clearFilterCriteria(this.getStagingTable(), "CrsRiskVOIterator");
        clearFilterCriteria(this.getCrsRiskBaseTable(), "CrsRiskBaseVOIterator");
        OperationBinding oper = ADFUtils.findOperation("Rollback");
        oper.execute();
        if (oper.getErrors().size() > 0)
            ADFUtils.showFacesMessage("An internal error has occured. Please try later.", FacesMessage.SEVERITY_ERROR);
    }

    public void setCrsRiskBaseTable(RichTable crsRiskBaseTable) {
        this.crsRiskBaseTable = crsRiskBaseTable;
    }

    public RichTable getCrsRiskBaseTable() {
        return crsRiskBaseTable;
    }
    
    public void prepareForDownloadAction(ActionEvent act) {
        Long crsId = new Long(this.getSelectedCrsId());
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
             logger.info("--outputFolder Logger--"+outputFolder);
             System.out.println("--outputFolder SOP--"+outputFolder);
             try {
                     ADFUtils.setPageFlowScopeValue("CrsId", crsId);
                     outputFolder = outputFolder.concat("\\PtExport\\"+crsId+".xls");
                     logger.info("--outputFolder1 Logger--"+outputFolder);
                     System.out.println("--outputFolder1 SOP--"+outputFolder);
                     File file = new File(outputFolder);
                     inputStream = new FileInputStream(file);
                 
                     FacesContext context = FacesContext.getCurrentInstance();
                            ExtendedRenderKitService erks = Service.getService(context.getRenderKit(),ExtendedRenderKitService.class);
                            erks.addScript(context, "customHandler1();");
                 
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
    
    public void downloadPtReport(FacesContext facesContext,
                                         OutputStream outputStream) throws IOException {
        Long crsId = new Long(this.getSelectedCrsId());
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
                    ADFUtils.setPageFlowScopeValue("CrsId", crsId);
                    outputFolder = outputFolder.concat("\\PtExport\\"+crsId+".xls");
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
}
