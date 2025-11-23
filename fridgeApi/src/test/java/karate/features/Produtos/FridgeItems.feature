Feature: Testing FridgeItems's CRUD

Background:
  * url 'http://localhost:8080'
  * header Accept = 'application/json'

  * def testFridge = { "on" : "true", "temperature" : "11" }


    Scenario: '2' Creates a fridge-item and Edit her status, and after deletes it

        # Primeiro: criar um usuario Parent
        Given path '/users'
        * def testLoginUser = { "name" : "Andre Gomes" , "email" : "AndreGomes@gmail.com" , "password" : "12010209" , "type" : "Parent" }
        * request testLoginUser
        When method POST
        Then status 200
        * def catchId = response.id
        * def catchEmail = response.email
        * def catchPassword = response.password
        And print "1"


        # Segundo: logar esse usuario Parent no sistema
        Given path '/users/login'
        * def userLogin = { "email" : #(catchEmail) , "password" : #(catchPassword) }
        * request userLogin
        When method POST
        Then status 200
        * def catchToken = response.token
        * header token = catchToken
        And print "2"


        # Terceiro: Criar a geladeira
        Given path '/fridges'
        * request testFridge
        When method POST
        Then status 200
        * def catchFridgeId = response.id
        And print "3"


        # Quarto: Criar um item
        Given path '/fridge-items'
        * def testFridgeItems = { "name" : "Suco De Uva" , "validDate" : "2026-01-09T10:00:00" , "availableForChildren" : "true" , "quantity" : 1 , "itemType" : "Drinks" , "fridge" : { "id" : #(catchFridgeId) } }
        * request testFridgeItems
        When method POST
        Then status 200
        * def fridgeItemCatchId = response.id
        And print "4"


        # Quinto: Editar o item
        Given path '/fridge-items'
        * header token = catchToken
        * def putFridgeItems = { "id" : #(fridgeItemCatchId), "name" : "ACABOU" , "validDate" : "2026-01-09T10:00:00" , "availableForChildren" : "true" , "quantity" : 1 , "itemType" : "Drinks" , "fridge" : { "id" : #(catchFridgeId) } }
        * request putFridgeItems
        When method PUT
        Then status 200
        And print "5"

        # Sexto: Excluir o item ACABOU
        Given path '/fridge-items/' , fridgeItemCatchId
        * header token = catchToken
        When method DELETE
        Then status 200
        And print "6"

        # Sexto: Excluir o usuario por conta de duplicidade
        Given path '/users/' , catchId
        When method DELETE
        Then status 200
        And print "7"


    Scenario: '2' Post and Get one FridgeItem in the database

       # Primeiro cria uma nova geladeira
       Given path '/fridges'
       * request testFridge
       When method POST
       Then status 200
       * def fridgeCatchId = response.id

       # Depois cria o item
       Given path '/fridge-items'

       #Item Criado
       * def testFridgeItems = { "name" : "Coca Cola" , "validDate" : "2026-01-09T10:00:00" , "availableForChildren" : "true" , "quantity" : 1 , "itemType" : "Drinks" , "fridge" : { "id" : #(fridgeCatchId) } }
       * request testFridgeItems
       When method POST
       Then status 200
       * def fridgeItemCatchId = response.id

       #Em busca do item
       Given path '/fridge-items' , fridgeItemCatchId
       When method GET
       Then status 200



    Scenario: '4' Get all the fridge-items fromm all fridges

       Given path '/fridge-items'
       When method GET
       Then status 200














