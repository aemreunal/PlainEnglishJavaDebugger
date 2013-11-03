package plainenglishjavadebugger.views.translatorView;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;
import org.osgi.framework.BundleException;

import plainenglishjavadebugger.views.translatorView.actions.AddLineAction;
import plainenglishjavadebugger.views.translatorView.actions.ListDoubleClickAction;
import plainenglishjavadebugger.views.translatorView.actions.RemoveLineAction;

/**
 * This sample class demonstrates how to plug-in a new workbench view. The view shows data obtained
 * from the model. The sample creates a dummy model on the fly, but a real implementation would
 * connect to the model available either in this or another plug-in (e.g. the workspace). The view
 * is connected to the model using a content provider.
 * <p>
 * The view uses a label provider to define how model objects should be presented in the view. Each view can present the same model objects using
 * different labels and icons, if needed. Alternatively, a single label provider can be shared between views in order to ensure that objects of the
 * same type are presented in the same way everywhere.
 * <p>
 */

public class TranslatorView extends ViewPart {
	
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "plainenglishjavadebugger.views.TranslatorView";
	
	private final TranslatorViewModel model;
	
	private TableViewer viewer;
	private Action addLineAction;
	private Action removeLineAction;
	private Action doubleClickAction;
	
	private String[] tableColNames = { "Step #", "Short Translation" };
	
	public TranslatorView() {
		model = new TranslatorViewModel(this);
	}
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		initTableViewer(parent);
		
		setHelpSystem();
		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}
	
	private void initTableViewer(Composite parent) {
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.V_SCROLL);
		
		initTableContent();
		initTableColumns(parent, viewer.getTable());
		initTable(viewer.getTable());
	}
	
	private void initTableContent() {
		viewer.setContentProvider(new TranslatorViewContentProvider(this));
		viewer.setLabelProvider(new TranslatorViewLabelProvider());
		viewer.setSorter(new TranslatorViewSorter());
		viewer.setInput(getViewSite());
	}
	
	private void initTable(final Table translatorViewTable) {
		translatorViewTable.setEnabled(true);
		translatorViewTable.setLinesVisible(true);
		translatorViewTable.setVisible(true);
		translatorViewTable.setHeaderVisible(true);
	}
	
	private void initTableColumns(Composite parent, final Table translatorViewTable) {
		for (int i = 0; i < tableColNames.length; i++) {
			TableColumn column = new TableColumn(translatorViewTable, SWT.NULL);
			column.setText(tableColNames[i]);
			column.setResizable(false);
			column.setMoveable(false);
			column.pack();
		}
		
		// Auto-resizing table info from: http://eclipsenuggets.blogspot.nl/2007/11/one-of-less-prominent-novelties-in.html
		TableColumnLayout layout = new TableColumnLayout();
		parent.setLayout(layout);
		layout.setColumnData(translatorViewTable.getColumn(0), new ColumnWeightData(2, translatorViewTable.getColumn(0).getWidth(), false));
		layout.setColumnData(translatorViewTable.getColumn(1), new ColumnWeightData(50, PROP_TITLE, false));
	}
	
	private void setHelpSystem() {
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(viewer.getControl(), "PlainEnglishJavaDebugger.viewer");
	}
	
	private void makeActions() {
		addLineAction = new AddLineAction(this);
		removeLineAction = new RemoveLineAction(this);
		doubleClickAction = new ListDoubleClickAction(this);
	}
	
	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				TranslatorView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}
	
	private void fillContextMenu(IMenuManager manager) {
		manager.add(addLineAction);
		manager.add(removeLineAction);
		// Other plug-ins can contribute their actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}
	
	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void fillLocalPullDown(IMenuManager manager) {
		// Adds actions to the view's drop down menu, the down-facing triangle to the top right.
		manager.add(addLineAction);
		manager.add(new Separator());
		manager.add(removeLineAction);
	}
	
	private void fillLocalToolBar(IToolBarManager manager) {
		// Adds actions to the view's tool bar as small icons on the top right, to the left of the 'minimize' button.
		manager.add(addLineAction);
		manager.add(removeLineAction);
	}
	
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
	
	@Override
	public void dispose() {
		System.out.println("In the dispose method of 'TranslatorView'");
		try {
			Platform.getBundle("PlainEnglishJavaDebugger").stop(); // DOES NOT STOP THE PLUG-IN!
			System.out.println("Stopped the plug-in.");
		} catch (BundleException e) {
			System.err.println("Unable to stop the plug-in!");
			e.printStackTrace();
		}
	}
	
	public TranslatorViewModel getModel() {
		return model;
	}
	
	public ISelection getSelection() {
		return viewer.getSelection();
	}
	
	public void refresh() {
		viewer.refresh();
	}
	
	public void showMessage(String title, String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), title, message);
	}
	
	public void showError(String title, String message) {
		MessageDialog.openError(viewer.getControl().getShell(), title, message);
	}
}