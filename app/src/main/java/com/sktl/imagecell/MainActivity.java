package com.sktl.imagecell;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    float x;
    float y;
    String sDown;
    String sMove;
    String sUp;

    float weightImage;
    float heightImage;

    String allActions;

    private TextView mTextMessage;
    private ImageView mImage;
    private SktlGridView mGrid;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    //getWidth/getHeight нельзя вызывать сразу в onCreate (покажет 0)
                    weightImage = mImage.getWidth();
                    heightImage = mImage.getHeight();
                    mTextMessage.setText("weightImage=" + weightImage + ", heightImage=" + heightImage);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    mTextMessage.setText(allActions);
                    return true;
            }
            return false;
        }
    };

    public MainActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mGrid = (SktlGridView) findViewById(R.id.GridSktl_View);

        mGrid.setOnTouchListener(this);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mGrid = (SktlGridView) findViewById(R.id.GridSktl_View);

        mImage = (ImageView) findViewById(R.id.image);
        mImage.setImageResource(R.drawable.venom);

        mTextMessage.bringToFront();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: " + x + "," + y;
                sMove = "";
                sUp = "";
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: " + x + "," + y;
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: " + x + "," + y;
                break;
        }
        //  mTextMessage.setText(sDown + "\n" + sMove + "\n" + sUp);
        allActions = (sDown + "\n" + sMove + "\n" + sUp);
        return true;
    }
}
