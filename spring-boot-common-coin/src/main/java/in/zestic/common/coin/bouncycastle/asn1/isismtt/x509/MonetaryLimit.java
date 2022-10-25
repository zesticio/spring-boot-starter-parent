package in.zestic.common.coin.bouncycastle.asn1.isismtt.x509;

import in.zestic.common.coin.bouncycastle.asn1.ASN1EncodableVector;
import in.zestic.common.coin.bouncycastle.asn1.DERInteger;
import in.zestic.common.coin.bouncycastle.asn1.DERPrintableString;
import in.zestic.common.coin.bouncycastle.asn1.DERSequence;
import in.zestic.common.coin.bouncycastle.asn1.ASN1Encodable;
import in.zestic.common.coin.bouncycastle.asn1.ASN1Sequence;
import in.zestic.common.coin.bouncycastle.asn1.DERObject;

import java.math.BigInteger;
import java.util.Enumeration;

/**
 * Monetary limit for transactions. The QcEuMonetaryLimit QC statement MUST be
 * used in new certificates in place of the extension/attribute MonetaryLimit
 * since January 1, 2004. For the sake of backward compatibility with
 * certificates already in use, components SHOULD support MonetaryLimit (as well
 * as QcEuLimitValue).

 * Indicates a monetary limit within which the certificate holder is authorized
 * to act. (This value DOES NOT express a limit on the liability of the
 * certification authority).
 * MonetaryLimitSyntax ::= SEQUENCE { currency PrintableString (SIZE(3)), amount
 * INTEGER, exponent INTEGER }
 * currency must be the ISO code.

 * value = amount�10*exponent
 */
public class MonetaryLimit
        extends ASN1Encodable {

    DERPrintableString currency;
    DERInteger amount;
    DERInteger exponent;

    public static MonetaryLimit getInstance(Object obj) {
        if (obj == null || obj instanceof MonetaryLimit) {
            return (MonetaryLimit) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new MonetaryLimit(ASN1Sequence.getInstance(obj));
        }

        throw new IllegalArgumentException("unknown object in getInstance");
    }

    private MonetaryLimit(ASN1Sequence seq) {
        if (seq.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: "
                    + seq.size());
        }
        Enumeration e = seq.getObjects();
        currency = DERPrintableString.getInstance(e.nextElement());
        amount = DERInteger.getInstance(e.nextElement());
        exponent = DERInteger.getInstance(e.nextElement());
    }

    /**
     * Constructor from a given details.


     * value = amount�10^exponent
     *
     * @param currency The currency. Must be the ISO code.
     * @param amount The amount
     * @param exponent The exponent
     */
    public MonetaryLimit(String currency, int amount, int exponent) {
        this.currency = new DERPrintableString(currency, true);
        this.amount = new DERInteger(amount);
        this.exponent = new DERInteger(exponent);
    }

    public String getCurrency() {
        return currency.getString();
    }

    public BigInteger getAmount() {
        return amount.getValue();
    }

    public BigInteger getExponent() {
        return exponent.getValue();
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.

     * Returns:

     * MonetaryLimitSyntax ::= SEQUENCE { currency PrintableString (SIZE(3)),
     * amount INTEGER, exponent INTEGER }
     *
     * @return a DERObject
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector seq = new ASN1EncodableVector();
        seq.add(currency);
        seq.add(amount);
        seq.add(exponent);

        return new DERSequence(seq);
    }

}
