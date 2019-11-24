
Feature: IATA Airport code lookup

Background:
* url 'http://localhost:8100/airport/iata'
# * url config.baseUrl

Scenario: Retrieve a list of airport codes
Given path '/'
# And path 'ATL'
When method GET
Then status 200
# And match $.iata_code == 'ATL'




Scenario Outline: Spot check a few airports
Given path '/<id>'
When method GET
Then status 200
And match $.iata_code == <id>

Examples:
| id    |
| 'ATL' |
| 'MSP' |
