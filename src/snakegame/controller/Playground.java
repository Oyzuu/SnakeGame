package snakegame.controller;

import snakegame.model.ColorPoint;
import snakegame.drawutils.PixelNumbers;
import snakegame.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import static snakegame.drawutils.MyColors.randomGrey;
import static snakegame.drawutils.PixelNumbers.getMessagePoints;

public class Playground extends JPanel {
    public static final int
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
            snakeDirection = RIGHT,
            score     = 0;
    
    private Snake snake;

    private boolean hasStarted = false;
    private LinkedList<ColorPoint>
            background = new LinkedList<>(),
            powerUps   = new LinkedList<>();

    private javax.swing.Timer timer, powerUpTimer;

    Playground() {
        setSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setPreferredSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setOpaque(false);

        init();

        /* Timer to control the running speed of the game */
        ActionListener runTheGame = e -> {
            moveSnake(snakeDirection);
            paintComponent(getGraphics());
        };

        timer = new javax.swing.Timer(gameSpeed, runTheGame);

        /* Timer to control power-up spawn */
        ActionListener powerUpSpawn = e -> addPowerUp();

        powerUpTimer = new javax.swing.Timer(20 * gameSpeed, powerUpSpawn);

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!timer.isRunning())
                    start();

                int key = e.getKeyCode();

                if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_Z) && snakeDirection != DOWN)
                    snakeDirection = UP;
                else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && snakeDirection != UP)
                    snakeDirection = DOWN;
                else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_Q) && snakeDirection != RIGHT)
                    snakeDirection = LEFT;
                else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && snakeDirection != LEFT)
                    snakeDirection = RIGHT;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        drawAll(g);
        if (!verifyMovement())
            gameOver(g);
    }

    // Methods

    private void init() {
        snake = new Snake();
        
        /* creates a list of colored points for the background */
        Random r = new Random();

        for (int i = 0; i < getWidth()/SQ_SIZE; i++) {
            for (int j = 0; j < getHeight()/SQ_SIZE; j++) {
                Color rColor = new Color(150, 200, r.nextInt(100) + 150);
                background.add(new ColorPoint(
                        i*SQ_SIZE, j*SQ_SIZE, rColor));
            }
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
                    getMessagePoints(SQ_SIZE, SQ_SIZE, "Welcome\nto my\nsnake\ngame", HALF_SQ);

            for (ColorPoint cp : welcomeList) {
                g2dImage.setColor(randomGrey());
                g2dImage.fillRect(cp.getX(), cp.getY(), HALF_SQ, HALF_SQ);
            }

            LinkedList<ColorPoint> byAuthorList =
                    getMessagePoints(SQ_SIZE, getHeight() - SQ_SIZE, "de cooman sammy, 2016", TENTH_SQ);

            for (ColorPoint cp : byAuthorList) {
                g2dImage.setColor(randomGrey());
                g2dImage.fillRect(cp.getX(), cp.getY(), TENTH_SQ, TENTH_SQ);
            }

            LinkedList<ColorPoint> instructions =
                    getMessagePoints(SQ_SIZE, 13 * SQ_SIZE, "Press any key to start...", QUART_SQ);

            for (ColorPoint cp : instructions) {
                g2dImage.setColor(randomGrey());
                g2dImage.fillRect(cp.getX(), cp.getY(), QUART_SQ, QUART_SQ);
            }

            LinkedList<ColorPoint> instructions2 =
                    getMessagePoints(SQ_SIZE, 15 * SQ_SIZE, "(ZQSD or arrows as controls)", FIFTH_SQ);

            for (ColorPoint cp : instructions2) {
                g2dImage.setColor(randomGrey());
                g2dImage.fillRect(cp.getX(), cp.getY(), FIFTH_SQ, FIFTH_SQ);
            }
        }
        else {
            /* Snake buffered paint */
            for (ColorPoint cp : snake.getBody()) {
                g2dImage.setColor(cp.getColor().brighter());
                g2dImage.fillRect(cp.getX(), cp.getY(), SQ_SIZE, SQ_SIZE);
            }

            /* Score buffered paint */
            String strScore = "" + score;
            LinkedList<ColorPoint> scoreList =
                    getMessagePoints(getWidth() - (strScore.length() * (HALF_SQ * 4)), HALF_SQ, strScore, HALF_SQ);
            g2dImage.setColor(new Color(0, 0, 0, 75));

            for (ColorPoint cp : scoreList)
                g2dImage.fillRect( cp.getX(), cp.getY(), HALF_SQ, HALF_SQ);

            /* Power-ups buffered paint */
            g2dImage.setColor(new Color(255, 150, 0));

            for (ColorPoint pu : powerUps)
                g2dImage.fillRect(pu.getX(), pu.getY(), SQ_SIZE, SQ_SIZE);
        }

        /* Paint the buffer onto the panel */
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(img, 0, 0, this);
    }

    private void moveSnake(int direction) {
        switch(direction) {
            case UP    : snake.up();    break;
            case RIGHT : snake.right(); break;
            case DOWN  : snake.down();  break;
            case LEFT  : snake.left();  break;
        }
    }

    private boolean verifyMovement() {
        ColorPoint head = snake.getBody().getLast();

        for (int i = 0; i < snake.getBody().size() - 2; i++) {
            if (snake.getBody().get(i).getX() == head.getX() && snake.getBody().get(i).getY() == head.getY()) {
                return false;
            }
            else if (head.getX() < 0 || head.getY() < 0) {
                return false;
            }
            else if (head.getX() > getWidth() - 1 || head.getY() > getHeight() - 1) {
                return false;
            }
        }

        ListIterator<ColorPoint> iter = powerUps.listIterator();
        while (iter.hasNext()) {
            ColorPoint pu = iter.next();

            if (head.getX() == pu.getX() && head.getY() == pu.getY()) {
                iter.remove();
                snake.getBody().addFirst(snake.getBody().getFirst());

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

            return;
        }

        System.out.println("Timer stops");
        timer.stop();
        powerUpTimer.stop();
    }

    private void gameOver(Graphics g) {
        int x = (getWidth() / 2) - 11 * SQ_SIZE;
        int y = (getHeight() / 2) - 5 * SQ_SIZE;

        LinkedList<ColorPoint> gameOverCPList = getMessagePoints(x, y, "GAME\nOVER", SQ_SIZE);

        /* Draws the shadow of "GAME\nOVER" */
        g.setColor(new Color(0, 0, 0, 30));
        for (ColorPoint cp : gameOverCPList) {

            g.fillRect(cp.getX() + SQ_SIZE,cp.getY() + SQ_SIZE, SQ_SIZE, SQ_SIZE);
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

            for (ColorPoint cp : snake.getBody()) {
                if (cp.getX() == x * SQ_SIZE && cp.getY() == y * SQ_SIZE)
                    isInSnake = true;
            }

            if (!isInSnake)
                break;
        }

        if (powerUps.size() == 2) {
            powerUps.removeFirst();
        }

        powerUps.add(new ColorPoint(x * SQ_SIZE, y * SQ_SIZE, Color.orange));
    }
}