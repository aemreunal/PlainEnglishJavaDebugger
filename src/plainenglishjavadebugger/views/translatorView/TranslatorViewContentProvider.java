package plainenglishjavadebugger.views.translatorView;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

/*
 * The content provider class is responsible for providing objects to the view. It can wrap
 * existing objects in adapters or simply return objects as-is. These objects may be sensitive
 * to the current input of the view, or ignore it and always show the same content (like Task
 * List, for example).
 */

public class TranslatorViewContentProvider implements IStructuredContentProvider {
	@SuppressWarnings("unused")
	private final TranslatorView view;
	private final TranslatorViewModel model;
	
	public TranslatorViewContentProvider(TranslatorView view) {
		this.view = view;
		model = view.getModel();
	}
	
	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	}
	
	@Override
	public void dispose() {
	}
	
	// This is where you feed the translatedLine objects to the TranslatorView
	@Override
	public Object[] getElements(Object parent) {
		return model.getElements().toArray();
	}
}