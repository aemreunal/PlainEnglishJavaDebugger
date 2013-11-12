package plainenglishjavadebugger.views.translatorView.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import plainenglishjavadebugger.views.translatorView.TranslatorView;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class StepIntoTranslateAction extends Action {
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	private final String buttonText = "Step Into and Translate";
	
	public StepIntoTranslateAction(TranslatorView view) {
		this.view = view;
		model = view.getModel();
		
		setText(buttonText);
		setToolTipText(buttonText);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	@Override
	public void run() {
		if (model.isDebugging()) {
			model.getThreadInfo();
			try {
				model.getThread().stepInto();
			} catch (DebugException e) {
				System.err.println("Unable to step into the line!");
				e.printStackTrace();
			}
			// model.removeElement();
			// view.showMessage("Info", "Removed the last translation line.");
		}
	}
	
}
