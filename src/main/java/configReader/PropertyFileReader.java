package configReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.testng.reporters.jq.Main;

public class PropertyFileReader {
	private static Properties prop;
	private static InputStream input;

	public PropertyFileReader() {
		try {
			prop = new Properties();
			input = new FileInputStream(".\\Config.properties");
			prop.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static String getConfigval(String key) {
		try {
			return prop.getProperty(key);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		String s = new PropertyFileReader().getConfigval("DriverName");
		System.out.println(s);
	}
}
