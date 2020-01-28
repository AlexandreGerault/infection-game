package io.github.alexandregerault.infectiongame;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.Test;

class MoveTest {

	@Test
	void testEndPoint() {
		Move move = new Move(Directions.DOWN, 1, new Point(0,0));
		
		assertEquals(new Point(0,1), move.end());
	}

}
