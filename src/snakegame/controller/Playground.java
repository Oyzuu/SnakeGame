package snakegame.controller;

import snakegame.drawutils.AppColors;
import snakegame.model.ColorPoint;
import snakegame.model.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import static snakegame.drawutils.AlphanumericMatrices.getMessagePoints;

public class Playground extends JPanel implements KeyListener {

    //region Static constants
    public static final int SQ_SIZE = 20;
    //endregion

    //region Constants
    private final int UP    = 0;
    private final int RIGHT = 1;
    private final int DOWN  = 2;
    private final int LEFT  = 3;

    private final int HALF_SQ  = SQ_SIZE / 2;
    private final int QUART_SQ = SQ_SIZE / 4;
    private final int FIFTH_SQ = SQ_SIZE / 5;
    private final int TENTH_SQ = SQ_SIZE / 10;
    //endregion

    //region Variables
    private List<ColorPoint>       background;
    private LinkedList<ColorPoint> powerUps;
    private javax.swing.Timer      timer;
    private javax.swing.Timer      powerUpTimer;
    private Graphics2D             graphics;
    private BufferedImage          bufferedImage;

    private int     gameSpeed      = 100;
    private int     snakeDirection = RIGHT;
    private int     score          = 0;
    private boolean hasStarted     = false;

    private Snake snake;
    //endregion

    //region Playground constructor, init and overrides
    Playground() {
        setSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setPreferredSize(new Dimension(50 * SQ_SIZE, 30 * SQ_SIZE));
        setOpaque(false);

        addKeyListener(this);

        init();
    }

    private void init() {
        bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        graphics = bufferedImage.createGraphics();
        background = new LinkedList<>();
        powerUps = new LinkedList<>();
        snake = new Snake();

        /* creates a list of colored points for the background */
        final Random r = new Random();

        for (int i = 0; i < getWidth() / SQ_SIZE; i++) {
            for (int j = 0; j < getHeight() / SQ_SIZE; j++) {
                final Color rColor = new Color(150, 200, r.nextInt(100) + 150);
                background.add(new ColorPoint(i * SQ_SIZE, j * SQ_SIZE, rColor));
            }
        }

        /* Timer to control the running speed of the game */
        final ActionListener runTheGame = e -> {
            moveSnake(snakeDirection);
            paintComponent(getGraphics());
        };

        timer = new javax.swing.Timer(gameSpeed, runTheGame);

        /* Timer to control power-up spawn */
        final ActionListener powerUpSpawn = e -> addPowerUp();

        powerUpTimer = new javax.swing.Timer(20 * gameSpeed, powerUpSpawn);
    }

    @Override
    public void paintComponent(final Graphics g) {
        drawAll(g);

        if (!verifyMovement()) {
            drawGameOver();
            stop();
        }
    }
    //endregion

    //region KeyListener
    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
        if (!timer.isRunning()) {
            start();
        }

        final int key = e.getKeyCode();

