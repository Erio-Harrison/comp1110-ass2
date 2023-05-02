## Code Review

Reviewed by: <Tay Shao An>, <u7553225>

Reviewing code written by: <Wenxuan Cao> <u7556980>

Component: Board.reset(), Board.isValidSettle()

### Comments 

Board.reset()
What are the best features of this code?
- Code is to the point and straightforward. No uneeded loops or if else statements.
Is the code well-documented?
- The function's purpose and its parameters are well documented
- Lack of documentation within the code to explain what it is doing
Is the program decomposition (class and method structure) appropriate?
- The function could make use of smaller functions to ensure blocks of repeated code are not written over and over.
- int i is initialized twice.


Board.isValidSettle()
Is the code well-documented?
- The function's purpose and its parameters are well documented
- Comments placed within the function helps to understand what it is doing
  Is the program decomposition (class and method structure) appropriate?
- Structure of one of the if else statements seems a bit redundant

