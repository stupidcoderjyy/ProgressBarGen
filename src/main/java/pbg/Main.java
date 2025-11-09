package pbg;

import io.qt.widgets.QApplication;

public class Main {
    public static void main(String[] args) {
        QApplication.initialize(new String[0]);
        try {
            new Generator().run();
        } catch (Exception e) {
            System.out.println("Failed to start generator: " + e.getMessage());
        }
    }
}
