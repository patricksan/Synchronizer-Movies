package be.digitalbio.movies.reader.bean;

public class Movie implements Comparable<Movie> {

	private int id;
	private String name;
	private String fileName;
	private String genre;
	private String imdb;
	private int year;
	private boolean watched;

	public Movie() {
	}

	public Movie(int id, String name, String fileName, String genre, String imdb, int year, boolean watched) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.genre = genre;
		this.imdb = imdb;
		this.year = year;
		this.watched = watched;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the watched
	 */
	public boolean isWatched() {
		return watched;
	}

	/**
	 * @param watched
	 *            the watched to set
	 */
	public void setWatched(boolean watched) {
		this.watched = watched;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre
	 *            the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the imdb
	 */
	public String getImdb() {
		return imdb;
	}

	/**
	 * @param imdb
	 *            the imdb to set
	 */
	public void setImdb(String imdb) {
		this.imdb = imdb;
	}

	@Override
	public String toString() {
		StringBuffer objBuffer = new StringBuffer();

		objBuffer.append("[id=").append(id);
		objBuffer.append(", name=").append(name);
		objBuffer.append(", fileName=").append(fileName);
		objBuffer.append(", genre=").append(genre);
		objBuffer.append(", imdb=").append(imdb);
		objBuffer.append(", year=").append(year);
		objBuffer.append(", watched=").append(watched).append("]");

		return objBuffer.toString();
	}

	@Override
	public int compareTo(Movie o) {
		return this.getFileName().compareTo(o.getFileName());
	}
}
