package Processor;

public class Sigmoid {

    public Sigmoid(){

    }

    public double getSigmoid(double computedSum){
        double neg = -1 * computedSum;


        double exp = Math.exp(neg);

        if(exp>403){
            exp = 150;
        }
        double plus1 = 1 + exp;

        double final1 = 1 / plus1;

        return final1;
    }

}
