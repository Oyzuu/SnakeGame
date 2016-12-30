package snakegame.model

import snakegame.controller.Playground.SQ_SIZE
import snakegame.drawutils.randomGrey
import java.util.*

/**
 * Created by Gilbert on 08-10-16.
 */
class Snake {

    val body: LinkedList<ColorPoint>

    init {
        body = LinkedList<ColorPoint>()

        for (i in 0..4) {
            body.add(ColorPoint((10 + i) * SQ_SIZE, 15 * SQ_SIZE, randomGrey()))
        }
    }

    fun up() {
        body.add(ColorPoint(body.peekLast().x, body.peekLast().y - SQ_SIZE, randomGrey()))
        body.removeFirst()
    }

    fun down() {
        body.add(ColorPoint(body.peekLast().x, body.peekLast().y + SQ_SIZE, randomGrey()))
        body.removeFirst()
    }

    fun left() {
        body.add(ColorPoint(body.peekLast().x - SQ_SIZE, body.peekLast().y, randomGrey()))
        body.removeFirst()
    }

    fun right() {
        body.add(ColorPoint(body.peekLast().x + SQ_SIZE, body.peekLast().y, randomGrey()))
        body.removeFirst()
    }

}
