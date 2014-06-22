import detection.Detector;

import java.awt.*;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String fileName = "lena.jpg";
        Detector detector = Detector.create("haarcascade_frontalface_default.xml");
        List<Rectangle> res = detector.getFaces(fileName, 1.2f,1.1f,.05f, 2,true);

        for (Rectangle r : res) {
            System.out.println("Face Found");
            System.out.println("X: " + r.getX());
            System.out.println("Y: " + r.getY());
        }
    }

}
