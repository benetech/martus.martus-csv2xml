x = 20;
EventDate = "EventDate"
Author = "Author"


this.MartusFieldSpecs = [
//function ()
//{
//	this.Tag = "Author";
//	this.Label = "";
//	this.Type = "STRING";
//	this.Value = "author-name";
//},

new StringField(EventDate,	"",
	function ()
	{
//		return row.get("first-name");
7
		return firstName + x + lastName;
	}
),

new StringField(Author, "aa", "ccc")


]



