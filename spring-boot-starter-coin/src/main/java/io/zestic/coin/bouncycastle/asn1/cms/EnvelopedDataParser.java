package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.*;

import java.io.IOException;

/**
 * EnvelopedData ::= SEQUENCE {
 * version CMSVersion,
 * originatorInfo [0] IMPLICIT OriginatorInfo OPTIONAL,
 * recipientInfos RecipientInfos,
 * encryptedContentInfo EncryptedContentInfo,
 * unprotectedAttrs [1] IMPLICIT UnprotectedAttributes OPTIONAL
 * }
 */
public class EnvelopedDataParser {

    private final ASN1SequenceParser _seq;
    private final DERInteger _version;
    private DEREncodable _nextObject;
    private boolean _originatorInfoCalled;

    public EnvelopedDataParser(
            ASN1SequenceParser seq)
            throws IOException {
        this._seq = seq;
        this._version = (DERInteger) seq.readObject();
    }

    public DERInteger getVersion() {
        return _version;
    }

    public OriginatorInfo getOriginatorInfo()
            throws IOException {
        _originatorInfoCalled = true;

        if (_nextObject == null) {
            _nextObject = _seq.readObject();
        }

        if (_nextObject instanceof ASN1TaggedObjectParser && ((ASN1TaggedObjectParser) _nextObject).getTagNo() == 0) {
            ASN1SequenceParser originatorInfo = (ASN1SequenceParser) ((ASN1TaggedObjectParser) _nextObject).getObjectParser(DERTags.SEQUENCE, false);
            _nextObject = null;
            return OriginatorInfo.getInstance(originatorInfo.getDERObject());
        }

        return null;
    }

    public ASN1SetParser getRecipientInfos()
            throws IOException {
        if (!_originatorInfoCalled) {
            getOriginatorInfo();
        }

        if (_nextObject == null) {
            _nextObject = _seq.readObject();
        }

        ASN1SetParser recipientInfos = (ASN1SetParser) _nextObject;
        _nextObject = null;
        return recipientInfos;
    }

    public EncryptedContentInfoParser getEncryptedContentInfo()
            throws IOException {
        if (_nextObject == null) {
            _nextObject = _seq.readObject();
        }

        if (_nextObject != null) {
            ASN1SequenceParser o = (ASN1SequenceParser) _nextObject;
            _nextObject = null;
            return new EncryptedContentInfoParser(o);
        }

        return null;
    }

    public ASN1SetParser getUnprotectedAttrs()
            throws IOException {
        if (_nextObject == null) {
            _nextObject = _seq.readObject();
        }

        if (_nextObject != null) {
            DEREncodable o = _nextObject;
            _nextObject = null;
            return (ASN1SetParser) ((ASN1TaggedObjectParser) o).getObjectParser(DERTags.SET, false);
        }

        return null;
    }
}
