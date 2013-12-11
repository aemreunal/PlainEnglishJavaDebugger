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

public class IfProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/nutsandbolts/if.html";
	
	public IfProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.IF, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		// +1 to remove the opening parenthesis.
		String condition = executedSourceLine.substring(executedSourceLine.indexOf('(') + 1, executedSourceLine.lastIndexOf(')'));
		translatedLine.setShortDescription("You are now checking a condition.");
		translatedLine.setLongDescription("This statement is an if-statement.");
		translatedLine.appendToLongDescription("This statement checks a boolean condition, in this case \"" + condition + "\",");
		if (condition.matches(SourceCodeProcessor.javaNameRegex)) {
			getVariableValue(condition);
		}
		translatedLine.appendToLongDescription("to decide what part of the code to run.");
		translatedLine.appendLinkToLongDescription("if-statements", infoLink);
	}
}
