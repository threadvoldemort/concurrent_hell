
# class load process
    - class loader load the .class file
      (call .getClassLoader() on class to get the loader)
    - initialize the class
    - the class become the template for creating instanse
      (call .getClass() on instance to get this template(Class class))
    - create instance

# class load, link and initialize process
    - load: find and load class's binary data
    - link:
        1. verification: verify wether the class to be loaded is correct  
        2. preparation:
            a) allocate memory space for static variables
            b) initialize the static variables with default value
        3. parse: 
            change the symbolic reference into direct reference in the class

            when compiling .java file into .class file, jvm does't know the real address
            which are referenced in .java file, so jvm uses symbols instead, this is called
            symbolic reference

            during the parse phase, the previous step has already allocated the memory space, so 
            now the jvm can replase the symbolic refernce with real address reference
    - initialization:
        in step "preparation", static variables are assigned with default value for its type
        in this stage, these static variable are assigned with real value

    - example:
        public class Test {
            public static int a = 1;
        }

        step: 
        1. compile into .class file
        2. get loaded into jvm via class loader
        3. verify the .class file is legit
        4. allocate memory space for int type
        5. give variable "a" a value of zero
        6. refer to variable "a" with real memory address
        7. give variable "a" a value of 1
        
# class load jvm param("vm option" in intellij)
    -XX:-TraceClassLoading (Expired in JDK 17)
    -verbose:class(JDK 21)        
    if the project takes long time to start, can use this vm option to see the loading process
    if the class is not been referenced it will not be loaded
    
# classloader type
    1. jvm loader:
        - bootstrap(root loader): load system/core package(i.e. rt.jar)
        - ext: load extended package
            for example company like Ali will add its own lib into jdk
            it will be put in jre/lib/ext folder
            it will be loaded by ext loader, and the priority is higher than project code
        - sys/app:
            classes in our project will be loaded by this loader
    2. user defined loader:
        extend class ClassLoader(not very often)





















