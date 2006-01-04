x = 20;
EventDateTag = "EventDate"
TitleTag = "Title"
AuthorTag = "Author"


this.MartusFieldSpecs = [

new StringField(AuthorTag,	"",
	function ()
	{
		return firstname + x + lastname;
	}
),

new StringField(TitleTag, "aa", "title")


]



