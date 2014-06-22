import com.desple.controller.ImageProcessing;
import com.desple.model.Detector;
import com.desple.model.image_processing.Grayscale;
import com.desple.view.ShowImage;

public class Main {
    private static final String CASCADE_LOCATION = "haar_cascades/haarcascade_frontalface_default.xml";

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                _createAndShowGUI();
            }
        });
    }

    private static void _createAndShowGUI() {
        Detector model = new Detector(CASCADE_LOCATION);
        ShowImage view = new ShowImage(model);
        ImageProcessing controller = new ImageProcessing(view, model);
    }
}
