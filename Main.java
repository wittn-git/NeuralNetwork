import java.util.Random;

public class Main {
    public static void main(String[] args) {

        NeuralNetwork nn = new NeuralNetwork(2, 1, 1, 0.1f);

        Random random = new Random();
        Datapoint[] trainingData = new Datapoint[250];
        for(int i=0; i<trainingData.length; i++){
            int x = random.nextInt(2);
            int y = random.nextInt(2);
            trainingData[i] = new Datapoint(new float[]{x, y}, new float[]{f(x, y)});
        }   

        Datapoint[] testData = new Datapoint[50];
        for(int i=0; i<testData.length; i++){
            int x = random.nextInt(2);
            int y = random.nextInt(2);
            testData[i] = new Datapoint(new float[]{x, y}, new float[]{f(x, y)});
        }   

        nn.train(100, trainingData, testData);
        
    }

    public static int f(int x, int y){
        if((x == 1 && y == 1)){
            return 1;
        }
        return 0;
    }

}