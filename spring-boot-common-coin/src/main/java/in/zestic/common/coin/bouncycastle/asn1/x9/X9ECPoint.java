package in.zestic.common.coin.bouncycastle.asn1.x9;

import in.zestic.common.coin.bouncycastle.asn1.ASN1Encodable;
import in.zestic.common.coin.bouncycastle.asn1.ASN1OctetString;
import in.zestic.common.coin.bouncycastle.asn1.DERObject;
import in.zestic.common.coin.bouncycastle.asn1.DEROctetString;
import in.zestic.common.coin.bouncycastle.math.ec.ECCurve;
import in.zestic.common.coin.bouncycastle.math.ec.ECPoint;

/**
 * class for describing an ECPoint as a DER object.
 */
public class X9ECPoint
        extends ASN1Encodable {

    ECPoint p;

    public X9ECPoint(
            ECPoint p) {
        this.p = p;
    }

    public X9ECPoint(
            ECCurve c,
            ASN1OctetString s) {
        this.p = c.decodePoint(s.getOctets());
    }

    public ECPoint getPoint() {
        return p;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     *  ECPoint ::= OCTET STRING
     * <p>
     * Octet string produced using ECPoint.getEncoded().
     */
    public DERObject toASN1Object() {
        return new DEROctetString(p.getEncoded());
    }
}
