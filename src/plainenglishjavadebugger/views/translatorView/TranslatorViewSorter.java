package plainenglishjavadebugger.views.translatorView;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class TranslatorViewSorter extends ViewerSorter {

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		if (((TranslatedLine) e1).getTranslationNumber() < ((TranslatedLine) e2).getTranslationNumber()) {
			return 1;
		} else {
			return -1;
		}
	}
}
