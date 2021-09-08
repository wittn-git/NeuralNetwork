package DigitRecognition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;  

public class Gui {

    JFrame frame;
    DrawingPanel panel;

    //init frame and drawingpanel
    public Gui(){
        frame = new JFrame("DigitRecoginition");
        panel = new DrawingPanel();
    }

    //set frame properties and show the frame
    public void show(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }  

    //update the drawing panel with new image
    public void update(byte[] pixels){
        panel.update(pixels);
    }

}

class DrawingPanel extends JPanel{

    int pixelsize = 20;
    byte[] pixels;
    int imagesize = 28;

    public DrawingPanel() {
        setBorder(BorderFactory.createLineBorder(Color.black));
        this.pixels = null;
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(560,560);
    }
    
    public void update(byte[] pixels){
        this.pixels = pixels;
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);  
        if(pixels != null){
            for(int i=0; i<pixels.length; i++){
                g.setColor(new Color(pixels[i]));
                int x = i%28;
                int y = (int) (i/28);
                g.fillRect(x*pixelsize, y*pixelsize, pixelsize, pixelsize);
            }
        }  
    }  

}