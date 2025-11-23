Feature: Testing Fridges's CRUD

Background:
  * url 'http://localhost:8080'
  * header Accept = 'application/json'
  * def testFridge =
          """
              {
                  "on" : "true",
                  "temperature" : "11"
              }
          """


    Scenario: '1' Post and Get one fridge in the database

    Given path '/fridges'
    * request testFridge
    When method POST
    Then status 200
    * def catchId = response.id

    # Get these fridge created previously

    Given path '/fridges/' , catchId
    When method GET
    Then status 200



    Scenario: '2' Creates a fridge and Edit her status

    Given path '/fridges'
    * request testFridge
    When method POST
    Then status 200
    * def catchId = response.id

    # Criando os novos dados
    * def putFridge = { "id" : #(catchId) , "on" : "false" , "temperature" : "9" }

    # Vai editar a geladeira recem-criada
    Given path '/fridges'
    * request putFridge
    When method PUT
    Then status 200



    Scenario: '3' Create a fridge and then delete it.

    Given path '/fridges'
    * request testFridge
    When method POST
    Then status 200
    * def catchId = response.id

    # Delete the fridge created!

    Given path '/fridges/' , catchId
    When method DELETE
    Then status 200



    Scenario: '4' Get all the fridges created

    Given path '/fridges'
    When method GET
    Then status 200
    And print response
















