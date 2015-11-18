package com.example.student.classification;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawingView drawView;
    private Button cleanButton;
    private Button addButton;
    private Button classifyButton;
    private Button trainButton;
    private EditText digitEditText;
    private TextView resultText;
    private String dataTrainName;
    private NaiveBayes nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nb = new NaiveBayes();
        init();
    }

    public void init() {
        dataTrainName = "trainData.txt";
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
        resultText = (TextView) findViewById(R.id.ResultText);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.CleanButton:
                drawView.cleanDrawing();
                resultText.setText("");
                break;
            case R.id.AddButton:
                add();
                break;
            case R.id.ClassifyButton:
                classify();
                break;
            case R.id.TrainButton:
                train();
                break;
            default:
                break;
        }
    }


    public void add() {
        if (digitEditText.getText().toString().equals("")) {
            showAlertDialog("Bład", "Nie podano cyfry do dodania");
        } else if (drawView.getPoints().size() == 0) {
            showAlertDialog("Bład", "Obrazek nie został narysowany");
        } else {
            Features features = new Features(drawView.getPoints(), drawView.getStrokes());
            List<Double> featureValues = features.calculateFeatures();
            drawView.cleanDrawing();
            writeToFile(featureValues);

        }
    }

    public void train() {
        try {
            File file = this.getFileStreamPath(dataTrainName);
            if (file.exists()) {
                InputStream inputStream = openFileInput(dataTrainName);
                DataCollection data = new DataCollection();
                data.build(inputStream);
                if (data.numberOfClasses() > 1) {
                    nb.buildClassifier(data);
                    showAlertDialog("Sukces", "Model został poprawnie wyuczony");
                } else {
                    showAlertDialog("Bład", "Narysuj co najmniej dwie różne cyfry aby wyuczyć model");
                }
                inputStream.close();
            } else {
                showAlertDialog("Bład", "Brak pliku z danymi uczącymi");
            }
        } catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void classify(){
        if (nb.isTrained()) {
            Features features = new Features(drawView.getPoints(), drawView.getStrokes());
            double predicted = nb.classifyInstance(features);
            resultText.setText(Integer.toString((int) predicted));
        } else {
            showAlertDialog("Bład", "Klasyfikator nie został wyuczony");
        }
    }


    public void writeToFile(List<Double> featureValues) {
        try {
            FileOutputStream fileout = openFileOutput(dataTrainName, MODE_PRIVATE | MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            for (int i = 0; i < featureValues.size(); i++) {
                outputWriter.write(featureValues.get(i).toString());
                outputWriter.write(",");
                Log.d("writeToFile ", "featureValues " + featureValues.get(i).toString());
            }
            outputWriter.write(digitEditText.getText().toString());
            Log.d("uczenie ", "cyfra " + digitEditText.getText().toString());
            outputWriter.write("\n");
            outputWriter.close();
        } catch (Exception e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
    }

    public void showAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message).setCancelable(true).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clean_data:
                clean();
                return true;
            case R.id.exit:
                super.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clean() {
        File file = this.getFileStreamPath(dataTrainName);
        if (file.exists()) {
            file.delete();
        }
    }

}



