package io.zestic.coin.bouncycastle.asn1.tsp;

import io.zestic.coin.bouncycastle.asn1.*;

public class Accuracy
        extends ASN1Encodable {

    // constantes
    protected static final int MIN_MILLIS = 1;
    protected static final int MAX_MILLIS = 999;
    protected static final int MIN_MICROS = 1;
    protected static final int MAX_MICROS = 999;
    DERInteger seconds;
    DERInteger millis;
    DERInteger micros;

    protected Accuracy() {
    }

    public Accuracy(
            DERInteger seconds,
            DERInteger millis,
            DERInteger micros) {
        this.seconds = seconds;

        //Verifications
        if (millis != null
                && (millis.getValue().intValue() < MIN_MILLIS || millis
                .getValue().intValue() > MAX_MILLIS)) {
            throw new IllegalArgumentException(
                    "Invalid millis field : not in (1..999)");
        } else {
            this.millis = millis;
        }

        if (micros != null
                && (micros.getValue().intValue() < MIN_MICROS || micros
                .getValue().intValue() > MAX_MICROS)) {
            throw new IllegalArgumentException(
                    "Invalid micros field : not in (1..999)");
        } else {
            this.micros = micros;
        }

    }

    public Accuracy(ASN1Sequence seq) {
        seconds = null;
        millis = null;
        micros = null;

        for (int i = 0; i < seq.size(); i++) {
            // seconds
            if (seq.getObjectAt(i) instanceof DERInteger) {
                seconds = (DERInteger) seq.getObjectAt(i);
            } else if (seq.getObjectAt(i) instanceof DERTaggedObject) {
                DERTaggedObject extra = (DERTaggedObject) seq.getObjectAt(i);

                switch (extra.getTagNo()) {
                    case 0:
                        millis = DERInteger.getInstance(extra, false);
                        if (millis.getValue().intValue() < MIN_MILLIS
                                || millis.getValue().intValue() > MAX_MILLIS) {
                            throw new IllegalArgumentException(
                                    "Invalid millis field : not in (1..999).");
                        }
                        break;
                    case 1:
                        micros = DERInteger.getInstance(extra, false);
                        if (micros.getValue().intValue() < MIN_MICROS
                                || micros.getValue().intValue() > MAX_MICROS) {
                            throw new IllegalArgumentException(
                                    "Invalid micros field : not in (1..999).");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalig tag number");
                }
            }
        }
    }

    public static Accuracy getInstance(Object o) {
        if (o == null || o instanceof Accuracy) {
            return (Accuracy) o;
        } else if (o instanceof ASN1Sequence) {
            return new Accuracy((ASN1Sequence) o);
        }

        throw new IllegalArgumentException(
                "Unknown object in 'Accuracy' factory : "
                        + o.getClass().getName() + ".");
    }

    public DERInteger getSeconds() {
        return seconds;
    }

    public DERInteger getMillis() {
        return millis;
    }

    public DERInteger getMicros() {
        return micros;
    }

    /**
     * Accuracy ::= SEQUENCE {
     * seconds        INTEGER              OPTIONAL,
     * millis     [0] INTEGER  (1..999)    OPTIONAL,
     * micros     [1] INTEGER  (1..999)    OPTIONAL
     * }
     */
    public DERObject toASN1Object() {

        ASN1EncodableVector v = new ASN1EncodableVector();

        if (seconds != null) {
            v.add(seconds);
        }

        if (millis != null) {
            v.add(new DERTaggedObject(false, 0, millis));
        }

        if (micros != null) {
            v.add(new DERTaggedObject(false, 1, micros));
        }

        return new DERSequence(v);
    }
}
