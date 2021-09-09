package JavaExamples;
import java.util.Arrays;
import java.util.Random;

import JavaLibrary.Datapoint;
import JavaLibrary.NeuralNetwork;

public class Main {
    public static void main(String[] args) {

        NeuralNetwork nn = new NeuralNetwork(2, 4, 1, 0.05f);

        Random r = new Random();   

        Datapoint[] trainingData = new Datapoint[1000];
        for(int i=0; i<trainingData.length; i++){
            trainingData[i] = getDatapoint(r);
        }   

        nn.train(2000, trainingData);

        Datapoint[] testData = new Datapoint[10];
        for(int i=0; i<testData.length; i++){
            testData[i] = getDatapoint(r);
        }
       
        System.out.println("Testing");
        for(Datapoint datapoint: testData){
            float[] prediction = nn.feedforward(datapoint.getInputs());
            System.out.println(String.format("Predicted: %s, correct: %s", Arrays.toString(prediction), Arrays.toString(datapoint.getLabels())));
            System.out.println(datapoint.toString());
            System.out.println();
        }
        
    }

    public static Datapoint getDatapoint(Random r){
        
        float x = r.nextInt(2);
        float y = r.nextInt(2);
        int result;
        if((x==0 && y==1) || (x==1 && y==0)){
            result = 1;
        }else{
            result = 0;
        }
        return new Datapoint(new float[]{x,y}, new float[]{result});
    }

}