package karstenroethig.parkinglot.webapp.util;

import lombok.Getter;

public enum MessageKeyEnum
{
	APPLICATION_VERSION( "application.version" ),

	/* ********************
	 * Company.
	 * ********************
	 */
	/** "Company > Hinzufügen": Eingaben nicht valide. */
	COMPANY_SAVE_INVALID( "company.save.invalid" ),

	/** "Company > Hinzufügen": Erfolgreich gespeichert. */
	COMPANY_SAVE_SUCCESS( "company.save.success" ),

	/** "Company > Hinzufügen": Unerwarteter Fehler ist aufgetreten. */
	COMPANY_SAVE_ERROR( "company.save.error" ),

	/** "Company > Bearbeiten": Eingaben nicht vailde. */
	COMPANY_UPDATE_INVALID( "company.update.invalid" ),

	/** "Company > Bearbeiten": Erfolgreich gespeichert. */
	COMPANY_UPDATE_SUCCESS( "company.update.success" ),

	/** "Company > Bearbeiten": Unerwarteter Fehler ist aufgetreten. */
	COMPANY_UPDATE_ERROR( "company.update.error" ),

	/** "Company > Löschen": Erfolgreich gelöscht. */
	COMPANY_DELETE_SUCCESS( "company.delete.success" ),

	/** "Company > Löschen": Unerwarteter Fehler ist aufgetreten. */
	COMPANY_DELETE_ERROR( "company.delete.error" ),

	/* ********************
	 * Offer Position.
	 * ********************
	 */
	/** "Offer Position > Hinzufügen": Eingaben nicht valide. */
	OFFER_POSITION_SAVE_INVALID( "offerPosition.save.invalid" ),

	/** "Offer Position > Hinzufügen": Erfolgreich gespeichert. */
	OFFER_POSITION_SAVE_SUCCESS( "offerPosition.save.success" ),

	/** "Offer Position > Hinzufügen": Unerwarteter Fehler ist aufgetreten. */
	OFFER_POSITION_SAVE_ERROR( "offerPosition.save.error" ),

	/** "Offer Position > Bearbeiten": Eingaben nicht vailde. */
	OFFER_POSITION_UPDATE_INVALID( "offerPosition.update.invalid" ),

	/** "Offer Position > Bearbeiten": Erfolgreich gespeichert. */
	OFFER_POSITION_UPDATE_SUCCESS( "offerPosition.update.success" ),

	/** "Offer Position > Bearbeiten": Unerwarteter Fehler ist aufgetreten. */
	OFFER_POSITION_UPDATE_ERROR( "offerPosition.update.error" ),

	/** "Offer Position > Löschen": Erfolgreich gelöscht. */
	OFFER_POSITION_DELETE_SUCCESS( "offerPosition.delete.success" ),

	/** "Offer Position > Löschen": Unerwarteter Fehler ist aufgetreten. */
	OFFER_POSITION_DELETE_ERROR( "offerPosition.delete.error" );

	@Getter
	private String key;

	private MessageKeyEnum( String key )
	{
		this.key = key;
	}
}
