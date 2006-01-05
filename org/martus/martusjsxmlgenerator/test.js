AuthorTag = "Author"
MyTitleTag = "MyTitle"

this.MartusFieldSpecs = [

new StringField(AuthorTag,	"",
	function ()
	{
		return firstname + " " + lastname;
	}
),

new StringField(MyTitleTag, "My Title", "title"),

new PrivateField(
	function ()
	{
		return "MY PRIVATE DATE = " + data2;
	}
)

]



