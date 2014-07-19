/*
 * File:  AI.java
 * Author:  Andrew Peters and Christian Rowlands
 * Last modified:  July 18, 2014
 */

package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * An AI object holds the current AI's position and the number of walls remaining.
 * In addition, it provides methods for having the AI make various moves.
 *
 * @author Andrew Peters and Christian Rowlands
 */
public class AI
{
    //Instance variables
    public Point aiPosition; //Stores the current AI Position on the game board
    public int numAIWallsRemaining;  //The AI starts with 10 walls
    private ArrayList<Point> visitedPositions;


    public AI()
    {
        aiPosition = new Point(5, 1);
        numAIWallsRemaining = 10; //The AI starts with 10 walls
        visitedPositions = new ArrayList<Point>();
    }

    /**
     * Finds the position in front of the user, and places a wall to block
     * the user's path.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aWallArray
     *              An ArrayList of walls on the game board.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of blocked horizontal paths on the game board.
     * @return Wall
     *              The wall that the AI placed on the board.  Null if the AI did not place a wall.
     */
    public Wall blockUserPathWithWall(Point aUserPosition, ArrayList<Wall> aWallArray, ArrayList<Point> aHorizontalBlockedPathList)
    {
        Wall wall = null;
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

        //First check to see if the AI has any walls left
        if(numAIWallsRemaining == 0)
            return null; //Return the null wall.

        //Next check to see if the user's path forward is already blocked.
        blockedPathIterator1 = aHorizontalBlockedPathList.iterator();
        while(blockedPathIterator1.hasNext())
        {
            Point tempPoint = blockedPathIterator1.next();
            if(tempPoint.x == aUserPosition.x && tempPoint.y == aUserPosition.y - 1)
                return null; //The user's path forward is already being blocked, so return null.
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
            blockedPathIterator2 = aHorizontalBlockedPathList.iterator();
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
                blockedPathIterator3 = aHorizontalBlockedPathList.iterator();
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
                return null; //We now know that both the left path is blocked and the right path is blocked, so a wall will not fit there. Return a null wall.
            else
            {
                //The center and right paths are open, so place a horizontal wall there.
                xLeft = aUserPosition.x;
                yLeft = aUserPosition.y - 1;
                xRight = aUserPosition.x + 1;
                yRight = aUserPosition.y - 1;
                Point tempLeftPoint = new Point(xLeft, yLeft);
                Point tempRightPoint = new Point(xRight, yRight);
                aHorizontalBlockedPathList.add(tempLeftPoint);
                aHorizontalBlockedPathList.add(tempRightPoint);

                xCenter = aUserPosition.x;
                yCenter = aUserPosition.y - 1;
                wall = new Wall(false, xCenter, yCenter);
                aWallArray.add(wall);
                numAIWallsRemaining--;
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
            aHorizontalBlockedPathList.add(tempLeftPoint);
            aHorizontalBlockedPathList.add(tempRightPoint);

            xCenter = aUserPosition.x - 1;
            yCenter = aUserPosition.y - 1;
            wall = new Wall(false, xCenter, yCenter);
            aWallArray.add(wall);
            numAIWallsRemaining--;
        }

        return wall;
    }

    /**
     * Places a vertical Wall next to a horizontal wall that is in front of the user's pawn.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aWallArray
     *              An ArrayList of walls on the game board.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of blocked horizontal paths on the game board.
     * @param aVerticalBlockedPathList
     *              An ArrayList of blocked vertical paths on the game board.
     * @return Wall
     *              The wall that the AI placed on the board.  Null if the AI did not place a wall.
     */
    public Wall placeVerticalWall(Point aUserPosition, ArrayList<Wall> aWallArray, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        Wall wall = null;

        //First check to see if the AI has any walls left
        if(numAIWallsRemaining == 0)
            return null; //Return the null wall since there are no remaining walls.

        //Now see if the user's pawn has a wall directly in front of it.
        if(aHorizontalBlockedPathList.contains(new Point(aUserPosition.x, aUserPosition.y - 1)))
        {
            System.out.println("The User has a wall directly in front of it.");
            //There is a wall directly in front of the user.  Now find the end of the wall row that is closest to the user.
            Point tempPointLeft = new Point(aUserPosition.x - 1, aUserPosition.y - 1);
            Point tempPointRight = new Point(aUserPosition.x + 1, aUserPosition.y - 1);
            int leftCount = 0;
            int rightCount = 0;
            boolean leftEdgeNotReached = true;
            boolean rightEdgeNotReached = true;
            boolean wallNotPlaced = false;

            //Check left.
            while(aHorizontalBlockedPathList.contains(tempPointLeft))
            {
                leftCount++;
                tempPointLeft.set(tempPointLeft.x - 1, tempPointLeft.y);
            }

            //Make sure the edge of the board was not reached.
            if(tempPointLeft.x < 1)
            {
                leftEdgeNotReached = false;
            }

            //Check right.
            while(aHorizontalBlockedPathList.contains(tempPointRight))
            {
                rightCount++;
                tempPointRight.set(tempPointRight.x + 1, tempPointRight.y);
            }

            //Make sure the edge of the board was not reached.
            if(tempPointRight.x > 9)
            {
                rightEdgeNotReached = false;
            }

            System.out.println("leftCount: " + leftCount + ", rightCount: " + rightCount);

            if(leftCount <= rightCount && leftEdgeNotReached)
            {
                System.out.println("Left count is better, and the left edge was not reached.");
                //The left side of the wall row is the best place to put the wall, but first check to see if a wall is already in that spot and also for the back edge of the board.
                Point tempWallCenter = new Point(tempPointLeft.x, tempPointLeft.y + 1);
                if(aWallArray.contains(new Wall(false, tempWallCenter.x, tempWallCenter.y)) || aWallArray.contains(new Wall(true, tempWallCenter.x, tempWallCenter.y)) || aUserPosition.y == 9)
                {
                    //There is already a wall there, try one spot up.
                    tempWallCenter.set(tempWallCenter.x, tempWallCenter.y - 1);
                    if(aWallArray.contains(new Wall(false, tempWallCenter.x, tempWallCenter.y)) || aWallArray.contains(new Wall(true, tempWallCenter.x, tempWallCenter.y)))
                    {
                        //There is a wall there too.
                        wallNotPlaced = true;
                    }
                    else
                    {
                        //There is not a wall in this spot.  Now check for overlapping walls.
                        Point topHalf = new Point(tempWallCenter);
                        Point bottomHalf = new Point(tempWallCenter.x, tempWallCenter.y + 1);
                        if(aVerticalBlockedPathList.contains(topHalf) || aVerticalBlockedPathList.contains(bottomHalf))
                        {
                            //The wall would overlap with another vertical wall.
                            wallNotPlaced = true;
                        }
                        else
                        {
                            //Everything checks out, place the wall.
                            aVerticalBlockedPathList.add(topHalf);
                            aVerticalBlockedPathList.add(bottomHalf);

                            wall = new Wall(true, tempWallCenter.x, tempWallCenter.y);
                            aWallArray.add(wall);
                            numAIWallsRemaining--;
                        }
                    }
                }
                else
                {
                    //The center point is open, but now check for overlapping walls.
                    Point topHalf = new Point(tempWallCenter);
                    Point bottomHalf = new Point(tempWallCenter.x, tempWallCenter.y + 1);
                    if(aVerticalBlockedPathList.contains(topHalf) || aVerticalBlockedPathList.contains(bottomHalf))
                    {
                        //The wall would overlap with another vertical wall.
                        wallNotPlaced = true;
                    }
                    else
                    {
                        //Everything checks out, place the wall.
                        aVerticalBlockedPathList.add(topHalf);
                        aVerticalBlockedPathList.add(bottomHalf);

                        wall = new Wall(true, tempWallCenter.x, tempWallCenter.y);
                        aWallArray.add(wall);
                        numAIWallsRemaining--;
                    }
                }
            }
            else
            {
                System.out.println("Right count is better or the left edge was reached");
                wallNotPlaced = true;
            }

            if((rightCount < leftCount || wallNotPlaced) && rightEdgeNotReached)
            {
                System.out.println("Trying to place a right wall now.");
                //Either the right side was the best, or the left side could not get a wall placed there.
                Point tempWallCenter = new Point(tempPointRight.x - 1, tempPointRight.y + 1);
                if(aWallArray.contains(new Wall(false, tempWallCenter.x, tempWallCenter.y)) || aWallArray.contains(new Wall(true, tempWallCenter.x, tempWallCenter.y)) || aUserPosition.y == 9)
                {
                    //There is already a wall there, try one spot up.
                    tempWallCenter.set(tempWallCenter.x, tempWallCenter.y - 1);
                    if(aWallArray.contains(new Wall(false, tempWallCenter.x, tempWallCenter.y)) || aWallArray.contains(new Wall(true, tempWallCenter.x, tempWallCenter.y)))
                    {
                        //There is a wall there too.
                        //noinspection UnusedAssignment
                        wallNotPlaced = true; //I know this assignment does not do anything, but I think it helps readability of the code.
                    }
                    else
                    {
                        //There is not a wall in this spot.  Now check for overlapping walls.
                        Point topHalf = new Point(tempWallCenter);
                        Point bottomHalf = new Point(tempWallCenter.x, tempWallCenter.y + 1);
                        if(aVerticalBlockedPathList.contains(topHalf) || aVerticalBlockedPathList.contains(bottomHalf))
                        {
                            //The wall would overlap with another vertical wall.
                            //noinspection UnusedAssignment
                            wallNotPlaced = true; //I know this assignment does not do anything, but I think it helps readability of the code.
                        }
                        else
                        {
                            //Everything checks out, place the wall.
                            aVerticalBlockedPathList.add(topHalf);
                            aVerticalBlockedPathList.add(bottomHalf);

                            wall = new Wall(true, tempWallCenter.x, tempWallCenter.y);
                            aWallArray.add(wall);
                            numAIWallsRemaining--;
                        }
                    }
                }
                else
                {
                    //The center point is open, but now check for overlapping walls.
                    Point topHalf = new Point(tempWallCenter);
                    Point bottomHalf = new Point(tempWallCenter.x, tempWallCenter.y + 1);
                    if(aVerticalBlockedPathList.contains(topHalf) || aVerticalBlockedPathList.contains(bottomHalf))
                    {
                        //The wall would overlap with another vertical wall.
                        //noinspection UnusedAssignment
                        wallNotPlaced = true; //I know this assignment does not do anything, but I think it helps readability of the code.
                    }
                    else
                    {
                        //Everything checks out, place the wall.
                        aVerticalBlockedPathList.add(topHalf);
                        aVerticalBlockedPathList.add(bottomHalf);

                        wall = new Wall(true, tempWallCenter.x, tempWallCenter.y);
                        aWallArray.add(wall);
                        numAIWallsRemaining--;
                    }
                }
            }
        }

        return wall;
    }

    /**
     * Places a Wall somewhere in front of the user.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aWallArray
     *              An ArrayList of walls on the game board.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of blocked horizontal paths on the game board.
     * @return Wall
     *              The wall that the AI placed on the board.  Null if the AI did not place a wall.
     */
    public Wall placeRandomWall(Point aUserPosition, ArrayList<Wall> aWallArray, ArrayList<Point> aHorizontalBlockedPathList)
    {
        Wall wall = null;
        int xLeft; //The x coordinate for the left blocked path by the wall to be placed
        int yLeft; //The y coordinate for the left blocked path by the wall to be placed
        int xRight; //The x coordinate for the right blocked path by the wall to be placed
        int yRight; //The y coordinate for the right blocked path by the wall to be placed
        int xCenter; //The x coordinate for the center point of the wall to be placed
        int yCenter; //The y coordinate for the center point of the wall to be placed
        boolean wallNotPlaced = true;

        //First check to see if the AI has any walls left
        if(numAIWallsRemaining == 0)
            return null; //Return the null wall since there are no remaining walls.

        //Get a random y value somewhere between row 0 and the users row.
        Random random = new Random();

        //Keep trying until a valid position is found.
        int index = 0;
        while(wallNotPlaced)
        {
            yCenter = random.nextInt(aUserPosition.y - 1) + 1; //Add 1 since there is not a row 0.

            //Get a random x value that still blocks the user's path.
            if(aUserPosition.x == 1)
            {
                //The user is on the left edge of the board.
                xCenter = 1;

            }
            else if(aUserPosition.x == 9)
            {
                //The user is on the right edge of the board.
                xCenter = 8;
            }
            else
            {
                xCenter = aUserPosition.x - random.nextInt(2);
            }

            //We have the center points for the wall.  Now check to see if there is a wall already there.
            Wall tempHorizontalWall = new Wall(false, xCenter, yCenter);
            Wall tempVerticalWall = new Wall(true, xCenter, yCenter);
            if(aWallArray.contains(tempHorizontalWall) || aWallArray.contains(tempVerticalWall))
                wallNotPlaced = true; //I know this is not needed because wallNotPlaced is already true, but I think it makes it easier to follow.
            else
            {
                //The center points are open, now check to make sure the sides of the wall are not going to overlap with another wall.
                xLeft = xCenter;
                yLeft = yCenter;
                xRight = xCenter + 1;
                yRight = yCenter;
                Point tempLeftPath = new Point(xLeft, yLeft);
                Point tempRightPath = new Point(xRight, yRight);

                if(aHorizontalBlockedPathList.contains(tempLeftPath) || aHorizontalBlockedPathList.contains(tempRightPath))
                    wallNotPlaced = true; //This new wall would overlap with an existing wall.
                else
                {
                    //The center point and the sides of the wall all check out.  Place the wall!
                    aHorizontalBlockedPathList.add(tempLeftPath);
                    aHorizontalBlockedPathList.add(tempRightPath);

                    wall = new Wall(false, xCenter, yCenter);
                    aWallArray.add(wall);
                    numAIWallsRemaining--;
                    wallNotPlaced = false;
                }
            }
            index++;
            if(index > 500)
                return wall;
        }

        return wall;
    }

    /**
     * Finds the shortest path to the other side of the board, and moves the AI's pawn one position down that path.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of horizontal blocked paths on the game board.
     * @param aVerticalBlockedPathList
     *              An ArrayList of vertical blocked paths on the game board.
     * @return boolean
     *              True if the shortest path was found, false otherwise.
     */
    public boolean makeGoodAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        ArrayList<ArrayList<Node<Point>>> moveCounts = new ArrayList<ArrayList<Node<Point>>>();
        Iterator<Node<Point>> iterator;
        boolean pathNotFound = true;
        visitedPositions.clear();

        //First, check to see if the users pawn can be jumped.
        if(isForwardPawnJumpPossible(aUserPosition, aHorizontalBlockedPathList))
        {
            //The users pawn can be jumped, but we need to see if there is a wall behind the user's pawn.
            if(!aHorizontalBlockedPathList.contains(aUserPosition))
            {
                //The path behind the user is NOT blocked, so jump the user.
                aiPosition.y = aiPosition.y + 2;
                return true; //The AI made its move by jumping the user's pawn.
            }
        }

        moveCounts.add(new ArrayList<Node<Point>>());

        //Add the first round of moves
        if(canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            moveCounts.get(0).add(new Node<Point>(new Point(aiPosition.x, aiPosition.y + 1), null));

        if(canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList))
            moveCounts.get(0).add(new Node<Point>(new Point(aiPosition.x - 1, aiPosition.y), null));

        if(canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList))
            moveCounts.get(0).add(new Node<Point>(new Point(aiPosition.x + 1, aiPosition.y), null));

