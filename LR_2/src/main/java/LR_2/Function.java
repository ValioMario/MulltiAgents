package LR_2;

public class Function {
    private float n;
    public float result;

    public float squaring(float n){
        this.n = n;
        result = (float) (1/Math.pow(2, n));
        System.out.println("2^(-x) = " + result);
        return result;
    }
    public float exp(float n){
        this.n = n;
        result = (float) Math.exp(n*0.2);
        System.out.println("e^(x*0.2) = " + result);
        return result;
    }
    public float cos(float n){
        this.n = n;
        result = (float) Math.cos(n);
        System.out.println("cos(x) = " + result);
        return result;
    }
}
