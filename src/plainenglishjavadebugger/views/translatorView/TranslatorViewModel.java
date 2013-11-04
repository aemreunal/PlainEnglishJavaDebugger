package plainenglishjavadebugger.views.translatorView;

import java.util.ArrayList;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.IDebugEventSetListener;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;

import plainenglishjavadebugger.actions.DebugBreakpointListener;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class TranslatorViewModel implements IDebugEventSetListener {
	private final TranslatorView view;
	private final DebugBreakpointListener listener;
	
	private boolean isDebugging = false;
	private IJavaThread thread;
	private IJavaBreakpoint breakpoint;
	
	private int debuggedLineNumber;
	private String debuggedClassPath;
	
	private ArrayList<TranslatedLine> elements = new ArrayList<TranslatedLine>();
	
	public TranslatorViewModel(TranslatorView view) {
		this.view = view;
		listener = new DebugBreakpointListener(this);
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
	
	public void setDebugInfo(IJavaThread thread, IJavaBreakpoint breakpoint) {
		this.thread = thread;
		this.breakpoint = breakpoint;
		// JavaDebugOptionsManager.getDefault().
		setDebugging(true);
	}
	
	public void removeDebugInfo() {
		thread = null;
		breakpoint = null;
		setDebugging(false);
	}
	
	public void getThreadInfo() {
		// System.out.println(JDIDebugPlugin.getDefault().isDebugging());
		try {
			/*
			 * System.out.println(thread.getDebugTarget().getName()); // Successfully got the name of the class with the main method attached to the
			 * // class being debugged at breakpoint time.
			 * System.out.println(breakpoint.getMarker().getResource()); // The proper path of the class being debugged
			 * System.out.println(thread.findVariable("solutionNumber").isPrivate()); // Query the variables and get information about them
			 * for (org.eclipse.debug.core.model.IStackFrame stackFrame : thread.getStackFrames()) {
			 * System.out.println(stackFrame.getLineNumber()); // Successfully getting the line number of the stack frame!
			 * }
			 */
			if (isDebugging) {
				IStackFrame topStackFrame = thread.getTopStackFrame();
				debuggedLineNumber = topStackFrame.getLineNumber();
				debuggedClassPath = topStackFrame.getLaunch().getSourceLocator().getSourceElement(thread.getTopStackFrame()).toString();
			}
			// System.out.println(thread.getTopStackFrame().getModelIdentifier());
			// System.out.println(thread.getTopStackFrame().getName());
			// System.out.println(thread.getTopStackFrame().getClass().getName());
			// System.out.println(thread.getTopStackFrame().getClass().getSimpleName());
			// System.out.println(thread.getTopStackFrame().getClass().getCanonicalName());
			// System.out.println(thread.getTopStackFrame().getDebugTarget().getName());
			// System.out.println(thread.getTopStackFrame().getLaunch().getSourceLocator().getSourceElement(thread.getTopStackFrame()));
			// System.out.println(thread.getTopStackFrame().getLaunch().getDebugTarget().getName());
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
	
	@Override
	public void handleDebugEvents(DebugEvent[] debugEvents) {
		getThreadInfo();
		System.out.println("The debugged class: " + debuggedClassPath);
		System.out.println("The debugged line number: " + debuggedLineNumber);
		for (DebugEvent debugEvent : debugEvents) {
			System.out.println(debugEvent.toString());
		}
	}
	
	/*
	 * @Override
	 * public void eventSetComplete(Event arg0, JDIDebugTarget arg1, boolean arg2, EventSet arg3) {
	 * // TODO Auto-generated method stub
	 * System.out.println(arg0);
	 * }
	 * 
	 * @Override
	 * public boolean handleEvent(Event arg0, JDIDebugTarget arg1, boolean arg2, EventSet arg3) {
	 * System.out.println(arg0);
	 * return false;
	 * }
	 */
}
