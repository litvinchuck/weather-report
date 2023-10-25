# How to use

## Authentication

To authenticate you must use Basic Auth. A default user with EDITOR role is created at startup:

```properties
username=admin@mail.com
password=password
```

## Methods

### GET

---


`GET localhost:8080/api/weather`

Returns an array of weather data.

Returns status `200 OK` if successful

---


`GET localhost:8080/api/weather?city={citites}&date={date}`

Returns weather data for the `date` and `citites`.

Returns status `200 OK` if successful.

| Path variable | Format                 | Description                                       |
|---------------|------------------------|---------------------------------------------------|
| date          | `String`, `yyyy-MM-dd` | date to search sessions for, ex `2022-10-22`      |
| citites       | `String[]`             | one or more cities to filter, eg `london,paris`   |

---

`GET localhost:8080/api/weather/{id}`

Returns a single weather data by `id`

Returns status `200 OK` if successful, `404 Not Found` otherwise

| Path variable | Format            | Description                |
|---------------|-------------------|----------------------------|
| id            | `number`          | id of weather date, eg `1` |

---

### POST

`POST localhost:8080/api/weather`

Creates a booking. Accepts [Weather Data](#Weather-object).

Returns the created [Weather Data](#Weather-object) with new id.

## Weather Object

```json
{
   "id": 1,
   "date": "1985-01-01",
   "lat": 36.1189,
   "lon": -86.6892,
   "city": "Nashville",
   "state": "Tennessee",
   "temperatures": [17.3, 16.8, 16.4, 16.0, 15.6, 15.3, 15.0, 14.9, 15.8, 18.0, 20.2, 22.3, 23.8, 24.9, 25.5, 25.7, 24.9, 23.0, 21.7, 20.8, 29.9, 29.2, 28.6, 28.1]
}
```
