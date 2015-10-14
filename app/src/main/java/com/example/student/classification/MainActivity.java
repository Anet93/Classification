package com.example.student.classification;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawingView drawView;
    private Button cleanButton;
    private Button addButton;
    private Button classifyButton;
    private Button trainButton;
    private EditText digitEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        drawView = (DrawingView) findViewById(R.id.drawing);
        cleanButton = (Button) findViewById(R.id.CleanButton);
        cleanButton.setOnClickListener(this);
        addButton = (Button) findViewById(R.id.AddButton);
        addButton.setOnClickListener(this);
        classifyButton = (Button) findViewById(R.id.ClassifyButton);
        classifyButton.setOnClickListener(this);
        trainButton = (Button) findViewById(R.id.TrainButton);
        trainButton.setOnClickListener(this);
        digitEditText = (EditText) findViewById(R.id.DigitEditText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CleanButton:
                drawView.cleanDrawing();
                break;
            case R.id.AddButton:
                if(drawView.getPointList().equals(null)){
                    Snackbar.make(view, "braku rysunku.", Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.ClassifyButton:
                break;
            default:
                break;
        }
    }
}

