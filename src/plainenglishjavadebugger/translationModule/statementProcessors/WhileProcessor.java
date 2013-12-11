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

public class WhileProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/nutsandbolts/while.html";
	
	public WhileProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.WHILE_LOOP, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		// +1 to remove the opening parenthesis.
		String condition = removeParantheses(executedSourceLine.substring(executedSourceLine.indexOf('(') + 1, executedSourceLine.lastIndexOf(')')));
		translatedLine.setShortDescription("You are now entering a while loop of structure \"while(" + condition + ")\".");
		translatedLine.setLongDescription("This statement is a while loop statement.");
		translatedLine.appendToLongDescription("\n\nWhile-loops allow you to repeat the execution of a block of code for a certain amount -");
		translatedLine.appendToLongDescription("more specifically, until the condition, in this case \"" + condition + "\",");
		if (isJavaName(condition)) {
			getVariableValue(condition);
		}
		translatedLine.appendToLongDescription("becomes false.");
		translatedLine.appendLinkToLongDescription("while-statements", infoLink);
	}
}
