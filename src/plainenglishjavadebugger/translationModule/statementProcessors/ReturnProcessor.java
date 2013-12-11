package plainenglishjavadebugger.translationModule.statementProcessors;

import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.StatementType;
import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class ReturnProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/javaOO/returnvalue.html";
	
	public ReturnProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.RETURN, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		translatedLine.setLongDescription("This statement is a return statement.");
		int returnValueStartIndex = executedSourceLine.indexOf(' ');
		if (returnValueStartIndex != -1) {
			// There is a return value.
			processReturnWithValue(returnValueStartIndex);
		} else {
			// Just an empty return statement.
			processReturnWithoutValue();
		}
		translatedLine.appendLinkToLongDescription("return values", infoLink);
	}
	
	private void processReturnWithoutValue() {
		translatedLine.setShortDescription("You are returning to the calling method.");
		translatedLine.appendToLongDescription("This statement returns to the calling method.");
	}
	
	private void processReturnWithValue(int returnValueStartIndex) {
		// +1 to throw out the space at the beginning, -1 to throw out the semi-colon.
		String returned = executedSourceLine.substring(returnValueStartIndex + 1, (executedSourceLine.length() - 1));
		translatedLine.setShortDescription("You are returning \"" + returned + "\".");
		translatedLine.appendToLongDescription("This statement returns a value to the calling method, specifically \"" + returned + "\",");
		if (returned.matches(SourceCodeProcessor.javaNameRegex)) {
			getVariableValue(returned);
		}
	}
}
