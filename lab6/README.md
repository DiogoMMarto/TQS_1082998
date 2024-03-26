# LAB 6 1

## e)

The project passed the quality gate as shown by !["sonarresult.png"](sonarresult.png)

## f)

![issue1](issue1.png)

| Issue         | Problem description | How to solve |
|---            |---                  |---           |
| Clean code    | The return type of this method should be an interface such as "List" rather than the implementation "ArrayList".                    | Declarations should use Java collection interfaces such as "List" rather than specific implementation classes             |

![issue2](issue2.png)

| Issue         | Problem description | How to solve |
|---            |---                  |---           |
| Maintainability          |  "Preconditions" and logging arguments should not require evaluation                   | Invoke method(s) only conditionally.             |

![issue3](issue3.png)


| Issue         | Problem description | How to solve |
|---            |---                  |---           |
| Maintainability          |  "for" loop stop conditions should be invariant                  | Refactor the code in order to not assign to this loop counter from within the loop body.             |

# Lab 6 2 

## a)

![sonarresult2](sonarresult2.png)

Technical debt is the amount of time required to solve a certain issue. Since I dont have issues this is 0.

## c)

![coverage](coverage.png)

The values are good , most of the classes with low coverage are due to them only being used by Springboot and not tested directly on tests. Ex:

![alt text](example.png)