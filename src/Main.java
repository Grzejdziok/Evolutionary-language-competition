import org.apache.commons.cli.*;
import cli.ELCPaperOptionsCreator;
import simulation.simulation.Simulation;
import simulation.simulation.SimulationConductor;
import simulation.simulation.SimulationConductorSerializer;
import simulation.simulation.creator.ELCPaperSimulationCreator;

import java.io.IOException;

public class Main {

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
            SimulationConductorSerializer.writeToJSON(conductor, commandLine.getOptionValue("p", "results.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
