package model;

import java.net.URL;
import java.time.LocalDate;

public class SourceWeb extends Source {

    private URL website;

    public String getWebsite() {
        if (website == null) {
            return "";
        }
        return website.toString();
    }

    public URL getWebsiteUrl() {
        return this.website;
    }

    public void setWebsite(String url) {
        url = url.trim();
        if (!url.startsWith("http")) {
            url = "https://" + url; // easy fix for forgotten protocol
        }
        try {
            this.website = new URL(url);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setWebsite(URL url) {
        this.website = url;
    }

    public SourceWeb(String name, String description, String author, LocalDate accessDate, String website) {
        super(name, description, author, accessDate);
        this.setWebsite(website);
    }

    public SourceWeb() {
        super("", "", "", LocalDate.now());
        setWebsite("http://this.doesnot.exist");
    }
    
    @Override
    public boolean update(ISource updateData) {
        if (updateData instanceof SourceWeb) {
            SourceWeb updateWeb = (SourceWeb)updateData;
            this.setWebsite(updateWeb.getWebsiteUrl());
        }
        return super.update(updateData);
    }
}
