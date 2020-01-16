package io.github.alexandregerault.infectiongame;

import java.awt.*;

public class Move implements IMove {

    private Directions direction;
    private int distance;
    private Point start;


    /**
     * Basic move constructor
     *
     * @param _direction The direction of the movement
     * @param _distance  The distance (1 or 2) of the movement
     * @param _start     The square before the movement
     */
    Move(Directions _direction, int _distance, Point _start) {
        this.direction = _direction;
        this.distance = _distance;
        this.start = _start;
    }


    /**
     * Get's the move's direction
     *
     * @return Returns the move direction
     */
    public Directions direction() {
        return this.direction;
    }


    /**
     * Gets the move's distance
     *
     * @return Returns the distance
     */
    public int distance() {
        return this.distance;
    }


    /**
     * Gets the starting point
     *
     * @return The point where the pawn is before the movement
     */
    public Point startingSquare() {
        return this.start;
    }


    /**
     * Compute the arrival point
     *
     * @return The arrival Point
     */
    @Override
    public Point arrivalPoint() {
        return new Point(start.x + distance * direction.x, start.y + distance * direction.y);
    }

}
