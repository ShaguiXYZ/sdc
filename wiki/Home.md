# What is SDC?

SDC, which stands for Software Development Console, serves as a comprehensive platform offering detailed metrics related to technology, quality, and security for Applications.

In this project, all users share the same roles, granting them access to all available data. This unique feature empowers users to effortlessly compare statistics among different components or squads, facilitating a simplified and efficient review of the project status.

# Functional overview

The central focus of the application revolves around components. Each component is linked to a squad and a specific component type or architecture. Each squad, in turn, is associated with a department.

The main objective of the application is to define metrics derived from various services such as Sonar and Git. These metrics enable the calculation of a reference value, which helps measure the technical status of a component, squad, or department.

To obtain metric values, the application makes API calls to different services. Each API call returns a single string-type result for each metric.

The metric configuration defines the expected response type from an API call to a service and how to compare that response with other responses of the same type (e.g., numeric values, versions, etc.).

Metrics are linked to a specific component type or architecture. All components associated with the same type of architecture share the same set of metrics.
