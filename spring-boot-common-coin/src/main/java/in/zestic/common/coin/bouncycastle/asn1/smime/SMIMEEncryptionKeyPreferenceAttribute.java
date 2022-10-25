package in.zestic.common.coin.bouncycastle.asn1.smime;

import in.zestic.common.coin.bouncycastle.asn1.ASN1OctetString;
import in.zestic.common.coin.bouncycastle.asn1.DERSet;
import in.zestic.common.coin.bouncycastle.asn1.cms.Attribute;
import in.zestic.common.coin.bouncycastle.asn1.DERTaggedObject;
import in.zestic.common.coin.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import in.zestic.common.coin.bouncycastle.asn1.cms.RecipientKeyIdentifier;

/**
 * The SMIMEEncryptionKeyPreference object.
 * SMIMEEncryptionKeyPreference ::= CHOICE {
 *     issuerAndSerialNumber   [0] IssuerAndSerialNumber,
 *     receipentKeyId          [1] RecipientKeyIdentifier,
 *     subjectAltKeyIdentifier [2] SubjectKeyIdentifier
 * } */
public class SMIMEEncryptionKeyPreferenceAttribute
        extends Attribute {

    public SMIMEEncryptionKeyPreferenceAttribute(
            IssuerAndSerialNumber issAndSer) {
        super(SMIMEAttributes.encrypKeyPref,
                new DERSet(new DERTaggedObject(false, 0, issAndSer)));
    }

    public SMIMEEncryptionKeyPreferenceAttribute(
            RecipientKeyIdentifier rKeyId) {

        super(SMIMEAttributes.encrypKeyPref,
                new DERSet(new DERTaggedObject(false, 1, rKeyId)));
    }

    /**
     * @param sKeyId the subjectKeyIdentifier value (normally the X.509 one)
     */
    public SMIMEEncryptionKeyPreferenceAttribute(
            ASN1OctetString sKeyId) {

        super(SMIMEAttributes.encrypKeyPref,
                new DERSet(new DERTaggedObject(false, 2, sKeyId)));
    }
}
