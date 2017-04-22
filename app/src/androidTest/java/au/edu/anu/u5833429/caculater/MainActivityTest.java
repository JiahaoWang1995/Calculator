package au.edu.anu.u5833429.caculater;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by angelasun on 14/05/2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2 {
    private MainActivity mainActivity;
    private EditText input;
    private Button[] btn = new Button[10];
    private Button
            div, mul, sub, add, equal,            // / * - + =
            sin, cos, tan, log, ln,               //Functions
            sqrt, square, factorial, del,  //radical sign, root, square, factorial, delete
            left, right, dot, drg,          //（     ）  .  button to shift between degree and radian
            c, up, down, bin, oct, hex;
    private TextView tip;
    private TextView _drg;
    private TextView mem;

    public MainActivityTest() {
        super("au.edu.anu.u5833429.caculater", MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mainActivity = (MainActivity) getActivity();
        input = (EditText) mainActivity.findViewById(R.id.input);
        mem = (TextView) mainActivity.findViewById(R.id.mem);
        tip = (TextView) mainActivity.findViewById(R.id.tip);
        _drg = (TextView) mainActivity.findViewById(R.id._drg);
        btn[0] = (Button) mainActivity.findViewById(R.id.zero);
        btn[1] = (Button) mainActivity.findViewById(R.id.one);
        btn[2] = (Button) mainActivity.findViewById(R.id.two);
        btn[3] = (Button) mainActivity.findViewById(R.id.three);
        btn[4] = (Button) mainActivity.findViewById(R.id.four);
        btn[5] = (Button) mainActivity.findViewById(R.id.five);
        btn[6] = (Button) mainActivity.findViewById(R.id.six);
        btn[7] = (Button) mainActivity.findViewById(R.id.seven);
        btn[8] = (Button) mainActivity.findViewById(R.id.eight);
        btn[9] = (Button) mainActivity.findViewById(R.id.nine);
        div = (Button) mainActivity.findViewById(R.id.divide);
        mul = (Button) mainActivity.findViewById(R.id.mul);
        sub = (Button) mainActivity.findViewById(R.id.sub);
        add = (Button) mainActivity.findViewById(R.id.add);
        equal = (Button) mainActivity.findViewById(R.id.equal);
        sin = (Button) mainActivity.findViewById(R.id.sin);
        cos = (Button) mainActivity.findViewById(R.id.cos);
        tan = (Button) mainActivity.findViewById(R.id.tan);
        log = (Button) mainActivity.findViewById(R.id.log);
        ln = (Button) mainActivity.findViewById(R.id.ln);
        sqrt = (Button) mainActivity.findViewById(R.id.sqrt);
        square = (Button) mainActivity.findViewById(R.id.square);
        factorial = (Button) mainActivity.findViewById(R.id.factorial);
        del = (Button) mainActivity.findViewById(R.id.del);
        left = (Button) mainActivity.findViewById(R.id.left);
        right = (Button) mainActivity.findViewById(R.id.right);
        dot = (Button) mainActivity.findViewById(R.id.dot);
        drg = (Button) mainActivity.findViewById(R.id.drg);
        c = (Button) mainActivity.findViewById(R.id.c);
        up = (Button) mainActivity.findViewById(R.id.up);
        down = (Button) mainActivity.findViewById(R.id.down);
        bin = (Button) mainActivity.findViewById(R.id.bin);
        oct = (Button) mainActivity.findViewById(R.id.oct);
        hex = (Button) mainActivity.findViewById(R.id.hex);
    }

    @Override
    protected void tearDown() throws Exception{
        super.tearDown();
    }

    public void testInputAndButton() throws Exception {
        boolean pass = false;
        SystemClock.sleep(1000);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                log.requestFocus();
                log.performClick();
                btn[1].requestFocus();
                btn[1].performClick();
                btn[0].requestFocus();
                btn[0].performClick();
                square.requestFocus();
                square.performClick();
                left.requestFocus();
                left.performClick();
                btn[6].requestFocus();
                btn[6].performClick();
                add.requestFocus();
                add.performClick();
                btn[7].requestFocus();
                btn[7].performClick();
                sub.requestFocus();
                sub.performClick();
                btn[9].requestFocus();
                btn[9].performClick();
                right.requestFocus();
                right.performClick();
                sub.requestFocus();
                sub.performClick();
                sin.requestFocus();
                sin.performClick();
                btn[3].requestFocus();
                btn[3].performClick();
                btn[0].requestFocus();
                btn[0].performClick();
                mul.requestFocus();
                mul.performClick();
                cos.requestFocus();
                cos.performClick();
                btn[6].requestFocus();
                btn[6].performClick();
                btn[0].requestFocus();
                btn[0].performClick();
                div.requestFocus();
                div.performClick();
                tan.requestFocus();
                tan.performClick();
                btn[4].requestFocus();
                btn[4].performClick();
                btn[5].requestFocus();
                btn[5].performClick();
                add.requestFocus();
                add.performClick();
                btn[8].requestFocus();
                btn[8].performClick();
                btn[1].requestFocus();
                btn[1].performClick();
                dot.requestFocus();
                dot.performClick();
                btn[0].requestFocus();
                btn[0].performClick();
                sqrt.requestFocus();
                sqrt.performClick();
                btn[2].requestFocus();
                btn[2].performClick();
                sub.requestFocus();
                sub.performClick();
                ln.requestFocus();
                ln.performClick();
                btn[3].requestFocus();
                btn[3].performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected input", "log10^(6+7-9)-sin30*cos60/tan45+81.0√2-ln3", input.getText().toString()); // To test if the input is expected

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                equal.requestFocus();
                equal.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected result", "11.6513877113319", input.getText().toString()); // To test if the calculation result it displayed in the editview as expected

        getInstrumentation().runOnMainSync(new Runnable() {

            @Override
            public void run() {
                c.requestFocus();
                c.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected input", "", input.getText().toString()); // To test if the clear result it displayed as expected
        assertEquals("unexpected tip", "Welcome", tip.getText().toString());
    }

    public void testMem() throws Exception{
        boolean pass = false;
        SystemClock.sleep(1000);
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        btn[1].requestFocus();
                        btn[1].performClick();
                        equal.requestFocus();
                        equal.performClick();
                        btn[1].requestFocus();
                        btn[1].performClick();
                        add.requestFocus();
                        add.performClick();
                        btn[2].requestFocus();
                        btn[2].performClick();
                        equal.requestFocus();
                        equal.performClick();
                    }
                }
        );
        SystemClock.sleep(1000);
        assertEquals("unexpected mem2", "1+2=3.0", mem.getText().toString());
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        btn[1].requestFocus();
                        btn[1].performClick();
                        equal.requestFocus();
                        equal.performClick();
                        btn[1].requestFocus();
                        btn[1].performClick();
                        add.requestFocus();
                        add.performClick();
                        btn[2].requestFocus();
                        btn[2].performClick();
                        equal.requestFocus();
                        equal.performClick();
                        up.requestFocus();
                        up.performClick();
                    }
                }
        );
        SystemClock.sleep(1000);
        assertEquals("unexpected mem1", "1=1.0", mem.getText().toString());
    }


    public void testInput2() throws Exception{
        boolean pass = false;
        SystemClock.sleep(1000);
        getInstrumentation().runOnMainSync(
                new Runnable() {
                    @Override
                    public void run() {
                        btn[1].requestFocus();
                        btn[1].performClick(); //show
                        left.requestFocus();
                        left.performClick();
                        right.requestFocus();
                        right.performClick();
                        sin.requestFocus();
                        sin.performClick();
                        cos.requestFocus();
                        cos.performClick();
                        tan.requestFocus();
                        tan.performClick();
                        log.requestFocus();
                        log.performClick();
                        ln.requestFocus();
                        ln.performClick();
                        add.requestFocus();
                        add.performClick(); //show
                        add.requestFocus();
                        add.performClick();
                        sub.requestFocus();
                        sub.performClick();
                        mul.requestFocus();
                        mul.performClick();
                        div.requestFocus();
                        div.performClick();
                        right.requestFocus();
                        right.performClick();
                        left.requestFocus();
                        left.performClick(); //show
                        add.requestFocus();
                        add.performClick();
                        mul.requestFocus();
                        mul.performClick();
                        div.requestFocus();
                        div.performClick();
                        sqrt.requestFocus();
                        sqrt.performClick();
                        square.requestFocus();
                        square.performClick();
                        sin.requestFocus();
                        sin.performClick(); //show
                        add.requestFocus();
                        add.performClick();
                        sub.requestFocus();
                        sub.performClick();
                        mul.requestFocus();
                        mul.performClick();
                        div.requestFocus();
                        div.performClick();
                        sin.requestFocus();
                        sin.performClick();
                        cos.requestFocus();
                        cos.performClick();
                        tan.requestFocus();
                        tan.performClick();
                        log.requestFocus();
                        log.performClick();
                        ln.requestFocus();
                        ln.performClick();
                        sqrt.requestFocus();
                        sqrt.performClick();
                        square.requestFocus();
                        square.performClick();
                        factorial.requestFocus();
                        factorial.performClick();
                        right.requestFocus();
                        right.performClick(); //show
                        btn[3].requestFocus();
                        btn[3].performClick();
                        btn[0].requestFocus();
                        btn[0].performClick();
                        right.requestFocus();
                        right.performClick();
                        left.requestFocus();
                        left.performClick();
                        sqrt.requestFocus();
                        sqrt.performClick();
                        add.requestFocus();
                        add.performClick();
                        sub.requestFocus();
                        sub.performClick();
                        mul.requestFocus();
                        mul.performClick();
                        div.requestFocus();
                        div.performClick();
                        sin.requestFocus();
                        sin.performClick();
                        cos.requestFocus();
                        cos.performClick();
                        tan.requestFocus();
                        tan.performClick();
                        log.requestFocus();
                        log.performClick();
                        ln.requestFocus();
                        ln.performClick();
                        sqrt.requestFocus();
                        sqrt.performClick();
                        square.requestFocus();
                        square.performClick();
                        factorial.requestFocus();
                        factorial.performClick();
                        right.requestFocus();
                        right.performClick();
                    }
                }
        );
        SystemClock.sleep(1000);
        assertEquals("unexpected input", "1+(sin30)√", input.getText().toString());
    }


    public void testTips() throws Exception{
        boolean pass = false;
        SystemClock.sleep(1000);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                sin.requestFocus();
                sin.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "sin usage example：\n" +
                "DEG：sin30 = 0.5      RAD：sin1 = 0.84\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "sin(cos45)，but not sincos45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                cos.requestFocus();
                cos.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "cos usage example：\n" +
                "DEG：cos60 = 0.5      RAD：cos1 = 0.54\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "cos(sin45)，but not cossin45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                tan.requestFocus();
                tan.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "tan usage example：\n" +
                "DEG：tan45 = 1      RAD：tan1 = 1.55\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "tan(cos45)，but not tancos45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                drg.requestFocus();
                drg.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "[DRG usage: choose DEG or RAD]", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                log.requestFocus();
                log.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "log usage example：\n" +
                "log10 = log(5+5) = 1\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "log(tan45)，but not logtan45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ln.requestFocus();
                ln.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "ln usage example：\n" +
                "ln10 = le(5+5) = 2.3\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "ln(tan45)，but not lntan45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                factorial.requestFocus();
                factorial.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "n! usage example：\n" +
                "n!3 = n!(1+2) = 3×2×1 = 6\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "n!(log1000)，but not n!log1000", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                sqrt.requestFocus();
                sqrt.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "√ usage example：\n" +
                "27 under the cube root sign  27√3 = 3\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "(function)√(function) ， (n!3)√(log100) = 2.45", tip.getText().toString());

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                square.requestFocus();
                square.performClick();
            }
        });
        SystemClock.sleep(1000);
        assertEquals("unexpected tips", "^ usage example：\n" +
                "2 to the 3: 2^3 = 8\n" +
                "Note：use parenthesis when using when other functions，eg.：\n" +
                "(function)√(function) ， (n!3)^(log100) = 36", tip.getText().toString());
    }
}
