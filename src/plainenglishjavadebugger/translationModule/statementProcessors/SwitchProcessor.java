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

public class SwitchProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html";
	
	public SwitchProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.SWITCH, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		String valueToMatch = removeParantheses(executedSourceLine.substring(executedSourceLine.indexOf('(') + 1, executedSourceLine.lastIndexOf(')')));
		translatedLine.setShortDescription("You are now matching the value of \"" + valueToMatch + "\" with cases below.");
		translatedLine.setLongDescription("This statement is a switch-statement.");
		// +1 to remove the opening parenthesis.
		translatedLine.appendToLongDescription("This statement matches a value, in this case \"" + valueToMatch + "\",");
		if (isJavaName(valueToMatch)) {
			getVariableValue(valueToMatch);
		}
		translatedLine.appendToLongDescription("with cases below, to decide what part of the code to run.");
		translatedLine.appendLinkToLongDescription("switch-statements", infoLink);
	}
}
