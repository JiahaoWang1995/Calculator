package au.edu.anu.u5833429.caculater;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {
    private long lastClickTime = 0;//Return clock timer
    private Button[] btn = new Button[10];    //Button 0-9
    private EditText input;                   //Monitor
    private TextView mem;                     //To show the memory of previous calculation
    private TextView _drg;                    //A sign of degree or radian
    private TextView tip;                     //Tip which is used to show the usage or alert
    private Button
            div, mul, sub, add, equal,            // / * - + =
            sin, cos, tan, log, ln,               //Functions
            sqrt, square, factorial, del,  //radical sign, root, square, factorial, delete
            left, right, dot, drg,          //（     ）  .  button to shift between degree and radian
            c, up, down, bin, oct, hex;                                // clear, last memory, next memory

    public String str_old;           //original expression
    public String str_new;           //changed expression
    private ArrayList<String> answer_data; //to store all the answers calculated
    private ArrayList<String> mem_data; //to store all the original expressions
    private int where_i_am; //the number of memory showing
    public boolean vbegin = true;    //Input mode, true to enter again, false to continue entering
    public boolean drg_flag = true;  //Trigonomeric function mode，true to be degree，false to be radian
    public double pi=4*Math.atan(1); //3.14
    public boolean tip_lock = true;  //true to indicate continue entering enabled, false to indicate wrong entry and entering to be locked
    public boolean equals_flag = true;  //true to indicate entry after pressing =, false to indicate entry before pressing

    private ClipboardManager myClipboard;
    private ClipData myClip;

    private WebView webView;


    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);
        setTitle("Calculator");

        myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
        input = (EditText)findViewById(R.id.input);
        mem = (TextView)findViewById(R.id.mem);
        tip = (TextView)findViewById(R.id.tip);
        _drg = (TextView)findViewById(R.id._drg);
        btn[0] = (Button)findViewById(R.id.zero);
        btn[1] = (Button)findViewById(R.id.one);
        btn[2] = (Button)findViewById(R.id.two);
        btn[3] = (Button)findViewById(R.id.three);
        btn[4] = (Button)findViewById(R.id.four);
        btn[5] = (Button)findViewById(R.id.five);
        btn[6] = (Button)findViewById(R.id.six);
        btn[7] = (Button)findViewById(R.id.seven);
        btn[8] = (Button)findViewById(R.id.eight);
        btn[9] = (Button)findViewById(R.id.nine);
        div = (Button)findViewById(R.id.divide);
        mul = (Button)findViewById(R.id.mul);
        sub = (Button)findViewById(R.id.sub);
        add = (Button)findViewById(R.id.add);
        equal = (Button)findViewById(R.id.equal);
        sin = (Button)findViewById(R.id.sin);
        cos = (Button)findViewById(R.id.cos);
        tan = (Button)findViewById(R.id.tan);
        log = (Button)findViewById(R.id.log);
        ln = (Button)findViewById(R.id.ln);
        sqrt = (Button)findViewById(R.id.sqrt);
        square = (Button)findViewById(R.id.square);
        factorial = (Button)findViewById(R.id.factorial);
        del = (Button)findViewById(R.id.del);
        left = (Button)findViewById(R.id.left);
        right = (Button)findViewById(R.id.right);
        dot = (Button)findViewById(R.id.dot);
        drg = (Button)findViewById(R.id.drg);
        c = (Button)findViewById(R.id.c);
        up = (Button) findViewById(R.id.up);
        down = (Button)findViewById(R.id.down);
        bin = (Button) findViewById(R.id.bin);
        oct = (Button) findViewById(R.id.oct);
        hex = (Button) findViewById(R.id.hex);

        answer_data = new ArrayList<String>();
        mem_data = new ArrayList<String>();
        where_i_am = answer_data.size();


        for(int i = 0; i < 10; ++i) {
            btn[i].setOnClickListener(actionPerformed);
        }
        div.setOnClickListener(actionPerformed);
        mul.setOnClickListener(actionPerformed);
        sub.setOnClickListener(actionPerformed);
        add.setOnClickListener(actionPerformed);
        equal.setOnClickListener(actionPerformed);
        sin.setOnClickListener(actionPerformed);
        cos.setOnClickListener(actionPerformed);
        tan.setOnClickListener(actionPerformed);
        log.setOnClickListener(actionPerformed);
        ln.setOnClickListener(actionPerformed);
        sqrt.setOnClickListener(actionPerformed);
        square.setOnClickListener(actionPerformed);
        factorial.setOnClickListener(actionPerformed);
        del.setOnClickListener(actionPerformed);
        left.setOnClickListener(actionPerformed);
        right.setOnClickListener(actionPerformed);
        dot.setOnClickListener(actionPerformed);
        drg.setOnClickListener(actionPerformed);
        c.setOnClickListener(actionPerformed);
        up.setOnClickListener(actionPerformed);
        down.setOnClickListener(actionPerformed);
        bin.setOnClickListener(actionPerformed);
        oct.setOnClickListener(actionPerformed);
        hex.setOnClickListener(actionPerformed);
    }

    /*
     * To get the command of buttons
     */
    String[] Tipcommand = new String[500];  //Command buffer, to check if the entry is right
    int tip_i = 0;         //the pointer of command buffer
    private View.OnClickListener actionPerformed = new View.OnClickListener() {
        public void onClick(View v) {
            String
                    command = ((Button)v).getText().toString(),       //To get the command of buttons
                    str = input.getText().toString();                 //String showing on the monitor

            if(equals_flag == false && "0123456789.()sincostanlnlogn!+-*/√^".indexOf(command) != -1) {
                if(right(input.getText().toString())) {
                    if("+-*/√^)".indexOf(command) != -1) {
                        for(int i =0 ; i < input.getText().toString().length(); i++) {
                            Tipcommand[tip_i] = String.valueOf(input.getText().toString().charAt(i));
                            tip_i++;
                        }
                        vbegin = false;
                    }
                } else {
                    input.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip_lock = true;
                    tip.setText("Welcome");
                }

                equals_flag = true;
            }
            if(tip_i > 0)
                TipChecker(Tipcommand[tip_i-1] , command);
            else if(tip_i == 0) {
                TipChecker("#" , command);
            }
            if("0123456789.()sincostanlnlogn!+-*/√^".indexOf(command) != -1 && tip_lock) {
                Tipcommand[tip_i] = command;
                tip_i++;
            }

            if("0123456789.()sincostanlnlogn!+-*/√^".indexOf(command) != -1
                    && tip_lock) { //25 buttons altogether
                print(command);
            } else if(command.compareTo("DRG") == 0 && tip_lock) {
                if(drg_flag == true) {
                    drg_flag = false;
                    _drg.setText("   RAD");
                } else {
                    drg_flag = true;
                    _drg.setText("   DEG");
                }
            } else if(command.compareTo("DEL") == 0 && equals_flag) {
                if(TTO(str) == 3) {
                    if(str.length() > 3)
                        input.setText(str.substring(0, str.length() - 3));
                    else if(str.length() == 3) {
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("Welcome");
                    }
                } else if(TTO(str) == 2) {
                    if(str.length() > 2)
                        input.setText(str.substring(0, str.length() - 2));
                    else if(str.length() == 2) {
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("Welcome");
                    }
                } else if(TTO(str) == 1) {
                    if(right(str)) {
                        if(str.length() > 1)
                            input.setText(str.substring(0, str.length() - 1));
                        else if(str.length() == 1) {
                            input.setText("0");
                            vbegin = true;
                            tip_i = 0;
                            tip.setText("Welcome");
                        }
                    } else {
                        input.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip.setText("Welcome");
                    }
                }
                if(input.getText().toString().compareTo("-") == 0 || equals_flag == false) {
                    input.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip.setText("Welcome");
                }
                tip_lock = true;
                if(tip_i > 0)
                    tip_i--;
            } else if(command.compareTo("DEL") == 0 && equals_flag ==false) {
                input.setText("0");
                vbegin = true;
                tip_i = 0;
                tip_lock = true;
                tip.setText("Welcome");
            } else if (command.compareTo("△") == 0){
                if (where_i_am > 1){
                    input.setText(answer_data.get(where_i_am-2));
                    mem.setText(mem_data.get(where_i_am-2));
                    where_i_am --;
                }else{
                    Toast.makeText(getApplicationContext(), "This is the last record", Toast.LENGTH_SHORT).show();
                }
            } else if (command.compareTo("▽") == 0){
                if (where_i_am != answer_data.size()){
                    input.setText(answer_data.get(where_i_am));
                    mem.setText(mem_data.get(where_i_am));
                    where_i_am ++;
                }else{
                    Toast.makeText(getApplicationContext(), "This is the latest record", Toast.LENGTH_SHORT).show();
                }
            }else if (command.compareTo("BIN") == 0 && tip_lock){
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Please input binary number: ")
                        .setView(editText)
                        .setPositiveButton("Done",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,2).toString();
                                            Tipcommand[tip_i] = output;
                                            tip_i++;
                                            print(output);
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 1 & 0 is available in Binary mode.");
                                        }
                                    }
                                })
                        .setNegativeButton("Copy",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,2).toString();
                                            myClip = ClipData.newPlainText("text",output);
                                            myClipboard.setPrimaryClip(myClip);
                                            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 1 & 0 is available in Binary mode.");
                                        }
                                    }
                                })
                        .show();
            } else if (command.compareTo("OCT") == 0 && tip_lock){
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Please input octal number: ")
                        .setView(editText)
                        .setPositiveButton("Done",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,8).toString();
                                            Tipcommand[tip_i] = output;
                                            tip_i++;
                                            print(output);
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 0,1,2,3,4,5,6,7 is available in Octal mode.");
                                        }
                                    }
                                })
                        .setNegativeButton("Copy",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,8).toString();
                                            myClip = ClipData.newPlainText("text",output);
                                            myClipboard.setPrimaryClip(myClip);
                                            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 0,1,2,3,4,5,6,7 is available in Octal mode.");
                                        }
                                    }
                                })
                        .show();
            } else if (command.compareTo("HEX") == 0 && tip_lock){
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Please input hexadecimal number: ")
                        .setView(editText)
                        .setPositiveButton("Done",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,16).toString();
                                            Tipcommand[tip_i] = output;
                                            tip_i++;
                                            print(output);
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 0-9 & a-f(A-F) is available in Hexadecimal mode.");
                                        }
                                    }
                                })
                        .setNegativeButton("Copy",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String input = String.valueOf(editText.getText());
                                        try{
                                            String output = Integer.valueOf(input,16).toString();
                                            myClip = ClipData.newPlainText("text",output);
                                            myClipboard.setPrimaryClip(myClip);
                                            Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                                        }catch (Exception e){
                                            Toast.makeText(MainActivity.this, "Unexpected input", Toast.LENGTH_SHORT).show();
                                            tip.setText("Only 0-9 & a-f(A-F) is available in Hexadecimal mode.");
                                        }
                                    }
                                })
                        .show();
            }else if(command.compareTo("C") == 0) {
                input.setCursorVisible(false);
                input.setText("");
                mem.setText("0");
                vbegin = true;
                tip_i = 0;
                tip_lock = true;
                equals_flag = true;
                tip.setText("Welcome");
            } else if(command.compareTo("=") == 0 && tip_lock && right(str) && equals_flag) {
                str_old = str;//保存原来算式样子
                str = str.replaceAll("sin", "s");
                str = str.replaceAll("cos", "c");
                str = str.replaceAll("tan", "t");
                str = str.replaceAll("log", "g");
                str = str.replaceAll("ln", "l");
                str = str.replaceAll("n!", "!");
                str_new = str.replaceAll("-", "-1*");
                vbegin = true;
                tip_i = 0;
                tip_lock = false;
                equals_flag = false;
                new calc().process(str_new);
                where_i_am = answer_data.size();
            }
            tip_lock = true;
        }
    };


    /*
     * To output str to input
     * input.setText(str);entry after clearing
     * input.append(str);entry after the original string showing on the monitor
     */
    private void print(String str) {
        if (vbegin)
            input.setText(str);
        else
            input.append(str);
        vbegin = false;
    }

    /*
     * To check if str is right, return true or false
     * str which contains only 0123456789.()sincostanlnlogn!+-/*√^ is right, return true
     * the others return false
     */
    public boolean right(String str) {
        int i = 0;
        for(i = 0;i < str.length();i++) {
            if(str.charAt(i)!='0' && str.charAt(i)!='1' && str.charAt(i)!='2' &&
                    str.charAt(i)!='3' && str.charAt(i)!='4' && str.charAt(i)!='5' &&
                    str.charAt(i)!='6' && str.charAt(i)!='7' && str.charAt(i)!='8' &&
                    str.charAt(i)!='9' && str.charAt(i)!='.' && str.charAt(i)!='-' &&
                    str.charAt(i)!='+' && str.charAt(i)!='*' && str.charAt(i)!='/' &&
                    str.charAt(i)!='√' && str.charAt(i)!='^' && str.charAt(i)!='s' &&
                    str.charAt(i)!='i' && str.charAt(i)!='n' && str.charAt(i)!='c' &&
                    str.charAt(i)!='o' && str.charAt(i)!='t' && str.charAt(i)!='a' &&
                    str.charAt(i)!='l' && str.charAt(i)!='g' && str.charAt(i)!='(' &&
                    str.charAt(i)!=')' && str.charAt(i)!='!')
                break;
        }
        if(i == str.length()) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * To check the functions and return 3 or 2 or 1 indications deleting 3 or 2 or 1 char each time. Three+Two+One = TTO
     * 3: one of sin、cos、tan、log
     * 2: one of ln、n!
     * 1: the rest (Exception: illegal char. Should clear all)
     */
    private int TTO(String str) {
        if((str.charAt(str.length() - 1) == 'n' &&
                str.charAt(str.length() - 2) == 'i' &&
                str.charAt(str.length() - 3) == 's') ||
                (str.charAt(str.length() - 1) == 's' &&
                        str.charAt(str.length() - 2) == 'o' &&
                        str.charAt(str.length() - 3) == 'c') ||
                (str.charAt(str.length() - 1) == 'n' &&
                        str.charAt(str.length() - 2) == 'a' &&
                        str.charAt(str.length() - 3) == 't') ||
                (str.charAt(str.length() - 1) == 'g' &&
                        str.charAt(str.length() - 2) == 'o' &&
                        str.charAt(str.length() - 3) == 'l')) {
            return 3;
        } else if((str.charAt(str.length() - 1) == 'n' &&
                str.charAt(str.length() - 2) == 'l') ||
                (str.charAt(str.length() - 1) == '!' &&
                        str.charAt(str.length() - 2) == 'n')) {
            return 2;
        } else { return 1; }
    }

    /*
     * To check the grammar of str
     * To assist tip choosing, using with TipShow()
     *  #    character    legal character following
     *   1     （          number|（|-|.|function
     *   2      ）            operator|）|√ |^
     *   3      .           number|operator|）|√ |^
     *   4    number      .|number|operator|）|√ |^
     *   5   operator       number|（|.|function
     *   6     √ ^           （ |. | number
     *   7   function          number|（|.
     *
     * There can be nothing before and after the dot, indicating 0
     * The first digit of number can be 0
     */
    public void TipChecker(String tipcommand1,String tipcommand2) {
        int Tipcode1 = 0 , Tipcode2 = 0;//Tipcode1 indicates error type，Tipcode2 indicates noun explanation type
        int tiptype1 = 0 , tiptype2 = 0;//Indicates command type
        int bracket = 0;      //number of parenthesis
        if(tipcommand1.compareTo("#") == 0 && (tipcommand2.compareTo("/") == 0 ||
                tipcommand2.compareTo("*") == 0 || tipcommand2.compareTo("+") == 0 ||
                tipcommand2.compareTo(")") == 0 || tipcommand2.compareTo("√") == 0 ||
                tipcommand2.compareTo("^") == 0)) {
            Tipcode1 = -1;//cannot be the first digit
        }
        if(tipcommand1.compareTo("#") != 0) {
            if(tipcommand1.compareTo("(") == 0) {
                tiptype1 = 1;
            } else if(tipcommand1.compareTo(")") == 0) {
                tiptype1 = 2;
            } else if(tipcommand1.compareTo(".") == 0) {
                tiptype1 = 3;
            } else if("0123456789".indexOf(tipcommand1) != -1) {
                tiptype1 = 4;
            } else if("+-*/".indexOf(tipcommand1) != -1) {
                tiptype1 = 5;
            } else if("√^".indexOf(tipcommand1) != -1) {
                tiptype1 = 6;
            } else if("sincostanloglnn!".indexOf(tipcommand1) != -1) {
                tiptype1 = 7;
            }

            if(tipcommand2.compareTo("(") == 0) {
                tiptype2 = 1;
            } else if(tipcommand2.compareTo(")") == 0) {
                tiptype2 = 2;
            } else if(tipcommand2.compareTo(".") == 0) {
                tiptype2 = 3;
            } else if("0123456789".indexOf(tipcommand2) != -1) {
                tiptype2 = 4;
            } else if("+-*/".indexOf(tipcommand2) != -1) {
                tiptype2 = 5;
            } else if("√^".indexOf(tipcommand2) != -1) {
                tiptype2 = 6;
            } else if("sincostanloglnn!".indexOf(tipcommand2) != -1) {
                tiptype2 = 7;
            }

            switch(tiptype1) {
                case 1:
                    if(tiptype2 == 2 || (tiptype2 == 5 && tipcommand2.compareTo("-") != 0) ||
                            tiptype2 == 6)
                        Tipcode1 = 1;
                    break;
                case 2:
                    if(tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4 || tiptype2 == 7) Tipcode1 = 2;
                    break;
                case 3:
                    if(tiptype2 == 1 || tiptype2 == 7) Tipcode1 = 3;
                    if(tiptype2 == 3) Tipcode1 = 8;
                    break;
                case 4:
                    if(tiptype2 == 1 || tiptype2 == 7) Tipcode1 = 4;
                    break;
                case 5:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6) Tipcode1 = 5;
                    break;
                case 6:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7) Tipcode1 = 6;
                    break;
                case 7:
                    if(tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6 || tiptype2 == 7) Tipcode1 = 7;
                    break;
            }
        }

        if(Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {//check the repetition of dot
            int tip_point = 0;
            for(int i = 0;i < tip_i;i++) {
                if(Tipcommand[i].compareTo(".") == 0) {
                    tip_point++;
                }
                if(Tipcommand[i].compareTo("sin") == 0 || Tipcommand[i].compareTo("cos") == 0 ||
                        Tipcommand[i].compareTo("tan") == 0 || Tipcommand[i].compareTo("log") == 0 ||
                        Tipcommand[i].compareTo("ln") == 0 || Tipcommand[i].compareTo("n!") == 0 ||
                        Tipcommand[i].compareTo("√") == 0 || Tipcommand[i].compareTo("^") == 0 ||
                        Tipcommand[i].compareTo("/") == 0 || Tipcommand[i].compareTo("*") == 0 ||
                        Tipcommand[i].compareTo("-") == 0 || Tipcommand[i].compareTo("+") == 0 ||
                        Tipcommand[i].compareTo("(") == 0 || Tipcommand[i].compareTo(")") == 0 ) {
                    tip_point = 0;
                }
            }
            tip_point++;
            if(tip_point > 1) {
                Tipcode1 = 8;
            }
        }
        if(Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {
            int tip_right_bracket = 0;//number of right parenthesis
            for(int i = 0;i < tip_i;i++) {
                if(Tipcommand[i].compareTo("(") == 0) {
                    tip_right_bracket++;
                }
                if(Tipcommand[i].compareTo(")") == 0) {
                    tip_right_bracket--;
                }
            }
            if(tip_right_bracket == 0) {
                Tipcode1 = 10;
            }
        }
        if(Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {
            int tip_bracket = 0; //number of left parenthesis
            for(int i = 0;i < tip_i;i++) {
                if(Tipcommand[i].compareTo("(") == 0) {
                    tip_bracket++;
                }
                if(Tipcommand[i].compareTo(")") == 0) {
                    tip_bracket--;
                }
            }
            if(tip_bracket > 0) {
                Tipcode1 = 9;
                bracket = tip_bracket;
            } else if(tip_bracket == 0) {
                if("√^sincostanloglnn!".indexOf(tipcommand1) != -1) {
                    Tipcode1 = 6;
                }
                if("+-*/".indexOf(tipcommand1) != -1) {
                    Tipcode1 = 5;
                }
            }
        }

        if(tipcommand2.compareTo("MC") == 0) Tipcode2 = 1;
        if(tipcommand2.compareTo("C") == 0) Tipcode2 = 2;
        if(tipcommand2.compareTo("DRG") == 0) Tipcode2 = 3;
        if(tipcommand2.compareTo("Bksp") == 0) Tipcode2 = 4;
        if(tipcommand2.compareTo("sin") == 0) Tipcode2 = 5;
        if(tipcommand2.compareTo("cos") == 0) Tipcode2 = 6;
        if(tipcommand2.compareTo("tan") == 0) Tipcode2 = 7;
        if(tipcommand2.compareTo("log") ==0) Tipcode2 = 8;
        if(tipcommand2.compareTo("ln") == 0) Tipcode2 = 9;
        if(tipcommand2.compareTo("n!") == 0) Tipcode2 = 10;
        if(tipcommand2.compareTo("√") == 0) Tipcode2 = 11;
        if(tipcommand2.compareTo("^") == 0) Tipcode2 = 12;

        TipShow(bracket , Tipcode1 , Tipcode2 , tipcommand1 , tipcommand2);
    }

    /*
     * showing tip message, using with TipChecker()
     */
    private void TipShow(int bracket , int tipcode1 , int tipcode2 ,
                         String tipcommand1 , String tipcommand2) {
        String tipmessage = "";
        if(tipcode1 != 0) tip_lock = false;
        switch(tipcode1) {
        /*case -1:
            tipmessage = tipcommand2 + "  cannot be the first operator\n";
            break;
        case 1:
            tipmessage = tipcommand1 + "  following should be: number/(/./-/function \n";
            break;
        case 2:
            tipmessage = tipcommand1 + "  following should be：)/operator \n";
            break;
        case 3:
            tipmessage = tipcommand1 + "  following should be：)/number/operator \n";
            break;
        case 4:
            tipmessage = tipcommand1 + "  following should be：)/./number/operator \n";
            break;
        case 5:
            tipmessage = tipcommand1 + "  following should be：(/./number/function \n";
            break;
        case 6:
            tipmessage = tipcommand1 + "  following should be: (/./number \n";
            break;
        case 7:
            tipmessage = tipcommand1 + "  following should be: (/./number \n";
            break;
        case 8:
            tipmessage = "dot repeated\n";
            break;*/
            case 9:
                tipmessage = "cannot calculate, lacking "+ bracket +" )";
                break;
        /*case 10:
            tipmessage = "no need of )";
            break;*/
        }
        switch(tipcode2) {
            case 1:
                tipmessage = tipmessage + "[MC usage: clear MEM]";
                break;
            case 2:
                tipmessage = tipmessage + "[C usage: return to zero]";
                break;
            case 3:
                tipmessage = tipmessage + "[DRG usage: choose DEG or RAD]";
                break;
            case 4:
                tipmessage = tipmessage + "[DEL usage: backspace]";
                break;
            case 5:
                tipmessage = tipmessage + "sin usage example：\n" +
                        "DEG：sin30 = 0.5      RAD：sin1 = 0.84\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "sin(cos45)，but not sincos45" ;
                break;
            case 6:
                tipmessage = tipmessage + "cos usage example：\n" +
                        "DEG：cos60 = 0.5      RAD：cos1 = 0.54\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "cos(sin45)，but not cossin45" ;
                break;
            case 7:
                tipmessage = tipmessage + "tan usage example：\n" +
                        "DEG：tan45 = 1      RAD：tan1 = 1.55\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "tan(cos45)，but not tancos45" ;
                break;
            case 8:
                tipmessage = tipmessage + "log usage example：\n" +
                        "log10 = log(5+5) = 1\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "log(tan45)，but not logtan45" ;
                break;
            case 9:
                tipmessage = tipmessage + "ln usage example：\n" +
                        "ln10 = le(5+5) = 2.3\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "ln(tan45)，but not lntan45" ;
                break;
            case 10:
                tipmessage = tipmessage + "n! usage example：\n" +
                        "n!3 = n!(1+2) = 3×2×1 = 6\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "n!(log1000)，but not n!log1000" ;
                break;
            case 11:
                tipmessage = tipmessage + "√ usage example：\n" +
                        "27 under the cube root sign  27√3 = 3\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "(function)√(function) ， (n!3)√(log100) = 2.45";
                break;
            case 12:
                tipmessage = tipmessage + "^ usage example：\n" +
                        "2 to the 3: 2^3 = 8\n" +
                        "Note：use parenthesis when using when other functions，eg.：\n" +
                        "(function)√(function) ， (n!3)^(log100) = 36";
                break;
        }

        tip.setText(tipmessage);
    }
    /*
         * Calculation，pass the str to calc().process() to calculate
         * Algorithm includes:
         * 1. Showing and storing    process(String str)               (to show the answer and store both the answer the original expression)
         * 2. Calculation            calculation(String str)           (only for a legal expression)
         * 2、data formatting        FP(double n)                      (to ensure the accuracy of data)
         * 3、factory algorithm      N(double n)                       (to calculate n! and return the result)
         * 4、Error alert               showError(int code ,String str)   (to return the error)
         */
    public class calc {

        public calc(){

        }
        final int MAXLEN = 500;

        public void process(String str){
            String result = calculation(str);
            if (!result.equals("Error")){
                answer_data.add(result);//to record the answer
                mem_data.add(str_old + "=" + result);//to record the original expression
                input.setText(result); //to show the answer on the monitor
                tip.setText("Calculation done. Press 'C' to continue.");
                mem.setText(str_old + "=" + result);
            }
        }

        /*
         * calculate the expression
         * scan from left to right, pass numbers to number[], pass operators to operator[]
         * +- priority are 1，×÷ priority are 2，log ln sin cos tan n! priority are 3，√^priority are 4
         * if the operator is prior than array element, give one operator and two numbers to do the calculation
         * repeat until the current operator is greater than the array element
         * after scanning, calculate the remainning operators and numbers
         */
        public String calculation(String str) {
            int topOp = 0, topNum = 0, flag = 1, weightTemp = 0;
            //weightTemp temporarily record the change of priority
            //topOp is the counter for weight[] and operator[]；topNum is the counter for number[]
            //flag is a counter for positive and negative numbers，1 indicates positive，-1 indicates negative
            int weight[];  //store the priority of operators in operator[], count with topOp
            double number[];  //store the numbers, count with topNum
            char ch, ch_gai, operator[];//operator[] stores operators, count with topOp
            String num;//record number，str spilted by +-*/()sctgl!√^，the string between +-*/()sctgl!√^ is number
            weight = new int[MAXLEN];
            number = new double[MAXLEN];
            operator = new char[MAXLEN];
            String expression = str;
            StringTokenizer expToken = new StringTokenizer(expression,"+-*/()sctgl!√^");
            int i = 0;
            while (i < expression.length()) {
                ch = expression.charAt(i);
                if (i == 0) {
                    if (ch == '-') flag = -1;
                } else if(expression.charAt(i-1) == '(' && ch == '-') flag = -1;
                if (ch <= '9' && ch >= '0'|| ch == '.' || ch == 'E') {
                    num = expToken.nextToken();
                    ch_gai = ch;
                    while (i < expression.length() &&
                            (ch_gai <= '9' && ch_gai >= '0'|| ch_gai == '.' || ch_gai == 'E'))
                        ch_gai = expression.charAt(i++);
                    if (i >= expression.length()) i-=1; else i-=2;
                    if (num.compareTo(".") == 0) number[topNum++] = 0;
                    else {
                        number[topNum++] = Double.parseDouble(num)*flag;
                        flag = 1;
                    }
                }

                if (ch == '('){
                    int lparenCount = 1;
                    int rparenCount = 0;
                    for (int k = i + 1; k < expression.length(); k ++) {
                        if (expression.charAt(k) == '(') lparenCount++;
                        if (expression.charAt(k) == ')') {
                            rparenCount++;
                            if (lparenCount == rparenCount) {
                                String newexp = calculation(expression.substring(i+1, k)) + expression.substring(k + 1);
                                expToken = new StringTokenizer(newexp,"+-*/()sctgl!√^");
                                expression = expression.substring(0, i) + newexp;
                                i--;
                                break;
                            }
                        }
                    }
                }
                if (ch == '-' && flag == 1 || ch == '+' || ch == '*'|| ch == '/' ||
                        ch == 's' ||ch == 'c' || ch == 't' || ch == 'g' || ch == 'l' ||
                        ch == '!' || ch == '√' || ch == '^') {
                    switch (ch) {
                        case '+':
                        case '-':
                            weightTemp = 1;
                            break;
                        case '*':
                        case '/':
                            weightTemp = 2;
                            break;
                        case 's':
                        case 'c':
                        case 't':
                        case 'g':
                        case 'l':
                        case '!':
                            weightTemp = 3;
                            break;
                        //case '^':
                        //case '√':
                        default:
                            weightTemp = 4;
                            break;
                    }
                    if (topOp == 0 || weight[topOp-1] < weightTemp) {
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                    }else {
                        while (topOp > 0 && weight[topOp-1] >= weightTemp) {
                            switch (operator[topOp-1]) {
                                case '+':
                                    number[topNum-2]+=number[topNum-1];
                                    break;
                                case '-':
                                    number[topNum-2]-=number[topNum-1];
                                    break;
                                case '*':
                                    number[topNum-2]*=number[topNum-1];
                                    break;
                                case '/':
                                    if (number[topNum-1] == 0) {
                                        showError(1,str_old);
                                        return "Error";
                                    }
                                    number[topNum-2]/=number[topNum-1];
                                    break;
                                case '√':
                                    if(number[topNum-1] == 0 || (number[topNum-2] < 0 &&
                                            number[topNum-1] % 2 == 0)) {
                                        showError(2,str_old);
                                        return "Error";
                                    }
                                    number[topNum-2] =
                                            Math.pow(number[topNum-2], 1/number[topNum-1]);
                                    break;
                                case '^':
                                    number[topNum-2] =
                                            Math.pow(number[topNum-2], number[topNum-1]);
                                    break;
                                case 's':
                                    if(drg_flag == true) {
                                        number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                                    } else {
                                        number[topNum-1] = Math.sin(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 'c':
                                    if(drg_flag == true) {
                                        number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                                    } else {
                                        number[topNum-1] = Math.cos(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 't':
                                    if(drg_flag == true) {
                                        if((Math.abs(number[topNum-1])/90)%2 == 1) {
                                            showError(2,str_old);
                                            return "Error";
                                        }
                                        number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                                    } else {
                                        if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {
                                            showError(2,str_old);
                                            return "Error";
                                        }
                                        number[topNum-1] = Math.tan(number[topNum-1]);
                                    }
                                    topNum++;
                                    break;
                                case 'g':
                                    if(number[topNum-1] <= 0) {
                                        showError(2,str_old);
                                        return "Error";
                                    }
                                    number[topNum-1] = Math.log10(number[topNum-1]);
                                    topNum++;
                                    break;
                                case 'l':
                                    if(number[topNum-1] <= 0) {
                                        showError(2,str_old);
                                        return "Error";
                                    }
                                    number[topNum-1] = Math.log(number[topNum-1]);
                                    topNum++;
                                    break;
                                case '!':
                                    if(number[topNum-1] > 170) {
                                        showError(3,str_old);
                                        return "Error";
                                    } else if(number[topNum-1] < 0) {
                                        showError(2,str_old);
                                        return "Error";
                                    }
                                    number[topNum-1] = N(number[topNum-1]);
                                    topNum++;
                                    break;
                            }
                            topNum--;
                            topOp--;
                        }
                        weight[topOp] = weightTemp;
                        operator[topOp] = ch;
                        topOp++;
                    }
                }
                i++;
            }
            while (topOp>0) {
                switch (operator[topOp-1]) {
                    case '+':
                        number[topNum-2]+=number[topNum-1];
                        break;
                    case '-':
                        number[topNum-2]-=number[topNum-1];
                        break;
                    case '*':
                        number[topNum-2]*=number[topNum-1];
                        break;
                    case '/':
                        if (number[topNum-1] == 0) {
                            showError(1,str_old);
                            return "Error";
                        }
                        number[topNum-2]/=number[topNum-1];
                        break;
                    case '√':
                        if(number[topNum-1] == 0 || (number[topNum-2] < 0 &&
                                number[topNum-1] % 2 == 0)) {
                            showError(2,str_old);
                            return "Error";
                        }
                        number[topNum-2] =
                                Math.pow(number[topNum-2], 1/number[topNum-1]);
                        break;
                    case '^':
                        number[topNum-2] =
                                Math.pow(number[topNum-2], number[topNum-1]);
                        break;
                    case 's':
                        if(drg_flag == true) {
                            number[topNum-1] = Math.sin((number[topNum-1]/180)*pi);
                        } else {
                            number[topNum-1] = Math.sin(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 'c':
                        if(drg_flag == true) {
                            number[topNum-1] = Math.cos((number[topNum-1]/180)*pi);
                        } else {
                            number[topNum-1] = Math.cos(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 't':
                        if(drg_flag == true) {
                            if((Math.abs(number[topNum-1])/90)%2 == 1) {
                                showError(2,str_old);
                                return "Error";
                            }
                            number[topNum-1] = Math.tan((number[topNum-1]/180)*pi);
                        } else {
                            if((Math.abs(number[topNum-1])/(pi/2))%2 == 1) {
                                showError(2,str_old);
                                return "Error";
                            }
                            number[topNum-1] = Math.tan(number[topNum-1]);
                        }
                        topNum++;
                        break;
                    case 'g':
                        if(number[topNum-1] <= 0) {
                            showError(2,str_old);
                            return "Error";
                        }
                        number[topNum-1] = Math.log10(number[topNum-1]);
                        topNum++;
                        break;
                    case 'l':
                        if(number[topNum-1] <= 0) {
                            showError(2,str_old);
                            return "Error";
                        }
                        number[topNum-1] = Math.log(number[topNum-1]);
                        topNum++;
                        break;
                    case '!':
                        if(number[topNum-1] > 170) {
                            showError(3,str_old);
                            return "Error";
                        } else if(number[topNum-1] < 0) {
                            showError(2,str_old);
                            return "Error";
                        }
                        number[topNum-1] = N(number[topNum-1]);
                        topNum++;
                        break;
                }
                topNum--;
                topOp--;
            }

            if(number[0] > 7.3E306) {
                showError(3,str_old);
                //input.setText("\""+str_old+"\": The answer is so large that I cannot hold it.");
                return "Error";
            }
            return String.valueOf(FP(number[0]));
        }

        /*
         * FP = floating point controlling the digits of floating numbers to ensure accuracy
         * or 0.6-0.2=0.39999999999999997 may occur
         * the accuracy format of this format is 15 digits
         */
        public double FP(double n) {
            //NumberFormat format=NumberFormat.getInstance();  //创建一个格式化类f
            //format.setMaximumFractionDigits(18);    //设置小数位的格式
            DecimalFormat format = new DecimalFormat("0.#############");
            return Double.parseDouble(format.format(n));
        }

        /*
         * factory algorithm
         */
        public double N(double n) {
            int i = 0;
            double sum = 1;
            for(i = 1;i <= n;i++) {
                sum = sum*i;
            }
            return sum;
        }

        /*
         * error alert，after pressing '=', if an error occurs during the calculation, show the alert
         */
        public void showError(int code ,String str) {
            String message="";
            switch (code) {
                case 1:
                    message = "0 cannot be the divisor";
                    break;
                case 2:
                    message = "wrong funcion format";
                    break;
                case 3:
                    message = "The data is too large that I cannot hold it";
            }
            input.setText("\""+str+"\""+": "+message);
            tip.setText(message+"\n"+"Calculation done. Press 'C' to continue.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_about:
                new AlertDialog.Builder(MainActivity.this)
                    .setTitle("ABOUT")
                    .setMessage("This is an application developed by Jiahao Wang(Frank) & Yi Sun(Yi) as the assignment project of COMP2500 in first semester 2016. \n\n"+
                            "Any bugs found please send to: \n\n"+"u5833429@anu.edu.au\nu5694171@anu.edu.au")
                    .setNegativeButton("I know", null)
                    .show();
                return true;
            case R.id.programmer_mode:
                Intent programmerMode = new Intent(this, ProgrammerMode.class);
                startActivity(programmerMode);
                return true;
            case R.id.web_mode:
//                webView = new WebView(this);
//                webView.getSettings().setJavaScriptEnabled(true);
//                try {
//                    webView.loadUrl("http://www.wolframalpha.com/");
//                }
//                catch(Exception ex)
//                {
//                    ex.printStackTrace();
//                }
                Intent webMode = new Intent(this, WebMode.class);
                startActivity(webMode);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (lastClickTime == 0){
            Toast.makeText(this, "Press back button one more time to exit.", Toast.LENGTH_SHORT).show();
            lastClickTime = System.currentTimeMillis();
        }
        else {
            if (System.currentTimeMillis() - lastClickTime <= 1000){
                supportFinishAfterTransition();
            }
            else {
                Toast.makeText(this, "Press back button one more time to exit.", Toast.LENGTH_SHORT).show();
                lastClickTime = System.currentTimeMillis();
            }
        }
    }
}
