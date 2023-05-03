## Code Review

Reviewed by: <Kenney Siu>, <u7481157>

Reviewing code written by: <Shao An Tay> <u7553225>

Component: <Model.java>

### Comments 
Good Points:
- Good comments regarding the instance variables as it includes necessary information that should be stored in a game
- Good use of the other the board class method 'Board.setBoardAttributes' and 'Board.setResource' to modify the board in the model class
- The model class is necessary with its interaction with the game in converting the stateString to our board classes and use in our Game/gui classes.
- Mostly good naming of variables as they describe 


Feedback:
- 'gamestate' instance should be capitalised to 'gameState'
- There is a lot of code within the 'toModel' method that could be broken up into different methods for of clarity as this method
seems to be doing everything at once. Which is foreseeable if the code does not run correctly and it becomes harder to debug.
- Line 49 to 54 could be implemented as a method to get the phase. 
- the naming of variable 'id' should be something more explicit like the 'player'
- It may be confusing what the gameState is as an int, it could be a kept as a char to avoid confusion
- The name 'Model' for the model class is not explicit in its implementation towards the design of the game
- There is no documentation with regard to what the model is in relation to the design of the game
- Line 58 is too long
