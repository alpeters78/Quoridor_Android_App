package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingDeque;

/**
 * An AI object holds the current AI's position and the number of walls remaining.
 * In addition, it provides methods for having the AI make various moves.
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
     *              Point - the users current position.
     * @param aWallArray
     *              ArrayList<Wall> - an ArrayList of walls on the game board.
     * @param aBlockedPathList
     *              ArrayList<Point> - an ArrayList of blocked paths on the game board.
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    private boolean blockUserPathWithWall(Point aUserPosition, ArrayList<Wall> aWallArray, ArrayList<Point> aBlockedPathList)
    {
        Wall wall;
        Iterator<Wall> wallIterator;
        Iterator<Point> blockedPathIterator1;
        Iterator<Point> blockedPathIterator2;
        Iterator<Point> blockedPathIterator3;
        int xLeft; //The x coordinate for the left blocked path by the wall to be placed
        int yLeft; //The y coordinate for the left blocked path by the wall to be placed
        int xRight; //The x coordinate for the right blocked path by the wall to be placed
        int yRight; //The y coordinate for the right blocked path by the wall to be placed
        int xCenter; //The x coordinate for the center point of the wall to be placed
        int yCenter; //The y coordinate for the center point of the wall to be placed
        boolean isLeftPathBlocked = false; //Assume the left path is not blocked until is is checked later on.
        boolean isRightPathBlocked = false; //Assume the left path is not blocked until is is checked later on.

        //First check to see if the user's path forward is already blocked.
        blockedPathIterator1 = aBlockedPathList.iterator();
        while(blockedPathIterator1.hasNext())
        {
            Point tempPoint = blockedPathIterator1.next();
            if(tempPoint.x == aUserPosition.x && tempPoint.y == aUserPosition.y - 1)
                return false; //The user's path forward is already being blocked, so return false.
        }

        //Now that we know the user's path forward is not being blocked, check to see if the left and right center wall positions are open.
        int xCenterLeftTemp = aUserPosition.x - 1;
        int yCenterLeftTemp = aUserPosition.y - 1;
        Wall tempHorizontalWallLeft = new Wall(false, xCenterLeftTemp, yCenterLeftTemp);
        Wall tempVerticalWallLeft = new Wall(true, xCenterLeftTemp, yCenterLeftTemp);
        if(aWallArray.contains(tempHorizontalWallLeft) || aWallArray.contains(tempVerticalWallLeft))
            isLeftPathBlocked = true; //The left center point is already taken.

        int xCenterRightTemp = aUserPosition.x;
        int yCenterRightTemp = aUserPosition.y - 1;
        Wall tempHorizontalWallRight = new Wall(false, xCenterRightTemp, yCenterRightTemp);
        Wall tempVerticalWallRight = new Wall(true, xCenterRightTemp, yCenterRightTemp);
        if(aWallArray.contains(tempHorizontalWallRight) || aWallArray.contains(tempVerticalWallRight))
            isRightPathBlocked = true; //The right center point is already taken.

        //Now we know the user's path forward is not being blocked, and we know if the left and right center positions are open.
        if(aUserPosition.x != 1) //False if the user is on the left side of the board.
        {
            blockedPathIterator2 = aBlockedPathList.iterator();
            while(blockedPathIterator2.hasNext())
            {
                Point tempPoint = blockedPathIterator2.next();
                if(tempPoint.x == aUserPosition.x - 1 && tempPoint.y == aUserPosition.y - 1)
                    isLeftPathBlocked = true;
            }
        }
        else
        {
            isLeftPathBlocked = true; //The user is on the left side of the board, so don't do anything with the left path.
        }

        if(isLeftPathBlocked)
        {
            //The left path is blocked, so try the right path.
            if(aUserPosition.x != 9) //False if the user is on the right side of the board.
            {
                blockedPathIterator3 = aBlockedPathList.iterator();
                while(blockedPathIterator3.hasNext())
                {
                    Point tempPoint = blockedPathIterator3.next();
                    if(tempPoint.x == aUserPosition.x + 1 && tempPoint.y == aUserPosition.y - 1)
                        isRightPathBlocked = true;
                }
            }
            else
            {
                isRightPathBlocked = true;
            }

            if(isRightPathBlocked)
            {
                return false; //We now know that both the left path is blocked and the right path is blocked, so a wall will not fit there.
            }
            else
            {
                //The center and right paths are open, so place a horizontal wall there.
                xLeft = aUserPosition.x;
                yLeft = aUserPosition.y - 1;
                xRight = aUserPosition.x + 1;
                yRight = aUserPosition.y - 1;
                Point tempLeftPoint = new Point(xLeft, yLeft);
                Point tempRightPoint = new Point(xRight, yRight);
                aBlockedPathList.add(tempLeftPoint);
                aBlockedPathList.add(tempRightPoint);

                xCenter = aUserPosition.x;
                yCenter = aUserPosition.y - 1;
                Wall tempWall = new Wall(false, xCenter, yCenter);
                aWallArray.add(tempWall);
            }
        }
        else
        {
            //The left path is NOT blocked, so we have found two consecutive horizontal paths that are free.  Place a horizontal wall there.
            xLeft = aUserPosition.x - 1;
            yLeft = aUserPosition.y - 1;
            xRight = aUserPosition.x;
            yRight = aUserPosition.y - 1;
            Point tempLeftPoint = new Point(xLeft, yLeft);
            Point tempRightPoint = new Point(xRight, yRight);
            aBlockedPathList.add(tempLeftPoint);
            aBlockedPathList.add(tempRightPoint);

            xCenter = aUserPosition.x - 1;
            yCenter = aUserPosition.y - 1;
            Wall tempWall = new Wall(false, xCenter, yCenter);
            aWallArray.add(tempWall);
        }

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
    private boolean makeGoodAIPawnMove()
    {


        return false;
    }

    /**
     * Pick a random spot to move the AI's pawn to.
     *
     * @param aUserPosition
     *              Point - the position of the user's pawn.
     * @param aBlockedPathList
     *              ArrayList<Point> - an ArrayList of blocked paths on the game board.
     * @return boolean
     *              True if the AI's pawn was moved, false otherwise.
     */
    private boolean makeRandomAIPawnMove(Point aUserPosition, ArrayList<Point> aBlockedPathList)
    {
        //First, check to see if the users pawn can be jumped.
        if(isPawnJumpPossible(aUserPosition, aBlockedPathList))
        {
            //The users pawn can be jumped, but we need to see if there is a wall behind the user's pawn.
            if(!aBlockedPathList.contains(aUserPosition))
            {
                //The path behind the user is NOT blocked, so jump the user.
                aiPosition.y = aiPosition.y + 2;
                return true; //The AI made its move by jumping the user's pawn.
            }
            else
            {
                //The path behind the user is blocked, but it still might be possible to move left or right.
                //TODO Pickup here - CR
            }
        }

        //The AI pawn cannot jump the user's pawn.
        //Now, get all of the possible moves for the AI's pawn.
        boolean canMoveLeft;
        boolean canMoveRight;
        boolean canMoveUp;
        boolean canMoveDown;

        return true;
    }

    /**
     * Figures out if the AI is in a position that allows it to jump the user's pawn.
     *
     * @param aUserPosition
     *              Point - the position of the user's pawn.
     * @param aBlockedPathList
     *              ArrayList<Point> - an ArrayList of blocked paths on the game board.
     * @return boolean
     *              True if the AI's pawn is directly in front of the user's pawn and there is not a wall between them.
     */
    private boolean isPawnJumpPossible(Point aUserPosition, ArrayList<Point> aBlockedPathList)
    {
        if(aiPosition.x == aUserPosition.x && aiPosition.y == aUserPosition.y + 1)
        {
            //The user and AI pawns are face to face.

            // Now check to make sure there is not a wall between them.
            Point tempPath = new Point(aiPosition.x, aiPosition.y);
            if(aBlockedPathList.contains(tempPath))
                return false;

            //There is not a wall between them.
            return true;
        }

        return false; //The AI and user pawns are not face to face...return false.
    }
}
