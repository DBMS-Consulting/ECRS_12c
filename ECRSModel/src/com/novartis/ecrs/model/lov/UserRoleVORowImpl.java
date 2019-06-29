package com.novartis.ecrs.model.lov;

import oracle.jbo.server.AttributeDefImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Apr 24 23:09:20 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class UserRoleVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        FullName {
            protected Object get(UserRoleVORowImpl obj) {
                return obj.getFullName();
            }

            protected void put(UserRoleVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        UserName {
            protected Object get(UserRoleVORowImpl obj) {
                return obj.getUserName();
            }

            protected void put(UserRoleVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        RoleName {
            protected Object get(UserRoleVORowImpl obj) {
                return obj.getRoleName();
            }

            protected void put(UserRoleVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LastName {
            protected Object get(UserRoleVORowImpl obj) {
                return obj.getLastName();
            }

            protected void put(UserRoleVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        FirstName {
            protected Object get(UserRoleVORowImpl obj) {
                return obj.getFirstName();
            }

            protected void put(UserRoleVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(UserRoleVORowImpl object);

        protected abstract void put(UserRoleVORowImpl object, Object value);

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

    public static final int FULLNAME = AttributesEnum.FullName.index();
    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int ROLENAME = AttributesEnum.RoleName.index();
    public static final int LASTNAME = AttributesEnum.LastName.index();
    public static final int FIRSTNAME = AttributesEnum.FirstName.index();

    /**
     * This is the default constructor (do not remove).
     */
    public UserRoleVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute FullName.
     * @return the FullName
     */
    public String getFullName() {
        return (String) getAttributeInternal(FULLNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute UserName.
     * @return the UserName
     */
    public String getUserName() {
        return (String) getAttributeInternal(USERNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute RoleName.
     * @return the RoleName
     */
    public String getRoleName() {
        return (String) getAttributeInternal(ROLENAME);
    }

    /**
     * Gets the attribute value for the calculated attribute LastName.
     * @return the LastName
     */
    public String getLastName() {
        return (String) getAttributeInternal(LASTNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute FirstName.
     * @return the FirstName
     */
    public String getFirstName() {
        return (String) getAttributeInternal(FIRSTNAME);
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

