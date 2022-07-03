package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* Singleton acts as an abstraction layer between the controller and data handling.
because at this stage of development I am unsure if a serialization into files sufices or a db access is needed */
public class DataAccessObject {
    // this basic DAO only supplies placeholders and has no backend, ideal for testing
    ObservableList<IRuleDomain> domains;
    ObservableList<IReference> references;
    ObservableList<ISource> sources;

    private Pattern numbericPattern = Pattern.compile("\\d+"); // regex searches for numerics only, no negative sign or floats

    private static DataAccessObject bridge;

    private DataAccessObject() {
        sources = FXCollections.observableArrayList(); //new ArrayList<ISource>();
        sources.add(new SourceBook("GURPS Basic Set Characters", "Character creation rules for GURPS.\n 4th edition 6th printing SJG04995", "Steve Jackson, Sean Punch, David Pulver", LocalDate.of(2016, 1, 1), "978-1-55634-729-0"));
        sources.add(new SourceBook("GURPS Basic Set Campaigns", "Mechanics and Gamemaster tips for GURPS.\n 4th edition 6th printing SJG03495", "Steve Jackson, Sean Punch, David Pulver", LocalDate.of(2016, 1, 1), "978-1-55634-730-6"));
        sources.add(new SourceBook("GURPS Magic", "Basic magic system for GURPS.\n 3rd edition 4th printing", "Steve Jackson, John Ross, Daniel Thibault", LocalDate.of(2016, 1, 1), "978-1-55634-811-2"));
        sources.add(new SourceBook("GURPS Martial Arts", "Indepth Melee combat mechanics, weapons and history", "Peter Dell'Orto, Sean Punch", LocalDate.of(2017, 1, 1), "9781556348211"));
        sources.add(new SourceBook("GURPS Thaumatology", "Alternative Magic systems", "Phil Masters", LocalDate.of(2016, 1, 1), "978-1-55634-809-9"));
        sources.add(new SourceBook("GURPS High Tech", "History and technology from 1500+.\nMore Ranged combat mechanics.", "S. A. Fisher, Michael Hurst, Hans-Christian Vortisch", LocalDate.of(2017, 1, 1), "978-1-55634-812-9"));
        
        
        domains = FXCollections.observableArrayList();
        domains.add(new RuleDomain("Social", "Rules about social encounters like talking to commoners, how to blend in social gatherings, how lies and deception are handled"));
        domains.add(new RuleDomain("Fight", "Direct physical encounter between opponents"));
        domains.add(new RuleDomain("Travel", "Moving big distances outside of combat"));
        domains.add(new RuleDomain("Character creation", "Rules and guidance about character creation and progression"));

        references = FXCollections.observableArrayList(); //new ArrayList<IReference>();
        references.add(new Reference("Basic character creation", sources.get(0), domains.get(3), "24 - 28"));
        references.add(new Reference("Optional modifications", sources.get(0), domains.get(3), "118"));
        references.add(new Reference("Overland speed", sources.get(3), domains.get(2), "30 ff."));
    }

    public static DataAccessObject getInstance() {
        if (bridge == null) {
            bridge = new DataAccessObject();
        }
        return bridge;
    }

    public ObservableList<ISource> getAvailableSources() {
        return sources;
    }

    public ObservableList<IRuleDomain> getAvailableDomains() {
        return domains;
    }

    public ObservableList<IReference> getAvailableReferences() {
        return references;
    }

