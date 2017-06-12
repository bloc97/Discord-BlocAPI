/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package container;

import helpers.ParserUtils;
import helpers.OtherUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bowen
 * @param <T>
 */
public abstract class Container<T> {
    protected int index = 0;
    protected int reverseIndex = 0;
    private ArrayList<T> content = new ArrayList();
    
    private final String rawString;
    private final String trimmedString;
    private final String prefix;
    private final String suffix;
    
    
    protected Container(String rawString, String trimmedString, String prefix, String suffix) {
        this.rawString = rawString;
        this.prefix = prefix;
        this.suffix = suffix;
        this.trimmedString = trimmedString;
        
    }
    protected Container(String rawString, PrefixSuffixCombo prefixSuffix) {
        this.index = 0;
        this.reverseIndex = 0;
        this.rawString = rawString;
        
        if (prefixSuffix.check(rawString)) {
            this.prefix = prefixSuffix.getPrefix(rawString);
            this.suffix = prefixSuffix.getSuffix(rawString);
            trimmedString = ParserUtils.trimPrefixSuffixLength(rawString, prefix.length(), suffix.length());
        } else {
            this.prefix = "";
            this.suffix = "";
            trimmedString = rawString;
        }
    }
    protected Container(String rawString, List<PrefixSuffixCombo> prefixSuffixList) {
        this.index = 0;
        this.reverseIndex = 0;
        this.rawString = rawString;
        
        PrefixSuffixCombo foundCombo = null;
        for (PrefixSuffixCombo prefixSuffix : prefixSuffixList) { //Finds a valid PrefixSuffixCombo
            if (prefixSuffix.check(rawString)) {
                foundCombo = prefixSuffix;
            }
        }
        if (foundCombo != null) {
            this.prefix = foundCombo.getPrefix(rawString);
            this.suffix = foundCombo.getSuffix(rawString);
            trimmedString = ParserUtils.trimPrefixSuffixLength(rawString, prefix.length(), suffix.length());
        } else {
            this.prefix = "";
            this.suffix = "";
            trimmedString = rawString;
        }
    }
    
    public String getRawString() {
        return rawString;
    }
    
    public String getTrimmedString() {
        return trimmedString;
    }
    @Override
    public String toString() {
        return rawString;
    }
    
    protected void setContent(List<T> content) {
        this.content = new ArrayList(content);
        reset();
    }
    
    public int getCurrentIndex() {
        return index;
    }
    public int reverseGetCurrentIndex() {
        return reverseIndex;
    }
    public int size() {
        return content.size();
    }
    public int remainingSize() {
        /*if (index > reverseIndex) {
            return 0;
        }*/
        return reverseIndex - index + 1;
    }
    public void reset() {
        index = 0;
        reverseIndex = size() - 1;
    }
    public abstract T getEmptyContent();
    public T get(int i) {
        if (isOob(i)) {
            return getEmptyContent();
        }
        return content.get(i);
    }
    public T get(int i, boolean respectBounds) {
        if (respectBounds) {
            if (isBoundedByIndexes(i)) {
                return get(i);
            } else {
                return getEmptyContent();
            }
        } else {
            return get(i);
        }
    }
    public T get() {
        return get(index, true);
    }
    public T reverseGet() {
        return get(reverseIndex, true);
    }
    public T getNext() {
        return get(index+1, true);
    }
    public T reverseGetNext() {
        return get(reverseIndex-1, true);
    }
    public List<T> getContent() {
        return new ArrayList(content);
    }
    public T[] getContentAsArray() {
        return (T[]) getContent().toArray();
    }
    public List<T> getRemainingContent() {
        if (index > reverseIndex) {
            return new ArrayList();
        }
        
        int i = OtherUtils.boundExcludeMax(this.index, 0, size());
        int ri = OtherUtils.boundExcludeMax(this.reverseIndex, 0, size());
        return content.subList(i, ri+1);
    }
    public T[] getRemainingContentAsArray() {
        return (T[]) getRemainingContent().toArray();
    }
    public boolean hasNext() {
        return isBoundedByIndexes(index+1);
    }
    public boolean reverseHasNext() {
        return isBoundedByIndexes(reverseIndex-1);
    }
    public boolean isOob(int i) {
        return i >= size() || i < 0;
    }
    public boolean isBoundedByIndexes(int i) {
        return !isOob(i) && i >= index && i <= reverseIndex;
    }
    public void next() {
        index++;
    }
    public void previous() {
        index--;
    }
    public void reverseNext() {
        reverseIndex--;
    }
    public void reversePrevious() {
        reverseIndex++;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getSuffix() {
        return suffix;
    }
    
}

