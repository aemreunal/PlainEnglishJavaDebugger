package plainenglishjavadebugger.translationModule.statementProcessors;

import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class SourceCodeProcessor {
	// Java regex info: http://docs.oracle.com/javase/tutorial/essential/regex/pre_char_classes.html
	public static final String whiteSpaceRegex = "\\s*";
	public static final String javaNameRegex = "[a-zA-Z]*[a-zA-Z_0-9]*";
	public static final String argumentRegex = "['('].*[')']";
	public static final String messageSendRegex = javaNameRegex + "['.']";
	public static final String assignmentRegex = "(" + messageSendRegex + ")?" + javaNameRegex + "[ ]=[ ]";
	public static final String assignmentPossibilityRegex = "(" + assignmentRegex + ")?";
	public static final String methodCallStatementRegex = assignmentPossibilityRegex + "(" + messageSendRegex + ")*" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	public static final String instantiationStatementRegex = assignmentPossibilityRegex + "new[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	public static final String visibilityDeclarationRegex = "\bprivate\b|\bpublic\b|\bprotected\b";
	public static final String returnTypeRegex = javaNameRegex;
	public static final String methodEnterStatementRegex = "(" + visibilityDeclarationRegex + ")?" + "[ ]" + returnTypeRegex + "[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[ ]?['{']";
	
	public void processStatement(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		// String code = "return (mustafa);";
		// new ReturnProcessor(thread, translatedLine, code);
		if (executedSourceLine.startsWith("if")) {
			new IfProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.startsWith("for")) {
			new ForProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.startsWith("while")) {
			new WhileProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.startsWith("switch")) {
			new SwitchProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.startsWith("return")) {
			new ReturnProcessor(thread, translatedLine, executedSourceLine);
		} else if (executedSourceLine.matches(instantiationStatementRegex)) {
			
		} else if (executedSourceLine.matches(methodCallStatementRegex)) {
			
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
			
		}
	}
}
