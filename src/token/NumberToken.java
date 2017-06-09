/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

/**
 *
 * @author bowen
 */
public class NumberToken extends Token<Number> {
    private final boolean useFloating;
    private final Long contentInteger;
    private final Double contentFloating;
    public NumberToken(long l) {
        super(Long.toString(l));
        Long lO = l;
        contentInteger = l;
        contentFloating = lO.doubleValue();
        useFloating = false;
    }
    public NumberToken(double d) {
        super(Double.toString(d));
        Double dO = d;
        contentInteger = dO.longValue();
        contentFloating = d;
        useFloating = true;
    }
    public NumberToken(String string) {
        super(string);
        Long i;
        Double d;
        try {
            i = Long.parseLong(string);
            d = Double.parseDouble(string);
        } catch (NumberFormatException ex) {
            i = 0l;
            d = Double.NaN;
        }
        contentInteger = i;
        contentFloating = d;
        try {
        } catch (NumberFormatException ex) {
        }
        useFloating = !contentInteger.toString().equals(string);
    }

    @Override
    public String getContentAsString() {
        return useFloating ? contentFloating.toString() : contentInteger.toString();
    }

    @Override
    public String getContentAsReadableString() {
        return useFloating ? contentFloating.toString() : contentInteger.toString();
    }

    @Override
    public Number getContent() {
        return new Number() {
            @Override
            public int intValue() {
                return contentInteger.intValue();
            }

            @Override
            public long longValue() {
                return contentInteger;
            }

            @Override
            public float floatValue() {
                return contentFloating.floatValue();
            }

            @Override
            public double doubleValue() {
                return contentFloating;
            }
        };
    }

    @Override
    public String getTokenType() {
        return "Number";
    }
    
    public static boolean isType(String string) {
        try {
            Double.parseDouble(string);
            Long.parseLong(string);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
}
