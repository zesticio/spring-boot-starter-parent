package io.zestic.coin.bouncycastle.asn1.x509.sigi;

import io.zestic.coin.bouncycastle.asn1.*;
import io.zestic.coin.bouncycastle.asn1.x500.DirectoryString;

import java.util.Enumeration;

/**
 * Structure for a name or pseudonym.
 * <p>
 * NameOrPseudonym ::= CHOICE {
 * surAndGivenName SEQUENCE {
 * surName DirectoryString,
 * givenName SEQUENCE OF DirectoryString
 * },
 * pseudonym DirectoryString
 * } *
 *
 * @see PersonalData
 */
public class NameOrPseudonym
        extends ASN1Encodable
        implements ASN1Choice {

    private DirectoryString pseudonym;

    private DirectoryString surname;

    private ASN1Sequence givenName;

    public static NameOrPseudonym getInstance(Object obj) {
        if (obj == null || obj instanceof NameOrPseudonym) {
            return (NameOrPseudonym) obj;
        }

        if (obj instanceof DERString) {
            return new NameOrPseudonym(DirectoryString.getInstance(obj));
        }

        if (obj instanceof ASN1Sequence) {
            return new NameOrPseudonym((ASN1Sequence) obj);
        }

        throw new IllegalArgumentException("illegal object in getInstance: "
                + obj.getClass().getName());
    }

    /**
     * Constructor from DERString.
     * <p>
     * The sequence is of type NameOrPseudonym:
     * <p>
     * NameOrPseudonym ::= CHOICE { surAndGivenName SEQUENCE { surName
     * DirectoryString, givenName SEQUENCE OF DirectoryString }, pseudonym
     * DirectoryString }
     *
     * @param pseudonym pseudonym value to use.
     */
    public NameOrPseudonym(DirectoryString pseudonym) {
        this.pseudonym = pseudonym;
    }

    /**
     * Constructor from ASN1Sequence.
     * <p>
     * The sequence is of type NameOrPseudonym:
     * <p>
     * NameOrPseudonym ::= CHOICE { surAndGivenName SEQUENCE { surName
     * DirectoryString, givenName SEQUENCE OF DirectoryString }, pseudonym
     * DirectoryString }
     *
     * @param seq The ASN.1 sequence.
     */
    private NameOrPseudonym(ASN1Sequence seq) {
        if (seq.size() != 2) {
            throw new IllegalArgumentException("Bad sequence size: "
                    + seq.size());
        }

        if (!(seq.getObjectAt(0) instanceof DERString)) {
            throw new IllegalArgumentException("Bad object encountered: "
                    + seq.getObjectAt(0).getClass());
        }

        surname = DirectoryString.getInstance(seq.getObjectAt(0));
        givenName = ASN1Sequence.getInstance(seq.getObjectAt(1));
    }

    /**
     * Constructor from a given details.
     *
     * @param pseudonym The pseudonym.
     */
    public NameOrPseudonym(String pseudonym) {
        this(new DirectoryString(pseudonym));
    }

    /**
     * Constructor from a given details.
     *
     * @param surname   The surname.
     * @param givenName A sequence of directory strings making up the givenName
     */
    public NameOrPseudonym(DirectoryString surname, ASN1Sequence givenName) {
        this.surname = surname;
        this.givenName = givenName;
    }

    public DirectoryString getPseudonym() {
        return pseudonym;
    }

    public DirectoryString getSurname() {
        return surname;
    }

    public DirectoryString[] getGivenName() {
        DirectoryString[] items = new DirectoryString[givenName.size()];
        int count = 0;
        for (Enumeration e = givenName.getObjects(); e.hasMoreElements(); ) {
            items[count++] = DirectoryString.getInstance(e.nextElement());
        }
        return items;
    }

    /**
     * Produce an object suitable for an ASN1OutputStream.
     * <p>
     * Returns:
     * <p>
     * NameOrPseudonym ::= CHOICE { surAndGivenName SEQUENCE { surName
     * DirectoryString, givenName SEQUENCE OF DirectoryString }, pseudonym
     * DirectoryString }
     *
     * @return a DERObject
     */
    public DERObject toASN1Object() {
        if (pseudonym != null) {
            return pseudonym.toASN1Object();
        } else {
            ASN1EncodableVector vec1 = new ASN1EncodableVector();
            vec1.add(surname);
            vec1.add(givenName);
            return new DERSequence(vec1);
        }
    }
}
