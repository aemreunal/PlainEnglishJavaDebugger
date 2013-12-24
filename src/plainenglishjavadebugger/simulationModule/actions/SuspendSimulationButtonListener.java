package plainenglishjavadebugger.simulationModule.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import plainenglishjavadebugger.simulationModule.SimulationRunner;
import plainenglishjavadebugger.simulationModule.Simulator;

public class SuspendSimulationButtonListener implements ActionListener{
	private Simulator simulator;
	private SimulationRunner runner;

	public SuspendSimulationButtonListener(Simulator simulator) {
		this.simulator = simulator;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		runner = simulator.getSimulationRunner();
		if( !((runner == null) || (!runner.isSimulating()))) { 
			//This is because if first statement is false second one should be checked. Statement equals to ((runner not null) and (runner is simulating))
			
			runner.setIsSimulating(false);
		}
	}
}
