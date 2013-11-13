package plainenglishjavadebugger.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import plainenglishjavadebugger.views.translatorView.TranslatorView;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be
 * delegated to it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class MenuActionSet implements IWorkbenchWindowActionDelegate {
	public static final String ID = "PlainEnglishJavaDebugger.views.translatorView.TranslatorView";
	
	private IWorkbenchWindow window;
	
	/**
	 * The constructor.
	 */
	public MenuActionSet() {
		
	}
	
	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	
	@Override
	public void run(IAction action) {
		// MessageDialog.openInformation(window.getShell(), "Plain English Java Debugger", "Hello, Eclipse world");
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().activate(new TranslatorView());
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(TranslatorView.ID);
		} catch (PartInitException e) {
			System.err.println("Unable to show the Translator View!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Selection in the workbench has been changed. We
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after
	 * the delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}
	
	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	@Override
	public void dispose() {
	}
	
	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}