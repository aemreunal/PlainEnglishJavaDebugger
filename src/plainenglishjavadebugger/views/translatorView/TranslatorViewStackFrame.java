package plainenglishjavadebugger.views.translatorView;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.eclipse.debug.core.model.IStackFrame;

@SuppressWarnings("serial")
public class TranslatorViewStackFrame extends JFrame {
	private TranslatorViewModel model;
	private Container contentPane;

	public TranslatorViewStackFrame(TranslatorViewModel model) {
		this.model = model;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = getContentPane();
		
		contentPane.setLayout(new FlowLayout());

		contentPane.add(new JLabel("This is the Simulation Window"),
				BorderLayout.NORTH);
		setMinimumSize(new Dimension(300, 300));
		pack();
	}
	
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}
	
	public void addStackNames(String[] stackNames) {
		removeComponentsFromContentPane();
		System.out.println("Adding Stack Names:");
		for (String stackName : stackNames) {
			System.out.println(stackName);
			contentPane.add(new JLabel(stackName));
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
