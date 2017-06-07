/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

/**
 *
 * @author bowen
 */
public abstract class Colour {
    public static int get4IntFromRGB(int r, int g, int b) {
        r = (r << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
        g = (g << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
        b = b & 0x000000FF; //Mask out anything not blue.

        return 0xFF000000 | r | g | b; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }
    public static int get4IntFromRGB(float r, float g, float b){
        int R = Math.round(255 * r);
        int G = Math.round(255 * g);
        int B = Math.round(255 * b);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return 0xFF000000 | R | G | B;
    }
    public static int get4IntFromRGB(double r, double g, double b){
        return get4IntFromRGB((float)r, (float)g, (float)b);
    }
    public static int get3IntFromRGB(float r, float g, float b){
        int R = Math.round(255 * r);
        int G = Math.round(255 * g);
        int B = Math.round(255 * b);

        R = (R << 16) & 0x00FF0000;
        G = (G << 8) & 0x0000FF00;
        B = B & 0x000000FF;

        return R | G | B;
    }
    public static int get3IntFromRGB(double r, double g, double b){
        return get3IntFromRGB((float)r, (float)g, (float)b);
    }
}
