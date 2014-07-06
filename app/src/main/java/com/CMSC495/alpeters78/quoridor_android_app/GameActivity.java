package com.CMSC495.alpeters78.quoridor_android_app;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.CMSC495.alpeters78.quoridor_android_app.R;

import java.util.ArrayList;


public class GameActivity extends Activity {

    //Instance variables
    public ArrayList<Wall> wallArray = new ArrayList<Wall>(); //Stores the details of the walls on the board
    public ArrayList<Point> blockedPathList = new ArrayList<Point>(); //The paths that are blocked
    public User user = new User();
    public AI ai = new AI();

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
        if(user.userPosition.y == 1 || ai.aiPosition.y == 9)
            return true; //Either the AI or User has won.
        else
            return false; //No one has won, keep playing the game!
    }
}
