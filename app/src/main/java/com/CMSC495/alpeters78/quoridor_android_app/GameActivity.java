package com.CMSC495.alpeters78.quoridor_android_app;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.ArrayList;

public class GameActivity extends Activity implements View.OnClickListener {

    //Instance variables
    public ArrayList<Wall> wallArray = new ArrayList<Wall>(); //Stores the details of the walls on the board
    public ArrayList<Point> hBlockedPathList = new ArrayList<Point>(); //The paths that are blocked by horizontal walls
    public ArrayList<Point> vBlockedPathList = new ArrayList<Point>(); //The paths that are blocked by horizontal walls
    public User user = new User();
    public AI ai = new AI();
    public int currentResID;
    public int aiResID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //These will be inside the loop I'm just using it for testing
        setPawnClickListners();
        setWallClickListners();

        //Initialize game board
        //userPawn start position
        ImageView User = (ImageView) findViewById(R.id.pawn59);
        User.setImageResource(R.drawable.user_pawn);
        currentResID = R.id.pawn59;
        //Ai pawn start position
        ImageView AI = (ImageView) findViewById(R.id.pawn51);
        AI.setImageResource(R.drawable.ai_pawn);
        aiResID = R.id.pawn51;


        // the loop will be set up after all the components are working properly.
        //TODO start game Loop while(!win){}
        //TODO setup radio buttons to call clickListeners inside loop
        //TODO boolean win == checkForWin(); if true break; and call popup
        //TODO AI turn
        //TODO win == checkForWin(); if true break; and call popup
        //TODO end Loop



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
     * True if someone won, false if no one won.
     */

    private boolean checkForWin() {
        if (user.userPosition.y == 1 || ai.aiPosition.y == 9)
            return true; //Either the AI or User has won.
        else
            return false; //No one has won, keep playing the game!
    }

