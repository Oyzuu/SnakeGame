package snakegame.controller;

import snakegame.model.ColorPoint;
import snakegame.drawutils.PixelNumbers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

class Playground extends JPanel {
    private final int
            SQ_SIZE  = 20,
            HALF_SQ  = SQ_SIZE/2,
            QUART_SQ = SQ_SIZE/4,
            FIFTH_SQ = SQ_SIZE/5,
            TENTH_SQ = SQ_SIZE/10,

            UP    =  0,
            RIGHT =  1,
            DOWN  =  2,
            LEFT  =  3;

    private int
            gameSpeed = 100,
            snakeDir  = RIGHT,
            score     = 0;

    private boolean hasStarted = false;
    private LinkedList<ColorPoint>
            background = new LinkedList<ColorPoint>(),
            powerUps   = new LinkedList<ColorPoint>(),
            theSnake   = new LinkedList<ColorPoint>();

    private javax.swing.Timer timer, powerUpTimer;

    Playground() {
        setSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setPreferredSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setOpaque(false);

        init();

        /* Timer to control the running speed of the game */
        ActionListener runTheGame = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveSnake(snakeDir);
                paintComponent(getGraphics());
            }
        };
        timer = new javax.swing.Timer(gameSpeed, runTheGame);

        /* Timer to control power-up spawn */
        ActionListener powerUpSpawn = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPowerUp();
            }
        };
        powerUpTimer = new javax.swing.Timer(20 * gameSpeed, powerUpSpawn);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!timer.isRunning()) {
                    start();
                }
                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_Z)
                        && snakeDir != DOWN)
                    snakeDir = UP;
                else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
                        && snakeDir != UP)
                    snakeDir = DOWN;
                else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_Q)
                        && snakeDir != RIGHT)
                    snakeDir = LEFT;
                else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
                        && snakeDir != LEFT)
                    snakeDir = RIGHT;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        drawAll(g);
        if (!moveCheck())
            gameOver(g);
    }

    private void init() {
        /* creates a list of colored points for the background */
        Random r = new Random();

        for (int i = 0; i < getWidth()/SQ_SIZE; i++) {
            for (int j = 0; j < getHeight()/SQ_SIZE; j++) {
                Color rColor = new Color(150, 200, r.nextInt(100) + 150);
                background.add(new ColorPoint(
                        i*SQ_SIZE, j*SQ_SIZE, rColor));
            }
        }

        /* creates a 5 squares long snake */
        for (int i = 0; i < 5; i++) {
            theSnake.add(new ColorPoint((10+i) * SQ_SIZE,
                    15 * SQ_SIZE, randomGreyScale()));
        }
    }

    private void drawAll(Graphics g) {
        BufferedImage img = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2dImage = img.createGraphics();

        /* Background buffered paint */
        for (ColorPoint cp : background) {
            g2dImage.setColor(cp.getColor());
            g2dImage.fillRect(cp.getX(), cp.getY(),
                    SQ_SIZE, SQ_SIZE);
        }

        /* Welcome screen at launch */
        if (!hasStarted) {
            LinkedList<ColorPoint> welcomeList =
                    matrix2List(SQ_SIZE, SQ_SIZE, "Welcome\nto my\nsnake\ngame",
                            HALF_SQ);
            for (ColorPoint cp : welcomeList) {
                g2dImage.setColor(randomGreyScale());
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), HALF_SQ, HALF_SQ);
            }

            LinkedList<ColorPoint> byAuthorList =
                    matrix2List(SQ_SIZE, getHeight() - SQ_SIZE,
                            "de cooman sammy, 2016", TENTH_SQ);
            for (ColorPoint cp : byAuthorList) {
                g2dImage.setColor(randomGreyScale());
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), TENTH_SQ, TENTH_SQ);
            }

            LinkedList<ColorPoint> instructions =
                    matrix2List(SQ_SIZE, 13 * SQ_SIZE,
                            "Press any key to start...", QUART_SQ);
            for (ColorPoint cp : instructions) {
                g2dImage.setColor(randomGreyScale());
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), QUART_SQ, QUART_SQ);
            }

            LinkedList<ColorPoint> instructions2 =
                    matrix2List(SQ_SIZE, 15 * SQ_SIZE,
                            "(ZQSD or arrows as controls)", FIFTH_SQ);
            for (ColorPoint cp : instructions2) {
                g2dImage.setColor(randomGreyScale());
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), FIFTH_SQ, FIFTH_SQ);
            }
        }
        else {
            /* Snake buffered paint */
            for (ColorPoint cp : theSnake) {
                g2dImage.setColor(cp.getColor().brighter());
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), SQ_SIZE, SQ_SIZE);
            }

            /* Score buffered paint */
            String strScore = "" + score;
            LinkedList<ColorPoint> scoreList = matrix2List(getWidth() - (strScore.length() * (HALF_SQ * 4)), HALF_SQ, strScore, HALF_SQ);
            g2dImage.setColor(new Color(0, 0, 0, 75));
            for (ColorPoint cp : scoreList) {
                g2dImage.fillRect(
                        cp.getX(), cp.getY(), HALF_SQ, HALF_SQ);
            }

            /* Power-ups buffered paint */
            g2dImage.setColor(new Color(255, 150, 0));
            for (ColorPoint pu : powerUps)
                g2dImage.fillRect(
                        pu.getX(), pu.getY(), SQ_SIZE, SQ_SIZE);
        }

        /* Paint the buffer onto the panel */
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this);
    }

    /* Random Color Generation */

    private Color randomGreyScale() {
        int rValue = (int)(Math.random() * 50) + 50;
        return new Color(rValue, rValue, rValue);
    }

    private void moveSnake(int direction) {
        switch(direction) {
            case UP    : moveUp();    break;
            case RIGHT : moveRight(); break;
            case DOWN  : moveDown();  break;
            case LEFT  : moveLeft();  break;
        }
    }

    private void moveUp() {
        if (snakeDir == DOWN)
            return;
        theSnake.add(
                new ColorPoint(theSnake.peekLast().getX(),
                        theSnake.peekLast().getY() - SQ_SIZE,
                        randomGreyScale()));
        theSnake.removeFirst();
    }

    private void moveDown() {
        if (snakeDir == UP)
            return;
        theSnake.add(
                new ColorPoint(theSnake.peekLast().getX(),
                        theSnake.peekLast().getY() + SQ_SIZE,
                        randomGreyScale()));
        theSnake.removeFirst();
    }

    private void moveLeft() {
        if (snakeDir == RIGHT)
            return;
        theSnake.add(
                new ColorPoint(theSnake.peekLast().getX() - SQ_SIZE,
                        theSnake.peekLast().getY(),
                        randomGreyScale()));
        theSnake.removeFirst();
    }

    private void moveRight() {
        if (snakeDir == LEFT)
            return;
        theSnake.add(
                new ColorPoint(theSnake.peekLast().getX() + SQ_SIZE,
                        theSnake.peekLast().getY(),
                        randomGreyScale()));
        theSnake.removeFirst();
    }

    private boolean moveCheck() {
        ColorPoint snakeHead = theSnake.getLast();
        for (int i = 0; i < theSnake.size() - 2; i++) {
            if (theSnake.get(i).getX() == snakeHead.getX()
                    && theSnake.get(i).getY() == snakeHead.getY())
                return false;
            else if (snakeHead.getX() < 0 || snakeHead.getY() < 0)
                return false;
            else if (snakeHead.getX() > getWidth() - 1
                    || snakeHead.getY() > getHeight() - 1)
                return false;
        }

        ListIterator<ColorPoint> iter = powerUps.listIterator();
        while (iter.hasNext()) {
            ColorPoint pu = iter.next();

            if (snakeHead.getX() == pu.getX() && snakeHead.getY() == pu.getY()) {
                iter.remove();
                theSnake.addFirst(theSnake.getFirst());
                score += 1;
                if (score % 10 == 0) {
                    gameSpeed -= gameSpeed / 5;
                    setSpeed(gameSpeed);
                }
            }
        }

        return true;
    }

    private void start() {
        if (!timer.isRunning()) {
            System.out.println("Timer starts");
            hasStarted = true;
            timer.start();
            powerUpTimer.start();
        }
        else {
            System.out.println("Timer stops");
            timer.stop();
            powerUpTimer.stop();
        }
    }

    /*
    Converts matrices returned by PixelNumbers.getInt() or getString()
    into a linked list of color points
    */
    private LinkedList<ColorPoint> matrix2List(
            int x, int y, String message, int pixelSize) {
        int initX = x;
        LinkedList<ColorPoint> cpList = new LinkedList<ColorPoint>();

        for (int[] charMatrix : PixelNumbers.getString(message)) {
            int i = 0;
            int j = 0;
            int step;
            int charWidth;

            if (charMatrix.length > 20 && charMatrix.length != 1 &&
                    charMatrix[0] != 2) {
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
                    cpList.add(new ColorPoint(
                            x + (i * pixelSize), y + (j * pixelSize),
                            randomGreyScale()));
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
                else
                    i++;
            }
            if (!newLine)
                x += step * pixelSize;
        }

        return cpList;
    }

    private void gameOver(Graphics g) {
        int x = (getWidth() / 2) - 11 * SQ_SIZE;
        int y = (getHeight() / 2) - 5 * SQ_SIZE;

        LinkedList<ColorPoint> gameOverCPList =
                matrix2List(x, y, "GAME\nOVER", SQ_SIZE);

        /* Draws the shadow of "GAME\nOVER" */
        g.setColor(new Color(0, 0, 0, 30));
        for (ColorPoint cp : gameOverCPList) {

            g.fillRect(cp.getX() + SQ_SIZE,
                    cp.getY() + SQ_SIZE,
                    SQ_SIZE,
                    SQ_SIZE);
        }

        /* draws "GAME\nOVER" */
        for (ColorPoint cp : gameOverCPList) {
            g.setColor(cp.getColor().darker());
            g.fillRect(cp.getX(), cp.getY(), SQ_SIZE, SQ_SIZE);
        }

        timer.stop();
        powerUpTimer.stop();
    }

    private void setSpeed(int speed) {
        timer.setDelay(speed);
        timer.setInitialDelay(speed);
        powerUpTimer.setDelay(speed * 20);
        powerUpTimer.setInitialDelay(speed * 20);
    }

    private void addPowerUp() {
        boolean isInSnake;
        int x, y;
        Random r = new Random();

        while (true) {
            isInSnake = false;
            x = r.nextInt(getWidth() / SQ_SIZE);
            y = r.nextInt(getHeight() / SQ_SIZE);
            for (ColorPoint cp : theSnake) {
                if (cp.getX() == x * SQ_SIZE && cp.getY() == y * SQ_SIZE)
                    isInSnake = true;
            }

            if (!isInSnake) break;
        }

        if (powerUps.size() == 2) {
            powerUps.removeFirst();
        }
        powerUps.add(
                new ColorPoint(x * SQ_SIZE, y * SQ_SIZE, Color.orange));
    }
}