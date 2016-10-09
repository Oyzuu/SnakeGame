package snakegame.model;

import java.util.LinkedList;
import static snakegame.controller.Playground.*;
import static snakegame.drawutils.MyColors.randomGrey;

/**
 * Created by Gilbert on 08-10-16.
 */
public class Snake {

    private LinkedList<ColorPoint> body;
    
    public Snake() {
        body = new LinkedList<>();
        
        for (int i = 0; i < 5; i++) {
            body.add(new ColorPoint((10+i) * SQ_SIZE, 15 * SQ_SIZE, randomGrey()));
        }
    }

    public LinkedList<ColorPoint> getBody() {
        return body;
    }

    public void up() {
        body.add(new ColorPoint(body.peekLast().getX(), body.peekLast().getY() - SQ_SIZE, randomGrey()));
        body.removeFirst();
    }

    public void down() {
        body.add(new ColorPoint(body.peekLast().getX(), body.peekLast().getY() + SQ_SIZE, randomGrey()));
        body.removeFirst();
    }

    public void left() {
        body.add(new ColorPoint(body.peekLast().getX() - SQ_SIZE, body.peekLast().getY(), randomGrey()));
        body.removeFirst();
    }

    public void right() {
        body.add(new ColorPoint(body.peekLast().getX() + SQ_SIZE, body.peekLast().getY(), randomGrey()));
        body.removeFirst();
    }

}
