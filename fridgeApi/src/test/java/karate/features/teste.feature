Feature: Testando geladeira

  Scenario: Criando uma geladeira
    Given url 'https://reqres.in/api/users?page=2'
    When method GET
    Then status 200
    * print response