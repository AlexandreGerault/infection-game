package io.github.alexandregerault.infectiongame;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public final class GameBoard implements IGameBoard {

    private Colors[][] grid;


    /**
     * Basic constructor
     *
     * @param width_  The width of the board
     * @param height_ The height of the board
     */
    GameBoard(int width_, int height_) {
        this.grid = new Colors[width_][height_];

        this.grid[0][0] = Colors.BLACK;
        this.grid[width_ - 1][height_ - 1] = Colors.WHITE;
    }


    /**
     * Copy constructor
     *
     * @param board_ The copied board
     */
    public GameBoard(GameBoard board_) {
        this.grid = board_.grid;
    }


    /**
     * Count the amount of pawns for a player
     *
     * @param player_ The player we want to count the pawns
     * @return The amount of pawns the player has on the board
     */
    @Override
    public int pawns(Colors player_) {
        long count = 0L;
        for (Colors[] colors : grid) {
            for (Colors color : colors) {
                if (color != null && color.equals(player_)) {
                    count++;
                }
            }
        }
        return (int) count;
    }

    /**
     * Get the board's width
     *
     * @return The board's width
     */
    @Override
    public int width() {
        return this.grid.length;
    }


    /**
     * Get the board's height
     *
     * @return The board's height
     */
    @Override
    public int height() {
        return this.grid[0].length;
    }

    /**
     * Check the arrival square is free
     *
     * @param square_ The square to check whether it is free or not
     * @return True if the square is free
     */
    @Override
    public boolean isSquareFree(Point square_) {
        return grid[square_.x][square_.y] == null;
    }


    /**
     * Evaluation function
     *
     * @param player_ The playing player
     * @return An evaluation number
     */
    @Override
    public float evaluate(Colors player_) {
        return (float) pawns(player_) / (pawns(Colors.WHITE) + pawns(Colors.WHITE));
    }


    /**
     * Check if this state is the end of the game
     *
     * @param player_ The active player to check whether he can play or not
     * @return True if the active player cannot play
     */
    @Override
    public boolean gameOver(Colors player_) {
        return allMoves(player_).size() == 0 || pawns(player_) == 0;
    }


    /**
     * Check if a given move is possible or not
     *
     * @param move_ The move to check
     * @return True if move can be performed
     */
    @Override
    public boolean movePossible(IMove move_) {
        Point arrival = new Point(
                move_.arrivalPoint().x,
                move_.arrivalPoint().y
        );

        if (moveInBounds(move_)) {
            return isSquareFree(arrival);
        }

        return false;
    }


    /**
     * Check that the move won't apply out of the board bounds
     *
     * @param move_ The move we want to check
     * @return True if the move is valid
     */
    @Override
    public boolean moveInBounds(IMove move_) {
        return move_.arrivalPoint().x >= 0
                && move_.arrivalPoint().x < grid.length
                && move_.arrivalPoint().y >= 0
                && move_.arrivalPoint().y < grid.length;
    }


    /**
     * List all the possible moves for a player
     *
     * @return a List of all possible moves for a given player
     */
    @Override
    public Set<Move> allMoves(Colors player_) {
        Set<Move> moves = new HashSet<>();

        for (int line = 0; line < grid.length; line++) {
            for (int column = 0; column < grid[line].length; column++) {
                if (grid[line][column] != null && grid[line][column].equals(player_)) {
                    for (Directions direction : Directions.values()) {
                        for (int distance = 1; distance <= 2; distance++) {
                            Move move = new Move(direction, distance, new Point(line, column));

                            if (movePossible(move))
                                moves.add(move);
                        }
                    }
                }

            }
        }

        return moves;
    }


    /**
     * Infect all adjacent cells for a given square
     *
     * @param square_ The square in the middle of the adjacent cells
     */
    @Override
    public void infectAdjacentCells(Point square_) {
        if (square_.x + 1 < grid.length && grid[square_.x + 1][square_.y] != null) {
            grid[square_.x + 1][square_.y] = grid[square_.x][square_.y];
        }
        if (square_.x - 1 > 0 && grid[square_.x - 1][square_.y] != null) {
            grid[square_.x - 1][square_.y] = grid[square_.x][square_.y];
        }
        if (square_.y + 1 < grid[0].length && grid[square_.x][square_.y + 1] != null) {
            grid[square_.x][square_.y + 1] = grid[square_.x][square_.y];
        }
        if (square_.y - 1 > 0 && grid[square_.x][square_.y - 1] != null) {
            grid[square_.x][square_.y - 1] = grid[square_.x][square_.y];
        }
    }


    /**
     * Add a pawn on the game board
     *
     * @param square_ The cell to add a new pawn
     * @param player_ The color of the pawn
     */
    @Override
    public void addPawn(Point square_, Colors player_) {
        grid[square_.x][square_.y] = player_;
    }


    /**
     * Move a pawn from a square to another square
     *
     * @param move_ The movement
     */
    @Override
    public void movePawn(IMove move_) {
        grid[move_.arrivalPoint().x][move_.arrivalPoint().y] = grid[move_.startingSquare().x][move_.startingSquare().y];
        grid[move_.startingSquare().x][move_.startingSquare().y] = null;
    }

    public String toString() {
        StringBuilder output = new StringBuilder(new String());

        for (Colors[] colors : grid) {
            for (Colors color : colors) {
                output.append(color).append("\t");
            }
            output.append("\n");
        }

        return String.valueOf(output);
    }

}
