package plainenglishjavadebugger.simulationModule.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.simulationModule.Simulator;

public class StartSimulationButtonListener implements ActionListener {
	private Simulator simulator;
	private SimulationRunner runner;

	public StartSimulationButtonListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (runner == null) {
			runner = new SimulationRunner(simulator.getModel());
			runner.setIsSimulating(true);
			runner.start();
		} else if(!runner.isSimulating()) {
			runner.setIsSimulating(true);
		}
	}
	
	public SimulationRunner getSimulationRunner() {
		return runner;
	}
}
