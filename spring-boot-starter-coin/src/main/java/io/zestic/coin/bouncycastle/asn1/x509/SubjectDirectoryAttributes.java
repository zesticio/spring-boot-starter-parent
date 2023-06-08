package io.zestic.coin.bouncycastle.asn1.x509;

import io.zestic.coin.bouncycastle.asn1.*;

import java.util.Enumeration;
import java.util.Vector;

/**
 * This extension may contain further X.500 attributes of the subject. See also
 * RFC 3039.
 * <p>
 * SubjectDirectoryAttributes ::= Attributes
 * Attributes ::= SEQUENCE SIZE (1..MAX) OF Attribute
 * Attribute ::= SEQUENCE
 * {
 * type AttributeType
 * values SET OF AttributeValue
 * }
 * <p>
 * AttributeType ::= OBJECT IDENTIFIER
 * AttributeValue ::= ANY DEFINED BY AttributeType *
 *
 * @see X509Name for AttributeType
 * ObjectIdentifiers.
 */
public class SubjectDirectoryAttributes
        extends ASN1Encodable {

    private final Vector attributes = new Vector();

    /**
     * Constructor from ASN1Sequence.
     * <p>
     * The sequence is of type SubjectDirectoryAttributes:
     * <p>
     * SubjectDirectoryAttributes ::= Attributes
     * Attributes ::= SEQUENCE SIZE (1..MAX) OF Attribute
     * Attribute ::= SEQUENCE
     * {
     * type AttributeType
     * values SET OF AttributeValue
     * }
     * <p>
     * AttributeType ::= OBJECT IDENTIFIER
     * AttributeValue ::= ANY DEFINED BY AttributeType
     *
     * @param seq The ASN.1 sequence.
     */
    public SubjectDirectoryAttributes(ASN1Sequence seq) {
        Enumeration e = seq.getObjects();

        while (e.hasMoreElements()) {
            ASN1Sequence s = ASN1Sequence.getInstance(e.nextElement());
            attributes.addElement(new Attribute(s));
        }
    }

    /**
     * Constructor from a vector of attributes.
     * <p>
     * The vector consists of attributes of type {@link Attribute Attribute}
     *
     * @param attributes The attributes.
     */
    public SubjectDirectoryAttributes(Vector attributes) {
        Enumeration e = attributes.elements();

        while (e.hasMoreElements()) {
            this.attributes.addElement(e.nextElement());
        }
    }

    public static SubjectDirectoryAttributes getInstance(
            Object obj) {
        if (obj == null || obj instanceof SubjectDirectoryAttributes) {
            return (SubjectDirectoryAttributes) obj;
        }

        if (obj instanceof ASN1Sequence) {
            return new SubjectDirectoryAttributes((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * <p>
     * Returns:
     * <p>
     * SubjectDirectoryAttributes ::= Attributes
     * Attributes ::= SEQUENCE SIZE (1..MAX) OF Attribute
     * Attribute ::= SEQUENCE
     * {
     * type AttributeType
     * values SET OF AttributeValue
     * }
     * <p>
     * AttributeType ::= OBJECT IDENTIFIER
     * AttributeValue ::= ANY DEFINED BY AttributeType
     *
     * @return a DERObject
     */
    public DERObject toASN1Object() {
        ASN1EncodableVector vec = new ASN1EncodableVector();
        Enumeration e = attributes.elements();

        while (e.hasMoreElements()) {

            vec.add((Attribute) e.nextElement());
        }

        return new DERSequence(vec);
    }

    /**
     * @return Returns the attributes.
     */
    public Vector getAttributes() {
        return attributes;
    }
}
