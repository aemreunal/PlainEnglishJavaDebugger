package plainenglishjavadebugger.simulationModule;

import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * Celebi Murat
 * S001751
 * celebi.murat@ozu.edu.tr
 */

public class SimulationRunner extends Thread {
	TranslatorViewModel model;
	DebugEventListener debugEventListener;
	private boolean isSimulating = false;
	private int sleepTime = 1000;
	
	public SimulationRunner(TranslatorViewModel model) {
		this.model = model;
		debugEventListener = model.getEventListener();
	}
	
	@Override
	public void run() {
		while (debugEventListener.getInDebugState()) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (isSimulating) {
				model.nextStep();
			}
		}
		System.out.println("Runner finished");
	}
	
	public synchronized void setSleepTime(int sleepTime) {
		this.sleepTime = sleepTime;
	}
	
	public boolean isSimulating() {
		return isSimulating;
	}
	
	public synchronized void setIsSimulating(boolean isSimulating) {
		this.isSimulating = isSimulating;
	}
}
