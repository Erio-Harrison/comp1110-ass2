## Code Review

Reviewed by: <Tay Shao An>, <u7553225>

Reviewing code written by: <Wenxuan Cao> <u7556980>

Component: Board.reset(), Board.isValidSettle()

### Comments 

Board.reset()
- Code is to the point and straightforward. No uneeded loops or if else statements.
- The function's purpose and its parameters are well documented
- Lack of documentation within the code to explain what it is doing
- The function could make use of smaller functions to ensure blocks of repeated code are not written over and over.
- int i is initialized twice.


Board.isValidSettle()
- The function's purpose and its parameters are well documented
- Comments placed within the function helps to understand what it is doing
- Structure of one of the if else statements seems a bit redundant

