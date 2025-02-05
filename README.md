# repok-verifier-and-injector

## Instructions to replicators

### Download randoop
```
wget -P tools https://github.com/randoop/randoop/releases/download/v4.3.2/randoop-all-4.3.2.jar
```

### Execute program
```
gradle run --args="<classPath> <className> <promptType>"
```

### Notes
Keep in mind that the program searches for one of two files:
- `PropertiesClass.java`
- `RepOkClass.java`

This are the classes files that must contain the methods to inyect in the class.