'use strict';

// For minified protocol

var API = 'api/';
var GAME_ID = null,
    BOARD = null,
    STATE = null;


const toList = string => string.split('');
const isOpen = state => ! ['X', 'F', '+'].includes(state)
const isEmpty = state => ['-', '+'].includes(state)

function Cell(rowIndex, colIndex, visible_state) {
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
            className: 'cell' + (isOpen(visible_state) ? " open" : ""),
            style: {
                color: getCellColor(visible_state)
            }
        },
        isEmpty(visible_state) ? ' ' : visible_state
    );
}

function getCellColor(visible_state) {
    if ( ! isNaN(visible_state)) {
        return [
            null, 'green', 'darkcyan', 
            'blue', 'darkgreen', 'cyan', 
            'darkblue', 'pink', 'yellow'
        ][parseInt(visible_state)];
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

function new_game() {
    post('newgame', get_new_game_params()).then(response => {
        var lines = response.data.split('\n');

        GAME_ID = lines[0];
        STATE = lines[1];
        BOARD = lines.splice(2).map(toList);

        render_board();
    });
}

function post_and_render(action, row, col) {
    post(action, {id:GAME_ID, row:row, col:col}).then(response => {
        var lines = response.data.split('\n');
        if (action === 'flag') {
            toggle_flag(row, col);
        } else {
            STATE = lines[0];
            BOARD = lines.splice(1).map(toList);
        }

        render_board();
    });
}

function toggle_flag(row, col) {
    var current = BOARD[row][col];
    if      (current === '+')   BOARD[row][col] = 'F';
    else if (current === 'F')   BOARD[row][col] = '+';
}

function render_board() {
    ReactDOM.render(
        Board(BOARD),
        document.querySelector('#board')
    );
}

function get_new_game_params() {
    try {
        var params = {
            height: get_input('height'),
            width: get_input('width'),
            bombs: get_input('bombs')
        };
    } catch(err) {
        console.log(err);
        throw "Invalid parameters";
    }
}

function get_input(name) {
    var val = document.querySelector("#" + name).valueAsNumber;

    if (isNaN(val)) {
        throw name + " input is not a number.";
    }

    return val;
}
