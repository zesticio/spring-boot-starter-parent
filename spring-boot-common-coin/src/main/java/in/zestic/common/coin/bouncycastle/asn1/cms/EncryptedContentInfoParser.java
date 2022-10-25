package in.zestic.common.coin.bouncycastle.asn1.cms;

import in.zestic.common.coin.bouncycastle.asn1.ASN1SequenceParser;
import in.zestic.common.coin.bouncycastle.asn1.ASN1TaggedObjectParser;
import in.zestic.common.coin.bouncycastle.asn1.DEREncodable;
import in.zestic.common.coin.bouncycastle.asn1.DERObjectIdentifier;
import in.zestic.common.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.IOException;

/**
 * EncryptedContentInfo ::= SEQUENCE {
 *     contentType ContentType,
 *     contentEncryptionAlgorithm ContentEncryptionAlgorithmIdentifier,
 *     encryptedContent [0] IMPLICIT EncryptedContent OPTIONAL
 * }
 */
public class EncryptedContentInfoParser {

    private final DERObjectIdentifier _contentType;
    private final AlgorithmIdentifier _contentEncryptionAlgorithm;
    private final ASN1TaggedObjectParser _encryptedContent;

    public EncryptedContentInfoParser(
            ASN1SequenceParser seq)
            throws IOException {
        _contentType = (DERObjectIdentifier) seq.readObject();
        _contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance(seq.readObject().getDERObject());
        _encryptedContent = (ASN1TaggedObjectParser) seq.readObject();
    }

    public DERObjectIdentifier getContentType() {
        return _contentType;
    }

    public AlgorithmIdentifier getContentEncryptionAlgorithm() {
        return _contentEncryptionAlgorithm;
    }

    public DEREncodable getEncryptedContent(
            int tag)
            throws IOException {
        return _encryptedContent.getObjectParser(tag, false);
    }
}
