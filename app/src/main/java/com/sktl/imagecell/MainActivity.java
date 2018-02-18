package com.sktl.imagecell;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    float x;
    float y;

    String[] data = {"size one", "size two", "size three", "size four", "size five", "size six"};

    private float xPress = 0;
    private float yPress = 0;

    float widthImage;
    float heightImage;

    private TextView mTextMessage;
    private ImageView mImage;
    private SktlGridView mGrid;
    private Spinner spinner;

    private int SKTL_REQUEST_CODE = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    spinner.setVisibility(View.INVISIBLE);
                    mTextMessage.setText(R.string.title_home);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "select Picture"),
                            SKTL_REQUEST_CODE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Log.d("eee", "mGrid.getGridSpacing()=" + mGrid.getGridSpacing());
                    mGrid.setGridWidth(mImage.getWidth());
                    mGrid.setGridHeight(mImage.getHeight());
                    mGrid.invalidate();//перерисовывает view
                    Log.d("eee", "mGrid.getGridSpacing()=" + mGrid.getGridSpacing());
                    //getWidth/getHeight нельзя вызывать сразу в onCreate (покажет 0)
                    widthImage = mImage.getWidth();
                    heightImage = mImage.getHeight();
//                    mTextMessage.setText("WidthImage=" + widthImage
//                            + ", HeightImage=" + heightImage
//                            + "\n"
//                            + "WidthGrid=" + mGrid.getGridWidth()
//                            + ", GridHeight=" + mGrid.getGridHeight());
                    spinner.setVisibility(View.VISIBLE);
                    spinner.bringToFront();
                    mGrid.setGridSpacing(spinner.getSelectedItemPosition() + 1);
                    return true;
                case R.id.navigation_notifications:
                    spinner.setVisibility(View.INVISIBLE);
                    mTextMessage.setText(R.string.title_notifications);
                    mTextMessage.setText(String.valueOf(getNumberOfCell(xPress, yPress)));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SKTL_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                mImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        mImage = (ImageView) findViewById(R.id.image);
        mImage.setImageResource(R.drawable.fourh);
        mTextMessage.bringToFront();
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(0);

        spinner.setVisibility(View.INVISIBLE);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                mGrid.setGridSpacing(spinner.getSelectedItemPosition() + 1);
                mGrid.invalidate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        mGrid.setGridWidth(mImage.getWidth());
        mGrid.setGridHeight(mImage.getHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие

                if (x <= mGrid.getLastVerticalLine() & y <= mGrid.getLastHorizontalLine()) {
                    xPress = x;
                    yPress = y;
                } else {
                    xPress = x;
                    yPress = y;
                }
                break;
            case MotionEvent.ACTION_MOVE: // движение
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                // по сути здесь пишем ACTION_UP
                break;
        }
        return true;
    }


    public int getNumberOfCell(float x, float y) {
        int n = 0;
        List<Integer> xGrid = mGrid.getArrayColumns();
        List<Integer> yGrid = mGrid.getArrayRows();
        Log.d("eee", "xGrid=" + xGrid + ", yGrid=" + yGrid);
        for (Integer elX : xGrid) {
            if (x < elX) {
                n = elX / mGrid.getGridSpacing();
                Log.d("eee", "n=elX/mGrid.getGridSpacing()=" + n);
                break;
            }
        }
        for (Integer elY : yGrid) {
            if (y < elY) {
                n = n + ((int) (elY / mGrid.getGridSpacing())) * (xGrid.size() - 1) - (xGrid.size() - 1);
                Log.d("eee", "n=n+elY/mGrid.getGridSpacing()=" + n);
                break;
            }
        }
        if (x > mGrid.getLastVerticalLine() || y > (float) mGrid.getLastHorizontalLine()) {
            n = 8888;
        }
        if (x == 0 && y == 0) {
            n = 0;
        }
        return n;
    }


}
