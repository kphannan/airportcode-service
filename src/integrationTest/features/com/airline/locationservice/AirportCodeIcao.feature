
Feature: ICAO Airport code lookup

    Background:
        * url 'http://localhost:8100/airport/icao'
        # * url config.baseUrl
        * configure report = { showLog: true, showAllSteps: true }


    Scenario: Retrieve a list of ICAO airport codes
        # Given url http://localhost:8100/airport/icao
        Given path '/'
        When method GET
        Then status 200
        And match $ contains { icaoAirportCode: 'KATL' }


    # ----- (GET) Lookup existing airport -----
        Scenario Outline: Spot check a few ICAO airport codes
        Given path '/<id>'
        When method GET
        Then status 200
        And match $.icaoAirportCode == "<id>"
        Examples:
        | id  |
        | KATL |
        | KMSP |

    # ----- (GET) lookup non-existent codes -----
    Scenario Outline: Ensure unknown ICAO airport codes are not found
        Given path '/<id>'
        When method GET
        Then status 404
        Examples:
        | id  |
        | KSOS |
        | KZZZ |



    # ----- Add airport code (POST) -----
    Scenario Outline: Add a new ICAO airport code
        Given path '/'
        And request {icaoAirportCode: "<id>" }
        When method POST
        Then status 201
        And match $.icaoAirportCode == "<id>"
        Examples:
        | id  |
        | KYYZ |
        | KLAX |



    # ----- Put (Update) -----

    # ----- Put (Create) -----


    # ----- Delete -----
    Scenario: Add, then delete an ICAO airport code
        Given path '/'
        And request {icaoAirportCode: "KSOS" }
        When method POST
        Then status 201
            # now delete it - this is the real test
        Given path '/KSOS'
        When method DELETE
        Then status 204
