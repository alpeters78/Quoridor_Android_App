package com.CMSC495.alpeters78.quoridor_android_app;

import android.graphics.Point;
import android.widget.ImageView;
import com.CMSC495.alpeters78.quoridor_android_app.GameActivity;
import java.util.ArrayList;
import java.util.Iterator;

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
       //TEST BLOCK
        System.out.println("Previous move valid positions");
        System.out.println("runs before move is made");
        ArrayList<Point> validPositions = getUserValidNextPositions(anAIPawnPosition, userPosition, aHBlockedPathList, aVBlockedPathList);
        Iterator<Point> validPositionsIterator = validPositions.iterator();
        while(validPositionsIterator.hasNext()){

            System.out.println(validPositionsIterator.next());
        }
        //END TEST BLOCK

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

    public boolean isValidHorizontalWallMove(Point aPossibleNewWallPosition, Point anAIPawnPosition, ArrayList<Point> aHBlockedPathList, ArrayList<Point> aVBlockedPathList, ArrayList<Point> placedWalls)
    {
        Point newBlockedPath1 = new Point(aPossibleNewWallPosition);
        Point newBlockedPath2 = new Point(aPossibleNewWallPosition.x + 1, aPossibleNewWallPosition.y);
        ArrayList<Point> newaHBlockedPathList = new ArrayList<Point>();

        //add new paths

        newaHBlockedPathList.add(newBlockedPath1);
        newaHBlockedPathList.add(newBlockedPath2);
        Iterator<Point> pathListIterator = aHBlockedPathList.iterator();
        while(pathListIterator.hasNext()){
            newaHBlockedPathList.add(pathListIterator.next());
        }
        boolean test1 = isWinningPathBlocked(userPosition, anAIPawnPosition, newaHBlockedPathList, aVBlockedPathList, 9);//test AI path
        boolean test2 = isWinningPathBlocked(anAIPawnPosition, userPosition, newaHBlockedPathList, aVBlockedPathList, 1);//test User path

        //TEST BLOCK
        System.out.println("Is AI win blocked? " + test1);
        System.out.println("Is User win blocked? " + test2);
        System.out.println(newBlockedPath1);
        System.out.println(newBlockedPath2);
        System.out.println("Ai valid next position list");
        ArrayList<Point> validPositions = getUserValidNextPositions(userPosition, anAIPawnPosition, aHBlockedPathList, aVBlockedPathList);
        Iterator<Point> validPositionsIterator = validPositions.iterator();
        while(validPositionsIterator.hasNext()){
            System.out.println(validPositionsIterator.next());
        }
        System.out.println("User valid next position list");
        ArrayList<Point> validUserPositions = getUserValidNextPositions(anAIPawnPosition, userPosition, aHBlockedPathList, aVBlockedPathList);
        Iterator<Point> validUserPositionsIterator = validUserPositions.iterator();
        while(validUserPositionsIterator.hasNext()){
            System.out.println(validUserPositionsIterator.next());
        }
        System.out.println("path1 path2  wallCenter");
        boolean path1 = aHBlockedPathList.contains(newBlockedPath1);
        boolean path2 = aHBlockedPathList.contains(newBlockedPath2);
        boolean wallCenter = placedWalls.contains(aPossibleNewWallPosition);
        System.out.println(path1 + " " + path2 + " " + wallCenter);
        //END TEST BLOCK

        if(test1 || test2)
        {
            //The placement of the new horizontal wall completely blocks the AI's path to win; therefore, it is an invalid move
            return false;
        }
        else
        {
            if(aHBlockedPathList.contains(newBlockedPath1) || aHBlockedPathList.contains(newBlockedPath2) || placedWalls.contains(aPossibleNewWallPosition)){
                return false;
            }
            else {
                return true;
            }
        }
    }

    public boolean isValidVerticalWallMove(Point aPossibleNewWallPosition, Point anAIPawnPosition, ArrayList<Point> aHBlockedPathList, ArrayList<Point> aVBlockedPathList, ArrayList<Point> placedWalls)
    {
        Point newBlockedPath1 = new Point(aPossibleNewWallPosition);
        Point newBlockedPath2 = new Point(aPossibleNewWallPosition.x, aPossibleNewWallPosition.y + 1);
        ArrayList<Point> newaVBlockedPathList = new ArrayList<Point>();

        //add new paths
        newaVBlockedPathList.add(newBlockedPath1);
        newaVBlockedPathList.add(newBlockedPath2);

        Iterator<Point> pathListIterator = aVBlockedPathList.iterator();
        while(pathListIterator.hasNext()){
            newaVBlockedPathList.add(pathListIterator.next());
        }

        boolean test1 = isWinningPathBlocked(userPosition, anAIPawnPosition, aHBlockedPathList, newaVBlockedPathList, 9);//test AI path
        boolean test2 = isWinningPathBlocked(anAIPawnPosition, userPosition, aHBlockedPathList, newaVBlockedPathList, 1);//test User path

        //TEST BLOCK
        System.out.println("Is AI win blocked? " + test1);
        System.out.println("Is User win blocked? " + test2);
        System.out.println(newBlockedPath1);
        System.out.println(newBlockedPath2);
        System.out.println("Ai valid next position list");
        ArrayList<Point> validPositions = getUserValidNextPositions(userPosition, anAIPawnPosition, aHBlockedPathList, aVBlockedPathList);
        Iterator<Point> validPositionsIterator = validPositions.iterator();
        while(validPositionsIterator.hasNext()){
            System.out.println(validPositionsIterator.next());
        }
        System.out.println("User valid next position list");
        ArrayList<Point> validUserPositions = getUserValidNextPositions(anAIPawnPosition, userPosition, aHBlockedPathList, aVBlockedPathList);
        Iterator<Point> validUserPositionsIterator = validUserPositions.iterator();
        while(validUserPositionsIterator.hasNext()){
            System.out.println(validUserPositionsIterator.next());
        }
        System.out.println("path1 path2  wallCenter");
        boolean path1 = aVBlockedPathList.contains(newBlockedPath1);
        boolean path2 = aVBlockedPathList.contains(newBlockedPath2);
        boolean wallCenter = placedWalls.contains(aPossibleNewWallPosition);
        System.out.println(path1 + " " + path2 + " " + wallCenter);
        //END TEST BLOCK

        if(test1 || test2)
        {
            //The placement of the new vertical wall completely blocks the AI's path to win; therefore, it is an invalid move
            return false;
        }
        else
        {
            //check path's and center against lists
            if(aVBlockedPathList.contains(newBlockedPath1) || aVBlockedPathList.contains(newBlockedPath2) || placedWalls.contains(aPossibleNewWallPosition)){
                return false;
            }
            else {
                return true;
            }
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

    public boolean isWinningPathBlocked(Point aiCurrentPosition, Point userCurrentPosition, ArrayList<Point> hBlockedPathList, ArrayList<Point> vBlockedPathList, int winningNumber){
        //works for user and Ai by passing winning y-coordinate.
        ArrayList<Point> tempPositionsList;
        ArrayList<Point> nextValidPosition = getUserValidNextPositions(aiCurrentPosition, userCurrentPosition, hBlockedPathList, vBlockedPathList);
        ArrayList<ArrayList> arraysOfPossibleMoves = new ArrayList<ArrayList>();
        ArrayList<ArrayList> newArraysOfPossibleMoves = new ArrayList<ArrayList>();
        ArrayList<ArrayList> usedArrays = new ArrayList<ArrayList>();
        ArrayList<Point> usedPoints = new ArrayList<Point>();
        Point tempPosition;

        usedPoints.add(userCurrentPosition);
        arraysOfPossibleMoves.add(nextValidPosition); //get first array based on input position
        for(int k = 0; k < 50; k++) {  //50 - estimated max number of moves needed to win.
            for (int i = arraysOfPossibleMoves.size() - 1; i >= 0; i--) { //check each ArrayList in the ArrayList<ArrayList>
                tempPositionsList = arraysOfPossibleMoves.get(i);
                if (!usedArrays.contains(tempPositionsList)) {  //Don't check arrays already checked
                    for (int j = tempPositionsList.size() - 1; j >= 0; j--) { //check each point in the ArrayList
                        tempPosition = tempPositionsList.get(j);
                        if (!usedPoints.contains(tempPosition)) { //Don't check points already checked
                            newArraysOfPossibleMoves.add(getUserValidNextPositions(aiCurrentPosition, tempPosition, hBlockedPathList, vBlockedPathList)); //Get all valid positions from checked point
                            usedPoints.add(tempPosition); //add to list of checked points
                            if (tempPosition.y == winningNumber) { //test for win
                                return false;
                            }
                            usedArrays.add(tempPositionsList); //add to list of checked ArrayLists
                        }

                    }
                }
            }
            arraysOfPossibleMoves = newArraysOfPossibleMoves; //Update ArrayList of ArrayLists to check
        }
        return true;
    }

    public ArrayList<Point> getUserValidNextPositions(Point aiPosition, Point userPosition, ArrayList<Point> hBlockedPathList, ArrayList<Point> vBlockedPathList){
        //create needed lists
        ArrayList<Point> nextValidPositions = new ArrayList<Point>();
        ArrayList<Point> allPossibleMovePositions = new ArrayList<Point>();
        ArrayList<Point> allPossibleJumpPositions = new ArrayList<Point>();
        ArrayList<Point> allPossibleMovePaths = new ArrayList<Point>();
        ArrayList<Point> allPossibleJumpPaths = new ArrayList<Point>();

        // Define all possible moves and paths
        Point moveForward = new Point(userPosition.x, userPosition.y-1);
        Point pathForward = new Point(userPosition.x, userPosition.y-1); //Horizontal path
        Point moveBack = new Point(userPosition.x,userPosition.y+1);
        Point pathBack = new Point(userPosition.x,userPosition.y); //Horizontal path
        Point moveLeft = new Point(userPosition.x-1,userPosition.y);
        Point pathLeft = new Point(userPosition.x-1,userPosition.y); //Vertical path
        Point moveRight = new Point(userPosition.x+1,userPosition.y);
        Point pathRight = new Point(userPosition.x,userPosition.y); //Vertical path

        Point jumpForward = new Point(userPosition.x,userPosition.y-2);
        Point pathJumpForward = new Point(userPosition.x,userPosition.y-2); //Horizontal path
        Point jumpForwardRight = new Point(userPosition.x+1,userPosition.y-1);
        Point jumpForwardLeft = new Point(userPosition.x-1,userPosition.y-1);

        Point jumpBack = new Point(userPosition.x,userPosition.y+2);
        Point pathJumpBack = new Point(userPosition.x,userPosition.y+1); //Horizontal path
        Point jumpBackRight = new Point(userPosition.x+1,userPosition.y+1);
        Point jumpBackLeft = new Point(userPosition.x-1,userPosition.y+1);

        Point jumpLeft = new Point(userPosition.x-2,userPosition.y);
        Point pathJumpLeft = new Point(userPosition.x-2,userPosition.y); //Vertical path
        Point jumpLeftForward = new Point(userPosition.x-1,userPosition.y-1);
        Point jumpLeftBack = new Point(userPosition.x-1,userPosition.y+1);

        Point jumpRight = new Point(userPosition.x+2,userPosition.y);
        Point pathJumpRight = new Point(userPosition.x+1,userPosition.y); //Vertical path
        Point jumpRightForward = new Point(userPosition.x+1,userPosition.y-1);
        Point jumpRightBack = new Point(userPosition.x+1,userPosition.y+1);

        //add all moves to an ArrayList
        allPossibleMovePositions.add(moveForward); //0
        allPossibleMovePositions.add(moveBack);    //1
        allPossibleMovePositions.add(moveLeft);    //2
        allPossibleMovePositions.add(moveRight);   //3

        //add all move paths to an ArrayList
        allPossibleMovePaths.add(pathForward);     //0
        allPossibleMovePaths.add(pathBack);        //1
        allPossibleMovePaths.add(pathLeft);        //2
        allPossibleMovePaths.add(pathRight);       //3

        //add all jumps to an ArrayList
        allPossibleJumpPositions.add(jumpForward); //0
        allPossibleJumpPositions.add(jumpBack);    //1
        allPossibleJumpPositions.add(jumpLeft);    //2
        allPossibleJumpPositions.add(jumpRight);   //3

        //add all jump paths to an ArrayList
        allPossibleJumpPaths.add(pathJumpForward);  //0
        allPossibleJumpPaths.add(pathJumpBack);     //1
        allPossibleJumpPaths.add(pathJumpLeft);     //2
        allPossibleJumpPaths.add(pathJumpRight);    //3

        //test all possible moves
        //is the possible move on the game board
        for(int i=0; i < 4; i++){
            Point testMove = allPossibleMovePositions.get(i);
            //is the possible move on the game board
            if((testMove.x > 0) && (testMove.x < 10) && (testMove.y > 0) && (testMove.y < 10)){
                Point testPath = allPossibleMovePaths.get(i);
                // test if move paths are on blocked path list for specific orientation
                // before test for jump if there is a wall between the user and AI jump is not allowed.
                if(i < 2){  //0,1 - horizontal  2,3 - vertical
                    if(!hBlockedPathList.contains(testPath)){  //check horizontal paths
                        if(!(aiPosition.x == testMove.x && aiPosition.y == testMove.y)){            //check for jump
                            nextValidPositions.add(testMove);  //Move is valid, add to list.
                        }
                        else{  //jump is allowed
                            switch (i){ //get jump direction
                                case 0:
                                    if(!hBlockedPathList.contains(testPath) && (jumpForward.x > 0) && (jumpForward.x < 10) && (jumpForward.y > 0) && (jumpForward.y < 10)){  //check for blocked path
                                        nextValidPositions.add(jumpForward);
                                    }
                                    else{ //test diagonal moves are on the board
                                        if((jumpForwardLeft.x > 0) && (jumpForwardLeft.x < 10) && (jumpForwardLeft.y > 0) && (jumpForwardLeft.y < 10)){
                                            nextValidPositions.add(jumpForwardLeft);
                                        }
                                        if((jumpForwardRight.x > 0) && (jumpForwardRight.x < 10) && (jumpForwardRight.y > 0) && (jumpForwardRight.y < 10)){
                                            nextValidPositions.add(jumpForwardRight);
                                        }
                                    }
                                    break;
                                case 1:
                                    if(!hBlockedPathList.contains(testPath) && (jumpBack.x > 0) && (jumpBack.x < 10) && (jumpBack.y > 0) && (jumpBack.y < 10)){  //check for blocked path
                                        nextValidPositions.add(jumpBack);
                                    }
                                    else{ //test diagonal moves are on the board
                                        if((jumpBackLeft.x > 0) && (jumpBackLeft.x < 10) && (jumpBackLeft.x > 0) && (jumpBackLeft.x < 10)){
                                            nextValidPositions.add(jumpBackLeft);
                                        }
                                        if((jumpBackRight.x > 0) && (jumpBackRight.x < 10) && (jumpBackRight.x > 0) && (jumpBackRight.x < 10)){
                                            nextValidPositions.add(jumpBackRight);
                                        }
                                    }
                                    break;

                            }
                        }
                    }
                }
                else{
                    if(!vBlockedPathList.contains(testPath)){  //check vertical paths
                        if(aiPosition != testMove){            //check for jump
                            nextValidPositions.add(testMove);  //Move is valid, add to list.
                        }
                        else{  //jump is allowed
                            switch (allPossibleJumpPositions.indexOf(testMove)){ //get jump direction
                                case 2:
                                    if(!hBlockedPathList.contains(testPath) && (jumpLeft.x > 0) && (jumpLeft.x < 10) && (jumpLeft.y > 0) && (jumpLeft.y < 10)){  //check for blocked path
                                        nextValidPositions.add(jumpLeft);
                                    }
                                    else{ //test diagonal moves are on the board
                                        if((jumpLeftForward.x > 0) && (jumpLeftForward.x < 10) && (jumpLeftForward.x > 0) && (jumpLeftForward.x < 10)){
                                            nextValidPositions.add(jumpLeftForward);
                                        }
                                        if((jumpLeftBack.x > 0) && (jumpLeftBack.x < 10) && (jumpLeftBack.x > 0) && (jumpLeftBack.x < 10)){
                                            nextValidPositions.add(jumpRightForward);
                                        }
                                    }
                                    break;
                                case 3:
                                    if(!hBlockedPathList.contains(testPath) && (jumpRight.x > 0) && (jumpRight.x < 10) && (jumpRight.y > 0) && (jumpRight.y < 10)){  //check for blocked path
                                        nextValidPositions.add(jumpRight);
                                    }
                                    else{ //test diagonal moves are on the board
                                        if((jumpRightForward.x > 0) && (jumpRightForward.x < 10) && (jumpRightForward.x > 0) && (jumpRightForward.x < 10)){
                                            nextValidPositions.add(jumpLeftBack);
                                        }
                                        if((jumpRightBack.x > 0) && (jumpRightBack.x < 10) && (jumpRightBack.x > 0) && (jumpRightBack.x < 10)){
                                            nextValidPositions.add(jumpRightBack);
                                        }
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }

        return nextValidPositions;
    }


}
