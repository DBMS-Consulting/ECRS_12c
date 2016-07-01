package com.novartis.ecrs.model.view;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.RowIterator;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Apr 29 09:31:43 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class HierarchyChildVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        Prikey {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getPrikey();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setPrikey((String)value);
            }
        }
        ,
        Parent {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getParent();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setParent((String)value);
            }
        }
        ,
        DictContentId {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getDictContentId();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setDictContentId((String)value);
            }
        }
        ,
        DictContentCode {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getDictContentCode();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setDictContentCode((String)value);
            }
        }
        ,
        Term {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTerm();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTerm((String)value);
            }
        }
        ,
        Level {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getLevel();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setLevel((Long)value);
            }
        }
        ,
        LevelName {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getLevelName();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setLevelName((String)value);
            }
        }
        ,
        DictShortName {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getDictShortName();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setDictShortName((String)value);
            }
        }
        ,
        DictContentAltCode {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getDictContentAltCode();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setDictContentAltCode((String)value);
            }
        }
        ,
        ApprovedFlag {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getApprovedFlag();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setApprovedFlag((String)value);
            }
        }
        ,
        Status {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getStatus();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setStatus((String)value);
            }
        }
        ,
        Termlvl {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTermlvl();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTermlvl((BigDecimal)value);
            }
        }
        ,
        Termscp {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTermscp();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTermscp((BigDecimal)value);
            }
        }
        ,
        Termcat {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTermcat();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTermcat((String)value);
            }
        }
        ,
        Termweig {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTermweig();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTermweig((String)value);
            }
        }
        ,
        PredictGroupId {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getPredictGroupId();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setPredictGroupId((BigDecimal)value);
            }
        }
        ,
        FormattedScope {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getFormattedScope();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setFormattedScope((BigDecimal)value);
            }
        }
        ,
        TermPath {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getTermPath();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setTermPath((String)value);
            }
        }
        ,
        PrimLinkFlag {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getPrimLinkFlag();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setPrimLinkFlag((String)value);
            }
        }
        ,
        SortOrder {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getSortOrder();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setSortOrder((BigDecimal)value);
            }
        }
        ,
        ChildExists {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getChildExists();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setChildExists((String)value);
            }
        }
        ,
        DictContentEntryTs {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getDictContentEntryTs();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setDictContentEntryTs((Timestamp)value);
            }
        }
        ,
        CEndTs {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getCEndTs();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setCEndTs((Timestamp)value);
            }
        }
        ,
        Qual {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getQual();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setQual((String)value);
            }
        }
        ,
        QualFlag {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getQualFlag();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setQualFlag((String)value);
            }
        }
        ,
        HierarchyChildDetailVO {
            public Object get(HierarchyChildVORowImpl obj) {
                return obj.getHierarchyChildDetailVO();
            }

            public void put(HierarchyChildVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(HierarchyChildVORowImpl object);

        public abstract void put(HierarchyChildVORowImpl object, Object value);

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


    public static final int PRIKEY = AttributesEnum.Prikey.index();
    public static final int PARENT = AttributesEnum.Parent.index();
    public static final int DICTCONTENTID = AttributesEnum.DictContentId.index();
    public static final int DICTCONTENTCODE = AttributesEnum.DictContentCode.index();
    public static final int TERM = AttributesEnum.Term.index();
    public static final int LEVEL = AttributesEnum.Level.index();
    public static final int LEVELNAME = AttributesEnum.LevelName.index();
    public static final int DICTSHORTNAME = AttributesEnum.DictShortName.index();
    public static final int DICTCONTENTALTCODE = AttributesEnum.DictContentAltCode.index();
    public static final int APPROVEDFLAG = AttributesEnum.ApprovedFlag.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int TERMLVL = AttributesEnum.Termlvl.index();
    public static final int TERMSCP = AttributesEnum.Termscp.index();
    public static final int TERMCAT = AttributesEnum.Termcat.index();
    public static final int TERMWEIG = AttributesEnum.Termweig.index();
    public static final int PREDICTGROUPID = AttributesEnum.PredictGroupId.index();
    public static final int FORMATTEDSCOPE = AttributesEnum.FormattedScope.index();
    public static final int TERMPATH = AttributesEnum.TermPath.index();
    public static final int PRIMLINKFLAG = AttributesEnum.PrimLinkFlag.index();
    public static final int SORTORDER = AttributesEnum.SortOrder.index();
    public static final int CHILDEXISTS = AttributesEnum.ChildExists.index();
    public static final int DICTCONTENTENTRYTS = AttributesEnum.DictContentEntryTs.index();
    public static final int CENDTS = AttributesEnum.CEndTs.index();
    public static final int QUAL = AttributesEnum.Qual.index();
    public static final int QUALFLAG = AttributesEnum.QualFlag.index();
    public static final int HIERARCHYCHILDDETAILVO = AttributesEnum.HierarchyChildDetailVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public HierarchyChildVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute Prikey.
     * @return the Prikey
     */
    public String getPrikey() {
        return (String) getAttributeInternal(PRIKEY);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Prikey.
     * @param value value to set the  Prikey
     */
    public void setPrikey(String value) {
        setAttributeInternal(PRIKEY, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Parent.
     * @return the Parent
     */
    public String getParent() {
        return (String) getAttributeInternal(PARENT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Parent.
     * @param value value to set the  Parent
     */
    public void setParent(String value) {
        setAttributeInternal(PARENT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DictContentId.
     * @return the DictContentId
     */
    public String getDictContentId() {
        return (String) getAttributeInternal(DICTCONTENTID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DictContentId.
     * @param value value to set the  DictContentId
     */
    public void setDictContentId(String value) {
        setAttributeInternal(DICTCONTENTID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DictContentCode.
     * @return the DictContentCode
     */
    public String getDictContentCode() {
        return (String) getAttributeInternal(DICTCONTENTCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DictContentCode.
     * @param value value to set the  DictContentCode
     */
    public void setDictContentCode(String value) {
        setAttributeInternal(DICTCONTENTCODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Term.
     * @return the Term
     */
    public String getTerm() {
        return (String) getAttributeInternal(TERM);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Term.
     * @param value value to set the  Term
     */
    public void setTerm(String value) {
        setAttributeInternal(TERM, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Level.
     * @return the Level
     */
    public Long getLevel() {
        return (Long) getAttributeInternal(LEVEL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Level.
     * @param value value to set the  Level
     */
    public void setLevel(Long value) {
        setAttributeInternal(LEVEL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute LevelName.
     * @return the LevelName
     */
    public String getLevelName() {
        return (String) getAttributeInternal(LEVELNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute LevelName.
     * @param value value to set the  LevelName
     */
    public void setLevelName(String value) {
        setAttributeInternal(LEVELNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DictShortName.
     * @return the DictShortName
     */
    public String getDictShortName() {
        return (String) getAttributeInternal(DICTSHORTNAME);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DictShortName.
     * @param value value to set the  DictShortName
     */
    public void setDictShortName(String value) {
        setAttributeInternal(DICTSHORTNAME, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DictContentAltCode.
     * @return the DictContentAltCode
     */
    public String getDictContentAltCode() {
        return (String) getAttributeInternal(DICTCONTENTALTCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DictContentAltCode.
     * @param value value to set the  DictContentAltCode
     */
    public void setDictContentAltCode(String value) {
        setAttributeInternal(DICTCONTENTALTCODE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ApprovedFlag.
     * @return the ApprovedFlag
     */
    public String getApprovedFlag() {
        return (String) getAttributeInternal(APPROVEDFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ApprovedFlag.
     * @param value value to set the  ApprovedFlag
     */
    public void setApprovedFlag(String value) {
        setAttributeInternal(APPROVEDFLAG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Status.
     * @return the Status
     */
    public String getStatus() {
        return (String) getAttributeInternal(STATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Status.
     * @param value value to set the  Status
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Termlvl.
     * @return the Termlvl
     */
    public BigDecimal getTermlvl() {
        return (BigDecimal) getAttributeInternal(TERMLVL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Termlvl.
     * @param value value to set the  Termlvl
     */
    public void setTermlvl(BigDecimal value) {
        setAttributeInternal(TERMLVL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Termscp.
     * @return the Termscp
     */
    public BigDecimal getTermscp() {
        return (BigDecimal) getAttributeInternal(TERMSCP);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Termscp.
     * @param value value to set the  Termscp
     */
    public void setTermscp(BigDecimal value) {
        setAttributeInternal(TERMSCP, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Termcat.
     * @return the Termcat
     */
    public String getTermcat() {
        return (String) getAttributeInternal(TERMCAT);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Termcat.
     * @param value value to set the  Termcat
     */
    public void setTermcat(String value) {
        setAttributeInternal(TERMCAT, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Termweig.
     * @return the Termweig
     */
    public String getTermweig() {
        return (String) getAttributeInternal(TERMWEIG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Termweig.
     * @param value value to set the  Termweig
     */
    public void setTermweig(String value) {
        setAttributeInternal(TERMWEIG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PredictGroupId.
     * @return the PredictGroupId
     */
    public BigDecimal getPredictGroupId() {
        return (BigDecimal) getAttributeInternal(PREDICTGROUPID);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PredictGroupId.
     * @param value value to set the  PredictGroupId
     */
    public void setPredictGroupId(BigDecimal value) {
        setAttributeInternal(PREDICTGROUPID, value);
    }

    /**
     * Gets the attribute value for the calculated attribute FormattedScope.
     * @return the FormattedScope
     */
    public BigDecimal getFormattedScope() {
        return (BigDecimal) getAttributeInternal(FORMATTEDSCOPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute FormattedScope.
     * @param value value to set the  FormattedScope
     */
    public void setFormattedScope(BigDecimal value) {
        setAttributeInternal(FORMATTEDSCOPE, value);
    }

    /**
     * Gets the attribute value for the calculated attribute TermPath.
     * @return the TermPath
     */
    public String getTermPath() {
        return (String) getAttributeInternal(TERMPATH);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute TermPath.
     * @param value value to set the  TermPath
     */
    public void setTermPath(String value) {
        setAttributeInternal(TERMPATH, value);
    }

    /**
     * Gets the attribute value for the calculated attribute PrimLinkFlag.
     * @return the PrimLinkFlag
     */
    public String getPrimLinkFlag() {
        return (String) getAttributeInternal(PRIMLINKFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute PrimLinkFlag.
     * @param value value to set the  PrimLinkFlag
     */
    public void setPrimLinkFlag(String value) {
        setAttributeInternal(PRIMLINKFLAG, value);
    }

    /**
     * Gets the attribute value for the calculated attribute SortOrder.
     * @return the SortOrder
     */
    public BigDecimal getSortOrder() {
        return (BigDecimal) getAttributeInternal(SORTORDER);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SortOrder.
     * @param value value to set the  SortOrder
     */
    public void setSortOrder(BigDecimal value) {
        setAttributeInternal(SORTORDER, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ChildExists.
     * @return the ChildExists
     */
    public String getChildExists() {
        return (String) getAttributeInternal(CHILDEXISTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute ChildExists.
     * @param value value to set the  ChildExists
     */
    public void setChildExists(String value) {
        setAttributeInternal(CHILDEXISTS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute DictContentEntryTs.
     * @return the DictContentEntryTs
     */
    public Timestamp getDictContentEntryTs() {
        return (Timestamp) getAttributeInternal(DICTCONTENTENTRYTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute DictContentEntryTs.
     * @param value value to set the  DictContentEntryTs
     */
    public void setDictContentEntryTs(Timestamp value) {
        setAttributeInternal(DICTCONTENTENTRYTS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute CEndTs.
     * @return the CEndTs
     */
    public Timestamp getCEndTs() {
        return (Timestamp) getAttributeInternal(CENDTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute CEndTs.
     * @param value value to set the  CEndTs
     */
    public void setCEndTs(Timestamp value) {
        setAttributeInternal(CENDTS, value);
    }

    /**
     * Gets the attribute value for the calculated attribute Qual.
     * @return the Qual
     */
    public String getQual() {
        return (String) getAttributeInternal(QUAL);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute Qual.
     * @param value value to set the  Qual
     */
    public void setQual(String value) {
        setAttributeInternal(QUAL, value);
    }

    /**
     * Gets the attribute value for the calculated attribute QualFlag.
     * @return the QualFlag
     */
    public String getQualFlag() {
        return (String) getAttributeInternal(QUALFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute QualFlag.
     * @param value value to set the  QualFlag
     */
    public void setQualFlag(String value) {
        setAttributeInternal(QUALFLAG, value);
    }

    /**
     * Gets the associated <code>RowIterator</code> using master-detail link HierarchyChildDetailVO.
     */
    public RowIterator getHierarchyChildDetailVO() {
        return (RowIterator)getAttributeInternal(HIERARCHYCHILDDETAILVO);
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