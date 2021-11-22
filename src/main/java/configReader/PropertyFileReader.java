package configReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
	private static Properties properties;
	private static InputStream input;

	public PropertyFileReader() {
		try {
			properties = new Properties();
			input = new FileInputStream(".\\Config.properties");
			properties.load(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String getConfigval(String key) {
		try {
			return properties.getProperty(key);
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
