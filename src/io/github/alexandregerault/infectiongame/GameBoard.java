package io.github.alexandregerault.infectiongame;

import java.awt.Point;
import java.util.Arrays;
import java.util.Vector;

public final class GameBoard implements IGameBoard {

    private Colors[][] grid;


    /**
     * Basic constructor
     *
     * @param _width  The width of the board
     * @param _height The height of the board
     */
    GameBoard(int _width, int _height) {
        this.grid = new Colors[_width][_height];

        this.grid[0][0] = Colors.BLACK;
        this.grid[_width - 1][_height - 1] = Colors.WHITE;
    }


    /**
     * Copy constructor
     *
     * @param _board The copied board
     */
    public GameBoard(GameBoard _board) {
        this.grid = _board.grid;
    }


    /**
     * Count the amount of pawns for a player
     *
     * @param _player The player we want to count the pawns
     * @return The amount of pawns the player has on the board
     */
    @Override
    public int pawns(Colors _player) {
        return (int) Arrays.stream(grid)
                .flatMap(Arrays::stream)
                .filter(color -> color.equals(_player))
                .count();
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
     * @param _square The square to check whether it is free or not
     * @return True if the square is free
     */
    @Override
    public boolean isSquareFree(Point _square) {
        return grid[_square.x][_square.y] == null;
    }


    /**
     * Evaluation function
     *
     * @param _player The playing player
     * @return An evaluation number
     */
    @Override
    public float evaluate(Colors _player) {
        return (float) pawns(_player) / (pawns(Colors.WHITE) + pawns(Colors.WHITE));
    }


    /**
     * Check if this state is the end of the game
     *
     * @return True if the active player cannot play
     */
    @Override
    public boolean gameOver(Colors _player) {
        return allMoves(_player).size() == 0 || pawns(_player) == 0;
    }


    /**
     * Check if a given move is possible or not
     *
     * @param _move The move to check
     * @return True if move can be performed
     */
    @Override
    public boolean movePossible(IMove _move) {
        Point arrival = new Point(
                _move.startingSquare().x + _move.distance() * _move.direction().x,
                _move.startingSquare().y + _move.distance() * _move.direction().y
        );

        return moveInBounds(_move) && isSquareFree(arrival);
    }


    /**
     * Check that the move won't apply out of the board bounds
     *
     * @param _move The move we want to check
     * @return True if the move is valid
     */
    @Override
    public boolean moveInBounds(IMove _move) {
        return _move.direction().x * _move.distance() >= 0
                && _move.direction().x * _move.distance() < grid.length
                && _move.direction().y * _move.distance() >= 0
                && _move.direction().y * _move.distance() < grid.length;
    }


    /**
     * List all the possible moves for a player
     *
     * @return a Vector of all possible moves for a given player
     */
    @Override
    public Vector<Move> allMoves(Colors _player) {
        Vector<Move> moves = new Vector<>();

        for (int line = 0; line < grid.length; line++) {
            for (int column = 0; column < grid[line].length; column++) {
                for (Directions direction : Directions.values()) {
                    for (int distance = 1; distance <= 2; distance++) {
                        Move move = new Move(direction, distance, new Point(line, column));

                        if (grid[line][column].equals(_player) && movePossible(move))
                            moves.add(move);
                    }
                }
            }
        }

        return moves;
    }


    /**
     * Infect all adjacent cells for a given square
     *
     * @param _square The square in the middle of the adjacent cells
     */
    @Override
    public void infectAdjacentCells(Point _square) {
        if (_square.x + 1 < grid.length && grid[_square.x + 1][_square.y] != null) {
            grid[_square.x + 1][_square.y] = grid[_square.x][_square.y];
        }
        if (_square.x - 1 > 0 && grid[_square.x - 1][_square.y] != null) {
            grid[_square.x - 1][_square.y] = grid[_square.x][_square.y];
        }
        if (_square.y + 1 < grid[0].length && grid[_square.x][_square.y + 1] != null) {
            grid[_square.x][_square.y + 1] = grid[_square.x][_square.y];
        }
        if (_square.y - 1 > 0 && grid[_square.x + 1][_square.y] != null) {
            grid[_square.x][_square.y - 1] = grid[_square.x][_square.y];
        }
    }


    /**
     * Add a pawn on the game board
     *
     * @param _square The cell to add a new pawn
     * @param _player The color of the pawn
     */
    @Override
    public void addPawn(Point _square, Colors _player) {
        grid[_square.x][_square.y] = _player;
    }


    /**
     * Move a pawn from a square to another square
     *
     * @param _move The movement
     */
    @Override
    public void movePawn(IMove _move) {
        grid[_move.arrivalPoint().x][_move.arrivalPoint().y] = grid[_move.startingSquare().x][_move.startingSquare().y];
        grid[_move.startingSquare().x][_move.startingSquare().y] = null;
    }

}
