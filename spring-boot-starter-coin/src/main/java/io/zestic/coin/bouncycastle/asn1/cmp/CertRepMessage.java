package io.zestic.coin.bouncycastle.asn1.cmp;

import io.zestic.coin.bouncycastle.asn1.*;

public class CertRepMessage extends ASN1Encodable {

    private final ASN1Sequence response;
    private ASN1Sequence caPubs;

    private CertRepMessage(ASN1Sequence seq) {
        int index = 0;

        if (seq.size() > 1) {
            caPubs = ASN1Sequence.getInstance((ASN1TaggedObject) seq.getObjectAt(index++), true);
        }

        response = ASN1Sequence.getInstance(seq.getObjectAt(index));
    }

    public static CertRepMessage getInstance(Object o) {
        if (o instanceof CertRepMessage) {
            return (CertRepMessage) o;
        }

        if (o instanceof ASN1Sequence) {
            return new CertRepMessage((ASN1Sequence) o);
        }

        throw new IllegalArgumentException("Invalid object: " + o.getClass().getName());
    }

    public CMPCertificate[] getCaPubs() {
        if (caPubs == null) {
            return null;
        }

        CMPCertificate[] results = new CMPCertificate[caPubs.size()];

        for (int i = 0; i != results.length; i++) {
            results[i] = CMPCertificate.getInstance(caPubs.getObjectAt(i));
        }

        return results;
    }

    public CertResponse[] getResponse() {
        CertResponse[] results = new CertResponse[response.size()];

        for (int i = 0; i != results.length; i++) {
            results[i] = CertResponse.getInstance(response.getObjectAt(i));
        }

        return results;
    }

    /**
     * CertRepMessage ::= SEQUENCE {
     * caPubs       [1] SEQUENCE SIZE (1..MAX) OF CMPCertificate
     * OPTIONAL,
     * response         SEQUENCE OF CertResponse
     * }
     *
     * @return a basic ASN.1 object representation.
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        if (caPubs != null) {
            v.add(new DERTaggedObject(true, 1, caPubs));
        }

        v.add(response);

        return new DERSequence(v);
    }
}
