package plainenglishjavadebugger.simulationModule;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

public class Simulator {
	private TranslatorViewModel model;
	private SimulationStackFrame simulationFrame;
	
	public Simulator(TranslatorViewModel model) {
		this.model = model;
		simulationFrame = new SimulationStackFrame(this);
	}
	
	private void init() {
		
	}
	
	public void addStackToFrame(IStackFrame[] stackFrames) throws DebugException {
		simulationFrame.addStackToFrame(stackFrames);
	}
	
	public TranslatorViewModel getModel() {
		return model;
	}

	public SimulationStackFrame getSimulationFrame() {
		return simulationFrame;
	}
	
	public SimulationRunner getSimulationRunner() {
		return simulationFrame.getStartListener().getSimulationRunner();
	}
}
