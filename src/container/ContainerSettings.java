/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author bowen
 */
public interface ContainerSettings {
    
    public static ContainerSettings buildSettings() {
        return buildSettings("");
    }
    public static ContainerSettings buildSettings(String... prefixList) {
        return buildSettings(' ', prefixList);
    }
    public static ContainerSettings buildSettings(char separator, String... prefixList) {
        return buildSettings(new char[] {separator}, prefixList);
    }
    public static ContainerSettings buildSettings(char[] separatorList, String... prefixList) {
        return buildSettings(separatorList, new PrefixSuffixCombo(prefixList));
    }
    public static ContainerSettings buildSettings(char[] separatorList, PrefixSuffixCombo prefixSuffix) {
        return buildSettings(separatorList, Arrays.asList(new PrefixSuffixCombo[] {prefixSuffix}));
    }
    public static ContainerSettings buildSettings(char[] separatorList, List<PrefixSuffixCombo> prefixSuffixList) {
        return new ContainerSettings() {
            @Override
            public List<PrefixSuffixCombo> getPrefixSuffixComboList() {
                return prefixSuffixList;
            }

            @Override
            public char[] getSeparatorList() {
                return separatorList;
            }
        };
    }
    
    public char[] getSeparatorList();
    public List<PrefixSuffixCombo> getPrefixSuffixComboList();
}
