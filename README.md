# Timezone @ Report Level Testing
    - The script is created to reduce the testing effort for timezone feature.
    - As we made major changes in code flow which. This script will help to test some basic cases.
        - Normal Metric.
        - Resolved Tickets Metric.
        - Unresovled Tickets Metric.

## Testing Approach
    - We will deploy our timezone branch code in one stack.
    - And master branch code in another stack.
    - We need to compare the Query and Data between both with different metrics with date filters.
        - First we need to test the normal flow without applying timezone filter. 
            To make without timezone filter all flow queries are same as old one.
        - After that we need to test with applying the timezone filter in the report level.

## Script Details
    - Java code which has major part which will make API for two different stack and write it 
        has json object. With the configuration we have given.
    - Javascript is used to loop through the json object and render the two stack query with
        differentiate text in an table.





