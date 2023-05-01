## Code Review

Reviewed by: <Wemxuan Cao>, <u7556970>

Reviewing code written by: <Kenney Siu> <u7481157>

Component: <Viewer.java>

### Comments 

<
> Good Points:
> All the method names are well-formed using CamelCase. They are clarified.
> Before displaying the game board you use the isStateStringWellFormed method in BlueLagoon class and cover the edge cases.
> You split the gameString to get each component. It reduces the duplication of non-trivial code. 
> There are no overexposure, all the features are used.
> The implementation of the Viewer class is detailed at displaying not only the information but shows a rough visualisation of the board.
>
> Feedback
> Line 106, 107, 149, 154, 174, 176, 198, 200, 355, 358 are too long and hard to read. You should separate them into several lines.
> You lack enough comments to explain the function of each method.
> There is not a lot of documentation regarding what methods are supposed to accomplish
> There are a lot of calls of setLayoutX and setLayoutY, there should be a method like helper function to accomplish that, which reduces duplication.
> There is some confusion about Tile and Hexagon class. They are not clarified enough because they have different functions on chessboard.


