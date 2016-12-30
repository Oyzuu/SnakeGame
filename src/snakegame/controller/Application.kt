package snakegame.controller

import javax.swing.*
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

internal class Application(name: String) : JFrame() {
    private val DISPLAY: Container
    private val playground: Playground

    init {
        title = name
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        DISPLAY = contentPane

        playground = Playground()
        DISPLAY.add(playground)

        addWindowListener(object : WindowAdapter() {
            override fun windowOpened(e: WindowEvent?) {
                playground.requestFocus()
            }
        })

        pack()
        isVisible = true
    }
}
