package com.novartis.ecrs.model.view;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Jul 11 00:17:25 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class RelationCountVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public RelationCountVOImpl() {
    }

    /**
     * Returns the bind variable value for bindCrsId.
     * @return bind variable value for bindCrsId
     */
    public String getbindCrsId() {
        return (String) getNamedWhereClauseParam("bindCrsId");
    }

    /**
     * Sets <code>value</code> for bind variable bindCrsId.
     * @param value value to bind as bindCrsId
     */
    public void setbindCrsId(String value) {
        setNamedWhereClauseParam("bindCrsId", value);
    }

    /**
     * Returns the bind variable value for bindDomain.
     * @return bind variable value for bindDomain
     */
    public String getbindDomain() {
        return (String) getNamedWhereClauseParam("bindDomain");
    }

    /**
     * Sets <code>value</code> for bind variable bindDomain.
     * @param value value to bind as bindDomain
     */
    public void setbindDomain(String value) {
        setNamedWhereClauseParam("bindDomain", value);
    }

    /**
     * Returns the bind variable value for bindSafetyTopic.
     * @return bind variable value for bindSafetyTopic
     */
    public String getbindSafetyTopic() {
        return (String) getNamedWhereClauseParam("bindSafetyTopic");
    }

    /**
     * Sets <code>value</code> for bind variable bindSafetyTopic.
     * @param value value to bind as bindSafetyTopic
     */
    public void setbindSafetyTopic(String value) {
        setNamedWhereClauseParam("bindSafetyTopic", value);
    }
}

