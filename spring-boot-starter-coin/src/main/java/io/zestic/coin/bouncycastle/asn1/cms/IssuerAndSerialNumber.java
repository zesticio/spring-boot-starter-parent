package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x509.X509Name;

import java.math.BigInteger;

public class IssuerAndSerialNumber
        extends ASN1Encodable {

    X509Name name;
    DERInteger serialNumber;

    public IssuerAndSerialNumber(
            ASN1Sequence seq) {
        this.name = X509Name.getInstance(seq.getObjectAt(0));
        this.serialNumber = (DERInteger) seq.getObjectAt(1);
    }

    public IssuerAndSerialNumber(
            X509Name name,
            BigInteger serialNumber) {
        this.name = name;
        this.serialNumber = new DERInteger(serialNumber);
    }

    public IssuerAndSerialNumber(
            X509Name name,
            DERInteger serialNumber) {
        this.name = name;
        this.serialNumber = serialNumber;
    }

    public static IssuerAndSerialNumber getInstance(
            Object obj) {
        if (obj instanceof IssuerAndSerialNumber) {
            return (IssuerAndSerialNumber) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new IssuerAndSerialNumber((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException(
                "Illegal object in IssuerAndSerialNumber: " + obj.getClass().getName());
    }

    public X509Name getName() {
        return name;
    }

    public DERInteger getSerialNumber() {
        return serialNumber;
    }

    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(name);
        v.add(serialNumber);

        return new DERSequence(v);
    }
}
