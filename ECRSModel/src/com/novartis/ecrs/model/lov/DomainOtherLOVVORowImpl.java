package com.novartis.ecrs.model.lov;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue Jun 18 17:16:56 IST 2019
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class DomainOtherLOVVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        DomainId {
            protected Object get(DomainOtherLOVVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DomainOtherLOVVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DomainName {
            protected Object get(DomainOtherLOVVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DomainOtherLOVVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        DomainDesc {
            protected Object get(DomainOtherLOVVORowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(DomainOtherLOVVORowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        protected abstract Object get(DomainOtherLOVVORowImpl object);

        protected abstract void put(DomainOtherLOVVORowImpl object, Object value);

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


    public static final int DOMAINID = AttributesEnum.DomainId.index();
    public static final int DOMAINNAME = AttributesEnum.DomainName.index();
    public static final int DOMAINDESC = AttributesEnum.DomainDesc.index();

    /**
     * This is the default constructor (do not remove).
     */
    public DomainOtherLOVVORowImpl() {
    }
}
