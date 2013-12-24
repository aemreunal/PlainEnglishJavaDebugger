package plainenglishjavadebugger.simulationModule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

@SuppressWarnings("serial")
public class SimulationStackFrame extends JFrame {
	private TranslatorViewModel model;
	private Container container;

	public SimulationStackFrame(TranslatorViewModel model) {
		this.model = model;

		setDefaultCloseOperation(HIDE_ON_CLOSE);
		init();
		pack();
	}

	private void init() {
		setMenuBar();
		setContainer();
		setMinimumSize(new Dimension(300, 100));
		setPreferredSize(new Dimension(300, 400));
	}
	
	private void setMenuBar() {
		JMenu menu = new JMenu();
		
		JButton button = new JButton();
	}
	
	private void setContainer() {
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(new JLabel("This is the Simulation Window"),
				BorderLayout.PAGE_END);
		
		add(new JScrollPane(container));
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
}