        if ((key == KeyEvent.VK_UP || key == KeyEvent.VK_Z) && snakeDirection != DOWN) {
            snakeDirection = UP;
        }
        else if ((key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) && snakeDirection != UP) {
            snakeDirection = DOWN;
        }
        else if ((key == KeyEvent.VK_LEFT || key == KeyEvent.VK_Q) && snakeDirection != RIGHT) {
            snakeDirection = LEFT;
        }
        else if ((key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) && snakeDirection != LEFT) {
            snakeDirection = RIGHT;
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
    //endregion

    //region Draw methods
    private void drawAll(final Graphics g) {

        /* Background buffered paint */
        for (final ColorPoint colorPoint : background) {
            graphics.setColor(colorPoint.getColor());
            graphics.fillRect(colorPoint.getX(), colorPoint.getY(), SQ_SIZE, SQ_SIZE);
        }

        /* Welcome screen at launch */
        if (!hasStarted) {
            drawText("Welcome\nto my\nsnake\ngame", SQ_SIZE, SQ_SIZE, HALF_SQ);
            drawText("de cooman sammy, 2016", SQ_SIZE, getHeight() - SQ_SIZE, TENTH_SQ);
            drawText("Press any key to start...", SQ_SIZE, 13 * SQ_SIZE, QUART_SQ);
            drawText("(ZQSD or arrows as controls)", SQ_SIZE, 15 * SQ_SIZE, FIFTH_SQ);
        }
        else {
            /* Snake buffered paint */
            for (final ColorPoint colorPoint : snake.getBody()) {
                graphics.setColor(colorPoint.getColor().brighter());
                graphics.fillRect(colorPoint.getX(), colorPoint.getY(), SQ_SIZE, SQ_SIZE);
            }

            drawScore();
            drawPowerUps();
        }

        /* Paint the buffer onto the panel */
        final Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(bufferedImage, 0, 0, this);
    }

    private void drawScore() {
        final String scoreAsString = Integer.toString(score);
        final LinkedList<ColorPoint> scoreList =
                getMessagePoints(getWidth() - (scoreAsString.length() * (HALF_SQ * 4)), HALF_SQ, scoreAsString, HALF_SQ);

        graphics.setColor(new Color(0, 0, 0, 75));
        drawPointsFromListWithSize(scoreList, HALF_SQ);
    }

    private void drawPowerUps() {
        graphics.setColor(new Color(255, 150, 0));
        drawPointsFromListWithSize(powerUps, SQ_SIZE);
    }

    private void drawText(final String text, final int x, final int y, final int pixelSize) {
        final LinkedList<ColorPoint> instructions = getMessagePoints(x, y, text, pixelSize);

        for (final ColorPoint cp : instructions) {
            graphics.setColor(AppColors.randomGrey());
            graphics.fillRect(cp.getX(), cp.getY(), pixelSize, pixelSize);
        }
    }

    private void drawPointsFromListWithSize(final List<ColorPoint> list, final int pixelSize) {
        for (final ColorPoint cp : list) {
            graphics.fillRect(cp.getX(), cp.getY(), pixelSize, pixelSize);
        }
    }

    private void drawGameOver() {
        final Graphics graphics = getGraphics();
        final int      x        = (getWidth() / 2) - 11 * SQ_SIZE;
        final int      y        = (getHeight() / 2) - 5 * SQ_SIZE;

        final LinkedList<ColorPoint> gameOverCPList = getMessagePoints(x, y, "GAME\nOVER", SQ_SIZE);

        /* Draws the shadow of "GAME\nOVER" */
        graphics.setColor(new Color(0, 0, 0, 30));
        for (final ColorPoint cp : gameOverCPList) {
            graphics.fillRect(cp.getX() + SQ_SIZE, cp.getY() + SQ_SIZE, SQ_SIZE, SQ_SIZE);
        }

        /* draws "GAME\nOVER" */
        for (final ColorPoint cp : gameOverCPList) {
            graphics.setColor(cp.getColor().darker());
            graphics.fillRect(cp.getX(), cp.getY(), SQ_SIZE, SQ_SIZE);
        }

        stop();
    }
    //endregion

    //region Snake movement
    private void moveSnake(final int direction) {
        switch (direction) {
            case UP:
                snake.up();
                break;
            case RIGHT:
                snake.right();
                break;
            case DOWN:
                snake.down();
                break;
            case LEFT:
                snake.left();
                break;
        }
    }

    private boolean verifyMovement() {
        final ColorPoint head = snake.getBody().getLast();

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

        final ListIterator<ColorPoint> iter = powerUps.listIterator();
        while (iter.hasNext()) {
            final ColorPoint pu = iter.next();

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
    //endregion

    //region Game state
    private void start() {
        System.out.println("Timer starts");
        hasStarted = true;
        timer.start();
        powerUpTimer.start();
    }

    private void stop() {
        timer.stop();
        powerUpTimer.stop();
    }

    private void setSpeed(final int speed) {
        timer.setDelay(speed);
        timer.setInitialDelay(speed);
        powerUpTimer.setDelay(speed * 20);
        powerUpTimer.setInitialDelay(speed * 20);
    }

    private void addPowerUp() {
        boolean      isInSnake;
        int          x, y;
        final Random r = new Random();

        while (true) {
            isInSnake = false;
            x = r.nextInt(getWidth() / SQ_SIZE);
            y = r.nextInt(getHeight() / SQ_SIZE);

            for (final ColorPoint cp : snake.getBody()) {
                if (cp.getX() == x * SQ_SIZE && cp.getY() == y * SQ_SIZE) {
                    isInSnake = true;
                }
            }

            if (!isInSnake) break;
        }

        if (powerUps.size() == 2) {
            powerUps.removeFirst();
        }

        powerUps.add(new ColorPoint(x * SQ_SIZE, y * SQ_SIZE, Color.orange));
    }
    //endregion

}