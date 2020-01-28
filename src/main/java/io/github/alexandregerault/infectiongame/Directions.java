package io.github.alexandregerault.infectiongame;

public enum Directions
{
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    public int x;
    public int y;

    Directions(int x, int y)
    {
        this.y = y;
        this.x = x;
    }
}
