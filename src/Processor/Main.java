package Processor;

import java.io.IOException;

public class Main {
    public static void main(String[] args){
        buildNewRunOnce();
        //testSigmoid();
        //testWeight();
        //printInputs();
    }

    private static void printInputs(){
        NeuralNetStructure net = null;


        try {
            net = new NeuralNetStructure();
        } catch (IOException e) {
            e.printStackTrace();
        }


        net.buildNet(784, 16, 10);

        double Correct = net.populateInputs();

        net.propagateForward();
        net.printHiddenNeurons();
        net.checkConnections();
       // net.propagateBackwards();
    }

    private static void testSigmoid() {
        Sigmoid sigmoid = new Sigmoid();
        for(int i = -10; i < 10;i ++){

            System.out.println(sigmoid.getSigmoid(i));
        }

    }

    private static void testWeight()  {
        NeuralNetStructure net = null;

        try {
            net = new NeuralNetStructure();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(double i = -10; i < 10;i ++){
            System.out.println(net.randomWeight());
        }

    }

    private static void buildNewRunOnce()  {
        NeuralNetStructure net = null;


        try {
            net = new NeuralNetStructure();
        } catch (IOException e) {
            e.printStackTrace();
        }


        net.buildNet(784, 16, 10);


        double guessedCorrectly = 0;
        double count = 0;

        while(net.dataBaseHasNext()){
            double Correct = net.populateInputs();
            net.propagateForward();
            double guessed = net.printGuess();

            if(Correct != guessed){

                net.propagateBackwards();
            }
            if(Correct == guessed){
                guessedCorrectly++;
            }
            net.nextTrainingData();
            count ++;
        }

        System.out.print((guessedCorrectly / count)* 100);

    }
}
