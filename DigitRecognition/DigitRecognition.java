package DigitRecognition;

import java.util.Formatter;

import BaseLibrary.Datapoint;
import BaseLibrary.NeuralNetwork;

public class DigitRecognition {

    public static void main(String[] args) {

        NeuralNetwork neuralNetwork = new NeuralNetwork(28*28, 28, 10, 0.1f);

        DataExtractor dataExtractorTraining = new DataExtractor("data/mnist/train-images.idx3-ubyte", "data/mnist/train-labels.idx1-ubyte");
        Datapoint[] trainingData = dataExtractorTraining.getData();
        neuralNetwork.train(1, trainingData);

        Gui gui = new Gui();
        gui.show();
        DataExtractor dataExtractorTest = new DataExtractor("data/mnist/test-images.idx3-ubyte", "data/mnist/test-labels.idx1-ubyte");
        while(true){
            float[] label = dataExtractorTest.nextLabel();
            byte[] image = dataExtractorTest.nextImage();
            if(image == null){
                break;
            }
            float[] input = DataExtractor.convertByteToFloat(image);
            float[] prediction = neuralNetwork.feedforward(input);

            Formatter formatter = new Formatter(); 
            formatter.format("%10s %10s %10s \n", "L", "NN", "AC");  
            for(int i=0; i<10; i++){
                formatter.format("%10s %10s %10s \n", i, prediction[i], label[i]);  
            }

            System.out.println(formatter);
            gui.update(image);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            formatter.close();
            System.out.println();
        }
    }
    
}