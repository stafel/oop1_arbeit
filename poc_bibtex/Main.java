public class Main {
    public static void main(String[] args) {
        NetReference netref = new NetReference("PLUTO:1",
         "Pluto: The non planet",
          "NASA", "Probably true facts",
           "2022",
            "https://solarsystem.nasa.gov/planets/dwarf-planets/pluto/overview/");

        System.out.println(netref.getReferenceString());
    }
}