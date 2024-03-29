package io.zestic.coin.bouncycastle.asn1.icao;

import io.zestic.coin.bouncycastle.asn1.*;

import java.util.Enumeration;

/**
 * The DataGroupHash object.
 * DataGroupHash  ::=  SEQUENCE {
 * dataGroupNumber         DataGroupNumber,
 * dataGroupHashValue     OCTET STRING }
 * <p>
 * DataGroupNumber ::= INTEGER {
 * dataGroup1    (1),
 * dataGroup1    (2),
 * dataGroup1    (3),
 * dataGroup1    (4),
 * dataGroup1    (5),
 * dataGroup1    (6),
 * dataGroup1    (7),
 * dataGroup1    (8),
 * dataGroup1    (9),
 * dataGroup1    (10),
 * dataGroup1    (11),
 * dataGroup1    (12),
 * dataGroup1    (13),
 * dataGroup1    (14),
 * dataGroup1    (15),
 * dataGroup1    (16) }
 */
public class DataGroupHash
        extends ASN1Encodable {

    DERInteger dataGroupNumber;
    ASN1OctetString dataGroupHashValue;

    public DataGroupHash(ASN1Sequence seq) {
        Enumeration e = seq.getObjects();

        // dataGroupNumber
        dataGroupNumber = DERInteger.getInstance(e.nextElement());
        // dataGroupHashValue
        dataGroupHashValue = ASN1OctetString.getInstance(e.nextElement());
    }

    public DataGroupHash(
            int dataGroupNumber,
            ASN1OctetString dataGroupHashValue) {
        this.dataGroupNumber = new DERInteger(dataGroupNumber);
        this.dataGroupHashValue = dataGroupHashValue;
    }

    public static DataGroupHash getInstance(
            Object obj) {
        if (obj == null || obj instanceof DataGroupHash) {
            return (DataGroupHash) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new DataGroupHash(ASN1Sequence.getInstance(obj));
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    public int getDataGroupNumber() {
        return dataGroupNumber.getValue().intValue();
    }

    public ASN1OctetString getDataGroupHashValue() {
        return dataGroupHashValue;
    }

    public DERObject toASN1Object() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.add(dataGroupNumber);
        seq.add(dataGroupHashValue);

        return new DERSequence(seq);
    }
}
