# Calculating metric coverage

Each metric associated with a type of component/architecture can fall into one of three possible ranges: expected, good, or perfect.

Every metric is assigned a weight, which represents its relative importance compared to other metrics.

Each metric is also assigned a comparison operation, such as Equal, Less than, Greater than, Less than or equal, or Greater than or equal.

## Calculating the coverage of a metric 

The value returned by the API call is compared against the configured range values. Based on the associated comparison operation, a fixed coverage percentage is determined:

If the value does not reach the expected range: 10% coverage.
If the value falls between the expected and good ranges: 50% coverage.
If the value falls between the good and perfect ranges: 75% coverage.
If the value is equal to or higher than the perfect range: 100% coverage.

## Calculating Metric List Coverage

To calculate the coverage of a list of metrics, the following formula is applied:

totalWeight: The sum of weights of all the metrics to be validated.
relativeWeight: The weight of a metric divided by the totalWeight.
relativeMetricCoverage: The metric coverage multiplied by the relativeWeight.
total coverage of the metrics: The sum of relative coverage for each metric.

## Calculating Component Coverage

To calculate the coverage of a component, select all the metrics associated with the component (based on component/architecture), and apply the coverage calculation formula to this list of metrics.

To enhance performance, this calculation is performed during component analysis and the results are stored in the database.

## Calculating Squad Coverage

To calculate the coverage of a squad, select all the metrics associated with the squad, and apply the coverage calculation formula to this list of metrics.

To enhance performance, this calculation is performed during squad analysis and the results are saved in the database.

## Calculating Department Coverage

The coverage of a department is determined by taking the average of the coverage values of its squads.