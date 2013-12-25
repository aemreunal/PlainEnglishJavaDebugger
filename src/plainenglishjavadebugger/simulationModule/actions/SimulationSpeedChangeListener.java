package plainenglishjavadebugger.simulationModule.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.simulationModule.Simulator;

/*
 * This code belongs to:
 * ‚elebi Murat
 * S001751
 * celebi.murat@ozu.edu.tr
 */




public class SimulationSpeedChangeListener implements ActionListener{

	private Simulator simulator;

	public SimulationSpeedChangeListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String speed = JOptionPane.showInputDialog("Simulation Speed(ms)");
		simulator.changeSimulationSpeed(Integer.parseInt(speed));
	}

}
