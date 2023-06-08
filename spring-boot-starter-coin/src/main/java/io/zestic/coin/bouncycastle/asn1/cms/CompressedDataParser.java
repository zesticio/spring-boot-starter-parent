package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.ASN1SequenceParser;
import io.zestic.coin.bouncycastle.asn1.DERInteger;
import io.zestic.coin.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.io.IOException;

/**
 * RFC 3274 - CMS Compressed Data.
 * CompressedData ::= SEQUENCE {
 * version CMSVersion,
 * compressionAlgorithm CompressionAlgorithmIdentifier,
 * encapContentInfo EncapsulatedContentInfo
 * }
 */
public class CompressedDataParser {

    private final DERInteger _version;
    private final AlgorithmIdentifier _compressionAlgorithm;
    private final ContentInfoParser _encapContentInfo;

    public CompressedDataParser(
            ASN1SequenceParser seq)
            throws IOException {
        this._version = (DERInteger) seq.readObject();
        this._compressionAlgorithm = AlgorithmIdentifier.getInstance(seq.readObject().getDERObject());
        this._encapContentInfo = new ContentInfoParser((ASN1SequenceParser) seq.readObject());
    }

    public DERInteger getVersion() {
        return _version;
    }

    public AlgorithmIdentifier getCompressionAlgorithmIdentifier() {
        return _compressionAlgorithm;
    }

    public ContentInfoParser getEncapContentInfo() {
        return _encapContentInfo;
    }
}
