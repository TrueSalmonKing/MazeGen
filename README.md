# MazeGen
A java gui application that allows for creation and solving for mazes.
We create the mazes through a user choice of either prim's randomized algorithm, or a recursive backtracker approach, and we finally save the maze in a png image file.
When solving a maze, we first read the image containing the maze (granted it adheres to the rules of the software listed below), using djikstra's algorithm, we solve the maze through 3 basic steps listed in the process of execution section

## Build

To build the application it suffices to run the batch script build.bat, it will handle the compilation process and adequate resources managment.

## Getting started

To run this program optimally, it is necessary to note that the image to be used must adhere to appropriate format of which is an image representing a maze made out of 1 pixel black blocks, a sample image is provided along with the software.
Run the software, and choose the maze and the result will be printed in the same directory as the source maze

# Process of Creating
### Step 1

We initialize a 2 dimensional array containing the cells of our maze.

### Step 2

We run the algorithm chosen by the user iteratively in order to draw each frame in the JPanel object.

### Step 3

The algorithm is then later on saved to the image file in the same directoy as the gui software, we name the maze using unix time in order to differentiate each maze created and avoid name collision.

# Process of Solving
### Step 1

The pixels are read and nodes are defined as to ease the computational overhead in the case of a large maze, we do so following 2 rules: if a pixel is surronded 2 pixels on oppsing sides ( left and right or up and down ), then said pixel is not a node. Otherwise it's a node. This two rules seperate a path ( the first case ) from a node.

### Step two

Each node is added to linked list that will later be treated using djikstra's algorith to define the shortest path from source to end, such is done using the defined list of nodes along with a sublist of sources and exits, which chooses the optimal path between them.

### Step three

The node list is treated and we return a list of nodes with the exit node as a head of the list, and with a previous pointer, pointing to the previous node in the shortest path leading to the source node. Finally the path from the source to the exit.

### Prerequisites

#### In order to rebuild
Java Developpment Kit - [JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
#### In order to run the prebuilt jar file(built using javac 12.0.2)
Java Run Environment - [JRE](https://www.java.com/en/download/)

## Written With

* [Eclipse](https://www.eclipse.org/documentation/) - The java IDE used.

## Note

The Program not optimzed well for mazes above 100x100 cells, and may result in animation failure but the maze will be created.
The maze Solving algorithm only accepts mazes created by this application or mazes that follow the rules below:

#### 1.The borders of image match the borders of the maze, meaning there is not a single pixel between the very edge pixel of the image and borders of the maze.
#### 2.The Maze walls should always be black, meaning the walls must have a set color hex value of #000000
