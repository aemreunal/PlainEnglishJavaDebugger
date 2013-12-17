package plainenglishjavadebugger.simulationModule;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;

import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

@SuppressWarnings("serial")
public class SimulationStackFrame extends JFrame {
	private TranslatorViewModel model;
	private Container contentPane;

	public SimulationStackFrame(TranslatorViewModel model) {
		this.model = model;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		contentPane.add(new JLabel("This is the Simulation Window"), BorderLayout.PAGE_END);
		setMinimumSize(new Dimension(300, 300));
		pack();
	}
	
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}
	
	public void addStackToFrame(IStackFrame[] stackFrames) throws DebugException {
		removeComponentsFromContentPane();
		addStackInfo(stackFrames);
	}
	
	public void addStackInfo(IStackFrame[] stackFrames) throws DebugException {
		System.out.println("Adding Stack Names:");
		for (IStackFrame stackFrame : stackFrames) {
			System.out.println(stackFrame.getName());
			SimulationStackInfoPanel infoPanel = new SimulationStackInfoPanel(stackFrame, this);
			infoPanel.initInfoPanel();
			add(infoPanel);
		}
		repaint();
		pack();
	}
	
	private void removeComponentsFromContentPane() {
		Component[] comps = contentPane.getComponents();
		for (Component component : comps) {
			contentPane.remove(component);
		}
	}
}
