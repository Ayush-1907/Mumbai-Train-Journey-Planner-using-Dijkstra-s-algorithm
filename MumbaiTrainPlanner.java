import java.util.*;

class TrainStation {
    String name;
    String line;
    boolean isMajor; // Added field to indicate major or minor station
    Map<TrainStation, Integer> connections;

    public TrainStation(String name, String line, boolean isMajor) {
        this.name = name;
        this.line = line;
        this.isMajor = isMajor;
        this.connections = new HashMap<>();
    }

    public void addConnection(TrainStation station, int distance) {
        connections.put(station, distance);
        station.connections.put(this, distance); // Ensure bidirectional connection
    }
}

class TrainNetwork {
    Map<String, TrainStation> stations;

    public TrainNetwork() {
        this.stations = new HashMap<>();
    }

    public void addStation(String name, String line, boolean isMajor) {
        stations.put(name, new TrainStation(name, line, isMajor));
    }

    public void addSlowConnection(String station1, String station2, int distance) {
        TrainStation s1 = stations.get(station1);
        TrainStation s2 = stations.get(station2);

        if (s1 != null && s2 != null) {
            boolean areBothMajor = s1.isMajor && s2.isMajor;
            int connectionDistance = areBothMajor ? distance + 5 : distance;

            s1.addConnection(s2, connectionDistance);
        }
    }

    public void addFastConnection(String station1, String station2, int distance) {
        TrainStation s1 = stations.get(station1);
        TrainStation s2 = stations.get(station2);

        if (s1 != null && s2 != null) {
            boolean areBothMajor = s1.isMajor && s2.isMajor;
            int connectionDistance = areBothMajor ? distance : distance + 5;

            s1.addConnection(s2, connectionDistance);
        }
    }


    public Map.Entry<List<TrainStation>, Integer> shortestPath(String source, String destination) {
        if (!stations.containsKey(source) || !stations.containsKey(destination)) {
            throw new IllegalArgumentException("Invalid source or destination station");
        }

        PriorityQueue<Map.Entry<TrainStation, Integer>> queue = new PriorityQueue<>(Comparator.comparingInt(entry -> entry.getValue()));
        Map<TrainStation, Integer> distanceMap = new HashMap<>();
        Map<TrainStation, TrainStation> previousMap = new HashMap<>();

        TrainStation sourceStation = stations.get(source);
        TrainStation destinationStation = stations.get(destination);

        queue.add(new AbstractMap.SimpleEntry<>(sourceStation, 0));
        distanceMap.put(sourceStation, 0);

        while (!queue.isEmpty()) {
            Map.Entry<TrainStation, Integer> currentEntry = queue.poll();
            TrainStation currentStation = currentEntry.getKey();
            int currentDistance = currentEntry.getValue();

            if (currentStation == destinationStation) {
                break; // Reached the destination, exit the loop
            }

            for (Map.Entry<TrainStation, Integer> neighborEntry : currentStation.connections.entrySet()) {
                TrainStation neighbor = neighborEntry.getKey();
                int newDistance = currentDistance + neighborEntry.getValue();

                if (!distanceMap.containsKey(neighbor) || newDistance < distanceMap.get(neighbor)) {
                    distanceMap.put(neighbor, newDistance);
                    previousMap.put(neighbor, currentStation);
                    queue.add(new AbstractMap.SimpleEntry<>(neighbor, newDistance));
                }
            }
        }

        List<TrainStation> path = new ArrayList<>();
        TrainStation current = destinationStation;

        while (current != null) {
            path.add(current);
            current = previousMap.get(current);
        }

        Collections.reverse(path);
        int totalDistance = distanceMap.get(destinationStation);

        return new AbstractMap.SimpleEntry<>(path, totalDistance);
    }
    
}

