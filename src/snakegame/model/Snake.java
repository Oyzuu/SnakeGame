package snakegame.model;

import java.awt.*;
import java.util.LinkedList;
import java.util.ListIterator;
import static snakegame.controller.Playground.*;

/**
 * Created by Gilbert on 08-10-16.
 */
public class Snake {

    public interface SnakeHost {
        void setDirection(int direction);
        int  getDirection();
        void incrementScore();
    }

    private LinkedList<ColorPoint> body;
    private SnakeHost host;
    
    public Snake(SnakeHost host) {
        body = new LinkedList<>();
        this.host = host;
        
        for (int i = 0; i < 5; i++) {
            body.add(new ColorPoint((10+i) * SQ_SIZE, 15 * SQ_SIZE, randomGrey()));
        }
        
    }

    public LinkedList<ColorPoint> getBody() {
        return body;
    }

    public void up() {
        if (host.getDirection() == DOWN)
            return;
        host.setDirection(UP);

        body.add(new ColorPoint(body.peekLast().getX(), body.peekLast().getY() - SQ_SIZE, randomGrey()));
        body.removeFirst();
    }

    public void down() {
        if (host.getDirection() == UP)
            return;
        host.setDirection(DOWN);

        body.add(new ColorPoint(body.peekLast().getX(), body.peekLast().getY() + SQ_SIZE, randomGrey()));
        body.removeFirst();
    }

    public void left() {
        if (host.getDirection() == RIGHT)
            return;
        host.setDirection(LEFT);

        body.add(new ColorPoint(body.peekLast().getX() - SQ_SIZE, body.peekLast().getY(), randomGrey()));
        body.removeFirst();
    }

    public void right() {
        if (host.getDirection() == LEFT)
            return;
        host.setDirection(RIGHT);

        body.add(new ColorPoint(body.peekLast().getX() + SQ_SIZE, body.peekLast().getY(), randomGrey()));
        body.removeFirst();
    }

    public boolean verifyMovement(int playgroundWidth, int playgroundHeight, LinkedList<ColorPoint> powerUps) {
        ColorPoint snakeHead = body.getLast();

        for (int i = 0; i < body.size() - 2; i++) {
            if (body.get(i).getX() == snakeHead.getX() && body.get(i).getY() == snakeHead.getY()) {
                return false;
            }
            else if (snakeHead.getX() < 0 || snakeHead.getY() < 0) {
                return false;
            }
            else if (snakeHead.getX() > playgroundWidth - 1 || snakeHead.getY() > playgroundHeight - 1) {
                return false;
            }
        }

        ListIterator<ColorPoint> iter = powerUps.listIterator();
        while (iter.hasNext()) {
            ColorPoint pu = iter.next();

            if (snakeHead.getX() == pu.getX() && snakeHead.getY() == pu.getY()) {
                iter.remove();
                body.addFirst(body.getFirst());
                host.incrementScore();
            }
        }

        return true;
    }

    private Color randomGrey() {
        int rValue = (int)(Math.random() * 50) + 50;
        return new Color(rValue, rValue, rValue);
    }

}
