
Here is an extended markdown file with a code block and additional content like lists, links, images, and text sections:

---

# Sample Java Code in Markdown

This file contains a simple Java program followed by other content types in Markdown.

```java
// Sample Java Program
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println("Addition: " + calc.add(5, 3));
        System.out.println("Subtraction: " + calc.subtract(5, 3));
    }
}
```

## Key Features of Java

Java is a powerful programming language with many key features:

1. **Platform Independence**: Java code can be run on any device that supports the JVM.
2. **Object-Oriented**: It allows for a modular approach, making it easier to maintain and reuse code.
3. **Robust**: It has strong memory management and built-in error handling.

---

## How to Set Up Java

### Prerequisites

- Java Development Kit (JDK) version 11 or higher.
- An IDE like IntelliJ IDEA or Eclipse.

### Steps to Install

1. Download the JDK from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
2. Install the JDK on your machine.
3. Set up your `JAVA_HOME` environment variable.

After installing, verify the installation by running:

```bash
java -version
```

---

## Useful Java Libraries

Here are some useful libraries that are frequently used in Java projects:

- **Spring Framework**: Used for building enterprise-level applications.
- **Hibernate**: An ORM framework for database operations.
- **JUnit**: A framework for writing unit tests in Java.

---

## Sample Image

You can add images to your markdown for more clarity. Below is an example of how to embed an image:

```md
![Java Logo](https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg)
```

![Java Logo](https://upload.wikimedia.org/wikipedia/en/3/30/Java_programming_language_logo.svg)

---

## Additional Resources

To learn more about Java, check out the following resources:

- [Official Java Documentation](https://docs.oracle.com/javase/8/docs/)
- [Java Tutorials on Udemy](https://www.udemy.com/topic/java/)
- [Stack Overflow Java Community](https://stackoverflow.com/questions/tagged/java)

---

## Running the Code

To run the code in the above block, follow these steps:

1. Copy the code into a file named `Calculator.java`.
2. Compile the file:

   ```bash
   javac Calculator.java
   ```

3. Run the compiled code:

   ```bash
   java Calculator
   ```

---

This Markdown file includes a variety of content elements along with the Java code block, showcasing how Markdown can be used for documentation.