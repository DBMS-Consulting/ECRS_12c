package com.novartis.ecrs.model.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Oct 22 02:03:36 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsStateVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CrsStateVOImpl() {
    }

    /**
     * Returns the variable value for bindStateId.
     * @return variable value for bindStateId
     */
    public Integer getbindStateId() {
        return (Integer) ensureVariableManager().getVariableValue("bindStateId");
    }

    /**
     * Sets <code>value</code> for variable bindStateId.
     * @param value value to bind as bindStateId
     */
    public void setbindStateId(Integer value) {
        ensureVariableManager().setVariableValue("bindStateId", value);
    }

    /**
     * Returns the variable value for bindStateName.
     * @return variable value for bindStateName
     */
    public String getbindStateName() {
        return (String) ensureVariableManager().getVariableValue("bindStateName");
    }

    /**
     * Sets <code>value</code> for variable bindStateName.
     * @param value value to bind as bindStateName
     */
    public void setbindStateName(String value) {
        ensureVariableManager().setVariableValue("bindStateName", value);
    }
}

