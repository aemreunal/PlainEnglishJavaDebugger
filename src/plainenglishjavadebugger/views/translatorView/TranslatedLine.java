package plainenglishjavadebugger.views.translatorView;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/*
 * This code belongs to:
 * Ahmet Emre Unal
 * S001974
 * emre.unal@ozu.edu.tr
 */

public class TranslatedLine {
	private static int itemNumber = 0;
	
	private final int translationStepNumber;
	private final String translatedCode;
	private final String shortDescription;
	private final String longDescription;
	private final String debugEventType;
	private String popupMessage = "";
	
	public TranslatedLine(String translatedCode, String shortDescription, String longDescription, String debugEventType) {
		TranslatedLine.itemNumber++;
		translationStepNumber = TranslatedLine.itemNumber;
		this.translatedCode = translatedCode;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.debugEventType = debugEventType;
		createPopupMessage();
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
	
	public String getDebugEventType() {
		return debugEventType;
	}
	
	private void createPopupMessage() {
		popupMessage += "Step #" + translationStepNumber + "\n\n";
		popupMessage += "Debug event: " + debugEventType + "\n\n";
		popupMessage += "The translated line of code:\n" + translatedCode + "\n\n";
		popupMessage += getLongDescription();
	}
	
	public String getPopupMessage() {
		return popupMessage;
	}
	
	public Image getImage() {
		// Returns a dummy image for now. Later on, it should return an icon depending on the translation.
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
}
