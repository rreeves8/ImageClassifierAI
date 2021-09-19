package MnistReader;

import GUI.ImagePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DataBaseTest {

    public static void main(String[] args) throws IOException {

        String restOfPath = "C:\\Users\\Magnus Reeves\\IdeaProjects\\ImageClassifier\\src\\MnistReader\\data\\";
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData(restOfPath + "train-images.idx3-ubyte", restOfPath + "train-labels.idx1-ubyte");
        //displayMatrix(mnistMatrix[10]);
        printMnistMatrix(mnistMatrix[10]);
    }

    private static void printMnistMatrix(final MnistMatrix matrix) {
        System.out.println("label: " + matrix.getLabel());
        for (int r = 0; r < matrix.getNumberOfRows(); r++ ) {
            for (int c = 0; c < matrix.getNumberOfColumns(); c++) {
                System.out.print(matrix.getValue(r, c) + " ");
            }
            System.out.println();
        }
    }

    private static void displayMatrix(final MnistMatrix matrix){
        BufferedImage image = new BufferedImage(matrix.getNumberOfColumns(), matrix.getNumberOfRows(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < matrix.getNumberOfRows(); y++) {
            for (int x = 0; x < matrix.getNumberOfColumns(); x++) {
                image.setRGB(x, y, (int)(254 * matrix.getValue(y, x)));
            }
        }

        File outputfile = new File("C:\\Users\\Magnus Reeves\\Desktop\\Training images\\image.jpg");
        try {
            ImageIO.write(image, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ImageIO.save(image, "png", new File("C:\\Users\\Magnus Reeves\\Desktop\\Training images"));
    }
}
