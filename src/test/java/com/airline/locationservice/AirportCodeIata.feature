
Feature: IATA Airport code lookup

Background:
* url 'http://localhost:8100/airport'
# * url config.baseUrl

Scenario: Retrieve a single airport code by id
Given path '/iata'
And path 'ATL'
When method GET
Then status 200
And match $.iata_code == 'ATL'




Scenario Outline: Spot check a few airports
Given path '/iata/<id>'
When method GET
Then status 200
And match $.iata_code == <id>

Examples:
| id    |
| 'ATL' |
| 'MSP' |
