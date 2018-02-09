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


def new_game(height, width, bombs):
    return post("newgame", dict(height=height, width=width, bombs=bombs))


def open(id, x, y):
    return post("open", dict(id=id, x=x, y=y))


def flag(id, x, y):
    return post("flag", dict(id=id, x=x, y=y))


def game(id):
    return get("game", dict(id=id))


class Color:
    RED = "\033[1;31m"
    BLUE = "\033[1;34m"
    CYAN = "\033[1;36m"
    GREEN = "\033[0;32m"
    RESET = "\033[0;0m"
    BOLD = "\033[;1m"
    REVERSE = "\033[;7m"


def red(s):
    return Color.RED + s + Color.RESET


def green(s):
    return Color.GREEN + s + Color.RESET


def blue(s):
    return Color.BLUE + s + Color.RESET


def bold(s):
    return Color.BOLD + s + Color.RESET


def string_of(grid):
    if grid['open']:
        if grid['bomb']:
            return red('x')
        else:
            return bold(str(grid['nearbyBombs']))
    else:
        if grid['flagged']:
            return green('F')
        else:
            return "#"


def show(cells):
    print("  ", end='')
    for i in range(len(cells[0])):
        print(" " + blue(str(i)), end='')
    print()
    c = 0
    for row in cells:
        print(" " + blue(str(c)), end='')
        for grid in row:
            print(" " + string_of(grid), end='')
        print()
        c += 1


def end(cells, win=None):
    show(cells)

    if win is None:
        pass
    elif win:
        print("You won!")
    else:
        print("You lost.")

    exit(0)


state = new_game(5, 5, 10)
id = state['id']
cells = state['grid']['cells']

while True:
    show(cells)

    i = input("?").split(" ")

    if i[0] == 'o':
        state = open(id, i[1], i[2])
    elif i[0] == 'f':
        state = flag(id, i[1], i[2])
    elif i[0] == 'exit':
        end(id)

    cells = state['grid']['cells']

    if state['gameOver']:
        end(cells, state['win'])


