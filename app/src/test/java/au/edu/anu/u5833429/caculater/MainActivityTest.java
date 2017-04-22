package au.edu.anu.u5833429.caculater;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by angelasun on 14/05/2016.
 */
public class MainActivityTest extends MainActivity{
    MainActivity mainActivity = new MainActivity();
    calc calc = new calc();

    @Test
    public void testCalculation(){
        String ans1 = calc.calculation("5");
        assertEquals("ans1", "5.0", ans1);
        String ans2 = calc.calculation("3.02");
        assertEquals("ans2", "3.02", ans2);
        String ans3 = calc.calculation("3.6+6.7");
        assertEquals("ans3", "10.3", ans3);
        String ans4 = calc.calculation("9.5-3.2");
        assertEquals("ans4", "6.3", ans4);
        String ans5 = calc.calculation("3.4*11.5");
        assertEquals("ans5", "39.1", ans5);
        String ans6 = calc.calculation("9.6/3.2");
        assertEquals("ans6", "3.0", ans6);
        String ans7 = calc.calculation("s30");
        assertEquals("ans7", "0.5", ans7);
        String ans8 = calc.calculation("c60");
        assertEquals("ans8", "0.5", ans8);
        String ans9 = calc.calculation("t45");
        assertEquals("ans9", "1.0", ans9);
        String ans10 = calc.calculation("(6.7+3.3)*5-10/2");
        assertEquals("ans10", "45.0", ans10);
        String ans11 = calc.calculation("!4");
        assertEquals("ans11", "24.0", ans11);
        String ans12 = calc.calculation("1.1^2");
        assertEquals("ans12", "1.21", ans12);
        String ans13 = calc.calculation("27√3");
        assertEquals("ans13", "3.0", ans13);
        String ans14 = calc.calculation("g10");
        assertEquals("ans14", "1.0", ans14);
        String ans15 = calc.calculation("l5");
        assertEquals("ans15", "1.6094379124341", ans15);
        String ans16 = calc.calculation("(s30+5)*c60/t45-(!3)^(g(t45*10))-l3+4√2+1.75");
        assertEquals("ans16", "-0.5986122886681", ans16);
        drg_flag = false;
        String ans17 = calc.calculation("s1");
        assertEquals("ans17", "0.8414709848079", ans17);
        String ans18 = calc.calculation("c1");
        assertEquals("ans18", "0.5403023058681", ans18);
        String ans19 = calc.calculation("t1");
        assertEquals("ans19", "1.5574077246549", ans19);
    }

    @Test
    public void testTTO(){
        int ans1 = mainActivity.TTO("skfjeifjsin");
        assertEquals("ans1" , 3, ans1);
        int ans2 = mainActivity.TTO("djfiejoiascos");
        assertEquals("ans2", 3, ans2);
        int ans3 = mainActivity.TTO("dfjsaejfjtan");
        assertEquals("ans3", 3, ans3);
        int ans4 = mainActivity.TTO("skfjjeijlog");
        assertEquals("ans4", 3, ans4);
        int ans5 = mainActivity.TTO("skfjeiln");
        assertEquals("ans5", 2, ans5);
        int ans6 = mainActivity.TTO("akjsfjeijfn!");
        assertEquals("ans6", 2, ans6);
        int ans7 = mainActivity.TTO("sakfjasjfiasjeijfisaj");
        assertEquals("ans7", 1, ans7);
    }

    @Test
    public void testRight(){
        String str1 = "12";
        assertEquals("str1", true, mainActivity.right(str1));
        String str2 = "34+89";
        assertEquals("str2", true, mainActivity.right(str2));
        String str3 = "3+h";
        assertEquals("str3", false, mainActivity.right(str3));
        String str4 = "1+*";
        assertEquals("str4", true, mainActivity.right(str4));
        String str5 = "si8";
        assertEquals("str5", true, mainActivity.right(str5));
        String str6 = "sljfeijs";
        assertEquals("str6", false, mainActivity.right(str6));
    }

}
