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

public abstract class StatementProcessor {
	protected IJavaThread thread;
	protected TranslatedLine translatedLine;
	protected String executedSourceLine;
	
	public StatementProcessor(StatementType type, String executedSourceLine, TranslatedLine translatedLine) {
		this.translatedLine = translatedLine;
		this.executedSourceLine = executedSourceLine;
		this.translatedLine.setStatementType(type);
	}
	
	public StatementProcessor(StatementType type, IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		this(type, executedSourceLine, translatedLine);
		this.thread = thread;
	}
	
	protected abstract void process();
	
	protected int getIndexForChar(int startIndex, char charToIndex) {
		int paranthesesLevel = 0;
		for (int i = startIndex; i < executedSourceLine.length(); i++) {
			char currentChar = executedSourceLine.charAt(i);
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
}
