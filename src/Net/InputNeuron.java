package Net;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class InputNeuron extends Neuron {
    private ArrayList<Connection> conns;

    private double value;

    public InputNeuron() {
        conns = new ArrayList<Connection>();
    }

    public InputNeuron(double value) {
        conns = new ArrayList<Connection>();
        this.value = value;
    }

    public double getValue() {
        return value;
    }


    public void setValue(double value) {
        this.value = value;
    }

    public void addConnection(Connection conn) {
        conns.add(conn);
    }

    public ArrayList<Connection> getConnections() {
        return conns;
    }

    public Connection getConnectionIndex(int i) {
        return conns.get(i);
    }

    public Neuron getNextNeuronIndex(int i) {
        return conns.get(i).getFront();
    }

}
