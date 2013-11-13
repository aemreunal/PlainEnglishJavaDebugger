package plainenglishjavadebugger.views.translatorView;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchActionConstants;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class TranslatorViewMenuManager extends MenuManager {
	private final TranslatorView view;
	
	public TranslatorViewMenuManager(TranslatorView view, Control control) {
		super("#PopupMenu");
		this.view = view;
		setRemoveAllWhenShown(true);
		setMenuListener();
		control.setMenu(createContextMenu(control));
	}
	
	private void setMenuListener() {
		addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				fillContextMenu(manager);
			}
		});
	}
	
	private void fillContextMenu(IMenuManager manager) {
		manager.add(view.getStepOverTranslateAction());
		manager.add(view.getStepIntoTranslateAction());
		// Other plug-ins can contribute their actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
}
