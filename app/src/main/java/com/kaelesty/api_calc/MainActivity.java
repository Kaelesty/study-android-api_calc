package com.kaelesty.api_calc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button evalButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map<Integer, String> dict = new HashMap<Integer, String>();
        dict.put(R.id.num_0, "0");
        dict.put(R.id.num_1, "1");
        dict.put(R.id.num_2, "2");
        dict.put(R.id.num_3, "3");
        dict.put(R.id.num_4, "4");
        dict.put(R.id.num_5, "5");
        dict.put(R.id.num_6, "6");
        dict.put(R.id.num_7, "7");
        dict.put(R.id.num_8, "8");
        dict.put(R.id.num_9, "9");
        dict.put(R.id.br_left, "(");
        dict.put(R.id.br_right, ")");
        dict.put(R.id.btn_div, "/");
        dict.put(R.id.btn_minus, "-");
        dict.put(R.id.btn_mult, "*");
        dict.put(R.id.btn_plus, "+");
        dict.put(R.id.btn_dot, ".");

        textView = findViewById(R.id.textView);
        findViewById(R.id.btn_eval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    evalButtonPressed();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
            }
        });

        findViewById(R.id.btn_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldText = textView.getText().toString();
                oldText = oldText.substring(0, oldText.length() - 1);
                textView.setText(oldText);
            }
        });

        Button button;
        for (int id: dict.keySet()) {
            button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textView.append(dict.get(id));
                }
            });
        }
    }

    protected void evalButtonPressed() throws IOException {
        String expression = textView.getText().toString();
        String result = apiEvaluate(expression);
        textView.setText(result);
    }

    protected String localEvaluate(String exp) {
        Expression expression = new ExpressionBuilder(exp).build();
        double result = expression.evaluate();
        return Double.toString(result);
    }

    protected String apiEvaluate(String exp) throws IOException {
        String result = "";
        URL api = new URL("https://kaelesty-api-calc.loca.lt/evaluate/" + exp);
        HttpURLConnection urlConnection = (HttpURLConnection) api.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            result = String.valueOf(in.read());
        }
        finally {
            urlConnection.disconnect();
        }
        return result;
    }
}