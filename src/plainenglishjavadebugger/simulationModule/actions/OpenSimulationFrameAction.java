package plainenglishjavadebugger.simulationModule.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import plainenglishjavadebugger.simulationModule.SimulationStackFrame;
import plainenglishjavadebugger.simulationModule.Simulator;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

public class OpenSimulationFrameAction extends Action{
	private Simulator simulator;
	private final SimulationStackFrame simulationFrame;
	
	private final String buttonText = "Open simulation frame.";
	
	public OpenSimulationFrameAction(TranslatorViewModel model) {
		simulator = model.getSimulator();
		simulationFrame = simulator.getSimulationFrame();
		
		setText(buttonText);
		setToolTipText(buttonText);
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
	}
	
	@Override
	public void run() {
		simulationFrame.setVisibility(true);
	}

}
