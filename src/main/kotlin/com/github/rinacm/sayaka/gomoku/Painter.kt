package com.github.rinacm.sayaka.gomoku

import java.awt.*
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object Painter {
    private const val chessboardImageSize = 650
    private const val chessboardSize = 560
    private val lines = intArrayOf(50, 90, 130, 170, 210, 250, 290, 330, 370, 410, 450, 490, 530, 570, 610)
    private val points = arrayOf(
        Point(170, 170),
        Point(170, 490),
        Point(330, 330),
        Point(490, 170),
        Point(490, 490),
    )

    fun save(path: String) {
        val img = drawNew()
        ImageIO.write(img, "png", File(path))
    }

    fun save(path: String, chessboard: Array<Array<Piece>>, toBeHighlight: Piece) {
        val img = drawNew()
        for (piece in chessboard.flatten()) {
            drawPiece(img, piece, piece == toBeHighlight)
        }
        ImageIO.write(img, "png", File(path))
    }

    private fun drawNew(): BufferedImage {
        val img = BufferedImage(chessboardImageSize, chessboardImageSize, BufferedImage.TYPE_INT_ARGB)
        val g = getGraphics(img)
        fillChessboard(g)
        drawBorder(g)
        drawCross(g)
        drawIdentifier(g)
        drawStars(g)
        return img
    }

    private fun getGraphics(img: BufferedImage): Graphics2D {
        val graphics = img.createGraphics()
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
        return graphics
    }

    private fun drawPiece(image: BufferedImage, piece: Piece, highlight: Boolean) {
        val g = image.createGraphics()
        val real = piece.toActualPoint()
        when (piece.kind) {
            Role.BLACK -> {
                g.paint = Color.BLACK
                g.fillOval(real.x - 20, real.y - 20, 40, 40)
            }
            Role.WHITE -> {
                g.paint = Color.WHITE
                g.fillOval(real.x - 20, real.y - 20, 40, 40)
                g.paint = Color.BLACK
                g.stroke = BasicStroke(0.5.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
                g.drawOval(real.x - 20, real.y - 20, 40, 40)
            }
            Role.NONE -> return
        }

        if (highlight) {
            g.paint = Color(220, 20, 60)
            g.stroke = BasicStroke(1.5.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
            with(real) {
                g.drawLine(x - 20, y - 20, x - 20, y - 15)
                g.drawLine(x - 20, y - 20, x - 15, y - 20)
                g.drawLine(x - 20, y + 20, x - 20, y + 15)
                g.drawLine(x - 20, y + 20, x - 15, y + 20)
                g.drawLine(x + 20, y - 20, x + 15, y - 20)
                g.drawLine(x + 20, y - 20, x + 20, y - 15)
                g.drawLine(x + 20, y + 20, x + 20, y + 15)
                g.drawLine(x + 20, y + 20, x + 15, y + 20)
            }
        }
    }

    private fun fillChessboard(g: Graphics2D) {
        g.paint = Color(232, 180, 130)
        g.fillRect(0, 0, chessboardImageSize, chessboardImageSize)
    }

    private fun drawBorder(g: Graphics2D) {
        g.paint = Color.BLACK
        g.stroke = BasicStroke(1.0.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
        g.draw(Rectangle2D.Double(0.5, 0.5, 649.0, 649.0))
    }

    private fun drawCross(g: Graphics2D) {
        g.paint = Color.BLACK
        g.stroke = BasicStroke(1.0.toFloat(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER)
        for (line in lines) {
            g.drawLine(line, 50, line, chessboardSize + 50)
            g.drawLine(50, line, chessboardSize + 50, line)
        }
    }

    private fun drawIdentifier(g: Graphics2D) {
        g.font = Font("consolas", Font.PLAIN, 20)
        val metrics = g.fontMetrics
        for (i in lines.indices) {
            // centered align the string
            g.drawString(i.toString(), lines[i] - metrics.stringWidth(i.toString()) / 2, 25)
            g.drawString(Piece.numberToYAxis(i), 20, lines[i] - ((metrics.height / 2) - metrics.ascent - metrics.descent))
        }
    }

    private fun drawStars(g: Graphics2D) {
        g.paint = Color.BLACK
        for ((x, y) in points) {
            g.fillOval(x - 3, y - 3, 6, 6)
        }
    }
}