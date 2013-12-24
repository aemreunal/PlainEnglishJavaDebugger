package plainenglishjavadebugger.actions;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;


/*
 * http://www.eclipse.org/forums/index.php/t/369839/
 * http://help.eclipse.org/helios/nftopic/org.eclipse.platform.doc.isv/reference/api/org/eclipse/debug/core/DebugEvent.html
 */

public class DebugEventListener implements IDebugEventSetListener {
	private final TranslatorViewModel model;
	private boolean isListening = false;
	private boolean inDebugState = false;

	private SimulationRunner runner; 

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
			System.out.println("Stopped Listening");
			try {
				runner.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// IDebugEventSetListener interface methods begin
	@Override
	public void handleDebugEvents(DebugEvent[] debugEvents) {
		for (DebugEvent debugEvent : debugEvents) {
			if (debugEvent.getKind() == DebugEvent.CREATE) {
				setInDebugState(true);
				runner = new SimulationRunner(model);
				runner.start();
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
