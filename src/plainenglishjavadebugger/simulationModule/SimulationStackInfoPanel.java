package plainenglishjavadebugger.simulationModule;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IVariable;

public class SimulationStackInfoPanel extends JPanel{
	private IStackFrame stackInfo;
	private Container parent;
	public SimulationStackInfoPanel(IStackFrame stackInfo, Container parent) {
		this.stackInfo = stackInfo;
		this.parent = parent;
	}
	public void initInfoPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		try {
			setBorder();
			addVariables();
		} catch (DebugException e) {
			e.printStackTrace();
		}
		repaint();
		revalidate();
	}
	
	private void setBorder() throws DebugException {
		TitledBorder border = BorderFactory.createTitledBorder(stackInfo.getName());
		setBorder(border);
	}
	
	private void addVariables() throws DebugException {
		JPanel variablePanel = new JPanel();
		variablePanel.setLayout(new BoxLayout(variablePanel, BoxLayout.Y_AXIS));
		for (IVariable variable : stackInfo.getVariables()) {
			variablePanel.add(new JLabel(getNameWithSpaces(variable)), BorderLayout.PAGE_END);
		}
		variablePanel.repaint();
		add(variablePanel);
	}
	
	private String getNameWithSpaces(IVariable variable) throws DebugException {
		String variableInfo = variable.getName() + " = " + variable.getValue();
		int lenghtDifference = stackInfo.getName().length() - variableInfo.length();
		if(lenghtDifference > 0) {
			for (int i = 0; i < lenghtDifference; i++) {
				variableInfo += " ";
			}
		}
		return variableInfo + "  ";
	}
}
