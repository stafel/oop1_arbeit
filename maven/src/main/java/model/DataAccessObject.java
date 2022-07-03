package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* Singleton acts as an abstraction layer between the controller and data handling.
because at this stage of development I am unsure if a serialization into files sufices or a db access is needed */
public class DataAccessObject {
    // this basic DAO only supplies placeholders and has no backend, ideal for testing
    ObservableList<IRuleDomain> domains;
    ObservableList<IReference> references;
    ObservableList<ISource> sources;

    private static DataAccessObject bridge;

    private DataAccessObject() {
        sources = FXCollections.observableArrayList(); //new ArrayList<ISource>();
        sources.add(new SourceBook("GURPS Basic Set Characters", "Character creation rules for GURPS. 4th edition 6th printing SJG04995", "Steve Jackson, Sean Punch, David Pulver", "2016", "978-1-55634-729-0"));
        sources.add(new SourceBook("GURPS Basic Set Campaigns", "Mechanics and Gamemaster tips for GURPS. 4th edition 6th printing SJG03495", "Steve Jackson, Sean Punch, David Pulver", "2016", "978-1-55634-730-6"));
        sources.add(new SourceBook("GURPS Magic", "Basic magic system for GURPS 3rd edition 4th printing", "Steve Jackson, John Ross, Daniel Thibault", "2016", "978-1-55634-811-2"));
        sources.add(new SourceBook("GURPS Martial Arts", "Indepth Melee combat mechanics, weapons and history", "Peter Dell'Orto, Sean Punch", "2017", "9781556348211"));
        sources.add(new SourceBook("GURPS Thaumatology", "Alternative Magic systems", "Phil Masters", "2016", "978-1-55634-809-9"));
        sources.add(new SourceBook("GURPS High Tech", "More Ranged combat mechanics. History and technology from 1500+", "S. A. Fisher, Michael Hurst, Hans-Christian Vortisch", "2017", "978-1-55634-812-9"));
        
        
        domains = FXCollections.observableArrayList();
        domains.add(new RuleDomain("Social"));
        domains.add(new RuleDomain("Fight"));
        domains.add(new RuleDomain("Travel"));
        domains.add(new RuleDomain("Character creation"));

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
                references.remove(originalRef);
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

    public boolean sourceNameValid(String name) {
        for (ISource src : sources) {
            if (src.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean createSource(ISource src) {
        if (!referenceNameValid(src.getName())) {
            return false;
        } else {
            sources.add(src);
            return true;
        }
    }

    public boolean modifySource(ISource src) {
        for (ISource originalSource : sources) {
            if (originalSource.getName().equals(src.getName())) {
                originalSource.update(src);
                return true;
            }
        }
        return false;
    }

    public boolean deleteSource(ISource src) {
        // for consistency: while it only uses the name as a primary key for deletion the delete method takes the same IReference input as the create and modify
        for (ISource originalSource : sources) {
            if (originalSource.getName().equals(src.getName())) {
                sources.remove(originalSource);
                return true;
            }
        }
        return false;
    }
}
