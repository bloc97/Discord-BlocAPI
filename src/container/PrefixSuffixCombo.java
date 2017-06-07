/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;

/**
 *
 * @author bowen
 */
public class PrefixSuffixCombo {
    
    private final String[] prefixList, suffixList;
    
    /**
     * Creates a Prefix-Suffix Combo that only detects prefixes.
     * @param prefixList
     * List of prefixes to detect.
     */
    public PrefixSuffixCombo(String... prefixList) {
        this(prefixList, new String[0]);
    }
    /**
     * Creates a Prefix-Suffix Combo that detects prefixes, with suffix mirroring option.
     * @param isBoth
     * If true, this PrefixSuffix Combo will detect both prefixes and suffixes using the prefixList
     * @param prefixList
     * List of prefixes to detect, with the option of mirroring to suffixes using isBoth
     */
    public PrefixSuffixCombo(boolean isBoth, String... prefixList) {
        if (!isBoth) {
            this.prefixList = prefixList;
            this.suffixList = new String[0];
        } else {
            this.prefixList = prefixList;
            this.suffixList = prefixList;
        }
    }

    /**
     * Creates a Prefix-Suffix Combo that detects prefixes and suffixes.
     * @param prefixList
     * List of prefixes to detect.
     * @param suffixList
     * List of suffixes to detect.
     */
    public PrefixSuffixCombo(String[] prefixList, String[] suffixList) {
        this.prefixList = prefixList;
        this.suffixList = suffixList;
    }
    
    /**
     * Retrieves the trimmed version of a string.
     * @param string
     * String to be trimmed.
     * @return
     * String that has its suffix and prefix removed.
     */
    public String retrieveTrimmed(String string) {
        return ParserUtils.trimPrefixSuffix(string, prefixList, suffixList);
    }
    
    /**
     * Gets the prefix of the string in the prefix list.
     * @param string
     * @return
     * The prefix of the string. If no prefix was found, returns an empty string.
     */
    public String getPrefix(String string) {
        return ParserUtils.getPrefix(string, prefixList);
    }
    
    /**
     * Gets the suffix of the string in the suffix list.
     * @param string
     * @return
     * The suffix of the string. If no suffix was found, returns an empty string.
     */
    public String getSuffix(String string) {
        return ParserUtils.getSuffix(string, suffixList);
    }
    
    /**
     * Checks if a string contains at least one of the prefixes and one of the suffixes.
     * @param string
     * @return
     * True if Prefix AND Suffix exists.
     */
    public boolean check(String string) {
        return ParserUtils.checkPrefix(string, prefixList) && ParserUtils.checkSuffix(string, suffixList);
    }
    
}
