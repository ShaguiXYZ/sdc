# rws-sdc

RWS - Software deveplopment console

## How to

### ddbb relation entities description
The central object of the application are the components.

Each component is associated with a squad and a component type / architecture.
Each squad is associated with a department.

The object of the application is to define metrics extracted from different services (sonar, git...) from which we can calculate a reference value to measure the technical status of a component / squad / department.

Obtaining the values ​​of the metrics is done by calling the different APIs that provide the services. The call to an API returns a single string-type result for each metric.

The metric configuration defines what type of object is expected as a response to an api call to a service and how to compare that response with other responses of the same type 
(Numeric, Versions. .. ..).

The metrics are associated with a type of component of an architecture, all the components associated with this type of architecture component will have the same metrics associated.


### Calculate metric coverage
Each metric of a type of component / architecture can be associated with three possible ranges (expected, good, perfect).

Each metric is assigned a type of component/architecture and is assigned a weight (absolute value that measures the importance of this metric with respect to the rest of the metrics).

Each metric is assigned the type of comparison operation that must be fulfilled (Equal, Less than, Greater than, Less than or equal, greater than or equal)

To calculate the coverage of each metric, the value returned by the api call is compared with the values ​​configured as possible values ​​and depending on the operation associated with the metric, a fixed coverage (%) is returned:
- Does not reach the expected value: 10%.
- Between the expected value and good value: 50%.
- Between the good value and the perfect value 75%.
- If it is the perfect value or higher: 100%.

### Calculate metric list coverage
To calculate the coverage of a list of coverages, the following formula is applied.

- totalWeight = sum of all the weights of the metrics to be validated
- relativeWeight = weight of the metric / totalWeight
- relativeMetricCoverage = metric coverage * relativeWeight
- total coverage of the metrics = sum of all the relative coverage of each of the metrics

### Calculate component coverage
To calculate the coverage of a component, all the metrics with coverage associated with the component (component/architecture of the component) are selected and the coverage calculation formula is applied to this list of metrics.

To increase performance, this calculation is performed at the time of component analysis and the database is saved.

### Calculate squad coverage
To calculate the coverage of a squad, all the metrics with coverage associated with the squad are selected and the coverage calculation formula is applied to this list of metrics.

To increase performance, this calculation is performed at the time of component analysis and the database is saved.

### Calculate department coverage
The coverage of a department is calculated with the average of the coverage of its squads.


## Local configuration

### Boot arguments
```
-Dspring.profiles.active=local
-Ddb.domain=localhost:5432
-Ddb.user=user
-Ddb.password=
```

## Useful urls

### Swagger
```
http://localhost:8081/rws-sdc/swagger-ui/index.html
```

### h2

```
http://localhost:8081/rws-sdc/h2-ui
```
