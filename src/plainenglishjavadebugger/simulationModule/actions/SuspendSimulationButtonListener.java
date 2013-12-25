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




public class SuspendSimulationButtonListener implements ActionListener {
	private Simulator simulator;

	public SuspendSimulationButtonListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		simulator.suspendSimulation();
	}
}
