# Aplikacja pobierająca repozytoria określonego użytkownika GitHuba
## Konfiguracja
- sklonuj repozytorium
## Wysyłanie zapytań API
Aplikacja posiada 2 endpointy: 

/repos/list/{nazwa_uzytkownika} - zwraca listę wszystkich repozytoriów, wraz z liczbą gwiazdek, dla danego użytkownika GitHuba.<br>
/repos/rating/{nazwa_uzytkownika} - zwraca sumę gwiazdek ze wszystkich repozytoriów danego użytkownika GitHuba.

Endpointy można przestetestować na przykład przy użyciu Postmana:<br>
![image](https://user-images.githubusercontent.com/95762263/147682752-21c926fc-5486-4fd3-acc5-fa03d5b74881.png)<br>
## Możliwe kierunki rozwoju
- sortowanie repozytoriów według ilości stargazerów
- listowanie najpopularniejszych języków programowania
- dodanie uwierzytelniania za pomocą tokenu GitHub (Nieuwierzytelnieni klienci mogą wykonać do 60 zapytań do API GitHuba, a uwierzytelnienie rozszerza ilość zapytań na godzinę do 5000)
## Technologie
- Kotlin 1.6
- Spring Boot 2.6.2
- JUnit5
