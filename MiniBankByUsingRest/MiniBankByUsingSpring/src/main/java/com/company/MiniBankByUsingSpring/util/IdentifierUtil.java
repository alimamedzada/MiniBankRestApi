package com.company.MiniBankByUsingSpring.util;

import java.math.BigInteger;
import java.util.UUID;

public class IdentifierUtil {

    public static String generateId(int length) {
        UUID uuid = UUID.randomUUID();
        String uuidToInteger = uuid.toString().replace("-", "").toUpperCase();
        BigInteger result = new BigInteger(uuidToInteger, 16);

        return result.toString().substring(0, length - 1);
    }

    public static String generateAzerbaijanIBANId() {
        String countryCode = "AZ";
        String checkDigits = "32";
        String bankCode = "MNBNK";
        String accountCode = generateId(20);

        return countryCode + checkDigits + bankCode + accountCode;
    }

}
