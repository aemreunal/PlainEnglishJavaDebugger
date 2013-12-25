package plainenglishjavadebugger.simulationModule.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.simulationModule.Simulator;

/*
 * This code belongs to:
 * ‚elebi Murat
 * S001751
 * celebi.murat@ozu.edu.tr
 */




public class StartSimulationButtonListener implements ActionListener {
	private Simulator simulator;
	private SimulationRunner runner;

	public StartSimulationButtonListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		simulator.startSimulation();
	}
	
	public SimulationRunner getSimulationRunner() {
		return runner;
	}
}
