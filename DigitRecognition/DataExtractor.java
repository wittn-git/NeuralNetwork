package DigitRecognition;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import BaseLibrary.Datapoint;

public class DataExtractor {

    private int currentLabel, currentImage;
    //byte arrays with all images and labels
    byte[] labels, images;
    //preset array filled with zeros
    float[] output_labels;

    //reading the bytearrays from the mnist dataset files
    public DataExtractor(String image_path, String label_path){
        currentImage = 0;
        currentLabel = 8;
        try {
            images = Files.readAllBytes(Paths.get(image_path));
            labels = Files.readAllBytes(Paths.get(label_path));
        } catch (IOException e) {
            e.printStackTrace();
        }   
        //fill outputlabel array with zeros
        output_labels = new float[10];
        IntStream.iterate(1, n -> n + 1).limit(9).forEach(i -> output_labels[i] = 0);
    }

    public Datapoint[] getData(){
        List<Datapoint> list = new LinkedList<Datapoint>();
        while(true){
            float[] label = nextLabel();
            byte[] image = nextImage();
            if(image == null){
                break;
            }
            float[] input = DataExtractor.convertByteToFloat(image);
            list.add(new Datapoint(input, label));
        }
        return list.toArray(new Datapoint[0]);
    }

    public float[] nextLabel(){
        if(currentLabel>=labels.length){
            return null;
        }
        byte label = labels[currentLabel];
        currentLabel++;
        //put label to one at correct position
        float[] output_labels = Arrays.copyOf(this.output_labels, 10);
        output_labels[label] = 1;
        return output_labels;
    }

    public byte[] nextImage(){
        if(currentImage*(28*28)+16>=images.length){
            return null;
        }
        byte[] image = Arrays.copyOfRange(images, currentImage*(28*28)+16, (currentImage+1)*(28*28)+16); 
        currentImage++;
        return image;
    }

    public static float[] convertByteToFloat(byte[] input){
        float[] output = new float[input.length];
        for (int i = 0; i < input.length; i++){
            output[i] = input[i];
        }
        return output;
    }
    
}