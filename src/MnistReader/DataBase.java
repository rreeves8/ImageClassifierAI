package MnistReader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class DataBase {

    private MnistMatrix[] mnistMatrix;

    public DataBase() throws IOException {
        String restOfPath = "src\\MnistReader\\data\\";
        this.mnistMatrix = new MnistDataReader().readData(restOfPath + "train-images.idx3-ubyte", restOfPath + "train-labels.idx1-ubyte");
    }

    public MnistMatrix[] getMatrix(){
        return mnistMatrix;
    }

    public double size(){
        return mnistMatrix.length;
    }

    public Iterator getIterable(){
        Iterable<MnistMatrix> iterable = Arrays.asList(mnistMatrix);
        return iterable.iterator();
    }

}
