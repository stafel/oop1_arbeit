public class NetReference extends Reference {

    String url;

    NetReference(String referenceIdentifier, String title, String author, String note, String year, String url) {
        super("misc", referenceIdentifier, title, author, note, year);
        this.url = url;
    }
    
    @Override
    protected String getAttributesString() {
        return super.getAttributesString() + ",\n" + buildAtrStr("howpublished", "\\url{" + url + "}");
    }
}
