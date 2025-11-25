Feature: Testing Users's CRUD and Login
#Observação depois de cada teste eu decidir excluir os usuarios que seriam criadas, para não encher o banco de dados

Background:
  * url 'http://localhost:8080'
  * header Accept = 'application/json'
  * def testUser =
            """
                {
                    "name" : "Gabriel Silva",
                    "email" : "GabrieslSilva@gmail.com",
                    "password" : "12010209",
                    "type" : "Children"
                }
            """


    Scenario: '1' Post and Get one User in the database

       Given path '/users'
       * request testUser
       When method POST
       Then status 200
       * def catchId = response.id

       # Get these fridge created previously
       Given path '/users/' , catchId
       When method GET
       Then status 200

       #Para que o banco de dados não seja preenchido decidi excluir o usuario
       Given path '/users/' , catchId
       When method DELETE
       Then status 200



    Scenario: '2' Creates a User and Edit her status

      Given path '/users'
      * request testUser
      When method POST
      Then status 200
      * def catchId = response.id

      # Criando os novos dados
      * def putUser = { "name" : "Gabriel Silva Santos", "email" : "GabrielSilvaSantos@gmail.com" , "password" : "10212030" , "type" : "Children" }

      # Vai editar a geladeira recem-criada
      Given path '/users/' , catchId
      * request putUser
      When method PUT
      Then status 200

      #Para que o banco de dados não seja preenchido decidi excluir o usuario
      Given path '/users/' , catchId
      When method DELETE
      Then status 200



    Scenario: '3' Create a User and then delete it.

      Given path '/users'
      * request testUser
      When method POST
      Then status 200
      * def catchId = response.id

      # Delete the user created!

      Given path '/users/' , catchId
      When method DELETE
      Then status 200



    Scenario: '4' Get all the Users created

      # Vai retornar vazio por conta de estar excluindo todos os usuarios
      Given path '/users'
      When method GET
      Then status 200
      And print "Teste numero 4" + response



    Scenario: '5' The user logs into the system and receives a token.

      Given path '/users'
      * def testLoginUser = { "name" : "CaueLuz" , "email" : "CaueLuz@gmail.com" , "password" : "12010209" , "type" : "Parent" }
      * request testLoginUser
      When method POST
      Then status 200
      * def catchEmail = response.email
      * def catchPassword = response.password
      * def catchId = response.id

      # Usuario vai logar com email e senha
      Given path '/users/login'
      * def userLogin = { "email" : #(catchEmail) , "password" : #(catchPassword) }
      * request userLogin
      When method POST
      Then status 200
      * def catchToken = response.token

      #Para que o banco de dados não seja preenchido decidi excluir o usuario
      Given path '/users/' , catchId
      When method DELETE
      Then status 200




















