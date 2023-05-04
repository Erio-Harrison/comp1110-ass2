package comp1110.ass2.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Resources {
    private class bamboo extends Rectangle {
        public bamboo(int x, int y, int size) {
            super(x, y, size/2, size);
            this.setFill(Color.YELLOW);
        }

    }
    private class stones extends Polygon {
        public stones(int x, int y, int size) {
            // Triangle
            super(x, y, size, size);
            this.setFill(Color.GREEN);
        }
    }

    private class water extends Circle {
        public water(int x, int y, int size) {
            super(x,y,size,Color.BROWN);
        }
    }

    private class statuette extends Rectangle {
        public statuette(int x, int y, int size) {
            super(x, y, size/2, size);
        }
    }

    private class coconut extends Circle {
        public coconut(int x, int y, int size) {
            super(x,y,size,Color.BROWN);
        }
    }

}
