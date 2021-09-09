package BaseLibrary;

import java.util.Arrays;

public class Datapoint {
    
    float[] inputs, labels;

    public Datapoint(float[] inputs, float[] labels){
        this.inputs = inputs;
        this.labels = labels;
    }

    public String toString(){
        return String.format("Inputs: %s | Label: %s", Arrays.toString(inputs), Arrays.toString(labels));
    }

}