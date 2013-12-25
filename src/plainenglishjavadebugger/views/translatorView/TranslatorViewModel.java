package plainenglishjavadebugger.views.translatorView;

import java.util.ArrayList;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.swt.widgets.Display;

import plainenglishjavadebugger.actions.DebugBreakpointListener;
import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.simulationModule.Simulator;
import plainenglishjavadebugger.translationModule.TranslatedLine;
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
	private final ArrayList<TranslatedLine> translatedLines;
	
	private boolean isDebugging = false;
	private IJavaThread thread;
	
	private Simulator simulator;
	
	public TranslatorViewModel(TranslatorView view) {
		this.view = view;
		translatedLines = new ArrayList<TranslatedLine>();
		eventListener = new DebugEventListener(this);
		breakpointListener = new DebugBreakpointListener(this);
		translator = new Translator(this);
		
		simulator = new Simulator(this);
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
		removeAllTranslatedLines();
		TranslatedLine.resetItemNumbering();
		setDebugging(false);
		simulator.reset();
	}
	
	public void respondToDebugEvent(final int debugEventType) {
		if (isDebugging) {
			translator.translate(debugEventType);
		}
	}
	
	// TranslatorView actions
	public void addTranslatedLine(TranslatedLine translatedLine) {
		translatedLines.add(translatedLine);
		refreshView();
	}
	
	public void removeTranslatedLine() {
		if (translatedLines.size() > 0) {
			translatedLines.remove(translatedLines.size() - 1);
		}
		refreshView();
	}
	
	public void removeAllTranslatedLines() {
		translatedLines.clear();
		refreshView();
	}
	
	private void refreshView() {
		// Required to run UI refresh as part of UI thread system.
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				view.refresh();
			}
		});
	}
	
	// Simulation methods begin
	
	public void stepInto() {
		try {
			thread.stepInto();
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	public void stepOver() {
		if ((thread != null) && (thread.canStepOver())) {
			try {
				thread.stepOver();
			} catch (DebugException e) {
				e.printStackTrace();
			}
		} else {
			stopDebugState();
		}
	}
	
	public void addStacksToSimulationFrame() {
		try {
			if (thread == null || (thread.getTopStackFrame() != null && thread.getTopStackFrame().getName().equals("exit"))) {
				eventListener.setInDebugState(false);
			} else {
				simulator.addStackToFrame(thread.getStackFrames());
			}
		} catch (DebugException e) {
			e.printStackTrace();
		}
	}
	
	private String[] getCurrentStackNames() throws DebugException {
		IStackFrame[] stackFrames = thread.getStackFrames();
		String[] stackNames = new String[stackFrames.length];
		for (int i = 0; i < stackFrames.length; i++) {
			stackNames[i] = stackFrames[i].getName();
		}
		return stackNames;
	}
	
	public void resetSimulator() {
		simulator.reset();
	}
	
	// Simulation methods end
	
	public synchronized boolean isDebugging() {
		return isDebugging;
	}
	
	public synchronized void setDebugging(boolean isDebugging) {
		this.isDebugging = isDebugging;
		eventListener.setIsListening(isDebugging);
	}
	
	public DebugEventListener getEventListener() {
		return eventListener;
	}
	
	public Simulator getSimulator() {
		return simulator;
	}
	
	public synchronized IJavaThread getThread() {
		return thread;
	}
	
	public ArrayList<TranslatedLine> getElements() {
		return translatedLines;
	}
}
