import cli.ELCPaperOptionsCreator;
import org.apache.commons.cli.*;
import simulation.simulation.Simulation;
import simulation.simulation.SimulationConductor;
import simulation.simulation.SimulationConductorSerializer;
import simulation.simulation.creator.ELCPaperSimulationCreator;

import java.io.IOException;

/**
 * This class is provided as a main class to conduct simulations of computer model described in the "Evolutionary Language Competition - an agent-based model" paper. Please see the paper for more details. It is available <a href="https://github.com/Grzejdziok/Evolutionary-language-competition">here</a>.
 * <p>
 * The class contains only {@code main} method, which pparses command-line arguments, conducts simulations with specified parameters, and saves results to a JSON file.
 */

public class Main {

    /**
     * A method that parses arguments, conducts simulations with specified parameters, and saves results to a JSON file.
     * <p>
     * All arguments that can be provided are described below:
     * <ul>
     * <li> {@code -n} defines the side of the agents' lattice graph (default: {@code 4}) </li>
     * <li> {@code -N} defines the number of agents (default: {@code n*n})</li>
     * <li> {@code -N1} defines the initial size of the 1-lingual population (default: {@code N/2})</li>
     * <li> {@code -N2} defines the initial size of the 2-lingual population (default: {@code N-N1})</li>
     * <li> {@code -d} defines the number of objects (default: {@code 5})</li>
     * <li> {@code -d1} defines the initial environment (number of recognized objects with the words belonging to a language) of the 1-lingual population (default: {@code 5})</li>
     * <li> {@code -d2} defines the initial environemnt of the 2-lingual population (default: {@code 5})</li>
     * <li> {@code -eps} defines the epsilon parameter (default: {@code 0.05})</li>
     * <li> {@code -var} defines the model variant (with string argument from the {{@code "zero"}, {@code "ts"}, {@code "tl"}} set) (default: {@code "zero"})</li>
     * <li> {@code -v} defines the variant influence (default: {@code 0})</li>
     * <li> {@code -iv} defines the variant language (from the set {{@code 1}, {@code 2}}) (default: {@code 1})</li>
     * <li> {@code -p} defines the json results file path (default: {@code "results.json"})</li>
     * <li> {@code -s} defines the number of independent simulations to be conducted (default: {@code 1000})</li>
     * <li> {@code -stop} defines the maximal number of iterations to simulate in every independent simulation (default: {@code 100000})</li>
     * </ul>
     *
     * @see ELCPaperOptionsCreator
     * @see Simulation
     * @see SimulationConductor
     * @param args command-line arguments specyfing the details of a simulation to conduct
     */
    public static void main(String args[]){

        Options options = new ELCPaperOptionsCreator().create();

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        int things = Integer.parseInt(commandLine.getOptionValue("d", "5"));
        int devdegree1 = Integer.parseInt(commandLine.getOptionValue("d1", String.valueOf(things)));
        int devdegree2 = Integer.parseInt(commandLine.getOptionValue("d2", String.valueOf(things)));

        int side = Integer.parseInt(commandLine.getOptionValue("n", "4"));

        int agents = Integer.parseInt(commandLine.getOptionValue("N", String.valueOf(side*side)));
        int agents1 = Integer.parseInt(commandLine.getOptionValue("N1", String.valueOf(side*side/2)));
        int agents2 = Integer.parseInt(commandLine.getOptionValue("N2", String.valueOf(agents-agents1)));

        double epsilon = Double.parseDouble(commandLine.getOptionValue("eps", "0.05"));
        String variant = commandLine.getOptionValue("var", "zero");
        int variantLanguage = Integer.parseInt(commandLine.getOptionValue("iv", "0"))-1;
        int variantInfluence = Integer.parseInt(commandLine.getOptionValue("v", "0"));

        Simulation pattern = ELCPaperSimulationCreator.builder()
                .numOfAgents(agents).numsOfUsers(new int[]{agents1, agents2}).side(side)
                .numOfThings(things).devdegrees(new int[]{devdegree1, devdegree2})
                .epsilon(epsilon).variant(variant).variantInfluence(variantInfluence)
                .variantLanguage(variantLanguage).build().create();

        SimulationConductor conductor = new SimulationConductor(pattern);

        int simulations = Integer.parseInt(commandLine.getOptionValue("s", "1000"));
        int stopIteration = Integer.parseInt(commandLine.getOptionValue("stop", "100000"));

        conductor.conduct(simulations, stopIteration);

        try {
            SimulationConductorSerializer.writeToFile(conductor, commandLine.getOptionValue("p", "results.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
