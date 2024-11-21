/**
 * Class represents solar panels, street map, and
 * an array of parking lot projects.
 * 
 * @author Jessica De Brito
 * @author Kal Pandit
 */
public class SolarPanels {
    
    private Panel[][] panels;
    private String[][] streetMap;
    private ParkingLot[] lots;


    /**
     * Default constructor: initializes empty panels and objects.
     */
    public SolarPanels() {
        panels = new Panel[0][0];
        streetMap = null;
        lots = null;
        StdRandom.setSeed(2023);
    }

    /**
     * Updates the instance variable streetMap to be an l x w
     * array of Strings. Reads each label from input file in parameters.
     * 
     * @param streetMapFile the input file to read from
     */
    public void setupStreetMap(String streetMapFile) {
        StdIn.setFile(streetMapFile);
        int length = StdIn.readInt();
        int width = StdIn.readInt();
    
        this.streetMap = new String[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                this.streetMap[i][j] = StdIn.readString();
            }
        }
        System.out.println("Street Map Set Up:");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(this.streetMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Adds parking lot information to an array of parking lots.
     * Updates the instance variable lots to store these parking lots.
     * 
     * @param parkingLotFile the lot input file to read
     */
    public void setupParkingLots(String parkingLotFile) {
        StdIn.setFile(parkingLotFile);
        int n = StdIn.readInt();
        this.lots = new ParkingLot[n];

        for (int i = 0; i < n; i++) {
            String name = StdIn.readString();
            int maxPanels = StdIn.readInt();
            double budget = StdIn.readDouble();
            int energyCapacity = StdIn.readInt();
            double panelEfficiency = StdIn.readDouble();
    
            
            this.lots[i] = new ParkingLot(name, maxPanels, budget, energyCapacity, panelEfficiency);
        }
    
       System.out.println("Parking Lots Set up:");
       System.out.println("Lots:");
       for (int i = 0; i < lots.length; i++) {
        System.out.println(i + ": " + lots[i]);
    }

    }

    /**
     * Insert panels on each lot as much as space and budget allows.
     * Updates the instance variable panels to be a 2D array parallel to
     * streetMap, storing panels placed.
     * 
     * Panels have a 95% chance of working. Use StdRandom.uniform(); if
     * the resulting value is < 0.95 the panel works.
     * 
     * @param costPerPanel the fixed cost per panel, as a double
     */
    public void insertPanels(double costPerPanel) {
        panels = new Panel[streetMap.length][streetMap[0].length];

        System.out.println("Cost per panel: $" + costPerPanel);
        System.out.println("Panels Inserted:");
    
        for (ParkingLot lot : lots) {
            int currentPanels = 0;
            double currentBudget = lot.getBudget();
    
            for (int i = 0; i < streetMap.length; i++) {
                for (int j = 0; j < streetMap[i].length; j++) {
                    if (streetMap[i][j].equals(lot.getLotName()) && currentPanels < lot.getMaxPanels()) {
                        if (currentBudget >= costPerPanel) {
                            boolean works = StdRandom.uniform() < 0.95;
                            panels[i][j] = new Panel(lot.getPanelEfficiency(), lot.getEnergyCapacity(), works);
                            currentPanels++;
                            currentBudget -= costPerPanel;
    
                            // Print the panel's details
                            System.out.printf("Index [%d][%d]: {Efficiency: %.2f, Energy: %.2f, Status: %s}%n",
                                    i, j,
                                    panels[i][j].getRatedEfficiency(),
                                    panels[i][j].getMaxOutput(),
                                    works ? "Works" : "Broken");
                        }
                    }
                }
            }
        }
    
        // Print locations without panels
        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                if (panels[i][j] == null) {
                    System.out.printf("Index [%d][%d]: No panel here!%n", i, j);
                }
            }
        }
    }


    /**
     * Given a temperature and coefficient, update panels' actual efficiency
     * values. Panels are most optimal at 77 degrees F.
     * 
     * Panels perform worse in hotter environments and better in colder ones.
     * worse = efficiency loss, better = efficiency gain.
     * 
     * Coefficients are usually negative to represent energy loss.
     * 
     * @param temperature the current temperature, in degrees F
     * @param coefficient the coefficient to use
     */
    public void updateActualEfficiency(int temperature, double coefficient) {
        double efficiencyChange = coefficient * (temperature - 77);
    
        for (int i = 0; i < panels.length; i++) {
            if (panels[i] == null) {
                System.out.println("Row " + i + " is null!");
                continue;
            }
            for (int j = 0; j < panels[i].length; j++) {
                Panel panel = panels[i][j];
                if (panel != null) {
                    double newEfficiency = panel.getRatedEfficiency() + efficiencyChange;
                    newEfficiency = Math.max(0, Math.min(100, newEfficiency)); 
                    panel.setActualEfficiency(newEfficiency);
                    System.out.printf("Panel [%d][%d]: RatedEff: %.2f, NewEff: %.2f, ElecGen: %.0f, MaxOut: %.0f, Status: %s%n",
                                      i, j,
                                      panel.getRatedEfficiency(), 
                                      newEfficiency,
                                      panel.getElectricityGenerated(),
                                      panel.getMaxOutput(),
                                      panel.isWorking() ? "Works" : "Broken");
                } else {
                    System.out.println("No panel at position [" + i + "][" + j + "]");
                }
            }
        }
        }
    

    /**
     * For each WORKING panel, update the electricity generated for 4 hours 
     * of sunlight as follows:
     * 
     * (actual efficiency / 100) * 1500 * 4
     * 
     * RUN updateActualEfficiency BEFORE running this method.
     */
    public void updateElectricityGenerated() {
        //double powerOutput = (actualEfficiency/ 100) * 1500;
    }

    /**
     * Count the number of working panels in a parking lot.
     * 
     * @param parkingLot the parking lot name
     * @return the number of working panels
     */
    public int countWorkingPanels(String parkingLot) {
        // WRITE YOUR CODE HERE
        return -1; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /**
     * Find the broken panels in the map and repair them.
     * @return the count of working panels in total, after repair
     */
    public int updateWorkingPanels() {
        // WRITE YOUR CODE HERE
        return -1; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /**
     * Calculate Rutgers' savings on energy by using
     * these solar panels.
     * 
     * ASSUME:
     * - Multiply total electricity generated by 0.001 to convert to KwH.
     * - There are 365 days in a year.
     * 
     * RUN electricityGenerated before running this method.
     */
    public double calculateSavings() {
        // WRITE YOUR CODE HERE
        return -1; // PLACEHOLDER TO AVOID COMPILATION ERROR - REPLACE WITH YOUR CODE
    }

    /*
     * Getter and Setter methods
     */
    public Panel[][] getPanels() {
        // DO NOT TOUCH THIS METHOD
        return this.panels;
    }

    public void setPanels(Panel[][] panels) {
        // DO NOT TOUCH THIS METHOD
        this.panels = panels;
    }

    public String[][] getStreetMap() {
        // DO NOT TOUCH THIS METHOD
        return this.streetMap;
    }

    public void setStreetMap(String[][] streetMap) {
        // DO NOT TOUCH THIS METHOD
        this.streetMap = streetMap;
    }

    public ParkingLot[] getLots() {
        // DO NOT TOUCH THIS METHOD
        return this.lots;
    }

    public void setLots(ParkingLot[] lots) {
        // DO NOT TOUCH THIS METHOD
        this.lots = lots;
    }
}

