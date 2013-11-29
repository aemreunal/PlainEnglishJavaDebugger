package plainenglishjavadebugger.views.translatorView;

import java.util.ArrayList;

import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.swt.widgets.Display;

import plainenglishjavadebugger.actions.DebugBreakpointListener;
import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.translationModule.Translator;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

/*
 * System.out.println(thread.findVariable("solutionNumber").isPrivate()); // Query the variables and get information about them
 * System.out.println(thread.getTopStackFrame().getName()); // Get current method name
 * System.out.println(thread.getTopStackFrame().getDebugTarget().getName()); // Get current class name from src/...
 */

/*
 * http://codeandme.blogspot.nl/2013/11/debugger-7-source-lookup.html
 * http://www.planeteclipse.org/planet/
 */

@SuppressWarnings("unused")
public class TranslatorViewModel {
	private final TranslatorView view;
	private final DebugBreakpointListener breakpointListener;
	private final DebugEventListener eventListener;
	private final Translator translator;
	
	private boolean isDebugging = false;
	private IJavaThread thread;
	
	private ArrayList<TranslatedLine> elements = new ArrayList<TranslatedLine>();
	
	public TranslatorViewModel(TranslatorView view) {
		this.view = view;
		eventListener = new DebugEventListener(this);
		breakpointListener = new DebugBreakpointListener(this);
		translator = new Translator(this);
	}
	
	public void initDebugState(IJavaThread thread) {
		this.thread = thread;
		eventListener.startListening();
		translator.setThread(thread);
		setDebugging(true);
	}
	
	public void stopDebugState() {
		thread = null;
		eventListener.stopListening();
		translator.clearThread();
		setDebugging(false);
	}
	
	public void respondToDebugEvent(final int debugEventType) {
		if (isDebugging) {
			// Required to run UI refresh as part of UI thread system.
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					translator.translate(debugEventType);
				}
			});
		}
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
	
	public synchronized boolean isDebugging() {
		return isDebugging;
	}
	
	public synchronized void setDebugging(boolean isDebugging) {
		this.isDebugging = isDebugging;
	}
	
	public synchronized IJavaThread getThread() {
		return thread;
	}
	
	public ArrayList<TranslatedLine> getElements() {
		return elements;
	}
}
