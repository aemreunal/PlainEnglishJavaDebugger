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
	
	public DebugEventListener(TranslatorViewModel model) {
		this.model = model;
	}
	
	public void startListening() {
		if (!isListening) {
			DebugPlugin.getDefault().addDebugEventListener(this);
		}
	}
	
	public void stopListening() {
		if (isListening) {
			DebugPlugin.getDefault().removeDebugEventListener(this);
		}
	}
	
	@Override
	public void handleDebugEvents(DebugEvent[] debugEvents) {
		for (DebugEvent debugEvent : debugEvents) {
			if (debugEvent.getDetail() == DebugEvent.STEP_OVER) {
				System.out.println("Stepped over!");
			}
			// System.out.println(debugEvent);
		}
	}
	
}
