# Seat Picker

**Author:** Ron Hockman (ron@hockman.org)

**License:** MIT

## Building
This project uses Apache Maven as its build and packaging system.

1. Install [Maven](https://maven.apache.org/install.html)
1. Install Git
1. Get the latest code: `git clone git@github.com:rhockman/Homework.git`
1. `cd Homework` 
1. Build: `mvn package`

### Running

To run with the default parameters use:

`java -jar cli/target/cli-1.0.0-SNAPSHOT-jar-with-dependencies.jar -r inputFilePath`

### Tests

To run unit tests: `mvn test`

## My Process


1. Read the requirements
1. Realize that the algorithm for this is going to be tricky. I'm not going to have time to determine the optimal solution in isolation during the course of this homework assignment.  It reminds me of the backpack problem that I last encountered during college.
1. Start picking technologies.
    1. Java
        1. I am familiar Java
        1. Walmart uses Java
        1. Given the expected complexity of the solution, a reasonably fast-running language would be nice  
    1. Maven
        1. Easy Dependency Management
        1. Handles all my build/packaging needs
1. Go figure out Maven
    1. Recognize that Apache's documentation is an acquired taste.
1. Write the supporting methods
    1. Maven package and modules
    1. Create Executable JAR
    1. Parse Arguments
    1. Set up Classes and Interfaces
    1. Wire everything together leaving the seat assignment as TODO
        1. Leave room to have multiple algorithms (then later decide to implement only one)
1. Start researching algorithms.
    1. Find the [Knapsack problem](https://en.wikipedia.org/wiki/Knapsack_problem) Wikipedia Page
    1. Find the [Subset sum problem](https://en.wikipedia.org/wiki/Subset_sum_problem) Wikipedia Page
1. Realize that I'm not going to have time to implement an ideal solution
1. Write a greedy algorithm that should provide decent results
1. Make the input/output exactly what the problem statement requires
1. Go figure out how to get code coverage reports in maven
1. Write tests
1. Fix bugs found during testing
1. Overthink this README
1. Ship it!

## Axioms

1. Each seat in the row gets a value which corresponds to its "quality" from 6 through 2, inclusive.
1. A customer in a higher quality seat will have higher satisfaction than another customer in a lower quality seat, all else being equal.
1. Customers in equal quality seats will have equal satisfaction, all else being equal.
1. The satisfaction value for a reservation is the sum of seat quality of the seats assigned to that reservation if the reservation fits in a contiguous set of seats in a row.
1. If the reservation must be split, a negative satisfaction value is included in the sum of the above.  That value is N, where N is the number of seats in the reservation.
1. The total satisfaction for the set of reservations is the sum of satisfaction values for each reservation
1. If the number of reserved seats exceeds the capacity of the theater, as many as possible will be seated

## Commentary

This problem looks to be a flavor of NP-hard.  It can be abstracted into a variation on the Multiple Knapsack problem or a Multi-objective Knapsack.

A greedy algorithm that prioritizes filling high-value seats should yield a satisfactory runtime that also compares favorably with the ideal solution.

During my research, one paper in particular showed what appears to be an analogous problem and corresponding approaches: "Heuristics for cardinality constrained portfolio optimization" by T.-J Chang et al.  Since that paper likely took some grad students moths to figure out and implement, but I've only got a weekend and a few weeknights, I'm going to skip using the methods described in the paper and go with something simpler.


