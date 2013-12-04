package plainenglishjavadebugger.views.translatorView.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import plainenglishjavadebugger.views.translatorView.TranslatorView;
import plainenglishjavadebugger.views.translatorView.TranslatorViewStackFrame;

public class OpenSimulationFrameAction extends Action{
	
	private final TranslatorView view;
	private final TranslatorViewStackFrame simulationFrame;
	
	private final String buttonText = "Open simulation frame.";
	
	public OpenSimulationFrameAction(TranslatorView view) {
		this.view = view;
		simulationFrame = view.getSimulationFrame();
		
		setText(buttonText);
		setToolTipText(buttonText);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
	}
	
	@Override
	public void run() {
		simulationFrame.setVisibility(true);
	}

}
