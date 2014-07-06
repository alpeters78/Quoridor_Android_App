package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by christian on 7/6/14.
 */
public class AI
{
    //Instance variables
    public Point aiPosition; //Stores the current AI Position on the game board
    public int numAIWallsRemaining;  //The AI starts with 10 walls

    public AI()
    {
        aiPosition = new Point(5, 9); //TODO is this the right default spot for the AI? - CR
        numAIWallsRemaining = 10; //The AI starts with 10 walls
    }




    /**
     * Finds the position in front of the user, and places a wall to block
     * the user's path.
     *
     * @param aUserPosition
     *              Point value - the users current position.
     * @param aWallArray
     *              ArrayList<Wall> value - an ArrayList of walls on the game board.
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    private boolean blockUserPathWithWall(Point aUserPosition, ArrayList<Wall> aWallArray)
    {
        Wall wall;
        Iterator<Wall> iterator;
        int x;
        int y;

        if(aUserPosition.x == 9)
            x = aUserPosition.x - 1; //Shift the position left to compensate for the end of the board.
        else
            x = aUserPosition.x;

        y = aUserPosition.y - 1;

        iterator = aWallArray.iterator();
        while(iterator.hasNext())
        {
            Wall tempWall = iterator.next();
            if(tempWall.getPosition().equals(x, y))
                return false; //The wall spot was already taken by another wall.

            //TODO Check to see if the wall overlaps with another wall. - CR

        }

        wall = new Wall(false, x, y);
        aWallArray.add(wall);

        //TODO Possibly add logic to also include adding vertical walls. - CR
        //TODO Possibly add logic to add the wall in a different position if that spot is already taken. - CR

        return true;
    }

    /**
     * Places a Wall in a random position somewhere near the user.
     *
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    private boolean placeRandomWallNearUser()
    {
        Wall wall;
        Iterator<Wall> iterator;
        int x;
        int y;

        return false;
    }

    /**
     * Places a Wall somewhere next to another wall already on the board.
     *
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    private boolean placeRandomWallNextToAnotherWall()
    {
        return false;
    }

    /**
     * Calculates the shortest path to the other end of the board,
     * and moves the AI's pawn to the next point on that path.
     *
     * @return boolean
     *              True if the AI's pawn was moved, false otherwise.
     */
    private boolean makeGoodAIMove()
    {
        return false;
    }

    /**
     * Pick a random spot to move the AI's pawn to.
     *
     * @return boolean
     *              True if the AI's pawn was moved, false otherwise.
     */
    private boolean makeRandomAIMove()
    {
        return false;
    }
}
