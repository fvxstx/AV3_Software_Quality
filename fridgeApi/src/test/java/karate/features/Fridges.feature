Feature: Testing Fridges's CRUD
#Observação depois de cada teste eu decidir excluir as geladeiras que seriam criadas, para não encher o banco de dados

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

    #Vou deletar a geladeira para que não seja armazenada no banco de dados
    Given path '/fridges/' , catchId
    When method DELETE
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

    #Vou deletar a geladeira para que não seja armazenada no banco de dados
    Given path '/fridges/' , catchId
    When method DELETE
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

    #Por conta de deletar as geladeira acima, vai aparecer nul
    Given path '/fridges'
    When method GET
    Then status 200
    And print response
















