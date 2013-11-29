package plainenglishjavadebugger.translationModule;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.translationModule.fileReader.SourceFileReader;
import plainenglishjavadebugger.views.translatorView.TranslatedLine;
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
	private IStackFrame topStackFrame;
	private Object debuggedClassSourceElement;
	private int debuggedLineNumber;
	private String debuggedClassPath;
	
	public Translator(TranslatorViewModel model) {
		this.model = model;
		sourceFileReader = new SourceFileReader();
	}
	
	public void setThread(IJavaThread thread) {
		this.thread = thread;
	}
	
	public void clearThread() {
		thread = null;
	}
	
	public void translate(int debugEventType) {
		try {
			getCurrentFrameInfo();
			if (sourceCodeIsAvailable()) {
				getCurrentClassInfo();
				model.addTranslatedLine(getTranslation(debugEventType));
			} else {
				System.out.println("Undebuggable, closed-source class.");
			}
		} catch (DebugException e) {
			System.err.println("Unable to get stack frame info from thread!");
			e.printStackTrace();
		}
	}
	
	private boolean sourceCodeIsAvailable() {
		/* Which means it's not a built-in, closed-source library class */
		return debuggedClassSourceElement != null;
	}
	
	private void getCurrentFrameInfo() throws DebugException {
		topStackFrame = thread.getTopStackFrame();
		debuggedClassSourceElement = topStackFrame.getLaunch().getSourceLocator().getSourceElement(topStackFrame);
	}
	
	private void getCurrentClassInfo() throws DebugException {
		debuggedLineNumber = topStackFrame.getLineNumber();
		debuggedClassPath = debuggedClassSourceElement.toString();
	}
	
	private TranslatedLine getTranslation(int debugEventType) {
		String executedSourceLine = sourceFileReader.readLine(topStackFrame, debuggedClassPath, debuggedLineNumber);
		TranslatedLine translatedLine = new TranslatedLine(executedSourceLine, executedSourceLine, debuggedClassPath + " " + debuggedLineNumber, getDebugEventTypeName(debugEventType));
		return translatedLine;
	}
	
	public String getDebugEventTypeName(int debugEventType) {
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
