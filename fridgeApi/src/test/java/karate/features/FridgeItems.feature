Feature: Testing FridgeItems's CRUD

Background:
  * url 'http://localhost:8080'
  * header Accept = 'application/json'
  * def testFridge = { "on" : "true", "temperature" : "24" }


    Scenario: '1' Creates, Edit ,  Get Only , and after deletes a fridge-item

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
        * def putFridgeItems = { "id" : #(fridgeItemCatchId), "name" : "Suco De Uva Integral" , "validDate" : "2026-01-09T10:00:00" , "availableForChildren" : "true" , "quantity" : 1 , "itemType" : "Drinks" , "fridge" : { "id" : #(catchFridgeId) } }
        * request putFridgeItems
        When method PUT
        Then status 200
        And print "5"

        # Sexto: Pegar apenas o item Suco De Uva Integral no banco de dados
        Given path '/fridge-items/' , fridgeItemCatchId
        When method GET
        Then status 200
        And print "6"

        # Setimo: Excluir o item Suco De Uva Integral
        Given path '/fridge-items/' , fridgeItemCatchId
        * header token = catchToken
        When method DELETE
        Then status 200
        And print "7"

        # Oitavo: Excluir o usuario por conta para não inclui-lo no banco de dados
        Given path '/users/' , catchId
        When method DELETE
        Then status 200
        And print "8"

       # Nono: Excluir a geladeira para não inclui-la no banco de dados
       Given path '/fridges/' , catchFridgeId
       When method DELETE
       Then status 200
       And print "9"



    Scenario: '2' Get all the fridge-items from all the fridges

       # A resposta vai ser null por conta de excluir todos os items
       Given path '/fridge-items'
       When method GET
       Then status 200














