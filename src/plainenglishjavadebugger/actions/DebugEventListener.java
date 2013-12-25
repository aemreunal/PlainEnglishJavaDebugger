package plainenglishjavadebugger.actions;

import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IDebugEventSetListener;

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
	private int debugEventType = -1;
	private boolean inDebugState = false;
	
	public DebugEventListener(TranslatorViewModel model) {
		this.model = model;
	}
	
	public void startListening() {
		if (!isListening) {
			DebugPlugin.getDefault().addDebugEventListener(this);
			setInDebugState(true);
		}
	}
	
	public void stopListening() {
		if (isListening) {
			DebugPlugin.getDefault().removeDebugEventListener(this);
			setInDebugState(false);
		}
	}
	
	// IDebugEventSetListener interface methods begin
	@Override
	public void handleDebugEvents(DebugEvent[] debugEvents) {
		for (DebugEvent debugEvent : debugEvents) {
			if ((debugEvent.getKind() == DebugEvent.SUSPEND) && (debugEvent.getDetail() == DebugEvent.STEP_END)) {
				model.respondToDebugEvent(debugEventType);
				debugEventType = -1;
			} else if ((debugEvent.getDetail() == DebugEvent.STEP_OVER) || (debugEvent.getDetail() == DebugEvent.STEP_INTO)) {
				debugEventType = debugEvent.getDetail();
			}
			if ((debugEvent.getKind() == DebugEvent.STEP_INTO) || (debugEvent.getKind() == DebugEvent.STEP_OVER) || (debugEvent.getKind() == DebugEvent.STEP_RETURN)) {
				model.addStacksToSimulationFrame();
			}
		}
	}
	
	// IDebugEventSetListener interface methods end
	
	public boolean getInDebugState() {
		return inDebugState;
	}
	
	public synchronized void setInDebugState(boolean inDebugState) {
		this.inDebugState = inDebugState;
		model.setDebugging(inDebugState);
	}
	
	public void setIsListening(boolean isListening) {
		this.isListening = isListening;
	}
}
