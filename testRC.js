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
		return "Test Organization";
	}
),

new MartusDetailsField("id"),

new MartusKeywordsField(
	function ()
	{
		return id +", "+ data2;
	}
),

new MartusDateOfEventField (
	function ()
	{
		return event_date_start + DATE_RANGE_DELIMETER + event_date_end;
	}, "MMddyyyy"
),

new MartusRequiredPrivateField(
	function ()
	{
		return "MY PRIVATE DATE = " + data2;
	}
),

new DropDownField("gun_tag", "Where guns Used?", "guns", ["Yes","No","Unknown"]),

new BooleanField("anonymous_tag", "Does interviewee wish to remain anonymous?", "anonymous"),

new MessageField("MessageProfession", "Profession History Table Note", 
	function()
	{
		return "If you have information about a person who has had different professions over time, enter multiple rows with the same First and Last Names and show the date ranges for each profession on a separate row.";
	}
)
]


