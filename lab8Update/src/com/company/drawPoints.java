import com.company.Point;
import processing.core.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class drawPoints extends PApplet {


	public void settings() {
    size(500, 500);
	}
  
	public void setup() {
    	background(180);
    	noLoop();
  	}

  	public void draw() {

		double x, y;
		int z;

		String[] lines = loadStrings("positions.txt");

		println("there are " + lines.length);

		ArrayList<Point> listOfPoints = new ArrayList<>();

		for (int i=0; i < lines.length; i++){
			String[] words= lines[i].split(",");
			x = Double.parseDouble(words[0]);
			y = Double.parseDouble(words[1]);
			z = Integer.parseInt(words[2].replaceAll("\\s", ""));

			listOfPoints.add(new Point(x,y,z));
		}

		List<Point> result = listOfPoints
				.stream()
				.filter(pnt -> pnt.getZ() <= 2)
				.map(pnt -> pnt.multiplyPoint(0.5))
				.map(pnt -> pnt.translate(-150,-60))
				.collect(Collectors.toList());

		for(Point a : result) {
			ellipse((int) a.getX(), (int) a.getY(), 1, 1);
		}



  	}
	// Create a point class [list]
	// Stream and filter by z value and map [multiply 0.5], map again to translate [-150 for x , -37 for y]
	// Collect them into list and then draw points

  	public static void main(String args[]) {
      PApplet.main("drawPoints");
   }
}
