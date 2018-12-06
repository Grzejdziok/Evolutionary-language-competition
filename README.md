The implementation of the computer model developed for my bachelor thesis. The paper, uploaded here also, is currently under review in International Journal of Applied Mathematics and Computer Science.

The program's documentation to be added in the nearest future.

To run the program, the following libraries are needed:
- Jackson Core and Jackson Databind, v. 2.9.7
- Apache Commons CLI, v. 1.3.1
- Lombok, v. 1.18.4
- Cloning (https://github.com/kostaskougios/cloning), v. 1.9.10

All the libraries can be easily downloaded with maven

The program can be run from the command line (compile and run Main.java) with the arguments denoted as in the paper.
For simplicity, all parameters have specified default values, so they don't have to be specified.

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
