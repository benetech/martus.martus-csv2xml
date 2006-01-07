DATE_RANGE_DELIMETER = "_"

WitnessTag = "Witness"
CommentTag = "WitnessComment"

this.MartusFieldSpecs = [
new StringField(WitnessTag,	"Witness",
	function ()
	{
		return firstname + " " + lastname;
	}
),

new StringField(CommentTag, "Comment", "comment"),

new MartusRequiredLanguageField("language"),
	
new MartusRequiredAuthorField("author"),

new MartusRequiredTitleField("title"),

new MartusRequiredDateCreatedField("entrydate", "yyyyMMdd"),

new MartusSummaryField("data2"),

new MartusLocationField("comment"),

new MartusOrganizationField(	
	function ()
	{
		return "XYZ NGO";
	}
),

new MartusDetailsField("id"),

new MartusKeywordsField(
	function ()
	{
		return id +", "+ data2;
	}
),

//DateRange must be in the format StartDate_EndDate_dateFormat
new MartusDateOfEventField(
	function ()
	{
		return event_date_start + DATE_RANGE_DELIMETER + event_date_end + DATE_RANGE_DELIMETER + "MMddyyyy";
	}
),

new MartusRequiredPrivateField(
	function ()
	{
		return "MY PRIVATE DATE = " + data2;
	}
)

]



