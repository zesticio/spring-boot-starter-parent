package io.zestic.coin.bouncycastle.asn1.pkcs;

import io.zestic.coin.bouncycastle.asn1.*;

import java.util.Enumeration;

public class ContentInfo
        extends ASN1Encodable
        implements PKCSObjectIdentifiers {

    private final DERObjectIdentifier contentType;
    private DEREncodable content;

    public static ContentInfo getInstance(
            Object obj) {
        if (obj instanceof ContentInfo) {
            return (ContentInfo) obj;
        } else if (obj instanceof ASN1Sequence) {
            return new ContentInfo((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public ContentInfo(
            ASN1Sequence seq) {
        Enumeration e = seq.getObjects();

        contentType = (DERObjectIdentifier) e.nextElement();

        if (e.hasMoreElements()) {
            content = ((DERTaggedObject) e.nextElement()).getObject();
        }
    }

    public ContentInfo(
            DERObjectIdentifier contentType,
            DEREncodable content) {
        this.contentType = contentType;
        this.content = content;
    }

    public DERObjectIdentifier getContentType() {
        return contentType;
    }

    public DEREncodable getContent() {
        return content;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * ContentInfo ::= SEQUENCE {
     *          contentType ContentType,
     *          content
     *          [0] EXPLICIT ANY DEFINED BY contentType OPTIONAL }
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector v = new ASN1EncodableVector();

        v.add(contentType);

        if (content != null) {
            v.add(new BERTaggedObject(0, content));
        }

        return new BERSequence(v);
    }
}
