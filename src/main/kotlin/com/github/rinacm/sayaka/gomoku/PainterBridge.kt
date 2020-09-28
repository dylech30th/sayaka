package com.github.rinacm.sayaka.gomoku

import com.github.rinacm.sayaka.common.util.buildListImmutable
import com.github.rinacm.sayaka.common.util.toJson
import com.google.gson.annotations.SerializedName
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.Closeable

@Suppress("EXPERIMENTAL_API_USAGE")
class PainterBridge(
    private val httpClient: HttpClient = HttpClient(CIO) {
        expectSuccess = false
    },
) : Closeable by httpClient {
    suspend fun drawNew(path: String): Boolean {
        val resp: HttpResponse = httpClient.post(GameConfiguration.bridgingUrlBase() + "/new") {
            body = path
        }
        return resp.status == HttpStatusCode.OK
    }

    suspend fun drawPiece(path: String, chessboard: Array<Array<Piece>>, toBeHighlight: Piece): Boolean {
        fun chessboardToPayload(): PiecePayload {
            val list: List<PiecePayload.SinglePiece> = buildListImmutable {
                for (piece in chessboard.flatten()) {
                    add(PiecePayload.SinglePiece(piece.kind.toInt(), piece.x, piece.y, piece == toBeHighlight))
                }
            }
            return PiecePayload(path, list.toTypedArray())
        }

        val resp: HttpResponse = httpClient.post(GameConfiguration.bridgingUrlBase() + "/piece") {
            body = chessboardToPayload().toJson()
        }
        return resp.status == HttpStatusCode.OK
    }
}

data class PiecePayload(@SerializedName("save") val savePath: String, val pieces: Array<SinglePiece>) {
    data class SinglePiece(@SerializedName("piece") val pieceKind: Int, val x: Int, val y: Int, val highlight: Boolean)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PiecePayload

        if (savePath != other.savePath) return false
        if (!pieces.contentEquals(other.pieces)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = savePath.hashCode()
        result = 31 * result + pieces.contentHashCode()
        return result
    }
}