package plainenglishjavadebugger.simulationModule;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import plainenglishjavadebugger.simulationModule.actions.SimulationSpeedChangeListener;
import plainenglishjavadebugger.simulationModule.actions.StartSimulationButtonListener;
import plainenglishjavadebugger.simulationModule.actions.SuspendSimulationButtonListener;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

public class Simulator {
	private TranslatorViewModel model;
	private SimulationStackFrame simulationFrame;
	
	public Simulator(TranslatorViewModel model) {
		this.model = model;
		simulationFrame = new SimulationStackFrame(this);
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
	
	public void setSimulationSpeed(int speed) {
		
	}
}
