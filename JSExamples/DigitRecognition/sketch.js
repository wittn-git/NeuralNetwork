let nn;
let setN;
let images, labels;
let resolution;
let training_interval, begin_training;

function setup() {

  resolution = 15;

  createCanvas(28*resolution*2, 28*resolution);

  let sigmoid = new ActivationFunction(
    x => 1 / (1 + Math.exp(-x)),
    y => y * (1 - y)
  );
  
  nn = new NeuralNetwork(28*28, 28*28, 10, 0.1, [-1, 1], sigmoid);
  setN = 0;
  training_interval = 100;
  labels = labels.bytes;
  images = images.bytes;
  
  frameRate(5);
}

function preload() {
  images = loadBytes('../../data/mnist/train-images.idx3-ubyte');
  labels = loadBytes('../../data/mnist/train-labels.idx1-ubyte');
}

function getNext(){
  let label_array = Array(10).fill(0);
  let index = labels[8+setN];
  label_array[index] = 1;

  image = images.subarray(16+setN*28*28, 16+setN*28*28+28*28);

  setN += 1;
  return [image, label_array];
}

function draw() {
  background(200);

  for(let i=0; i<training_interval; i++){
    let data = getNext();
    let image = data[0];
    let label = data[1];
    nn.train(image, label);
    console.log(setN.toString(),"/",(labels.length-9).toString());
  }

  let data = getNext();
  let image = data[0];
  let label = data[1];

  for(let i=0; i<28*28; i++){
    fill(image[i]);
    let row = parseInt(i/28);
    let col = i%28;
    rect(col*resolution, row*resolution, resolution, resolution);
  }

  result = nn.train(image, label)[0];
  for(let i=0; i<10; i++){
    if(result[i] < 0.000001){
      result[i] = 0
    }
  }

  fill(0);
  textSize(22);
  text("L:   AC   | NN", 20+28*resolution, 40);
  for(let i=0; i<10; i++){
    text(i+":   "+label[i]+"      | "+round(result[i],7), 20+28*resolution, 40+35*(i+1));
  }

}