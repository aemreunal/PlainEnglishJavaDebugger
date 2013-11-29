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

public class StepOverTranslateAction extends Action {
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	private final String buttonText = "Step Over and Translate";
	
	public StepOverTranslateAction(TranslatorView view) {
		this.view = view;
		model = view.getModel();
		
		setText(buttonText);
		setToolTipText(buttonText);
		// setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_FORWARD));
	}
	
	@Override
	public void run() {
		if (model.isDebugging()) {
			// model.getThreadInfo();
			// try {
			// model.getThread().stepOver();
			// } catch (DebugException e) {
			// System.err.println("Unable to step over the line!");
			// e.printStackTrace();
			// }
			// model.addElement(new TranslatedLine("System.out.println(\"Hello\")", "Hello", "Hellooooo"));
			// view.showMessage("Info", "Added new translation line.");
		}
	}
	
}
