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

public class AddLineAction extends Action {
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	public AddLineAction(TranslatorView view) {
		this.view = view;
		model = view.getModel();
		
		setText("Add translated line");
		setToolTipText("Add translated line");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	@Override
	public void run() {
		// model.addElement(new TranslatedLine("System.out.println(\"Hello\")", "Hello", "Hellooooo"));
		// view.showMessage("Info", "Added new translation line.");
		model.getThreadInfo();
	}
	
}
