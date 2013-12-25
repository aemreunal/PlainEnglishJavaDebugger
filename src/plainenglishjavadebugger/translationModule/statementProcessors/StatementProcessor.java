package plainenglishjavadebugger.translationModule.statementProcessors;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.StatementType;
import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public abstract class StatementProcessor {
	protected IJavaThread thread;
	protected TranslatedLine translatedLine;
	protected String executedSourceLine;
	
	public StatementProcessor(StatementType type, IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		this.translatedLine = translatedLine;
		this.executedSourceLine = executedSourceLine;
		this.translatedLine.setStatementType(type);
		this.thread = thread;
	}
	
	protected abstract void process();
	
	protected int getIndexForChar(int startIndex, char charToIndex) {
		int paranthesesLevel = 0;
		for (int i = startIndex; i < executedSourceLine.length(); i++) {
			char currentChar = executedSourceLine.charAt(i);
			if (charToIndex == ')' && currentChar == charToIndex && paranthesesLevel == 1) {
				return i;
			}
			if (currentChar == charToIndex && paranthesesLevel == 0) {
				return i;
			}
			if (currentChar == '(' || currentChar == '[' || currentChar == '{') {
				paranthesesLevel++;
			} else if (currentChar == ')' || currentChar == ']' || currentChar == '}') {
				paranthesesLevel--;
			}
		}
		return -1;
	}
	
	protected String removeParantheses(String code) {
		while (code.charAt(0) == '(' && code.charAt(code.length() - 1) == ')') {
			code = code.substring(1, code.length() - 1);
		}
		return code;
	}
	
	protected boolean isJavaName(String condition) {
		return condition.matches(SourceCodeMatcher.javaNameRegex);
	}
	
	protected void getVariableValue(String variable) {
		try {
			IValue variableValue = thread.findVariable(variable).getValue();
			translatedLine.appendToLongDescription("which has the value of \"" + variableValue.getValueString() + "\",");
		} catch (NullPointerException e) {
			// Don't append the value of the returned variable.
			System.err.println("Unable to get the variable itself!");
		} catch (DebugException e) {
			// Don't append the value of the returned variable.
			System.err.println("Unable to get the value of the variable!");
		}
	}
	
	protected String getSnippetType(String arg) {
		if (arg.matches(SourceCodeMatcher.instantiationStatementRegex)) {
			return "a newly instantiated object";
		} else if (arg.matches(SourceCodeMatcher.methodCallStatementRegex)) {
			return "another method call";
		} else if (arg.matches(SourceCodeMatcher.javaNameRegex)) {
			return "the value of a variable";
		} else if (arg.matches(SourceCodeMatcher.numberRegex)) {
			return "a number";
		} else if (arg.matches(SourceCodeMatcher.stringRegex)) {
			return "a string";
		} else if (arg.matches(SourceCodeMatcher.incrementRegex)) {
			return "an increment";
		}
		return "";
	}
}