public class MumbaiTrainPlanner {
        public static void main(String[] args) {
            TrainNetwork trainNetwork = new TrainNetwork();
            addStations(trainNetwork);
            addConnections(trainNetwork);
    
            Scanner scanner = new Scanner(System.in);
    
            // Get user input for source and destination
            System.out.print("Enter source station: ");
            String sourceStation = scanner.nextLine();
    
            System.out.print("Enter destination station: ");
            String destinationStation = scanner.nextLine();
    
            // Example usage: finding the shortest path
            Map.Entry<List<TrainStation>, Integer> result = trainNetwork.shortestPath(sourceStation, destinationStation);
    
            System.out.println("Shortest Path from " + sourceStation + " to " + destinationStation + ":");
            System.out.println("Total Distance: " + result.getValue());
            printPath(result.getKey());
            printLineChanges(result.getKey());
    
            // Close the scanner
            scanner.close();
        }
        private static void printPath(List<TrainStation> path) {
            System.out.println("Stations Visited:");
    
            for (TrainStation station : path) {
                System.out.println(station.name + " (Line: " + station.line + ")" + (station.isMajor ? " (Major)" : " (Minor)"));
            }
        }
    

    private static void addStations(TrainNetwork trainNetwork) {
        trainNetwork.addStation("Churchgate", "Western", true);
        trainNetwork.addStation("Marine Lines", "Western", false);
        trainNetwork.addStation("Charni Road", "Western", false);
        trainNetwork.addStation("Grant Road", "Western", false);
        trainNetwork.addStation("Mumbai Central", "Western", true);
        trainNetwork.addStation("Mahalakshmi", "Western", false);
        trainNetwork.addStation("Lower Parel", "Western", false);
        trainNetwork.addStation("Prabhadevi", "Western", false);
        trainNetwork.addStation("Dadar", "Western", true);
        trainNetwork.addStation("Matunga Road", "Western", false);
        trainNetwork.addStation("Mahim", "Western", false); 
        trainNetwork.addStation("Bandra", "Western", true);        
        trainNetwork.addStation("Khar Road", "Western", false); 
        trainNetwork.addStation("Santacruz", "Western", false); 
        trainNetwork.addStation("Vile Parle", "Western", false);
        trainNetwork.addStation("Andheri", "Western", true);
        trainNetwork.addStation("Jogeshwari", "Western", false);   
        trainNetwork.addStation("Ram Mandir", "Western", false);
        trainNetwork.addStation("Goregaon", "Western", false);
        trainNetwork.addStation("Malad", "Western", false);
        trainNetwork.addStation("Kandivali", "Western", false);
        trainNetwork.addStation("Borivali", "Western", true);
        trainNetwork.addStation("Dahisar", "Western", false);
        trainNetwork.addStation("Mira Road", "Western", false);
        trainNetwork.addStation("Bhayander", "Western", true);
        trainNetwork.addStation("Naingaon", "Western", false);    
        trainNetwork.addStation("Vasai Road", "Western",true);
        trainNetwork.addStation("Nalla Sopara", "Western", false);
        trainNetwork.addStation("Virar", "Western", true);
        trainNetwork.addStation("Vaitarna", "Western", true);  
        trainNetwork.addStation("Saphale", "Western", true);        
        trainNetwork.addStation("Kelva Road", "Western", true);
        trainNetwork.addStation("Palghar", "Western", true);
        trainNetwork.addStation("Umroli Road", "Western", true);
        trainNetwork.addStation("Boisar", "Western", true);
        trainNetwork.addStation("Vangaon", "Western", true);
        trainNetwork.addStation("Dahanu Road", "Western", true);
        trainNetwork.addStation("CSMT", "Central", true);
        trainNetwork.addStation("Masjid", "Central", false);
        trainNetwork.addStation("Sandhurst Road", "Central", false);
        trainNetwork.addStation("Byculla", "Central", true);
        trainNetwork.addStation("Chinchpokli", "Central", false);
        trainNetwork.addStation("Currey Road", "Central", false);
        trainNetwork.addStation("Parel", "Central", false);
        trainNetwork.addStation("Dadar Central", "Central", true);
        trainNetwork.addStation("Matunga", "Central", false);
        trainNetwork.addStation("Sion", "Central", false);
        trainNetwork.addStation("Kurla", "Central", true);
        trainNetwork.addStation("Vidhyavihar", "Central", false);
        trainNetwork.addStation("Ghatkopar", "Central", true);
        trainNetwork.addStation("Vikhroli", "Central", false);
        trainNetwork.addStation("Kanjur Marg", "Central", false);
        trainNetwork.addStation("Bhandup", "Central", false);
        trainNetwork.addStation("Nahur", "Central", false);
        trainNetwork.addStation("Mulund", "Central", false);
        trainNetwork.addStation("Thane", "Central", true);
        trainNetwork.addStation("Kalva", "Central", false);
        trainNetwork.addStation("Mumbra", "Central", false);
        trainNetwork.addStation("Diva JN", "Central", false);
        trainNetwork.addStation("Kopar", "Central", false);
        trainNetwork.addStation("Dombivali", "Central", true);
        trainNetwork.addStation("Thakurli", "Central", false);
        trainNetwork.addStation("Kalyan", "Central", true);
        trainNetwork.addStation("Vithalwadi", "Central", false);
        trainNetwork.addStation("Ulhas Nagar", "Central", false);
        trainNetwork.addStation("Ambernath", "Central", false);
        trainNetwork.addStation("Badlapur", "Central", false);
        trainNetwork.addStation("Vangani", "Central", false);
        trainNetwork.addStation("Shelu", "Central", false);
        trainNetwork.addStation("Neral", "Central", false);
        trainNetwork.addStation("Bhivpuri Road", "Central", false);
        trainNetwork.addStation("Karjat", "Central", false);
        trainNetwork.addStation("Palasdhari", "Central", false);
        trainNetwork.addStation("Kelavali", "Central", false);
        trainNetwork.addStation("Dolavali", "Central", false);
        trainNetwork.addStation("Lowjee", "Central", false);
        trainNetwork.addStation("Khopoli", "Central", true);
        trainNetwork.addStation("Shahad", "Central", false);
        trainNetwork.addStation("Ambivali", "Central", false);
        trainNetwork.addStation("Titwala", "Central", false);
        trainNetwork.addStation("Khadavali", "Central", false);
        trainNetwork.addStation("Vasind", "Central", false);
        trainNetwork.addStation("Asangaon", "Central", false);
        trainNetwork.addStation("Atagaon", "Central", false);
        trainNetwork.addStation("Thansit", "Central", false);
        trainNetwork.addStation("Khardi", "Central", false);
        trainNetwork.addStation("Umbermali", "Central", false);
        trainNetwork.addStation("Kasara", "Central", true);

    }

