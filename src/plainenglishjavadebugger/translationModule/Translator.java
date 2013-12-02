package plainenglishjavadebugger.translationModule;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.fileReader.SourceFileReader;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

/*
 * Scheduling a translation job: http://help.eclipse.org/kepler/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs.htm?cp=2_0_3_5
 * 
 * When the status of the debugger changes, the translation job can be started, joined, executed and terminated.
 * Can get other info about other jobs through IJobManager.
 * Set a listener to the debugger, when it's status changes, start the translation job.
 * 
 * Translation job should be a system job:
 * (http://help.eclipse.org/kepler/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs_progress.htm?cp=2_0_3_5_0)
 * 
 * This, most probably, is a SHORT job (Job Scheduling).
 */

public class Translator {
	private final TranslatorViewModel model;
	private final SourceFileReader sourceFileReader;
	
	private IJavaThread thread;
	
	private TranslatedLine translatedLine;
	private IStackFrame topStackFrame;
	private Object debuggedClassSourceElement;
	private int debuggedLineNumber;
	private String debuggedClassPath;
	private int debugEventType;
	private String executedSourceLine;
	
	private String[] ignoredLines = { "}", "});" };
	// Java regex info: http://docs.oracle.com/javase/tutorial/essential/regex/pre_char_classes.html
	private final String javaNameRegex = "[a-zA-Z]*[a-zA-Z_0-9]*";
	private final String argumentRegex = "['('].*[')']";
	private final String messageSendRegex = javaNameRegex + "['.']";
	private final String assignmentRegex = "(" + messageSendRegex + ")?" + javaNameRegex + "[ ]=[ ]";
	private final String assignmentPossibilityRegex = "(" + assignmentRegex + ")?";
	private final String methodCallStatementRegex = assignmentPossibilityRegex + "(" + messageSendRegex + ")*" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	private final String instantiationStatementRegex = assignmentPossibilityRegex + "new[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[';']";
	private final String visibilityDeclarationRegex = "\bprivate\b|\bpublic\b|\bprotected\b";
	private final String returnTypeRegex = javaNameRegex;
	private final String methodEnterStatementRegex = "(" + visibilityDeclarationRegex + ")?" + "[ ]" + returnTypeRegex + "[ ]" + javaNameRegex + "[ ]?" + argumentRegex + "[ ]?['{']";
	
	public Translator(TranslatorViewModel model) {
		this.model = model;
		sourceFileReader = new SourceFileReader();
		resetTranslator();
	}
	
	public void setThread(IJavaThread thread) {
		this.thread = thread;
	}
	
	public void clearThread() {
		thread = null;
	}
	
	// The method that does the translation work
	public void translate(int debugEventType) {
		try {
			getCurrentFrameInfo();
			if (sourceCodeIsAvailable()) {
				this.debugEventType = debugEventType;
				getCurrentClassInfo();
				generateTranslatedLine();
			} else {
				System.out.println("Undebuggable, closed-source class.");
			}
		} catch (DebugException e) {
			System.err.println("Unable to get stack frame info from thread!");
			e.printStackTrace();
		}
	}
	
	private void getCurrentFrameInfo() throws DebugException {
		topStackFrame = thread.getTopStackFrame();
		debuggedClassSourceElement = topStackFrame.getLaunch().getSourceLocator().getSourceElement(topStackFrame);
	}
	
	private boolean sourceCodeIsAvailable() {
		/* Which means it's not a built-in, closed-source library class */
		return debuggedClassSourceElement != null;
	}
	
	private void getCurrentClassInfo() throws DebugException {
		debuggedLineNumber = topStackFrame.getLineNumber();
		debuggedClassPath = debuggedClassSourceElement.toString();
	}
	
	private void generateTranslatedLine() {
		executedSourceLine = sourceFileReader.readLine(topStackFrame, debuggedClassPath, debuggedLineNumber);
		if (!lineIsIgnored()) {
			createTranslatedLineObject();
			setTranslationInfo();
			returnLine();
			resetTranslator();
		}
	}
	
	private void createTranslatedLineObject() {
		translatedLine = new TranslatedLine(executedSourceLine, debugEventType);
	}
	
	private void setTranslationInfo() {
		identifyStatementType();
	}
	
	private void identifyStatementType() {
		if (executedSourceLine.startsWith("if")) {
			translatedLine.setStatementType(StatementType.IF);
			translatedLine.setShortDescription("This is an if-statement.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.startsWith("for")) {
			translatedLine.setStatementType(StatementType.FOR_LOOP);
			translatedLine.setShortDescription("This is a for-loop.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.startsWith("while")) {
			translatedLine.setStatementType(StatementType.WHILE_LOOP);
			translatedLine.setShortDescription("This is a while-loop.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.startsWith("switch")) {
			translatedLine.setStatementType(StatementType.SWITCH);
			translatedLine.setShortDescription("This is a switch-statement.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.startsWith("return")) {
			translatedLine.setStatementType(StatementType.RETURN);
			translatedLine.setShortDescription("This is a return-statement.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.matches(instantiationStatementRegex)) {
			translatedLine.setStatementType(StatementType.INSTANTIATION);
			translatedLine.setShortDescription("This is an instantiation.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} else if (executedSourceLine.matches(methodCallStatementRegex)) {
			translatedLine.setStatementType(StatementType.METHOD_CALL);
			translatedLine.setShortDescription("This is a method call.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		} /*
		 * else if (executedSourceLine.matches(methodEnterStatementRegex)) {
		 * // Can this ever be reached? Does the debugger highlight method entrances?
		 * translatedLine.setStatementType(StatementType.METHOD_ENTER);
		 * translatedLine.setShortDescription("This is a method entrance.");
		 * translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		 * }
		 */else {
			translatedLine.setStatementType(StatementType.OTHER);
			translatedLine.setShortDescription("This is something else.");
			translatedLine.setLongDescription(debuggedClassPath + " " + debuggedLineNumber);
		}
	}
	
	private void returnLine() {
		model.addTranslatedLine(translatedLine);
	}
	
	private void resetTranslator() {
		translatedLine = null;
		topStackFrame = null;
		debuggedClassSourceElement = null;
		debuggedLineNumber = -1;
		debuggedClassPath = "";
		debugEventType = -1;
		executedSourceLine = "";
	}
	
	private boolean lineIsIgnored() {
		for (String ignoredLine : ignoredLines) {
			if (ignoredLine.equals(executedSourceLine)) {
				return true;
			}
		}
		return false;
	}
	
	private String getDebugEventTypeName(int debugEventType) {
		switch (debugEventType) {
		case DebugEvent.STEP_OVER:
			return "Stepped over the line";
		case DebugEvent.STEP_INTO:
			return "Stepped into the line";
		default:
			return "Undetermined debug event.";
		}
	}
}