        if(canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            moveCounts.get(0).add(new Node<Point>(new Point(aiPosition.x, aiPosition.y - 1), null));

        visitedPositions.add(aiPosition); //No reason to come back to where we have already visited.

        int length = moveCounts.get(0).size();
        for(int i = 0; i < length; i++)
        {
            if(moveCounts.get(0).get(i).point.y == 9)
            {
                //A winning move has been found!
                aiPosition.set(moveCounts.get(0).get(i).point.x, moveCounts.get(0).get(i).point.y);
                return true;
            }
        }

        int index = 1;
        while(pathNotFound)
        {
            //Add the next round of moves
            moveCounts.add(new ArrayList<Node<Point>>());
            length = moveCounts.get(index - 1).size();

            for(int i = 0; i < length; i++)
            {
                Point currentPosition;
                currentPosition = new Point(moveCounts.get(index - 1).get(i).point);
                visitedPositions.add(currentPosition); //No reason to keep coming back to where we have already visited.

                if(canAIMoveDown(null, currentPosition, aHorizontalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x, currentPosition.y + 1))))
                    moveCounts.get(index).add(new Node<Point>(new Point(currentPosition.x, currentPosition.y + 1), moveCounts.get(index - 1).get(i)));

                if(canAIMoveLeft(null, currentPosition, aVerticalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x - 1, currentPosition.y))))
                    moveCounts.get(index).add(new Node<Point>(new Point(currentPosition.x - 1, currentPosition.y), moveCounts.get(index - 1).get(i)));

                if(canAIMoveRight(null, currentPosition, aVerticalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x + 1, currentPosition.y))))
                    moveCounts.get(index).add(new Node<Point>(new Point(currentPosition.x + 1, currentPosition.y), moveCounts.get(index - 1).get(i)));

                if(canAIMoveUp(null, currentPosition, aHorizontalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x, currentPosition.y - 1))))
                    moveCounts.get(index).add(new Node<Point>(new Point(currentPosition.x, currentPosition.y - 1), moveCounts.get(index - 1).get(i)));
            }

            iterator = moveCounts.get(index).iterator();
            while(iterator.hasNext())
            {
                Node<Point> tempNode = iterator.next();
                if(tempNode.point.y == 9)
                {
                    //The winning path has been found!  Follow the path back to find the first move.
                    while(tempNode.parent != null)
                        tempNode = tempNode.parent;

                    aiPosition.set(tempNode.point.x, tempNode.point.y);

                    return true;
                }
            }
            //Just in case there is a condition not covered, make sure the loop does not go on forever.
            if(index > 1000)
                pathNotFound = false;

            index++;
        }

        return false;
    }

    /**
     * Pick a random spot to move the AI's pawn to.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of horizontal blocked paths on the game board.
     * @param aVerticalBlockedPathList
     *              An ArrayList of vertical blocked paths on the game board.
     */
    public boolean makeRandomAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        //First, check to see if the users pawn can be jumped.
        if(isForwardPawnJumpPossible(aUserPosition, aHorizontalBlockedPathList))
        {
            //The users pawn can be jumped, but we need to see if there is a wall behind the user's pawn.
            if(!aHorizontalBlockedPathList.contains(aUserPosition))
            {
                //The path behind the user is NOT blocked, so jump the user.
                aiPosition.y = aiPosition.y + 2;
                return true; //The AI made its move by jumping the user's pawn.
            }
        }

        //The AI pawn cannot jump the user's pawn.
        //Now, get all of the possible moves for the AI's pawn.
        boolean canMoveLeft = canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList);
        boolean canMoveRight = canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList);
        boolean canMoveUp = canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList);
        boolean canMoveDown = canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList);

        Random random = new Random();

        //Keep trying random moves until one of them is valid.  There are extra left, right, and down moves since they are better than up moves.
        for(int i = 0; i < 20; i++)
        {
            int index = random.nextInt(9);
            switch (index) {
                case 0:
                    if(canMoveUp)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y - 1);
                        return true;
                    }
                    break;
                case 1:
                    if(canMoveLeft)
                    {
                        aiPosition.set(aiPosition.x - 1, aiPosition.y);
                        return true;
                    }
                    break;
                case 2:
                    if(canMoveLeft)
                    {
                        aiPosition.set(aiPosition.x - 1, aiPosition.y);
                        return true;
                    }
                    break;
                case 3:
                    if(canMoveRight)
                    {
                        aiPosition.set(aiPosition.x + 1, aiPosition.y);
                        return true;
                    }
                    break;
                case 4:
                    if(canMoveRight)
                    {
                        aiPosition.set(aiPosition.x + 1, aiPosition.y);
                        return true;
                    }
                    break;
                case 5:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        return true;
                    }
                    break;
                case 6:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        return true;
                    }
                    break;
                case 7:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        return true;
                    }
                    break;
                case 8:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        return true;
                    }
                    break;
            }
        }
        return false;
    }

    /**
     * Figures out if the AI is in a position that allows it to jump the user's pawn.
     *
     * @param aUserPosition
     *              Point - the position of the user's pawn.
     * @param aHorizontalBlockedPathList
     *              ArrayList<Point> - an ArrayList of blocked paths on the game board.
     * @return boolean
     *              True if the AI's pawn is directly in front of the user's pawn and there is not a wall between them.
     */
    private boolean isForwardPawnJumpPossible(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList)
    {
        if(aiPosition.x == aUserPosition.x && aiPosition.y + 1 == aUserPosition.y)
        {
            //The user and AI pawns are face to face.

            // Now check to make sure there is not a wall between them.
            if(aHorizontalBlockedPathList.contains(aiPosition))
                return false;

            //There is not a wall between them.
            return true;
        }

        return false; //The AI and user pawns are not face to face...return false.
    }

    /**
     * Checks to see if moving to the left is a valid move for the AI's pawn.  Left is referring to the user's view
     * of left.  So as the user looks at the device screen, left is the same as the user's left.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aVerticalBlockedPathList
     *              An ArrayList of vertical blocked paths on the game board.
     * @return
     *              True if moving the AI's pawn left is valid.
     */
    private boolean canAIMoveLeft(Point aUserPosition, Point anAIPosition, ArrayList<Point> aVerticalBlockedPathList) {
        //First check to see if the AI Position is out of bounds.
        if (anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        if(aUserPosition != null)
        {
            //Check to see if the user is to the left of the AI.
            if(aUserPosition.equals(anAIPosition.x - 1, anAIPosition.y))
                return false;
        }

        //Now check for the edge of the board
        if(anAIPosition.x == 1)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        return !aVerticalBlockedPathList.contains(new Point(anAIPosition.x - 1, anAIPosition.y));
    }

    /**
     * Checks to see if moving to the right is a valid move for the AI's pawn.  Right is referring to the user's view
     * of right.  So as the user looks at the device screen, right is the same as the user's right.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aVerticalBlockedPathList
     *              An ArrayList of vertical blocked paths on the game board.
     * @return
     *              True if moving the AI's pawn right is valid.
     */
    private boolean canAIMoveRight(Point aUserPosition, Point anAIPosition, ArrayList<Point> aVerticalBlockedPathList) {
        //First check to see if the AI Position is out of bounds.
        if (anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        if(aUserPosition != null)
        {
            //Check to see if the user is to the right of the AI.
            if(aUserPosition.equals(anAIPosition.x + 1, anAIPosition.y))
                return false;
        }

        //Now check for the edge of the board
        if(anAIPosition.x == 9)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        return !aVerticalBlockedPathList.contains(anAIPosition);
    }

    /**
     * Checks to see if moving up is a valid move for the AI's pawn.  Up is referring to the user's view
     * of up.  So as the user looks at the device screen, up is the same as the user's up.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of horizontal blocked paths on the game board.
     * @return
     *              True if moving the AI's pawn up is valid.
     */
    private boolean canAIMoveUp(Point aUserPosition, Point anAIPosition, ArrayList<Point> aHorizontalBlockedPathList)
    {
        //First check to see if the AI Position is out of bounds.
        if(anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        if(aUserPosition != null)
        {
            //Check to see if the user is above the AI.
            if (aUserPosition.equals(anAIPosition.x, anAIPosition.y - 1))
                return false;
        }

        //Now check for the edge of the board
        if(anAIPosition.y == 1)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        //System.out.println("Possible AI Position in canAIMoveUp()  " + anAIPosition.toString());
        return !aHorizontalBlockedPathList.contains(new Point(anAIPosition.x, anAIPosition.y - 1));
    }

    /**
     * Checks to see if moving to the down is a valid move for the AI's pawn.  Down is referring to the user's view
     * of down.  So as the user looks at the device screen, down is the same as the user's down.
     *
     * @param aUserPosition
     *              The position of the user's pawn.
     * @param aHorizontalBlockedPathList
     *              An ArrayList of horizontal blocked paths on the game board.
     * @return
     *              True if moving the AI's pawn down is valid.
     */
    private boolean canAIMoveDown(Point aUserPosition, Point anAIPosition, ArrayList<Point> aHorizontalBlockedPathList) {
        //First check to see if the AI Position is out of bounds.
        if (anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        if(aUserPosition != null)
        {
            //Check to see if the user is above the AI.
            if(aUserPosition.equals(anAIPosition.x, anAIPosition.y + 1))
                return false;
        }

        //The position is not taken by the user's pawn, now test for wall.
        return !aHorizontalBlockedPathList.contains(anAIPosition);
    }

    /**
     * Private Node Class for the tree data structure.
     * @param <T>
     */
    private class Node<T> implements Cloneable
    {
        public T point;
        private Node<T> parent;

        private Node(T aPoint, Node<T> aParent)
        {
            point = aPoint;
            parent = aParent;
        }
    }
}

