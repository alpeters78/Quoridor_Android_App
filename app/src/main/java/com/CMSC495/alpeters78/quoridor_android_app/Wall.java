package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

/**
 * A Wall object records all the details about a Wall on the
 * Quoridor game board.  It stores the position of the wall and the orientation.
 */
public class Wall
{
    private Point position; //The center position of the wall.
    private boolean orientation; //True is vertical, false is horizontal.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wall wall = (Wall) o;

        if (orientation != wall.orientation) return false;
        if (!position.equals(wall.position)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + (orientation ? 1 : 0);
        return result;
    }

    /**
     * Constructor for a Wall object that takes three parameters.
     *
     * @param anOrientation
     *              boolean value - True for vertical, false for horizontal
     * @param x
     *              int value - the x coordinate
     * @param y
     *              int value - the y coordinate
     */
    public Wall(boolean anOrientation, int x, int y)
    {
        position = new Point(x, y);
        orientation = anOrientation;
    }

    public Point getPosition()
    {
        return position;
    }

    public void setPosition(Point aWallPosition)
    {
        position = aWallPosition;
    }

    public boolean getOrientation()
    {
        return orientation;
    }

    public void setOrientation(boolean anOrientation)
    {
        orientation = anOrientation;
    }
}
