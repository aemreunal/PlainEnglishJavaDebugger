package plainenglishjavadebugger.translationModule.fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class JavaClassReader {
	private String filePath = "";
	
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	
	public String readLine(String filePath, int lineNumber) {
		String processedFilePath = processFilePath(filePath);
		if (this.filePath != processedFilePath) {
			this.filePath = processedFilePath;
			openFileAndReader();
			return getLine(lineNumber);
		} else {
			resetReader();
			return getLine(lineNumber);
		}
	}
	
	private String processFilePath(String filePath) {
		return filePath.substring(2, filePath.length());
	}
	
	private void openFileAndReader() {
		try {
			if (fileReader != null) {
				closeFileAndReader();
			}
			fileReader = new FileReader(filePath);
			bufferedReader = new BufferedReader(fileReader);
		} catch (IOException ioException) {
			System.err.println("File not found or can't be opened!");
			System.exit(-1);
		}
	}
	
	private void resetReader() {
		try {
			bufferedReader.reset();
		} catch (IOException e) {
			System.out.println("Unable to reset the buffered reader stream!");
			e.printStackTrace();
		}
	}
	
	private String getLine(int lineNumber) {
		String line = "";
		for (int i = 0; i < lineNumber; i++) {
			try {
				line = bufferedReader.readLine();
			} catch (IOException e) {
				System.err.println("Unable to read the line from the buffered reader!");
				e.printStackTrace();
			}
		}
		return line;
	}
	
	private void closeFileAndReader() {
		try {
			bufferedReader.close();
			fileReader.close();
		} catch (IOException e) {
			System.err.println("Unable to close the input file reader!");
			e.printStackTrace();
		}
	}
}
