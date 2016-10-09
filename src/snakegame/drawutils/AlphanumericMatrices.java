package snakegame.drawutils;

import snakegame.model.ColorPoint;

import java.util.LinkedList;

import static snakegame.drawutils.AppColors.randomGrey;

final public class AlphanumericMatrices {
    final static private int[][] NUMBER_LIST = {
        /* 0 */
        {0, 1, 0,
         1, 0, 1,
         1, 0, 1,
         1, 0, 1,
         0, 1, 0},
        /* 1 */
        {0, 1, 0,
         1, 1, 0,
         0, 1, 0,
         0, 1, 0,
         1, 1, 1},
        /* 2 */
        {1, 1, 0,
         0, 0, 1,
         1, 1, 1,
         1, 0, 0,
         1, 1, 1},
        /* 3 */
        {1, 1, 1,
         0, 0, 1,
         1, 1, 1,
         0, 0, 1,
         1, 1, 1},
        /* 4 */
        {1, 0, 1,
         1, 0, 1,
         0, 1, 1,
         0, 0, 1,
         0, 0, 1},
        /* 5 */
        {1, 1, 1,
         1, 0, 0,
         1, 1, 1,
         0, 0, 1,
         1, 1, 0},
        /* 6 */
        {0, 1, 1,
         1, 0, 0,
         1, 1, 1,
         1, 0, 1,
         1, 1, 1},
        /* 7 */
        {1, 1, 1,
         0, 0, 1,
         0, 0, 1,
         0, 1, 0,
         0, 1, 0},
        /* 8 */
        {0, 1, 1,
         1, 0, 1,
         0, 1, 0,
         1, 0, 1,
         1, 1, 0},
        /* 9 */
        {0, 1, 1,
         1, 0, 1,
         1, 1, 1,
         0, 0, 1,
         0, 1, 0}
    };

    final static private int[][] LETTER_LIST = {
        /* 0 : A */
        {0, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 1,
         1, 0, 0, 0, 1},
        /* 1 : B */
        {1, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 0},
        /* 2 : C */
        {0, 1, 1, 1, 1,
         1, 0, 0, 0, 0,
         1, 0, 0, 0, 0,
         1, 0, 0, 0, 0,
         0, 1, 1, 1, 1},
        /* 3 : D */
        {1, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 0},
        /* 4 : E */
        {1, 1, 1, 1, 1,
         1, 0, 0, 0, 0,
         1, 1, 1, 1, 0,
         1, 0, 0, 0, 0,
         1, 1, 1, 1, 1},
        /* 5 : F */
        {1, 1, 1, 1, 1,
         1, 0, 0, 0, 0,
         1, 1, 1, 1, 0,
         1, 0, 0, 0, 0,
         1, 0, 0, 0, 0},
        /* 6 : G */
        {0, 1, 1, 1, 0,
         1, 0, 0, 0, 0,
         1, 0, 0, 1, 1,
         1, 0, 0, 0, 1,
         0, 1, 1, 1, 0},
        /* 7 : H */
        {1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1},
        /* 8 : I */
        {0, 1, 1, 1, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 1, 1, 1, 0},
        /* 9 : J */
        {0, 1, 1, 1, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 1, 1, 0, 0},
        /* 10 : K */
        {1, 0, 0, 0, 1,
         1, 0, 1, 1, 0,
         1, 1, 0, 0, 0,
         1, 0, 1, 1, 0,
         1, 0, 0, 0, 1},
        /* 11 : L */
        {1, 0, 0, 0, 0,
         1, 0, 0, 0, 0,
         1, 0, 0, 0, 0,
         1, 0, 0, 0, 0,
         1, 1, 1, 1, 0},
        /* 12 : M */
        {1, 0, 0, 0, 1,
         1, 1, 0, 1, 1,
         1, 0, 1, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1},
        /* 13 : N */
        {1, 0, 0, 0, 1,
         1, 1, 0, 0, 1,
         1, 0, 1, 0, 1,
         1, 0, 0, 1, 1,
         1, 0, 0, 0, 1},
        /* 14 : O */
        {0, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         0, 1, 1, 1 ,0},
        /* 15 : P */
        {1, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 0,
         1, 0, 0, 0, 0},
        /* 16 : Q */
        {0, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 1, 0,
         0, 1, 1, 0, 1},
        /* 17 : R */
        {1, 1, 1, 1, 0,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 1, 1, 1, 0,
         1, 0, 0, 0, 1},
        /* 18 : S */
        {0, 1, 1, 1, 1,
         1, 0, 0, 0, 0,
         0, 1, 1, 1, 0,
         0, 0, 0, 0, 1,
         1, 1, 1, 1, 0},
        /* 19 : T */
        {1, 1, 1, 1, 1,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0},
        /* 20 : U */
        {1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         0, 1, 1, 1, 0},
        /* 21 : V */
        {1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         0, 1, 0, 1, 0,
         0, 0, 1, 0, 0},
        /* 22 : W */
        {1, 0, 0, 0, 1,
         1, 0, 0, 0, 1,
         1, 0, 1, 0, 1,
         1, 0, 1, 0, 1,
         0, 1, 0, 1, 0},
        /* 23 : X */
        {1, 0, 0, 0, 1,
         0, 1, 0, 1, 0,
         0, 0, 1, 0, 0,
         0, 1, 0, 1, 0,
         1, 0, 0, 0, 1},
        /* 24 : Y */
        {1, 0, 0, 0, 1,
         0, 1, 0, 1, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0,
         0, 0, 1, 0, 0},
        /* 25 : Z */
        {1, 1, 1, 1, 1,
         0, 0, 0, 1, 0,
         0, 0, 1, 0, 0,
         0, 1, 0, 0, 0,
         1, 1, 1, 1, 1},
        /* 26 : SPACE */
        {2},
        /* 27 : NEW LINE */
        {3},
        /* 28 : LEFT PARENTHESIS */
        {0, 1, 0,
         1, 0, 0,
         1, 0, 0,
         1, 0, 0,
         0, 1, 0},
        /*¨29 : RIGHT PARENTHESIS */
        {0, 1, 0,
         0, 0, 1,
         0, 0, 1,
         0, 0, 1,
         0, 1, 0},
        /* 30 : COMMA */
        {0, 0, 0,
         0, 0, 0,
         0, 0, 0,
         0, 1, 0,
         0, 1, 0,
         1, 0, 0},
        /* 31 : DOT */
        {0, 0, 0,
         0, 0, 0,
         0, 0, 0,
         0, 0, 0,
         0, 1, 0}
    };

