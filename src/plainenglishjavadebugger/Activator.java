package plainenglishjavadebugger;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;

/*
 * Services: http://help.eclipse.org/kepler/topic/org.eclipse.platform.doc.isv/guide/wrkAdv_services.htm?cp=2_0_12_13
 */

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "PlainEnglishJavaDebugger"; //$NON-NLS-1$
	
	// The shared instance
	private static Activator plugin;
	
	private Bundle debugBundle;
	
	public Activator() {
		System.out.println("Inside the Activator constructor.");
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		// http://help.eclipse.org/kepler/index.jsp?topic=%2Forg.eclipse.platform.doc.isv%2Fguide%2Fdebug_model.htm
		// IExpression must be used to access the expression text.
		
		super.start(context);
		plugin = this;
		System.out.println("Inside the \"start\" of the plug-in.");
		
		// Check if the debugger is running, otherwise quit
		debugBundle = Platform.getBundle("org.eclipse.jdt.debug");
		if (debugBundle.getState() == BundleEvent.STARTED) {
			// then start it
		} else {
			// shut the plug-in down with an error message pop-up
			System.err.println("Debugger not running!");
			// ErrorDialog.openError(this.getWorkbench().getActiveWorkbenchWindow().getShell(), "Debugger error",
			// "The debugger is not running!\n\nThe debugger should be running for the Translator plug-in.");
			// this.stop(context); // Stopping is not working!
		}
		
		context.addBundleListener(new SynchronousBundleListener() {
			// We can add both BundleEvent.STARTED & BundleEvent.UPDATED to the listener.
			@Override
			public void bundleChanged(BundleEvent bundleEvent) {
				System.out.println("State of the bundle " + bundleEvent.getBundle().getSymbolicName() + " has changed to " + bundleEvent.getType());
				if (bundleEvent.getBundle() == debugBundle) {
					int bundleState = bundleEvent.getType();
					System.out.println("Debugger changed state to: " + bundleState);
					if (bundleState == BundleEvent.STARTED) {
						System.out.println("Inside the 'BundleListener's 'bundleChanged' method! Debugger has started.");
					} else if (bundleState == BundleEvent.UPDATED) {
						System.out.println("Inside the 'BundleListener's 'bundleChanged' method! Debugger has been updated.");
					} else if (bundleState == BundleEvent.STOPPED) {
						System.out.println("Inside the 'BundleListener's 'bundleChanged' method! Debugger has stopped.");
					}
				}
				
				if (bundleEvent.getBundle().getSymbolicName().equals("org.eclipse.jdt.debug.ui")) {
					System.out.println("State of the Debug UI bundle changed to: " + bundleEvent.getType());
				}
			}
		});
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		System.out.println("Inside the \"stop\" of the plug-in.");
	}
	
	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}
	
	/**
	 * Returns an image descriptor for the image file at the given plug-in relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
