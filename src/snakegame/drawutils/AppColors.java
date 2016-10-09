package snakegame.drawutils;

import java.awt.*;

/**
 * Created by Gilbert on 09-10-16.
 */
public final class AppColors {

    public static Color randomGrey() {
        int rValue = (int)(Math.random() * 50) + 50;
        return new Color(rValue, rValue, rValue);
    }

}
