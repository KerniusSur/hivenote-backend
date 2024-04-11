package com.hivenote.backend.account.util;

import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.passay.DigestDictionaryRule.ERROR_CODE;

@Service
public class PasswordUtil {
    private final PasswordGenerator generator;
    private final CharacterData specialChars = new CharacterData() {
        public String getErrorCode() {
            return ERROR_CODE;
        }

        public String getCharacters() {
            return "-_*";
        }
    };
    private final List<CharacterRule> rules = List.of(
            new CharacterRule(EnglishCharacterData.LowerCase),
            new CharacterRule(EnglishCharacterData.UpperCase),
            new CharacterRule(EnglishCharacterData.Digit),
            new CharacterRule(specialChars)
    );

    PasswordUtil(PasswordGenerator generator) {
        this.generator = generator;

        for (CharacterRule rule : rules) {
            rule.setNumberOfCharacters(2);
        }
    }

    public String generate(int length) {
        return generator.generatePassword(length, rules);
    }
}
