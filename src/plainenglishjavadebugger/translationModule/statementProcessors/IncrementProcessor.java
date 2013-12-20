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

public class IncrementProcessor extends StatementProcessor {
	private final String infoLink = "http://www.dummies.com/how-to/content/increment-and-decrement-operators-in-java.html";
	private String incrementedVariable;
	
	public IncrementProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.INCREMENT, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		incrementedVariable = removeParantheses(executedSourceLine.substring(0, executedSourceLine.indexOf("++")));
		translatedLine.setShortDescription("Incrementing " + incrementedVariable + ".");
		translatedLine.setLongDescription("This statement is an increment statement. This statement is incrementing the variable " + incrementedVariable + ",");
		getVariableValue(incrementedVariable);
		translatedLine.appendToLongDescription("by 1.");
		translatedLine.appendLinkToLongDescription("increment operations", infoLink);
	}
}
