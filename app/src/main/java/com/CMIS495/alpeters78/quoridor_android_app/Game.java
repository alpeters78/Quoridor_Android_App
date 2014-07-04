package com.CMIS495.alpeters78.quoridor_android_app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageView;



public class Game extends Activity {

    private static final String TAG = Game.class.getSimpleName();



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


            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //Log events so we can see what is going on.
    @Override
    protected void onDestroy() {
        Log.d(TAG, "Destroying...");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "Stopping...");
        super.onStop();
    }

}
