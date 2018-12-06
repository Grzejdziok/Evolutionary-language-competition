package simulation.simulation;

import com.rits.cloning.Cloner;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import simulation.language.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter @EqualsAndHashCode @ToString
public class SimulationConductor {

    private final Simulation patternSimulation;

    private Language[] languages;
    private Map<Language, ArrayList<Double>> averageWeightSums;
    private Map<Language, ArrayList<Double>> averageNumsOfUsers;
    private Map<Language, ArrayList<Double>> averageNumsOfRecognizedThings;

    private int simulationsConducted = 0;
    private int iterations = 0;
    private int interactions = 0;
    private int maxIterations = 0;
    private int unfinishedSimulations = 0;

    public SimulationConductor(Simulation patternSimulation, int initialMaxIteration) {
        this.patternSimulation = new Cloner().deepClone(patternSimulation);

        this.languages = new Language[this.patternSimulation.getLanguages().size()];

        this.averageWeightSums = new HashMap<>();
        this.averageNumsOfUsers = new HashMap<>();
        this.averageNumsOfRecognizedThings = new HashMap<>();

        int i = 0;
        for (Language language : patternSimulation.getLanguages()) {
            languages[i++] = language;

            averageWeightSums.put(language, new ArrayList<>(initialMaxIteration));
            averageNumsOfUsers.put(language, new ArrayList<>(initialMaxIteration));
            averageNumsOfRecognizedThings.put(language, new ArrayList<>(initialMaxIteration));

            if(initialMaxIteration == 0) {
                averageWeightSums.get(language).add(patternSimulation.getWeightSums().get(language));
                averageNumsOfUsers.get(language).add(patternSimulation.getNumsOfUsers().get(language).doubleValue());
                averageNumsOfRecognizedThings.get(language).add(patternSimulation.getNumsOfRecognizedThings().get(language).doubleValue());
            }
            else{
                averageWeightSums.get(language).set(0, patternSimulation.getWeightSums().get(language));
                averageNumsOfUsers.get(language).set(0, patternSimulation.getNumsOfUsers().get(language).doubleValue());
                averageNumsOfRecognizedThings.get(language).set(0, patternSimulation.getNumsOfRecognizedThings().get(language).doubleValue());
            }
        }
    }

    public SimulationConductor(Simulation patternSimulation) {
        this(patternSimulation, 0);
    }

    public void conduct(int stopIteration) {

        Simulation simulation = new Cloner().deepClone(patternSimulation);

        Map<Language, ArrayList<Double>> currentSimulationWeightSums = new HashMap<>();
        Map<Language, ArrayList<Double>> currentSimulationNumsOfUsers = new HashMap<>();
        Map<Language, ArrayList<Double>> currentSimulationNumsOfRecognizedThings = new HashMap<>();

        for (Language language : simulation.getLanguages()) {
            currentSimulationWeightSums.put(language, new ArrayList<>());
            currentSimulationNumsOfUsers.put(language, new ArrayList<>());
            currentSimulationNumsOfRecognizedThings.put(language, new ArrayList<>());
        }

        update(currentSimulationWeightSums, simulation.getWeightSums());
        update(currentSimulationNumsOfUsers, simulation.getNumsOfUsers());
        update(currentSimulationNumsOfRecognizedThings, simulation.getNumsOfRecognizedThings());

        while (!(simulation.agentsLexicallySynchronized() && simulation.oneToOneLexicons()) && simulation.getNumOfIterations() < stopIteration) {
            simulation.simulateOneStep();

            update(currentSimulationWeightSums, simulation.getWeightSums());
            update(currentSimulationNumsOfUsers, simulation.getNumsOfUsers());
            update(currentSimulationNumsOfRecognizedThings, simulation.getNumsOfRecognizedThings());
        }

        mergeToAverage(currentSimulationWeightSums, averageWeightSums);
        mergeToAverage(currentSimulationNumsOfUsers, averageNumsOfUsers);
        mergeToAverage(currentSimulationNumsOfRecognizedThings, averageNumsOfRecognizedThings);

        iterations += simulation.getNumOfIterations();
        interactions += simulation.getNumOfInteractions();

        if (simulation.getNumOfIterations() > maxIterations)
            maxIterations = simulation.getNumOfIterations();

        simulationsConducted++;

        if(simulation.getNumOfIterations() >= stopIteration)
            unfinishedSimulations++;
    }

    public void conduct(int times, int stopIteration) {
        for (int i = 0; i < times; i++)
            conduct(stopIteration);
    }

    private void update(Map<Language, ArrayList<Double>> statistics, Map<Language, ? extends Number> map) {
        for (Language language : map.keySet())
            statistics.get(language).add(map.get(language).doubleValue());
    }

    private void mergeToAverage(Map<Language, ArrayList<Double>> measurements, Map<Language, ArrayList<Double>> average) {
        for (Language language : measurements.keySet()) {
            for (int i = 0; i < Math.min(measurements.get(language).size(), average.get(language).size()); i++) {
                double oldAverage = average.get(language).get(i);
                double newElem = measurements.get(language).get(i);
                double newAverage = (simulationsConducted * oldAverage + newElem) / (simulationsConducted + 1);
                average.get(language).set(i, newAverage);
            }

            for (int i = measurements.get(language).size(); i < average.get(language).size(); i++){
                double oldAverage = average.get(language).get(i);
                double newElem = measurements.get(language).get(measurements.get(language).size()-1);
                double newAverage = (simulationsConducted * oldAverage + newElem) / (simulationsConducted + 1);
                average.get(language).set(i, newAverage);

            }

            int size = average.get(language).size();
            for(int i = size; i < measurements.get(language).size() ; i++){
                double oldAverage = average.get(language).get(size-1);
                double newElem = measurements.get(language).get(i);
                double newAverage = (simulationsConducted * oldAverage + newElem) / (simulationsConducted + 1);
                average.get(language).add(newAverage);
            }
        }

    }

    public Simulation getPatternSimulation() {
        return patternSimulation;
    }

    public int getAverageIterations() {
        return iterations / simulationsConducted;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public int getSimulationsConducted() {
        return simulationsConducted;
    }

    public double[] getAverageWeightSumsArray(Language language) {
        double[] array = new double[maxIterations];
        for (int i = 0; i < maxIterations; i++)
            array[i] = averageWeightSums.get(language).get(i);
        return array;
    }

    public double[] getAverageNumsOfUsersArray(Language language) {
        double[] array = new double[maxIterations];
        for (int i = 0; i < maxIterations; i++)
            array[i] = averageNumsOfUsers.get(language).get(i);
        return array;
    }

    public double[] getAverageNumsOfRecognizedThingsArray(Language language) {
        double[] array = new double[maxIterations];
        for (int i = 0; i < maxIterations; i++)
            array[i] = averageNumsOfRecognizedThings.get(language).get(i);
        return array;
    }

    public Language[] getLanguagesArray() {
        return languages;
    }

    public int getLanguageId(Language language) {
        int i = 0;
        for (Language lang : languages) {
            if (lang.equals(language)) return i;
            i++;
        }
        return -1;
    }

    public static void conduct(Simulation simulation, int maxIteration) {
        while (!(simulation.agentsLexicallySynchronized() && simulation.oneToOneLexicons()) && simulation.getNumOfIterations() < maxIteration)
            simulation.simulateOneStep();
    }

}
