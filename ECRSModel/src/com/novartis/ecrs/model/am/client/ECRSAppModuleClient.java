package com.novartis.ecrs.model.am.client;

import com.novartis.ecrs.model.am.common.ECRSAppModule;

import java.util.List;

import oracle.jbo.client.remote.ApplicationModuleImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun May 24 11:31:34 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class ECRSAppModuleClient extends ApplicationModuleImpl implements ECRSAppModule {
    /**
     * This is the default constructor (do not remove).
     */
    public ECRSAppModuleClient() {
    }


    public String activateCrs(Long pCRSId, String pReasonForChange) {
        Object _ret =
            this.riInvokeExportedMethod(this,"activateCrs",new String [] {"java.lang.Long","java.lang.String"},new Object[] {pCRSId, pReasonForChange});
        return (String)_ret;
    }

    public void copyCurrentRiskRelation(Long srcRiskId, Long destCrsId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"copyCurrentRiskRelation",new String [] {"java.lang.Long","java.lang.Long"},new Object[] {srcRiskId, destCrsId});
        return;
    }

    public void copyRoutineDefinition(Long crsId) {
        Object _ret = this.riInvokeExportedMethod(this,"copyRoutineDefinition",new String [] {"java.lang.Long"},new Object[] {crsId});
        return;
    }

    public String deleteCrs(Long crsId) {
        Object _ret = this.riInvokeExportedMethod(this,"deleteCrs",new String [] {"java.lang.Long"},new Object[] {crsId});
        return (String)_ret;
    }

    public List fetchDatabases() {
        Object _ret = this.riInvokeExportedMethod(this,"fetchDatabases",null,null);
        return (List)_ret;
    }

    public List fetchDesignees() {
        Object _ret = this.riInvokeExportedMethod(this,"fetchDesignees",null,null);
        return (List)_ret;
    }

    public String fetchDictionaryVersion() {
        Object _ret = this.riInvokeExportedMethod(this,"fetchDictionaryVersion",null,null);
        return (String)_ret;
    }

    public Integer fetchDomainIdFromName(String domainName) {
        Object _ret = this.riInvokeExportedMethod(this,"fetchDomainIdFromName",new String [] {"java.lang.String"},new Object[] {domainName});
        return (Integer)_ret;
    }

    public void filterCRSContent(String userInRole, String userName, boolean isInboxDisable, String flowType) {
        Object _ret =
            this.riInvokeExportedMethod(this,"filterCRSContent",new String [] {"java.lang.String","java.lang.String","boolean","java.lang.String"},new Object[] {userInRole, userName, new Boolean(isInboxDisable), flowType});
        return;
    }

    public boolean findByCrsFromStg(Long pCrsId) {
        Object _ret = this.riInvokeExportedMethod(this,"findByCrsFromStg",new String [] {"java.lang.Long"},new Object[] {pCrsId});
        return ((Boolean)_ret).booleanValue();
    }

    public String getMedDRAFreezeFlag() {
        Object _ret = this.riInvokeExportedMethod(this,"getMedDRAFreezeFlag",null,null);
        return (String)_ret;
    }

    public void initRiskRelation(Long crsId, String status) {
        Object _ret =
            this.riInvokeExportedMethod(this,"initRiskRelation",new String [] {"java.lang.Long","java.lang.String"},new Object[] {crsId, status});
        return;
    }

    public Boolean isCRSVersionInitial(Long crsId) {
        Object _ret = this.riInvokeExportedMethod(this,"isCRSVersionInitial",new String [] {"java.lang.Long"},new Object[] {crsId});
        return (Boolean)_ret;
    }

    public Boolean isRiskRelationsExistForCRS(Long crsId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"isRiskRelationsExistForCRS",new String [] {"java.lang.Long"},new Object[] {crsId});
        return (Boolean)_ret;
    }

    public String modifyCrs(Long pCRSId, String pReasonForChange) {
        Object _ret =
            this.riInvokeExportedMethod(this,"modifyCrs",new String [] {"java.lang.Long","java.lang.String"},new Object[] {pCRSId, pReasonForChange});
        return (String)_ret;
    }

    public String reactivateCrs(Long pCRSId, String pReasonForChange) {
        Object _ret =
            this.riInvokeExportedMethod(this,"reactivateCrs",new String [] {"java.lang.Long","java.lang.String"},new Object[] {pCRSId, pReasonForChange});
        return (String)_ret;
    }

    public boolean refreshRepository(Long crsId) {
        Object _ret = this.riInvokeExportedMethod(this,"refreshRepository",new String [] {"java.lang.Long"},new Object[] {crsId});
        return ((Boolean)_ret).booleanValue();
    }

    public String retireCrs(Long pCRSId, String pReasonForChange) {
        Object _ret =
            this.riInvokeExportedMethod(this,"retireCrs",new String [] {"java.lang.Long","java.lang.String"},new Object[] {pCRSId, pReasonForChange});
        return (String)_ret;
    }

    public String updateMedDRAFreezeFlag(String freezeFlag) {
        Object _ret = this.riInvokeExportedMethod(this,"updateMedDRAFreezeFlag",new String [] {"java.lang.String"},new Object[] {freezeFlag});
        return (String)_ret;
    }

    public Boolean validateSafetyTopic(Long crsId, String safetyTopic, String rpList, Long crsRiskId,
                                       Integer domainId) {
        Object _ret =
            this.riInvokeExportedMethod(this,"validateSafetyTopic",new String [] {"java.lang.Long","java.lang.String","java.lang.String","java.lang.Long","java.lang.Integer"},new Object[] {crsId, safetyTopic, rpList, crsRiskId, domainId});
        return (Boolean)_ret;
    }
}
