package JavaLibrary;

import java.util.Arrays;

public class Datapoint {
    
    float[] inputs;
    float[] labels;

    public Datapoint(float[] inputs, float[] labels){
        this.inputs = inputs;
        this.labels = labels;
    }

    public String toString(){
        return String.format("Inputs: %s | Label: %s", Arrays.toString(inputs), Arrays.toString(labels));
    }

    public float[] getInputs(){
        return inputs;
    }

    public float[] getLabels(){
        return labels;
    }

}