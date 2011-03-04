package be.digitalbio.movies.reader.util;

import java.io.File;

/**
 * <b>Project: </b>MoviesReader<br>
 * <B>Description: </B>Path to all files<br>
 * 
 * @author <a href=mailto:psantana@vangenechten.com>Patrick Santana</a><br>
 *         Created: Feb 23, 2009 4:16:17 PM
 */
public final class FileLocalization {

	public static final String CONFIG_FILE = System.getProperty("user.home") + File.separator + ".moviesReader" + File.separator
			+ "config.movies.reader.xml";
	public static final String LIST_FILE = System.getProperty("user.home") + File.separator + ".moviesReader" + File.separator
			+ "list.movies.xml";
}
