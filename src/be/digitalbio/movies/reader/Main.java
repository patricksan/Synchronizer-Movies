package be.digitalbio.movies.reader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;

import be.digitalbio.movies.reader.config.ConfigurationManipulation;

/**
 * <B>Project: </B>MoviesReader<br>
 * <B>Description: </B>ToDO<br>
 * 
 * @author <a href=mailto:psantana@moogu.com>Patrick Santana</a><br>
 *         Created: 28-apr-08 18:28:07
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		System.out.println("***********************************************************");
		System.out.println("* SYNCHRONIZATION BETWEEN LOCAL FOLDER AND FREECOM DEVICE *");
		System.out.println("***********************************************************");
		System.out.println("***********************************************************");

		Main objMain = new Main();

		/** folder for freecom */
		File objFolderFreecom = new File(ConfigurationManipulation.getConfig().getFolder());
		/** folder for local */
		File objLocalFolder = new File(ConfigurationManipulation.getConfig().getLocalFolder());
		/** all movies from freecom */
		List<File> lstMoviesFreecom = objMain.getFileListing(objFolderFreecom);

		if (objFolderFreecom.exists()) {
			System.out.println("* Local Folder: " + objLocalFolder);
			System.out.println("* Freecom Folder: " + objFolderFreecom);
			System.out.println("* How many movies: " + lstMoviesFreecom.size());

			/** make the synchronize from computer to freecom, if it is mounted */
			objMain.synchronizeHD();

			/** delete files that are not in the local folder */
			objMain.deleteFreecomFilesNotInLocalFolder();

			/** remove files that starts with ._* .DS_Store and others */
			objMain.removeUnnecessaryFilesFromFreecom();
			
			/** clean up Spotlight */
			objMain.finilizationFreecom();
			
		} else {
			System.out.println("Freecom not attached!");
		}
	}

	private void finilizationFreecom() throws IOException {
		System.out.println("Finalization of Freecom.");
		Runtime.getRuntime().exec("sudo rm -rf /Volumes/MOVIES/.Spotlight-V100/");
		Runtime.getRuntime().exec("sudo rm -rf /Volumes/MOVIES/.Trashes/");
		Runtime.getRuntime().exec("sudo rm -rf /Volumes/MOVIES/.fseventsd/");
		Runtime.getRuntime().exec("sudo rm -rf /Volumes/MOVIES/._._T*");
		Runtime.getRuntime().exec("sudo rm -rf /Volumes/MOVIES/.com.apple.timemachine.supported");
	}

	private void deleteFreecomFilesNotInLocalFolder() {
		/** Get Folder information */
		File objFolderFreecom = new File(ConfigurationManipulation.getConfig().getFolder());
		File objLocalFolder = new File(ConfigurationManipulation.getConfig().getLocalFolder());

		/** print information */
		System.out.println("Deleting files");
		System.out.println("Folder Freecom = " + objFolderFreecom);
		System.out.println("Local Folder = " + objLocalFolder);

		/** Read files from local folder */
		List<File> lstMoviesLocal = this.getFileListing(objLocalFolder);
		/** Read files from freecom */
		List<File> lstMoviesFreecom = this.getFileListing(objFolderFreecom);

		for (File objFileFreecom : lstMoviesFreecom) {
			boolean isFileLocal = false;
			for (File objLocalFile : lstMoviesLocal) {
				if (objFileFreecom.getName().equals(objLocalFile.getName())) {
					isFileLocal = true;
					break;
				}
			}

			if (!isFileLocal) {
				/**
				 * that means that this file in not on a local folder, so,
				 * remove it
				 */
				if (objFileFreecom.isFile())
					System.out.println("File [" + objFileFreecom + "] was deleted from Freecom. Result = " + FileUtils.deleteQuietly(objFileFreecom));
				else{
					 try {
						FileUtils.deleteDirectory(objFileFreecom);
						System.out.println("Folder [" + objFileFreecom + "] was deleted from Freecom.");
					} catch (IOException e) {
							e.printStackTrace();
					}		
				}
			}
		}
	}

	private void removeUnnecessaryFilesFromFreecom() {
		File objFolderFreecom = new File(ConfigurationManipulation.getConfig().getFolder());

		/** Read files from local folder */
		List<File> lstMovies = this.getFileListing(objFolderFreecom);

		for (File objFile : lstMovies) {
			/**
			 * Delete files not needed in the Freecom
			 */
			if (objFile.getName().equals(".DS_Store") || objFile.getName().startsWith("._")
					|| objFile.getName().startsWith(".Trash") || objFile.getName().startsWith(".Spotlight")
					|| objFile.getName().startsWith(".com.apple.timemachine.supported")
					|| objFile.getName().startsWith("Thumbs.db")) {
				System.out.println("File [" + objFile + "] will be deleted!");
				FileUtils.deleteQuietly(objFile);
			}
		}
	}

	public void synchronizeHD() throws IOException {
		/** Get Folder information */
		File objFolderFreecom = new File(ConfigurationManipulation.getConfig().getFolder());
		File objLocalFolder = new File(ConfigurationManipulation.getConfig().getLocalFolder());

		/** print information */
		System.out.println("Folder Freecom = " + objFolderFreecom);
		System.out.println("Local Folder = " + objLocalFolder);

		/** Read files from local folder */
		List<File> lstMovies = this.getFileListing(objLocalFolder);

		/** replace the local folder; just keep the genre folder and movie name */
		List<String> strLocalMvies = getCorrectNameWithFolder(lstMovies, objLocalFolder);

		/** synchronize */
		synchronize(strLocalMvies, objFolderFreecom.getAbsolutePath(), objLocalFolder.getAbsolutePath());
	}

	private void synchronize(List<String> pLocalMvies, String pFolderFreecom, String pFolderLocal) throws IOException {
		/** validates if ends with / */
		if (!pFolderFreecom.endsWith(File.separator)) {
			pFolderFreecom = pFolderFreecom + File.separator;
		}

		if (!pFolderLocal.endsWith(File.separator)) {
			pFolderLocal = pFolderLocal + File.separator;
		}
		
		for (String local : pLocalMvies) {
			/** files to ignore */
			if (local.endsWith(".DS_Store") || local.contains("Private")) {
				/** ignore */
				continue;
			}

			/** exist in the freecom */
			String fileFreecom = pFolderFreecom + local;
			if (!(new File(fileFreecom).exists())) {
				System.out.println("File [" + fileFreecom + "] does not exist, let's copy");
				/** copy */
				FileUtils.copyFile(new File(pFolderLocal + local), new File(fileFreecom));
			}
		}
	}

	private List<String> getCorrectNameWithFolder(List<File> pMovies, File pPathToRemove) {
		List<String> lstResult = new ArrayList<String>();
		String strToReplace = pPathToRemove.getAbsolutePath();

		/** validates if ends with / */
		if (!strToReplace.endsWith(File.separator)) {
			strToReplace = strToReplace + File.separator;
		}

		for (File file : pMovies) {
			lstResult.add(file.getAbsolutePath().replaceAll(strToReplace, ""));
		}

		return lstResult;
	}

	/**
	 * Recursively walk a directory tree and return a List of all Files found;
	 * the List is sorted using File.compareTo.
	 * 
	 * @param pFolder
	 *            is a valid directory, which can be read.
	 */
	public List<File> getFileListing(File pFolder) {
		List<File> lstResult = new ArrayList<File>();

		File[] objFilesAndFolders = pFolder.listFiles();
		List<File> objFilesDirs = Arrays.asList(objFilesAndFolders);
		for (File file : objFilesDirs) {
			if (file.isFile()) {
				/** if it is a file, add to the list */
				lstResult.add(file);
			} else {
				/** if it is a folder, read the content */
				List<File> deeperList = getFileListing(file);
				lstResult.addAll(deeperList);
			}

		}
		/** order and return the result */
		Collections.sort(lstResult);
		return lstResult;
	}
}
