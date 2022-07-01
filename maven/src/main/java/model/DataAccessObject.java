package model;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/* Singleton acts as an abstraction layer between the controller and data handling.
because at this stage of development I am unsure if a serialization into files sufices or a db access is needed */
public class DataAccessObject {
    // this basic DAO only supplies placeholders and has no backend, ideal for testing
    ArrayList<ISource> sources;
    ArrayList<IRuleDomain> domains;
    //ArrayList<IReference> references;
    ObservableList<IReference> references;

    private static DataAccessObject bridge;

    private DataAccessObject() {
        sources = new ArrayList<ISource>();
        sources.add(new Source("GURPS"));
        sources.add(new Source("Dungeons and Dragons"));
        sources.add(new Source("Shadowrun"));
        sources.add(new Source("Shadow of the Demon Lord"));
        
        domains = new ArrayList<IRuleDomain>();
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

    public ArrayList<ISource> getAvailableSources() {
        return sources;
    }

    public ArrayList<IRuleDomain> getAvailableDomains() {
        return domains;
    }

    public ObservableList<IReference> getAvailableReferences() {
        return references;
    }

    public boolean ReferenceNameValid(String name) {
        for (IReference ref : references) {
            if (ref.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean createReference(IReference ref) {
        if (!ReferenceNameValid(ref.getName())) {
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
}
