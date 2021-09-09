package JavaLibrary;

public class NeuralNetwork{

    private float learningRate;
    Matrix weigthsIH, weigthsHO, biasH, biasO;
    Function sig, derivSig;

    public NeuralNetwork(int inputNodes, int hiddenNodes, int outputNodes, float learningRate){
        this.learningRate = learningRate;

        //init weight- and bias-matrices with random values
        weigthsIH = new Matrix(hiddenNodes, inputNodes);
        weigthsIH.randomize(-1, 1);
        weigthsHO = new Matrix(outputNodes, hiddenNodes);
        weigthsHO.randomize(-1, 1);
        biasH = new Matrix(hiddenNodes, 1);
        biasH.randomize(-1, 1);
        biasO = new Matrix(outputNodes, 1);
        biasO.randomize(-1, 1);

        sig = new Sigmoid();
        derivSig = new DerivatedSigmoid();
    }

    //neural network process for given input, returns outputs (depended on number of outputs)
    public float[] feedforward(float[] inputs){

        //change input array into matrix
        Matrix inputVector = new Matrix(1, inputs.length);
        inputVector.toVectorColumn(inputs);

        //generate hidden outputs
        Matrix hiddenMatrix = Matrix.multiply(weigthsIH, inputVector);
        hiddenMatrix = Matrix.add(hiddenMatrix, biasH);
        hiddenMatrix = Matrix.map(hiddenMatrix, sig);

        //generate actual outputs
        Matrix outputMatrix = Matrix.multiply(weigthsHO, hiddenMatrix);
        outputMatrix = Matrix.add(outputMatrix, biasO);
        outputMatrix = Matrix.map(outputMatrix, sig);
       
        float[] outputs = outputMatrix.getColumn(0);
        return outputs;
    }

    public void train(int epoches, Datapoint[] trainingData){
        for(int i=0; i<epoches; i++){
            float errorsum = 0;
            for(Datapoint datapoint: trainingData){
            //for(int j=0; j<trainingData.length; j++){
                //trainPoint(trainingData[i].inputs, trainingData[i].labels); 
                errorsum += trainPoint(datapoint.inputs, datapoint.labels);
            }
            System.out.println(String.format("Epoche %d: %f errorrate", i, errorsum/trainingData.length));
        }
    }

    private float trainPoint(float[] inputs, float[] labels){
        //test current solution provided by network, convert into matrix
        float[] guess = feedforward(inputs);
        Matrix guessVector = new Matrix(1, guess.length);
        guessVector.toVectorColumn(guess);

        //convert labels into matrix
        Matrix labelVector = new Matrix(1, labels.length);
        labelVector.toVectorColumn(labels);

        //calculate output errors based on networks solution und actual solution
        Matrix outputErrorVector = Matrix.subtract(labelVector, guessVector);

        //calculate hidden errors based on the output errors (errors proportioned by the respective weights)
        //formula: weights^T * errorvector
        Matrix transposedWeights = Matrix.transpose(weigthsHO);
        Matrix hiddenErrorVector = Matrix.multiply(transposedWeights, outputErrorVector);

        //get the transposed inputvector and the transposed hiddenmatrix output
        Matrix inputVector = new Matrix(1, inputs.length);
        inputVector.toVectorColumn(inputs);
        Matrix transposedInputVector = Matrix.transpose(inputVector);
        Matrix hiddenMatrix = Matrix.multiply(weigthsIH, inputVector);
        hiddenMatrix = Matrix.add(hiddenMatrix, biasH);
        hiddenMatrix = Matrix.map(hiddenMatrix, sig);
        Matrix transposedHiddenMatrix = Matrix.transpose(hiddenMatrix);

        //calculate differences for the weights to be applied to correct the weights
        //formula : learningrate *(scalar) errorvector *(hadamard) derivated activation function of output *(matrix multiplication) inputvector^T
        //add the deltas to the weights
        //add the deltas without the transposed inputvectors to the biases to adjust those
        Matrix gradients = Matrix.map(guessVector, derivSig); 
        gradients = Matrix.hadamard_product(gradients, outputErrorVector);
        gradients = Matrix.scale(gradients, learningRate);
        Matrix delta_weightsHO = Matrix.multiply(gradients, transposedHiddenMatrix);
        biasO = Matrix.add(biasO, gradients);
        weigthsHO = Matrix.add(weigthsHO, delta_weightsHO);

        Matrix hiddenGradients = Matrix.map(hiddenMatrix, derivSig); 
        hiddenGradients = Matrix.hadamard_product(hiddenGradients, hiddenErrorVector);
        hiddenGradients = Matrix.scale(hiddenGradients, learningRate);
        Matrix delta_weightsIH = Matrix.multiply(hiddenGradients, transposedInputVector);
        biasH = Matrix.add(biasH, hiddenGradients);
        weigthsIH = Matrix.add(weigthsIH, delta_weightsIH);

        //return error rate probably
        return hiddenErrorVector.sumAbs()/hiddenErrorVector.m + outputErrorVector.sumAbs()/outputErrorVector.m;
    }

}