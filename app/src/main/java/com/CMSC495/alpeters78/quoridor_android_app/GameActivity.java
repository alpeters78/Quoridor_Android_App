package com.CMSC495.alpeters78.quoridor_android_app;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.CMIS495.alpeters78.quoridor_android_app.R;

import java.util.ArrayList;
import java.util.Iterator;


public class GameActivity extends Activity {

    //Instance variables
    private Point userPosition = new Point(5, 1); //Point(int x, int y)
    private Point oldUserPosition; //Stores the last user position for the undo move button
    private Point aiPosition = new Point(5, 9); //TODO is this the right default spot for the AI? - CR
    private ArrayList<Wall> wallArray = new ArrayList<Wall>(); //Stores the details of the walls on the board
    private int numUserWallsRemaining = 10; //The user starts with 10 walls
    private int numAIWallsRemaining = 10; //The AI starts with 10 walls
    private ArrayList<Point> blockedPathList = new ArrayList<Point>(); //The paths that are blocked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        /* I have been playing around with changing the image when clicked. I was thinking we can
                only allow clicks on valid moves and then turning the image off when a new square is clicked.
                Then parsing the ImageView's name to get the point the pawn was moved to.

                ImageView pawn = (ImageView) findViewById(R.id.pawn11);

                pawn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //This is how to change an image onClick
                        //ImageView imageView = (ImageView) findViewById(R.id.myimageview);
                        //imageView.setImageResource(R.drawable.myimage);

                        int position = view.getId();
                        findViewById(position);
                        String name = getResources().getResourceEntryName(position);
                        String clickPosition = name.substring(name.length(), -2);
                        int pawnPosition = Integer.parseInt(clickPosition);
                        int x = pawnPosition / 10;
                        int y = pawnPosition % 10;
                        */


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    /**
     * Checks to see if either the user's pawn, or AI's pawn is
     * on their opponents side of the board (the winning position).
     *
     * @return boolean
     *              True if someone won, false if no one won.
     */
    private boolean checkForWin()
    {
        return false;
    }

    /*
     * Inner class that stores a Wall Object and the
     * information about the wall object.
     */
    class Wall
    {
        private Point position; //The center position of the wall.
        private boolean orientation; //True is vertical, false is horizontal.

        Wall(boolean anOrientation, int x, int y)
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

    /* --------------------------- User Methods --------------------------- */

    private boolean undoLastMove()
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

    /* --------------------------- AI Methods --------------------------- */

    /**
     * Finds the position in front of the user, and places a wall to block
     * the user's path.
     *
     * @return boolean
     *              True if a wall was placed, false otherwise.
     */
    private boolean blockUserPathWithWall()
    {
        Wall wall;
        Iterator<Wall> iterator;
        int x;
        int y;

        if(userPosition.x == 9)
            x = userPosition.x - 1; //Shift the position left to compensate for the end of the board.
        else
            x = userPosition.x;

        y = userPosition.y - 1;

        iterator = wallArray.iterator();
        while(iterator.hasNext())
        {
            Wall tempWall = iterator.next();
            if(tempWall.getPosition().equals(x, y))
                return false; //The wall spot was already taken by another wall.

            //TODO Check to see if the wall overlaps with another wall. - CR

        }

        wall = new Wall(false, x, y);
        wallArray.add(wall);

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
