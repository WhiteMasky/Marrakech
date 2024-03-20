
# Test plan

## List of classes

* List below all classes in your implementation that should have unit tests.
* For each class, list methods that can be tested in isolation.
* For each class, if there are conditions on the class' behaviour that cannot
  be tested by calling one method in isolation, give at least one example of
  a test for such a condition.

Do **not** include in your test plan the `Marrakech` class or the predefined
static methods for which we have already provided unit tests.


### Class: GameString

#### Method: getPlayerStrings(String gameStr)
- Test case 1: Test a valid gameStr string with 4 Player String
  - Input: `gameString = "currentGame"`
  - Expected output: `true`

- Test case 2: Test an invalid rug string with  less than 4 Player String
  - Input: `gameString = "currentGame"`
  - Expected output: `false`

- Test case 3: Test an invalid rug string with no Player String
  - Input: `gameString = "currentGame"`
  - Expected output: `false`
  
#### Method: getgetAssamString(String gameStr)
- Test case 1: Test a valid gameStr string a valid assam String
  - Input: `gameString = "currentGame"`
  - Expected output: `true`

- Test case 2: Test an invalid rug string with invalid assam String
  - Input: `gameString = "currentGame"`
  - Expected output: `false`

#### Method: getgetBoardString(String gameStr)
- Test case 1: Test a valid gameStr string a valid Board String
  - Input: `gameString = "currentGame"`
  - Expected output: `true`

- Test case 2: Test an invalid rug string with invalid Board String
  - Input: `gameString = "currentGame"`
  - Expected output: `false`

### Class: IntPair

#### Method: isAdjacent(IntPair intPair2)
- Test case 1: Test two intPair which is adjacent.
  - Input: `Intpair intpair"`
  - Expected output: `true`

- Test case 2: Test two intPair which is not adjacent
  - Input: `Intpair intpair`
  - Expected output: `false`

#### Method: withinBoard(IntPair position)
- Test case 1: Test two intPair which is in Board.
  - Input: `(5,4)`
  - Expected output: `true`

- Test case 2: Test two intPair which is not in Board
  - Input: `(10,1)`
  - Expected output: `false`