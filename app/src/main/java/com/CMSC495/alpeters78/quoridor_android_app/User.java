package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

import java.util.ArrayList;

/**
 * A User object holds the current User's position and the number of walls remaining.
 */
public class User
{
    //Instance variables
    public Point userPosition; //Point(int x, int y)
    public Point oldUserPosition; //Stores the last user position for the undo move button
    public int numUserWallsRemaining; //The user starts with 10 walls


    public User()
    {
        userPosition = new Point(5, 9); //Point(int x, int y)
        oldUserPosition = new Point();
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

    /**
     * Checks to see if a point on the board is a valid move for the user's pawn.
     *
     * @param aPossibleNewPawnPosition
     *              The point to check if it is a valid move for the user's pawn.
     * @param aHBlockedPathList
     *              All the blocked paths by horizontal walls.
     * @param aVBlockedPathList
     *              All the blocked paths by vertical walls.
     * @return
     *              True if aPossibleNewPawnPosition is a valid move for the user's pawn.
     */
    public boolean isValidPawnMove(Point aPossibleNewPawnPosition, Point anAIPawnPosition, ArrayList<Point> aHBlockedPathList, ArrayList<Point> aVBlockedPathList)
    {
        //First check to see if the new position is on top of the AI's pawn
        if(aPossibleNewPawnPosition.equals(anAIPawnPosition))
        {
            return false;
        }
        // Now check to see if the new position is a forward move, backward move, left move, and finally a right move
        else if(aPossibleNewPawnPosition.equals(userPosition.x, userPosition.y - 1))
        {
            //The new position is a forward move, see if a wall is blocking its path
            if(aHBlockedPathList.contains(new Point(userPosition.x, userPosition.y - 1)))
            {
                //The position was a move forward, but the path is blocked
                return false;
            }
            return true;
        }
        else if(userPosition.y != 9 && aPossibleNewPawnPosition.equals(userPosition.x, userPosition.y + 1))
        {
            //The new position is a backward move, see if a wall is blocking its path
            if(aHBlockedPathList.contains(new Point(userPosition)))
            {
                //The position was a backward move, but the path is blocked
                return false;
            }
            return true;
        }
        else if(userPosition.x != 1 && aPossibleNewPawnPosition.equals(userPosition.x - 1, userPosition.y))
        {
            //The new position is a left move, see if a wall is blocking its path
            if(aHBlockedPathList.contains(new Point(userPosition.x - 1, userPosition.y)))
            {
                //The position was a backward move, but the path is blocked
                return false;
            }
            return true;
        }
        else if(userPosition.x != 9 && aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y))
        {
            //The new position is a right move, see if a wall is blocking its path
            if(aHBlockedPathList.contains(new Point(userPosition)))
            {
                //The position was a backward move, but the path is blocked
                return false;
            }
            return true;
        }
        //Next, check to see if the new position is jumping the AI's pawn
        else if(userPosition.x == anAIPawnPosition.x && userPosition.y == anAIPawnPosition.y + 1)
        {
            //The AI's pawn is in front of the user's pawn
            if(aPossibleNewPawnPosition.equals(userPosition.x, userPosition.y - 2))
            {
                //The user is trying to jump straight ahead, check for a blocked path (wall)
                if(aHBlockedPathList.contains(new Point(userPosition.x, userPosition.y - 2)))
                {
                    //There is a wall there blocking the path
                    return false;
                }
                else
                {
                    //Everything checks out, so let the user jump away!
                    return true;
                }
            }
            //TODO finish logic
        }
        else if(userPosition.x == anAIPawnPosition.x && userPosition.y == anAIPawnPosition.y - 1)
        {
            //The AI's pawn is behind the user's pawn
            //TODO finish logic
        }
        else if(userPosition.x == anAIPawnPosition.x + 1 && userPosition.y == anAIPawnPosition.y)
        {
            //The AI's pawn is to the left of the user's pawn
            //TODO finish logic
        }
        else if(userPosition.x == anAIPawnPosition.x - 1 && userPosition.y == anAIPawnPosition.y)
        {
            //The AI's pawn is to the right of the user's pawn
            //TODO finish logic
        }

        return false;
    }

    public boolean isValidHorizontalWallMove(Point aPossibleNewWallPosition, Point anAIPawnPosition, ArrayList<Point> aHBlockedPathList, ArrayList<Point> aVBlockedPathList)
    {
        Point newBlockedPath1 = new Point(aPossibleNewWallPosition);
        Point newBlockedPath2 = new Point(aPossibleNewWallPosition.x + 1, aPossibleNewWallPosition.y);
        if(isAIPathCompletelyBlockedByNewHorizontalWall(newBlockedPath1, newBlockedPath2))
        {
            //The placement of the new horizontal wall completely blocks the AI's path to win; therefore, it is an invalid move
            return false;
        }
        else
        {
            //TODO Figure out the rest of the logic
            return true;
        }
    }

    public boolean isValidVerticalWallMove(Point aPossibleNewWallPosition, Point anAIPawnPosition, ArrayList<Point> aHBlockedPathList, ArrayList<Point> aVBlockedPathList)
    {
        Point newBlockedPath1 = new Point(aPossibleNewWallPosition);
        Point newBlockedPath2 = new Point(aPossibleNewWallPosition.x, aPossibleNewWallPosition.y + 1);
        if(isAIPathCompletelyBlockedByNewVerticalWall(newBlockedPath1, newBlockedPath2))
        {
            //The placement of the new vertical wall completely blocks the AI's path to win; therefore, it is an invalid move
            return false;
        }
        else
        {
            //TODO Figure out the rest of the logic
            return true;
        }
    }

    /**
     * Sets the current user position to the value provided as a parameter.  The
     * value provided as a parameter is assumed to be valid; that is, no checking
     * is done in this method.
     *
     * @param aValidNewPosition
     *              A new user pawn position that is valid.
     */
    public void setNewUserPosition(Point aValidNewPosition)
    {
        oldUserPosition.set(userPosition.x, userPosition.y);
        userPosition.set(aValidNewPosition.x, aValidNewPosition.y);
    }

    private boolean isAIPathCompletelyBlockedByNewHorizontalWall(Point aNewBlockedPath1, Point aNewBlockedPath2)
    {
        //TODO Figure out this method
        return false;
    }

    private boolean isAIPathCompletelyBlockedByNewVerticalWall(Point aNewBlockedPath1, Point aNewBlockedPath2)
    {
        //TODO Figure out this method
        return false;
    }
}
