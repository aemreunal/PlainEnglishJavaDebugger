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
	
	public StatementProcessor(StatementType type, IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		this.thread = thread;
		this.translatedLine = translatedLine;
		this.executedSourceLine = executedSourceLine;
		this.translatedLine.setStatementType(type);
		process();
	}
	
	protected abstract void process();
}
