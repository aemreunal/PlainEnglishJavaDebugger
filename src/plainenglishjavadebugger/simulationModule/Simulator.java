package plainenglishjavadebugger.simulationModule;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * ‚elebi Murat
 * S001751
 * celebi.murat@ozu.edu.tr
 */

public class Simulator {
	private TranslatorViewModel model;
	private SimulationStackFrame simulationFrame;
	private SimulationRunner runner;
	private String[] systemDefinedMethods = { "doPrivileged",
			"retargetMouseEvent", "processMouseEvent", "dispatchEvent",
			"dispatchEventImpl", "doIntersectionPrivilege",
			"pumpOneEventForFilters", "pumpEventsForFilter",
			"pumpEventsForHierarchy", "pumpEvents", "access$400" };

	DebugEventListener debugEventListener;

	public Simulator(TranslatorViewModel model) {
		this.model = model;
		debugEventListener = model.getEventListener();
		simulationFrame = new SimulationStackFrame(this);
	}

	public void addStackToFrame(IStackFrame[] stackFrames)
			throws DebugException {
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

	public void startSimulation() {
		if (runner == null) {
			runner = new SimulationRunner(model);
			runner.setIsSimulating(true);
			runner.start();
		} else if (!runner.isSimulating()) {
			runner.setIsSimulating(true);
		}
	}

	public void suspendSimulation() {
		if (runner != null && runner.isSimulating()) {
			runner.setIsSimulating(false);
		}
	}

	public boolean isMethodNecessary(String methodName) {
		for (String systemMethod : systemDefinedMethods) {
			if (methodName.equals(systemMethod)) {
				return false;
			}
		}
		return true;
	}

	public void changeSimulationSpeed(int speed) {
		if (runner != null) {
			runner.setSleepTime(speed);
		}
	}

	public void reset() {
		simulationFrame.removeComponentsFromContentPane();
		runner = null;
	}
}
