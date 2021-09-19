package Net;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class HiddenNeuron extends Neuron {

    private ArrayList<Connection> connsBehind;
    private ArrayList<Connection> connsFront;
    private double value;
    private double z;

    public HiddenNeuron(){
        connsBehind = new ArrayList<Connection>();
        connsFront = new ArrayList<Connection>();
    }

    public HiddenNeuron(double value){
        connsBehind = new ArrayList<Connection>();
        connsFront = new ArrayList<Connection>();
        this.value = value;
    }

    public void addConnectionBehind(Connection conn){
        connsBehind.add(conn);
    }

    public void addConnectionInFront(Connection conn){
        connsFront.add(conn);
    }

    public void setZ(double z){
        this.z = z;
    }

    public double getZ(){
        return z;
    }

    public ArrayList<Connection> getConnectionsFront(){
        return connsFront;
    }

    public ArrayList<Connection> getConnectionsBehind(){
        return connsBehind;
    }

    public Connection getConnectionsFront(int index){
        return connsFront.get(index);
    }

    public Connection getConnectionsBehind(int index){
        return connsBehind.get(index);
    }

    public double getValue(){
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public boolean isLastInternal(){
        if (connsBehind.get(0).getBehind() instanceof InputNeuron) {
            return true;
        }else{
            return false;
        }
    }
}
