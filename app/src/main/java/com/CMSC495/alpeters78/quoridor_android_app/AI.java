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
    private ArrayList<Point> checkedPositions;
    private ArrayList<Point> visitedPositions;


    public AI()
    {
        aiPosition = new Point(5, 1);
        numAIWallsRemaining = 10; //The AI starts with 10 walls
        checkedPositions = new ArrayList<Point>();
        visitedPositions = new ArrayList<Point>();
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
        System.out.println("Ai method");
        for(int i = aWallArray.size()-1; i >= 0; i--) {
            Wall wallCenterPoint = aWallArray.get(i);
            System.out.println(wallCenterPoint.getPosition());
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
                aWallArray.add(wall); //this changes the local aWallArray not the global wallArray
                // TODO The value of the reference to the global wallArray was passed in as a parameter, so it should be adding a wall to the object in the above line of code.
                // TODO If I did something like aWallArray = new ArrayList<Wall>; then that would only change the local variable since there is a separate copy of the reference
                // TODO that can only be used in this method.  At least, this is how I understand Java Parameters.
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
            aWallArray.add(wall); //this changes the local aWallArray not the global wallArray
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

    public boolean makeGoodAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        ArrayList<ArrayList<Node<Point>>> moveCounts = new ArrayList<ArrayList<Node<Point>>>();
        Iterator<Node<Point>> iterator;
        boolean pathNotFound = true;
        visitedPositions.clear();

        moveCounts.add(new ArrayList<Node<Point>>());

        //Add the first round of moves
        if(canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            moveCounts.get(0).add(new Node(new Point(aiPosition.x, aiPosition.y + 1), null));

        if(canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList))
            moveCounts.get(0).add(new Node(new Point(aiPosition.x - 1, aiPosition.y), null));

        if(canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList))
            moveCounts.get(0).add(new Node(new Point(aiPosition.x + 1, aiPosition.y), null));

        if(canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            moveCounts.get(0).add(new Node(new Point(aiPosition.x, aiPosition.y - 1), null));

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
                Point currentPosition = new Point(moveCounts.get(index - 1).get(i).point);
                visitedPositions.add(currentPosition); //No reason to keep coming back to where we have already visited.

                if(canAIMoveDown(null, currentPosition, aHorizontalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x, currentPosition.y + 1))))
                    moveCounts.get(index).add(new Node(new Point(currentPosition.x, currentPosition.y + 1), moveCounts.get(index - 1).get(i)));

                if(canAIMoveLeft(null, currentPosition, aVerticalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x - 1, currentPosition.y))))
                    moveCounts.get(index).add(new Node(new Point(currentPosition.x - 1, currentPosition.y), moveCounts.get(index - 1).get(i)));

                if(canAIMoveRight(null, currentPosition, aVerticalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x + 1, currentPosition.y))))
                    moveCounts.get(index).add(new Node(new Point(currentPosition.x + 1, currentPosition.y), moveCounts.get(index - 1).get(i)));

                if(canAIMoveUp(null, currentPosition, aHorizontalBlockedPathList) && (!visitedPositions.contains(new Point(currentPosition.x, currentPosition.y - 1))))
                    moveCounts.get(index).add(new Node(new Point(currentPosition.x, currentPosition.y - 1), moveCounts.get(index - 1).get(i)));
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
            if(index > 10000)
                pathNotFound = false;

            index++;
        }

        return false;
    }

    /**
     * Calculates the shortest path to the other end of the board,
     * and moves the AI's pawn to the next point on that path.
     *
     * @return boolean
     *              True if the AI's pawn was moved, false otherwise.
     */
    /*public boolean makeGoodAIPawnMove(Point aUserPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList)
    {
        System.out.println("The makeGoodAIPawnMove() method was called.");
        int downMoveCount = 0;
        int upMoveCount = 0;
        int leftMoveCount = 0;
        int rightMoveCount = 0;

        checkedPositions.clear();
        checkedPositions.add(aiPosition);
        visitedPositions.clear();
        visitedPositions.add(aiPosition);
        if(canAIMoveDown(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            downMoveCount = nextMove(aUserPosition, new Point(aiPosition.x, aiPosition.y + 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, 0) + 1;
        else
            downMoveCount = 200;

        System.out.println("Down move count after recursive call: " + downMoveCount);

        visitedPositions.clear();
        visitedPositions.add(aiPosition);
        if(canAIMoveLeft(aUserPosition, aiPosition, aVerticalBlockedPathList))
            leftMoveCount = nextMove(aUserPosition, new Point(aiPosition.x - 1, aiPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, 0);
        else
            leftMoveCount = 200;

        visitedPositions.clear();
        visitedPositions.add(aiPosition);
        if(canAIMoveRight(aUserPosition, aiPosition, aVerticalBlockedPathList))
            rightMoveCount = nextMove(aUserPosition, new Point(aiPosition.x + 1, aiPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, 0);
        else
            rightMoveCount = 200;

        visitedPositions.clear();
        visitedPositions.add(aiPosition);
        if(canAIMoveUp(aUserPosition, aiPosition, aHorizontalBlockedPathList))
            upMoveCount = nextMove(aUserPosition, new Point(aiPosition.x, aiPosition.y - 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, 0);
        else
            upMoveCount = 200;

        System.out.println("The Move Counts -" + "  Down: " + downMoveCount + "  Up: " + upMoveCount + "  Left: " + leftMoveCount + "  Right: " + rightMoveCount);

        if(downMoveCount <= upMoveCount)
        {
            if(downMoveCount <= leftMoveCount)
            {
                if(downMoveCount <= rightMoveCount)
                {
                    //A down move is the most efficient.
                    aiPosition.set(aiPosition.x, aiPosition.y + 1);
                    System.out.println("End of the makeGoodAIPawnMove() method; Down Move was picked.");
                    return true;
                }
            }
        }

        if(leftMoveCount <= rightMoveCount)
        {
            if(leftMoveCount <= upMoveCount)
            {
                //A left move is the most efficient.
                aiPosition.set(aiPosition.x - 1, aiPosition.y);
                System.out.println("End of the makeGoodAIPawnMove() method; Left Move was picked.");
                return true;
            }
        }

        if(rightMoveCount <= upMoveCount)
        {
            //A right move is the most efficient.

            aiPosition.set(aiPosition.x + 1, aiPosition.y);
            System.out.println("End of the makeGoodAIPawnMove() method; Right Move was picked.");
            return true;
        }
        else
        {
            //An up move is the most efficient.
            aiPosition.set(aiPosition.x, aiPosition.y - 1);
            System.out.println("End of the makeGoodAIPawnMove() method; Up Move was picked.");
            return true;
        }
    }

    private int nextMove(Point aUserPosition, Point aNextPosition, ArrayList<Point> aHorizontalBlockedPathList, ArrayList<Point> aVerticalBlockedPathList, int aMoveCount)
    {
        //checkedPositions.add(aNextPosition);
        aMoveCount++;
        System.out.println("aMoveCount: " + aMoveCount);

        int downMoveCount = aMoveCount;
        int upMoveCount = aMoveCount;
        int leftMoveCount = aMoveCount;
        int rightMoveCount = aMoveCount;

        int downMoveReturn = 0;
        int upMoveReturn = 0;
        int leftMoveReturn = 0;
        int rightMoveReturn = 0;

        if(aNextPosition.y == 9)
        {
            System.out.println("The base case has been reached; returning 0");
            return 0;
        }
        else
        {
            if(aMoveCount > 30 || aNextPosition.equals(aiPosition))
            {
                System.out.println("The max number of moves has been reached, returning 21.");
                return 199;
            }
            else
            {
                visitedPositions.add(aNextPosition);

                if(canAIMoveDown(aUserPosition, aNextPosition, aHorizontalBlockedPathList) && (!checkedPositions.contains(new Point(aNextPosition.x, aNextPosition.y + 1))) && (!visitedPositions.contains(new Point(aNextPosition.x, aNextPosition.y + 1)))) {
                    System.out.println("The AI can move down");
                    downMoveReturn =  nextMove(aUserPosition, new Point(aNextPosition.x, aNextPosition.y + 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, downMoveCount) + 1;
                    System.out.println("nextMove() just returned a downMoveCount value of " + downMoveReturn);
                }
                else
                    downMoveReturn = 200;

                if(canAIMoveLeft(aUserPosition, aNextPosition, aVerticalBlockedPathList) && (!checkedPositions.contains(new Point(aNextPosition.x - 1, aNextPosition.y))) && (!visitedPositions.contains(new Point(aNextPosition.x - 1, aNextPosition.y)))) {
                    System.out.println("The AI can move left");
                    leftMoveReturn = nextMove(aUserPosition, new Point(aNextPosition.x - 1, aNextPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, leftMoveCount) + 1;
                    System.out.println("nextMove() just returned a leftMoveCount value of " + leftMoveReturn);
                }
                else
                    leftMoveReturn = 200;

                if(canAIMoveRight(aUserPosition, aNextPosition, aVerticalBlockedPathList) && (!checkedPositions.contains(new Point(aNextPosition.x + 1, aNextPosition.y))) && (!visitedPositions.contains(new Point(aNextPosition.x + 1, aNextPosition.y)))) {
                    System.out.println("The AI can move right");
                    rightMoveReturn = nextMove(aUserPosition, new Point(aNextPosition.x + 1, aNextPosition.y), aHorizontalBlockedPathList, aVerticalBlockedPathList, rightMoveCount) + 1;
                    System.out.println("nextMove() just returned a rightMoveCount value of " + rightMoveReturn);
                }
                else
                    rightMoveReturn = 200;

                if(canAIMoveUp(aUserPosition, aNextPosition, aHorizontalBlockedPathList) && (!checkedPositions.contains(new Point(aNextPosition.x, aNextPosition.y - 1))) && (!visitedPositions.contains(new Point(aNextPosition.x, aNextPosition.y - 1)))) {
                    System.out.println("The AI can move up");
                    upMoveReturn = nextMove(aUserPosition, new Point(aNextPosition.x, aNextPosition.y - 1), aHorizontalBlockedPathList, aVerticalBlockedPathList, upMoveCount) + 1;
                    System.out.println("nextMove() just returned a upMoveCount value of " + upMoveReturn);
                }
                else
                    upMoveReturn = 200;

                //if(aMoveCount > 10)
                //    checkedPositions.remove(aNextPosition);
                visitedPositions.remove(aNextPosition);


                System.out.println("End of nextMove() - "+ "  Down: " + downMoveReturn + "  Up: " + upMoveReturn + "  Left: " + leftMoveReturn + "  Right: " + rightMoveReturn);
                //Return the smallest of the four moves
                if(downMoveReturn <= upMoveReturn)
                {
                    if(downMoveReturn <= leftMoveReturn)
                    {
                        if(downMoveReturn <= rightMoveReturn)
                        {
                            //A down move is the most efficient.
                            if(downMoveReturn <= 31)
                                checkedPositions.add(aNextPosition);
                            return downMoveReturn;
                        }
                    }
                }

                if(leftMoveReturn <= rightMoveReturn)
                {
                    if(leftMoveReturn <= upMoveReturn)
                    {
                        //A left move is the most efficient.
                        if(leftMoveReturn <= 31)
                            checkedPositions.add(aNextPosition);
                        return leftMoveReturn;
                    }
                }

                if(rightMoveReturn <= upMoveReturn)
                {
                    //A right move is the most efficient.
                    if(rightMoveReturn <= 31)
                        checkedPositions.add(aNextPosition);
                    return rightMoveReturn;
                }
                else
                {
                    //An up move is the most efficient.
                    if(upMoveReturn <= 31)
                        checkedPositions.add(aNextPosition);
                    return upMoveReturn;
                }
            }
        }
    }*/

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
        //TODO The randomness of this method can be tweaked as needed.
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
                //TODO We probably don't have to implement this check, but I left a place for it if we do.
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

