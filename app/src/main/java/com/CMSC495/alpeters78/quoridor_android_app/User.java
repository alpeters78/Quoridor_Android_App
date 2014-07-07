package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

/**
 * A User object holds the current User's position and the number of walls remaining.
 */
public class User
{
    //Instance variables
    public Point userPosition; //Point(int x, int y)
    private Point oldUserPosition; //Stores the last user position for the undo move button
    public int numUserWallsRemaining; //The user starts with 10 walls

    public User()
    {
        userPosition = new Point(5, 1); //Point(int x, int y)
        numUserWallsRemaining = 10; //The user starts with 10 walls
    }

    public boolean undoLastMove()
    {
        if(oldUserPosition == null)
        {
            //Last move was a wall placement
            //TODO Remove the wall that was placed - CR
        }
        else
            userPosition = oldUserPosition;

        return true;
    }


}
