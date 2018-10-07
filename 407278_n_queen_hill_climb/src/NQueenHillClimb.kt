import java.util.*

class NQueenHillClimb(private val board: Array<IntArray>, private var queenPositions: IntArray?) {

    private fun generateQueens(): IntArray {

        val randomPos = ArrayList<Int>()

        val r = Random()
        for (i in 0 until TOTAL_QUEENS) {
            randomPos.add(r.nextInt(8))
        }

        val randomPositions = IntArray(TOTAL_QUEENS)

        for (i in randomPos.indices) {
            randomPositions[i] = randomPos[i]
        }

        return randomPositions
    }

    fun placeQueens() {

        queenPositions = generateQueens()

        for (i in board.indices) {
            board[queenPositions!![i]][i] = 1
        }
    }

    fun h(): Int {

        var totalPairs = 0

        for (i in board.indices) {
            val pairs = ArrayList<Boolean>()
            for (j in 0 until board[i].size) {

                if (board[i][j] == 1) {
                    pairs.add(true)
                }
            }
            if (pairs.size != 0)
                totalPairs += (pairs.size - 1)
        }

        val rows = board.size
        val cols = board.size
        val maxSum = rows + cols - 2

        for (sum in 0..maxSum) {
            val pairs = ArrayList<Boolean>()
            for (i in 0 until rows) {
                for (j in 0 until cols) {
                    if (i + j - sum == 0) {
                        if (board[i][j] == 1) {
                            pairs.add(true)
                        }
                    }
                }
            }
            if (pairs.size != 0)
                totalPairs += (pairs.size - 1)
        }

        val pairs = checkMirrorDiagonal()

        return totalPairs + pairs
    }

    private fun checkMirrorDiagonal(): Int {

        val b1 = intArrayOf(board[7][0])
        val b2 = intArrayOf(board[7][1], board[6][0])
        val b3 = intArrayOf(board[7][2], board[6][1], board[5][0])
        val b4 = intArrayOf(board[7][3], board[6][2], board[5][1], board[4][0])
        val b5 = intArrayOf(board[7][4], board[6][3], board[5][2], board[4][1], board[3][0])
        val b6 = intArrayOf(board[7][5], board[6][4], board[5][3], board[4][2], board[3][1], board[2][0])
        val b7 = intArrayOf(board[7][6], board[6][5], board[5][4], board[4][3], board[3][2], board[2][1], board[1][0])
        val b8 = intArrayOf(
            board[7][7],
            board[6][6],
            board[5][5],
            board[4][4],
            board[3][3],
            board[2][2],
            board[1][1],
            board[0][0]
        )
        val b9 = intArrayOf(board[6][7], board[5][6], board[4][5], board[3][4], board[2][3], board[1][2], board[0][1])
        val b10 = intArrayOf(board[5][7], board[4][6], board[3][5], board[2][4], board[1][3], board[0][2])
        val b11 = intArrayOf(board[4][7], board[3][6], board[2][5], board[1][4], board[0][3])
        val b12 = intArrayOf(board[3][7], board[2][6], board[1][5], board[0][4])
        val b13 = intArrayOf(board[2][7], board[1][6], board[0][5])
        val b14 = intArrayOf(board[1][7], board[0][6])
        val b15 = intArrayOf(board[0][7])

        var totalPairs = 0

        listOf(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15).forEach {
            totalPairs += checkMirrorHorizontal(it)
        }

        return totalPairs
    }

    fun moveQueen(row: Int, col: Int) {

        board[queenPositions!![col]][col] = 2

        board[row][col] = 1
    }

    private fun checkMirrorHorizontal(b: IntArray): Int {

        var totalPairs = 0

        val pairs = ArrayList<Boolean>()
        for (i in b.indices) {
            if (b[i] == 1)
                pairs.add(true)
        }

        if (pairs.size != 0)
            totalPairs = pairs.size - 1

        return totalPairs
    }

    fun resetQueen(row: Int, col: Int) {

        if (board[row][col] == 1)
            board[row][col] = 0
    }

    fun resetBoard(col: Int) {

        for (i in board.indices) {
            if (board[i][col] == 2)
                board[i][col] = 1
        }
    }

    fun placeBestQueen(col: Int, queenPos: Int) {

        for (i in board.indices) {
            if (board[i][col] == 1)
                board[i][col] = 2
        }
        board[queenPos][col] = 1
        for (i in board.indices) {
            if (board[i][col] == 2)
                board[i][col] = 0
        }
    }

    fun printBoard() {

        for (i in board.indices) {
            for (j in 0 until board[i].size) {
                print((board[i][j]).toString() + " ")
            }
            println()
        }
    }

    companion object {

        var TOTAL_QUEENS = 8

        @JvmStatic
        fun main(args: Array<String>) {

            var climb = true
            var climbCount = 0
            val restarts = 5

            while (climb) {
                val board = NQueenHillClimb(
                    Array(TOTAL_QUEENS) { IntArray(TOTAL_QUEENS) }, IntArray(8)
                )
                board.placeQueens()
                println("===================================================")
                println("RESTART NUMBER: " + (climbCount + 1))
                println("===================================================")
                println("Randomly generated chessboard:")
                board.printBoard()
                println("Number of conflicting queens: ${board.h()}\n")

                var localMin = board.h()
                var best = false
                val bestQueenPositions = IntArray(8)

                for (j in board.board.indices) {
                    println("-------------------------------------")
                    println("Iterating through COLUMN $j:")
                    best = false
                    for (i in board.board.indices) {

                        if (i != board.queenPositions!![j]) {
                            board.moveQueen(i, j)
                            if (board.h() < localMin) {
                                best = true
                                localMin = board.h()
                                bestQueenPositions[j] = i
                            }
                            board.resetQueen(i, j)
                        }
                    }

                    board.resetBoard(j)
                    if (best) {
                        board.placeBestQueen(j, bestQueenPositions[j])
                        println("Best chessboard: ")
                        board.printBoard()
                        println("Number of conflicting queens: ${board.h()}\n")
                    } else {
                        println("No better chessboard found.")
                        board.printBoard()
                        println("Number of conflicting queens: ${board.h()}\n")
                    }
                }

                if (board.h() == 0)
                    climb = false

                climbCount++

                if (climbCount == restarts + 1) {
                    climb = false
                }
                println("Finished in " + (climbCount - 1) + " restarts.")
            }
        }
    }
}