    public void setPawnClickListners(){
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

    public void setWallClickListners(){
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


    @Override
    public void onClick(View view) {
        //This is how to change an image onClick
        //ImageView imageView = (ImageView) findViewById(R.id.myimageview);
        //imageView.setImageResource(R.drawable.myimage);
            /*
            int position = view.getId();
            findViewById(position);
            String name = getResources().getResourceEntryName(position);
            String clickPosition = name.substring(name.length(), -2);
            int pawnPosition = Integer.parseInt(clickPosition);
            int x = pawnPosition / 10;
            int y = pawnPosition % 10;
            */



        //The conditional will be added after the radio buttons are set up.
        //TODO if pawn radio button is selected
        pawnClick(view);

        //TODO else if H wall radio is selected
        hWallClick(view);

        //TODO else if V wall radio is selected
        vWallClick(view);


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

    public boolean setHorWallImage(int resID, Point aPossibleWallPosition){
        if (user.isValidHorizontalWallMove(aPossibleWallPosition, ai.aiPosition, hBlockedPathList, vBlockedPathList)) {

            //TODO set path images and wall center to H_wall

            return true;
        } else {

            return false;
        }
    }

    public boolean setVerWallImage(int resID, Point aPossibleWallPosition){
        if (user.isValidVerticalWallMove(aPossibleWallPosition, ai.aiPosition, hBlockedPathList, vBlockedPathList)) {

            //TODO set path images and wall center to H_wall

            return true;
        } else {

            return false;
        }
    }

    public void pawnClick(View view){
        boolean moveMade = false;
        //while loop allows the user to keep making selections until a valid move is made.
        // turned off for testing
        //while(!moveMade){
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
        //}
    }

    public void hWallClick(View view) {
        boolean moveMade = false;
        //while loop allows the user to keep making selections until a valid move is made.
        // turned off for testing
        //while(!moveMade){
            switch (view.getId()) {

                //row 1
                case R.id.wall11:
                    if (setHorWallImage(R.id.wall11, new Point(1, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall21:
                    if (setHorWallImage(R.id.wall21, new Point(2, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall31:
                    if (setHorWallImage(R.id.wall31, new Point(3, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall41:
                    if (setHorWallImage(R.id.wall41, new Point(4, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall51:
                    if (setHorWallImage(R.id.wall51, new Point(5, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall61:
                    if (setHorWallImage(R.id.wall61, new Point(6, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall71:
                    if (setHorWallImage(R.id.wall71, new Point(7, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall81:
                    if (setHorWallImage(R.id.wall81, new Point(8, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 2
                case R.id.wall12:
                    if (setHorWallImage(R.id.wall12, new Point(1, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall22:
                    if (setHorWallImage(R.id.wall22, new Point(2, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall32:
                    if (setHorWallImage(R.id.wall32, new Point(3, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall42:
                    if (setHorWallImage(R.id.wall42, new Point(4, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall52:
                    if (setHorWallImage(R.id.wall52, new Point(5, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall62:
                    if (setHorWallImage(R.id.wall62, new Point(6, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall72:
                    if (setHorWallImage(R.id.wall72, new Point(7, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall82:
                    if (setHorWallImage(R.id.wall82, new Point(8, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 3
                case R.id.wall13:
                    if (setHorWallImage(R.id.wall13, new Point(1, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall23:
                    if (setHorWallImage(R.id.wall23, new Point(2, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall33:
                    if (setHorWallImage(R.id.wall33, new Point(3, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall43:
                    if (setHorWallImage(R.id.wall43, new Point(4, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall53:
                    if (setHorWallImage(R.id.wall53, new Point(5, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall63:
                    if (setHorWallImage(R.id.wall63, new Point(6, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall73:
                    if (setHorWallImage(R.id.wall73, new Point(7, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall83:
                    if (setHorWallImage(R.id.wall83, new Point(8, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 4
                case R.id.wall14:
                    if (setHorWallImage(R.id.wall14, new Point(1, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall24:
                    if (setHorWallImage(R.id.wall24, new Point(2, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall34:
                    if (setHorWallImage(R.id.wall34, new Point(3, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall44:
                    if (setHorWallImage(R.id.wall44, new Point(4, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall54:
                    if (setHorWallImage(R.id.wall54, new Point(5, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall64:
                    if (setHorWallImage(R.id.wall64, new Point(6, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall74:
                    if (setHorWallImage(R.id.wall74, new Point(7, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall84:
                    if (setHorWallImage(R.id.wall84, new Point(8, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 5
                case R.id.wall15:
                    if (setHorWallImage(R.id.wall15, new Point(1, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall25:
                    if (setHorWallImage(R.id.wall25, new Point(2, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall35:
                    if (setHorWallImage(R.id.wall35, new Point(3, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall45:
                    if (setHorWallImage(R.id.wall45, new Point(4, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall55:
                    if (setHorWallImage(R.id.wall55, new Point(5, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall65:
                    if (setHorWallImage(R.id.wall65, new Point(6, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall75:
                    if (setHorWallImage(R.id.wall75, new Point(7, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall85:
                    if (setHorWallImage(R.id.wall85, new Point(8, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 6
                case R.id.wall16:
                    if (setHorWallImage(R.id.wall16, new Point(1, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall26:
                    if (setHorWallImage(R.id.wall26, new Point(2, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall36:
                    if (setHorWallImage(R.id.wall36, new Point(3, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall46:
                    if (setHorWallImage(R.id.wall46, new Point(4, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall56:
                    if (setHorWallImage(R.id.wall56, new Point(5, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall66:
                    if (setHorWallImage(R.id.wall66, new Point(6, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall76:
                    if (setHorWallImage(R.id.wall76, new Point(7, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall86:
                    if (setHorWallImage(R.id.wall86, new Point(8, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 7
                case R.id.wall17:
                    if (setHorWallImage(R.id.wall17, new Point(1, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall27:
                    if (setHorWallImage(R.id.wall27, new Point(2, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall37:
                    if (setHorWallImage(R.id.wall37, new Point(3, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall47:
                    if (setHorWallImage(R.id.wall47, new Point(4, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall57:
                    if (setHorWallImage(R.id.wall57, new Point(5, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall67:
                    if (setHorWallImage(R.id.wall67, new Point(6, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall77:
                    if (setHorWallImage(R.id.wall77, new Point(7, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall87:
                    if (setHorWallImage(R.id.wall87, new Point(8, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 8
                case R.id.wall18:
                    if (setHorWallImage(R.id.wall18, new Point(1, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall28:
                    if (setHorWallImage(R.id.wall28, new Point(2, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall38:
                    if (setHorWallImage(R.id.wall38, new Point(3, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall48:
                    if (setHorWallImage(R.id.wall48, new Point(4, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall58:
                    if (setHorWallImage(R.id.wall58, new Point(5, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall68:
                    if (setHorWallImage(R.id.wall68, new Point(6, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall78:
                    if (setHorWallImage(R.id.wall78, new Point(7, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall88:
                    if (setHorWallImage(R.id.wall88, new Point(8, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
            }
        //}
    }

    public void vWallClick(View view) {
        boolean moveMade = false;
        //while loop allows the user to keep making selections until a valid move is made.
        // turned off for testing
        //while(!moveMade){
            switch (view.getId()) {

                //row 1
                case R.id.wall11:
                    if (setVerWallImage(R.id.wall11, new Point(1, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall21:
                    if (setVerWallImage(R.id.wall21, new Point(2, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall31:
                    if (setVerWallImage(R.id.wall31, new Point(3, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall41:
                    if (setVerWallImage(R.id.wall41, new Point(4, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall51:
                    if (setVerWallImage(R.id.wall51, new Point(5, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall61:
                    if (setVerWallImage(R.id.wall61, new Point(6, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall71:
                    if (setVerWallImage(R.id.wall71, new Point(7, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall81:
                    if (setVerWallImage(R.id.wall81, new Point(8, 1))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 2
                case R.id.wall12:
                    if (setVerWallImage(R.id.wall12, new Point(1, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall22:
                    if (setVerWallImage(R.id.wall22, new Point(2, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall32:
                    if (setVerWallImage(R.id.wall32, new Point(3, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall42:
                    if (setVerWallImage(R.id.wall42, new Point(4, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall52:
                    if (setVerWallImage(R.id.wall52, new Point(5, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall62:
                    if (setVerWallImage(R.id.wall62, new Point(6, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall72:
                    if (setVerWallImage(R.id.wall72, new Point(7, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall82:
                    if (setVerWallImage(R.id.wall82, new Point(8, 2))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 3
                case R.id.wall13:
                    if (setVerWallImage(R.id.wall13, new Point(1, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall23:
                    if (setVerWallImage(R.id.wall23, new Point(2, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall33:
                    if (setVerWallImage(R.id.wall33, new Point(3, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall43:
                    if (setVerWallImage(R.id.wall43, new Point(4, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall53:
                    if (setVerWallImage(R.id.wall53, new Point(5, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall63:
                    if (setVerWallImage(R.id.wall63, new Point(6, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall73:
                    if (setVerWallImage(R.id.wall73, new Point(7, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall83:
                    if (setVerWallImage(R.id.wall83, new Point(8, 3))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 4
                case R.id.wall14:
                    if (setVerWallImage(R.id.wall14, new Point(1, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall24:
                    if (setVerWallImage(R.id.wall24, new Point(2, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall34:
                    if (setVerWallImage(R.id.wall34, new Point(3, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall44:
                    if (setVerWallImage(R.id.wall44, new Point(4, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall54:
                    if (setVerWallImage(R.id.wall54, new Point(5, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall64:
                    if (setVerWallImage(R.id.wall64, new Point(6, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall74:
                    if (setVerWallImage(R.id.wall74, new Point(7, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall84:
                    if (setVerWallImage(R.id.wall84, new Point(8, 4))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 5
                case R.id.wall15:
                    if (setVerWallImage(R.id.wall15, new Point(1, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall25:
                    if (setVerWallImage(R.id.wall25, new Point(2, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall35:
                    if (setVerWallImage(R.id.wall35, new Point(3, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall45:
                    if (setVerWallImage(R.id.wall45, new Point(4, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall55:
                    if (setVerWallImage(R.id.wall55, new Point(5, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall65:
                    if (setVerWallImage(R.id.wall65, new Point(6, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall75:
                    if (setVerWallImage(R.id.wall75, new Point(7, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall85:
                    if (setVerWallImage(R.id.wall85, new Point(8, 5))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 6
                case R.id.wall16:
                    if (setVerWallImage(R.id.wall16, new Point(1, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall26:
                    if (setVerWallImage(R.id.wall26, new Point(2, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall36:
                    if (setVerWallImage(R.id.wall36, new Point(3, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall46:
                    if (setVerWallImage(R.id.wall46, new Point(4, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall56:
                    if (setVerWallImage(R.id.wall56, new Point(5, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall66:
                    if (setVerWallImage(R.id.wall66, new Point(6, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall76:
                    if (setVerWallImage(R.id.wall76, new Point(7, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall86:
                    if (setVerWallImage(R.id.wall86, new Point(8, 6))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 7
                case R.id.wall17:
                    if (setVerWallImage(R.id.wall17, new Point(1, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall27:
                    if (setVerWallImage(R.id.wall27, new Point(2, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall37:
                    if (setVerWallImage(R.id.wall37, new Point(3, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall47:
                    if (setVerWallImage(R.id.wall47, new Point(4, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall57:
                    if (setVerWallImage(R.id.wall57, new Point(5, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall67:
                    if (setVerWallImage(R.id.wall67, new Point(6, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall77:
                    if (setVerWallImage(R.id.wall77, new Point(7, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall87:
                    if (setVerWallImage(R.id.wall87, new Point(8, 7))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;

                //row 8
                case R.id.wall18:
                    if (setVerWallImage(R.id.wall18, new Point(1, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall28:
                    if (setVerWallImage(R.id.wall28, new Point(2, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall38:
                    if (setVerWallImage(R.id.wall38, new Point(3, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall48:
                    if (setVerWallImage(R.id.wall48, new Point(4, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall58:
                    if (setVerWallImage(R.id.wall58, new Point(5, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall68:
                    if (setVerWallImage(R.id.wall68, new Point(6, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall78:
                    if (setVerWallImage(R.id.wall78, new Point(7, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
                case R.id.wall88:
                    if (setVerWallImage(R.id.wall88, new Point(8, 8))) {
                        moveMade = true;
                    } else {
                        Toast.makeText(GameActivity.this, "Invalid Wall Placement", Toast.LENGTH_SHORT).show();
                        moveMade = false;
                    }
                    break;
            }
        //}
    }

}