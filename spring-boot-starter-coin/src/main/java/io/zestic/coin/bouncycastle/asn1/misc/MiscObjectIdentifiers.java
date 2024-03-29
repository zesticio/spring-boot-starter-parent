package io.zestic.coin.bouncycastle.asn1.misc;

import io.zestic.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface MiscObjectIdentifiers {

    //
    // Netscape
    //       iso/itu(2) joint-assign(16) us(840) uscompany(1) netscape(113730) cert-extensions(1) }
    //
    String netscape = "2.16.840.1.113730.1";
    DERObjectIdentifier netscapeCertType = new DERObjectIdentifier(netscape + ".1");
    DERObjectIdentifier netscapeBaseURL = new DERObjectIdentifier(netscape + ".2");
    DERObjectIdentifier netscapeRevocationURL = new DERObjectIdentifier(netscape + ".3");
    DERObjectIdentifier netscapeCARevocationURL = new DERObjectIdentifier(netscape + ".4");
    DERObjectIdentifier netscapeRenewalURL = new DERObjectIdentifier(netscape + ".7");
    DERObjectIdentifier netscapeCApolicyURL = new DERObjectIdentifier(netscape + ".8");
    DERObjectIdentifier netscapeSSLServerName = new DERObjectIdentifier(netscape + ".12");
    DERObjectIdentifier netscapeCertComment = new DERObjectIdentifier(netscape + ".13");
    //
    // Verisign
    //       iso/itu(2) joint-assign(16) us(840) uscompany(1) verisign(113733) cert-extensions(1) }
    //
    String verisign = "2.16.840.1.113733.1";

    //
    // CZAG - country, zip, age, and gender
    //
    DERObjectIdentifier verisignCzagExtension = new DERObjectIdentifier(verisign + ".6.3");
    // D&B D-U-N-S number
    DERObjectIdentifier verisignDnbDunsNumber = new DERObjectIdentifier(verisign + ".6.15");

    //
    // Novell
    //       iso/itu(2) country(16) us(840) organization(1) novell(113719)
    //
    String novell = "2.16.840.1.113719";
    DERObjectIdentifier novellSecurityAttribs = new DERObjectIdentifier(novell + ".1.9.4.1");

    //
    // Entrust
    //       iso(1) member-body(16) us(840) nortelnetworks(113533) entrust(7)
    //
    String entrust = "1.2.840.113533.7";
    DERObjectIdentifier entrustVersionExtension = new DERObjectIdentifier(entrust + ".65.0");
}
