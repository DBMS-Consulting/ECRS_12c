package com.novartis.ecrs.model.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 09 22:28:57 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsContentVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public CrsContentVOImpl() {
    }

    /**
     * Returns the variable value for pCrsId.
     * @return variable value for pCrsId
     */
    public Long getpCrsId() {
        return (Long) ensureVariableManager().getVariableValue("pCrsId");
    }

    /**
     * Sets <code>value</code> for variable pCrsId.
     * @param value value to bind as pCrsId
     */
    public void setpCrsId(Long value) {
        ensureVariableManager().setVariableValue("pCrsId", value);
    }
}
