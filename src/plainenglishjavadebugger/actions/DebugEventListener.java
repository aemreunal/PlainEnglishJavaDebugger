package plainenglishjavadebugger.actions;

import org.eclipse.debug.core.DebugEvent;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */


/*
 * http://www.eclipse.org/forums/index.php/t/369839/
 * http://help.eclipse.org/helios/nftopic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/debug/core/DebugEvent.html
 */

public class DebugEventListener implements IDebugEventSetListener {
	private final TranslatorViewModel model;
	private boolean isListening = false;
	private boolean inDebugState = false;

	public DebugEventListener(TranslatorViewModel model) {
		this.model = model;
	}

	public void startListening() {
		if (!isListening) {
			DebugPlugin.getDefault().addDebugEventListener(this);
			System.out.println("Started Listening");
		}
	}

	public void stopListening() {
		if (isListening) {
			DebugPlugin.getDefault().removeDebugEventListener(this);
			inDebugState = false;
			System.out.println("Stopped Listening");
		}
	}

	// IDebugEventSetListener interface methods begin
	@Override
	public void handleDebugEvents(DebugEvent[] debugEvents) {
		for (DebugEvent debugEvent : debugEvents) {
			if (debugEvent.getKind() == DebugEvent.CREATE) {
				System.out.println("CREATE");
				setInDebugState(true);
			} else if((debugEvent.getKind() == DebugEvent.STEP_INTO) || (debugEvent.getKind() == DebugEvent.STEP_OVER) || (debugEvent.getKind() == DebugEvent.STEP_RETURN)) {
				model.addStacksToSimulationFrame();
			}
		}
	}

	public boolean getInDebugState() {
		return inDebugState;
	}
	
	public synchronized void setInDebugState(boolean inDebugState) {
		this.inDebugState = inDebugState;
		model.setDebugging(inDebugState);
	}

	// IDebugEventSetListener interface methods end
	public void setIsListening(boolean isListening) {
		this.isListening = isListening;
	}
}
