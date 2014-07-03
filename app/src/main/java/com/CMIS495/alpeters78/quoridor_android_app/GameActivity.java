package com.CMIS495.alpeters78.quoridor_android_app;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class GameActivity extends Activity {

    //Instance variables
    private Point userPosition = new Point(5, 1); //Point(int x, int y)
    private Point aiPosition = new Point(5, 9); //TODO is this the right default spot for the AI? - CR
    private ArrayList<Wall> wallArray = new ArrayList<Wall>(); //Stores the details of the walls on the board
    private int numUserWallsRemaining = 10; //The user starts with 10 walls
    private int numAIWallsRemaining = 10; //The AI starts with 10 walls
    private ArrayList<Point> blockedPathList = new ArrayList<Point>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
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
     *              true if someone won, false if no one won.
     */
    private boolean checkForWin()
    {
        return false; //TODO work on this method - CR
    }

    /*
     * Inner class that stores a Wall Object and the
     * information about the wall object.
     */
    class Wall
    {
        private Point position;
        private boolean orientation;

        Wall(boolean anOrientation)
        {
            position = new Point();
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
}
