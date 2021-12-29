# Aplikacja pobierająca repozytoria określonego użytkownika GitHuba
## Konfiguracja
- sklonuj repozytorium
## Wysyłanie zapytań API
Aplikacja posiada 2 endpointy: 

/list/{nazwa_uzytkownika} - zwraca listę wszystkich repozytoriów, wraz z liczbą gwiazdek, dla danego użytkownika GitHuba.<br>
/rating/{nazwa_uzytkownika} - zwraca sumę gwiazdek ze wszystkich repozytoriów danego użytkownika GitHuba.

## Możliwe kierunki rozwoju
- sortowanie repozytoriów według ilości stargazerów
- listowanie najpopularniejszych języków programowania
- dodanie uwierzytelniania za pomocą tokenu GitHub (Nieuwierzytelnieni klienci mogą wykonać do 60 zapytań do API GitHuba, a uwierzytelnienie rozszerza ilość zapytań na godzinę do 5000)
