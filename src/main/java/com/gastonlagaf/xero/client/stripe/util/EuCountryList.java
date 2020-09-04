package com.gastonlagaf.xero.client.stripe.util;

import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class EuCountryList {

    private final Set<String> set = Set.of("AD", "AT", "BE", "BG", "HR", "CY", "CZ", "DK", "EE", "FO", "FI", "FR", "DE",
            "GI", "GR", "GL", "GG", "VA", "HU", "IS", "IE", "IM", "IL", "IT", "JE", "LV", "LI", "LT", "LU", "MT", "MC",
            "NL", "NO", "PL", "PT", "RO", "PM", "SM", "SK", "SI", "ES", "SE", "TR", "GB");

    public boolean contains(String countryCode) {
        return set.contains(countryCode.toUpperCase());
    }

}
