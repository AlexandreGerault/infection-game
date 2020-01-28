package io.github.alexandregerault.infectiongame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class GameState {

    private final GameBoard board;
    private final Player activePlayer;

    /**
     * Initialize a default game node
     *
     * @param width_        The lines of the board
     * @param height_       The columns of the board
     * @param activePlayer_ The player that has to play
     */
    GameState(int width_, int height_, Player activePlayer_) {
        this.board = new GameBoard(width_, height_);

        this.activePlayer = activePlayer_;
    }
    
    /**
     * Create a new GameNode from a grid
     *
     * @param board_        A new board to create a new state
     * @param activePlayer_ The player that has to play
     */
    GameState(GameBoard board_, Player activePlayer_) {
        this.board = new GameBoard(board_);
        this.activePlayer = activePlayer_;
    }
    
    
    /**
     * Apply a move modification to a new GameNode (~state)
     *
     * @param move_   The move the player played
     * @return A new GameNode
     */
    public GameState newState(IMove move_, Player nextPlayer_) {
    	Point arrival = move_.end();

        GameBoard scopedBoard = new GameBoard(board);

        if (move_.distance() == 1) {
            scopedBoard.addPawn(arrival, activePlayer.color());
            scopedBoard.infectAdjacentCells(arrival);
        } else if (move_.distance() == 2) {
            scopedBoard.movePawn(move_);
        }
        
        return new GameState(scopedBoard, nextPlayer_);
    }
    
    public GameState newState(IMove move_) {
    	return newState(move_, Game.otherPlayer());
    }
    
    
    public final IMove bestMove(boolean pruning) {
    	Map<Move, Float> playerPossibilities = new HashMap<Move, Float>();
    	
    	if (board.allMoves(activePlayer.color()).size() == 0) {
    		System.out.println("Should be game over. . .");
    	}
    	board.allMoves(activePlayer.color()).forEach(move -> {
    		if(pruning)
                playerPossibilities.put(move, Minimax.alphaBeta(this, activePlayer.depth(), Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, activePlayer.color()));
            else
                playerPossibilities.put(move, Minimax.minmax(this, activePlayer.depth(), activePlayer.color()));
    	});

        Map.Entry<Move, Float> toPlay = null;
        
        if(playerPossibilities.size() < 1) {
        	System.out.println("FAIL. SYSTEM EXIT (CODE 11)");
        	System.exit(11);
        }
        
        for (Map.Entry<Move, Float> entry : playerPossibilities.entrySet()) {
            if (toPlay == null || entry.getValue().compareTo(toPlay.getValue()) > 0) {
                toPlay = entry;
            }
        }
        
        return toPlay.getKey();
    }
    

    public final GameBoard board() {
        return board;
    }
    

    public final Player player() { return activePlayer; }

}