    public boolean referenceNameValid(String name) {
        for (IReference ref : references) {
            if (ref.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean sourceNameValid(String name) {
        for (ISource src : sources) {
            if (src.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean domainNameValid(String name) {
        for (IRuleDomain dom : domains) {
            if (dom.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean createReference(IReference ref) {
        if (!referenceNameValid(ref.getName())) {
            return false;
        } else {
            references.add(ref);
            return true;
        }
    }

    public boolean modifyReference(IReference ref) {
        for (IReference originalRef : references) {
            if (originalRef.getName().equals(ref.getName())) {
                references.remove(originalRef); // this is a hack to force tableViews to update
                references.add(ref);
                return true;
            }
        }
        return false;
    }

    public boolean deleteReference(IReference ref) {
        // for consistency: while it only uses the name as a primary key for deletion the delete method takes the same IReference input as the create and modify
        for (IReference originalRef : references) {
            if (originalRef.getName().equals(ref.getName())) {
                references.remove(originalRef);
                return true;
            }
        }
        return false;
    }

    public ISource getSource(String name) {
        for (ISource src : sources) {
            if (src.getName().equals(name)) {
                return src;
            }
        }
        return null;
    }

    public IReference getReference(String name) {
        for (IReference ref : references) {
            if (ref.getName().equals(name)) {
                return ref;
            }
        }
        return null;
    }

    public IRuleDomain getDomain(String name) {
        for (IRuleDomain dom : domains) {
            if (dom.getName().equals(name)) {
                return dom;
            }
        }
        return null;
    }

    public boolean createSource(ISource src) {
        if (!sourceNameValid(src.getName())) {
            return false;
        } else {
            sources.add(src);
            return true;
        }
    }

    private void cascadeSourceModification(ISource oldSrc, ISource newSrc) {
        for (IReference ref : references) {
            Reference castRef = (Reference)ref;
            if (castRef.getSource().equals(oldSrc.getName())) {
                castRef.setSource(newSrc);
            }
        }
    }

    public boolean modifySource(ISource src) {
        for (ISource originalSource : sources) {
            if (originalSource.getName().equals(src.getName())) {
                cascadeSourceModification(originalSource, src);
                sources.remove(originalSource); // this is a hack to force tableViews to update
                sources.add(src);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSource(ISource src) {
        // for consistency: while it only uses the name as a primary key for deletion the delete method takes the same IReference input as the create and modify
        for (ISource originalSource : sources) {
            if (originalSource.getName().equals(src.getName())) {

                if (getReferencesForSource(src).size() > 0) {
                    return false;
                }            

                sources.remove(originalSource);
                return true;
            }
        }
        return false;
    }

    public boolean createDomain(IRuleDomain dom) {
        if (!domainNameValid(dom.getName())) {
            return false;
        } else {
            domains.add(dom);
            return true;
        }
    }

    private void cascadeDomainModification(IRuleDomain oldDom, IRuleDomain newDom) {
        for (IReference ref : references) {
            Reference castRef = (Reference)ref;
            if (castRef.getDomain().equals(oldDom.getName())) {
                castRef.setDomain(newDom);
            }
        }
    }

    public boolean modifyDomain(IRuleDomain dom) {
        for (IRuleDomain originalDomain : domains) {
            if (originalDomain.getName().equals(dom.getName())) {
                cascadeDomainModification(originalDomain, dom);
                domains.remove(originalDomain); // this is a hack to force tableViews to update
                domains.add(dom);
                return true;
            }
        }
        return false;
    }

    public boolean deleteDomain(IRuleDomain dom) {
        // for consistency: while it only uses the name as a primary key for deletion the delete method takes the same IReference input as the create and modify
        for (IRuleDomain originalDomain : domains) {
            if (originalDomain.getName().equals(dom.getName())) {

                if (getReferencesForDomain(dom).size() > 0) {
                    return false;
                }            

                domains.remove(originalDomain);
                return true;
            }
        }
        return false;
    }

    public ObservableList<IReference> getReferencesForSource(ISource src) {
        ObservableList<IReference> linkedReferences = FXCollections.observableArrayList();
        for (IReference ref : references) {
            if (ref.getSource().equals(src.getName())) { // check for names to allow weak binding later
                linkedReferences.add(ref);
            }
        }
        return linkedReferences;
    }

    public ObservableList<IReference> getReferencesForDomain(IRuleDomain dom) {
        ObservableList<IReference> linkedReferences = FXCollections.observableArrayList();
        for (IReference ref : references) {
            if (ref.getDomain().equals(dom.getName())) { // check for names to allow weak binding later
                linkedReferences.add(ref);
            }
        }
        return linkedReferences;
    }

    private boolean checkIsbn10(String isbn) {
        int multiplicator = 10;
        double result = 0;
        for (int i : isbn.chars().toArray()) {
            result += i * multiplicator;
            multiplicator--;
        }
        return (result % 11) == 0;
    }

    private boolean checkIsbn13(String isbn) {
        double result = 0;
        boolean oddChar = true;
        for (int i : isbn.chars().toArray()) {
            if (oddChar) {
                result += i * 1;
            } else {
                result += i * 3;
            }
            oddChar = !oddChar;
        }
        return (result % 10) == 0;
    }

    public boolean checkIsbnValid(String isbn) {
        String trimmedIsbn = isbn.replace('-', ' ').replaceAll("\\s", "");
        if ((trimmedIsbn.length() != 10) && (trimmedIsbn.length() != 13)) {
            return false; // size does not match isbn 10 or 13 standard
        }

        if (!numbericPattern.matcher(trimmedIsbn).matches()) {
            return false;
        }

        switch(trimmedIsbn.length()) {
            case (10):
                return checkIsbn10(trimmedIsbn);
            case (13):
                return checkIsbn13(trimmedIsbn);
            default:
                return false; // invalid size does not match
        }
    }
}
