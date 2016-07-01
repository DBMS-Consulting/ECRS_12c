package com.novartis.ecrs.model.entity;

import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
import oracle.jbo.server.TransactionEvent;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 12 14:58:54 IST 2015
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsCompoundEOImpl extends EntityImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. Do not modify.
     */
    public enum AttributesEnum {
        CompoundId {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCompoundId();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setCompoundId((Integer)value);
            }
        }
        ,
        CompoundCode {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCompoundCode();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setCompoundCode((String)value);
            }
        }
        ,
        CompoundType {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCompoundType();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setCompoundType((String)value);
            }
        }
        ,
        CompoundDesc {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCompoundDesc();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setCompoundDesc((String)value);
            }
        }
        ,
        ActiveFlag {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getActiveFlag();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setActiveFlag((String)value);
            }
        }
        ,
        CrsContentEO {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCrsContentEO();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        CrsContentEO1 {
            public Object get(CrsCompoundEOImpl obj) {
                return obj.getCrsContentEO1();
            }

            public void put(CrsCompoundEOImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static int firstIndex = 0;

        public abstract Object get(CrsCompoundEOImpl object);

        public abstract void put(CrsCompoundEOImpl object, Object value);

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


    public static final int COMPOUNDID = AttributesEnum.CompoundId.index();
    public static final int COMPOUNDCODE = AttributesEnum.CompoundCode.index();
    public static final int COMPOUNDTYPE = AttributesEnum.CompoundType.index();
    public static final int COMPOUNDDESC = AttributesEnum.CompoundDesc.index();
    public static final int ACTIVEFLAG = AttributesEnum.ActiveFlag.index();
    public static final int CRSCONTENTEO = AttributesEnum.CrsContentEO.index();
    public static final int CRSCONTENTEO1 = AttributesEnum.CrsContentEO1.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsCompoundEOImpl() {
    }


    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.novartis.ecrs.model.entity.CrsCompoundEO");
    }

    /**
     * Gets the attribute value for CompoundId, using the alias name CompoundId.
     * @return the value of CompoundId
     */
    public Integer getCompoundId() {
        return (Integer)getAttributeInternal(COMPOUNDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CompoundId.
     * @param value value to set the CompoundId
     */
    public void setCompoundId(Integer value) {
        setAttributeInternal(COMPOUNDID, value);
    }

    /**
     * Gets the attribute value for CompoundCode, using the alias name CompoundCode.
     * @return the value of CompoundCode
     */
    public String getCompoundCode() {
        return (String)getAttributeInternal(COMPOUNDCODE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CompoundCode.
     * @param value value to set the CompoundCode
     */
    public void setCompoundCode(String value) {
        setAttributeInternal(COMPOUNDCODE, value);
    }

    /**
     * Gets the attribute value for CompoundType, using the alias name CompoundType.
     * @return the value of CompoundType
     */
    public String getCompoundType() {
        return (String)getAttributeInternal(COMPOUNDTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CompoundType.
     * @param value value to set the CompoundType
     */
    public void setCompoundType(String value) {
        setAttributeInternal(COMPOUNDTYPE, value);
    }

    /**
     * Gets the attribute value for CompoundDesc, using the alias name CompoundDesc.
     * @return the value of CompoundDesc
     */
    public String getCompoundDesc() {
        return (String)getAttributeInternal(COMPOUNDDESC);
    }

    /**
     * Sets <code>value</code> as the attribute value for CompoundDesc.
     * @param value value to set the CompoundDesc
     */
    public void setCompoundDesc(String value) {
        setAttributeInternal(COMPOUNDDESC, value);
    }

    /**
     * Gets the attribute value for ActiveFlag, using the alias name ActiveFlag.
     * @return the value of ActiveFlag
     */
    public String getActiveFlag() {
        return (String)getAttributeInternal(ACTIVEFLAG);
    }

    /**
     * Sets <code>value</code> as the attribute value for ActiveFlag.
     * @param value value to set the ActiveFlag
     */
    public void setActiveFlag(String value) {
        setAttributeInternal(ACTIVEFLAG, value);
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
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getCrsContentEO() {
        return (RowIterator)getAttributeInternal(CRSCONTENTEO);
    }


    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getCrsContentEO1() {
        return (RowIterator)getAttributeInternal(CRSCONTENTEO1);
    }

    /**
     * @param compoundId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(Integer compoundId) {
        return new Key(new Object[]{compoundId});
    }

    public void doDML(int operation, TransactionEvent e) {
        if(operation == DML_INSERT)
            this.setCompoundId((Integer)(new SequenceImpl("crs_compounds_seq",getDBTransaction()).getSequenceNumber()).intValue());
        super.doDML(operation, e);
    }
}
