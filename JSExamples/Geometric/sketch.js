let nn;

function setup() {
  createCanvas(1000, 500);

  let sigmoid = new ActivationFunction(
    x => 1 / (1 + Math.exp(-x)),
    y => y * (1 - y)
  );
  
  nn = new NeuralNetwork(2, 20, 1, 0.02, [-1, 1], sigmoid);
  
  noStroke();
  frameRate(5);
}

function f(x,y){
  if(x<10 && y <= 10){
    return 0;
  }
  if(x >= 10 && y <= 10){
    return 0.33;
  }
  if(x >= 10 && y > 10){
    return 0.66;
  }
  return 1;
}

function draw() {

  background(220);
  let errorrate = 0;
  for(let i = 0; i<1000; i++){
    let x = random(0, 20);
    let y = random(0, 20);
    let r = nn.train([x,y], [f(x,y)]);
    errorrate += r[1];
  }
  console.log(errorrate/1000);

  for(let i=0; i<100; i++){
    for(let j=0; j<100; j++){
      let x = map(i, 0, 100, 0, 20);
      let y = map(j, 0, 100, 0, 20);
      let result = nn.predict([x,y]);
      fill(255*result[0]);
      rect(i*5, j*5, 5, 5);
      fill(255*f(x,y));
      rect(width/2+i*5, j*5, 5, 5);
    }
  }

}
