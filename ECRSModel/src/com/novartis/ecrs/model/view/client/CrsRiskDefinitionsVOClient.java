package com.novartis.ecrs.model.view.client;

import com.novartis.ecrs.model.view.common.CrsRiskDefinitionsVO;

import oracle.jbo.client.remote.ViewUsageImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Aug 19 14:07:38 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsRiskDefinitionsVOClient extends ViewUsageImpl implements CrsRiskDefinitionsVO {
    /**
     * This is the default constructor (do not remove).
     */
    public CrsRiskDefinitionsVOClient() {
    }

    public void undoDelete() {
        Object _ret = getApplicationModuleProxy().riInvokeExportedMethod(this,"undoDelete",null,null);
        return;
    }
}
