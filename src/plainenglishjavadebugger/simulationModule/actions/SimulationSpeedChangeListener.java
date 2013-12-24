package plainenglishjavadebugger.simulationModule.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.simulationModule.Simulator;

public class SimulationSpeedChangeListener implements ActionListener{

	private Simulator simulator;
	private SimulationRunner runner;

	public SimulationSpeedChangeListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String speed = JOptionPane.showInputDialog("Simulation Speed(ms)");
		runner = simulator.getSimulationRunner();
		if (runner != null) {
			runner.setSleepTime(Integer.parseInt(speed));
		}
	}

}
