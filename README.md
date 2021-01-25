<h1>Téma</h1>

A szakdogám témája egy munka nyilvántartó rendszer, aminek segítségével a dolgozó képes adott projekthez, adott típusú munkát rögzíteni, illetve a munkáltató képes kezelni a dolgozókat, ügyfeleket, projekteket, munkákat, tud elszámolásokat készíteni ügyfél, illetve dolgozo felé. Alapból 2 külön, jelszóval védett felületből áll: dolgozói felület, admin felület. Admin felületen lényegében csak CRUD-okat tartalmaz. A dolgozói felület kicsit extrább, mert a munka rögzítését egy egyedileg le gyártott stopperrel szeretném megoldani, ami 5-10 percenként screenshotokat készít a dolgozó képernyőjéről, melyeket utána az adott munkához csatolva a munkáltató megtekinthet.
Mindezt Java-ban szeretném megvalósítani, web alapon, Spring boot segítségével. 

<h1>Admin felület</h1>
Oldalak: Ügyfelek, Dolgozók, Projektek, Elszámolás

<h3>Ügyfelek:</h3>
  <ul>
    <li>Új ügyfél felvétele</li>
    <li>Ügyfél meglévő projekthez kötés</li>
    <li>Táblában ügyfelek megjelenítése</li>
    <li>Ügyfelek aktivitásának állítása</li>
    <li>Ügyfelek adatainak módosítása</li>
    <li>Kötések megjelenítése</li>
    <li>Kötések törlése</li>
  </ul>
<h3>Dolgozók:</h3>
  <ul>
    <li>Új dolgozó felvétele</li>
    <li>Dolgozó meglévő projekthez kötés</li>
    <li>Táblában dolgozók megjelenítése</li>
    <li>Dolgozók aktivitásának állítása(belépés szabályozás)</li>
    <li>Dolgozók adatainak módosítása(érzékeny adatok kivétel)</li>
    <li>Kötések megjelenítése</li>
    <li>Kötések módosítása</li>
    <li>Kötések törlése</li>
  </ul>
