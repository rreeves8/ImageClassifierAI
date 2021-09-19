package Processor;

import MnistReader.DataBase;
import MnistReader.MnistMatrix;
import Net.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class NeuralNetStructure {

    private Iterator trainingData;
    private MnistMatrix currentData;

    private ArrayList<InputNeuron> inputNeurons = new ArrayList<InputNeuron>();
    private ArrayList<HiddenNeuron> hiddenNeurons = new ArrayList<HiddenNeuron>();
    private ArrayList<HiddenNeuron> hiddenNeurons2 = new ArrayList<HiddenNeuron>();
    private ArrayList<OutputNeuron> outputNeurons = new ArrayList<OutputNeuron>();

    public NeuralNetStructure() throws IOException {
        DataBase data = new DataBase();
        trainingData = data.getIterable();
        System.out.println(data.size());
        nextTrainingData();
    }

    public void nextTrainingData(){
        currentData = (MnistMatrix) trainingData.next();
    }

    public boolean dataBaseHasNext(){
        return trainingData.hasNext();
    }

    public double populateInputs(){
        //System.out.println("label: " + currentData.getLabel());
        int count = 0;
        for (int r = 0; r < currentData.getNumberOfRows(); r++ ) {
            for (int c = 0; c < currentData.getNumberOfColumns(); c++) {
                inputNeurons.get(count++).setValue(currentData.getValue(r,c));
            }
        }
        return currentData.getLabel();
    }

    public void buildNet(int inputs, int hiddens, int outputs){

        for(int i = 0; i < inputs; i++) {
            inputNeurons.add(new InputNeuron());
        }
        for(int i = 0; i < hiddens; i++) {
            hiddenNeurons.add(new HiddenNeuron());
            hiddenNeurons2.add(new HiddenNeuron());
        }
        for(int i = 0; i < outputs; i ++){
            outputNeurons.add(new OutputNeuron());
        }

        for(int j = 0; j < inputs; j ++) {
            for (int i = 0; i < hiddens; i++) {
                Connection conn = new Connection(inputNeurons.get(j), hiddenNeurons.get(i), randomWeight());
                inputNeurons.get(j).addConnection(conn);
                hiddenNeurons.get(i).addConnectionBehind(conn);
            }
        }
        for(int i = 0 ; i < hiddens; i ++) {
            for (int k = 0; k < hiddens; k++) {
                Connection conn = new Connection(hiddenNeurons.get(i), hiddenNeurons2.get(k), randomWeight());
                hiddenNeurons.get(i).addConnectionInFront(conn);
                hiddenNeurons2.get(k).addConnectionBehind(conn);
            }
        }
        for(int l = 0; l < hiddens; l ++){
            for(int i = 0; i < outputs; i ++){
                Connection conn = new Connection(hiddenNeurons2.get(l), outputNeurons.get(i), randomWeight());
                hiddenNeurons2.get(l).addConnectionInFront(conn);
                outputNeurons.get(i).addConnection(conn);
            }
        }
    }

    public void propagateBackwards(){
        double learningRate = 0.1;
        Sigmoid sigmoid = new Sigmoid();

        for(int i = 0; i < outputNeurons.size(); i ++) {
            for(int j = 0; j < hiddenNeurons2.size(); j ++) {
                double Wo = outputNeurons.get(i).getConn(j).getWeight();

                double firstComponent = 2 * (outputNeurons.get(i).getValue()-outputNeurons.get(i).getExpected());
                double secondComponent = sigmoid.getSigmoid(outputNeurons.get(i).getZ()) * (1 -sigmoid.getSigmoid(outputNeurons.get(i).getZ()));
                double thirdComponent = outputNeurons.get(i).getConn(j).getBehind().getValue();

                double cost = firstComponent * secondComponent * thirdComponent;
                outputNeurons.get(i).getConn(j).setCost(cost);

                double newWeight = Wo - (learningRate * cost);

                outputNeurons.get(i).getConn(j).setWeight(newWeight);
            }
        }

        for(int i = 0; i < hiddenNeurons2.size(); i++) {
            for (int j = 0; j < hiddenNeurons.size(); j++) {
                double Wo = hiddenNeurons2.get(i).getConnectionsBehind(j).getWeight();

                double secondComponent = sigmoid.getSigmoid(hiddenNeurons2.get(i).getZ()) * (1 -sigmoid.getSigmoid(hiddenNeurons2.get(i).getZ()));
                double thirdComponent = hiddenNeurons2.get(i).getConnectionsBehind(j).getBehind().getValue();

                double sumOfPreviousCost = 0;

                for(int k = 0; k < outputNeurons.size(); k++){
                    sumOfPreviousCost += hiddenNeurons2.get(i).getConnectionsFront(k).getCost();
                }

                double cost = sumOfPreviousCost * secondComponent * thirdComponent;
                hiddenNeurons2.get(i).getConnectionsBehind(j).setCost(cost);

                double newWeight = Wo - (learningRate * cost);
                hiddenNeurons2.get(i).getConnectionsBehind(j).setWeight(newWeight);

            }
        }

        for(int i = 0; i < hiddenNeurons.size(); i++) {
            for (int j = 0; j < inputNeurons.size(); j++) {
                double Wo = hiddenNeurons.get(i).getConnectionsBehind(j).getWeight();

                double secondComponent = sigmoid.getSigmoid(hiddenNeurons.get(i).getZ()) * (1 -sigmoid.getSigmoid(hiddenNeurons.get(i).getZ()));
                double thirdComponent = hiddenNeurons.get(i).getConnectionsBehind(j).getBehind().getValue();

                double sumOfPreviousCost = 0;

                for(int k = 0; k < hiddenNeurons2.size(); k ++){
                    sumOfPreviousCost += hiddenNeurons.get(i).getConnectionsFront(k).getCost();
                }

                double cost = sumOfPreviousCost * secondComponent * thirdComponent;
                double newWeight = Wo - (learningRate * cost);

                hiddenNeurons.get(i).getConnectionsBehind(j).setWeight(newWeight);
            }
        }
    }

    public void checkConnections(){
        for(int i = 0; i < hiddenNeurons2.size();i++) {
            for (int k = 0; k < outputNeurons.size(); k++) {
                System.out.println(hiddenNeurons2.get(i).getConnectionsFront(k).getCost());
            }
        }
    }

    public void propagateForward(){
        Sigmoid sigmoid = new Sigmoid();

        for(int i = 0;i < hiddenNeurons.size(); i ++){
            double sum = 0;

            for(int j =0; j < inputNeurons.size(); j++) {
                double currentWeight = hiddenNeurons.get(i).getConnectionsBehind(j).getWeight();
                double inputValues = hiddenNeurons.get(i).getConnectionsBehind(j).getBehind().getValue();

                sum += (currentWeight * inputValues);
            }

            sum -= 1;

            double answer = sigmoid.getSigmoid(sum);

            hiddenNeurons.get(i).setValue(answer);

        }

        for(int i = 0;i < hiddenNeurons2.size(); i ++){
            double sum = 0;

            for(int j =0; j < hiddenNeurons.size(); j++) {
                double currentWeight = hiddenNeurons2.get(i).getConnectionsBehind(j).getWeight();
                double inputValues = hiddenNeurons2.get(i).getConnectionsBehind(j).getBehind().getValue();
                sum += currentWeight * inputValues;
            }

            sum -= 1;
            double answer = sigmoid.getSigmoid(sum);
            hiddenNeurons2.get(i).setZ(sum);
            hiddenNeurons2.get(i).setValue(answer);
        }

        for(int i = 0;i < outputNeurons.size(); i ++){
            double sum = 0;

            for(int j =0; j < hiddenNeurons2.size(); j++) {
                double currentWeight = outputNeurons.get(i).getConn(j).getWeight();
                double inputValues = outputNeurons.get(i).getConn(j).getBehind().getValue();
                sum += currentWeight * inputValues;
            }

            sum -= 1;
            double answer = sigmoid.getSigmoid(sum);
            outputNeurons.get(i).setZ(sum);
            outputNeurons.get(i).setValue(answer);
        }
    }

    public double printGuess(){
        Iterator<OutputNeuron> out = outputNeurons.iterator();

        double max = 0;
        int index = 0;
        for(int i = 0; out.hasNext(); i++){
            double current = out.next().getValue();
            if(current > max){
                current = max;
                index = i;
            }
        }

        for(int i =0; i< outputNeurons.size();i ++){
            if(i == index){
                outputNeurons.get(i).setExpected(1);
            }
            else{
                outputNeurons.get(i).setExpected(0);
            }
        }

        return index;
    }

    public void printInputNeurons(){
        Iterator<InputNeuron> iterable = inputNeurons.iterator();

        while(iterable.hasNext()){
            System.out.println(iterable.next().getValue());

        }
    }

    public void printHiddenNeurons(){
        Iterator<HiddenNeuron> iterable = hiddenNeurons.iterator();

        while(iterable.hasNext()){
            System.out.println(iterable.next().getValue());

        }
    }

    public double randomWeight(){
        double sign = Math.random();

        if(sign <= 0.5){
            sign = -1;
        }
        else{
            sign = 1;
        }

        return sign * round(Math.random(),3);
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(String.valueOf(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
