package plainenglishjavadebugger.views.translatorView;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import plainenglishjavadebugger.translationModule.TranslatedLine;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class TranslatorViewLabelProvider extends LabelProvider implements ITableLabelProvider {
	/*
	 * (non-Javadoc)
	 * LabelProviders provide the Strings for a given object to be displayed on the table.
	 */
	
	@Override
	public String getColumnText(Object obj, int index) {
		TranslatedLine translatedLine = (TranslatedLine) obj;
		switch (index) {
		case 0:
			return String.valueOf(translatedLine.getTranslationNumber());
		case 1:
			return translatedLine.getShortDescription();
		default:
			return "NULL";
		}
	}
	
	// For loops, there should be small loop icons, and so on.
	@Override
	public Image getColumnImage(Object obj, int index) {
		switch (index) {
		case 0:
			return null;
		case 1:
			return ((TranslatedLine) obj).getImage();
		default:
			return null;
		}
	}
}
