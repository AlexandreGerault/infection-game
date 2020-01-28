package io.github.alexandregerault.infectiongame;

public final class Player {
	
	private final Colors color;
	private final int depth;
	
	Player(Colors color_, int depth_) {
		this.color = color_;
		this.depth = depth_;
	}
	
	public final Colors color() {
		return color;
	}
	
	public final int depth() {
		return depth;
	}
}
