package io.github.alexandregerault.infectiongame;

import java.awt.Point;
import java.util.Vector;

public interface IGameBoard {
	int pawns(Colors _player);

	int width();

	int height();
	
	float evaluate(Colors _player);
	
	boolean isSquareFree(Point _square);
	
	boolean gameOver(Colors _player);
	
	boolean movePossible(IMove _move);
	
	boolean moveInBounds(IMove _move);
	
	Vector<Move> allMoves(Colors _player);
	
	void addPawn(Point _square, Colors _player);
	
	void movePawn(IMove _move);
	
	void infectAdjacentCells(Point _square);
}
