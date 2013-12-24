package plainenglishjavadebugger.simulationModule;

import plainenglishjavadebugger.actions.DebugEventListener;
import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

public class SimulationRunner extends Thread{
	TranslatorViewModel model;
	DebugEventListener debugEventListener;
	private int sleepTime = 1000;
	
	public SimulationRunner(TranslatorViewModel model){
		this.model = model;
		debugEventListener = model.getEventListener();
	}
	
	public void run() {
		while (debugEventListener.getInDebugState()) {
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Cannot sleep");
				e.printStackTrace();
			}
			System.out.println("Runner stepping");
			model.stepOver();
		}
		System.out.println("Runner finished");
	}
	
	public synchronized void setSleepTime(int sleepTime) {
		sleepTime = sleepTime;
	}
}
