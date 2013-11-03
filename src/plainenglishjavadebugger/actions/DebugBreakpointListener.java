package plainenglishjavadebugger.actions;

import org.eclipse.debug.core.DebugException;
import org.eclipse.jdt.core.dom.Message;
import org.eclipse.jdt.debug.core.IJavaBreakpoint;
import org.eclipse.jdt.debug.core.IJavaBreakpointListener;
import org.eclipse.jdt.debug.core.IJavaDebugTarget;
import org.eclipse.jdt.debug.core.IJavaLineBreakpoint;
import org.eclipse.jdt.debug.core.IJavaThread;
import org.eclipse.jdt.debug.core.IJavaType;
import org.eclipse.jdt.debug.core.JDIDebugModel;

import plainenglishjavadebugger.views.translatorView.TranslatorViewModel;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class DebugBreakpointListener implements IJavaBreakpointListener {
	private final TranslatorViewModel model;
	
	private IJavaThread thread;
	private IJavaBreakpoint breakpoint;
	
	private int numBreakpoints = 0;
	
	public DebugBreakpointListener(TranslatorViewModel model) {
		this.model = model;
		JDIDebugModel.addJavaBreakpointListener(this);
		// DebugPlugin.getDefault().isDebugging();
		// JDIDebugPlugin.getDefault().isDebugging();
		// JDIDebugPlugin.getDefault().getBundle().get;
	}
	
	@Override
	public void addingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// System.out.println("Inside the addingBreakpoint method");
		numBreakpoints++;
		if (numBreakpoints == 1) {
			System.out.println("The debugger has started!");
		}
	}
	
	@Override
	public int installingBreakpoint(IJavaDebugTarget arg0, IJavaBreakpoint arg1, IJavaType arg2) {
		// System.out.println("Inside the installingBreakpoint method");
		return 0;
	}
	
	@Override
	public void breakpointInstalled(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// System.out.println("Inside the breakpointInstalled method");
	}
	
	@Override
	public int breakpointHit(IJavaThread thread, IJavaBreakpoint breakpoint) {
		System.out.println("Just hit a breakpoint!");
		setDebugInfo(thread, breakpoint);
		return 0;
	}
	
	private void setDebugInfo(IJavaThread thread, IJavaBreakpoint breakpoint) {
		this.thread = thread;
		this.breakpoint = breakpoint;
		model.setDebugInfo(thread, breakpoint);
	}
	
	@Override
	public void breakpointHasCompilationErrors(IJavaLineBreakpoint arg0, Message[] arg1) {
		// System.out.println("Inside the breakpointHasCompilationErrors method");
	}
	
	@Override
	public void breakpointHasRuntimeException(IJavaLineBreakpoint arg0, DebugException arg1) {
		// System.out.println("Inside the breakpointHasRuntimeException method");
	}
	
	@Override
	public void breakpointRemoved(IJavaDebugTarget arg0, IJavaBreakpoint arg1) {
		// System.out.println("Inside the breakpointRemoved method");
		numBreakpoints--;
		if (numBreakpoints == 0) {
			System.out.println("The debugger has stopped!");
		}
	}
	
}
