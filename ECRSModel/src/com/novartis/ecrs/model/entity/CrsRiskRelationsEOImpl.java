package com.novartis.ecrs.model.entity;

import oracle.jbo.Key;
import oracle.jbo.domain.Timestamp;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 16 09:15:27 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsRiskRelationsEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CrsRiskId {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCrsRiskId();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setCrsRiskId((Long) value);
            }
        }
        ,
        SafetyTopicOfInterest {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSafetyTopicOfInterest();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSafetyTopicOfInterest((String) value);
            }
        }
        ,
        RiskPurposeSpFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeSpFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeSpFlag((String) value);
            }
        }
        ,
        RiskPurposeDsFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeDsFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeDsFlag((String) value);
            }
        }
        ,
        RiskPurposeRmFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeRmFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeRmFlag((String) value);
            }
        }
        ,
        RiskPurposePsFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposePsFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposePsFlag((String) value);
            }
        }
        ,
        RiskPurposeCdFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeCdFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeCdFlag((String) value);
            }
        }
        ,
        RiskPurposeIbFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeIbFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeIbFlag((String) value);
            }
        }
        ,
        RiskPurposeOsFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeOsFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeOsFlag((String) value);
            }
        }
        ,
        RiskPurposeMiFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeMiFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeMiFlag((String) value);
            }
        }
        ,
        RiskPurposeErFlag {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeErFlag();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeErFlag((String) value);
            }
        }
        ,
        CrsId {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCrsId();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setCrsId((Long) value);
            }
        }
        ,
        DatabaseId {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getDatabaseId();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setDatabaseId((Integer) value);
            }
        }
        ,
        DataDomain {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getDataDomain();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setDataDomain((String) value);
            }
        }
        ,
        NonMeddraComponentComment {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getNonMeddraComponentComment();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setNonMeddraComponentComment((String) value);
            }
        }
        ,
        MqmComment {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getMqmComment();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setMqmComment((String) value);
            }
        }
        ,
        SocTerm {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSocTerm();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSocTerm((String) value);
            }
        }
        ,
        SocDictContentId {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSocDictContentId();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSocDictContentId((Long) value);
            }
        }
        ,
        SocDictContentEntryTs {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSocDictContentEntryTs();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSocDictContentEntryTs((Timestamp) value);
            }
        }
        ,
        RiskPurposeList {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getRiskPurposeList();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setRiskPurposeList((String) value);
            }
        }
        ,
        DatabaseList {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getDatabaseList();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setDatabaseList((String) value);
            }
        }
        ,
        UiVersionNumber {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getUiVersionNumber();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsEffectiveDt {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCrsEffectiveDt();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DomainId {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getDomainId();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setDomainId((Integer) value);
            }
        }
        ,
        CreatedBy {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCreatedBy();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CreationTs {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCreationTs();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ModifiedBy {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getModifiedBy();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ModificationTs {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getModificationTs();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        SearchCriteriaDetails {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSearchCriteriaDetails();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSearchCriteriaDetails((String) value);
            }
        }
        ,
        SearchAppliedTo {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getSearchAppliedTo();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setSearchAppliedTo((String) value);
            }
        }
        ,
        Adr {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getAdr();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setAdr((String) value);
            }
        }
        ,
        CrsContentEO {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCrsContentEO();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setCrsContentEO((CrsContentEOImpl) value);
            }
        }
        ,
        CrsDomainsEO {
            public Object get(CrsRiskRelationsEOImpl obj) {
                return obj.getCrsDomainsEO();
            }

            public void put(CrsRiskRelationsEOImpl obj, Object value) {
                obj.setCrsDomainsEO((EntityImpl)value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CrsRiskRelationsEOImpl object);

        public abstract void put(CrsRiskRelationsEOImpl object, Object value);

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


    public static final int CRSRISKID = AttributesEnum.CrsRiskId.index();
    public static final int SAFETYTOPICOFINTEREST = AttributesEnum.SafetyTopicOfInterest.index();
    public static final int RISKPURPOSESPFLAG = AttributesEnum.RiskPurposeSpFlag.index();
    public static final int RISKPURPOSEDSFLAG = AttributesEnum.RiskPurposeDsFlag.index();
    public static final int RISKPURPOSERMFLAG = AttributesEnum.RiskPurposeRmFlag.index();
    public static final int RISKPURPOSEPSFLAG = AttributesEnum.RiskPurposePsFlag.index();
    public static final int RISKPURPOSECDFLAG = AttributesEnum.RiskPurposeCdFlag.index();
    public static final int RISKPURPOSEIBFLAG = AttributesEnum.RiskPurposeIbFlag.index();
    public static final int RISKPURPOSEOSFLAG = AttributesEnum.RiskPurposeOsFlag.index();
    public static final int RISKPURPOSEMIFLAG = AttributesEnum.RiskPurposeMiFlag.index();
    public static final int RISKPURPOSEERFLAG = AttributesEnum.RiskPurposeErFlag.index();
    public static final int CRSID = AttributesEnum.CrsId.index();
    public static final int DATABASEID = AttributesEnum.DatabaseId.index();
    public static final int DATADOMAIN = AttributesEnum.DataDomain.index();
    public static final int NONMEDDRACOMPONENTCOMMENT = AttributesEnum.NonMeddraComponentComment.index();
    public static final int MQMCOMMENT = AttributesEnum.MqmComment.index();
    public static final int SOCTERM = AttributesEnum.SocTerm.index();
    public static final int SOCDICTCONTENTID = AttributesEnum.SocDictContentId.index();
    public static final int SOCDICTCONTENTENTRYTS = AttributesEnum.SocDictContentEntryTs.index();
    public static final int RISKPURPOSELIST = AttributesEnum.RiskPurposeList.index();
    public static final int DATABASELIST = AttributesEnum.DatabaseList.index();
    public static final int UIVERSIONNUMBER = AttributesEnum.UiVersionNumber.index();
    public static final int CRSEFFECTIVEDT = AttributesEnum.CrsEffectiveDt.index();
    public static final int DOMAINID = AttributesEnum.DomainId.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONTS = AttributesEnum.CreationTs.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFICATIONTS = AttributesEnum.ModificationTs.index();
    public static final int SEARCHCRITERIADETAILS = AttributesEnum.SearchCriteriaDetails.index();
    public static final int SEARCHAPPLIEDTO = AttributesEnum.SearchAppliedTo.index();
    public static final int ADR = AttributesEnum.Adr.index();
    public static final int CRSCONTENTEO = AttributesEnum.CrsContentEO.index();
    public static final int CRSDOMAINSEO = AttributesEnum.CrsDomainsEO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsRiskRelationsEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.novartis.ecrs.model.entity.CrsRiskRelationsEO");
    }

    /**
     * Gets the attribute value for CrsRiskId, using the alias name CrsRiskId.
     * @return the value of CrsRiskId
     */
    public Long getCrsRiskId() {
        return (Long)getAttributeInternal(CRSRISKID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CrsRiskId.
     * @param value value to set the CrsRiskId
     */
    public void setCrsRiskId(Long value) {
        setAttributeInternal(CRSRISKID, value);
    }

    /**
     * Gets the attribute value for SafetyTopicOfInterest, using the alias name SafetyTopicOfInterest.
     * @return the value of SafetyTopicOfInterest
     */
    public String getSafetyTopicOfInterest() {
        return (String)getAttributeInternal(SAFETYTOPICOFINTEREST);
    }

    /**
     * Sets <code>value</code> as the attribute value for SafetyTopicOfInterest.
     * @param value value to set the SafetyTopicOfInterest
     */
    public void setSafetyTopicOfInterest(String value) {
        setAttributeInternal(SAFETYTOPICOFINTEREST, value);
    }

    /**
     * Gets the attribute value for RiskPurposeSpFlag, using the alias name RiskPurposeSpFlag.
     * @return the value of RiskPurposeSpFlag
     */
    public String getRiskPurposeSpFlag() {
        return (String)getAttributeInternal(RISKPURPOSESPFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeSpFlag.
     * @param value value to set the RiskPurposeSpFlag
     */
    public void setRiskPurposeSpFlag(String value) {
        setAttributeInternal(RISKPURPOSESPFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeDsFlag, using the alias name RiskPurposeDsFlag.
     * @return the value of RiskPurposeDsFlag
     */
    public String getRiskPurposeDsFlag() {
        return (String)getAttributeInternal(RISKPURPOSEDSFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeDsFlag.
     * @param value value to set the RiskPurposeDsFlag
     */
    public void setRiskPurposeDsFlag(String value) {
        setAttributeInternal(RISKPURPOSEDSFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeRmFlag, using the alias name RiskPurposeRmFlag.
     * @return the value of RiskPurposeRmFlag
     */
    public String getRiskPurposeRmFlag() {
        return (String)getAttributeInternal(RISKPURPOSERMFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeRmFlag.
     * @param value value to set the RiskPurposeRmFlag
     */
    public void setRiskPurposeRmFlag(String value) {
        setAttributeInternal(RISKPURPOSERMFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposePsFlag, using the alias name RiskPurposePsFlag.
     * @return the value of RiskPurposePsFlag
     */
    public String getRiskPurposePsFlag() {
        return (String)getAttributeInternal(RISKPURPOSEPSFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposePsFlag.
     * @param value value to set the RiskPurposePsFlag
     */
    public void setRiskPurposePsFlag(String value) {
        setAttributeInternal(RISKPURPOSEPSFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeCdFlag, using the alias name RiskPurposeCdFlag.
     * @return the value of RiskPurposeCdFlag
     */
    public String getRiskPurposeCdFlag() {
        return (String)getAttributeInternal(RISKPURPOSECDFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeCdFlag.
     * @param value value to set the RiskPurposeCdFlag
     */
    public void setRiskPurposeCdFlag(String value) {
        setAttributeInternal(RISKPURPOSECDFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeIbFlag, using the alias name RiskPurposeIbFlag.
     * @return the value of RiskPurposeIbFlag
     */
    public String getRiskPurposeIbFlag() {
        return (String)getAttributeInternal(RISKPURPOSEIBFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeIbFlag.
     * @param value value to set the RiskPurposeIbFlag
     */
    public void setRiskPurposeIbFlag(String value) {
        setAttributeInternal(RISKPURPOSEIBFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeOsFlag, using the alias name RiskPurposeOsFlag.
     * @return the value of RiskPurposeOsFlag
     */
    public String getRiskPurposeOsFlag() {
        return (String)getAttributeInternal(RISKPURPOSEOSFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeOsFlag.
     * @param value value to set the RiskPurposeOsFlag
     */
    public void setRiskPurposeOsFlag(String value) {
        setAttributeInternal(RISKPURPOSEOSFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeMiFlag, using the alias name RiskPurposeMiFlag.
     * @return the value of RiskPurposeMiFlag
     */
    public String getRiskPurposeMiFlag() {
        return (String)getAttributeInternal(RISKPURPOSEMIFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeMiFlag.
     * @param value value to set the RiskPurposeMiFlag
     */
    public void setRiskPurposeMiFlag(String value) {
        setAttributeInternal(RISKPURPOSEMIFLAG, value);
    }

    /**
     * Gets the attribute value for RiskPurposeErFlag, using the alias name RiskPurposeErFlag.
     * @return the value of RiskPurposeErFlag
     */
    public String getRiskPurposeErFlag() {
        return (String)getAttributeInternal(RISKPURPOSEERFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeErFlag.
     * @param value value to set the RiskPurposeErFlag
     */
    public void setRiskPurposeErFlag(String value) {
        setAttributeInternal(RISKPURPOSEERFLAG, value);
    }

    /**
     * Gets the attribute value for CrsId, using the alias name CrsId.
     * @return the value of CrsId
     */
    public Long getCrsId() {
        return (Long)getAttributeInternal(CRSID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CrsId.
     * @param value value to set the CrsId
     */
    public void setCrsId(Long value) {
        setAttributeInternal(CRSID, value);
    }

    /**
     * Gets the attribute value for DatabaseId, using the alias name DatabaseId.
     * @return the value of DatabaseId
     */
    public Integer getDatabaseId() {
        return (Integer)getAttributeInternal(DATABASEID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DatabaseId.
     * @param value value to set the DatabaseId
     */
    public void setDatabaseId(Integer value) {
        setAttributeInternal(DATABASEID, value);
    }

    /**
     * Gets the attribute value for DataDomain, using the alias name DataDomain.
     * @return the value of DataDomain
     */
    public String getDataDomain() {
        return (String)getAttributeInternal(DATADOMAIN);
    }

    /**
     * Sets <code>value</code> as the attribute value for DataDomain.
     * @param value value to set the DataDomain
     */
    public void setDataDomain(String value) {
        setAttributeInternal(DATADOMAIN, value);
    }

    /**
     * Gets the attribute value for NonMeddraComponentComment, using the alias name NonMeddraComponentComment.
     * @return the value of NonMeddraComponentComment
     */
    public String getNonMeddraComponentComment() {
        return (String)getAttributeInternal(NONMEDDRACOMPONENTCOMMENT);
    }

    /**
     * Sets <code>value</code> as the attribute value for NonMeddraComponentComment.
     * @param value value to set the NonMeddraComponentComment
     */
    public void setNonMeddraComponentComment(String value) {
        setAttributeInternal(NONMEDDRACOMPONENTCOMMENT, value);
    }

    /**
     * Gets the attribute value for MqmComment, using the alias name MqmComment.
     * @return the value of MqmComment
     */
    public String getMqmComment() {
        return (String)getAttributeInternal(MQMCOMMENT);
    }

    /**
     * Sets <code>value</code> as the attribute value for MqmComment.
     * @param value value to set the MqmComment
     */
    public void setMqmComment(String value) {
        setAttributeInternal(MQMCOMMENT, value);
    }

    /**
     * Gets the attribute value for SocTerm, using the alias name SocTerm.
     * @return the value of SocTerm
     */
    public String getSocTerm() {
        return (String)getAttributeInternal(SOCTERM);
    }

    /**
     * Sets <code>value</code> as the attribute value for SocTerm.
     * @param value value to set the SocTerm
     */
    public void setSocTerm(String value) {
        setAttributeInternal(SOCTERM, value);
    }

    /**
     * Gets the attribute value for SocDictContentId, using the alias name SocDictContentId.
     * @return the value of SocDictContentId
     */
    public Long getSocDictContentId() {
        return (Long)getAttributeInternal(SOCDICTCONTENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for SocDictContentId.
     * @param value value to set the SocDictContentId
     */
    public void setSocDictContentId(Long value) {
        setAttributeInternal(SOCDICTCONTENTID, value);
    }

    /**
     * Gets the attribute value for SocDictContentEntryTs, using the alias name SocDictContentEntryTs.
     * @return the value of SocDictContentEntryTs
     */
    public Timestamp getSocDictContentEntryTs() {
        return (Timestamp)getAttributeInternal(SOCDICTCONTENTENTRYTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for SocDictContentEntryTs.
     * @param value value to set the SocDictContentEntryTs
     */
    public void setSocDictContentEntryTs(Timestamp value) {
        setAttributeInternal(SOCDICTCONTENTENTRYTS, value);
    }

    /**
     * Gets the attribute value for RiskPurposeList, using the alias name RiskPurposeList.
     * @return the value of RiskPurposeList
     */
    public String getRiskPurposeList() {
        return (String)getAttributeInternal(RISKPURPOSELIST);
    }

    /**
     * Sets <code>value</code> as the attribute value for RiskPurposeList.
     * @param value value to set the RiskPurposeList
     */
    public void setRiskPurposeList(String value) {
        setAttributeInternal(RISKPURPOSELIST, value);
    }

    /**
     * Gets the attribute value for DatabaseList, using the alias name DatabaseList.
     * @return the value of DatabaseList
     */
    public String getDatabaseList() {
        return (String)getAttributeInternal(DATABASELIST);
    }

    /**
     * Sets <code>value</code> as the attribute value for DatabaseList.
     * @param value value to set the DatabaseList
     */
    public void setDatabaseList(String value) {
        setAttributeInternal(DATABASELIST, value);
    }

    /**
     * Gets the attribute value for UiVersionNumber, using the alias name UiVersionNumber.
     * @return the value of UiVersionNumber
     */
    public Integer getUiVersionNumber() {
        return (Integer)getAttributeInternal(UIVERSIONNUMBER);
    }


    /**
     * Gets the attribute value for CrsEffectiveDt, using the alias name CrsEffectiveDt.
     * @return the value of CrsEffectiveDt
     */
    public Timestamp getCrsEffectiveDt() {
        return (Timestamp)getAttributeInternal(CRSEFFECTIVEDT);
    }


    /**
     * Gets the attribute value for DomainId, using the alias name DomainId.
     * @return the value of DomainId
     */
    public Integer getDomainId() {
        return (Integer)getAttributeInternal(DOMAINID);
    }

    /**
     * Sets <code>value</code> as the attribute value for DomainId.
     * @param value value to set the DomainId
     */
    public void setDomainId(Integer value) {
        setAttributeInternal(DOMAINID, value);
    }

    /**
     * Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     * @return the value of CreatedBy
     */
    public String getCreatedBy() {
        return (String)getAttributeInternal(CREATEDBY);
    }


    /**
     * Gets the attribute value for CreationTs, using the alias name CreationTs.
     * @return the value of CreationTs
     */
    public Timestamp getCreationTs() {
        return (Timestamp)getAttributeInternal(CREATIONTS);
    }


    /**
     * Gets the attribute value for ModifiedBy, using the alias name ModifiedBy.
     * @return the value of ModifiedBy
     */
    public String getModifiedBy() {
        return (String)getAttributeInternal(MODIFIEDBY);
    }


    /**
     * Gets the attribute value for ModificationTs, using the alias name ModificationTs.
     * @return the value of ModificationTs
     */
    public Timestamp getModificationTs() {
        return (Timestamp)getAttributeInternal(MODIFICATIONTS);
    }


    /**
     * Gets the attribute value for SearchCriteriaDetails, using the alias name SearchCriteriaDetails.
     * @return the value of SearchCriteriaDetails
     */
    public String getSearchCriteriaDetails() {
        return (String)getAttributeInternal(SEARCHCRITERIADETAILS);
    }

    /**
     * Sets <code>value</code> as the attribute value for SearchCriteriaDetails.
     * @param value value to set the SearchCriteriaDetails
     */
    public void setSearchCriteriaDetails(String value) {
        setAttributeInternal(SEARCHCRITERIADETAILS, value);
    }

    /**
     * Gets the attribute value for SearchAppliedTo, using the alias name SearchAppliedTo.
     * @return the value of SearchAppliedTo
     */
    public String getSearchAppliedTo() {
        return (String) getAttributeInternal(SEARCHAPPLIEDTO);
    }

    /**
     * Sets <code>value</code> as the attribute value for SearchAppliedTo.
     * @param value value to set the SearchAppliedTo
     */
    public void setSearchAppliedTo(String value) {
        setAttributeInternal(SEARCHAPPLIEDTO, value);
    }

    /**
     * Gets the attribute value for Adr, using the alias name Adr.
     * @return the value of Adr
     */
    public String getAdr() {
        return (String) getAttributeInternal(ADR);
    }

    /**
     * Sets <code>value</code> as the attribute value for Adr.
     * @param value value to set the Adr
     */
    public void setAdr(String value) {
        setAttributeInternal(ADR, value);
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

    /**
     * @return the associated entity CrsContentEOImpl.
     */
    public CrsContentEOImpl getCrsContentEO() {
        return (CrsContentEOImpl)getAttributeInternal(CRSCONTENTEO);
    }

    /**
     * Sets <code>value</code> as the associated entity CrsContentEOImpl.
     */
    public void setCrsContentEO(CrsContentEOImpl value) {
        setAttributeInternal(CRSCONTENTEO, value);
    }

    /**
     * @return the associated entity oracle.jbo.server.EntityImpl.
     */
    public EntityImpl getCrsDomainsEO() {
        return (EntityImpl)getAttributeInternal(CRSDOMAINSEO);
    }

    /**
     * Sets <code>value</code> as the associated entity oracle.jbo.server.EntityImpl.
     */
    public void setCrsDomainsEO(EntityImpl value) {
        setAttributeInternal(CRSDOMAINSEO, value);
    }

    /**
     * @param crsRiskId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Long crsRiskId) {
        return new Key(new Object[] { crsRiskId });
    }


}
