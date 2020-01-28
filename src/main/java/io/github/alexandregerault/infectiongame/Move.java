package io.github.alexandregerault.infectiongame;

import java.awt.*;

public class Move implements IMove {

    private Directions direction;
    private int distance;
    private Point start;
    private Point end;


    /**
     * Basic move constructor
     *
     * @param direction_ The direction of the movement
     * @param distance_  The distance (1 or 2) of the movement
     * @param start_     The square before the movement
     */
    Move(Directions direction_, int distance_, Point start_) {
        this.direction = direction_;
        this.distance = distance_;
        this.start = start_;
        this.end = new Point(start.x + distance * direction.x, start.y + distance * direction.y);
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
    public Point end() {
        return this.end;
    }

}
