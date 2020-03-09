
Feature: IATA Airport code lookup

    Background:
        # * url 'http://localhost:8100/airport'
        * url baseUrl
        * configure report = { showLog: true, showAllSteps: false, logPrettyRequest: true, logPrettyResponse: true }


    Scenario: Retrieve a list of IATA airport codes
        Given path '/iata'
        When method GET
        Then status 200
        # And $ #array
        And match $ contains { iataAirportCode: 'ATL' }


    # ----- (GET) Lookup existing airport -----
    Scenario Outline: Spot check a few IATA airport codes
        Given path '/iata/<id>'
        When method GET
        Then status 200
        And match $.iataAirportCode == "<id>"
        Examples:
        | id  |
        | ATL |
        | MSP |

    # ----- (GET) lookup non-existent codes -----
    Scenario Outline: Ensure unknown IATA airport codes are not found
        Given path '/iata/<id>'
        When method GET
        Then status 404
        Examples:
        | id  |
        | SOS |
        | ZZZ |


    # ----- Add airport code (POST) -----
    # Scenario Outline: Add a new IATA airport code
    #     Given path '/iata'
    #     And request {iataAirportCode: "<id>" }
    #     When method POST
    #     Then status 201

    #     # When method GET
    #     # Then status 200
    #     # And match $.iata_code == <id>
    #     # And match $ == {"iataAirportCode":"<id>"}
    #     And match $.iataAirportCode == "<id>"
    #     Examples:
    #     | id  |
    #     | YYZ |
    #     | LAX |



    # ----- Put (Update) -----
    # Scenario: Put (Update) an IATA code
        # Given path '/iata/ATL'
        # # When method GET
        # # Then status 200

        # And request {iataAirportCode: "ATL" }
        # When method PUT
        # Then status 200

    # ----- Put (Create) -----
    # Scenario: Put an IATA code that does not yet exist
    #    Given path '/iata/SOS'
    #     When method GET
    #     Then status 404

    #     And request {iataAirportCode: "SOS" }
    #     When method PUT
    #     Then status 201

    #     Given path '/iata/SOS'
    #     When method DELETE
    #     Then status 204



    # ----- Delete -----
    # Scenario: Add, then delete an IATA airport code
    #     Given path '/iata'
    #     And request {iataAirportCode: "SOS" }
    #     When method POST
    #     Then status 201
    #         # now delete it - this is the real test
    #     Given path '/iata/SOS'
    #     When method DELETE
    #     Then status 204
