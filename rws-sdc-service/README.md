# rws-sdc-service

RWS - Software deveplopment console

## How to

### ddbb relation entities description

the central object of the application are the components.
each component is associated with a squad and a component type / architecture.
Each squad is associated with a department.

The object of the application is to define metrics extracted from different services (sonar, git...) from which we can calculate a reference value to measure the technical status of a component / squad / department.

Obtaining the values ​​of the metrics is done by calling the different apis that provide the services, the call to an api returns a unique result for each metric of type string and that is comparable with results of the same type (Numeric, Versions... ..).

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
To calculate the coverage of a component, the previous process is applied to the metrics with calculated coverage of the component.

## boot arguments
```
-Dspring.profiles.active=local
-Ddb.domain=localhost:5432
-Ddb.user=user
-Ddb.password=
```

## urls

### Swagger
```
http://localhost:8081/rws-sdc/swagger-ui/index.html
```

### h2

```
http://localhost:8081/rws-sdc/h2-ui
```
