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

	private final String fileSeperator = System.getProperty("file.separator");

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

	public String getClassName(String path) {
		System.out.println("File Path = " + path);
		String[] delimitedPath = path.split(fileSeperator);
		String className = delimitedPath[delimitedPath.length - 1];
		return className.substring(0, className.length() - 5); // It return class name without extension (Normally class name ends with ".java").
	}

	public void changeSimulationSpeed(int speed) {
		if (runner != null) {
			runner.setSleepTime(speed);
		}
	}

	public void reset() {
		simulationFrame.removeComponentsFromContentPane();
		simulationFrame.repaint();
		simulationFrame.pack();
		runner = null;
	}
}
