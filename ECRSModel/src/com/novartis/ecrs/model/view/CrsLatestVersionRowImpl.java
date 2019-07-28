package com.novartis.ecrs.model.view;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Wed Aug 15 20:21:13 IST 2018
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CrsLatestVersionRowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    protected enum AttributesEnum {
        CrsId {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSafetyTopic {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSpp {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSppColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestDsur {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestDsurColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestRmp {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestRmpColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestPsur {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestPsurColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestIb {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestIbColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestCds {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestCdsColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestOtherSearch {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestOtherSearchColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMissingInformation {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EarliestMissingInformColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestExpeditingRules {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        EarliestExpeditingRulColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestUnderlyingDisease {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestUnderlyingDisColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestAesiForNisProtocol {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestAesiForNisProColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestAesiNotRmp {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestAesiNotRmpColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSoc {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSocColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestDataDomain {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSearchDetails {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestSearchDetailsColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraCode {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraCodeColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraTerm {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraTermColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraExtension {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraExtensionColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraQualifier {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestMeddraQualifierColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestNonMeddraCompCmt {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ,
        LatestNonMedCompCmtColor {
            protected Object get(CrsLatestVersionRowImpl obj) {
                return obj.getAttributeInternal(index());
            }

            protected void put(CrsLatestVersionRowImpl obj, Object value) {
                obj.setAttributeInternal(index(), value);
            }
        }
        ;
        private static AttributesEnum[] vals = null;
        ;
        private static final int firstIndex = 0;

        protected abstract Object get(CrsLatestVersionRowImpl object);

        protected abstract void put(CrsLatestVersionRowImpl object, Object value);

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
    public static final int LATESTSAFETYTOPIC = AttributesEnum.LatestSafetyTopic.index();
    public static final int LATESTSPP = AttributesEnum.LatestSpp.index();
    public static final int LATESTSPPCOLOR = AttributesEnum.LatestSppColor.index();
    public static final int LATESTDSUR = AttributesEnum.LatestDsur.index();
    public static final int LATESTDSURCOLOR = AttributesEnum.LatestDsurColor.index();
    public static final int LATESTRMP = AttributesEnum.LatestRmp.index();
    public static final int LATESTRMPCOLOR = AttributesEnum.LatestRmpColor.index();
    public static final int LATESTPSUR = AttributesEnum.LatestPsur.index();
    public static final int LATESTPSURCOLOR = AttributesEnum.LatestPsurColor.index();
    public static final int LATESTIB = AttributesEnum.LatestIb.index();
    public static final int LATESTIBCOLOR = AttributesEnum.LatestIbColor.index();
    public static final int LATESTCDS = AttributesEnum.LatestCds.index();
    public static final int LATESTCDSCOLOR = AttributesEnum.LatestCdsColor.index();
    public static final int LATESTOTHERSEARCH = AttributesEnum.LatestOtherSearch.index();
    public static final int LATESTOTHERSEARCHCOLOR = AttributesEnum.LatestOtherSearchColor.index();
    public static final int LATESTMISSINGINFORMATION = AttributesEnum.LatestMissingInformation.index();
    public static final int EARLIESTMISSINGINFORMCOLOR = AttributesEnum.EarliestMissingInformColor.index();
    public static final int LATESTEXPEDITINGRULES = AttributesEnum.LatestExpeditingRules.index();
    public static final int EARLIESTEXPEDITINGRULCOLOR = AttributesEnum.EarliestExpeditingRulColor.index();
    public static final int LATESTUNDERLYINGDISEASE = AttributesEnum.LatestUnderlyingDisease.index();
    public static final int LATESTUNDERLYINGDISCOLOR = AttributesEnum.LatestUnderlyingDisColor.index();
    public static final int LATESTAESIFORNISPROTOCOL = AttributesEnum.LatestAesiForNisProtocol.index();
    public static final int LATESTAESIFORNISPROCOLOR = AttributesEnum.LatestAesiForNisProColor.index();
    public static final int LATESTAESINOTRMP = AttributesEnum.LatestAesiNotRmp.index();
    public static final int LATESTAESINOTRMPCOLOR = AttributesEnum.LatestAesiNotRmpColor.index();
    public static final int LATESTSOC = AttributesEnum.LatestSoc.index();
    public static final int LATESTSOCCOLOR = AttributesEnum.LatestSocColor.index();
    public static final int LATESTDATADOMAIN = AttributesEnum.LatestDataDomain.index();
    public static final int LATESTSEARCHDETAILS = AttributesEnum.LatestSearchDetails.index();
    public static final int LATESTSEARCHDETAILSCOLOR = AttributesEnum.LatestSearchDetailsColor.index();
    public static final int LATESTMEDDRACODE = AttributesEnum.LatestMeddraCode.index();
    public static final int LATESTMEDDRACODECOLOR = AttributesEnum.LatestMeddraCodeColor.index();
    public static final int LATESTMEDDRATERM = AttributesEnum.LatestMeddraTerm.index();
    public static final int LATESTMEDDRATERMCOLOR = AttributesEnum.LatestMeddraTermColor.index();
    public static final int LATESTMEDDRAEXTENSION = AttributesEnum.LatestMeddraExtension.index();
    public static final int LATESTMEDDRAEXTENSIONCOLOR = AttributesEnum.LatestMeddraExtensionColor.index();
    public static final int LATESTMEDDRAQUALIFIER = AttributesEnum.LatestMeddraQualifier.index();
    public static final int LATESTMEDDRAQUALIFIERCOLOR = AttributesEnum.LatestMeddraQualifierColor.index();
    public static final int LATESTNONMEDDRACOMPCMT = AttributesEnum.LatestNonMeddraCompCmt.index();
    public static final int LATESTNONMEDCOMPCMTCOLOR = AttributesEnum.LatestNonMedCompCmtColor.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CrsLatestVersionRowImpl() {
    }
}
