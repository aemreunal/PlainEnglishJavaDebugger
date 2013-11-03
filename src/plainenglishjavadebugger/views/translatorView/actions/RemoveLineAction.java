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

public class RemoveLineAction extends Action {
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	public RemoveLineAction(TranslatorView view) {
		this.view = view;
		model = view.getModel();
		
		setText("Remove translated line");
		setToolTipText("Remove translated line");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	@Override
	public void run() {
		model.removeElement();
		view.showMessage("Info", "Removed the last translation line.");
	}
	
}
