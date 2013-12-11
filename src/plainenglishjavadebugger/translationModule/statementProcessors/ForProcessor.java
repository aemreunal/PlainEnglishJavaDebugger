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

public class ForProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html";
	
	private int paramStartIndex;
	private int firstParamIndex;
	private int secondParamIndex;
	private int thirdParamIndex;
	
	private String firstParam;
	private String secondParam;
	private String thirdParam;
	
	private boolean firstParamExists;
	private boolean secondParamExists;
	private boolean thirdParamExists;
	
	public ForProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.FOR_LOOP, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		getParameterInfo();
		translatedLine.setShortDescription("You are now entering a for-loop of structure \"for (" + firstParam + "; " + secondParam + "; " + thirdParam + ")\".");
		translatedLine.setLongDescription("This statement is a for-loop statement.");
		appendParamInfo();
		translatedLine.appendToLongDescription("\n\nFor-loops allow you to repeat the execution of a block of code for a certain amount - more specifically, until the second parameter becomes false.");
		translatedLine.appendToLongDescription("The first parameter of a for-loop is usually the index initialization, executed only once, when the program enters the loop.");
		translatedLine.appendToLongDescription("The second parameter of a for-loop is the condition checked every time after 1) the program finishes executing the code block, 2) loops back to the beginning of the for-loop, 3) executes the third parameter.");
		translatedLine.appendToLongDescription("If this condition is true, then program starts executing the code block again.");
		translatedLine.appendToLongDescription("If this condition is false, the program exits the loop.");
		translatedLine.appendToLongDescription("The third parameter of a for-loop is usually the index increment, executed every time the program finishes executing the code block and loops back to the beginning of the for-loop.");
		translatedLine.appendLinkToLongDescription("for-loop statements", infoLink);
	}
	
	private void appendParamInfo() {
		if (firstParamExists) {
			translatedLine.appendToLongDescription("\n\nFirst parameter: \"" + firstParam + "\"");
		} else {
			translatedLine.appendToLongDescription("\n\nFirst parameter: <blank>");
		}
		
		if (secondParamExists) {
			translatedLine.appendToLongDescription("\nSecond parameter: \"" + secondParam + "\"");
		} else {
			translatedLine.appendToLongDescription("\nSecond parameter: <blank>");
		}
		
		if (thirdParamExists) {
			translatedLine.appendToLongDescription("\nThird parameter: \"" + thirdParam + "\"");
		} else {
			translatedLine.appendToLongDescription("\nThird parameter: blank.");
		}
	}
	
	private void getParameterInfo() {
		paramStartIndex = executedSourceLine.indexOf('(');
		
		firstParamIndex = getIndexForChar(paramStartIndex + 1, ';');
		secondParamIndex = getIndexForChar(firstParamIndex + 1, ';');
		thirdParamIndex = getIndexForChar(secondParamIndex + 1, ')');
		
		firstParam = removeParantheses(executedSourceLine.substring(paramStartIndex + 1, firstParamIndex).trim());
		secondParam = removeParantheses(executedSourceLine.substring(firstParamIndex + 1, secondParamIndex).trim());
		thirdParam = removeParantheses(executedSourceLine.substring(secondParamIndex + 1, thirdParamIndex).trim());
		
		firstParamExists = !(paramStartIndex + 1 == firstParamIndex || firstParam.matches(SourceCodeProcessor.whiteSpaceRegex));
		secondParamExists = !(firstParamIndex + 1 == secondParamIndex || secondParam.matches(SourceCodeProcessor.whiteSpaceRegex));
		thirdParamExists = !(secondParamIndex + 1 == thirdParamIndex || thirdParam.matches(SourceCodeProcessor.whiteSpaceRegex));
	}
}
