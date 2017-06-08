/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import helpers.TextFormatter;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bowen
 */
public class StringContainer extends Container<String> {
    
    /*
    private static StringContainer lastStringContainer = null;
    protected StringContainer(String rawString, String trimmedString, List<String> content, String prefix, String suffix) {
        super(rawString, trimmedString, prefix, suffix);
        setContent(content);
    }
    protected StringContainer(int index, int reverseIndex, String rawString, String trimmedString, List<String> content, String prefix, String suffix) {
        this(rawString, trimmedString, content, prefix, suffix);
        this.index = index;
        this.reverseIndex = reverseIndex;
    }
    
    protected StringContainer(String trimmedString, char[] separatorList, String prefix, String suffix) {
        super(prefix + trimmedString + suffix, trimmedString, prefix, suffix);
        if (trimmedString.isEmpty()) {
            return;
        }
        setContent(ParserUtils.tokenizeString(trimmedString, separatorList));
    }*/
    
    public StringContainer(String rawString, ContainerSettings settings) {
        super(rawString, settings.getPrefixSuffixComboList());
        
        String trimmedString = getTrimmedString();
        if (trimmedString.isEmpty()) {
            return;
        }
        setContent(ParserUtils.tokenizeString(trimmedString, settings.getSeparatorList()));
    }
    
    public StringContainer(String rawString, String trimmedString, String prefix, String suffix, List<String> content) {
        super(rawString, trimmedString, prefix, suffix);
        setContent(content);
    }
    public StringContainer(StringContainer container) {
        super(container.getRawString(), container.getTrimmedString(), container.getPrefix(), container.getSuffix());
        setContent(container.getContent());
    }
    
    @Deprecated
    public String getContentAsString() {
        return TextFormatter.join(getContentAsArray(), ' ');
    }
    @Deprecated
    public String getRemainingContentAsString() {
        if (index > reverseIndex) {
            return "";
        }
        return TextFormatter.join(getRemainingContentAsArray(), ' ');
    }
    @Override
    public StringContainer clone() {
        return new StringContainer(this);
    }
    
}
