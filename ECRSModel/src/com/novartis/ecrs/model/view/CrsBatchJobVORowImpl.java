package com.novartis.ecrs.model.view;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Jun 02 17:06:03 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsBatchJobVORowImpl extends ViewRowImpl {


    public static final int ENTITY_CRSBATCHJOBEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        JobDesc {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobDesc();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobDesc((String) value);
            }
        }
        ,
        JobId {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobId();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobId((BigDecimal) value);
            }
        }
        ,
        JobStatus {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobStatus();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobStatus((String) value);
            }
        }
        ,
        JobSubmitDate {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobSubmitDate();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobSubmitDate((Timestamp) value);
            }
        }
        ,
        JobEndDate {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobEndDate();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobEndDate((Timestamp) value);
            }
        }
        ,
        JobRunDuration {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobRunDuration();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobRunDuration((String) value);
            }
        }
        ,
        JobStartDate {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getJobStartDate();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setJobStartDate((Timestamp) value);
            }
        }
        ,
        Recurring {
            protected Object get(CrsBatchJobVORowImpl obj) {
                return obj.getRecurring();
            }

            protected void put(CrsBatchJobVORowImpl obj, Object value) {
                obj.setRecurring((String) value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        ;
        private static final int firstIndex = 0;

        protected abstract Object get(CrsBatchJobVORowImpl object);

        protected abstract void put(CrsBatchJobVORowImpl object, Object value);

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


    public static final int JOBDESC = AttributesEnum.JobDesc.index();
    public static final int JOBID = AttributesEnum.JobId.index();
    public static final int JOBSTATUS = AttributesEnum.JobStatus.index();
    public static final int JOBSUBMITDATE = AttributesEnum.JobSubmitDate.index();
    public static final int JOBENDDATE = AttributesEnum.JobEndDate.index();
    public static final int JOBRUNDURATION = AttributesEnum.JobRunDuration.index();
    public static final int JOBSTARTDATE = AttributesEnum.JobStartDate.index();
    public static final int RECURRING = AttributesEnum.Recurring.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsBatchJobVORowImpl() {
    }

    /**
     * Gets CrsBatchJobEO entity object.
     * @return the CrsBatchJobEO
     */
    public EntityImpl getCrsBatchJobEO() {
        return (EntityImpl) getEntity(ENTITY_CRSBATCHJOBEO);
    }

    /**
     * Gets the attribute value for JOB_DESC using the alias name JobDesc.
     * @return the JOB_DESC
     */
    public String getJobDesc() {
        return (String) getAttributeInternal(JOBDESC);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_DESC using the alias name JobDesc.
     * @param value value to set the JOB_DESC
     */
    public void setJobDesc(String value) {
        setAttributeInternal(JOBDESC, value);
    }

    /**
     * Gets the attribute value for JOB_ID using the alias name JobId.
     * @return the JOB_ID
     */
    public BigDecimal getJobId() {
        return (BigDecimal) getAttributeInternal(JOBID);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_ID using the alias name JobId.
     * @param value value to set the JOB_ID
     */
    public void setJobId(BigDecimal value) {
        setAttributeInternal(JOBID, value);
    }


    /**
     * Gets the attribute value for JOB_STATUS using the alias name JobStatus.
     * @return the JOB_STATUS
     */
    public String getJobStatus() {
        return (String) getAttributeInternal(JOBSTATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_STATUS using the alias name JobStatus.
     * @param value value to set the JOB_STATUS
     */
    public void setJobStatus(String value) {
        setAttributeInternal(JOBSTATUS, value);
    }

    /**
     * Gets the attribute value for JOB_SUBMIT_DATE using the alias name JobSubmitDate.
     * @return the JOB_SUBMIT_DATE
     */
    public Timestamp getJobSubmitDate() {
        return (Timestamp) getAttributeInternal(JOBSUBMITDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_SUBMIT_DATE using the alias name JobSubmitDate.
     * @param value value to set the JOB_SUBMIT_DATE
     */
    public void setJobSubmitDate(Timestamp value) {
        setAttributeInternal(JOBSUBMITDATE, value);
    }

    /**
     * Gets the attribute value for JOB_END_DATE using the alias name JobEndDate.
     * @return the JOB_END_DATE
     */
    public Timestamp getJobEndDate() {
        return (Timestamp) getAttributeInternal(JOBENDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_END_DATE using the alias name JobEndDate.
     * @param value value to set the JOB_END_DATE
     */
    public void setJobEndDate(Timestamp value) {
        setAttributeInternal(JOBENDDATE, value);
    }

    /**
     * Gets the attribute value for JOB_RUN_DURATION using the alias name JobRunDuration.
     * @return the JOB_RUN_DURATION
     */
    public String getJobRunDuration() {
        return (String) getAttributeInternal(JOBRUNDURATION);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_RUN_DURATION using the alias name JobRunDuration.
     * @param value value to set the JOB_RUN_DURATION
     */
    public void setJobRunDuration(String value) {
        setAttributeInternal(JOBRUNDURATION, value);
    }

    /**
     * Gets the attribute value for JOB_START_DATE using the alias name JobStartDate.
     * @return the JOB_START_DATE
     */
    public Timestamp getJobStartDate() {
        return (Timestamp) getAttributeInternal(JOBSTARTDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for JOB_START_DATE using the alias name JobStartDate.
     * @param value value to set the JOB_START_DATE
     */
    public void setJobStartDate(Timestamp value) {
        setAttributeInternal(JOBSTARTDATE, value);
    }

    /**
     * Gets the attribute value for RECURRING using the alias name Recurring.
     * @return the RECURRING
     */
    public String getRecurring() {
        return (String) getAttributeInternal(RECURRING);
    }

    /**
     * Sets <code>value</code> as attribute value for RECURRING using the alias name Recurring.
     * @param value value to set the RECURRING
     */
    public void setRecurring(String value) {
        setAttributeInternal(RECURRING, value);
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

