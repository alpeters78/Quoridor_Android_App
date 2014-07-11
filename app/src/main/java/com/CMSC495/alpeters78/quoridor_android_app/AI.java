package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * An AI object holds the current AI's position and the number of walls remaining.
 * In addition, it provides methods for having the AI make various moves.
 */
public class AI
{
    //Instance variables
    public Point aiPosition; //Stores the current AI Position on the game board
    public int numAIWallsRemaining;  //The AI starts with 10 walls
    private enum moveEnum {LEFT, RIGHT, DOWN};


    public AI()
    {
        aiPosition = new Point(5, 1);
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
     * @param aHorizontalBlockedPathList
     *              ArrayList<Point> - an ArrayList of blocked paths on the game board.
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    public Wall blockUserPathWithWall(Point aUserPosition, ArrayList<Wall> aWallArray, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        Wall wall = null;
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

        //First check to see if the AI has any walls left
        if(numAIWallsRemaining == 0)
        {
            return wall; //Return the null wall.
        }
        //Next check to see if the user's path forward is already blocked.
        blockedPathIterator1 = aHorizontalBlockedPathList.iterator();
        while(blockedPathIterator1.hasNext())
        {
            Point tempPoint = blockedPathIterator1.next();
            if(tempPoint.x == aUserPosition.x && tempPoint.y == aUserPosition.y - 1)
                return wall; //The user's path forward is already being blocked, so return null.
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
            {
                return wall; //We now know that both the left path is blocked and the right path is blocked, so a wall will not fit there. Return a null wall.
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
     * Places a Wall in a random position somewhere near the user.
     *
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    public boolean placeRandomWallNearUser()
    {
        Wall wall;
        Iterator<Wall> iterator;
        int x;
        int y;

        //TODO Complete this method.

        return false;
    }

    /**
     * Places a Wall somewhere next to another wall already on the board.
     *
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    public boolean placeRandomWallNextToAnotherWall()
    {
        //TODO Complete this method.

        return false;
    }

    /**
     * Calculates the shortest path to the other end of the board,
     * and moves the AI's pawn to the next point on that path.
     *
     * @return boolean
     *              True if the AI's pawn was moved, false otherwise.
     */
    public boolean makeGoodAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        //TODO I thought I would give a recursion an attempt.  However, it seems I have failed so far.  Feel free to take a look and see where I went wrong.
        int downMoveCount = 0;
        int upMoveCount = 0;
        int leftMoveCount = 0;
        int rightMoveCount = 0;

        if(canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            downMoveCount = nextMove(aUserPosition, new Point(aiPosition.x, aiPosition.y + 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, downMoveCount);

        if(canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            upMoveCount = nextMove(aUserPosition, new Point(aiPosition.x, aiPosition.y - 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, upMoveCount);

        if(canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList))
            leftMoveCount = nextMove(aUserPosition, new Point(aiPosition.x - 1, aiPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, leftMoveCount);

        if(canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList))
            rightMoveCount = nextMove(aUserPosition, new Point(aiPosition.x + 1, aiPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, rightMoveCount);

        if(downMoveCount <= upMoveCount)
        {
            if(downMoveCount <= leftMoveCount)
        {
                if(downMoveCount <= rightMoveCount)
                {
                    //A down move is the most efficient.
                    aiPosition.set(aiPosition.x, aiPosition.y + 1);
                    return true;
                }
            }
        }

        if(upMoveCount <= leftMoveCount)
        {
            if(upMoveCount <= rightMoveCount)
            {
                //An up move is the most efficient.
                aiPosition.set(aiPosition.x, aiPosition.y - 1);
                return true;
            }
        }

        if(leftMoveCount <= rightMoveCount)
        {
            //A left move is the most efficient.
            aiPosition.set(aiPosition.x - 1, aiPosition.y);
            return true;
        }
        else
        {
            //A right move is the most efficient.
            aiPosition.set(aiPosition.x + 1, aiPosition.y);
            return true;
        }
    }

    private int nextMove(Point aUserPosition, Point aNextPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList, int aMoveCount)
    {
        if(aNextPosition.y == 9)
            return aMoveCount;
        else
        {
            if(aMoveCount > 20)
                return 21;
            else
            {
                if(canAIMoveDown(aUserPosition, aNextPosition, aHorizontalBlockedPathList))
                    return nextMove(aUserPosition, new Point(aNextPosition.x, aNextPosition.y + 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, aMoveCount++);

                if(canAIMoveUp(aUserPosition, aNextPosition, aHorizontalBlockedPathList))
                    return nextMove(aUserPosition, new Point(aNextPosition.x, aNextPosition.y - 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, aMoveCount++);

                if(canAIMoveLeft(aUserPosition, aNextPosition, aVerticalBlockedPathList))
                    return nextMove(aUserPosition, new Point(aNextPosition.x - 1, aNextPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, aMoveCount++);

                if(canAIMoveRight(aUserPosition, aNextPosition, aVerticalBlockedPathList))
                    return nextMove(aUserPosition, new Point(aNextPosition.x + 1, aNextPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, aMoveCount++);
            }
        }

        return 21;
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
    public void makeRandomAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        //TODO I am NOT really happy with this method, and I think it could be tweaked as we decide how the AI should work

        //First, check to see if the users pawn can be jumped.
        if(isForwardPawnJumpPossible(aUserPosition, aHorizontalBlockedPathList))
        {
            //The users pawn can be jumped, but we need to see if there is a wall behind the user's pawn.
            if(!aHorizontalBlockedPathList.contains(aUserPosition))
            {
                //The path behind the user is NOT blocked, so jump the user.
                aiPosition.y = aiPosition.y + 2;
                return; //The AI made its move by jumping the user's pawn.
            }
            else
            {
                //The path behind the user is blocked, but it still might be possible to move left or right.
                //TODO Pickup here - CR
            }
        }

        //The AI pawn cannot jump the user's pawn.
        //Now, get all of the possible moves for the AI's pawn.
        boolean canMoveLeft = canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList);
        boolean canMoveRight = canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList);
        boolean canMoveUp = canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList);
        boolean canMoveDown = canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList);

        boolean hasMoved = false;
        Random random = new Random();

        //Keep trying random moves until one of them is valid.  There are extra left, right, and down moves since they are better than up moves.
        while(!hasMoved)
        {
            int index = random.nextInt(9);

            switch (index) {
                case 0:
                    if(canMoveUp)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y - 1);
                        hasMoved = canMoveUp;
                        break;
                    }
                case 1:
                    if(canMoveLeft)
                    {
                        aiPosition.set(aiPosition.x - 1, aiPosition.y);
                        hasMoved = canMoveLeft;
                        break;
                    }
                case 2:
                    if(canMoveLeft)
                    {
                        aiPosition.set(aiPosition.x - 1, aiPosition.y);
                        hasMoved = canMoveLeft;
                        break;
                    }
                case 3:
                    if(canMoveRight)
                    {
                        aiPosition.set(aiPosition.x + 1, aiPosition.y);
                        hasMoved = canMoveRight;
                        break;
                    }
                case 4:
                    if(canMoveRight)
                    {
                        aiPosition.set(aiPosition.x + 1, aiPosition.y);
                        hasMoved = canMoveRight;
                        break;
                    }
                case 5:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        hasMoved = canMoveDown;
                        break;
                    }
                case 6:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        hasMoved = canMoveDown;
                        break;
                    }
                case 7:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        hasMoved = canMoveDown;
                        break;
                    }
                case 8:
                    if(canMoveDown)
                    {
                        aiPosition.set(aiPosition.x, aiPosition.y + 1);
                        hasMoved = canMoveDown;
                        break;
                    }
            }
        }
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
        if(aiPosition.x == aUserPosition.x && aiPosition.y == aUserPosition.y + 1)
        {
            //The user and AI pawns are face to face.

            // Now check to make sure there is not a wall between them.
            Point tempPath = new Point(aiPosition.x, aiPosition.y);
            if(aHorizontalBlockedPathList.contains(tempPath))
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
     */
    private boolean canAIMoveLeft(Point aUserPosition, Point anAIPosition, ArrayList<Point> aVerticalBlockedPathList)
    {
        //Check to see if the AI Position is out of bounds.
        if(anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        //First check to see if the user is to the left of the AI.
        if(aUserPosition.equals(anAIPosition.x - 1, anAIPosition.y))
            return false;

        //Now check for the edge of the board
        if(aiPosition.x == 1)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        if(aVerticalBlockedPathList.contains(new Point(anAIPosition.x - 1, anAIPosition.y)))
        {
            //There is a wall blocking the path to that position.
            return false;
        }
        else
        {
            //No wall, and no user pawn.  The move is valid.
            return true;
        }
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
     */
    private boolean canAIMoveRight(Point aUserPosition, Point anAIPosition, ArrayList<Point> aVerticalBlockedPathList)
    {
        //Check to see if the AI Position is out of bounds.
        if(anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        //First check to see if the user is to the right of the AI.
        if(aUserPosition.equals(anAIPosition.x + 1, anAIPosition.y))
            return false;

        //Now check for the edge of the board
        if(aiPosition.x == 9)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        if(aVerticalBlockedPathList.contains(anAIPosition))
        {
            //There is a wall blocking the path to that position.
            return false;
        }
        else
        {
            //No wall, and no user pawn.  The move is valid.
            return true;
        }
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
     */
    private boolean canAIMoveUp(Point aUserPosition, Point anAIPosition, ArrayList<Point> aHorizontalBlockedPathList)
    {
        //Check to see if the AI Position is out of bounds.
        if(anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        //First check to see if the user is above the AI.
        if(aUserPosition.equals(anAIPosition.x, anAIPosition.y - 1))
            return false;

        //Now check for the edge of the board
        if(aiPosition.y == 1)
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        System.out.println("Possible AI Position in canAIMoveUp()  " + anAIPosition.toString());
        if(aHorizontalBlockedPathList.contains(new Point(anAIPosition.x, anAIPosition.y - 1)))
        {
            //There is a wall blocking the path to that position.
            return false;
        }
        else
        {
            //No wall, and no user pawn.  The move is valid.
            return true;
        }
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
     */
    private boolean canAIMoveDown(Point aUserPosition, Point anAIPosition, ArrayList<Point> aHorizontalBlockedPathList)
    {
        //Check to see if the AI Position is out of bounds.
        if(anAIPosition.x < 1 || 9 < anAIPosition.x || anAIPosition.y < 1 || 9 < anAIPosition.y)
            return false;

        //First check to see if the user is above the AI.
        if(aUserPosition.equals(anAIPosition.x, anAIPosition.y + 1))
            return false;

        //The position is not taken by the user's pawn, now test for wall.
        if(aHorizontalBlockedPathList.contains(anAIPosition))
        {
            //There is a wall blocking the path to that position.
            return false;
        }
        else
        {
            //No wall, and no user pawn.  The move is valid.
            return true;
        }
    }
}
