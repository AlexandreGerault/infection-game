package io.github.alexandregerault.infectiongame;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private static Player active;
	private static Player black;
	private static Player white;
	private boolean pruning;
	private int aheadMoves;
	private List<GameState> states;
	
	Game(Player black_, Player white_, boolean pruning_, int width_, int height_, int aheadMoves_) {
		Game.black = black_;
		Game.white = white_;
		this.pruning = pruning_;
		this.aheadMoves = aheadMoves_;
		this.states = new ArrayList<>();
		this.states.add(new GameState(width_, height_, Game.white));
		Game.active = white;
	}
	
	public void aheadMoves() {
		for (int i = 0; i < aheadMoves; i++) {
			if(!lastState().board().gameOver(active.color()))
				this.states.add(lastState().newState(lastState().bestMove(pruning), active));
		}
	}
	
	public void loop() {
		while (! lastState().board().gameOver(active.color()) && ! lastState().board().gameOver(otherPlayer().color())) {			
			this.states.add(lastState().newState(lastState().bestMove(pruning), active));
			active = otherPlayer();
		}
		
		System.out.println("Game Over. Le joueur " + otherPlayer().color().toString() + " a perdu.");
		System.out.println(lastState().board());
	}
	
	private GameState lastState() {
		return states.get(states.size() - 1);
	}
	
	public static Player otherPlayer() {
		return active.equals(black) ? white : black;
	}
	
	public String toString() {
        StringBuilder output = new StringBuilder(new String());

        for (GameState state : states) {
        	output.append(state.board().toString()).append("\n\n");
        }

        return String.valueOf(output);
	}
}
