package com.novartis.ecrs.model.view.base;

import java.sql.Timestamp;

import oracle.jbo.RowIterator;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 17 23:50:42 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsContentBaseVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CrsId {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsId();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        StateId {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getStateId();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CompoundId {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCompoundId();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        GenericName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getGenericName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TradeName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getTradeName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Indication {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getIndication();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        IsMarketedFlag {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getIsMarketedFlag();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ReleaseStatusFlag {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getReleaseStatusFlag();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BslName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getBslName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        Designee {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getDesignee();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MqmComment {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getMqmComment();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TaslName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getTaslName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TaslRejectComment {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getTaslRejectComment();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MedicalLeadName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getMedicalLeadName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MedicalLeadRejectComment {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getMedicalLeadRejectComment();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsEffectiveDt {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsEffectiveDt();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ReviewApproveRequiredFlag {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getReviewApproveRequiredFlag();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UiVersionNumber {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getUiVersionNumber();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsCompoundType {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsCompoundType();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsCompoundCode {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsCompoundCode();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        StateName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getStateName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DesigneeName {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getDesigneeName();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LastSyncDate {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getLastSyncDate();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsCurrentPublished {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsCurrentPublished();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CreationTs {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCreationTs();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        ModificationTs {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getModificationTs();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        VersionsVO {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getVersionsVO();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CRSVersionCompareVO {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCRSVersionCompareVO();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsExportPTCurrentVO {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getCrsExportPTCurrentVO();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        BSLUserVA {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getBSLUserVA();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        TASLUserVA {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getTASLUserVA();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        MLUserVA {
            protected Object get(CrsContentBaseVORowImpl obj) {
                return obj.getMLUserVA();
            }

            protected void put(CrsContentBaseVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(CrsContentBaseVORowImpl object);

        protected abstract void put(CrsContentBaseVORowImpl object, Object value);

        protected int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        protected static final int firstIndex() {
            return firstIndex;
        }

        protected static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        protected static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CRSID = AttributesEnum.CrsId.index();
    public static final int CRSNAME = AttributesEnum.CrsName.index();
    public static final int STATEID = AttributesEnum.StateId.index();
    public static final int COMPOUNDID = AttributesEnum.CompoundId.index();
    public static final int GENERICNAME = AttributesEnum.GenericName.index();
    public static final int TRADENAME = AttributesEnum.TradeName.index();
    public static final int INDICATION = AttributesEnum.Indication.index();
    public static final int ISMARKETEDFLAG = AttributesEnum.IsMarketedFlag.index();
    public static final int RELEASESTATUSFLAG = AttributesEnum.ReleaseStatusFlag.index();
    public static final int BSLNAME = AttributesEnum.BslName.index();
    public static final int DESIGNEE = AttributesEnum.Designee.index();
    public static final int MQMCOMMENT = AttributesEnum.MqmComment.index();
    public static final int TASLNAME = AttributesEnum.TaslName.index();
    public static final int TASLREJECTCOMMENT = AttributesEnum.TaslRejectComment.index();
    public static final int MEDICALLEADNAME = AttributesEnum.MedicalLeadName.index();
    public static final int MEDICALLEADREJECTCOMMENT = AttributesEnum.MedicalLeadRejectComment.index();
    public static final int CRSEFFECTIVEDT = AttributesEnum.CrsEffectiveDt.index();
    public static final int REVIEWAPPROVEREQUIREDFLAG = AttributesEnum.ReviewApproveRequiredFlag.index();
    public static final int UIVERSIONNUMBER = AttributesEnum.UiVersionNumber.index();
    public static final int CRSCOMPOUNDTYPE = AttributesEnum.CrsCompoundType.index();
    public static final int CRSCOMPOUNDCODE = AttributesEnum.CrsCompoundCode.index();
    public static final int STATENAME = AttributesEnum.StateName.index();
    public static final int DESIGNEENAME = AttributesEnum.DesigneeName.index();
    public static final int LASTSYNCDATE = AttributesEnum.LastSyncDate.index();
    public static final int CRSCURRENTPUBLISHED = AttributesEnum.CrsCurrentPublished.index();
    public static final int CREATIONTS = AttributesEnum.CreationTs.index();
    public static final int MODIFICATIONTS = AttributesEnum.ModificationTs.index();
    public static final int VERSIONSVO = AttributesEnum.VersionsVO.index();
    public static final int CRSVERSIONCOMPAREVO = AttributesEnum.CRSVersionCompareVO.index();
    public static final int CRSEXPORTPTCURRENTVO = AttributesEnum.CrsExportPTCurrentVO.index();
    public static final int BSLUSERVA = AttributesEnum.BSLUserVA.index();
    public static final int TASLUSERVA = AttributesEnum.TASLUserVA.index();
    public static final int MLUSERVA = AttributesEnum.MLUserVA.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsContentBaseVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CrsId.
     * @return the CrsId
     */
    public Long getCrsId() {
        return (Long) getAttributeInternal(CRSID);
    }

    /**
     * Gets the attribute value for the calculated attribute CrsName.
     * @return the CrsName
     */
    public String getCrsName() {
        return (String) getAttributeInternal(CRSNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute StateId.
     * @return the StateId
     */
    public Integer getStateId() {
        return (Integer) getAttributeInternal(STATEID);
    }

    /**
     * Gets the attribute value for the calculated attribute CompoundId.
     * @return the CompoundId
     */
    public Integer getCompoundId() {
        return (Integer) getAttributeInternal(COMPOUNDID);
    }

    /**
     * Gets the attribute value for the calculated attribute GenericName.
     * @return the GenericName
     */
    public String getGenericName() {
        return (String) getAttributeInternal(GENERICNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute TradeName.
     * @return the TradeName
     */
    public String getTradeName() {
        return (String) getAttributeInternal(TRADENAME);
    }

    /**
     * Gets the attribute value for the calculated attribute Indication.
     * @return the Indication
     */
    public String getIndication() {
        return (String) getAttributeInternal(INDICATION);
    }

    /**
     * Gets the attribute value for the calculated attribute IsMarketedFlag.
     * @return the IsMarketedFlag
     */
    public String getIsMarketedFlag() {
        return (String) getAttributeInternal(ISMARKETEDFLAG);
    }

    /**
     * Gets the attribute value for the calculated attribute ReleaseStatusFlag.
     * @return the ReleaseStatusFlag
     */
    public String getReleaseStatusFlag() {
        return (String) getAttributeInternal(RELEASESTATUSFLAG);
    }

    /**
     * Gets the attribute value for the calculated attribute BslName.
     * @return the BslName
     */
    public String getBslName() {
        return (String) getAttributeInternal(BSLNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute Designee.
     * @return the Designee
     */
    public String getDesignee() {
        return (String) getAttributeInternal(DESIGNEE);
    }

    /**
     * Gets the attribute value for the calculated attribute MqmComment.
     * @return the MqmComment
     */
    public String getMqmComment() {
        return (String) getAttributeInternal(MQMCOMMENT);
    }

    /**
     * Gets the attribute value for the calculated attribute TaslName.
     * @return the TaslName
     */
    public String getTaslName() {
        return (String) getAttributeInternal(TASLNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute TaslRejectComment.
     * @return the TaslRejectComment
     */
    public String getTaslRejectComment() {
        return (String) getAttributeInternal(TASLREJECTCOMMENT);
    }

    /**
     * Gets the attribute value for the calculated attribute MedicalLeadName.
     * @return the MedicalLeadName
     */
    public String getMedicalLeadName() {
        return (String) getAttributeInternal(MEDICALLEADNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute MedicalLeadRejectComment.
     * @return the MedicalLeadRejectComment
     */
    public String getMedicalLeadRejectComment() {
        return (String) getAttributeInternal(MEDICALLEADREJECTCOMMENT);
    }

    /**
     * Gets the attribute value for the calculated attribute CrsEffectiveDt.
     * @return the CrsEffectiveDt
     */
    public Timestamp getCrsEffectiveDt() {
        return (Timestamp) getAttributeInternal(CRSEFFECTIVEDT);
    }

    /**
     * Gets the attribute value for the calculated attribute ReviewApproveRequiredFlag.
     * @return the ReviewApproveRequiredFlag
     */
    public String getReviewApproveRequiredFlag() {
        return (String) getAttributeInternal(REVIEWAPPROVEREQUIREDFLAG);
    }

    /**
     * Gets the attribute value for the calculated attribute UiVersionNumber.
     * @return the UiVersionNumber
     */
    public Integer getUiVersionNumber() {
        return (Integer) getAttributeInternal(UIVERSIONNUMBER);
    }

    /**
     * Gets the attribute value for the calculated attribute CrsCompoundType.
     * @return the CrsCompoundType
     */
    public String getCrsCompoundType() {
        return (String) getAttributeInternal(CRSCOMPOUNDTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute CrsCompoundCode.
     * @return the CrsCompoundCode
     */
    public String getCrsCompoundCode() {
        return (String) getAttributeInternal(CRSCOMPOUNDCODE);
    }

    /**
     * Gets the attribute value for the calculated attribute StateName.
     * @return the StateName
     */
    public String getStateName() {
        return (String) getAttributeInternal(STATENAME);
    }

    /**
     * Gets the attribute value for the calculated attribute DesigneeName.
     * @return the DesigneeName
     */
    public String getDesigneeName() {
        return (String) getAttributeInternal(DESIGNEENAME);
    }

    /**
     * Gets the attribute value for the calculated attribute LastSyncDate.
     * @return the LastSyncDate
     */
    public Date getLastSyncDate() {
        return (Date) getAttributeInternal(LASTSYNCDATE);
    }

    /**
     * Gets the attribute value for the calculated attribute CreationTs.
     * @return the CreationTs
     */
    public Timestamp getCreationTs() {
        return (Timestamp) getAttributeInternal(CREATIONTS);
    }

    /**
     * Gets the attribute value for the calculated attribute ModificationTs.
     * @return the ModificationTs
     */
    public Timestamp getModificationTs() {
        return (Timestamp) getAttributeInternal(MODIFICATIONTS);
    }

    /**
     * Gets the attribute value for the calculated attribute CrsCurrentPublished.
     * @return the CrsCurrentPublished
     */
    public String getCrsCurrentPublished() {
        return (String) getAttributeInternal(CRSCURRENTPUBLISHED);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link VersionsVO.
     */
    public RowIterator getVersionsVO() {
        return (RowIterator) getAttributeInternal(VERSIONSVO);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link CRSVersionCompareVO.
     */
    public RowIterator getCRSVersionCompareVO() {
        return (RowIterator) getAttributeInternal(CRSVERSIONCOMPAREVO);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link CrsExportPTCurrentVO.
     */
    public RowIterator getCrsExportPTCurrentVO() {
        return (RowIterator) getAttributeInternal(CRSEXPORTPTCURRENTVO);
    }

    /**
     * Gets the view accessor <code>RowSet</code> BSLUserVA.
     */
    public RowSet getBSLUserVA() {
        return (RowSet) getAttributeInternal(BSLUSERVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> TASLUserVA.
     */
    public RowSet getTASLUserVA() {
        return (RowSet) getAttributeInternal(TASLUSERVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> MLUserVA.
     */
    public RowSet getMLUserVA() {
        return (RowSet) getAttributeInternal(MLUSERVA);
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

