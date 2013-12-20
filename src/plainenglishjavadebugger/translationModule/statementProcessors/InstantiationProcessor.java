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

public class InstantiationProcessor extends StatementProcessor {
	private final String infoLink = "http://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html";
	
	private final ArrayList<String> args;
	
	private String assignedTo = "";
	private String instantiatedObject = "";
	
	public InstantiationProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.INSTANTIATION, thread, translatedLine, executedSourceLine);
		args = new ArrayList<String>();
		process();
	}
	
	@Override
	protected void process() {
		processCodeLine();
		translatedLine.setShortDescription("Instantiating: \"" + instantiatedObject + "\".");
		translatedLine.setLongDescription("This is an instantiation statement.");
		translatedLine.appendToLongDescription("This statement lets you instantiate a new object from a class, in this case a(n) \"" + instantiatedObject + "\" object.");
		translatedLine.appendToLongDescription("A number of arguments can be passed to the constructor while instantiating this object.");
		appendParamInfo();
		appendAssignmentInfo();
		translatedLine.appendLinkToLongDescription("instantiation statements", infoLink);
	}
	
	private void processCodeLine() {
		// Assignment processing
		int assignmentOpIndex = getIndexForChar(0, '=');
		if (assignmentOpIndex != -1) {
			/* There is an assignment */
			assignedTo = (executedSourceLine.substring(0, assignmentOpIndex)).trim();
		}
		
		// Method call processing
		int classNameIndex = executedSourceLine.indexOf("new") + 3;
		instantiatedObject = (executedSourceLine.substring(classNameIndex, getIndexForChar(classNameIndex, '('))).trim();
		
		// Argument processing
		getArgumentInfo(classNameIndex);
	}
	
	private void getArgumentInfo(int classNameIndex) {
		int paramStartIndex = executedSourceLine.indexOf('(', classNameIndex);
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
			translatedLine.appendToLongDescription("\n\nWhen creating this object, the following argument(s) are passed:\n");
			for (int i = 0; i < args.size(); i++) {
				String arg = removeParantheses(args.get(i).trim());
				translatedLine.appendToLongDescription("\n" + (i + 1) + ") " + arg + " (" + getSnippetType(arg) + ")");
			}
		}
	}
	
	private void appendAssignmentInfo() {
		if (!assignedTo.isEmpty()) {
			translatedLine.appendToLongDescription("\n\nThe object is assigned to \"" + assignedTo + "\".");
		}
	}
	
}
