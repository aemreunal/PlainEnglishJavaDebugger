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

public class GenericProcessor extends StatementProcessor {
	
	public GenericProcessor(IJavaThread thread, TranslatedLine translatedLine, String executedSourceLine) {
		super(StatementType.OTHER, thread, translatedLine, executedSourceLine);
		process();
	}
	
	@Override
	protected void process() {
		translatedLine.setShortDescription("Executing " + executedSourceLine + ".");
		translatedLine.setLongDescription("(No additional information is available)");
	}
}
