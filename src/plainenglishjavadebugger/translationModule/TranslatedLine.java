package plainenglishjavadebugger.translationModule;

import org.eclipse.debug.core.DebugEvent;
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
	private String translatedCode = "";
	private String shortDescription = "";
	private String longDescription = "";
	private String debugEventType;
	private String popupMessage = "";
	
	private StatementType statementType;
	
	public TranslatedLine(String translatedCode, int debugEventType) {
		this(translatedCode, "", "", debugEventType);
	}
	
	public TranslatedLine(String translatedCode, String shortDescription, String longDescription, int debugEventType) {
		TranslatedLine.itemNumber++;
		translationStepNumber = TranslatedLine.itemNumber;
		this.translatedCode = translatedCode;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.debugEventType = getDebugEventTypeName(debugEventType);
	}
	
	private String getDebugEventTypeName(int debugEventType) {
		switch (debugEventType) {
		case DebugEvent.STEP_OVER:
			return "Stepped over the line";
		case DebugEvent.STEP_INTO:
			return "Stepped into the line";
		default:
			return "Undetermined debug event.";
		}
	}
	
	private void createPopupMessage() {
		popupMessage = "";
		popupMessage += "- Step #" + translationStepNumber + "\n\n";
		popupMessage += "- Debug event: " + debugEventType + "\n\n";
		popupMessage += "- The translated line of code:\n\t" + translatedCode + "\n\n";
		popupMessage += getLongDescription();
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
	
	public String getPopupMessage() {
		createPopupMessage();
		return popupMessage;
	}
	
	public Image getImage() {
		// Returns a dummy image for now. Later on, it should return an icon depending on the translation.
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
	
	/**
	 * @param shortDescription
	 *            the shortDescription to set
	 */
	public synchronized void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	/**
	 * @param longDescription
	 *            the longDescription to set
	 */
	public synchronized void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	
	public synchronized void appendToLongDescription(String longDescription) {
		this.longDescription += (" " + longDescription);
	}
	
	public synchronized void appendLinkToLongDescription(String statementType, String link) {
		longDescription += "\n\nFor more information on " + statementType + ", please visit:\n" + link;
	}
	
	/**
	 * @return the statementType
	 */
	public StatementType getStatementType() {
		return statementType;
	}
	
	/**
	 * @param statementType
	 *            the statementType to set
	 */
	public synchronized void setStatementType(StatementType statementType) {
		this.statementType = statementType;
	}
	
	public static void resetItemNumbering() {
		TranslatedLine.itemNumber = 0;
	}
}
