package io.zestic.coin.bouncycastle.asn1.x9;

import io.zestic.coin.bouncycastle.asn1.*;

public class X962Parameters
        extends ASN1Encodable
        implements ASN1Choice {

    private DERObject params = null;

    public X962Parameters(
            X9ECParameters ecParameters) {
        this.params = ecParameters.getDERObject();
    }

    public X962Parameters(
            DERObjectIdentifier namedCurve) {
        this.params = namedCurve;
    }

    public X962Parameters(
            DERObject obj) {
        this.params = obj;
    }

    public static X962Parameters getInstance(
            Object obj) {
        if (obj == null || obj instanceof X962Parameters) {
            return (X962Parameters) obj;
        }

        if (obj instanceof DERObject) {
            return new X962Parameters((DERObject) obj);
        }

        throw new IllegalArgumentException("unknown object in getInstance()");
    }

    public static X962Parameters getInstance(
            ASN1TaggedObject obj,
            boolean explicit) {
        return getInstance(obj.getObject()); // must be explicitly tagged
    }

    public boolean isNamedCurve() {
        return (params instanceof DERObjectIdentifier);
    }

    public boolean isImplicitlyCA() {
        return (params instanceof ASN1Null);
    }

    public DERObject getParameters() {
        return params;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * Parameters ::= CHOICE {
     * ecParameters ECParameters,
     * namedCurve   CURVES.&id({CurveNames}),
     * implicitlyCA NULL
     * }
     */
    public DERObject toASN1Object() {
        return params;
    }
}
