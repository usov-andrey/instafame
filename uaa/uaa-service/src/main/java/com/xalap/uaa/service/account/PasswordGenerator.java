/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.account;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.springframework.stereotype.Component;

/**
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Component
public class PasswordGenerator {

    public String generateWithoutSpecial() {
        return new org.passay.PasswordGenerator()
                .generatePassword(8, lowerCaseRule(),
                        upperCaseRule(), digitRule());
    }

    public String generatePassword() {
        return new org.passay.PasswordGenerator()
                .generatePassword(8, specialCharsRule(), lowerCaseRule(),
                        upperCaseRule(), digitRule());
    }

    private CharacterRule lowerCaseRule() {
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);
        return lowerCaseRule;
    }

    private CharacterRule upperCaseRule() {
        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);
        return upperCaseRule;
    }

    private CharacterRule digitRule() {
        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);
        return digitRule;
    }

    private CharacterRule specialCharsRule() {
        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return "ERRONEOUS_SPECIAL_CHARS";
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);
        return splCharRule;
    }

}
