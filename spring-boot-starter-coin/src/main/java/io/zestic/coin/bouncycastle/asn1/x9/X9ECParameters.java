package io.zestic.coin.bouncycastle.asn1.x9;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.math.ec.ECCurve;
import io.zestic.coin.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * ASN.1 def for Elliptic-Curve ECParameters structure. See X9.62, for further
 * details.
 */
public class X9ECParameters
        extends ASN1Encodable
        implements X9ObjectIdentifiers {

    private static final BigInteger ONE = BigInteger.valueOf(1);
    private final ECCurve curve;
    private final ECPoint g;
    private final BigInteger n;
    private final byte[] seed;
    private X9FieldID fieldID;
    private BigInteger h;

    public X9ECParameters(
            ASN1Sequence seq) {
        if (!(seq.getObjectAt(0) instanceof DERInteger)
                || !((DERInteger) seq.getObjectAt(0)).getValue().equals(ONE)) {
            throw new IllegalArgumentException("bad version in X9ECParameters");
        }

        X9Curve x9c = new X9Curve(
                new X9FieldID((ASN1Sequence) seq.getObjectAt(1)),
                (ASN1Sequence) seq.getObjectAt(2));

        this.curve = x9c.getCurve();
        this.g = new X9ECPoint(curve, (ASN1OctetString) seq.getObjectAt(3)).getPoint();
        this.n = ((DERInteger) seq.getObjectAt(4)).getValue();
        this.seed = x9c.getSeed();

        if (seq.size() == 6) {
            this.h = ((DERInteger) seq.getObjectAt(5)).getValue();
        }
    }

    public X9ECParameters(
            ECCurve curve,
            ECPoint g,
            BigInteger n) {
        this(curve, g, n, ONE, null);
    }

    public X9ECParameters(
            ECCurve curve,
            ECPoint g,
            BigInteger n,
            BigInteger h) {
        this(curve, g, n, h, null);
    }

    public X9ECParameters(
            ECCurve curve,
            ECPoint g,
            BigInteger n,
            BigInteger h,
            byte[] seed) {
        this.curve = curve;
        this.g = g;
        this.n = n;
        this.h = h;
        this.seed = seed;

        if (curve instanceof ECCurve.Fp) {
            this.fieldID = new X9FieldID(((ECCurve.Fp) curve).getQ());
        } else {
            if (curve instanceof ECCurve.F2m) {
                ECCurve.F2m curveF2m = (ECCurve.F2m) curve;
                this.fieldID = new X9FieldID(curveF2m.getM(), curveF2m.getK1(),
                        curveF2m.getK2(), curveF2m.getK3());
            }
        }
    }

    public ECCurve getCurve() {
        return curve;
    }

    public ECPoint getG() {
        return g;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getH() {
        if (h == null) {
            return ONE;        // TODO - this should be calculated, it will cause issues with custom curves.
        }

        return h;
    }

    public byte[] getSeed() {
        return seed;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * ECParameters ::= SEQUENCE {
     * version         INTEGER { ecpVer1(1) } (ecpVer1),
     * fieldID         FieldID {{FieldTypes}},
     * curve           X9Curve,
     * base            X9ECPoint,
     * order           INTEGER,
     * cofactor        INTEGER OPTIONAL
     * }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(new DERInteger(1));
        v.add(fieldID);
        v.add(new X9Curve(curve, seed));
        v.add(new X9ECPoint(g));
        v.add(new DERInteger(n));

        if (h != null) {
            v.add(new DERInteger(h));
        }

        return new DERSequence(v);
    }
}
