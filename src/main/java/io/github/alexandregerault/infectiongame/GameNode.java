package io.github.alexandregerault.infectiongame;

import java.awt.*;

public final class GameNode {

    private final GameBoard board;
    private Colors activePlayer;

    /**
     * Initialize a default game node
     *
     * @param width_        The lines of the board
     * @param height_       The columns of the board
     * @param activePlayer_ The player that has to play
     */
    GameNode(int width_, int height_, Colors activePlayer_) {
        this.board = new GameBoard(width_, height_);

        this.activePlayer = activePlayer_;
    }

    /**
     * Create a new GameNode from a grid
     *
     * @param board_        A new board to create a new state
     * @param activePlayer_ The player that has to play
     */
    GameNode(GameBoard board_, Colors activePlayer_) {
        this.board = board_;
        this.activePlayer = activePlayer_;
    }

    /**
     * Apply a move modification to a new GameNode (~state)
     *
     * @param move_   The move the player played
     * @return A new GameNode
     */
    public GameNode newState(Move move_) {
        Point arrival = new Point(move_.startingSquare().x + move_.direction().x * move_.distance(),
                move_.startingSquare().y + move_.direction().y * move_.distance());

        GameBoard scopedBoard = new GameBoard(board);

        if (move_.distance() == 1) {
            scopedBoard.addPawn(arrival, activePlayer);
            scopedBoard.infectAdjacentCells(arrival);
        } else if (move_.distance() == 2) {
            scopedBoard.movePawn(move_);
        }

        return new GameNode(scopedBoard, activePlayer.equals(Colors.WHITE) ? Colors.BLACK : Colors.WHITE);
    }

    /**
     * Apply a move modification to a new GameNode (~state)
     *
     * @param move_   The move the player played
     * @return A new GameNode
     */
    public GameNode newState(Move move_, Colors player_) {
        GameNode returned = newState(move_);

        return returned.setPlayer(player_);
    }

    /**
     * Set the active player
     * @param player_ The active player
     * @return The GameNode (fluent setter)
     */
    private GameNode setPlayer(Colors player_) {
        this.activePlayer = player_;
        return this;
    }

    public final GameBoard board() {
        return board;
    }

    public final Colors player() { return activePlayer; }
}
