package com.novartis.ecrs.model.view.report;

import java.math.BigDecimal;
import java.math.BigInteger;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Oct 21 12:13:43 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class PTReportVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public PTReportVOImpl() {
    }

    /**
     * Returns the bind variable value for pCRSStatus.
     * @return bind variable value for pCRSStatus
     */
    public String getpCRSStatus() {
        return (String)getNamedWhereClauseParam("pCRSStatus");
    }

    /**
     * Sets <code>value</code> for bind variable pCRSStatus.
     * @param value value to bind as pCRSStatus
     */
    public void setpCRSStatus(String value) {
        setNamedWhereClauseParam("pCRSStatus", value);
    }

    /**
     * Returns the bind variable value for pCrsId.
     * @return bind variable value for pCrsId
     */
    public BigDecimal getpCrsId() {
        return (BigDecimal)getNamedWhereClauseParam("pCrsId");
    }

    /**
     * Sets <code>value</code> for bind variable pCrsId.
     * @param value value to bind as pCrsId
     */
    public void setpCrsId(BigDecimal value) {
        setNamedWhereClauseParam("pCrsId", value);
    }

    /**
     * Returns the bind variable value for pRelScope.
     * @return bind variable value for pRelScope
     */
    public String getpRelScope() {
        return (String) getNamedWhereClauseParam("pRelScope");
    }

    /**
     * Sets <code>value</code> for bind variable pRelScope.
     * @param value value to bind as pRelScope
     */
    public void setpRelScope(String value) {
        setNamedWhereClauseParam("pRelScope", value);
    }
}
