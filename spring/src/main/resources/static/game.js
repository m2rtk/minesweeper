'use strict';


function Cell(rowIndex, colIndex, state) {
    var visible_state = get_visible_state(state);
    return React.createElement(
        'button',
        {
            onMouseDown: event => {
                post_and_render(
                    {0: 'open', 1: 'chord', 2: 'flag'}[event.button], 
                    rowIndex, 
                    colIndex
                );
            },
            onContextMenu: (event) => { 
                event.preventDefault()
            },
            key: rowIndex + "-" + colIndex,
            className: 'cell' + (state.open ? " open" : ""),
            style: {
                color: get_cell_color(visible_state)
            }
        },
        visible_state
    );
}

function get_visible_state(state) {
    if (state.open) {
        if (state.bomb) {
            return 'X';
        }
        if (state.nearbyBombs === 0) {
            return '';
        }
        return state.nearbyBombs;
    }

    if (state.flagged) {
        return 'F';
    }

    return '';
}

function get_cell_color(visible_state) {
    if (Number.isInteger(visible_state)) {
        return [null, 'green', 'darkcyan', 'blue', 'darkgreen', 'cyan', 'darkblue', 'pink', 'yellow'][visible_state];
    }

    if (['F', 'X'].includes(visible_state)) {
        return 'red';
    }

    return 'black';
}


function Row(rowIndex, cells) {
    return React.createElement(
        'div', 
        {
            key: rowIndex,
            className: 'board-row'
        }, 
        cells.map((state, colIndex) => Cell(rowIndex, colIndex, state))
    );
}


function Board(rows) {
    return React.createElement(
        'div',
        {
            className: 'board'
        },
        rows.map((cells, rowIndex) => Row(rowIndex, cells))
    );
}

function post(endpoint, data) {
    return axios({
        method: 'post',
        url: API + endpoint,
        params: data
    }).catch(error => {
        console.log(error);
    });
}

function post_and_render(action, row, col) {
    post(action, {id:GAME_ID, row:row, col:col}).then(response => {
        handle_game_state(response.data);
    });
}

function get_input(name) {
    var val = document.querySelector("#" + name).valueAsNumber;

    if (isNaN(val)) {
        throw name + " input is not a number.";
    }

    return val;
}

function new_game() {
    try {
        var params = {
            height: get_input('height'),
            width: get_input('width'),
            bombs: get_input('bombs')
        };
    } catch(err) {
        console.log(err);
        return;
    }

    post('newgame', params).then(response => {
        GAME_ID = response.data.id;
        handle_game_state(response.data.game);
    });
}

function handle_game_state(game) {
    render_board(game.grid);
}

function render_board(grid) {
    ReactDOM.render(
        Board(grid.cells),
        document.querySelector('#board')
    );
}

var DEFAULTS = {
    height: 16, 
    width: 16, 
    bombs: 40
}

var API = '';
var GAME_ID = null;
