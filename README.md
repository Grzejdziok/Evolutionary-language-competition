# Evolutionary language competition - an agent-based model
## Description
The project is concerned with simulating the process of how the world's languages are going extinct as a result of *language competition*. The problem is serious, as it is estimated that about 43% of the world's languages are endangered of extinction within next 100 years [[1]](http://www.unesco.org/languages-atlas/index.php?hl=en&page=atlasmap). 

The model implemented here is agent-based and each agent has its own language. The agents are initially divided into two groups speaking two different languages. Through pair-wise interactions agents modify their languages (their languages evolve). At last, in most cases, agents use one common language. Depending on initial conditions, the final language can be identical to one of the initial languages or can be a mixture of both of languages.

The project is an implementation of the computer model described in the "Evolutionary language competition - an agent-based model" paper which is currently under review in *[International Journal of Applied Mathematics and Computer Science](https://www.amcs.uz.zgora.pl/)*. Detailed information of the model's rules can be found in a pdf file attached to this repository.

## Installation
The project is implemented in Java, and therefore to compile it you will need JDK 8.0 or later. You can get it [here](https://www.oracle.com/technetwork/java/javase/downloads/index.html). To compile and run the program conveniently you should have either [Maven](https://maven.apache.org/) or [Gradle](https://gradle.org/) installed and added to your path variable. You can get the first [here](https://maven.apache.org/download.cgi/) and the latter [here](https://gradle.org/install/).
### Installation with Maven
First, clone the repository with the following command:
```
git clone https://github.com/Grzejdziok/Evolutionary-language-competition
```
Then switch the current directory:
```
cd ./Evolutionary-language-competition
```
And run the following command to compile the project into an executable jar file with dependencies included:
```
mvn install
```
You should have the "jar" directory created and there you should find an executable jar file named "ELCPaperApp.jar".
### Installation with Gradle
This part will be added soon.
### Manual Installation
This part will be added soon.
## Run the program
The program can be run from the command line with the arguments denoted as in the paper. Run:
```
java -jar ECLPaperApp.jar <arguments>
```

For simplicity, all parameters have default values specified.

Arguments:
- -n for the side of the agents' lattice graph (default: 4)
- -N for the number of agents (default: n*n)
- -N1 for the initial size of the 1-lingual population (default: N/2)
- -N2 for the initial size of the 2-lingual population (default: N-N1)
- -d for the number of objects (default: 5)
- -d1 for the initial environment (number of recognized objects with the words belonging to a language) of the 1-lingual population (default: 5)
- -d2 for the initial environemnt of the 2-lingual population (default:5)
- -eps for the epsilon parameter (default: 0.05)
- -var for the model variant (with string argument from the {"zero", "ts", "tl"} set) (default: "zero")
- -v for the variant influence (default: 0)
- -iv for the variant language (from the set {1, 2}) (default: 1)
- -p for the json results file path (default: "results.json")
- -s for the number of independent simulations to be conducted (default: 1000)
- -stop fot the maximal number of iterations to simulate in every independent simulation (default: 100000)


## References
[[1]](http://www.unesco.org/languages-atlas/index.php?hl=en&page=atlasmap) Moseley, C. (Ed.) (2010). *Atlas of the World’s Languages in Danger*, 1 edn, UNESCO Publishing, Paris. Accessed on 20 Febr. 2018.
