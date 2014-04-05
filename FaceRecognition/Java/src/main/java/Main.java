import com.desple.view.ShowImage;

public class Main {
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
        new ShowImage();
    }
}
