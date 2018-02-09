import requests
from pprint import pprint

URL = "http://localhost:8081/"


def request(response):
    if response.ok:
        return response.json()
    else:
        pprint(response.json())
        exit(1)


def post(endpoint, data=None):
    if data is None:
        data = {}

    return request(requests.post(
        url=URL + endpoint,
        data=data
    ))


def get(endpoint, params=None):
    if params is None:
        params = {}

    return request(requests.get(
        url=URL + endpoint,
        params=params
    ))


def start_game(height, width, bombs):
    return post("startgame", dict(height=height, width=width, bombs=bombs))['newGameId']


def open(id, x, y):
    return post("open", dict(id=id, x=x, y=y))


def flag(id, x, y):
    return post("flag", dict(id=id, x=x, y=y))


def cell(id, x, y):
    return get("cell", dict(id=id, x=x, y=y))


def grid(id):
    return get("grid", dict(id=id))


def string_of(cell):
    if cell['open']:
        if cell['bomb']:
            return 'X'
        else:
            return str(cell['nearbyBombs'])
    else:
        if cell['flagged']:
            return 'F'
        else:
            return "#"


def show(cells):
    for row in cells:
        for cell in row:
            print(string_of(cell), end='')

        print()


def end(cells, msg=None):
    show(cells)
    if msg is not None:
        print(msg)
    exit(0)


id = start_game(10, 10, 25)

while True:
    show(grid(id)['cells'])

    i = input("?").split(" ")

    if i[0] == 'o':
        res = open(id, i[1], i[2])
        if res['gameOver']:
            end(res['grid']['cells'], res['message'])
    elif i[0] == 'f':
        flag(id, i[1], i[2])
    elif i[0] == 'exit':
        end(id)

