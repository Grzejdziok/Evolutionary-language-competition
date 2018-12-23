# Evolutionary language competition - an agent-based model
## Description
The project is concerned with simulating the process of how the world's languages are going extinct as a result of *language competition*. The problem is serious, as it is estimated that about 43% of the world's languages are endangered of extinction within next 100 years [[1]](http://www.unesco.org/languages-atlas/index.php?hl=en&page=atlasmap). 

The model implemented here is agent-based and each agent has its own language. The agents are initially divided into two groups speaking two different languages. Through pair-wise interactions agents modify their languages (their languages evolve). At last, in most cases, agents use one common language. Depending on initial conditions, the final language can be identical to one of the initial languages or can be a mixture of both of languages.

The project is an implementation of the computer model described in the "Evolutionary language competition - an agent-based model" paper which is currently under review in *[International Journal of Applied Mathematics and Computer Science](https://www.amcs.uz.zgora.pl/)*. Detailed information of the model's rules can be found in a pdf file attached to this repository.

## Installation and build process
### Installation
The project is implemented in Java, and therefore to compile it you will need JDK 8.0 or later. You can get it [here](https://www.oracle.com/technetwork/java/javase/downloads/index.html). 

To get the program, clone the repository with the following command:
```
git clone https://github.com/Grzejdziok/Evolutionary-language-competition
```
Source code is provided in "src" directory and an executable file in the "jar" directory.

If you want to build the program by your own, there are configuration files provided for build with [Maven](https://maven.apache.org/)(pom.xml) and [Gradle](https://gradle.org/) (build.gradle, settings.gradle). Build process is configured so that it creates a jar executable file in the "jar" directory with all dependencies included.
#### Building the project with Maven
In the project directory, run the following command to compile the project into an executable jar file with dependencies included:
```
mvn install
```
The "jar" directory will contain a new version of the jar executable file of the program.
#### Building the project with Gradle
In the project directory, run the following command to compile the project into an executable jar file with dependencies included:
```
gradle build
```
The "jar" directory will contain a new version of the jar executable file of the program. There will be also a directory named ".gradle" which serves for build purposes of Gradle.
#### Dependencies
The project makes use of several external libraries. All of them are provided below:
- [Lombok, version 1.18.4](https://projectlombok.org/)
- [Apache Commons CLI, version 1.4.0](https://commons.apache.org/proper/commons-cli)
- [jackson-core, version 2.9.7](https://github.com/FasterXML/jackson-core)
- [jackson-databind, version 2.9.7](https://github.com/FasterXML/jackson-databind)
- [Java Cloning Library, version 1.9.10](https://github.com/kostaskougios/cloning)
## Running the program
The program can be run from the command line with the arguments denoted as in the paper. Go to the "jar" directory and run the following command:
```
java -jar ECLPaperApp.jar <arguments>
```
Program creates a Simulation object in accordance with provided arguments, conducts the specified number of independent simulations (by default one thousand) and outputs the averaged results to a JSON file (by default named "results.json").

All arguments that can be provided are described below:
- -n defines the side of the agents' lattice graph (default: 4)
- -N defines the number of agents (default: n*n)
- -N1 defines the initial size of the 1-lingual population (default: N/2)
- -N2 defines the initial size of the 2-lingual population (default: N-N1)
- -d defines the number of objects (default: 5)
- -d1 defines the initial environment (number of recognized objects with the words belonging to a language) of the 1-lingual population (default: 5)
- -d2 defines the initial environemnt of the 2-lingual population (default:5)
- -eps defines the epsilon parameter (default: 0.05)
- -var defines the model variant (with string argument from the {"zero", "ts", "tl"} set) (default: "zero")
- -v defines the variant influence (default: 0)
- -iv defines the variant language (from the set {1, 2}) (default: 1)
- -p defines the json results file path (default: "results.json")
- -s defines the number of independent simulations to be conducted (default: 100)
- -stop defines the maximal number of iterations to simulate in every independent simulation (default: 100000)

Please see the paper for more detailed descriptions of the parameters and the model's rules.

### Examples
Several experiments were conducted for the evaluation of the model and described in the paper. Below you can find the commands for these simulations for each section describing the experiments.

#### 4.1. Initial populations’ sizes’ impact.
You can run the simulations for this section with the following commands:
```
-N1 14 -p sizes14_1.json
-N1 12 -p sizes12_1.json
-N1 10 -p sizes10_1.json
-N1 8 -p sizes8_1.json
```
#### 4.2. Initial environments’ sizes impact.
You can run the simulations for this section with the following commands:
```
-d2 1 -p devdegrees1_2.json
-d2 2 -p devdegrees2_2.json
-d2 3 -p devdegrees3_2.json
-d2 4 -p devdegrees4_2.json
```
#### 4.3. Joint impact of initial populations’ sizes and initial environments’ sizes.
You can run the simulations for this section with the following commands:
```
-N1 2 -d2 1 -p sizes2_1devdegrees1_2.json
-N1 2 -d2 2 -p sizes2_1devdegrees2_2.json
-N1 2 -d2 3 -p sizes2_1devdegrees3_2.json
-N1 2 -d2 4 -p sizes2_1devdegrees4_2.json
-N1 4 -d2 1 -p sizes4_1devdegrees1_2.json
-N1 4 -d2 2 -p sizes4_1devdegrees2_2.json
-N1 4 -d2 3 -p sizes4_1devdegrees3_2.json
-N1 4 -d2 4 -p sizes4_1devdegrees4_2.json
```
#### 4.4. ”Total speaker” impact.
You can run the simulations for this section with the following commands:
```
-N1 4 -var ts -iv 1 -v 6 -p sizes4_1ts6_1.json 
-N1 6 -var ts -iv 1 -v 8 -p sizes6_1ts8_1.json
```
#### 4.5. ”Total listener” impact.
You can run the simulations for this section with the following commands:
```
-N1 12 -var tl -v 2 -p sizes12_1tl2.json
-N1 12 -var tl -v 4 -p sizes12_1tl4.json
-N1 12 -var tl -v 6 -p sizes12_1tl6.json
```
## Extensions
I tried to implement the model so that it can be easily extended. The implementation is flexible and one can create new classes to define new behaviours of agents (by implementing the Agent interface), new rules for interactions (by implementing the InteractionRunner interface), new rules for lexicons (by implementing the Lexicon interface), or new structures of population (by implementing the Population interface). This allows to conduct simulations for similar models of language competition within the project's framework.
## API Docs
More detailed documentation will be provided soon in the form of Javadoc.
## Contact
Please do not hesitate to contact me if you have any questions.
## References
[[1]](http://www.unesco.org/languages-atlas/index.php?hl=en&page=atlasmap) Moseley, C. (Ed.) (2010). *Atlas of the World’s Languages in Danger*, 1 edn, UNESCO Publishing, Paris. Accessed on 20 Febr. 2018.
