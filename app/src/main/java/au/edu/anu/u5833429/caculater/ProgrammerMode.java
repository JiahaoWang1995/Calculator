package au.edu.anu.u5833429.caculater;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 2016/5/8.
 */
public class ProgrammerMode extends Activity{
    private TextView tv;
    private EditText binary;
    private EditText octal;
    private EditText hex;
    private Button btndecimal;
    private Button btnbinary;
    private Button btnoctal;
    private Button btnhex;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.programmer_mode);

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);

        tv = (TextView) findViewById(R.id.tv_decimal);
        binary = (EditText) findViewById(R.id.tv_binary);
        octal = (EditText) findViewById(R.id.tv_octal);
        hex = (EditText) findViewById(R.id.tv_hex);
        btndecimal = (Button) findViewById(R.id.btn_decimal);
        btnbinary = (Button) findViewById(R.id.btn_binary);
        btnoctal = (Button) findViewById(R.id.btn_octal);
        btnhex = (Button) findViewById(R.id.btn_hex);


        btndecimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = (String) tv.getText();
                myClip = ClipData.newPlainText("text",text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(ProgrammerMode.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });


        btnbinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(binary.getText());
                try{
                    String output = Integer.valueOf(input,2).toString();
                    tv.setText(output);
                }catch (Exception e){
                    Toast.makeText(ProgrammerMode.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                    binary.setText(null);
                }
            }
        });

        btnoctal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(octal.getText());
                try{
                    String output = Integer.valueOf(input,8).toString();
                    tv.setText(output);
                }catch (Exception e){
                    Toast.makeText(ProgrammerMode.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                    octal.setText(null);
                }
            }
        });

        btnhex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = String.valueOf(hex.getText());
                try{
                    String output = Integer.valueOf(input,16).toString();
                    tv.setText(output);
                }catch (Exception e){
                    Toast.makeText(ProgrammerMode.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                    hex.setText(null);
                }
            }
        });

    }
}
