package plainenglishjavadebugger.views.translatorView.actions;

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

public class ClearTranslatorViewAction extends Action {
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	private final String buttonText = "Clear the list";
	
	public ClearTranslatorViewAction(TranslatorView view) {
		this.view = view;
		model = view.getModel();
		
		setText(buttonText);
		setToolTipText(buttonText);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_ELCL_REMOVE));
	}
	
	@Override
	public void run() {
		model.removeAllTranslatedLines();
	}
}
