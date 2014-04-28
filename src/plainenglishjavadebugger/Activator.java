package plainenglishjavadebugger;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

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
    
    // private Bundle debugBundle;
    
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