    /*
    Returns an array of matrices corresponding to a given string
    ------------------------------------------------------------

    Does not render lowercase letters and special characters such as
    accentuated latin letters
    */
    private static int[][] getMessageMatrix(String message) {
        int[][] outputArray = new int[message.length()][];
        int i = 0;

        for (char c : message.toUpperCase().toCharArray()) {
            switch(c) {
                case 10 : outputArray[i] = LETTER_LIST[27]; break;
                case 32 : outputArray[i] = LETTER_LIST[26]; break;
                case 40 : outputArray[i] = LETTER_LIST[28]; break;
                case 41 : outputArray[i] = LETTER_LIST[29]; break;
                case 44 : outputArray[i] = LETTER_LIST[30]; break;
                case 46 : outputArray[i] = LETTER_LIST[31]; break;
                default :
                    if (c >= 48 && c <= 57)
                        outputArray[i] = NUMBER_LIST[c-48];
                    else
                        outputArray[i] = LETTER_LIST[c-65];
                    break;
            }

            i++;
        }
        return outputArray;
    }

    /*
    Converts matrices into a linked list of color points
    */
    public static LinkedList<ColorPoint> getMessagePoints(int x, int y, String message, int pixelSize) {
        int initX = x;
        LinkedList<ColorPoint> colorPoints = new LinkedList<>();

        for (int[] charMatrix : getMessageMatrix(message)) {
            int i = 0;
            int j = 0;
            int step;
            int charWidth;

            if (charMatrix.length > 20 && charMatrix.length != 1 && charMatrix[0] != 2) {
                step = 6;
                charWidth = 5;
            }
            else {
                step = 4;
                charWidth = 3;
            }

            boolean newLine = false;
            for (int p : charMatrix) {
                if (p == 1) {
                    colorPoints.add(new ColorPoint(x + (i * pixelSize), y + (j * pixelSize), randomGrey()));
                }
                else if (p == 3) {
                    x = initX;
                    y += 6 * pixelSize;
                    newLine = true;
                }

                if (i % (charWidth - 1) == 0 && i != 0) {
                    i = 0;
                    j++;
                }
                else {
                    i++;
                }
            }

            if (!newLine)
                x += step * pixelSize;
        }

        return colorPoints;
    }
}