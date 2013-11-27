package plainenglishjavadebugger.translationModule.fileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.debug.core.model.IStackFrame;

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
	
	public String readLine(IStackFrame stackFrame, String filePath, int lineNumber) {
		String processedFilePath = processFilePath(stackFrame, filePath);
		if (this.filePath != processedFilePath) {
			this.filePath = processedFilePath;
			openFileAndReader();
			return getLine(lineNumber);
		} else {
			resetReader();
			return getLine(lineNumber);
		}
	}
	
	private String processFilePath(IStackFrame frame, String filePath) {
		String command = (frame.getLaunch().getProcesses())[0].getAttribute(org.eclipse.debug.core.model.IProcess.ATTR_CMDLINE);
		int workingDirBeginIndex = command.indexOf("-classpath ") + ("-classpath ".length());
		int workingDirEndIndex = command.lastIndexOf('/', command.indexOf(':', workingDirBeginIndex));
		return command.substring(workingDirBeginIndex, workingDirEndIndex) + filePath.substring(filePath.indexOf('/', 3));
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
		return line.trim();
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
