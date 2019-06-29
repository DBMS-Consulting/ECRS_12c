package com.novartis.ecrs.model.view;

import java.math.BigDecimal;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Oct 20 01:20:51 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsExportPTPendingImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CrsExportPTPendingImpl() {
    }

    /**
     * Returns the variable value for bindCrsID.
     * @return variable value for bindCrsID
     */
    public BigDecimal getbindCrsID() {
        return (BigDecimal) ensureVariableManager().getVariableValue("bindCrsID");
    }

    /**
     * Sets <code>value</code> for variable bindCrsID.
     * @param value value to bind as bindCrsID
     */
    public void setbindCrsID(BigDecimal value) {
        ensureVariableManager().setVariableValue("bindCrsID", value);
    }

    /**
     * Returns the variable value for bindSafetyInterestTopic.
     * @return variable value for bindSafetyInterestTopic
     */
    public String getbindSafetyInterestTopic() {
        return (String) ensureVariableManager().getVariableValue("bindSafetyInterestTopic");
    }

    /**
     * Sets <code>value</code> for variable bindSafetyInterestTopic.
     * @param value value to bind as bindSafetyInterestTopic
     */
    public void setbindSafetyInterestTopic(String value) {
        ensureVariableManager().setVariableValue("bindSafetyInterestTopic", value);
    }

    /**
     * Returns the variable value for bindDomainName.
     * @return variable value for bindDomainName
     */
    public String getbindDomainName() {
        return (String) ensureVariableManager().getVariableValue("bindDomainName");
    }

    /**
     * Sets <code>value</code> for variable bindDomainName.
     * @param value value to bind as bindDomainName
     */
    public void setbindDomainName(String value) {
        ensureVariableManager().setVariableValue("bindDomainName", value);
    }
}

