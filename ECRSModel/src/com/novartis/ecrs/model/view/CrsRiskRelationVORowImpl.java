package com.novartis.ecrs.model.view;

import com.novartis.ecrs.model.entity.CrsRiskRelationsEOImpl;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Aug 14 22:28:08 IST 2016
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsRiskRelationVORowImpl extends ViewRowImpl {
    public static final int ENTITY_CRSRISKRELATIONSEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CrsId {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCrsId();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setCrsId((Long)value);
            }
        }
        ,
        CrsRiskId {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCrsRiskId();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setCrsRiskId((Long)value);
            }
        }
        ,
        MqmComment {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getMqmComment();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setMqmComment((String)value);
            }
        }
        ,
        NonMeddraComponentComment {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getNonMeddraComponentComment();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setNonMeddraComponentComment((String)value);
            }
        }
        ,
        SafetyTopicOfInterest {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSafetyTopicOfInterest();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setSafetyTopicOfInterest((String)value);
            }
        }
        ,
        SocDictContentEntryTs {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSocDictContentEntryTs();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setSocDictContentEntryTs((Timestamp)value);
            }
        }
        ,
        SocDictContentId {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSocDictContentId();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setSocDictContentId((Long)value);
            }
        }
        ,
        SocTerm {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSocTerm();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setSocTerm((String)value);
            }
        }
        ,
        UiVersionNumber {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getUiVersionNumber();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setUiVersionNumber((Integer)value);
            }
        }
        ,
        RiskPurposeList {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getRiskPurposeList();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setRiskPurposeList((String)value);
            }
        }
        ,
        CrsEffectiveDt {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCrsEffectiveDt();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setCrsEffectiveDt((Timestamp)value);
            }
        }
        ,
        DomainId {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getDomainId();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setDomainId((Integer)value);
            }
        }
        ,
        CreatedBy {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setCreatedBy((String)value);
            }
        }
        ,
        CreationTs {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCreationTs();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setCreationTs((Timestamp)value);
            }
        }
        ,
        ModifiedBy {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setModifiedBy((String)value);
            }
        }
        ,
        ModificationTs {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getModificationTs();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setModificationTs((Timestamp)value);
            }
        }
        ,
        SearchCriteriaDetails {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSearchCriteriaDetails();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setSearchCriteriaDetails((String)value);
            }
        }
        ,
        CrsRiskDefinitionsVO {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getCrsRiskDefinitionsVO();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SocLOVO {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getSocLOVO();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DomainLOVVA {
            public Object get(CrsRiskRelationVORowImpl obj) {
                return obj.getDomainLOVVA();
            }

            public void put(CrsRiskRelationVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CrsRiskRelationVORowImpl object);

        public abstract void put(CrsRiskRelationVORowImpl object, Object value);

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int CRSID = AttributesEnum.CrsId.index();
    public static final int CRSRISKID = AttributesEnum.CrsRiskId.index();
    public static final int MQMCOMMENT = AttributesEnum.MqmComment.index();
    public static final int NONMEDDRACOMPONENTCOMMENT = AttributesEnum.NonMeddraComponentComment.index();
    public static final int SAFETYTOPICOFINTEREST = AttributesEnum.SafetyTopicOfInterest.index();
    public static final int SOCDICTCONTENTENTRYTS = AttributesEnum.SocDictContentEntryTs.index();
    public static final int SOCDICTCONTENTID = AttributesEnum.SocDictContentId.index();
    public static final int SOCTERM = AttributesEnum.SocTerm.index();
    public static final int UIVERSIONNUMBER = AttributesEnum.UiVersionNumber.index();
    public static final int RISKPURPOSELIST = AttributesEnum.RiskPurposeList.index();
    public static final int CRSEFFECTIVEDT = AttributesEnum.CrsEffectiveDt.index();
    public static final int DOMAINID = AttributesEnum.DomainId.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONTS = AttributesEnum.CreationTs.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFICATIONTS = AttributesEnum.ModificationTs.index();
    public static final int SEARCHCRITERIADETAILS = AttributesEnum.SearchCriteriaDetails.index();
    public static final int CRSRISKDEFINITIONSVO = AttributesEnum.CrsRiskDefinitionsVO.index();
    public static final int SOCLOVO = AttributesEnum.SocLOVO.index();
    public static final int DOMAINLOVVA = AttributesEnum.DomainLOVVA.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsRiskRelationVORowImpl() {
    }

    /**
     * Gets CrsRiskRelationsEO entity object.
     * @return the CrsRiskRelationsEO
     */
    public CrsRiskRelationsEOImpl getCrsRiskRelationsEO() {
        return (CrsRiskRelationsEOImpl)getEntity(ENTITY_CRSRISKRELATIONSEO);
    }

    /**
     * Gets the attribute value for CRS_ID using the alias name CrsId.
     * @return the CRS_ID
     */
    public Long getCrsId() {
        return (Long) getAttributeInternal(CRSID);
    }

    /**
     * Sets <code>value</code> as attribute value for CRS_ID using the alias name CrsId.
     * @param value value to set the CRS_ID
     */
    public void setCrsId(Long value) {
        setAttributeInternal(CRSID, value);
    }

    /**
     * Gets the attribute value for CRS_RISK_ID using the alias name CrsRiskId.
     * @return the CRS_RISK_ID
     */
    public Long getCrsRiskId() {
        return (Long) getAttributeInternal(CRSRISKID);
    }

    /**
     * Sets <code>value</code> as attribute value for CRS_RISK_ID using the alias name CrsRiskId.
     * @param value value to set the CRS_RISK_ID
     */
    public void setCrsRiskId(Long value) {
        setAttributeInternal(CRSRISKID, value);
    }

    /**
     * Gets the attribute value for MQM_COMMENT using the alias name MqmComment.
     * @return the MQM_COMMENT
     */
    public String getMqmComment() {
        return (String) getAttributeInternal(MQMCOMMENT);
    }

    /**
     * Sets <code>value</code> as attribute value for MQM_COMMENT using the alias name MqmComment.
     * @param value value to set the MQM_COMMENT
     */
    public void setMqmComment(String value) {
        setAttributeInternal(MQMCOMMENT, value);
    }

    /**
     * Gets the attribute value for NON_MEDDRA_COMPONENT_COMMENT using the alias name NonMeddraComponentComment.
     * @return the NON_MEDDRA_COMPONENT_COMMENT
     */
    public String getNonMeddraComponentComment() {
        return (String) getAttributeInternal(NONMEDDRACOMPONENTCOMMENT);
    }

    /**
     * Sets <code>value</code> as attribute value for NON_MEDDRA_COMPONENT_COMMENT using the alias name NonMeddraComponentComment.
     * @param value value to set the NON_MEDDRA_COMPONENT_COMMENT
     */
    public void setNonMeddraComponentComment(String value) {
        setAttributeInternal(NONMEDDRACOMPONENTCOMMENT, value);
    }

    /**
     * Gets the attribute value for SAFETY_TOPIC_OF_INTEREST using the alias name SafetyTopicOfInterest.
     * @return the SAFETY_TOPIC_OF_INTEREST
     */
    public String getSafetyTopicOfInterest() {
        return (String) getAttributeInternal(SAFETYTOPICOFINTEREST);
    }

    /**
     * Sets <code>value</code> as attribute value for SAFETY_TOPIC_OF_INTEREST using the alias name SafetyTopicOfInterest.
     * @param value value to set the SAFETY_TOPIC_OF_INTEREST
     */
    public void setSafetyTopicOfInterest(String value) {
        setAttributeInternal(SAFETYTOPICOFINTEREST, value);
    }

    /**
     * Gets the attribute value for SOC_DICT_CONTENT_ENTRY_TS using the alias name SocDictContentEntryTs.
     * @return the SOC_DICT_CONTENT_ENTRY_TS
     */
    public Timestamp getSocDictContentEntryTs() {
        return (Timestamp) getAttributeInternal(SOCDICTCONTENTENTRYTS);
    }

    /**
     * Sets <code>value</code> as attribute value for SOC_DICT_CONTENT_ENTRY_TS using the alias name SocDictContentEntryTs.
     * @param value value to set the SOC_DICT_CONTENT_ENTRY_TS
     */
    public void setSocDictContentEntryTs(Timestamp value) {
        setAttributeInternal(SOCDICTCONTENTENTRYTS, value);
    }

    /**
     * Gets the attribute value for SOC_DICT_CONTENT_ID using the alias name SocDictContentId.
     * @return the SOC_DICT_CONTENT_ID
     */
    public Long getSocDictContentId() {
        return (Long) getAttributeInternal(SOCDICTCONTENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for SOC_DICT_CONTENT_ID using the alias name SocDictContentId.
     * @param value value to set the SOC_DICT_CONTENT_ID
     */
    public void setSocDictContentId(Long value) {
        setAttributeInternal(SOCDICTCONTENTID, value);
    }

    /**
     * Gets the attribute value for SOC_TERM using the alias name SocTerm.
     * @return the SOC_TERM
     */
    public String getSocTerm() {
        return (String) getAttributeInternal(SOCTERM);
    }

    /**
     * Sets <code>value</code> as attribute value for SOC_TERM using the alias name SocTerm.
     * @param value value to set the SOC_TERM
     */
    public void setSocTerm(String value) {
        setAttributeInternal(SOCTERM, value);
    }

    /**
     * Gets the attribute value for UI_VERSION_NUMBER using the alias name UiVersionNumber.
     * @return the UI_VERSION_NUMBER
     */
    public Integer getUiVersionNumber() {
        return (Integer) getAttributeInternal(UIVERSIONNUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for UI_VERSION_NUMBER using the alias name UiVersionNumber.
     * @param value value to set the UI_VERSION_NUMBER
     */
    public void setUiVersionNumber(Integer value) {
        setAttributeInternal(UIVERSIONNUMBER, value);
    }

    /**
     * Gets the attribute value for RISK_PURPOSE_LIST using the alias name RiskPurposeList.
     * @return the RISK_PURPOSE_LIST
     */
    public String getRiskPurposeList() {
        return (String) getAttributeInternal(RISKPURPOSELIST);
    }

    /**
     * Sets <code>value</code> as attribute value for RISK_PURPOSE_LIST using the alias name RiskPurposeList.
     * @param value value to set the RISK_PURPOSE_LIST
     */
    public void setRiskPurposeList(String value) {
        setAttributeInternal(RISKPURPOSELIST, value);
    }

    /**
     * Gets the attribute value for CRS_EFFECTIVE_DT using the alias name CrsEffectiveDt.
     * @return the CRS_EFFECTIVE_DT
     */
    public Timestamp getCrsEffectiveDt() {
        return (Timestamp) getAttributeInternal(CRSEFFECTIVEDT);
    }

    /**
     * Sets <code>value</code> as attribute value for CRS_EFFECTIVE_DT using the alias name CrsEffectiveDt.
     * @param value value to set the CRS_EFFECTIVE_DT
     */
    public void setCrsEffectiveDt(Timestamp value) {
        setAttributeInternal(CRSEFFECTIVEDT, value);
    }

    /**
     * Gets the attribute value for DOMAIN_ID using the alias name DomainId.
     * @return the DOMAIN_ID
     */
    public Integer getDomainId() {
        return (Integer) getAttributeInternal(DOMAINID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOMAIN_ID using the alias name DomainId.
     * @param value value to set the DOMAIN_ID
     */
    public void setDomainId(Integer value) {
        setAttributeInternal(DOMAINID, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATION_TS using the alias name CreationTs.
     * @return the CREATION_TS
     */
    public Timestamp getCreationTs() {
        return (Timestamp) getAttributeInternal(CREATIONTS);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATION_TS using the alias name CreationTs.
     * @param value value to set the CREATION_TS
     */
    public void setCreationTs(Timestamp value) {
        setAttributeInternal(CREATIONTS, value);
    }

    /**
     * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @return the MODIFIED_BY
     */
    public String getModifiedBy() {
        return (String) getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @param value value to set the MODIFIED_BY
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for MODIFICATION_TS using the alias name ModificationTs.
     * @return the MODIFICATION_TS
     */
    public Timestamp getModificationTs() {
        return (Timestamp) getAttributeInternal(MODIFICATIONTS);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFICATION_TS using the alias name ModificationTs.
     * @param value value to set the MODIFICATION_TS
     */
    public void setModificationTs(Timestamp value) {
        setAttributeInternal(MODIFICATIONTS, value);
    }

    /**
     * Gets the attribute value for SEARCH_CRITERIA_DETAILS using the alias name SearchCriteriaDetails.
     * @return the SEARCH_CRITERIA_DETAILS
     */
    public String getSearchCriteriaDetails() {
        return (String) getAttributeInternal(SEARCHCRITERIADETAILS);
    }

    /**
     * Sets <code>value</code> as attribute value for SEARCH_CRITERIA_DETAILS using the alias name SearchCriteriaDetails.
     * @param value value to set the SEARCH_CRITERIA_DETAILS
     */
    public void setSearchCriteriaDetails(String value) {
        setAttributeInternal(SEARCHCRITERIADETAILS, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link CrsRiskDefinitionsVO.
     */
    public RowIterator getCrsRiskDefinitionsVO() {
        return (RowIterator)getAttributeInternal(CRSRISKDEFINITIONSVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> SocLOVO.
     */
    public RowSet getSocLOVO() {
        return (RowSet)getAttributeInternal(SOCLOVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> DomainLOVVA.
     */
    public RowSet getDomainLOVVA() {
        return (RowSet)getAttributeInternal(DOMAINLOVVA);
    }

    /**
     * getAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param attrDef the attribute

     * @return the attribute value
     * @throws Exception
     */
    protected Object getAttrInvokeAccessor(int index, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            return AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].get(this);
        }
        return super.getAttrInvokeAccessor(index, attrDef);
    }

    /**
     * setAttrInvokeAccessor: generated method. Do not modify.
     * @param index the index identifying the attribute
     * @param value the value to assign to the attribute
     * @param attrDef the attribute

     * @throws Exception
     */
    protected void setAttrInvokeAccessor(int index, Object value, AttributeDefImpl attrDef) throws Exception {
        if ((index >= AttributesEnum.firstIndex()) && (index < AttributesEnum.count())) {
            AttributesEnum.staticValues()[index - AttributesEnum.firstIndex()].put(this, value);
            return;
        }
        super.setAttrInvokeAccessor(index, value, attrDef);
    }
}