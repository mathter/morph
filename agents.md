# Project Structure Overview

This project is a multi-module Maven project containing sources in both Scala and Java.

The three main modules are:
1.  **core/**: Contains the fundamental DSL definition, path mapping, and evaluation engine. It defines the core language constructs in Scala (e.g., `Dsl.scala`, `Eval.scala`).
2.  **jcore/**: Provides programmatic bindings for the DSL in Java and Scala. This module allows users to construct DSL expressions and interact with the core logic from Java environments.
3.  **processing/**: Handles data persistence and I/O. It is responsible for serializing and deserializing the processed data structures into common formats like JSON and XML.

**Key Source Details:**
*   **core/** includes Scala sources for defining the Domain Specific Language (DSL), managing data structures through path mapping, and executing the evaluation of the DSL.
*   **jcore/** provides explicit Java/Scala classes that wrap the core functionality, enabling integration into Java-based applications.
*   **processing/** implements data handling capabilities, focusing on connecting the processed logic to external data formats.