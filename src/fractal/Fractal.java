/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fractal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author DaTho7561
 */
public class Fractal extends JFrame {
    
    private int iteration;
    private int lastIteration;
    private int rotation;
    
    public Fractal() {
        super("Fractal");
        this.setSize(500, 520);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        //this.setVisible(true);
        iteration = 1;
        lastIteration = 0;
        rotation = 0;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Fractal fractal = new Fractal();
        Scanner sc = new Scanner(System.in);
        
        int numberIterations = sc.nextInt();
        fractal.setVisible(true);
        
        for (int i = 0; i < numberIterations; i++) {
            fractal.iterate();
        }
        Timer t = new Timer(20, (ActionEvent e) -> {
            fractal.repaint();
        });
        
        t.start();
        
    }
    
    /**
     * <b>Bleh.png</b>
     * @param g 
     */
    @Override
    public void paint(Graphics g) {
        
        // Sets up a new image for double buffering
        Image base = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
        
        // Graphics 2D for custom stroke for drawing shapes
        Graphics2D l = (Graphics2D) base.getGraphics();
        
        // Fill the background with black
        l.setStroke(new BasicStroke(5));
        l.setColor(Color.black);
        l.fillRect(0, 0, 500, 500);
        
        // For each iteration that is required
        for (int i = 0; i < iteration; i++) {
            l.setColor(new Color((int)(125*Math.sin(rotation/1000)+125),(int)(125*Math.sin(-rotation/1000)+125),255));
            l.drawOval(5, 5, 490, 490);
            l.drawOval(5, 5, 85, 85);
            l.drawOval(500 - 5 - 85, 5, 85, 85);
            l.drawOval(5, 500 - 5 - 85, 85, 85);
            l.drawOval(500 - 5 - 85, 500 - 5 - 85, 85, 85);
            l.drawRect(5, 5, 490, 490);
            int SUBIMAGE_SIZE = 350;
            BufferedImage subimage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            subimage.getGraphics().drawImage(base.getScaledInstance(SUBIMAGE_SIZE,
                    SUBIMAGE_SIZE, Image.SCALE_SMOOTH), (500 - SUBIMAGE_SIZE) / 2,
                    (500 - SUBIMAGE_SIZE) / 2, null);
            AffineTransform trns = AffineTransform.getRotateInstance(Math.toRadians(rotation),
                    250, 250);
            AffineTransformOp rg = new AffineTransformOp(trns, AffineTransformOp.TYPE_BILINEAR);
            subimage = rg.filter(subimage, null);
            l.drawImage(subimage, 0, 0, null);
        }
        
        g.drawImage(base, 0, 20, null);
        rotation = (rotation + 1 > 360)? 0: rotation + 1;
    }
    
    public void iterate() {
        iteration++;
        this.repaint();
    }
    
}
