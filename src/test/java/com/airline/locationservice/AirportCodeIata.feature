
Feature: IATA Airport code lookup

Background:
* url 'http://localhost:8100/airport'
# * url config.baseUrl
* configure report = { showLog: true, showAllSteps: true }

Scenario: Retrieve a list of airport codes
# Given url http://localhost:8100/airport/iata
Given path '/iata'
# And path 'ATL'
When method GET
Then status 200
And match $ contains { iataAirportCode: 'ATL' }
# And match $.iataAirportCode contains 'ATL'
# And match $.iata_code == 'ATL'



Scenario Outline: Spot check a few airports
Given path '/iata/<id>'
When method GET
Then status 200
# And match $.iata_code == <id>
# And match $ == {"iataAirportCode":"<id>"}
And match $.iataAirportCode == "<id>"
Examples:
| id  |
| ATL |
| MSP |


Scenario Outline: Add a new airport code
Given path '/iata'
And request {iataAirportCode: "<id>" }
When method POST
Then status 200

# When method GET
# Then status 200
# And match $.iata_code == <id>
# And match $ == {"iataAirportCode":"<id>"}
And match $.iataAirportCode == "<id>"
Examples:
| id  |
| YYZ |
| LAX |

