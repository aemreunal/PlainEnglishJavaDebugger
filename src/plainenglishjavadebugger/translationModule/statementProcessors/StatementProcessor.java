package plainenglishjavadebugger.translationModule.statementProcessors;

import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class StatementProcessor {
	// Java regex info: http://docs.oracle.com/javase/tutorial/essential/regex/pre_char_classes.html
	protected final String javaNameRegex = "[a-zA-Z]*[a-zA-Z_0-9]*";
	protected final String argumentRegex = "['('].*[')']";
	protected final String messageSendRegex = javaNameRegex + "['.']";
	protected final String assignmentRegex = "(" + messageSendRegex + ")?" + javaNameRegex + "[ ]=[ ]";
	protected final String assignmentPossibilityRegex = "(" + assignmentRegex + ")?";
	protected final String methodCallStatementRegex = assignmentPossibilityRegex + "(" + messageSendRegex + ")*" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	protected final String instantiationStatementRegex = assignmentPossibilityRegex + "new[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	protected final String visibilityDeclarationRegex = "\bprivate\b|\bpublic\b|\bprotected\b";
	protected final String returnTypeRegex = javaNameRegex;
	protected final String methodEnterStatementRegex = "(" + visibilityDeclarationRegex + ")?" + "[ ]" + returnTypeRegex + "[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[ ]?['{']";
	
	public void processStatement(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		if (executedSourceLine.startsWith("if")) {
			processIfStatement();
		} else if (executedSourceLine.startsWith("for")) {
			processForStatement();
		} else if (executedSourceLine.startsWith("while")) {
			processWhileStatement();
		} else if (executedSourceLine.startsWith("switch")) {
			processSwitchStatement();
		} else if (executedSourceLine.startsWith("return")) {
			new ReturnStatementProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.matches(instantiationStatementRegex)) {
			processInstantiation();
		} else if (executedSourceLine.matches(methodCallStatementRegex)) {
			processMethodCall();
			/*
			 * }
			 * else if (executedSourceLine.matches(methodEnterStatementRegex)) {
			 * // Can this ever be reached? Does the debugger highlight method entrances?
			 * translatedLine.setStatementType(StatementType.METHOD_ENTER);
			 * translatedLine.setShortDescription("This is a method entrance.");
			 * translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
			 * }
			 */
		} else {
			processOtherStatement();
		}
	}
	
	private void processOtherStatement() {
		// translatedLine.setStatementType(StatementType.OTHER);
		// translatedLine.setShortDescription("This is something else.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processMethodCall() {
		// translatedLine.setStatementType(StatementType.METHOD_CALL);
		// translatedLine.setShortDescription("This is a method call.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processInstantiation() {
		// translatedLine.setStatementType(StatementType.INSTANTIATION);
		// translatedLine.setShortDescription("This is an instantiation.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processSwitchStatement() {
		// translatedLine.setStatementType(StatementType.SWITCH);
		// translatedLine.setShortDescription("This is a switch-statement.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processWhileStatement() {
		// translatedLine.setStatementType(StatementType.WHILE_LOOP);
		// translatedLine.setShortDescription("This is a while-loop.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processForStatement() {
		// translatedLine.setStatementType(StatementType.FOR_LOOP);
		// translatedLine.setShortDescription("This is a for-loop.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
	
	private void processIfStatement() {
		// translatedLine.setStatementType(StatementType.IF);
		// translatedLine.setShortDescription("This is an if-statement.");
		// translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
	}
}
