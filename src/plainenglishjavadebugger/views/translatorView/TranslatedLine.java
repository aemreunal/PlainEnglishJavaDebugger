package plainenglishjavadebugger.views.translatorView;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * ---------------------------
 * emre.unal@ozu.edu.tr
 * barlas.karahocaoglu@ozu.edu.tr
 * celebi.murat@ozu.edu.tr
 */

public class TranslatedLine {
	private static int itemNumber = 0;
	
	private int translationStepNumber;
	private String translatedCode;
	private String shortDescription;
	private String longDescription;
	
	public TranslatedLine(String translatedCode, String shortDescription, String longDescription) {
		TranslatedLine.itemNumber++;
		translationStepNumber = TranslatedLine.itemNumber;
		this.translatedCode = translatedCode;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}
	
	public int getTranslationNumber() {
		return translationStepNumber;
	}
	
	public String getTranslatedCode() {
		return translatedCode;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	
	public String getPopupMessage() {
		String message = "Step #" + translationStepNumber + "\n\n";
		message += "The translated line of code:\n" + translatedCode + "\n\n";
		message += getLongDescription();
		return message;
	}
	
	public Image getImage() {
		// Returns a dummy image for now. Later on, it should return an icon depending on the translation.
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
}
