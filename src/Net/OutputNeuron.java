package Net;

import java.util.ArrayList;

public class OutputNeuron extends Neuron{
    private ArrayList<Connection> conns;

    private double value;
    private double expected;
    private double z;

    public OutputNeuron(){
        conns = new ArrayList<Connection>();
    }

    public OutputNeuron(double value){
        conns = new ArrayList<Connection>();
        this.value = value;
    }

    public double getValue(){
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public void setExpected(double expected){
        this.expected = expected;
    }

    public double getExpected(){
        return expected;
    }

    public void setZ(double z){
        this.z = z;
    }

    public double getZ(){
        return z;
    }

    public void addConnection(Connection conn){
        conns.add(conn);
    }

    public ArrayList<Connection> getConnectionList(){
        return conns;
    }

    public Connection getConn(int i){
        return conns.get(i);
    }


}
