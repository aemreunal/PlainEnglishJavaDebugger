package plainenglishjavadebugger.translationModule.statementProcessors;

import java.util.ArrayList;

import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.StatementType;
import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class MethodCallProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/javaOO/methods.html";
	
	private final ArrayList<String> args;
	
	private String assignedTo = "";
	private String calledMethod = "";
	
	public MethodCallProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.METHOD_CALL, thread, translatedLine, executedSourceLine);
		args = new ArrayList<String>();
		process();
	}
	
	@Override
	protected void process() {
		processCodeLine();
		translatedLine.setShortDescription("Calling the method: \"" + calledMethod + "\".");
		translatedLine.setLongDescription("This is a method call statement.");
		translatedLine.appendToLongDescription("This statement lets you call other methods so that they can perform their functions.");
		translatedLine.appendToLongDescription("A number of arguments can be passed to the methods while calling them.");
		appendParamInfo();
		appendAssignmentInfo();
		translatedLine.appendLinkToLongDescription("for-loop statements", infoLink);
	}
	
	private void processCodeLine() {
		// Assignment processing
		int assignmentOpIndex = getIndexForChar(0, '=');
		if (assignmentOpIndex != -1) {
			/* There is an assignment */
			assignedTo = (executedSourceLine.substring(0, assignmentOpIndex)).trim();
		}
		
		// Method call processing
		int methodCallIndex = assignmentOpIndex + 1;
		calledMethod = (executedSourceLine.substring(methodCallIndex, executedSourceLine.lastIndexOf(';'))).trim();
		
		// Argument processing
		getArgumentInfo(methodCallIndex);
	}
	
	private void getArgumentInfo(int methodCallStartIndex) {
		int paramStartIndex = executedSourceLine.indexOf('(', methodCallStartIndex);
		if (executedSourceLine.indexOf('(', paramStartIndex) != -1) {
			// Removing type casts
			paramStartIndex = executedSourceLine.indexOf('(', paramStartIndex);
		}
		int lastParantheses = executedSourceLine.lastIndexOf(')');
		while (true) {
			int nextComma = getIndexForChar(paramStartIndex + 1, ',');
			if (nextComma == -1) {
				// There is no comma
				String argument = executedSourceLine.substring(paramStartIndex + 1, lastParantheses);
				if (argument.matches(SourceCodeProcessor.whiteSpaceRegex) || argument.isEmpty()) {
					// No argument, do nothing
				} else {
					args.add(argument);
				}
				break;
			} else {
				String argument = executedSourceLine.substring(paramStartIndex + 1, nextComma);
				args.add(argument);
				paramStartIndex = nextComma;
			}
		}
	}
	
	private void appendParamInfo() {
		if (args.size() != 0) {
			translatedLine.appendToLongDescription("\n\nIn this method call, the following argument(s) are passed:\n");
			for (int i = 0; i < args.size(); i++) {
				String arg = removeParantheses(args.get(i).trim());
				translatedLine.appendToLongDescription("\n" + (i + 1) + ") " + arg + " (" + getSnippetType(arg) + ")");
			}
		}
	}
	
	private void appendAssignmentInfo() {
		if (!assignedTo.isEmpty()) {
			translatedLine.appendToLongDescription("\n\nThe return value is assigned to \"" + assignedTo + "\".");
		}
	}
}
