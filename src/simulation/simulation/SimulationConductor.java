package simulation.simulation;

import com.rits.cloning.Cloner;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import simulation.language.Language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class for conducting a simulation with given parameters multiple number of times so that its results can be averaged.
 * <p>
 * A {@code SimulationConductor} object gathers averaged statistics for the set of simulations conducted by it. They are accessible
 * with appropriate getters. Statistic are gathered for each language in the pattern simulation of a conductor.
 * A {@code SimulationConductor} holds pointers to languages in an array, and therefore each language can be identified with an id (index in the array).
 * @see Simulation
 * @see Language
 * @see SimulationConductorSerializer
 */
@EqualsAndHashCode @ToString
public class SimulationConductor {

    private final Simulation patternSimulation;

    private Language[] languages;
    private Map<Language, ArrayList<Double>> averageWeightSums;
    private Map<Language, ArrayList<Double>> averageNumsOfUsers;
    private Map<Language, ArrayList<Double>> averageNumsOfRecognizedThings;

    private int simulationsConducted = 0;
    private int iterations = 0;
    private int interactions = 0;
    private int maxSimulationsLength = 0;
    private int unfinishedSimulations = 0;

    private SimulationConductor(Simulation patternSimulation, int initialMaxIteration) {
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

    /**
     * Initializes this conductor with the given simulation.
     * @param patternSimulation the simulation to be conducted multiple times by this conductor.
     */
    public SimulationConductor(Simulation patternSimulation) {
        this(patternSimulation, 0);
    }

    /**
     * Conducts this conductor's {@code patternSimulation} until the simulation's population reaches lexicon identity
     * or {@code stopIteration} are conducted. During the conductment, statistics are gathered and at the end, they are merged
     * to averaged statistics held by this conductor.
     * @param stopIteration maximal number of iterations for this conductment
     */
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

        if (simulation.getNumOfIterations() > maxSimulationsLength)
            maxSimulationsLength = simulation.getNumOfIterations();

        simulationsConducted++;

        if(simulation.getNumOfIterations() >= stopIteration)
            unfinishedSimulations++;
    }

    /**
     * Conducts this conductor's {@code patternSimulation} multiple times.
     * It is equivalent to invoking {@code this.conduct(stopIteration} {@code times} times.
     * @param times number of times to conduct the {@code patternSimulation}
     * @param stopIteration maximal number of iterations for this conductment
     */
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

    /**
     * Returns the average length in iterations of simulations conducted by this conductor.
     * @return the average length in iterations of simulations conducted by this conductor
     */
    public int getAverageIterations() {
        return iterations / simulationsConducted;
    }

    /**
     * Returns an array of weight sums of the given language in each iteration averaged over all simulations conducted by this conductor.
     * @param language the language of which statistics should be returned
     * @return an array of weight sums of the given language in each iteration averaged over all simulations conducted by this conductor.
     */
    public double[] getAverageWeightSumsArray(Language language) {
        double[] array = new double[maxSimulationsLength];
        for (int i = 0; i < maxSimulationsLength; i++)
            array[i] = averageWeightSums.get(language).get(i);
        return array;
    }

    /**
     * Returns an array of numbers of users of the given language in each iteration averaged over all simulations conducted by this conductor.
     * @param language the language of which statistics should be returned
     * @return an array of numbers of users of the given language in each iteration averaged over all simulations conducted by this conductor.
     */
    public double[] getAverageNumsOfUsersArray(Language language) {
        double[] array = new double[maxSimulationsLength];
        for (int i = 0; i < maxSimulationsLength; i++)
            array[i] = averageNumsOfUsers.get(language).get(i);
        return array;
    }

    /**
     * Returns an array of numbers of recognized things of the given language in each iteration averaged over all simulations conducted by this conductor.
     * @param language the language of which statistics should be returned
     * @return an array of numbers of recognized things of the given language in each iteration averaged over all simulations conducted by this conductor.
     */
    public double[] getAverageNumsOfRecognizedThingsArray(Language language) {
        double[] array = new double[maxSimulationsLength];
        for (int i = 0; i < maxSimulationsLength; i++)
            array[i] = averageNumsOfRecognizedThings.get(language).get(i);
        return array;
    }

    /**
     * Returns an array of languages of this conductr's {@code patternSimulation}
     * @return an array of languages of this conductr's {@code patternSimulation}
     */
    public Language[] getLanguagesArray() {
        return languages;
    }

    /**
     * Returns the id of the given language in this conductor's array of languages.
     * @param language the language of which id should be returned
     * @return the id of the given language in this conductor's array of languages.
     */
    public int getLanguageId(Language language) {
        int i = 0;
        for (Language lang : languages) {
            if (lang.equals(language)) return i;
            i++;
        }
        return -1;
    }

    public Simulation getPatternSimulation() {
        return this.patternSimulation;
    }

    public Language[] getLanguages() {
        return this.languages;
    }

    public Map<Language, ArrayList<Double>> getAverageWeightSums() {
        return this.averageWeightSums;
    }

    public Map<Language, ArrayList<Double>> getAverageNumsOfUsers() {
        return this.averageNumsOfUsers;
    }

    public Map<Language, ArrayList<Double>> getAverageNumsOfRecognizedThings() {
        return this.averageNumsOfRecognizedThings;
    }

    public int getSimulationsConducted() {
        return this.simulationsConducted;
    }

    public int getIterations() {
        return this.iterations;
    }

    public int getInteractions() {
        return this.interactions;
    }

    public int getMaxSimulationsLength() {
        return this.maxSimulationsLength;
    }

    public int getUnfinishedSimulations() {
        return this.unfinishedSimulations;
    }
}
