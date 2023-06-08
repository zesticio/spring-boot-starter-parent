package io.zestic.coin.bouncycastle.asn1.cms;

import io.zestic.coin.bouncycastle.asn1.ASN1SequenceParser;
import io.zestic.coin.bouncycastle.asn1.ASN1TaggedObjectParser;
import io.zestic.coin.bouncycastle.asn1.DEREncodable;
import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;

import java.io.IOException;

/**
 * Produce an object suitable for an ASN1OutputStream.
 * ContentInfo ::= SEQUENCE {
 * contentType ContentType,
 * content
 * [0] EXPLICIT ANY DEFINED BY contentType OPTIONAL
 * }
 */
public class ContentInfoParser {

    private final DERObjectIdentifier contentType;
    private final ASN1TaggedObjectParser content;

    public ContentInfoParser(
            ASN1SequenceParser seq)
            throws IOException {
        contentType = (DERObjectIdentifier) seq.readObject();
        content = (ASN1TaggedObjectParser) seq.readObject();
    }

    public DERObjectIdentifier getContentType() {
        return contentType;
    }

    public DEREncodable getContent(
            int tag)
            throws IOException {
        if (content != null) {
            return content.getObjectParser(tag, true);
        }

        return null;
    }
}
