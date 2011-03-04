package be.digitalbio.movies.reader.config;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import be.digitalbio.movies.reader.util.FileLocalization;

public class ConfigurationManipulation {

		public static Config getConfig() {
		XMLDecoder d = null;
		try {
			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(FileLocalization.CONFIG_FILE)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File " + FileLocalization.CONFIG_FILE + " does not exist");
			System.exit(-1);
		}
		Config objConfiguration = (Config) d.readObject();
		d.close();

		/** return result */
		return objConfiguration;
	}

	public static void saveConfig(Config pConfig) {
		XMLEncoder e = null;
		try {
			e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FileLocalization.CONFIG_FILE)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("File " + FileLocalization.CONFIG_FILE + " does not exist");
			System.exit(-1);
		}
		e.writeObject(pConfig);
		e.close();
	}
}