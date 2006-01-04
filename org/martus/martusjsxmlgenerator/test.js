x = 20;
EventDateTag = "EventDateTag"
AuthorTag = "AuthorTag"


this.MartusFieldSpecs = [
//function ()
//{
//	this.Tag = "Author";
//	this.Label = "";
//	this.Type = "STRING";
//	this.Value = "author-name";
//},

new StringField(EventDateTag,	"",
	function ()
	{
//		return row.get("first-name");
		return firstName + x + lastName;
	}
),

new StringField(AuthorTag, "aa", "ccc")


]



