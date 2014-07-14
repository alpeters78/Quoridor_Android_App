package com.CMSC495.alpeters78.quoridor_android_app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;



public class GameActivity extends Activity implements View.OnClickListener {

    //Instance variables
    public ArrayList<Wall> wallArray = new ArrayList<Wall>(); //Stores the details of the walls on the board - //TODO are you using this?  Yes I am.
    public ArrayList<Point> placedWalls = new ArrayList<Point>();//stores the center point of the placed walls
    public ArrayList<Point> hBlockedPathList = new ArrayList<Point>(); //The paths that are blocked by horizontal walls
    public ArrayList<Point> vBlockedPathList = new ArrayList<Point>(); //The paths that are blocked by horizontal walls
    public User user = new User();
    public AI ai = new AI();
    public int currentResID;
    public int aiResID;
    public boolean orientation = true; //False= Horizontal True= Vertical
    public boolean moveMade = false;
    private boolean didUserWin = false;
    private boolean didAIWin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Initialize game board
        //userPawn start position
        ImageView User = (ImageView) findViewById(R.id.pawn59);
        User.setImageResource(R.drawable.user_pawn);
        currentResID = R.id.pawn59;

        //Ai pawn start position
        ImageView AI = (ImageView) findViewById(R.id.pawn51);
        AI.setImageResource(R.drawable.ai_pawn);
        aiResID = R.id.pawn51;

        //Initialize listeners
        setPawnClickListeners();
        setWallClickListeners();
        setWallCLickListenersOFF(); //TODO This is not working.  A Wall can still be placed even before the wall radio button is selected.
        //TODO We need to prevent the screen from rotating since we don't have a horizontal view setup.
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


    public void radioButtonClick(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        int checkedID = radioGroup.getCheckedRadioButtonId();
        if (checkedID == R.id.radioPawn && (!didUserWin && !didAIWin)) {
            setPawnClickListenersON();
            setWallCLickListenersOFF();
        } else if (checkedID == R.id.radioVwall && (!didUserWin && !didAIWin)) {
            orientation = true;
            setWallClickListenersON();
            setPawnCLickListenersOFF();
        } else if (checkedID == R.id.radioHwall && (!didUserWin && !didAIWin)) {
            orientation = false;
            setWallClickListenersON();
            setPawnCLickListenersOFF();
        }
    }

    @Override
    public void onClick(View view) {

        pawnClick(view);

        if (orientation) {
            vWallClick(view);
        }
        else if (!orientation) {
            hWallClick(view);
        }

        if(moveMade) {
            //Turn off all clicks since the user turn is over.
            setPawnCLickListenersOFF();
            setWallCLickListenersOFF();

            didUserWin = checkForWin(); //Check to see if user won.
            if(didUserWin) {
                //The user won.  Show a popup window.
                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.user_won_popup, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                Button newGameButton = (Button) popupView.findViewById(R.id.new_game_button);
                Button mainMenuButton = (Button) popupView.findViewById(R.id.main_menu_button);

                newGameButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GameActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                });

                mainMenuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

                popupWindow.showAsDropDown((ImageView) findViewById(R.id.pawn32), -35, 0);
            } else {
                //The user's turn is over and he/she did not win, now let the AI make a move.

                //Wall newWall = ai.blockUserPathWithWall(user.userPosition, wallArray, hBlockedPathList, vBlockedPathList);
                Wall newWall = ai.blockUserPathWithWall(user.userPosition, wallArray, hBlockedPathList, vBlockedPathList);
                if(newWall != null && (!User.isWinningPathBlocked(ai.aiPosition, user.userPosition, hBlockedPathList, vBlockedPathList, 1)) && (!User.isWinningPathBlocked(user.userPosition, ai.aiPosition, hBlockedPathList, vBlockedPathList, 9))) {
                    if(newWall.getOrientation())
                        setAIVerWallImage(newWall.getPosition());
                    else
                        setAIHorWallImage(newWall.getPosition());

                } else {
                    //A wall was not placed.
                    System.out.println("A Wall is already in that spot or the AI is out of walls.");
                    if(newWall != null && !newWall.getOrientation()){ //Remove Horizontal wall that blocks winning path from all lists.
                        Point temp1 = newWall.getPosition();
                        Point temp2 = new Point(newWall.getPosition().x + 1,newWall.getPosition().y);
                        hBlockedPathList.remove(temp1);
                        hBlockedPathList.remove(temp2);
                        wallArray.remove(newWall);
                    }
                    else if(newWall != null && newWall.getOrientation()){ //Remove Vertical wall that blocks winning path from all lists.
                        Point temp1 = newWall.getPosition();
                        Point temp2 = new Point(newWall.getPosition().x,newWall.getPosition().y -1);
                        hBlockedPathList.remove(temp1);
                        hBlockedPathList.remove(temp2);
                        wallArray.remove(newWall);
                    }
                    ai.makeGoodAIPawnMove(user.userPosition, hBlockedPathList, vBlockedPathList);
                    //ai.makeRandomAIPawnMove(user.userPosition, hBlockedPathList, vBlockedPathList);
                    setAIPawnImage();
                }

                didAIWin = checkForWin();
                if(didAIWin) {
                    //The AI won.  Show a popup window.
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    View popupView = inflater.inflate(R.layout.ai_won_popup, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                    Button newGameButton = (Button) popupView.findViewById(R.id.new_game_button);
                    Button mainMenuButton = (Button) popupView.findViewById(R.id.main_menu_button);

                    newGameButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(GameActivity.this, GameActivity.class);
                            startActivity(intent);
                        }
                    });

