package com.geekersjoel237.weline.iam.domain.vo;

import com.geekersjoel237.weline.shared.domain.exceptions.CustomIllegalArgumentException;

import java.util.regex.Pattern;

/**
 * Created on 04/10/2025
 *
 * @author Geekers_Joel237
 **/

/*
## 💡 Les Règles d'un Numéro Camerounais
Longueur : Il est composé de NEUF chiffres.

Préfixe Mobile : Les 9 chiffres commencent par un 6.

Préfixe Fixe : Les 9 chiffres commencent par un 2.

Format International : Il peut être précédé de +237 ou 237.

L'objectif de notre Value Object sera de valider ces règles et
de stocker le numéro dans un format unique et standard (canonique),
ici le format international E.164 : +237....
*/
public final class PhoneNumber {

    private static final Pattern CAMEROON_PHONE_PATTERN = Pattern.compile("^\\+237[26]\\d{8}$");
    private final String value;

    public PhoneNumber(String value) throws CustomIllegalArgumentException {
        if (value == null || value.isBlank()) {
            throw new CustomIllegalArgumentException("Veuillez renseigner votre numéro de téléphone.");
        }


        String normalized = this.normalize(value.trim());

        if (!CAMEROON_PHONE_PATTERN.matcher(normalized).matches()) {
            throw new CustomIllegalArgumentException("Votre numéro de téléphone ne respecte pas le format Camerounais: " + value);
        }
        this.value = normalized;
    }


    private String normalize(String value) {
        String sanitized = value.replaceAll("\\s", "");
        if (sanitized.startsWith("+237")) return sanitized;
        if (sanitized.startsWith("237")) return "+" + sanitized;
        if (sanitized.length() == 9) return "+237" + sanitized;
        return sanitized;
    }

    public String value() {
        return this.value;
    }
}
