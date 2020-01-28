package io.github.alexandregerault.infectiongame;

import java.awt.Point;

public interface IMove {
	int distance();

	Directions direction();

	Point startingSquare();
	
	Point arrivalPoint();
}