                    mainMenuButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });

                    popupWindow.showAsDropDown(findViewById(R.id.pawn12), 36, 0);
                }


            }

            if(didUserWin || didAIWin) {
                setPawnCLickListenersOFF();
                setWallCLickListenersOFF();
                return;
            }
            else {

                //Turn back on the correct clickListeners since no one won.
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                int checkedID = radioGroup.getCheckedRadioButtonId();
                if (checkedID == R.id.radioPawn && (!didUserWin && !didAIWin)) {
                    setPawnClickListenersON();
                    setWallCLickListenersOFF();
                } else if (checkedID == R.id.radioVwall && (!didUserWin && !didAIWin)) {
                    orientation = true;
                    setWallClickListenersON();
                    setPawnCLickListenersOFF();
                } else if (checkedID == R.id.radioHwall && (!didUserWin && !didAIWin)) {
                    orientation = false;
                    setWallClickListenersON();
                    setPawnCLickListenersOFF();
                }
            }
            moveMade = false;
        }
        //TEST BLOCK

        //First wait for the user to move.
        System.out.println("onClick method was invoked.");

        //print out wall centers
        System.out.println("Wall Array");
        for(int i = wallArray.size()-1; i >= 0; i--) {
            Wall wallCenterPoint = wallArray.get(i);
            System.out.println(wallCenterPoint.getPosition());
        }
        System.out.println("Placed Walls");
        for(int i = placedWalls.size()-1; i >= 0; i--) {
            System.out.println(placedWalls.get(i));
        }
        System.out.println("H Blocked Paths");
        for(int i = hBlockedPathList.size()-1; i >= 0; i--) {
            System.out.println(hBlockedPathList.get(i));
        }
        System.out.println("V Blocked Paths");
        for(int i = vBlockedPathList.size()-1; i >= 0; i--) {
            System.out.println(vBlockedPathList.get(i));
        }



        //END TEST BLOCK
    }

    public boolean setPawnImage(int resID, Point aPossiblePawnPosition){
        if (user.isValidPawnMove(aPossiblePawnPosition, ai.aiPosition , hBlockedPathList, vBlockedPathList)) {

            //sets previous position to blank
            ImageView oldPosition = (ImageView) findViewById(currentResID);
            oldPosition.setImageResource(R.drawable.blank);

            //sets image of new position to user pawn
            ImageView newPosition = (ImageView) findViewById(resID);
            newPosition.setImageResource(R.drawable.user_pawn);

            //new position is set to current
            currentResID = resID;
            user.setNewUserPosition(aPossiblePawnPosition);

            return true;
        } else {

            return false;
        }
    }

    /**
     * Updates the AI's pawn position on the game board after an AI pawn move.
     */
    public void setAIPawnImage()
    {
        String newPawnId = "pawn" + ai.aiPosition.x + ai.aiPosition.y;
        int newAIResID = getResources().getIdentifier(newPawnId, "id", getPackageName());

        //Set previous position to blank
        ImageView oldPosition = (ImageView) findViewById(aiResID);
        oldPosition.setImageResource(R.drawable.blank);

        //Set image of new position to AI pawn
        ImageView newPosition = (ImageView) findViewById(newAIResID);
        newPosition.setImageResource(R.drawable.ai_pawn);

        //Set new resource id to current
        aiResID = newAIResID;
    }

    public boolean setHorWallImage(int wallID, int path1ID, int path2ID, Point aPossibleWallPosition) {

        if (user.isValidHorizontalWallMove(aPossibleWallPosition, ai.aiPosition, hBlockedPathList, vBlockedPathList, placedWalls) && (user.numUserWallsRemaining > 0)) {

            //set images
            ImageView path1 = (ImageView) findViewById(path1ID);
            path1.setImageResource(R.drawable.h_path);

            ImageView center = (ImageView) findViewById(wallID);
            center.setImageResource(R.drawable.wall_center);

            ImageView path2 = (ImageView) findViewById(path2ID);
            path2.setImageResource(R.drawable.h_path);

            //add blocked paths and wall centers to array lists
            Point blockedPath1 = new Point(aPossibleWallPosition.x, aPossibleWallPosition.y);
            Point blockedPath2 = new Point(aPossibleWallPosition.x + 1, aPossibleWallPosition.y);
            placedWalls.add(aPossibleWallPosition);
            hBlockedPathList.add(blockedPath1);
            hBlockedPathList.add(blockedPath2);
            //Sync wall lists.
            Wall newWallPosition = new Wall(false,aPossibleWallPosition.x,aPossibleWallPosition.y);
            wallArray.add(newWallPosition);

            //take away one wall
            user.numUserWallsRemaining--;

            //Update display
            String wallsRemaining = String.valueOf(user.numUserWallsRemaining);
            TextView text = (TextView) findViewById(R.id.userWalls);
            text.setText(wallsRemaining);

            return true;
        } else {

            return false;
        }

    }

    public void setAIHorWallImage(Point aNewWallPosition) {

        String path1ID = "path" + aNewWallPosition.x + aNewWallPosition.y + "h";
        String wallID = "wall" + aNewWallPosition.x + aNewWallPosition.y;
        String path2ID = "path" + (aNewWallPosition.x + 1) + aNewWallPosition.y + "h";

        ImageView path1 = (ImageView) findViewById(getResources().getIdentifier(path1ID, "id", getPackageName()));
        path1.setImageResource(R.drawable.h_path);

        ImageView center = (ImageView) findViewById(getResources().getIdentifier(wallID, "id", getPackageName()));
        center.setImageResource(R.drawable.wall_center);

        ImageView path2 = (ImageView) findViewById(getResources().getIdentifier(path2ID, "id", getPackageName()));
        path2.setImageResource(R.drawable.h_path);

        //Sync wall lists.
        placedWalls.add(aNewWallPosition);

        //Update display
        String wallsRemaining = String.valueOf(ai.numAIWallsRemaining);
        TextView text = (TextView) findViewById(R.id.aiWalls);
        text.setText(wallsRemaining);
    }

    public boolean setVerWallImage(int wallID, int path1ID, int path2ID, Point aPossibleWallPosition) {

        if (user.isValidVerticalWallMove(aPossibleWallPosition, ai.aiPosition, hBlockedPathList, vBlockedPathList, placedWalls) && (user.numUserWallsRemaining > 0)) {

            //set images
            ImageView path1 = (ImageView) findViewById(path1ID);
            path1.setImageResource(R.drawable.v_path);

            ImageView center = (ImageView) findViewById(wallID);
            center.setImageResource(R.drawable.wall_center);

            ImageView path2 = (ImageView) findViewById(path2ID);
            path2.setImageResource(R.drawable.v_path);

            //add blocked paths and wall centers to array lists
            Point blockedPath1 = new Point(aPossibleWallPosition.x,aPossibleWallPosition.y);
            Point blockedPath2 = new Point(aPossibleWallPosition.x,aPossibleWallPosition.y + 1);
            placedWalls.add(aPossibleWallPosition);
            vBlockedPathList.add(blockedPath1);
            vBlockedPathList.add(blockedPath2);

            //Sync wall lists.
            Wall newWallPosition = new Wall(true,aPossibleWallPosition.x,aPossibleWallPosition.y);
            wallArray.add(newWallPosition);

            //take away one wall
            user.numUserWallsRemaining--;

            //Update display
            String wallsRemaining = String.valueOf(user.numUserWallsRemaining);
            TextView text = (TextView) findViewById(R.id.userWalls);
            text.setText(wallsRemaining);

            return true;
        } else {

            return false;
        }

    }

    public void setAIVerWallImage(Point aNewWallPosition) {

        String path1ID = "path" + aNewWallPosition.x + aNewWallPosition.y + "v";
        String wallID = "wall" + aNewWallPosition.x + aNewWallPosition.y;
        String path2ID = "path" + aNewWallPosition.x + (aNewWallPosition.y + 1) + "v";

        ImageView path1 = (ImageView) findViewById(getResources().getIdentifier(path1ID, "id", getPackageName()));
        path1.setImageResource(R.drawable.v_path);

        ImageView center = (ImageView) findViewById(getResources().getIdentifier(wallID, "id", getPackageName()));
        center.setImageResource(R.drawable.wall_center);

        ImageView path2 = (ImageView) findViewById(getResources().getIdentifier(path2ID, "id", getPackageName()));
        path2.setImageResource(R.drawable.v_path);

        //Sync wall lists.
        placedWalls.add(aNewWallPosition);


        //Update display
        String wallsRemaining = String.valueOf(ai.numAIWallsRemaining);
        TextView text = (TextView) findViewById(R.id.aiWalls);
        text.setText(wallsRemaining);
    }

    /**
     *
     */
    public void pawnClick(View view){

            switch (view.getId()) {
                //row 1
                case R.id.pawn11:
                    if (setPawnImage(R.id.pawn11, new Point(1, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn21:
                    if (setPawnImage(R.id.pawn21, new Point(2, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn31:
                    if (setPawnImage(R.id.pawn31, new Point(3, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn41:
                    if (setPawnImage(R.id.pawn41, new Point(4, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn51:
                    if (setPawnImage(R.id.pawn51, new Point(5, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn61:
                    if (setPawnImage(R.id.pawn61, new Point(6, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn71:
                    if (setPawnImage(R.id.pawn71, new Point(7, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn81:
                    if (setPawnImage(R.id.pawn81, new Point(8, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn91:
                    if (setPawnImage(R.id.pawn91, new Point(9, 1))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 2
                case R.id.pawn12:
                    if (setPawnImage(R.id.pawn12, new Point(1, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn22:
                    if (setPawnImage(R.id.pawn22, new Point(2, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn32:
                    if (setPawnImage(R.id.pawn32, new Point(3, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn42:
                    if (setPawnImage(R.id.pawn42, new Point(4, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn52:
                    if (setPawnImage(R.id.pawn52, new Point(5, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn62:
                    if (setPawnImage(R.id.pawn62, new Point(6, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn72:
                    if (setPawnImage(R.id.pawn72, new Point(7, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn82:
                    if (setPawnImage(R.id.pawn82, new Point(8, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn92:
                    if (setPawnImage(R.id.pawn92, new Point(9, 2))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 3
                case R.id.pawn13:
                    if (setPawnImage(R.id.pawn13, new Point(1, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn23:
                    if (setPawnImage(R.id.pawn23, new Point(2, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn33:
                    if (setPawnImage(R.id.pawn33, new Point(3, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn43:
                    if (setPawnImage(R.id.pawn43, new Point(4, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn53:
                    if (setPawnImage(R.id.pawn53, new Point(5, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn63:
                    if (setPawnImage(R.id.pawn63, new Point(6, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn73:
                    if (setPawnImage(R.id.pawn73, new Point(7, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn83:
                    if (setPawnImage(R.id.pawn83, new Point(8, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn93:
                    if (setPawnImage(R.id.pawn93, new Point(9, 3))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 4
                case R.id.pawn14:
                    if (setPawnImage(R.id.pawn14, new Point(1, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn24:
                    if (setPawnImage(R.id.pawn24, new Point(2, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn34:
                    if (setPawnImage(R.id.pawn34, new Point(3, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn44:
                    if (setPawnImage(R.id.pawn44, new Point(4, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn54:
                    if (setPawnImage(R.id.pawn54, new Point(5, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn64:
                    if (setPawnImage(R.id.pawn64, new Point(6, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn74:
                    if (setPawnImage(R.id.pawn74, new Point(7, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn84:
                    if (setPawnImage(R.id.pawn84, new Point(8, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn94:
                    if (setPawnImage(R.id.pawn94, new Point(9, 4))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 5
                case R.id.pawn15:
                    if (setPawnImage(R.id.pawn15, new Point(1, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn25:
                    if (setPawnImage(R.id.pawn25, new Point(2, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn35:
                    if (setPawnImage(R.id.pawn35, new Point(3, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn45:
                    if (setPawnImage(R.id.pawn45, new Point(4, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn55:
                    if (setPawnImage(R.id.pawn55, new Point(5, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn65:
                    if (setPawnImage(R.id.pawn65, new Point(6, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn75:
                    if (setPawnImage(R.id.pawn75, new Point(7, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn85:
                    if (setPawnImage(R.id.pawn85, new Point(8, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn95:
                    if (setPawnImage(R.id.pawn95, new Point(9, 5))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 6
                case R.id.pawn16:
                    if (setPawnImage(R.id.pawn16, new Point(1, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn26:
                    if (setPawnImage(R.id.pawn26, new Point(2, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn36:
                    if (setPawnImage(R.id.pawn36, new Point(3, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn46:
                    if (setPawnImage(R.id.pawn46, new Point(4, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn56:
                    if (setPawnImage(R.id.pawn56, new Point(5, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn66:
                    if (setPawnImage(R.id.pawn66, new Point(6, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn76:
                    if (setPawnImage(R.id.pawn76, new Point(7, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn86:
                    if (setPawnImage(R.id.pawn86, new Point(8, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn96:
                    if (setPawnImage(R.id.pawn96, new Point(9, 6))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 7
                case R.id.pawn17:
                    if (setPawnImage(R.id.pawn17, new Point(1, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn27:
                    if (setPawnImage(R.id.pawn27, new Point(2, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn37:
                    if (setPawnImage(R.id.pawn37, new Point(3, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn47:
                    if (setPawnImage(R.id.pawn47, new Point(4, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn57:
                    if (setPawnImage(R.id.pawn57, new Point(5, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn67:
                    if (setPawnImage(R.id.pawn67, new Point(6, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn77:
                    if (setPawnImage(R.id.pawn77, new Point(7, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn87:
                    if (setPawnImage(R.id.pawn87, new Point(8, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn97:
                    if (setPawnImage(R.id.pawn97, new Point(9, 7))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 8
                case R.id.pawn18:
                    if (setPawnImage(R.id.pawn18, new Point(1, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn28:
                    if (setPawnImage(R.id.pawn28, new Point(2, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn38:
                    if (setPawnImage(R.id.pawn38, new Point(3, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn48:
                    if (setPawnImage(R.id.pawn48, new Point(4, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn58:
                    if (setPawnImage(R.id.pawn58, new Point(5, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn68:
                    if (setPawnImage(R.id.pawn68, new Point(6, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn78:
                    if (setPawnImage(R.id.pawn78, new Point(7, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn88:
                    if (setPawnImage(R.id.pawn88, new Point(8, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn98:
                    if (setPawnImage(R.id.pawn98, new Point(9, 8))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }

                    //row 9
                case R.id.pawn19:
                    if (setPawnImage(R.id.pawn19, new Point(1, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn29:
                    if (setPawnImage(R.id.pawn29, new Point(2, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn39:
                    if (setPawnImage(R.id.pawn39, new Point(3, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn49:
                    if (setPawnImage(R.id.pawn49, new Point(4, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn59:
                    if (setPawnImage(R.id.pawn59, new Point(5, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn69:
                    if (setPawnImage(R.id.pawn69, new Point(6, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn79:
                    if (setPawnImage(R.id.pawn79, new Point(7, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn89:
                    if (setPawnImage(R.id.pawn89, new Point(8, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
                case R.id.pawn99:
                    if (setPawnImage(R.id.pawn99, new Point(9, 9))) {
                        moveMade = true;
                        break;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Move", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                        break;
                    }
            }

    }

    /**
     *
     */
    public void hWallClick(View view) {

            switch (view.getId()) {

                //row 1
                case R.id.wall11:
                    if (setHorWallImage(R.id.wall11, R.id.path11h, R.id.path21h, new Point(1, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall21:
                    if (setHorWallImage(R.id.wall21, R.id.path21h, R.id.path31h, new Point(2, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall31:
                    if (setHorWallImage(R.id.wall31, R.id.path31h, R.id.path41h, new Point(3, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall41:
                    if (setHorWallImage(R.id.wall41, R.id.path41h, R.id.path51h, new Point(4, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall51:
                    if (setHorWallImage(R.id.wall51, R.id.path51h, R.id.path61h, new Point(5, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall61:
                    if (setHorWallImage(R.id.wall61, R.id.path61h, R.id.path71h, new Point(6, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall71:
                    if (setHorWallImage(R.id.wall71, R.id.path71h, R.id.path81h, new Point(7, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall81:
                    if (setHorWallImage(R.id.wall81, R.id.path81h, R.id.path91h, new Point(8, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 2
                case R.id.wall12:
                    if (setHorWallImage(R.id.wall12, R.id.path12h, R.id.path22h, new Point(1, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall22:
                    if (setHorWallImage(R.id.wall22, R.id.path22h, R.id.path32h, new Point(2, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall32:
                    if (setHorWallImage(R.id.wall32, R.id.path32h, R.id.path42h, new Point(3, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall42:
                    if (setHorWallImage(R.id.wall42, R.id.path42h, R.id.path52h, new Point(4, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall52:
                    if (setHorWallImage(R.id.wall52, R.id.path52h, R.id.path62h, new Point(5, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall62:
                    if (setHorWallImage(R.id.wall62, R.id.path62h, R.id.path72h, new Point(6, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall72:
                    if (setHorWallImage(R.id.wall72, R.id.path72h, R.id.path82h, new Point(7, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall82:
                    if (setHorWallImage(R.id.wall82, R.id.path82h, R.id.path92h, new Point(8, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 3
                case R.id.wall13:
                    if (setHorWallImage(R.id.wall13, R.id.path13h, R.id.path23h, new Point(1, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall23:
                    if (setHorWallImage(R.id.wall23, R.id.path23h, R.id.path33h, new Point(2, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall33:
                    if (setHorWallImage(R.id.wall33, R.id.path33h, R.id.path43h, new Point(3, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall43:
                    if (setHorWallImage(R.id.wall43, R.id.path43h, R.id.path53h, new Point(4, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall53:
                    if (setHorWallImage(R.id.wall53, R.id.path53h, R.id.path63h, new Point(5, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall63:
                    if (setHorWallImage(R.id.wall63, R.id.path63h, R.id.path73h, new Point(6, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall73:
                    if (setHorWallImage(R.id.wall73, R.id.path73h, R.id.path83h, new Point(7, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall83:
                    if (setHorWallImage(R.id.wall83, R.id.path83h, R.id.path93h, new Point(8, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 4
                case R.id.wall14:
                    if (setHorWallImage(R.id.wall14, R.id.path14h, R.id.path24h, new Point(1, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall24:
                    if (setHorWallImage(R.id.wall24, R.id.path24h, R.id.path34h, new Point(2, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall34:
                    if (setHorWallImage(R.id.wall34, R.id.path34h, R.id.path44h, new Point(3, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall44:
                    if (setHorWallImage(R.id.wall44, R.id.path44h, R.id.path54h, new Point(4, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall54:
                    if (setHorWallImage(R.id.wall54, R.id.path54h, R.id.path64h, new Point(5, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall64:
                    if (setHorWallImage(R.id.wall64, R.id.path64h, R.id.path74h, new Point(6, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall74:
                    if (setHorWallImage(R.id.wall74, R.id.path74h, R.id.path84h, new Point(7, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall84:
                    if (setHorWallImage(R.id.wall84, R.id.path84h, R.id.path94h, new Point(8, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 5
                case R.id.wall15:
                    if (setHorWallImage(R.id.wall15, R.id.path15h, R.id.path25h, new Point(1, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall25:
                    if (setHorWallImage(R.id.wall25, R.id.path25h, R.id.path35h, new Point(2, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall35:
                    if (setHorWallImage(R.id.wall35, R.id.path35h, R.id.path45h, new Point(3, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall45:
                    if (setHorWallImage(R.id.wall45, R.id.path45h, R.id.path55h, new Point(4, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall55:
                    if (setHorWallImage(R.id.wall55, R.id.path55h, R.id.path65h, new Point(5, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall65:
                    if (setHorWallImage(R.id.wall65, R.id.path65h, R.id.path75h, new Point(6, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall75:
                    if (setHorWallImage(R.id.wall75, R.id.path75h, R.id.path85h, new Point(7, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall85:
                    if (setHorWallImage(R.id.wall85, R.id.path85h, R.id.path95h, new Point(8, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 6
                case R.id.wall16:
                    if (setHorWallImage(R.id.wall16, R.id.path16h, R.id.path26h, new Point(1, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall26:
                    if (setHorWallImage(R.id.wall26, R.id.path26h, R.id.path36h, new Point(2, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall36:
                    if (setHorWallImage(R.id.wall36, R.id.path36h, R.id.path46h, new Point(3, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall46:
                    if (setHorWallImage(R.id.wall46, R.id.path46h, R.id.path56h, new Point(4, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall56:
                    if (setHorWallImage(R.id.wall56, R.id.path56h, R.id.path66h, new Point(5, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall66:
                    if (setHorWallImage(R.id.wall66, R.id.path66h, R.id.path76h, new Point(6, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall76:
                    if (setHorWallImage(R.id.wall76, R.id.path76h, R.id.path86h, new Point(7, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall86:
                    if (setHorWallImage(R.id.wall86, R.id.path86h, R.id.path96h, new Point(8, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 7
                case R.id.wall17:
                    if (setHorWallImage(R.id.wall17, R.id.path17h, R.id.path27h, new Point(1, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall27:
                    if (setHorWallImage(R.id.wall27, R.id.path27h, R.id.path37h, new Point(2, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall37:
                    if (setHorWallImage(R.id.wall37, R.id.path37h, R.id.path47h, new Point(3, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall47:
                    if (setHorWallImage(R.id.wall47, R.id.path47h, R.id.path57h, new Point(4, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall57:
                    if (setHorWallImage(R.id.wall57, R.id.path57h, R.id.path67h, new Point(5, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall67:
                    if (setHorWallImage(R.id.wall67, R.id.path67h, R.id.path77h, new Point(6, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall77:
                    if (setHorWallImage(R.id.wall77, R.id.path77h, R.id.path87h, new Point(7, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall87:
                    if (setHorWallImage(R.id.wall87, R.id.path87h, R.id.path97h, new Point(8, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 8
                case R.id.wall18:
                    if (setHorWallImage(R.id.wall18, R.id.path18h, R.id.path28h, new Point(1, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall28:
                    if (setHorWallImage(R.id.wall28, R.id.path28h, R.id.path38h, new Point(2, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall38:
                    if (setHorWallImage(R.id.wall38, R.id.path38h, R.id.path48h, new Point(3, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall48:
                    if (setHorWallImage(R.id.wall48, R.id.path48h, R.id.path58h, new Point(4, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall58:
                    if (setHorWallImage(R.id.wall58, R.id.path58h, R.id.path68h, new Point(5, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall68:
                    if (setHorWallImage(R.id.wall68, R.id.path68h, R.id.path78h, new Point(6, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall78:
                    if (setHorWallImage(R.id.wall78, R.id.path78h, R.id.path88h, new Point(7, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall88:
                    if (setHorWallImage(R.id.wall88, R.id.path88h, R.id.path98h, new Point(8, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
            }
    }

    /**
     *
     */
    public void vWallClick(View view) {


            switch (view.getId()) {

                //row 1
                case R.id.wall11:
                    if (setVerWallImage(R.id.wall11, R.id.path11v, R.id.path12v, new Point(1, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall21:
                    if (setVerWallImage(R.id.wall21, R.id.path21v, R.id.path22v, new Point(2, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall31:
                    if (setVerWallImage(R.id.wall31, R.id.path31v, R.id.path32v, new Point(3, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall41:
                    if (setVerWallImage(R.id.wall41, R.id.path41v, R.id.path42v, new Point(4, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall51:
                    if (setVerWallImage(R.id.wall51, R.id.path51v, R.id.path52v, new Point(5, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall61:
                    if (setVerWallImage(R.id.wall61, R.id.path61v, R.id.path62v, new Point(6, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall71:
                    if (setVerWallImage(R.id.wall71, R.id.path71v, R.id.path72v, new Point(7, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall81:
                    if (setVerWallImage(R.id.wall81, R.id.path81v, R.id.path82v, new Point(8, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 2
                case R.id.wall12:
                    if (setVerWallImage(R.id.wall12, R.id.path12v, R.id.path13v, new Point(1, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall22:
                    if (setVerWallImage(R.id.wall22, R.id.path22v, R.id.path23v, new Point(2, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall32:
                    if (setVerWallImage(R.id.wall32, R.id.path32v, R.id.path33v, new Point(3, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall42:
                    if (setVerWallImage(R.id.wall42, R.id.path42v, R.id.path43v, new Point(4, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall52:
                    if (setVerWallImage(R.id.wall52, R.id.path52v, R.id.path53v, new Point(5, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall62:
                    if (setVerWallImage(R.id.wall62, R.id.path62v, R.id.path63v, new Point(6, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall72:
                    if (setVerWallImage(R.id.wall72, R.id.path72v, R.id.path73v, new Point(7, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall82:
                    if (setVerWallImage(R.id.wall82, R.id.path82v, R.id.path83v, new Point(8, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 3
                case R.id.wall13:
                    if (setVerWallImage(R.id.wall13, R.id.path13v, R.id.path14v, new Point(1, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall23:
                    if (setVerWallImage(R.id.wall23, R.id.path23v, R.id.path24v, new Point(2, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall33:
                    if (setVerWallImage(R.id.wall33, R.id.path33v, R.id.path34v, new Point(3, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall43:
                    if (setVerWallImage(R.id.wall43, R.id.path43v, R.id.path44v, new Point(4, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall53:
                    if (setVerWallImage(R.id.wall53, R.id.path53v, R.id.path54v, new Point(5, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall63:
                    if (setVerWallImage(R.id.wall63, R.id.path63v, R.id.path64v, new Point(6, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall73:
                    if (setVerWallImage(R.id.wall73, R.id.path73v, R.id.path74v, new Point(7, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall83:
                    if (setVerWallImage(R.id.wall83, R.id.path83v, R.id.path84v, new Point(8, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 4
                case R.id.wall14:
                    if (setVerWallImage(R.id.wall14, R.id.path14v, R.id.path15v, new Point(1, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall24:
                    if (setVerWallImage(R.id.wall24, R.id.path24v, R.id.path25v, new Point(2, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall34:
                    if (setVerWallImage(R.id.wall34, R.id.path34v, R.id.path35v, new Point(3, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall44:
                    if (setVerWallImage(R.id.wall44, R.id.path44v, R.id.path45v, new Point(4, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall54:
                    if (setVerWallImage(R.id.wall54, R.id.path54v, R.id.path55v, new Point(5, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall64:
                    if (setVerWallImage(R.id.wall64, R.id.path64v, R.id.path65v, new Point(6, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall74:
                    if (setVerWallImage(R.id.wall74, R.id.path74v, R.id.path75v, new Point(7, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall84:
                    if (setVerWallImage(R.id.wall84, R.id.path84v, R.id.path85v, new Point(8, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 5
                case R.id.wall15:
                    if (setVerWallImage(R.id.wall15, R.id.path15v, R.id.path16v, new Point(1, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall25:
                    if (setVerWallImage(R.id.wall25, R.id.path25v, R.id.path26v, new Point(2, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall35:
                    if (setVerWallImage(R.id.wall35, R.id.path35v, R.id.path36v, new Point(3, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall45:
                    if (setVerWallImage(R.id.wall45, R.id.path45v, R.id.path46v, new Point(4, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall55:
                    if (setVerWallImage(R.id.wall55, R.id.path55v, R.id.path56v, new Point(5, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall65:
                    if (setVerWallImage(R.id.wall65, R.id.path65v, R.id.path66v, new Point(6, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall75:
                    if (setVerWallImage(R.id.wall75, R.id.path75v, R.id.path76v, new Point(7, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall85:
                    if (setVerWallImage(R.id.wall85, R.id.path85v, R.id.path86v, new Point(8, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 6
                case R.id.wall16:
                    if (setVerWallImage(R.id.wall16, R.id.path16v, R.id.path17v, new Point(1, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall26:
                    if (setVerWallImage(R.id.wall26, R.id.path26v, R.id.path27v, new Point(2, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall36:
                    if (setVerWallImage(R.id.wall36, R.id.path36v, R.id.path37v, new Point(3, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall46:
                    if (setVerWallImage(R.id.wall46, R.id.path46v, R.id.path47v, new Point(4, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall56:
                    if (setVerWallImage(R.id.wall56, R.id.path56v, R.id.path57v, new Point(5, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall66:
                    if (setVerWallImage(R.id.wall66, R.id.path66v, R.id.path67v, new Point(6, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall76:
                    if (setVerWallImage(R.id.wall76, R.id.path76v, R.id.path77v, new Point(7, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall86:
                    if (setVerWallImage(R.id.wall86, R.id.path86v, R.id.path87v, new Point(8, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 7
                case R.id.wall17:
                    if (setVerWallImage(R.id.wall17, R.id.path17v, R.id.path18v, new Point(1, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall27:
                    if (setVerWallImage(R.id.wall27, R.id.path27v, R.id.path28v, new Point(2, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall37:
                    if (setVerWallImage(R.id.wall37, R.id.path37v, R.id.path38v, new Point(3, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall47:
                    if (setVerWallImage(R.id.wall47, R.id.path47v, R.id.path48v, new Point(4, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall57:
                    if (setVerWallImage(R.id.wall57, R.id.path57v, R.id.path58v, new Point(5, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall67:
                    if (setVerWallImage(R.id.wall67, R.id.path67v, R.id.path68v, new Point(6, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall77:
                    if (setVerWallImage(R.id.wall77, R.id.path77v, R.id.path78v, new Point(7, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall87:
                    if (setVerWallImage(R.id.wall87, R.id.path87v, R.id.path88v, new Point(8, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 8
                case R.id.wall18:
                    if (setVerWallImage(R.id.wall18, R.id.path18v, R.id.path19v, new Point(1, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall28:
                    if (setVerWallImage(R.id.wall28, R.id.path28v, R.id.path29v, new Point(2, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall38:
                    if (setVerWallImage(R.id.wall38, R.id.path38v, R.id.path39v, new Point(3, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall48:
                    if (setVerWallImage(R.id.wall48, R.id.path48v, R.id.path49v, new Point(4, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall58:
                    if (setVerWallImage(R.id.wall58, R.id.path58v, R.id.path59v, new Point(5, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall68:
                    if (setVerWallImage(R.id.wall68, R.id.path68v, R.id.path69v, new Point(6, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall78:
                    if (setVerWallImage(R.id.wall78, R.id.path78v, R.id.path79v, new Point(7, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall88:
                    if (setVerWallImage(R.id.wall88, R.id.path88v, R.id.path89v, new Point(8, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
            }
    }

    /**
     * calls
     *
     */
    public void setPawnClickListeners(){
        //Row 1
        ImageView pawn11 = (ImageView) findViewById(R.id.pawn11);
        pawn11.setOnClickListener(this);

        ImageView pawn21 = (ImageView) findViewById(R.id.pawn21);
        pawn21.setOnClickListener(this);

        ImageView pawn31 = (ImageView) findViewById(R.id.pawn31);
        pawn31.setOnClickListener(this);

        ImageView pawn41 = (ImageView) findViewById(R.id.pawn41);
        pawn41.setOnClickListener(this);

        ImageView pawn51 = (ImageView) findViewById(R.id.pawn51);
        pawn51.setOnClickListener(this);

        ImageView pawn61 = (ImageView) findViewById(R.id.pawn61);
        pawn61.setOnClickListener(this);

        ImageView pawn71 = (ImageView) findViewById(R.id.pawn71);
        pawn71.setOnClickListener(this);

        ImageView pawn81 = (ImageView) findViewById(R.id.pawn81);
        pawn81.setOnClickListener(this);

        ImageView pawn91 = (ImageView) findViewById(R.id.pawn91);
        pawn91.setOnClickListener(this);

        //Row 2
        ImageView pawn12 = (ImageView) findViewById(R.id.pawn12);
        pawn12.setOnClickListener(this);

        ImageView pawn22 = (ImageView) findViewById(R.id.pawn22);
        pawn22.setOnClickListener(this);

        ImageView pawn32 = (ImageView) findViewById(R.id.pawn32);
        pawn32.setOnClickListener(this);

        ImageView pawn42 = (ImageView) findViewById(R.id.pawn42);
        pawn42.setOnClickListener(this);

        ImageView pawn52 = (ImageView) findViewById(R.id.pawn52);
        pawn52.setOnClickListener(this);

        ImageView pawn62 = (ImageView) findViewById(R.id.pawn62);
        pawn62.setOnClickListener(this);

        ImageView pawn72 = (ImageView) findViewById(R.id.pawn72);
        pawn72.setOnClickListener(this);

        ImageView pawn82 = (ImageView) findViewById(R.id.pawn82);
        pawn82.setOnClickListener(this);

        ImageView pawn92 = (ImageView) findViewById(R.id.pawn92);
        pawn92.setOnClickListener(this);

        //Row 3
        ImageView pawn13 = (ImageView) findViewById(R.id.pawn13);
        pawn13.setOnClickListener(this);

        ImageView pawn23 = (ImageView) findViewById(R.id.pawn23);
        pawn23.setOnClickListener(this);

        ImageView pawn33 = (ImageView) findViewById(R.id.pawn33);
        pawn33.setOnClickListener(this);

        ImageView pawn43 = (ImageView) findViewById(R.id.pawn43);
        pawn43.setOnClickListener(this);

        ImageView pawn53 = (ImageView) findViewById(R.id.pawn53);
        pawn53.setOnClickListener(this);

        ImageView pawn63 = (ImageView) findViewById(R.id.pawn63);
        pawn63.setOnClickListener(this);

        ImageView pawn73 = (ImageView) findViewById(R.id.pawn73);
        pawn73.setOnClickListener(this);

        ImageView pawn83 = (ImageView) findViewById(R.id.pawn83);
        pawn83.setOnClickListener(this);

        ImageView pawn93 = (ImageView) findViewById(R.id.pawn93);
        pawn93.setOnClickListener(this);

        //Row 4
        ImageView pawn14 = (ImageView) findViewById(R.id.pawn14);
        pawn14.setOnClickListener(this);

        ImageView pawn24 = (ImageView) findViewById(R.id.pawn24);
        pawn24.setOnClickListener(this);

        ImageView pawn34 = (ImageView) findViewById(R.id.pawn34);
        pawn34.setOnClickListener(this);

        ImageView pawn44 = (ImageView) findViewById(R.id.pawn44);
        pawn44.setOnClickListener(this);

        ImageView pawn54 = (ImageView) findViewById(R.id.pawn54);
        pawn54.setOnClickListener(this);

        ImageView pawn64 = (ImageView) findViewById(R.id.pawn64);
        pawn64.setOnClickListener(this);

        ImageView pawn74 = (ImageView) findViewById(R.id.pawn74);
        pawn74.setOnClickListener(this);

        ImageView pawn84 = (ImageView) findViewById(R.id.pawn84);
        pawn84.setOnClickListener(this);

        ImageView pawn94 = (ImageView) findViewById(R.id.pawn94);
        pawn94.setOnClickListener(this);

        //Row 5
        ImageView pawn15 = (ImageView) findViewById(R.id.pawn15);
        pawn15.setOnClickListener(this);

        ImageView pawn25 = (ImageView) findViewById(R.id.pawn25);
        pawn25.setOnClickListener(this);

        ImageView pawn35 = (ImageView) findViewById(R.id.pawn35);
        pawn35.setOnClickListener(this);

        ImageView pawn45 = (ImageView) findViewById(R.id.pawn45);
        pawn45.setOnClickListener(this);

        ImageView pawn55 = (ImageView) findViewById(R.id.pawn55);
        pawn55.setOnClickListener(this);

        ImageView pawn65 = (ImageView) findViewById(R.id.pawn65);
        pawn65.setOnClickListener(this);

        ImageView pawn75 = (ImageView) findViewById(R.id.pawn75);
        pawn75.setOnClickListener(this);

        ImageView pawn85 = (ImageView) findViewById(R.id.pawn85);
        pawn85.setOnClickListener(this);

        ImageView pawn95 = (ImageView) findViewById(R.id.pawn95);
        pawn95.setOnClickListener(this);

        //Row 6
        ImageView pawn16 = (ImageView) findViewById(R.id.pawn16);
        pawn16.setOnClickListener(this);

        ImageView pawn26 = (ImageView) findViewById(R.id.pawn26);
        pawn26.setOnClickListener(this);

        ImageView pawn36 = (ImageView) findViewById(R.id.pawn36);
        pawn36.setOnClickListener(this);

        ImageView pawn46 = (ImageView) findViewById(R.id.pawn46);
        pawn46.setOnClickListener(this);

        ImageView pawn56 = (ImageView) findViewById(R.id.pawn56);
        pawn56.setOnClickListener(this);

        ImageView pawn66 = (ImageView) findViewById(R.id.pawn66);
        pawn66.setOnClickListener(this);

        ImageView pawn76 = (ImageView) findViewById(R.id.pawn76);
        pawn76.setOnClickListener(this);

        ImageView pawn86 = (ImageView) findViewById(R.id.pawn86);
        pawn86.setOnClickListener(this);

        ImageView pawn96 = (ImageView) findViewById(R.id.pawn96);
        pawn96.setOnClickListener(this);

        //Row 7
        ImageView pawn17 = (ImageView) findViewById(R.id.pawn17);
        pawn17.setOnClickListener(this);

        ImageView pawn27 = (ImageView) findViewById(R.id.pawn27);
        pawn27.setOnClickListener(this);

        ImageView pawn37 = (ImageView) findViewById(R.id.pawn37);
        pawn37.setOnClickListener(this);

        ImageView pawn47 = (ImageView) findViewById(R.id.pawn47);
        pawn47.setOnClickListener(this);

        ImageView pawn57 = (ImageView) findViewById(R.id.pawn57);
        pawn57.setOnClickListener(this);

        ImageView pawn67 = (ImageView) findViewById(R.id.pawn67);
        pawn67.setOnClickListener(this);

        ImageView pawn77 = (ImageView) findViewById(R.id.pawn77);
        pawn77.setOnClickListener(this);

        ImageView pawn87 = (ImageView) findViewById(R.id.pawn87);
        pawn87.setOnClickListener(this);

        ImageView pawn97 = (ImageView) findViewById(R.id.pawn97);
        pawn97.setOnClickListener(this);

        //Row 8
        ImageView pawn18 = (ImageView) findViewById(R.id.pawn18);
        pawn18.setOnClickListener(this);

        ImageView pawn28 = (ImageView) findViewById(R.id.pawn28);
        pawn28.setOnClickListener(this);

        ImageView pawn38 = (ImageView) findViewById(R.id.pawn38);
        pawn38.setOnClickListener(this);

        ImageView pawn48 = (ImageView) findViewById(R.id.pawn48);
        pawn48.setOnClickListener(this);

        ImageView pawn58 = (ImageView) findViewById(R.id.pawn58);
        pawn58.setOnClickListener(this);

        ImageView pawn68 = (ImageView) findViewById(R.id.pawn68);
        pawn68.setOnClickListener(this);

        ImageView pawn78 = (ImageView) findViewById(R.id.pawn78);
        pawn78.setOnClickListener(this);

        ImageView pawn88 = (ImageView) findViewById(R.id.pawn88);
        pawn88.setOnClickListener(this);

        ImageView pawn98 = (ImageView) findViewById(R.id.pawn98);
        pawn98.setOnClickListener(this);

        //Row 9
        ImageView pawn19 = (ImageView) findViewById(R.id.pawn19);
        pawn19.setOnClickListener(this);

        ImageView pawn29 = (ImageView) findViewById(R.id.pawn29);
        pawn29.setOnClickListener(this);

        ImageView pawn39 = (ImageView) findViewById(R.id.pawn39);
        pawn39.setOnClickListener(this);

        ImageView pawn49 = (ImageView) findViewById(R.id.pawn49);
        pawn49.setOnClickListener(this);

        ImageView pawn59 = (ImageView) findViewById(R.id.pawn59);
        pawn59.setOnClickListener(this);

        ImageView pawn69 = (ImageView) findViewById(R.id.pawn69);
        pawn69.setOnClickListener(this);

        ImageView pawn79 = (ImageView) findViewById(R.id.pawn79);
        pawn79.setOnClickListener(this);

        ImageView pawn89 = (ImageView) findViewById(R.id.pawn89);
        pawn89.setOnClickListener(this);

        ImageView pawn99 = (ImageView) findViewById(R.id.pawn99);
        pawn99.setOnClickListener(this);
    }

    /**
     * calls
     *
     */
    public void setWallClickListeners(){
        //Row 1
        ImageView wall11 = (ImageView) findViewById(R.id.wall11);
        wall11.setOnClickListener(this);

        ImageView wall21 = (ImageView) findViewById(R.id.wall21);
        wall21.setOnClickListener(this);

        ImageView wall31 = (ImageView) findViewById(R.id.wall31);
        wall31.setOnClickListener(this);

        ImageView wall41 = (ImageView) findViewById(R.id.wall41);
        wall41.setOnClickListener(this);

        ImageView wall51 = (ImageView) findViewById(R.id.wall51);
        wall51.setOnClickListener(this);

        ImageView wall61 = (ImageView) findViewById(R.id.wall61);
        wall61.setOnClickListener(this);

        ImageView wall71 = (ImageView) findViewById(R.id.wall71);
        wall71.setOnClickListener(this);

        ImageView wall81 = (ImageView) findViewById(R.id.wall81);
        wall81.setOnClickListener(this);

        //Row 2
        ImageView wall12 = (ImageView) findViewById(R.id.wall12);
        wall12.setOnClickListener(this);

        ImageView wall22 = (ImageView) findViewById(R.id.wall22);
        wall22.setOnClickListener(this);

        ImageView wall32 = (ImageView) findViewById(R.id.wall32);
        wall32.setOnClickListener(this);

        ImageView wall42 = (ImageView) findViewById(R.id.wall42);
        wall42.setOnClickListener(this);

        ImageView wall52 = (ImageView) findViewById(R.id.wall52);
        wall52.setOnClickListener(this);

        ImageView wall62 = (ImageView) findViewById(R.id.wall62);
        wall62.setOnClickListener(this);

        ImageView wall72 = (ImageView) findViewById(R.id.wall72);
        wall72.setOnClickListener(this);

        ImageView wall82 = (ImageView) findViewById(R.id.wall82);
        wall82.setOnClickListener(this);

        //Row 3
        ImageView wall13 = (ImageView) findViewById(R.id.wall13);
        wall13.setOnClickListener(this);

        ImageView wall23 = (ImageView) findViewById(R.id.wall23);
        wall23.setOnClickListener(this);

        ImageView wall33 = (ImageView) findViewById(R.id.wall33);
        wall33.setOnClickListener(this);

        ImageView wall43 = (ImageView) findViewById(R.id.wall43);
        wall43.setOnClickListener(this);

        ImageView wall53 = (ImageView) findViewById(R.id.wall53);
        wall53.setOnClickListener(this);

        ImageView wall63 = (ImageView) findViewById(R.id.wall63);
        wall63.setOnClickListener(this);

        ImageView wall73 = (ImageView) findViewById(R.id.wall73);
        wall73.setOnClickListener(this);

        ImageView wall83 = (ImageView) findViewById(R.id.wall83);
        wall83.setOnClickListener(this);

        //Row 4
        ImageView wall14 = (ImageView) findViewById(R.id.wall14);
        wall14.setOnClickListener(this);

        ImageView wall24 = (ImageView) findViewById(R.id.wall24);
        wall24.setOnClickListener(this);

        ImageView wall34 = (ImageView) findViewById(R.id.wall34);
        wall34.setOnClickListener(this);

        ImageView wall44 = (ImageView) findViewById(R.id.wall44);
        wall44.setOnClickListener(this);

        ImageView wall54 = (ImageView) findViewById(R.id.wall54);
        wall54.setOnClickListener(this);

        ImageView wall64 = (ImageView) findViewById(R.id.wall64);
        wall64.setOnClickListener(this);

        ImageView wall74 = (ImageView) findViewById(R.id.wall74);
        wall74.setOnClickListener(this);

        ImageView wall84 = (ImageView) findViewById(R.id.wall84);
        wall84.setOnClickListener(this);

        //Row 5
        ImageView wall15 = (ImageView) findViewById(R.id.wall15);
        wall15.setOnClickListener(this);

        ImageView wall25 = (ImageView) findViewById(R.id.wall25);
        wall25.setOnClickListener(this);

        ImageView wall35 = (ImageView) findViewById(R.id.wall35);
        wall35.setOnClickListener(this);

        ImageView wall45 = (ImageView) findViewById(R.id.wall45);
        wall45.setOnClickListener(this);

        ImageView wall55 = (ImageView) findViewById(R.id.wall55);
        wall55.setOnClickListener(this);

        ImageView wall65 = (ImageView) findViewById(R.id.wall65);
        wall65.setOnClickListener(this);

        ImageView wall75 = (ImageView) findViewById(R.id.wall75);
        wall75.setOnClickListener(this);

        ImageView wall85 = (ImageView) findViewById(R.id.wall85);
        wall85.setOnClickListener(this);

        //Row 6
        ImageView wall16 = (ImageView) findViewById(R.id.wall16);
        wall16.setOnClickListener(this);

        ImageView wall26 = (ImageView) findViewById(R.id.wall26);
        wall26.setOnClickListener(this);

        ImageView wall36 = (ImageView) findViewById(R.id.wall36);
        wall36.setOnClickListener(this);

        ImageView wall46 = (ImageView) findViewById(R.id.wall46);
        wall46.setOnClickListener(this);

        ImageView wall56 = (ImageView) findViewById(R.id.wall56);
        wall56.setOnClickListener(this);

        ImageView wall66 = (ImageView) findViewById(R.id.wall66);
        wall66.setOnClickListener(this);

        ImageView wall76 = (ImageView) findViewById(R.id.wall76);
        wall76.setOnClickListener(this);

        ImageView wall86 = (ImageView) findViewById(R.id.wall86);
        wall86.setOnClickListener(this);

        //Row 7
        ImageView wall17 = (ImageView) findViewById(R.id.wall17);
        wall17.setOnClickListener(this);

        ImageView wall27 = (ImageView) findViewById(R.id.wall27);
        wall27.setOnClickListener(this);

        ImageView wall37 = (ImageView) findViewById(R.id.wall37);
        wall37.setOnClickListener(this);

        ImageView wall47 = (ImageView) findViewById(R.id.wall47);
        wall47.setOnClickListener(this);

        ImageView wall57 = (ImageView) findViewById(R.id.wall57);
        wall57.setOnClickListener(this);

        ImageView wall67 = (ImageView) findViewById(R.id.wall67);
        wall67.setOnClickListener(this);

        ImageView wall77 = (ImageView) findViewById(R.id.wall77);
        wall77.setOnClickListener(this);

        ImageView wall87 = (ImageView) findViewById(R.id.wall87);
        wall87.setOnClickListener(this);

        //Row 8
        ImageView wall18 = (ImageView) findViewById(R.id.wall18);
        wall18.setOnClickListener(this);

        ImageView wall28 = (ImageView) findViewById(R.id.wall28);
        wall28.setOnClickListener(this);

        ImageView wall38 = (ImageView) findViewById(R.id.wall38);
        wall38.setOnClickListener(this);

        ImageView wall48 = (ImageView) findViewById(R.id.wall48);
        wall48.setOnClickListener(this);

        ImageView wall58 = (ImageView) findViewById(R.id.wall58);
        wall58.setOnClickListener(this);

        ImageView wall68 = (ImageView) findViewById(R.id.wall68);
        wall68.setOnClickListener(this);

        ImageView wall78 = (ImageView) findViewById(R.id.wall78);
        wall78.setOnClickListener(this);

        ImageView wall88 = (ImageView) findViewById(R.id.wall88);
        wall88.setOnClickListener(this);



    }

    /**
     * calls
     *
     */
    public void setPawnClickListenersON() {
        //Row 1
        ImageView pawn11 = (ImageView) findViewById(R.id.pawn11);
        pawn11.setClickable(true);

        ImageView pawn21 = (ImageView) findViewById(R.id.pawn21);
        pawn21.setClickable(true);

        ImageView pawn31 = (ImageView) findViewById(R.id.pawn31);
        pawn31.setClickable(true);

        ImageView pawn41 = (ImageView) findViewById(R.id.pawn41);
        pawn41.setClickable(true);

        ImageView pawn51 = (ImageView) findViewById(R.id.pawn51);
        pawn51.setClickable(true);

        ImageView pawn61 = (ImageView) findViewById(R.id.pawn61);
        pawn61.setClickable(true);

        ImageView pawn71 = (ImageView) findViewById(R.id.pawn71);
        pawn71.setClickable(true);

        ImageView pawn81 = (ImageView) findViewById(R.id.pawn81);
        pawn81.setClickable(true);

        ImageView pawn91 = (ImageView) findViewById(R.id.pawn91);
        pawn91.setClickable(true);

        //Row 2
        ImageView pawn12 = (ImageView) findViewById(R.id.pawn12);
        pawn12.setClickable(true);

        ImageView pawn22 = (ImageView) findViewById(R.id.pawn22);
        pawn22.setClickable(true);

        ImageView pawn32 = (ImageView) findViewById(R.id.pawn32);
        pawn32.setClickable(true);

        ImageView pawn42 = (ImageView) findViewById(R.id.pawn42);
        pawn42.setClickable(true);

        ImageView pawn52 = (ImageView) findViewById(R.id.pawn52);
        pawn52.setClickable(true);

        ImageView pawn62 = (ImageView) findViewById(R.id.pawn62);
        pawn62.setClickable(true);

        ImageView pawn72 = (ImageView) findViewById(R.id.pawn72);
        pawn72.setClickable(true);

        ImageView pawn82 = (ImageView) findViewById(R.id.pawn82);
        pawn82.setClickable(true);

        ImageView pawn92 = (ImageView) findViewById(R.id.pawn92);
        pawn92.setClickable(true);

        //Row 3
        ImageView pawn13 = (ImageView) findViewById(R.id.pawn13);
        pawn13.setClickable(true);

        ImageView pawn23 = (ImageView) findViewById(R.id.pawn23);
        pawn23.setClickable(true);

        ImageView pawn33 = (ImageView) findViewById(R.id.pawn33);
        pawn33.setClickable(true);

        ImageView pawn43 = (ImageView) findViewById(R.id.pawn43);
        pawn43.setClickable(true);

        ImageView pawn53 = (ImageView) findViewById(R.id.pawn53);
        pawn53.setClickable(true);

        ImageView pawn63 = (ImageView) findViewById(R.id.pawn63);
        pawn63.setClickable(true);

        ImageView pawn73 = (ImageView) findViewById(R.id.pawn73);
        pawn73.setClickable(true);

        ImageView pawn83 = (ImageView) findViewById(R.id.pawn83);
        pawn83.setClickable(true);

        ImageView pawn93 = (ImageView) findViewById(R.id.pawn93);
        pawn93.setClickable(true);

        //Row 4
        ImageView pawn14 = (ImageView) findViewById(R.id.pawn14);
        pawn14.setClickable(true);

        ImageView pawn24 = (ImageView) findViewById(R.id.pawn24);
        pawn24.setClickable(true);

        ImageView pawn34 = (ImageView) findViewById(R.id.pawn34);
        pawn34.setClickable(true);

        ImageView pawn44 = (ImageView) findViewById(R.id.pawn44);
        pawn44.setClickable(true);

        ImageView pawn54 = (ImageView) findViewById(R.id.pawn54);
        pawn54.setClickable(true);

        ImageView pawn64 = (ImageView) findViewById(R.id.pawn64);
        pawn64.setClickable(true);

        ImageView pawn74 = (ImageView) findViewById(R.id.pawn74);
        pawn74.setClickable(true);

        ImageView pawn84 = (ImageView) findViewById(R.id.pawn84);
        pawn84.setClickable(true);

        ImageView pawn94 = (ImageView) findViewById(R.id.pawn94);
        pawn94.setClickable(true);

        //Row 5
        ImageView pawn15 = (ImageView) findViewById(R.id.pawn15);
        pawn15.setClickable(true);

        ImageView pawn25 = (ImageView) findViewById(R.id.pawn25);
        pawn25.setClickable(true);

        ImageView pawn35 = (ImageView) findViewById(R.id.pawn35);
        pawn35.setClickable(true);

        ImageView pawn45 = (ImageView) findViewById(R.id.pawn45);
        pawn45.setClickable(true);

        ImageView pawn55 = (ImageView) findViewById(R.id.pawn55);
        pawn55.setClickable(true);

        ImageView pawn65 = (ImageView) findViewById(R.id.pawn65);
        pawn65.setClickable(true);

        ImageView pawn75 = (ImageView) findViewById(R.id.pawn75);
        pawn75.setClickable(true);

        ImageView pawn85 = (ImageView) findViewById(R.id.pawn85);
        pawn85.setClickable(true);

        ImageView pawn95 = (ImageView) findViewById(R.id.pawn95);
        pawn95.setClickable(true);

        //Row 6
        ImageView pawn16 = (ImageView) findViewById(R.id.pawn16);
        pawn16.setClickable(true);

        ImageView pawn26 = (ImageView) findViewById(R.id.pawn26);
        pawn26.setClickable(true);

        ImageView pawn36 = (ImageView) findViewById(R.id.pawn36);
        pawn36.setClickable(true);

        ImageView pawn46 = (ImageView) findViewById(R.id.pawn46);
        pawn46.setClickable(true);

        ImageView pawn56 = (ImageView) findViewById(R.id.pawn56);
        pawn56.setClickable(true);

        ImageView pawn66 = (ImageView) findViewById(R.id.pawn66);
        pawn66.setClickable(true);

        ImageView pawn76 = (ImageView) findViewById(R.id.pawn76);
        pawn76.setClickable(true);

        ImageView pawn86 = (ImageView) findViewById(R.id.pawn86);
        pawn86.setClickable(true);

        ImageView pawn96 = (ImageView) findViewById(R.id.pawn96);
        pawn96.setClickable(true);

        //Row 7
        ImageView pawn17 = (ImageView) findViewById(R.id.pawn17);
        pawn17.setClickable(true);

        ImageView pawn27 = (ImageView) findViewById(R.id.pawn27);
        pawn27.setClickable(true);

        ImageView pawn37 = (ImageView) findViewById(R.id.pawn37);
        pawn37.setClickable(true);

        ImageView pawn47 = (ImageView) findViewById(R.id.pawn47);
        pawn47.setClickable(true);

        ImageView pawn57 = (ImageView) findViewById(R.id.pawn57);
        pawn57.setClickable(true);

        ImageView pawn67 = (ImageView) findViewById(R.id.pawn67);
        pawn67.setClickable(true);

        ImageView pawn77 = (ImageView) findViewById(R.id.pawn77);
        pawn77.setClickable(true);

        ImageView pawn87 = (ImageView) findViewById(R.id.pawn87);
        pawn87.setClickable(true);

        ImageView pawn97 = (ImageView) findViewById(R.id.pawn97);
        pawn97.setClickable(true);

        //Row 8
        ImageView pawn18 = (ImageView) findViewById(R.id.pawn18);
        pawn18.setClickable(true);

        ImageView pawn28 = (ImageView) findViewById(R.id.pawn28);
        pawn28.setClickable(true);

        ImageView pawn38 = (ImageView) findViewById(R.id.pawn38);
        pawn38.setClickable(true);

        ImageView pawn48 = (ImageView) findViewById(R.id.pawn48);
        pawn48.setClickable(true);

        ImageView pawn58 = (ImageView) findViewById(R.id.pawn58);
        pawn58.setClickable(true);

        ImageView pawn68 = (ImageView) findViewById(R.id.pawn68);
        pawn68.setClickable(true);

        ImageView pawn78 = (ImageView) findViewById(R.id.pawn78);
        pawn78.setClickable(true);

        ImageView pawn88 = (ImageView) findViewById(R.id.pawn88);
        pawn88.setClickable(true);

        ImageView pawn98 = (ImageView) findViewById(R.id.pawn98);
        pawn98.setClickable(true);

        //Row 9
        ImageView pawn19 = (ImageView) findViewById(R.id.pawn19);
        pawn19.setClickable(true);

        ImageView pawn29 = (ImageView) findViewById(R.id.pawn29);
        pawn29.setClickable(true);

        ImageView pawn39 = (ImageView) findViewById(R.id.pawn39);
        pawn39.setClickable(true);

        ImageView pawn49 = (ImageView) findViewById(R.id.pawn49);
        pawn49.setClickable(true);

        ImageView pawn59 = (ImageView) findViewById(R.id.pawn59);
        pawn59.setClickable(true);

        ImageView pawn69 = (ImageView) findViewById(R.id.pawn69);
        pawn69.setClickable(true);

        ImageView pawn79 = (ImageView) findViewById(R.id.pawn79);
        pawn79.setClickable(true);

        ImageView pawn89 = (ImageView) findViewById(R.id.pawn89);
        pawn89.setClickable(true);

        ImageView pawn99 = (ImageView) findViewById(R.id.pawn99);
        pawn99.setClickable(true);
    }

    /**
     * calls
     *
     */
    public void setWallClickListenersON() {
        //Row 1
        ImageView wall11 = (ImageView) findViewById(R.id.wall11);
        wall11.setClickable(true);

        ImageView wall21 = (ImageView) findViewById(R.id.wall21);
        wall21.setClickable(true);

        ImageView wall31 = (ImageView) findViewById(R.id.wall31);
        wall31.setClickable(true);

        ImageView wall41 = (ImageView) findViewById(R.id.wall41);
        wall41.setClickable(true);

        ImageView wall51 = (ImageView) findViewById(R.id.wall51);
        wall51.setClickable(true);

        ImageView wall61 = (ImageView) findViewById(R.id.wall61);
        wall61.setClickable(true);

        ImageView wall71 = (ImageView) findViewById(R.id.wall71);
        wall71.setClickable(true);

        ImageView wall81 = (ImageView) findViewById(R.id.wall81);
        wall81.setClickable(true);

        //Row 2
        ImageView wall12 = (ImageView) findViewById(R.id.wall12);
        wall12.setClickable(true);

        ImageView wall22 = (ImageView) findViewById(R.id.wall22);
        wall22.setClickable(true);

        ImageView wall32 = (ImageView) findViewById(R.id.wall32);
        wall32.setClickable(true);

        ImageView wall42 = (ImageView) findViewById(R.id.wall42);
        wall42.setClickable(true);

        ImageView wall52 = (ImageView) findViewById(R.id.wall52);
        wall52.setClickable(true);

        ImageView wall62 = (ImageView) findViewById(R.id.wall62);
        wall62.setClickable(true);

        ImageView wall72 = (ImageView) findViewById(R.id.wall72);
        wall72.setClickable(true);

        ImageView wall82 = (ImageView) findViewById(R.id.wall82);
        wall82.setClickable(true);

        //Row 3
        ImageView wall13 = (ImageView) findViewById(R.id.wall13);
        wall13.setClickable(true);

        ImageView wall23 = (ImageView) findViewById(R.id.wall23);
        wall23.setClickable(true);

        ImageView wall33 = (ImageView) findViewById(R.id.wall33);
        wall33.setClickable(true);

        ImageView wall43 = (ImageView) findViewById(R.id.wall43);
        wall43.setClickable(true);

        ImageView wall53 = (ImageView) findViewById(R.id.wall53);
        wall53.setClickable(true);

        ImageView wall63 = (ImageView) findViewById(R.id.wall63);
        wall63.setClickable(true);

        ImageView wall73 = (ImageView) findViewById(R.id.wall73);
        wall73.setClickable(true);

        ImageView wall83 = (ImageView) findViewById(R.id.wall83);
        wall83.setClickable(true);

        //Row 4
        ImageView wall14 = (ImageView) findViewById(R.id.wall14);
        wall14.setClickable(true);

        ImageView wall24 = (ImageView) findViewById(R.id.wall24);
        wall24.setClickable(true);

        ImageView wall34 = (ImageView) findViewById(R.id.wall34);
        wall34.setClickable(true);

        ImageView wall44 = (ImageView) findViewById(R.id.wall44);
        wall44.setClickable(true);

        ImageView wall54 = (ImageView) findViewById(R.id.wall54);
        wall54.setClickable(true);

        ImageView wall64 = (ImageView) findViewById(R.id.wall64);
        wall64.setClickable(true);

        ImageView wall74 = (ImageView) findViewById(R.id.wall74);
        wall74.setClickable(true);

        ImageView wall84 = (ImageView) findViewById(R.id.wall84);
        wall84.setClickable(true);

        //Row 5
        ImageView wall15 = (ImageView) findViewById(R.id.wall15);
        wall15.setClickable(true);

        ImageView wall25 = (ImageView) findViewById(R.id.wall25);
        wall25.setClickable(true);

        ImageView wall35 = (ImageView) findViewById(R.id.wall35);
        wall35.setClickable(true);

        ImageView wall45 = (ImageView) findViewById(R.id.wall45);
        wall45.setClickable(true);

        ImageView wall55 = (ImageView) findViewById(R.id.wall55);
        wall55.setClickable(true);

        ImageView wall65 = (ImageView) findViewById(R.id.wall65);
        wall65.setClickable(true);

        ImageView wall75 = (ImageView) findViewById(R.id.wall75);
        wall75.setClickable(true);

        ImageView wall85 = (ImageView) findViewById(R.id.wall85);
        wall85.setClickable(true);

        //Row 6
        ImageView wall16 = (ImageView) findViewById(R.id.wall16);
        wall16.setClickable(true);

        ImageView wall26 = (ImageView) findViewById(R.id.wall26);
        wall26.setClickable(true);

        ImageView wall36 = (ImageView) findViewById(R.id.wall36);
        wall36.setClickable(true);

        ImageView wall46 = (ImageView) findViewById(R.id.wall46);
        wall46.setClickable(true);

        ImageView wall56 = (ImageView) findViewById(R.id.wall56);
        wall56.setClickable(true);

        ImageView wall66 = (ImageView) findViewById(R.id.wall66);
        wall66.setClickable(true);

        ImageView wall76 = (ImageView) findViewById(R.id.wall76);
        wall76.setClickable(true);

        ImageView wall86 = (ImageView) findViewById(R.id.wall86);
        wall86.setClickable(true);

        //Row 7
        ImageView wall17 = (ImageView) findViewById(R.id.wall17);
        wall17.setClickable(true);

        ImageView wall27 = (ImageView) findViewById(R.id.wall27);
        wall27.setClickable(true);

        ImageView wall37 = (ImageView) findViewById(R.id.wall37);
        wall37.setClickable(true);

        ImageView wall47 = (ImageView) findViewById(R.id.wall47);
        wall47.setClickable(true);

        ImageView wall57 = (ImageView) findViewById(R.id.wall57);
        wall57.setClickable(true);

        ImageView wall67 = (ImageView) findViewById(R.id.wall67);
        wall67.setClickable(true);

        ImageView wall77 = (ImageView) findViewById(R.id.wall77);
        wall77.setClickable(true);

        ImageView wall87 = (ImageView) findViewById(R.id.wall87);
        wall87.setClickable(true);

        //Row 8
        ImageView wall18 = (ImageView) findViewById(R.id.wall18);
        wall18.setClickable(true);

        ImageView wall28 = (ImageView) findViewById(R.id.wall28);
        wall28.setClickable(true);

        ImageView wall38 = (ImageView) findViewById(R.id.wall38);
        wall38.setClickable(true);

        ImageView wall48 = (ImageView) findViewById(R.id.wall48);
        wall48.setClickable(true);

        ImageView wall58 = (ImageView) findViewById(R.id.wall58);
        wall58.setClickable(true);

        ImageView wall68 = (ImageView) findViewById(R.id.wall68);
        wall68.setClickable(true);

        ImageView wall78 = (ImageView) findViewById(R.id.wall78);
        wall78.setClickable(true);

        ImageView wall88 = (ImageView) findViewById(R.id.wall88);
        wall88.setClickable(true);

    }

    /**
     * calls
     *
     */
    public void setPawnCLickListenersOFF(){
        //Row 1
        ImageView pawn11 = (ImageView) findViewById(R.id.pawn11);
        pawn11.setClickable(false);

        ImageView pawn21 = (ImageView) findViewById(R.id.pawn21);
        pawn21.setClickable(false);

        ImageView pawn31 = (ImageView) findViewById(R.id.pawn31);
        pawn31.setClickable(false);

        ImageView pawn41 = (ImageView) findViewById(R.id.pawn41);
        pawn41.setClickable(false);

        ImageView pawn51 = (ImageView) findViewById(R.id.pawn51);
        pawn51.setClickable(false);

        ImageView pawn61 = (ImageView) findViewById(R.id.pawn61);
        pawn61.setClickable(false);

        ImageView pawn71 = (ImageView) findViewById(R.id.pawn71);
        pawn71.setClickable(false);

        ImageView pawn81 = (ImageView) findViewById(R.id.pawn81);
        pawn81.setClickable(false);

        ImageView pawn91 = (ImageView) findViewById(R.id.pawn91);
        pawn91.setClickable(false);

        //Row 2
        ImageView pawn12 = (ImageView) findViewById(R.id.pawn12);
        pawn12.setClickable(false);

        ImageView pawn22 = (ImageView) findViewById(R.id.pawn22);
        pawn22.setClickable(false);

        ImageView pawn32 = (ImageView) findViewById(R.id.pawn32);
        pawn32.setClickable(false);

        ImageView pawn42 = (ImageView) findViewById(R.id.pawn42);
        pawn42.setClickable(false);

        ImageView pawn52 = (ImageView) findViewById(R.id.pawn52);
        pawn52.setClickable(false);

        ImageView pawn62 = (ImageView) findViewById(R.id.pawn62);
        pawn62.setClickable(false);

        ImageView pawn72 = (ImageView) findViewById(R.id.pawn72);
        pawn72.setClickable(false);

        ImageView pawn82 = (ImageView) findViewById(R.id.pawn82);
        pawn82.setClickable(false);

        ImageView pawn92 = (ImageView) findViewById(R.id.pawn92);
        pawn92.setClickable(false);

        //Row 3
        ImageView pawn13 = (ImageView) findViewById(R.id.pawn13);
        pawn13.setClickable(false);

        ImageView pawn23 = (ImageView) findViewById(R.id.pawn23);
        pawn23.setClickable(false);

        ImageView pawn33 = (ImageView) findViewById(R.id.pawn33);
        pawn33.setClickable(false);

        ImageView pawn43 = (ImageView) findViewById(R.id.pawn43);
        pawn43.setClickable(false);

        ImageView pawn53 = (ImageView) findViewById(R.id.pawn53);
        pawn53.setClickable(false);

        ImageView pawn63 = (ImageView) findViewById(R.id.pawn63);
        pawn63.setClickable(false);

        ImageView pawn73 = (ImageView) findViewById(R.id.pawn73);
        pawn73.setClickable(false);

        ImageView pawn83 = (ImageView) findViewById(R.id.pawn83);
        pawn83.setClickable(false);

        ImageView pawn93 = (ImageView) findViewById(R.id.pawn93);
        pawn93.setClickable(false);

        //Row 4
        ImageView pawn14 = (ImageView) findViewById(R.id.pawn14);
        pawn14.setClickable(false);

        ImageView pawn24 = (ImageView) findViewById(R.id.pawn24);
        pawn24.setClickable(false);

        ImageView pawn34 = (ImageView) findViewById(R.id.pawn34);
        pawn34.setClickable(false);

        ImageView pawn44 = (ImageView) findViewById(R.id.pawn44);
        pawn44.setClickable(false);

        ImageView pawn54 = (ImageView) findViewById(R.id.pawn54);
        pawn54.setClickable(false);

        ImageView pawn64 = (ImageView) findViewById(R.id.pawn64);
        pawn64.setClickable(false);

        ImageView pawn74 = (ImageView) findViewById(R.id.pawn74);
        pawn74.setClickable(false);

        ImageView pawn84 = (ImageView) findViewById(R.id.pawn84);
        pawn84.setClickable(false);

        ImageView pawn94 = (ImageView) findViewById(R.id.pawn94);
        pawn94.setClickable(false);

        //Row 5
        ImageView pawn15 = (ImageView) findViewById(R.id.pawn15);
        pawn15.setClickable(false);

        ImageView pawn25 = (ImageView) findViewById(R.id.pawn25);
        pawn25.setClickable(false);

        ImageView pawn35 = (ImageView) findViewById(R.id.pawn35);
        pawn35.setClickable(false);

        ImageView pawn45 = (ImageView) findViewById(R.id.pawn45);
        pawn45.setClickable(false);

        ImageView pawn55 = (ImageView) findViewById(R.id.pawn55);
        pawn55.setClickable(false);

        ImageView pawn65 = (ImageView) findViewById(R.id.pawn65);
        pawn65.setClickable(false);

        ImageView pawn75 = (ImageView) findViewById(R.id.pawn75);
        pawn75.setClickable(false);

        ImageView pawn85 = (ImageView) findViewById(R.id.pawn85);
        pawn85.setClickable(false);

        ImageView pawn95 = (ImageView) findViewById(R.id.pawn95);
        pawn95.setClickable(false);

        //Row 6
        ImageView pawn16 = (ImageView) findViewById(R.id.pawn16);
        pawn16.setClickable(false);

        ImageView pawn26 = (ImageView) findViewById(R.id.pawn26);
        pawn26.setClickable(false);

        ImageView pawn36 = (ImageView) findViewById(R.id.pawn36);
        pawn36.setClickable(false);

        ImageView pawn46 = (ImageView) findViewById(R.id.pawn46);
        pawn46.setClickable(false);

        ImageView pawn56 = (ImageView) findViewById(R.id.pawn56);
        pawn56.setClickable(false);

        ImageView pawn66 = (ImageView) findViewById(R.id.pawn66);
        pawn66.setClickable(false);

        ImageView pawn76 = (ImageView) findViewById(R.id.pawn76);
        pawn76.setClickable(false);

        ImageView pawn86 = (ImageView) findViewById(R.id.pawn86);
        pawn86.setClickable(false);

        ImageView pawn96 = (ImageView) findViewById(R.id.pawn96);
        pawn96.setClickable(false);

        //Row 7
        ImageView pawn17 = (ImageView) findViewById(R.id.pawn17);
        pawn17.setClickable(false);

        ImageView pawn27 = (ImageView) findViewById(R.id.pawn27);
        pawn27.setClickable(false);

        ImageView pawn37 = (ImageView) findViewById(R.id.pawn37);
        pawn37.setClickable(false);

        ImageView pawn47 = (ImageView) findViewById(R.id.pawn47);
        pawn47.setClickable(false);

        ImageView pawn57 = (ImageView) findViewById(R.id.pawn57);
        pawn57.setClickable(false);

        ImageView pawn67 = (ImageView) findViewById(R.id.pawn67);
        pawn67.setClickable(false);

        ImageView pawn77 = (ImageView) findViewById(R.id.pawn77);
        pawn77.setClickable(false);

        ImageView pawn87 = (ImageView) findViewById(R.id.pawn87);
        pawn87.setClickable(false);

        ImageView pawn97 = (ImageView) findViewById(R.id.pawn97);
        pawn97.setClickable(false);

        //Row 8
        ImageView pawn18 = (ImageView) findViewById(R.id.pawn18);
        pawn18.setClickable(false);

        ImageView pawn28 = (ImageView) findViewById(R.id.pawn28);
        pawn28.setClickable(false);

        ImageView pawn38 = (ImageView) findViewById(R.id.pawn38);
        pawn38.setClickable(false);

        ImageView pawn48 = (ImageView) findViewById(R.id.pawn48);
        pawn48.setClickable(false);

        ImageView pawn58 = (ImageView) findViewById(R.id.pawn58);
        pawn58.setClickable(false);

        ImageView pawn68 = (ImageView) findViewById(R.id.pawn68);
        pawn68.setClickable(false);

        ImageView pawn78 = (ImageView) findViewById(R.id.pawn78);
        pawn78.setClickable(false);

        ImageView pawn88 = (ImageView) findViewById(R.id.pawn88);
        pawn88.setClickable(false);

        ImageView pawn98 = (ImageView) findViewById(R.id.pawn98);
        pawn98.setClickable(false);

        //Row 9
        ImageView pawn19 = (ImageView) findViewById(R.id.pawn19);
        pawn19.setClickable(false);

        ImageView pawn29 = (ImageView) findViewById(R.id.pawn29);
        pawn29.setClickable(false);

        ImageView pawn39 = (ImageView) findViewById(R.id.pawn39);
        pawn39.setClickable(false);

        ImageView pawn49 = (ImageView) findViewById(R.id.pawn49);
        pawn49.setClickable(false);

        ImageView pawn59 = (ImageView) findViewById(R.id.pawn59);
        pawn59.setClickable(false);

        ImageView pawn69 = (ImageView) findViewById(R.id.pawn69);
        pawn69.setClickable(false);

        ImageView pawn79 = (ImageView) findViewById(R.id.pawn79);
        pawn79.setClickable(false);

        ImageView pawn89 = (ImageView) findViewById(R.id.pawn89);
        pawn89.setClickable(false);

        ImageView pawn99 = (ImageView) findViewById(R.id.pawn99);
        pawn99.setClickable(false);
    }

    /**
     * calls
     *
     */
    public void setWallCLickListenersOFF(){
        //Row 1
        ImageView wall11 = (ImageView) findViewById(R.id.wall11);
        wall11.setClickable(false);

        ImageView wall21 = (ImageView) findViewById(R.id.wall21);
        wall21.setClickable(false);

        ImageView wall31 = (ImageView) findViewById(R.id.wall31);
        wall31.setClickable(false);

        ImageView wall41 = (ImageView) findViewById(R.id.wall41);
        wall41.setClickable(false);

        ImageView wall51 = (ImageView) findViewById(R.id.wall51);
        wall51.setClickable(false);

        ImageView wall61 = (ImageView) findViewById(R.id.wall61);
        wall61.setClickable(false);

        ImageView wall71 = (ImageView) findViewById(R.id.wall71);
        wall71.setClickable(false);

        ImageView wall81 = (ImageView) findViewById(R.id.wall81);
        wall81.setClickable(false);

        //Row 2
        ImageView wall12 = (ImageView) findViewById(R.id.wall12);
        wall12.setClickable(false);

        ImageView wall22 = (ImageView) findViewById(R.id.wall22);
        wall22.setClickable(false);

        ImageView wall32 = (ImageView) findViewById(R.id.wall32);
        wall32.setClickable(false);

        ImageView wall42 = (ImageView) findViewById(R.id.wall42);
        wall42.setClickable(false);

        ImageView wall52 = (ImageView) findViewById(R.id.wall52);
        wall52.setClickable(false);

        ImageView wall62 = (ImageView) findViewById(R.id.wall62);
        wall62.setClickable(false);

        ImageView wall72 = (ImageView) findViewById(R.id.wall72);
        wall72.setClickable(false);

        ImageView wall82 = (ImageView) findViewById(R.id.wall82);
        wall82.setClickable(false);

        //Row 3
        ImageView wall13 = (ImageView) findViewById(R.id.wall13);
        wall13.setClickable(false);

        ImageView wall23 = (ImageView) findViewById(R.id.wall23);
        wall23.setClickable(false);

        ImageView wall33 = (ImageView) findViewById(R.id.wall33);
        wall33.setClickable(false);

        ImageView wall43 = (ImageView) findViewById(R.id.wall43);
        wall43.setClickable(false);

        ImageView wall53 = (ImageView) findViewById(R.id.wall53);
        wall53.setClickable(false);

        ImageView wall63 = (ImageView) findViewById(R.id.wall63);
        wall63.setClickable(false);

        ImageView wall73 = (ImageView) findViewById(R.id.wall73);
        wall73.setClickable(false);

        ImageView wall83 = (ImageView) findViewById(R.id.wall83);
        wall83.setClickable(false);

        //Row 4
        ImageView wall14 = (ImageView) findViewById(R.id.wall14);
        wall14.setClickable(false);

        ImageView wall24 = (ImageView) findViewById(R.id.wall24);
        wall24.setClickable(false);

        ImageView wall34 = (ImageView) findViewById(R.id.wall34);
        wall34.setClickable(false);

        ImageView wall44 = (ImageView) findViewById(R.id.wall44);
        wall44.setClickable(false);

        ImageView wall54 = (ImageView) findViewById(R.id.wall54);
        wall54.setClickable(false);

        ImageView wall64 = (ImageView) findViewById(R.id.wall64);
        wall64.setClickable(false);

        ImageView wall74 = (ImageView) findViewById(R.id.wall74);
        wall74.setClickable(false);

        ImageView wall84 = (ImageView) findViewById(R.id.wall84);
        wall84.setClickable(false);

        //Row 5
        ImageView wall15 = (ImageView) findViewById(R.id.wall15);
        wall15.setClickable(false);

        ImageView wall25 = (ImageView) findViewById(R.id.wall25);
        wall25.setClickable(false);

        ImageView wall35 = (ImageView) findViewById(R.id.wall35);
        wall35.setClickable(false);

        ImageView wall45 = (ImageView) findViewById(R.id.wall45);
        wall45.setClickable(false);

        ImageView wall55 = (ImageView) findViewById(R.id.wall55);
        wall55.setClickable(false);

        ImageView wall65 = (ImageView) findViewById(R.id.wall65);
        wall65.setClickable(false);

        ImageView wall75 = (ImageView) findViewById(R.id.wall75);
        wall75.setClickable(false);

        ImageView wall85 = (ImageView) findViewById(R.id.wall85);
        wall85.setClickable(false);

        //Row 6
        ImageView wall16 = (ImageView) findViewById(R.id.wall16);
        wall16.setClickable(false);

        ImageView wall26 = (ImageView) findViewById(R.id.wall26);
        wall26.setClickable(false);

        ImageView wall36 = (ImageView) findViewById(R.id.wall36);
        wall36.setClickable(false);

        ImageView wall46 = (ImageView) findViewById(R.id.wall46);
        wall46.setClickable(false);

        ImageView wall56 = (ImageView) findViewById(R.id.wall56);
        wall56.setClickable(false);

        ImageView wall66 = (ImageView) findViewById(R.id.wall66);
        wall66.setClickable(false);

        ImageView wall76 = (ImageView) findViewById(R.id.wall76);
        wall76.setClickable(false);

        ImageView wall86 = (ImageView) findViewById(R.id.wall86);
        wall86.setClickable(false);

        //Row 7
        ImageView wall17 = (ImageView) findViewById(R.id.wall17);
        wall17.setClickable(false);

        ImageView wall27 = (ImageView) findViewById(R.id.wall27);
        wall27.setClickable(false);

        ImageView wall37 = (ImageView) findViewById(R.id.wall37);
        wall37.setClickable(false);

        ImageView wall47 = (ImageView) findViewById(R.id.wall47);
        wall47.setClickable(false);

        ImageView wall57 = (ImageView) findViewById(R.id.wall57);
        wall57.setClickable(false);

        ImageView wall67 = (ImageView) findViewById(R.id.wall67);
        wall67.setClickable(false);

        ImageView wall77 = (ImageView) findViewById(R.id.wall77);
        wall77.setClickable(false);

        ImageView wall87 = (ImageView) findViewById(R.id.wall87);
        wall87.setClickable(false);

        //Row 8
        ImageView wall18 = (ImageView) findViewById(R.id.wall18);
        wall18.setClickable(false);

        ImageView wall28 = (ImageView) findViewById(R.id.wall28);
        wall28.setClickable(false);

        ImageView wall38 = (ImageView) findViewById(R.id.wall38);
        wall38.setClickable(false);

        ImageView wall48 = (ImageView) findViewById(R.id.wall48);
        wall48.setClickable(false);

        ImageView wall58 = (ImageView) findViewById(R.id.wall58);
        wall58.setClickable(false);

        ImageView wall68 = (ImageView) findViewById(R.id.wall68);
        wall68.setClickable(false);

        ImageView wall78 = (ImageView) findViewById(R.id.wall78);
        wall78.setClickable(false);

        ImageView wall88 = (ImageView) findViewById(R.id.wall88);
        wall88.setClickable(false);


    }

    /**
     * Checks to see if either the user's pawn, or AI's pawn is
     * on their opponents side of the board (the winning position).
     *
     * @return boolean
     * True if someone won, false if no one won.
     */
    private boolean checkForWin() {
        if (user.userPosition.y == 1 || ai.aiPosition.y == 9)
            return true; //Either the AI or User has won.
        else
            return false; //No one has won, keep playing the game!
    }


}