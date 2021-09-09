package BaseLibrary;

public interface Function {
    abstract float f(float x);   
}
    
//sigmoid function used as the activation function
class Sigmoid implements Function{
    public float f(float x){
        return (float) (1 / (1+Math.exp(-x)));
    }
}

//deriviation of sigmoid
//as this is used for the outputvector and the hiddenoutput, to both of which sig as been applied, the sig(x) part of the derivation has been omitted
class DerivatedSigmoid implements Function{
    public float f(float x){
        Sigmoid sig = new Sigmoid();
        return x * (1-sig.f(x));
    }
}