@file:JvmName("AppColors")

package snakegame.drawutils

import java.awt.Color

/**
 * Created by Gilbert on 09-10-16.
 */

fun randomGrey(): Color {
    val randomValue = (Math.random() * 50).toInt() + 50
    return Color(randomValue, randomValue, randomValue)
}
