//test.js Version 1.0
//Requires Martus 2.9

//You must have the following Required Fields. 
//MartusRequiredLanguageField
//MartusRequiredAuthorField
//MartusRequiredTitleField
//MartusRequiredDateCreatedField
//MartusRequiredPrivateField

//Here are some of the Martus Default Fields
//MartusSummaryField
//MartusLocationField
//MartusOrganizationField
//MartusDetailsField
//MartusKeywordsField
//MartusDateOfEventField 

//Here are the optional fields you can use
//StringField
//MultilineField
//SingleDateField
//DateRangeField
//DropDownField
//BooleanField
//MessageField


//Define static variables here if you want to reference them my name further on in the script

DATE_RANGE_DELIMETER = "_"
WitnessTag = "Witness" 
CommentTag = "WitnessComment" 

//This is the field spec we are creating and use for each Martusbulletin
this.MartusFieldSpecs = [

//Add all Fields you want now in the Martus Bulletin in the order you want them to appear.
//You can put the MartusRequiredPrivateField anywhere, it will always get added to the end of your Martus Bulletin.

new StringField(WitnessTag,	"Witness",
	function ()
	{
		return firstname + " " + lastname;
	}
),

new StringField(CommentTag, "Comment", "comment"),

new MartusRequiredLanguageField("language"),

new MultilineField("MultiLineTag", "Description of Situation", 
	function ()
	{
		return "line1\nline2\n";
	}
),
	
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

//DateRange must be in the format StartDate_EndDate
//The DateFormat Field is optional if the dates are in the MartusDefaultFormat yyyy-MM-dd
new DateRangeField("EventOccuredTag",	"Event Occured",
	function ()
	{
		return event_date_start + DATE_RANGE_DELIMETER + event_date_end;
	}
	, "MMddyyyy"
),

new SingleDateField("StartDateTag",	"Start Date",
	function ()
	{
		return event_date_start;
	}
	, "MMddyyyy"
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



