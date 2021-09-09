class NeuralNetwork{

    constructor(input_nodes, hidden_nodes, output_nodes, learning_rate, startingbounds, activation_function) {
        
        this.learning_rate = learning_rate;
        
        //init weights and biases with random values
        this.weights_IH = new Matrix(hidden_nodes, input_nodes);
        this.weights_HO = new Matrix(output_nodes, hidden_nodes);
        this.weights_IH.randomize(startingbounds[0], startingbounds[1]);
        this.weights_HO.randomize(startingbounds[0], startingbounds[1]);

        this.bias_H = new Matrix(hidden_nodes, 1);
        this.bias_O = new Matrix(output_nodes, 1);
        this.bias_H.randomize(startingbounds[0], startingbounds[1]);
        this.bias_O.randomize(startingbounds[0], startingbounds[1]);

        this.activation_function = activation_function;    
    }

    //networks processes output for given input
    predict(input_array){

        //convert input array to vector
        let input = new Matrix(0, 0);
        input.fromArray(input_array);

        //generate hidden outputs
        let hidden_matrix = Matrix.multiply(this.weights_IH, input);
        hidden_matrix.add(this.bias_H);
        hidden_matrix.map(this.activation_function.func);

        //generate actual outputs
        let output_matrix = Matrix.multiply(this.weights_HO, hidden_matrix);
        output_matrix.add(this.bias_O);
        output_matrix.map(this.activation_function.func);

        let outputs = output_matrix.toArray();
        return outputs;
    }

    train(input_array, label_array){

        //get prediction for input from network
        let prediction_array = this.predict(input_array);
        let prediction = new Matrix(0, 0);
        prediction.fromArray(prediction_array);

        //convert label array into vector
        let label = new Matrix(0, 0);
        label.fromArray(label_array);

        //convert input array to vector
        let input = new Matrix(0, 0);
        input.fromArray(input_array);

        //get needed matrices: hidden matrix results (+ transposed), transposed weights, transposed input
        let hidden_matrix = Matrix.multiply(this.weights_IH, input);
        hidden_matrix.add(this.bias_H);
        hidden_matrix.map(this.activation_function.func);

        let input_t = Matrix.transpose(input);
        let hidden_matrix_t = Matrix.transpose(hidden_matrix);
        let weights_HO_t = Matrix.transpose(this.weights_HO);

        //get error vectors: errors proportioned by weights
        let output_error = Matrix.sub(label, prediction);
        let hidden_error = Matrix.multiply(weights_HO_t, output_error);

        //calculate gradients: 
        //learningrate *(scalar) errorvector *(hadamard) derivated activation function of output
        let output_gradients = Matrix.map(this.activation_function.dfunc, prediction);
        output_gradients.hadamard(output_error);
        output_gradients.scale(this.learning_rate);

        let hidden_gradients = Matrix.map(this.activation_function.dfunc, hidden_matrix);
        hidden_gradients.hadamard(hidden_error);
        hidden_gradients.scale(this.learning_rate);

        //calculate weight-deltas: gradients *(matrix multiplication) input ^ T
        let delta_weights_HO = Matrix.multiply(output_gradients, hidden_matrix_t);
        let delta_weights_IH = Matrix.multiply(hidden_gradients, input_t);

        //add gradients to biases and deltas to weights
        this.weights_HO.add(delta_weights_HO);
        this.bias_O.add(output_gradients);
        this.weights_IH.add(delta_weights_IH);
        this.bias_H.add(hidden_gradients);

        let errorrate = sqrt(output_error.absvalue())+sqrt(hidden_error.absvalue());
        return [prediction_array, errorrate];
    }   

}