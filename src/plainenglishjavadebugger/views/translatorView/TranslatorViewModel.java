package plainenglishjavadebugger.views.translatorView;

import java.util.ArrayList;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.actions.DebugBreakpointListener;
import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.translationModule.fileReader.JavaClassReader;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

@SuppressWarnings("unused")
public class TranslatorViewModel {
	private final TranslatorView view;
	private final DebugBreakpointListener breakpointListener;
	private final DebugEventListener eventListener;
	private final JavaClassReader sourceFileReader;
	
	private boolean isDebugging = false;
	private IJavaThread thread;
	private int debuggedLineNumber;
	private String debuggedClassPath;
	
	private ArrayList<TranslatedLine> elements = new ArrayList<TranslatedLine>();
	
	public TranslatorViewModel(TranslatorView view) {
		this.view = view;
		eventListener = new DebugEventListener(this);
		breakpointListener = new DebugBreakpointListener(this);
		sourceFileReader = new JavaClassReader();
	}
	
	public ArrayList<TranslatedLine> getElements() {
		return elements;
	}
	
	// TranslatorView actions
	public void addElement(TranslatedLine translatedLine) {
		elements.add(translatedLine);
		view.refresh();
	}
	
	public void removeElement() {
		if (elements.size() > 0) {
			elements.remove(elements.size() - 1);
		}
		view.refresh();
	}
	
	public void setDebugInfo(IJavaThread thread) {
		this.thread = thread;
		eventListener.startListening();
		setDebugging(true);
	}
	
	public void removeDebugInfo() {
		thread = null;
		eventListener.stopListening();
		setDebugging(false);
	}
	
	public void getThreadInfo() {
		try {
			
			/*
			 * System.out.println(thread.findVariable("solutionNumber").isPrivate()); // Query the variables and get information about them
			 * System.out.println(thread.getTopStackFrame().getName()); // Get current method name
			 * System.out.println(thread.getTopStackFrame().getDebugTarget().getName()); // Get current class name from src/...
			 */
			
			/*
			 * http://codeandme.blogspot.nl/2013/11/debugger-7-source-lookup.html
			 * http://www.planeteclipse.org/planet/
			 */
			
			if (isDebugging) {
				IStackFrame topStackFrame = thread.getTopStackFrame();
				Object debuggedClassSourceElement = topStackFrame.getLaunch().getSourceLocator().getSourceElement(thread.getTopStackFrame());
				if (debuggedClassSourceElement != null /* Which means it's not a built-in, closed-source library class */) {
					debuggedLineNumber = topStackFrame.getLineNumber();
					debuggedClassPath = debuggedClassSourceElement.toString();
					TranslatedLine translatedLine = new TranslatedLine(debuggedClassPath + " " + debuggedLineNumber, debuggedLineNumber + "", debuggedClassPath + " " + debuggedLineNumber);
					// TranslatedLine translatedLine = new TranslatedLine(sourceFileReader.readLine(debuggedClassPath, debuggedLineNumber),
					// debuggedClassPath + " " + debuggedLineNumber, debuggedClassPath + " " + debuggedLineNumber);
					addElement(translatedLine);
					printDebugInfo();
				} else {
					System.out.println("Undebuggable, closed-source class.");
				}
			}
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized boolean isDebugging() {
		return isDebugging;
	}
	
	public synchronized void setDebugging(boolean isDebugging) {
		this.isDebugging = isDebugging;
	}
	
	public void printDebugInfo() {
		System.out.println("The debugged class: " + debuggedClassPath);
		System.out.println("The debugged line number: " + debuggedLineNumber);
	}
	
	public synchronized IJavaThread getThread() {
		return thread;
	}
}
