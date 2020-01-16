package io.github.alexandregerault.infectiongame;

import java.awt.*;

public final class GameNode {

    private GameBoard board;
    private Colors activePlayer;

    /**
     * Initialize a default game node
     *
     * @param _width        The lines of the board
     * @param _height       The columns of the board
     * @param _activePlayer The player that has to play
     */
    GameNode(int _width, int _height, Colors _activePlayer) {
        this.board = new GameBoard(_width, _height);

        this.activePlayer = _activePlayer;
    }

    /**
     * Create a new GameNode from a grid
     *
     * @param _board        A new board to create a new state
     * @param _activePlayer The player that has to play
     */
    GameNode(GameBoard _board, Colors _activePlayer) {
        this.board = _board;
        this.activePlayer = _activePlayer;
    }

    /**
     * Apply a move modification to a new GameNode (~state)
     *
     * @param _move   The move the player played
     * @return A new GameNode
     */
    public GameNode newState(Move _move) {
        Point arrival = new Point(_move.startingSquare().x + _move.direction().x * _move.distance(),
                _move.startingSquare().y + _move.direction().y * _move.distance());

        GameBoard scopedBoard = new GameBoard(board);

        scopedBoard.addPawn(arrival, activePlayer);

        if (_move.distance() == 1) {
            scopedBoard.infectAdjacentCells(arrival);
        } else if (_move.distance() == 2) {
            scopedBoard.movePawn(_move);
        }

        return new GameNode(scopedBoard, activePlayer.equals(Colors.WHITE) ? Colors.BLACK : Colors.WHITE);
    }

    public final GameBoard board() {
        return board;
    }
}
