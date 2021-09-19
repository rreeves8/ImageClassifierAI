package Net;

public class Connection {
    private Neuron behind;
    private Neuron front;

    private double weight;
    private double cost;

    public Connection() {

    }

    public Connection(Neuron behind, Neuron front, double weight) {
        this.front = front;
        this.behind = behind;
        this.weight = weight;
    }

    public Neuron getBehind() {
        return behind;
    }

    public Neuron getFront() {
        return front;
    }

    public double getCost(){
        return cost;
    }

    public void setCost(double cost){
        this.cost = cost;
    }

    public void setBehind(Neuron behind) {
        this.behind = behind;
    }

    public void setFront(Neuron front) {
        this.front = front;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;

    }
}
