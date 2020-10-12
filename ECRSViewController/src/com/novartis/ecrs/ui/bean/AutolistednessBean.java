package com.novartis.ecrs.ui.bean;

import com.novartis.ecrs.ui.utility.ADFUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;

public class AutolistednessBean {
    public AutolistednessBean() {
        super();
    }
    
    public String onSave() {
        String output = null;
            try {
               output = (String) ADFUtils.executeAction("updateAutolistedness", null);
            } catch (Exception e) {
                e.printStackTrace();
            } 
       // ADFUtils.invokeEL("#{bindings.Commit.execute}");
        if("Y".equalsIgnoreCase(output))
        ADFUtils.showFacesMessage("Saved Successfully.", FacesMessage.SEVERITY_INFO);
        else
        ADFUtils.showFacesMessage("Details not saved due to some internal error.", FacesMessage.SEVERITY_ERROR);
        return null;
    }
    
    public String onClickBack() {
        ADFUtils.invokeEL("#{bindings.Rollback.execute}");
        String returnValue = (String)ADFUtils.invokeEL("#{controllerContext.currentViewPort.taskFlowContext.trainModel.getPrevious}");
        return returnValue;
    }
}
