let nn;

function setup() {
  createCanvas(500, 500);

  let sigmoid = new ActivationFunction(
    x => 1 / (1 + Math.exp(-x)),
    y => y * (1 - y)
  );
  
  nn = new NeuralNetwork(2, 4, 1, 0.05, [-1, 1], sigmoid);
  
  noStroke();
  frameRate(5);
}

function f(x,y){
  if(x>5 && y>5){
    return 0;
  }
  return 1;
}

function draw() {

  background(220);
  for(let i = 0; i<100; i++){
    let x = random(0, 10);
    let y = random(0, 10);
    nn.train([x,y], [f(x,y)]);
  }

  for(let i=0; i<100; i++){
    for(let j=0; j<100; j++){
      let x = map(i, 0, 100, 0, 10);
      let y = map(j, 0, 100, 0, 10);
      let result = nn.predict([x,y]);
      fill(255*result[0]);
      rect(i*5, j*5, 5, 5);
    }
  }

}
