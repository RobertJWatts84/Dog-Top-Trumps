import java.io.*;
import java.lang.String;

public class getDogs {
	public static String[][] dogsList() {

		String[][] dogs = new String[30][7];
		InputStream in = getDogs.class.getResourceAsStream("dogs.csv");
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
		//try (BufferedReader br = new BufferedReader(new FileReader("dogs.csv"))) {
			String line;
			for (int i = 0; i < 30; i++) {
				line = br.readLine();
				String[] values = line.split(",");
				dogs[i] = values;
			}
		}
		catch (IOException e) {
			//deal with exception
		}
		return(dogs);

	}
}