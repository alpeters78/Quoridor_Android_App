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
            if(aVBlockedPathList.contains(new Point(userPosition.x - 1, userPosition.y)))
            {
                //The position was a left move, but the path is blocked
                return false;
            }
            return true;
        }
        else if(userPosition.x != 9 && aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y))
        {
            //The new position is a right move, see if a wall is blocking its path
            if(aVBlockedPathList.contains(new Point(userPosition)))
            {
                //The position was a right move, but the path is blocked
                return false;
            }
            return true;
        }
        //Next, check to see if the AI's pawn is next to the user's pawn
        else if(userPosition.x == anAIPawnPosition.x && userPosition.y == anAIPawnPosition.y + 1)
        {
            //The AI's pawn is directly in front of the user's pawn, now check if there is a wall or edge behind the AI
            if(aHBlockedPathList.contains(new Point(userPosition.x, userPosition.y - 2)) || anAIPawnPosition.y == 1)
            {
                //The AI's pawn has a wall behind it (or edge of the board), so the user should be allowed to jump left or right
                if(aPossibleNewPawnPosition.equals(userPosition.x - 1, userPosition.y - 1) || aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y - 1))
                {
                    //Everything checks out, so let the user jump left or right
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
            else
            {
                //There is not a wall behind the AI's pawn, so the only valid jump position is directly behind the AI's pawn
                if(aPossibleNewPawnPosition.equals(userPosition.x, userPosition.y - 2))
                {
                    //Everything checks out, so let the user jump away!
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
        }
        else if(userPosition.x == anAIPawnPosition.x && userPosition.y == anAIPawnPosition.y - 1)
        {
            //The AI's pawn is directly behind the user's pawn, now check if there is a wall in front of the AI's pawn
            if(aHBlockedPathList.contains(new Point(userPosition.x, userPosition.y + 1)))
            {
                //The AI's pawn has a wall or edge in front of it, so the user should be allowed to jump left or right
                if(aPossibleNewPawnPosition.equals(userPosition.x - 1, userPosition.y + 1) || aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y + 1))
                {
                    //Everything checks out, so let the user jump left or right
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
            else
            {
                //There is not a wall or edge in front of the AI's pawn, so the only valid jump position is directly in front of the AI's pawn
                if(aPossibleNewPawnPosition.equals(userPosition.x, userPosition.y + 2))
                {
                    //Everything checks out, so let the user jump away!
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
        }
        else if(userPosition.x == anAIPawnPosition.x + 1 && userPosition.y == anAIPawnPosition.y)
        {
            //The AI's pawn is directly to the left of the user's pawn, now check if there is a wall or edge to the left of the AI's pawn
            if(aVBlockedPathList.contains(new Point(userPosition.x - 2, userPosition.y)) || anAIPawnPosition.x == 1)
            {
                //The AI's pawn has a wall or edge to the left of it, so the user should be allowed to jump up or down
                if(aPossibleNewPawnPosition.equals(userPosition.x - 1, userPosition.y - 1) || aPossibleNewPawnPosition.equals(userPosition.x - 1, userPosition.y + 1))
                {
                    //Everything checks out, so let the user jump up or down
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
            else
            {
                //There is not a wall or edge to the left of the AI's pawn, so the only valid jump position is directly left of the AI's pawn
                if(aPossibleNewPawnPosition.equals(userPosition.x - 2, userPosition.y))
                {
                    //Everything checks out, so let the user jump away!
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
        }
        else if(userPosition.x == anAIPawnPosition.x - 1 && userPosition.y == anAIPawnPosition.y)
        {
            //The AI's pawn is directly to the right of the user's pawn, now check if there is a wall or edge to the right of the AI's pawn
            if(aVBlockedPathList.contains(new Point(userPosition.x + 1, userPosition.y)) || anAIPawnPosition.x == 9)
            {
                //The AI's pawn has a wall or edge to the right of it, so the user should be allowed to jump up or down
                if(aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y - 1) || aPossibleNewPawnPosition.equals(userPosition.x + 1, userPosition.y + 1))
                {
                    //Everything checks out, so let the user jump up or down
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
            else
            {
                //There is not a wall or edge to the right of the AI's pawn, so the only valid jump position is directly left of the AI's pawn
                if(aPossibleNewPawnPosition.equals(userPosition.x + 2, userPosition.y))
                {
                    //Everything checks out, so let the user jump away!
                    return true;
                }
                else
                {
                    //The user tried to jump to an invalid position
                    return false;
                }
            }
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
