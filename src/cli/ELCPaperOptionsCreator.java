package cli;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * A factory class for creating {@code Options} objects with options for simulations conducted
 * in accordance with the computer model described in the "Evolutionary language competition - an agent-based model" paper.
 */
public class ELCPaperOptionsCreator implements OptionsCreator {

    /**
     * Returns an {@code Options} object with options for simulations conducted in accordance with the computer model described in the "Evolutionary language competition - an agent-based model" paper.
     * <p>
     * Returned {@code Options} object contains the following options:
     * <ul>
     * <li> {@code -n} ({@code --side}) defines the side of the agents' lattice graph </li>
     * <li> {@code -N} ({@code --agents}) defines the number of agents</li>
     * <li> {@code -N1} ({@code --agents1}) defines the initial size of the 1-lingual population </li>
     * <li> {@code -N2} ({@code --agents2}) defines the initial size of the 2-lingual population</li>
     * <li> {@code -d} ({@code --objects}) defines the number of objects</li>
     * <li> {@code -d1} ({@code --devdegree1}) defines the initial environment (number of recognized objects with the words belonging to a language) of the 1-lingual population</li>
     * <li> {@code -d2} ({@code --devdegree2}) defines the initial environemnt of the 2-lingual population</li>
     * <li> {@code -eps} ({@code --epsilon}) defines the epsilon parameter</li>
     * <li> {@code -var} ({@code --variant}) defines the model variant</li>
     * <li> {@code -v} ({@code --variantInfluence}) defines the variant influence </li>
     * <li> {@code -iv} ({@code --variantLanguage}) defines the variant language</li>
     * <li> {@code -p} ({@code --path}) defines the json results file path</li>
     * <li> {@code -s} ({@code --simulations}) defines the number of independent simulations to be conducted</li>
     * <li> {@code -stop} ({@code --stopIteration}) defines the maximal number of iterations to simulate in every independent simulation</li>
     * </ul>
     * All of the options are by default not required.
     * @return An {@code Options} object with options for simulations conducted in accordance with the computer model described in the "Evolutionary language competition - an agent-based model" paper.
     */
    @Override
    public Options create() {
        Options options = new Options();

        Option numOfThings = new Option("d", "objects", true, "specifies the number of objects in simulation");
        numOfThings.setRequired(false);
        options.addOption(numOfThings);

        Option numOfThingsFirstLanguage = new Option("d1", "devdegree1", true, "specifies the initial degree of development of languageOfId L_1 in simulation");
        numOfThingsFirstLanguage.setRequired(false);
        options.addOption(numOfThingsFirstLanguage);

        Option numOfThingsSecondLanguage = new Option("d2", "devdegree2", true, "specifies the initial degree of development of langugae L_2 in simulation");
        numOfThingsSecondLanguage.setRequired(false);
        options.addOption(numOfThingsSecondLanguage);

        Option latticeSide = new Option("n", "side", true, "specifies the side of lattice");
        latticeSide.setRequired(false);
        options.addOption(latticeSide);

        Option numOfAgents = new Option("N", "agents", true, "specifies the number of numOfAgents in simulation");
        numOfAgents.setRequired(false);
        options.addOption(numOfAgents);

        Option numOfAgentsFirstLanguage = new Option("N1", "agents1", true, "specifies the number of numOfAgents initially using languageOfId L_1 in simulation");
        numOfAgentsFirstLanguage.setRequired(false);
        options.addOption(numOfAgentsFirstLanguage);

        Option numOfAgentsSecondLanguage = new Option("N2", "agents2", true, "specifies the number of numOfAgents initially using languageOfId L_2 in simulation");
        numOfAgentsSecondLanguage.setRequired(false);
        options.addOption(numOfAgentsSecondLanguage);

        Option epsilon = new Option("eps", "epsilon", true, "specifies the epsilon parameter in simulation");
        epsilon.setRequired(false);
        options.addOption(epsilon);

        Option numOfSimulations = new Option("s", "simulations", true, "specifies the number of simulations to conduct");
        numOfSimulations.setRequired(false);
        options.addOption(numOfSimulations);

        Option stopIteration = new Option("stop", "stopIteration", true, "specifies the maximal number of iterations in each simulation");
        numOfSimulations.setRequired(false);
        options.addOption(stopIteration);

        Option variant = new Option("var", "variant", true, "specifies the variant of simulation. zero for the zero variant, ts for the total speaker variant, tl for the total listener variant");
        variant.setRequired(false);
        options.addOption(variant);

        Option variantInfluence = new Option("v", "variantInfluence", true, "specifies the variant influence in simulation");
        variantInfluence.setRequired(false);
        options.addOption(variantInfluence);

        Option variantLanguage = new Option("iv", "variantLanguage", true, "specifies the variant languageOfId in simulation");
        variantLanguage.setRequired(false);
        options.addOption(variantLanguage);

        Option path = new Option("p", "path", true, "specifies the path to write the results file");
        path.setRequired(false);
        options.addOption(path);

        return options;
    }
}
