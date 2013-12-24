package plainenglishjavadebugger.simulationModule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import plainenglishjavadebugger.simulationModule.actions.StartSimulationButtonListener;
import plainenglishjavadebugger.simulationModule.actions.SuspendSimulationButtonListener;

@SuppressWarnings("serial")
public class SimulationStackFrame extends JFrame {
	private Simulator simulator;
	private Container container;
	private StartSimulationButtonListener startListener;
	private SuspendSimulationButtonListener suspendListener;

	public SimulationStackFrame(Simulator simulator) {
		this.simulator = simulator;

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		init();
		pack();
	}

	private void init() {
		setContainer();
		setListeners();
		setMenuBar(new JMenuBar());
		setMinimumSize(new Dimension(300, 100));
		setPreferredSize(new Dimension(300, 400));
	}
	
	private void setContainer() {
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(new JLabel("This is the Simulation Window"),
				BorderLayout.PAGE_END);
		
		add(new JScrollPane(container));
	}
	
	private void setListeners() {
		startListener = new StartSimulationButtonListener(simulator);
		suspendListener = new SuspendSimulationButtonListener(simulator);
	}
	
	private void setMenuBar(JMenuBar menuBar) {
		populateMenuBar(menuBar);
		setJMenuBar(menuBar);
	}
	
	private void populateMenuBar(JMenuBar menuBar) {
		JButton startSimulationButton = new JButton("Start");
		JButton stopSimulationButton = new JButton("Stop");
		
//		ImageIcon startIcon = new ImageIcon().setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT));
		
		stopSimulationButton.addActionListener(suspendListener);
		startSimulationButton.addActionListener(startListener);
		
		menuBar.add(startSimulationButton);
		menuBar.add(stopSimulationButton);
		
	}
	
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}

	public void addStackToFrame(IStackFrame[] stackFrames)
			throws DebugException {
		removeComponentsFromContentPane();
		addStackInfo(stackFrames);
	}

	public void addStackInfo(IStackFrame[] stackFrames) throws DebugException {
		System.out.println("Adding Stack Names:");
		for (IStackFrame stackFrame : stackFrames) {
			System.out.println(stackFrame.getName());
			SimulationStackInfoPanel infoPanel = new SimulationStackInfoPanel(
					stackFrame, this);
			infoPanel.initInfoPanel();
			container.add(infoPanel);
		}
		repaint();
		pack();
	}

	private void removeComponentsFromContentPane() {
		Component[] comps = container.getComponents();
		for (Component component : comps) {
			container.remove(component);
		}
	}
	
	public Container getContainer() {
		return container;
	}

	public StartSimulationButtonListener getStartListener() {
		return startListener;
	}

	public SuspendSimulationButtonListener getSuspendListener() {
		return suspendListener;
	}
}
