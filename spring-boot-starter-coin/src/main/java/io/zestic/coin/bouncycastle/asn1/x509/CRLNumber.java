package io.zestic.coin.bouncycastle.asn1.x509;

import io.zestic.coin.bouncycastle.asn1.DERInteger;

import java.math.BigInteger;

/**
 * The CRLNumber object.
 * CRLNumber::= INTEGER(0..MAX) */
public class CRLNumber
        extends DERInteger {

    public CRLNumber(
            BigInteger number) {
        super(number);
    }

    public BigInteger getCRLNumber() {
        return getPositiveValue();
    }

    public String toString() {
        return "CRLNumber: " + getCRLNumber();
    }
}
