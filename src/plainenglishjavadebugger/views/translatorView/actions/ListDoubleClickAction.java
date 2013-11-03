package plainenglishjavadebugger.views.translatorView.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import plainenglishjavadebugger.views.translatorView.TranslatedLine;
import plainenglishjavadebugger.views.translatorView.TranslatorView;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class ListDoubleClickAction extends Action {
	private final TranslatorView translatorView;
	
	public ListDoubleClickAction(TranslatorView translatorView) {
		this.translatorView = translatorView;
	}
	
	@Override
	public void run() {
		ISelection selection = translatorView.getSelection();
		TranslatedLine translatedLine = (TranslatedLine) ((IStructuredSelection) selection).getFirstElement();
		translatorView.showMessage("Translation #" + translatedLine.getTranslationNumber(), translatedLine.getPopupMessage());
	}
	
}
