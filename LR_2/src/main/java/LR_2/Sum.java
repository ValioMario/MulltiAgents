package LR_2;

import java.util.ArrayList;

public class Sum {

    public float[] sum1 = new float[5];
    public float[] sum(float[] sum, String msg){

        String[] numb = msg.split(";");
        System.out.println(" ПЕРЕДАЧА СУММЫ В РАСЧЕТ" + sum);

        float numb1 = Float.parseFloat(numb[0]);
        float numb2 = Float.parseFloat(numb[1]);
        float numb3 = Float.parseFloat(numb[2]);
        float X = Float.parseFloat(numb[3]);
        float delta = Float.parseFloat(numb[4]);
        System.out.println("беру на расчет занчения: "+sum[0] +","+ numb1);
        sum1[0] = sum[0] + numb1;
        sum1[1] = sum[1] + numb2;
        sum1[2] = sum[2] + numb3;
        sum1[3] = X;
        sum1[4] = delta;
        return sum1;
    }
}