    private static void addConnections(TrainNetwork trainNetwork) {
        trainNetwork.addSlowConnection("Churchgate", "Marine Lines", 3);
        trainNetwork.addSlowConnection("Marine lines", "Charni Road", 2);
        trainNetwork.addSlowConnection("Charni Road", "Grant Road", 3);
        trainNetwork.addSlowConnection("Grant Road", "Mumbai Central", 2);
        trainNetwork.addSlowConnection("Mumbai Central", "Mahalakashmi", 3);
        trainNetwork.addSlowConnection("Mahalakshmi", "Lower Parel", 3);
        trainNetwork.addSlowConnection("Lower Parel", "Prabhadevi", 3);
        trainNetwork.addSlowConnection("Prabhadevi", "Dadar", 2);
        trainNetwork.addSlowConnection("Dadar", "Matunga Road", 2);
        trainNetwork.addSlowConnection("Matunga Road", "Mahim", 3);
        trainNetwork.addSlowConnection("Mahim", "Bandra", 4);
        trainNetwork.addSlowConnection("Bandra", "Khar Road", 3);
        trainNetwork.addSlowConnection("Khar Road", "Santacruz", 2);
        trainNetwork.addSlowConnection("Santacruz", "Vile Parle", 3);
        trainNetwork.addSlowConnection("Vile Parle", "Andheri", 4);
        trainNetwork.addSlowConnection("Andheri", "Jogeshwari", 3);
        trainNetwork.addSlowConnection("Jogeshwari", "Ram Mandir", 3);
        trainNetwork.addSlowConnection("Ram Mandir", "Goregaon", 2);
        trainNetwork.addSlowConnection("Goregaon", "Malad", 4);
        trainNetwork.addSlowConnection("Malad", "Kandivali", 3);
        trainNetwork.addSlowConnection("Kandivali", "Borivali", 5);
        trainNetwork.addSlowConnection("Borivali", "Dahisar", 4);
        trainNetwork.addSlowConnection("Dahisar", "Mira Road", 5);
        trainNetwork.addSlowConnection("Mira Road", "Bhayander", 5);
        trainNetwork.addSlowConnection("Bhayander", "Naingaon", 6);
        trainNetwork.addSlowConnection("Naingaon", "Vasai Road", 5);
        trainNetwork.addSlowConnection("Vasai Road", "Nalla Sopara", 5);
        trainNetwork.addSlowConnection("Nalla Sopara", "Virar", 7);
        trainNetwork.addSlowConnection("Virar", "Vaitarna", 10);
        trainNetwork.addSlowConnection("Vaitarna", "Saphale", 8);
        trainNetwork.addSlowConnection("Saphale", "Kelva Road", 7);
        trainNetwork.addSlowConnection("Kelva Road", "Palghar", 16);
        trainNetwork.addSlowConnection("Palghar", "Umroli Road", 6);
        trainNetwork.addSlowConnection("Umroli Road", "Boisar", 6);
        trainNetwork.addSlowConnection("Boisar", "Vangaon", 9);
        trainNetwork.addSlowConnection("Vangaon", "Dahanu Road", 15);
        trainNetwork.addFastConnection("Churchgate", "Mumbai Central", 7);
        trainNetwork.addFastConnection("Mumbai Central", "Dadar", 7);
        trainNetwork.addFastConnection("Dadar", "Bandra", 6);
        trainNetwork.addFastConnection("Bandra", "Andheri", 8);
        trainNetwork.addFastConnection("Andheri", "Borivali", 15);
        trainNetwork.addFastConnection("Borivali", "Bhayander", 9);
        trainNetwork.addFastConnection("Bhayander", "Vasai Road", 9);
        trainNetwork.addFastConnection("Vasai Road", "Virar", 8);
        trainNetwork.addSlowConnection("Dadar", "Dadar Central", 5);
        trainNetwork.addSlowConnection("CSMT", "Masjid", 3);
        trainNetwork.addSlowConnection("Masjid", "Sandhurst Road", 2);
        trainNetwork.addSlowConnection("Sandhurst Road", "Byculla", 3);
        trainNetwork.addSlowConnection("Byculla", "Chinchpokli", 3);
        trainNetwork.addSlowConnection("Chinchpokli", "Currey Road", 3);
        trainNetwork.addSlowConnection("Currey Road", "Parel", 3);
        trainNetwork.addSlowConnection("Parel", "Dadar Central", 3);
        trainNetwork.addSlowConnection("Dadar Central", "Matunga", 3);
        trainNetwork.addSlowConnection("Matunga", "Sion", 3);
        trainNetwork.addSlowConnection("Sion", "Kurla", 4);
        trainNetwork.addSlowConnection("Kurla", "Vidhyavihar", 3);
        trainNetwork.addSlowConnection("Vidhyavihar", "Ghatkopar", 3);
        trainNetwork.addSlowConnection("Ghatkopar", "Vikhroli", 4);
        trainNetwork.addSlowConnection("Vikhroli", "Kanjur Marg", 3);
        trainNetwork.addSlowConnection("Kanjur Marg", "Bhandup", 3);
        trainNetwork.addSlowConnection("Bhandup", "Nahur", 3);
        trainNetwork.addSlowConnection("Nahur", "Mulund", 3);
        trainNetwork.addSlowConnection("Mulund", "Thane", 5);
        trainNetwork.addSlowConnection("Thane", "Kalva", 5);
        trainNetwork.addSlowConnection("Kalva", "Mumbra", 6);
        trainNetwork.addSlowConnection("Mumbra", "Diva JN", 4);
        trainNetwork.addSlowConnection("Diva JN", "Kopar", 5);
        trainNetwork.addSlowConnection("Kopar", "Dombivali", 3);
        trainNetwork.addSlowConnection("Dombivali", "Thakurli", 4);
        trainNetwork.addSlowConnection("Thakurli", "Kalyan", 7);
        trainNetwork.addSlowConnection("Kalyan", "Vithalwadi", 4);
        trainNetwork.addSlowConnection("Vithalwadi", "Ulhas Nagar", 3);
        trainNetwork.addSlowConnection("Ulhas Nagar", "Ambernath", 4);
        trainNetwork.addSlowConnection("Ambernath", "Badlapur", 7);
        trainNetwork.addSlowConnection("Badlapur", "Vangani", 9);
        trainNetwork.addSlowConnection("Vangani", "Shelu", 4);
        trainNetwork.addSlowConnection("Shelu", "Neral", 4);
        trainNetwork.addSlowConnection("Neral", "Bhivpuri Road", 7);
        trainNetwork.addSlowConnection("Bhivpuri Road", "Karjat", 10);
        trainNetwork.addSlowConnection("Karjat", "Palasdhari", 5);
        trainNetwork.addSlowConnection("Palasdhari", "Kelavali", 7);
        trainNetwork.addSlowConnection("Kelavali", "Dolavali", 3);
        trainNetwork.addSlowConnection("Dolavali", "Lowjee", 4);
        trainNetwork.addSlowConnection("Lowjee", "Khopoli", 6);
        trainNetwork.addSlowConnection("Kalyan", "Shahad", 4);
        trainNetwork.addSlowConnection("Shahad", "Ambivali", 3);
        trainNetwork.addSlowConnection("Ambivali", "Titwala", 6);
        trainNetwork.addSlowConnection("Titwala", "Khadavali", 7);
        trainNetwork.addSlowConnection("Khadavali", "Vasind", 7);
        trainNetwork.addSlowConnection("Vasind", "Asangaon", 6);
        trainNetwork.addSlowConnection("Asangaon", "Atagaon", 9);
        trainNetwork.addSlowConnection("Atagaon", "Thansit", 4);
        trainNetwork.addSlowConnection("Thansit", "Khardi", 6);
        trainNetwork.addSlowConnection("Khardi", "Umbermali", 5);
        trainNetwork.addSlowConnection("Umbermali", "Kasara", 13);
        trainNetwork.addFastConnection("CSMT","Byculla",6);
        trainNetwork.addFastConnection("Byculla","Dadar Central",6);
        trainNetwork.addFastConnection("Dadar Central","Kurla",7);
        trainNetwork.addFastConnection("Kurla","Ghatkopar",4);
        trainNetwork.addFastConnection("Ghatkopar","Thane",16);
        trainNetwork.addFastConnection("Thane","Domvivali",17);
        trainNetwork.addFastConnection("Dombivali","Kalyan",6);

    }

    

    private static void printLineChanges(List<TrainStation> path) {
        for (int i = 0; i < path.size() - 1; i++) {
            TrainStation current = path.get(i);
            TrainStation next = path.get(i + 1);

            if (!current.line.equals(next.line)) {
                System.out.println("\nLine Changes:");
                System.out.println("Change from " + current.line + " line to " + next.line + " line at " + current.name);
            }
        }
    }
}
