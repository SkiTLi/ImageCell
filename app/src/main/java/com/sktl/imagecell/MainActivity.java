package com.sktl.imagecell;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

public class MainActivity extends Activity implements View.OnTouchListener {

    float x;
    float y;

    String[] data = {"size one", "size two", "size three", "size four", "size five", "size six"};

    private float xPress = 0;
    private float yPress = 0;

    float widthImage;
    float heightImage;

    private Button mButton;
    private TextView mTextMessage;
    private TextView mTextVersion;
    private ImageView mImage;
    private SktlGridView mGrid;
    private Spinner mSpinner;

    private int SKTL_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        mTextVersion = (TextView) findViewById(R.id.version);
        mTextVersion.setText(BuildConfig.VERSION_NAME);

        //загружаем картинку из галереи по нажатию на кнопку
        mButton = (Button) findViewById(R.id.load_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select Picture"),
                        SKTL_REQUEST_CODE);
            }
        });


        mGrid = (SktlGridView) findViewById(R.id.GridSktl_View);
        mGrid.setOnTouchListener(this);
        mTextMessage = (TextView) findViewById(R.id.message);


        mImage = (ImageView) findViewById(R.id.image);
        mImage.setImageResource(R.drawable.fourh);
        mTextMessage.bringToFront();
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);
        // заголовок
//        mSpinner.setPrompt("Title");
        // выделяем элемент
        mSpinner.setSelection(0);

//        spinner.setVisibility(View.INVISIBLE);
        // устанавливаем обработчик нажатия
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                mGrid.setGridSpacing(mSpinner.getSelectedItemPosition() + 1);

                mGrid.setGridWidth(mImage.getWidth());
                mGrid.setGridHeight(mImage.getHeight());
                mGrid.invalidate();//перерисовывает view
//                widthImage = mImage.getWidth();
//                heightImage = mImage.getHeight();
//                mTextMessage.setText("WI=" + widthImage
//                        + ", HI=" + heightImage
//                        + ", WG=" + mGrid.getGridWidth()
//                        + ", HG=" + mGrid.getGridHeight());
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


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

                //для моментального отображения
                mTextMessage.setText(getNumberOfCell(xPress, yPress));

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


    public String getNumberOfCell(float x, float y) {
        int n = 0;
        String result = "click on the cell";
        List<Integer> xGrid = mGrid.getArrayColumns();
        List<Integer> yGrid = mGrid.getArrayRows();
        Log.d("eee", "xGrid=" + xGrid + ", yGrid=" + yGrid);
        for (Integer elX : xGrid) {
            if (x < elX) {
                n = elX / mGrid.getGridSpacing();
                Log.d("eee", "n=elX/mGrid.getGridSpacing()=" + n);
                result = "cell number " + valueOf(n);
                break;
            }
        }
        for (Integer elY : yGrid) {
            if (y < elY) {
                n = n + ((int) (elY / mGrid.getGridSpacing())) * (xGrid.size() - 1) - (xGrid.size() - 1);
                Log.d("eee", "n=n+elY/mGrid.getGridSpacing()=" + n);
                result = "cell number " + valueOf(n);
                break;
            }
        }
        if (x > mGrid.getLastVerticalLine() || y > (float) mGrid.getLastHorizontalLine()) {
            result = "you missed!";
        }
        if (x == 0 && y == 0) {
            n = 0;
            result = "cell number " + valueOf(n);
        }
        return result;
    }


}
