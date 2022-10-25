package in.zestic.common.coin.bouncycastle.asn1.iana;

import in.zestic.common.coin.bouncycastle.asn1.DERObjectIdentifier;

public interface IANAObjectIdentifiers {
    // id-SHA1 OBJECT IDENTIFIER ::=    
    // {iso(1) identified-organization(3) dod(6) internet(1) security(5) mechanisms(5) ipsec(8) isakmpOakley(1)}
    //

    DERObjectIdentifier isakmpOakley = new DERObjectIdentifier("1.3.6.1.5.5.8.1");

    DERObjectIdentifier hmacMD5 = new DERObjectIdentifier(isakmpOakley + ".1");
    DERObjectIdentifier hmacSHA1 = new DERObjectIdentifier(isakmpOakley + ".2");

    DERObjectIdentifier hmacTIGER = new DERObjectIdentifier(isakmpOakley + ".3");

    DERObjectIdentifier hmacRIPEMD160 = new DERObjectIdentifier(isakmpOakley + ".4");

}
