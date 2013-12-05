package plainenglishjavadebugger.translationModule.statementProcessors;

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
	
	public IfProcessor(TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.IF, executedSourceLine, translatedLine);
		process();
	}
	
	@Override
	protected void process() {
		translatedLine.setShortDescription("You are now checking a condition.");
		translatedLine.setLongDescription("This statement is an if-statement.");
		// +1 to remove the opening parenthesis.
		String condition = executedSourceLine.substring(executedSourceLine.indexOf('(') + 1, executedSourceLine.lastIndexOf(')'));
		translatedLine.appendToLongDescription("This statement checks a boolean condition, in this case \"" + condition + "\", to decide what part of the code to run.");
		translatedLine.appendLinkToLongDescription("if-statements", infoLink);
	}
}
