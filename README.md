# MvfLib
This is a library for Minecraft 1.15.2 that simplifies some tasks to reduce boilerplate code in your mod code.
This library handles things like object creation and registration, data generation and other things.

## Usage
### Building
First build the JAR using the task
````
build
```` 
after that, deobfuscate the output jar by executing the task
````
deobfJar
````
### Using MvfLib as a dependency
Now, in the build.gradle of the dependent, add this:
````
dependencies {
  // Some other things...
  
  compile fileTree(dir: 'run/mods', include: ['*.jar'])
}
```` 
or something like that, to compile the deobfuscated jar.
You also need to add MvfLib as a dependency in mods.toml:
```` 
[[dependencies.modname]]
  modId="mvflib"
  mandatory=true
  versionRange="[0.0.1,)"
  ordering="NONE"
  side="BOTH"
````
Finally, add the MvfLib JAR as a dependency for your project module in IntelliJ.
Now you can use MvfLib code in your mod!
