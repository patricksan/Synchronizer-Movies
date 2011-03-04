package be.digitalbio.movies.reader.list;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import be.digitalbio.movies.reader.bean.Movie;
import be.digitalbio.movies.reader.util.FileLocalization;

public class ListManipulation {
	private List<Movie> listMovies;

	private static ListManipulation instance = null;

	public static ListManipulation getInstance() {
		if (instance == null) {
			instance = new ListManipulation();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void readSavedList() {
		XMLDecoder xmlDecoder = null;
		try {
			xmlDecoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(FileLocalization.LIST_FILE)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("File " + FileLocalization.LIST_FILE + " does not exist");
			System.exit(-1);
		}
		listMovies = (List) xmlDecoder.readObject();
		xmlDecoder.close();
	}

	public void saveList() {
		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(FileLocalization.LIST_FILE)));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("File " + FileLocalization.LIST_FILE + " does not exist");
			System.exit(-1);
		}
		xmlEncoder.writeObject(listMovies);
		xmlEncoder.close();
	}

	/**
	 * @return the listMovies
	 */
	public List<Movie> getListMovies() {
		return listMovies;
	}

	/**
	 * @param listMovies
	 *            the listMovies to set
	 */
	public void setListMovies(List<Movie> listMovies) {
		this.listMovies = listMovies;
	}

	public List<Movie> saveListNoDuplicated(List<Movie> pListMovie) {
		List<Movie> lstNewMovies = new ArrayList<Movie>();
		int iResult = 0;
		/** get the actual list save in disk */
		this.readSavedList();

		for (Movie movie : pListMovie) {
			if (!verifyIfItIsInside(movie)) {
				/** add to the local list */
				lstNewMovies.add(movie);
				iResult++;
			}
		}

		if (iResult > 0) {
			listMovies.addAll(lstNewMovies);
			/** save it */
			saveList();
		}

		return lstNewMovies;
	}

	private boolean verifyIfItIsInside(Movie movie) {
		for (Movie obj : listMovies) {
			if (obj.getFileName().equals(movie.getFileName()))
				return true;
		}

		return false;
	}
}
