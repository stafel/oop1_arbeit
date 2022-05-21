public class Reference implements IReference {

    String referenceType;
    String referenceIdentifier;

    String title;
    String author;
    String note;
    String year;

    Reference(String referenceType, String referenceIdentifier) {
        this.referenceType = referenceType;
        this.referenceIdentifier = referenceIdentifier;
    }

    Reference(String referenceType, String referenceIdentifier, String title, String author, String note, String year) {
        this.referenceType = referenceType;
        this.referenceIdentifier = referenceIdentifier;
        this.title = title;
        this.author = author;
        this.note = note;
        this.year = year;
    }

    protected String buildAtrStr(String atrName, String atrValue) {
        return atrName + " = \"" + atrValue + "\"";
    }

    protected String buildAtrInt(String atrName, String atrValue) {
        return atrName + " = " + atrValue;
    }

    protected String getAttributesString() {
        return buildAtrStr("title", title) + ",\n" + 
        buildAtrStr("author", author) + ",\n" + 
        buildAtrStr("note", note) + ",\n" + 
        buildAtrInt("year", year); 
    }

    @Override
    public String getReferenceString() {
        return "@" + referenceType + "\n{" + referenceIdentifier + ",\n" + getAttributesString() + "}\n";
    }
    
}
